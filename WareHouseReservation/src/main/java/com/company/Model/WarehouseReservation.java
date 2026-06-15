package com.company.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "warehouse_reservations")
@Getter
@Setter
@NoArgsConstructor
public class WarehouseReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // id заказа из order-service
    @Column(name = "order_id")
    private Long orderId;

    // название товара из заказа
    @Column(name = "product_name")
    private String productName;

    // сколько товара надо зарезервировать
    @Column(name = "quantity")
    private Integer quantity;

    // CREATED / RESERVED / FAILED
    @Column(name = "status")
    private String status;
}