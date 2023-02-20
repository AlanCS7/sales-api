package io.github.alancs7.sales.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", length = 100)
    @NotEmpty(message = "The description field is required.")
    private String description;

    @Column(name = "unit_price", precision = 20, scale = 2)
    @NotNull(message = "The price field is required.")
    private BigDecimal price;

}
