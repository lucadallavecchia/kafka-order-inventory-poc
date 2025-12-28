package com.ldv.inventoryservice.messaging;

import com.ldv.inventoryservice.messaging.dto.InventoryResponseEvent;
import com.ldv.inventoryservice.messaging.dto.OrderPlacedEvent;
import com.ldv.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderPlacedConsumer {

    private final InventoryService inventoryService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-placed-topic", groupId = "inventory-group")
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("### Received Order: {}", event.orderId());

        try {
            event.items().forEach(item ->
                    inventoryService.deductStock(item.productId(), item.quantity())
            );
            sendResponse(event.orderId(), "SUCCESS", "Stock reserved successfully");

        } catch (Exception e) { // TODO improve error management
            log.error("### Error updating stock for order {}: {}", event.orderId(), e.getMessage());
            sendResponse(event.orderId(), "FAILURE", e.getMessage());
        }
    }

    private void sendResponse(UUID orderId, String status, String message) {
        InventoryResponseEvent response = new InventoryResponseEvent(orderId, status, message);

        // we use orderId as key again
        kafkaTemplate.send("inventory-response-topic", orderId.toString(), response);

        log.info("### Sent response for order {} with key: {}", orderId, orderId);
    }
}
