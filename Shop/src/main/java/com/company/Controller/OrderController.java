package com.company.Controller;

import com.company.Dto.OrderRequest;
import com.company.Dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private  final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "create order")
    @PostMapping("/createOrder")
    public String createOrder( @RequestBody OrderRequest orderRequest){
        return orderService.createOrder( orderRequest);
    }
}
