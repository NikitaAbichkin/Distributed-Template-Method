package com.company.Service;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.company.Dto.OrderCreatedEvent;
import com.company.Dto.OrderRequest;
import com.company.Model.WarehouseReservation;
import com.company.Repository.WarehouseReservationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WarehouseReservationService {
    private final WarehouseReservationRepository warehouseReservationRepository;
    private final ObjectMapper objectMapper;

    public WarehouseReservationService(WarehouseReservationRepository warehouseReservationRepository) {
        this.warehouseReservationRepository = warehouseReservationRepository;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "CreatingOrder-topic", groupId = "OrderReservation")
    public void ReservationOrderCreate(String eventJson) {
        
        try {
            OrderCreatedEvent  event = objectMapper.readValue(eventJson, OrderCreatedEvent.class);
            WarehouseReservation warehouseReservation = new WarehouseReservation();
            warehouseReservation.setOrderId(event.orderId());
            warehouseReservation.setProductName(event.name());
            warehouseReservation.setQuantity(event.quantity());
            warehouseReservation.setStatus(event.status());
            warehouseReservationRepository.save(warehouseReservation);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
    }

    @KafkaListener(topics = "UpdatingOrder-topic", groupId = "OrderReservation")
    public void ReservationOrderUpdate(String eventJson) {
        
        try {
            OrderCreatedEvent  event = objectMapper.readValue(eventJson, OrderCreatedEvent.class);
            WarehouseReservation warehouseReservation =warehouseReservationRepository.findByOrderId(event.orderId()).orElseThrow();
            warehouseReservation.setOrderId(event.orderId());
            warehouseReservation.setProductName(event.name());
            warehouseReservation.setQuantity(event.quantity());
            warehouseReservation.setStatus(event.status());
            warehouseReservationRepository.save(warehouseReservation);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
    }

    @KafkaListener(topics = "Deleting-order-topic", groupId = "OrderReservation")
    public void ReservationOrderDelete(OrderCreatedEvent event) {
        warehouseReservationRepository.deleteById(event.orderId());
    }

}