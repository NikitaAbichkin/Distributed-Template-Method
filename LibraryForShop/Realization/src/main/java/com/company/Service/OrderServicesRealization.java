package com.company.Service;

import com.company.Dto.OrderCreatedEvent;
import com.company.Dto.OrderRequest;
import com.company.Dto.OrderUpdatingRequest;
import com.company.Model.Order;
import com.company.Repository.OrderRepository;
import com.company.OrderServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServicesRealization implements OrderServiceInterface {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepository orderRepository;
    private  final  ObjectMapper objectMapper;
    private  final  OutBoxService outBoxService;

    public OrderServicesRealization(KafkaTemplate<String, Object> kafkaTemplate, OrderRepository orderRepository, ObjectMapper objectMapper, OutBoxService outBoxService) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
        this.outBoxService = outBoxService;
    }

    @Override
    public OrderCreatedEvent createOrder(OrderRequest request) throws JsonProcessingException {
        Order order = fromRequsetToEntity(request);
        order = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getName(),
                order.getQuantity(),
                order.getCost(),
                order.getStatus());

        // пайлоад в виде джсона
        String payload = objectMapper.writeValueAsString(event);

        // сохраняем событие
        outBoxService.SaveEvent("Creating-or-Updating-order-topic","CreatingOrder", payload);

        return  event;
    }

    @Override
    public String deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        orderRepository.deleteById(id);
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getName(),
                order.getQuantity(),
                order.getCost(),
                order.getStatus());
        
        kafkaTemplate.send("Deleting-order-topic", event);
        return "Order with id: "+ order.getId()+" was deleted";
    }

    @Override
    public OrderCreatedEvent updateOrder(OrderUpdatingRequest orderRequest) {
        Order order = fromOrderUpdatingRequesttoOrder(orderRequest);
        order = orderRepository.save(order);
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getName(),
                order.getQuantity(),
                order.getCost(),
                order.getStatus());
        kafkaTemplate.send("Creating-or-Updating-order-topic", event);
        return event;
    }

    @Override
    public OrderCreatedEvent GetOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getName(),
                order.getQuantity(),
                order.getCost(),
                order.getStatus());
       return event;
    }

    // Из Дто к сущности
    public Order fromRequsetToEntity(OrderRequest orderRequest) {
        Order order = new Order();
        if (orderRequest.getCost() != null)
            order.setCost(orderRequest.getCost());
        if (orderRequest.getName() != null)
            order.setName(orderRequest.getName());
        if (orderRequest.getQuantity() != null)
            order.setQuantity(orderRequest.getQuantity());
        if (orderRequest.getStatus() != null)
            order.setStatus(orderRequest.getStatus());
        return order;
    }

    // из Дто к сущности
    public Order fromOrderUpdatingRequesttoOrder(OrderUpdatingRequest orderUpdatingRequest) {
        Order order = new Order();
        if (orderUpdatingRequest.getId() != null)
            order.setId(orderUpdatingRequest.getId());
        if (orderUpdatingRequest.getCost() != null)
            order.setCost(orderUpdatingRequest.getCost());
        if (orderUpdatingRequest.getName() != null)
            order.setName(orderUpdatingRequest.getName());
        if (orderUpdatingRequest.getQuantity() != null)
            order.setQuantity(orderUpdatingRequest.getQuantity());
        if (orderUpdatingRequest.getStatus() != null)
            order.setStatus(orderUpdatingRequest.getStatus());
        return order;
    }

}
