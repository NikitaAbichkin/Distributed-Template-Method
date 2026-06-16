package com.company.Service;


import com.company.Model.OutboxEvent;
import com.company.Repository.OutboxEventRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class OutBoxService {
    private  final OutboxEventRepository outboxEventRepository;

    public OutBoxService(OutboxEventRepository outboxEventRepository) {
        this.outboxEventRepository = outboxEventRepository;
    }

    public void SaveEvent(String topic, String eventType, String payload){
        OutboxEvent outboxEvent = new OutboxEvent();
        String eventID = UUID.randomUUID().toString();

        outboxEvent.setEventId(eventID);
        outboxEvent.setEventType(eventType);
        outboxEvent.setStatus("new");
        outboxEvent.setTopic(topic);
        outboxEvent.setPayload(payload);

        outboxEventRepository.save(outboxEvent);
    }
}
