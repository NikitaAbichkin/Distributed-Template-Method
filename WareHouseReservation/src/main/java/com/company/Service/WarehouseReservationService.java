package com.company.Service;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.company.Dto.OrderCreatedEvent;
import com.company.Dto.OrderRequest;
import com.company.Model.WarehouseReservation;
import com.company.Repository.WarehouseReservationRepository;

@Service
public class WarehouseReservationService {
    private final WarehouseReservationRepository  warehouseReservationRepository ;

   public WarehouseReservationService(WarehouseReservationRepository warehouseReservationRepository) {
    this.warehouseReservationRepository = warehouseReservationRepository;
}
    
    
    @KafkaListener(topics = "Creating-or-Updating-order-topic", groupId = "OrderReservation")
    public void  ReservationOrderCreate(OrderCreatedEvent event){
        WarehouseReservation warehouseReservation = new WarehouseReservation();
        warehouseReservation.setOrderId(event.orderId());
        warehouseReservation.setProductName(event.name());
        warehouseReservation.setQuantity(event.quantity());
        warehouseReservation.setStatus(event.status());
        warehouseReservationRepository.save(warehouseReservation);
    }
    
    @KafkaListener(topics = "Deleting-order-topic", groupId = "OrderReservation")
    public void  ReservationOrderDelete(OrderCreatedEvent event){
        warehouseReservationRepository.deleteById(event.orderId());
    } 

}