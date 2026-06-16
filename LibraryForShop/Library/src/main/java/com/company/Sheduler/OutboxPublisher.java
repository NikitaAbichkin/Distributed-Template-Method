package com.company.Sheduler;

import com.company.Dto.OrderCreatedEvent;
import com.company.Model.OutboxEvent;
import com.company.Repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OutboxPublisher {
    private final OutboxEventRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;


    public OutboxPublisher(OutboxEventRepository outboxRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 3000)
    public void PublishEvents() throws ExecutionException, InterruptedException {
        // берем со статусом 10 "new"
        List<OutboxEvent> outboxEvents = outboxRepository.findTopByStatusOrderByEventId("new");

        for (OutboxEvent event : outboxEvents) {
            try {
                kafkaTemplate.send(
                        event.getTopic(),
                        event.getId().toString(),
                        event.getPayload());

                event.setStatus("sent");
                outboxRepository.save(event);

            } catch (Exception e) {
                event.setStatus("FAILED");
                outboxRepository.save(event);
            }
        }
    }
}
