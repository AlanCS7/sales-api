package io.github.alancs7.sales.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusDTO {
    private String newStatus;
}
