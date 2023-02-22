package io.github.alancs7.sales.service.impl;

import io.github.alancs7.sales.api.dto.CredentialsDTO;
import io.github.alancs7.sales.api.dto.TokenDTO;
import io.github.alancs7.sales.domain.entity.User;
import io.github.alancs7.sales.domain.repository.UserRepository;
import io.github.alancs7.sales.exception.DuplicateDataException;
import io.github.alancs7.sales.exception.InvalidPasswordException;
import io.github.alancs7.sales.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public TokenDTO save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateDataException("E-mail already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return new TokenDTO(user.getUsername(), token);
    }

    public TokenDTO authenticate(CredentialsDTO credentials) {

        User user = User.builder().email(credentials.getEmail())
                .password(credentials.getPassword())
                .build();

        UserDetails userDetails = loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(user);

        if (passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            return new TokenDTO(userDetails.getUsername(), token);
        }

        throw new InvalidPasswordException("Invalid password.");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

}
