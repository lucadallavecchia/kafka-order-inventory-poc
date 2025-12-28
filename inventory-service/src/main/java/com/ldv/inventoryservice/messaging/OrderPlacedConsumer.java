package com.ldv.inventoryservice.messaging;

import com.ldv.inventoryservice.messaging.dto.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderPlacedConsumer {

    @KafkaListener(topics = "order-placed-topic", groupId = "inventory-group")

    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("### CONSUMER: Received Order with ID: {}", event.orderId());
        log.info("Client: {} - Created at: {}", event.customerId(), event.createdAt());

        event.items().forEach(item ->
                log.info("-> Product ID: {} | Quantity required: {}", item.productId(), item.quantity())
        );

        // TODO: Qui chiameremo l'InventoryService per scalare le quantità a DB
    }
}
