package com.company;

import com.company.Dto.OrderCreatedEvent;
import com.company.Dto.OrderRequest;
import com.company.Dto.OrderUpdatingRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderServiceInterface {
    public OrderCreatedEvent createOrder(OrderRequest request) throws JsonProcessingException;
    public String deleteOrder(Long id) throws JsonProcessingException;
    public OrderCreatedEvent updateOrder(OrderUpdatingRequest orderRequest) throws JsonProcessingException;
    public OrderCreatedEvent GetOrder (Long id );
}
