package com.company;

import com.company.Dto.OrderCreatedEvent;
import com.company.Dto.OrderRequest;
import com.company.Dto.OrderUpdatingRequest;

public interface OrderServiceInterface {
    public OrderCreatedEvent createOrder(OrderRequest request);
    public String deleteOrder(Long id);
    public OrderCreatedEvent updateOrder(OrderUpdatingRequest orderRequest);
    public OrderCreatedEvent GetOrder (Long id );
}
