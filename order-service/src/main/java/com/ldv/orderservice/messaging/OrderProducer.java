package com.ldv.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.topic.order-placed}")
    private String topicName;

    public void sendOrderEvent(String orderId) {
        log.info("### PRODUCER: Sending event for order Id: {}", orderId);

        // Inviamo l'ID dell'ordine come "payload" del messaggio
        kafkaTemplate.send(topicName, orderId);
    }
}
