package com.company.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "outbox_events")
@Setter
@Getter
public class OutboxEvent {

    // сам генерится
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // индивидуально в каждом методе UUUD
    private String eventId;
    // топик для кафки
    private String topic;

    // название бизнес события
    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private String status; // NEW / SENT
}