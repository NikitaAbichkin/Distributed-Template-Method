package com.company.Controller;

import com.company.Dto.OrderRequest;
import com.company.Dto.OrderResponse;
import com.company.OrderServiceInterface;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private  final OrderServiceInterface orderServiceInterface;

    public OrderService(OrderServiceInterface orderServiceInterface) {
        this.orderServiceInterface = orderServiceInterface;
    }

    public String createOrder(OrderRequest orderRequest){
        // сама реализация и то как это будет сохраняться в базу
        // лежит в либе
        orderServiceInterface.createOrder(orderRequest);
        return "я вкинул в обработку";
    }
}

