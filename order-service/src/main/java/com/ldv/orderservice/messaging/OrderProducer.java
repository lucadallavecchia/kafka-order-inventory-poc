package com.ldv.orderservice.messaging;

import com.ldv.orderservice.messaging.dto.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Value("${app.kafka.topic.order-placed}")
    private String topicName;

    public void sendOrderEvent(OrderPlacedEvent event) {
        // Key will be the order id -> to be sure that order of messages related to this order will be satisfied
        String key = event.orderId().toString();

        log.info("### PRODUCER: Sending event to topic {} [Key: {}] [Payload: {}]", topicName, key, event);

        // Messages partitioned by key (Same id = same partition --> in kafka message order just in the same partition(
        kafkaTemplate.send(topicName, key, event);
    }
}
