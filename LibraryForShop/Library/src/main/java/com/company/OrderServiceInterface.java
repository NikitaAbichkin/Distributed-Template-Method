package com.company;

import com.company.Dto.OrderRequest;
import com.company.Dto.OrderUpdatingRequest;

public interface OrderServiceInterface {
    public void createOrder(OrderRequest request);
    public void deleteOrder(Long id);
    public void updateOrder(OrderUpdatingRequest orderRequest);
    public void GetOrder (Long id );
}
