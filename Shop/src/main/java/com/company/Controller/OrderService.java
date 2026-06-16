package com.company.Controller;

import com.company.Dto.OrderCreatedEvent;
import com.company.Dto.OrderRequest;
import com.company.Dto.OrderResponse;
import com.company.Dto.OrderUpdatingRequest;
import com.company.OrderServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private  final OrderServiceInterface orderServiceInterface;

    public OrderService(OrderServiceInterface orderServiceInterface) {
        this.orderServiceInterface = orderServiceInterface;
    }

    public String createOrder(OrderRequest orderRequest) throws JsonProcessingException {
        // сама реализация и то как это будет сохраняться в базу
        // лежит в либе
        orderServiceInterface.createOrder(orderRequest);
        return "я вкинул в обработку";
    }

     public String updateOrder(OrderUpdatingRequest orderRequest) throws JsonProcessingException {
        // сама реализация и то как это будет сохраняться в базу
        // лежит в либе
        orderServiceInterface.updateOrder(orderRequest);
        return "я вкинул в обработку";
    }

}

