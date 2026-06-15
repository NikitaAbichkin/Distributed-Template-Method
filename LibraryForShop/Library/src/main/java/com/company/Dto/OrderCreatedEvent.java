package com.company.Dto;

public record OrderCreatedEvent(
        Long orderId,
        String name,
        Integer quantity,
        Integer cost,
        String status
) {}