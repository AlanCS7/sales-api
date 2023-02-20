package io.github.alancs7.sales.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsOrderDTO {

    private Long id;
    private String customerName;
    private String cpf;
    private LocalDate orderDate;
    private BigDecimal total;
    private String status;
    private List<DetailsOrderItemDTO> items;
}
