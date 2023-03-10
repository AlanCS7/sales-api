package io.github.alancs7.sales.api.controller;

import io.github.alancs7.sales.api.dto.DetailsOrderDTO;
import io.github.alancs7.sales.api.dto.OrderDTO;
import io.github.alancs7.sales.api.dto.UpdateOrderStatusDTO;
import io.github.alancs7.sales.domain.enums.OrderStatus;
import io.github.alancs7.sales.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody @Valid OrderDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto).getId());
    }

    @GetMapping("{id}")
    public ResponseEntity<DetailsOrderDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getFullOrder(id));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable("id") Long id, @RequestBody UpdateOrderStatusDTO dto) {
        service.updateOrderStatus(id, OrderStatus.valueOf(dto.getNewStatus()));
    }
}
