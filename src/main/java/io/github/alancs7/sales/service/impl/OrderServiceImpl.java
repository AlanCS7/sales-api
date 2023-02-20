package io.github.alancs7.sales.service.impl;

import io.github.alancs7.sales.api.dto.DetailsOrderDTO;
import io.github.alancs7.sales.api.dto.DetailsOrderItemDTO;
import io.github.alancs7.sales.api.dto.OrderDTO;
import io.github.alancs7.sales.api.dto.OrderItemDTO;
import io.github.alancs7.sales.domain.entity.Customer;
import io.github.alancs7.sales.domain.entity.Order;
import io.github.alancs7.sales.domain.entity.OrderItem;
import io.github.alancs7.sales.domain.entity.Product;
import io.github.alancs7.sales.domain.enums.OrderStatus;
import io.github.alancs7.sales.domain.repository.CustomerRepository;
import io.github.alancs7.sales.domain.repository.OrderItemRepository;
import io.github.alancs7.sales.domain.repository.OrderRepository;
import io.github.alancs7.sales.domain.repository.ProductRepository;
import io.github.alancs7.sales.exception.BusinessException;
import io.github.alancs7.sales.exception.ResourceNotFoundException;
import io.github.alancs7.sales.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order save(OrderDTO dto) {

        Customer customer = customerRepository.findById(dto.getCustomer())
                .orElseThrow(() -> new BusinessException("Invalid customer code."));

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);
        order.setStatus(OrderStatus.REALIZED);

        List<OrderItem> orderItems = convertItems(order, dto.getItems());
        orderItemRepository.saveAll(orderItems);

        order.setTotal(calcTotalOrder(orderItems));

        return orderRepository.save(order);
    }

    @Override
    public DetailsOrderDTO getFullOrder(Long id) {
        return orderRepository.findById(id)
                .map(this::convertOrder)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order %s not found.", id)));
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long id, OrderStatus orderStatus) {
        orderRepository.findById(id)
                .map(order -> {
                    if (order.getStatus().name().equals(orderStatus.name())) {
                        throw new BusinessException("Status is already " + orderStatus.name());
                    }

                    order.setStatus(orderStatus);
                    return orderRepository.save(order);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Order %s not found.", id)));
    }

    private List<OrderItem> convertItems(Order order, List<OrderItemDTO> items) {
        if (items.isEmpty()) {
            throw new BusinessException("It is not possible to place an order without items.");
        }

        return items
                .stream()
                .map(dto -> {
                    Product product = productRepository.findById(dto.getProduct())
                            .orElseThrow(() -> new BusinessException("Invalid product code: " + dto.getProduct()));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(dto.getQuantity());
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    return orderItem;
                }).toList();
    }

    private BigDecimal calcTotalOrder(List<OrderItem> orderItems) {
        BigDecimal totalOrder = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            totalOrder = totalOrder.add(orderItem.getProduct().getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
        }
        return totalOrder;
    }

    private DetailsOrderDTO convertOrder(Order order) {
        return DetailsOrderDTO.builder()
                .id(order.getId())
                .customerName(order.getCustomer().getName())
                .cpf(order.getCustomer().getCpf())
                .orderDate(order.getOrderDate())
                .total(order.getTotal())
                .status(order.getStatus().name())
                .items(convertOrderItem(order.getItems()))
                .build();
    }

    private List<DetailsOrderItemDTO> convertOrderItem(List<OrderItem> items) {
        if (items.isEmpty()) {
            return Collections.emptyList();
        }

        return items.stream().map(orderItem ->
                        DetailsOrderItemDTO.builder()
                                .productDescription(orderItem.getProduct().getDescription())
                                .unitPrice(orderItem.getProduct().getPrice())
                                .quantity(orderItem.getQuantity())
                                .total(orderItem.getProduct().getPrice().multiply(new BigDecimal(orderItem.getQuantity())))
                                .build())
                .toList();
    }

}