package io.github.alancs7.sales.service;

import io.github.alancs7.sales.api.dto.DetailsOrderDTO;
import io.github.alancs7.sales.api.dto.OrderDTO;
import io.github.alancs7.sales.domain.entity.Order;
import io.github.alancs7.sales.domain.enums.OrderStatus;

public interface OrderService {

    Order save(OrderDTO dto);

    DetailsOrderDTO getFullOrder(Long id);

    void updateOrderStatus(Long id, OrderStatus orderStatus);
}
