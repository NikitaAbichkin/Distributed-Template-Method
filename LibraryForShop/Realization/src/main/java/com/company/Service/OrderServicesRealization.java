package com.company.Service;

import com.company.Dto.OrderRequest;
import com.company.Dto.OrderUpdatingRequest;
import com.company.OrderServiceInterface;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServicesRealization implements OrderServiceInterface {
    private  final KafkaTemplate<String,Object> kafkaTemplate;

    public OrderServicesRealization( KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createOrder(OrderRequest request){
        kafkaTemplate.send("Creating-order-topic", request);
    }

    public void deleteOrder(Long id){
        kafkaTemplate.send("Deleting-order-topic", id);
    }

    public void updateOrder(OrderUpdatingRequest orderRequest){
        kafkaTemplate.send("Updating-order-topic",orderRequest);
    }

    public void GetOrder (Long id ){
        kafkaTemplate.send("getOrder-order-topic", id);
    }

}
