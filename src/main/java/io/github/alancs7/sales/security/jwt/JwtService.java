package io.github.alancs7.sales.security.jwt;

import io.github.alancs7.sales.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.signature-key}")
    private String signatureKey;

    public String generateToken(User user) {
        long exp = Long.parseLong(expiration);
        LocalDateTime dateHourExp = LocalDateTime.now().plusMinutes(exp);
        Date date = Date.from(dateHourExp.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setExpiration(date)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws ExpiredJwtException {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String extractUsername(String token) throws ExpiredJwtException {
        return getClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                .isBefore(LocalDateTime.now());
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signatureKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
