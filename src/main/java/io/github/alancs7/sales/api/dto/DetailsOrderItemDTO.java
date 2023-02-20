package io.github.alancs7.sales.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsOrderItemDTO {

    private String productDescription;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal total;
}
