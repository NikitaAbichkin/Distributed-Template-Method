package com.company.Service;

import com.company.Dto.OrderRequest;
import com.company.Dto.OrderResponse;
import com.company.Dto.OrderUpdatingRequest;
import com.company.Model.Order;
import com.company.OrderServiceInterface;
import com.company.Repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.OptionalDouble;

@Service
// тут методы которые уже реально обрабатывают и делают записи в базу
public class RealOrderService {
    private  final OrderRepository orderRepository;
    private  final   ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory;

    public RealOrderService(OrderRepository orderRepository, ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory) {
        this.orderRepository = orderRepository;
        this.kafkaListenerContainerFactory = kafkaListenerContainerFactory;
    }

    @KafkaListener(topics = "Creating-order-topic", groupId = "userGroup")
    public  void createOrder(OrderRequest request){
        Order order = fromRequsetToEntity(request);
        orderRepository.save(order);
    }
    @KafkaListener(topics = "Deleting-order-topic", groupId = "userGroup")
    public void deleteOrder(Long id ){
        orderRepository.deleteById(id);
    }

    @KafkaListener(topics = "getOrder-order-topic", groupId = "userGroup")
    public  void getOrder(Long id ){
        orderRepository.getById(id);
    }

    @KafkaListener(topics = "Updating-order-topic", groupId = "userGroup")
    public void updatingOrder(OrderUpdatingRequest orderUpdatingRequest){
        Order order = fromOrderUpdatingRequesttoOrder(orderUpdatingRequest);
        orderRepository.save(order);
    }

    // Из Дто к сущности
    public Order fromRequsetToEntity(OrderRequest orderRequest){
        Order order = new Order();
        if (orderRequest.getCost()!=null)
            order.setCost(orderRequest.getCost());
        if(orderRequest.getName()!=null)
            order.setName(orderRequest.getName());
        if (orderRequest.getQuantity()!=null)
            order.setQuantity(orderRequest.getQuantity());
        if(orderRequest.getStatus()!=null)
            order.setStatus(orderRequest.getStatus());
        return  order;
    }
    // из Дто к сущности
    public  Order fromOrderUpdatingRequesttoOrder( OrderUpdatingRequest orderUpdatingRequest){
        Order order = new Order();
        if (orderUpdatingRequest.getId()!=null)
            order.setId(orderUpdatingRequest.getId());
        if (orderUpdatingRequest.getCost()!=null)
            order.setCost(orderUpdatingRequest.getCost());
        if(orderUpdatingRequest.getName()!=null)
            order.setName(orderUpdatingRequest.getName());
        if (orderUpdatingRequest.getQuantity()!=null)
            order.setQuantity(orderUpdatingRequest.getQuantity());
        if(orderUpdatingRequest.getStatus()!=null)
            order.setStatus(orderUpdatingRequest.getStatus());
        return  order;
    }
}
