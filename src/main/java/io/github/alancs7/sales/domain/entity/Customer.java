package io.github.alancs7.sales.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    @NotEmpty(message = "The name field is required.")
    private String name;

    @Column(name = "cpf", length = 11)
    @NotEmpty(message = "The cpf field is required.")
    @CPF(message = "CPF is not valid.")
    private String cpf;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<Order> orders;

}
