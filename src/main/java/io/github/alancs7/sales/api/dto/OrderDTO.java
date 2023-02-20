package io.github.alancs7.sales.api.dto;

import io.github.alancs7.sales.validation.NotEmptyList;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @NotNull(message = "Inform the customer code.")
    private Long customer;

    @NotEmptyList(message = "It is not possible to place an order without items.")
    private List<OrderItemDTO> items;
}
