package com.ldv.orderservice.messaging;

import com.ldv.orderservice.messaging.dto.InventoryResponseEvent;
import com.ldv.orderservice.model.OrderStatus;
import com.ldv.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryResponseConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "inventory-response-topic", groupId = "order-group")
    public void handleInventoryResponse(InventoryResponseEvent event) {
        log.info("### RECEIVED RESPONSE: Order {} status is {}", event.orderId(), event.status());

        if ("SUCCESS".equals(event.status())) {
            log.info("Stock confirmed! Updating order to COMPLETED.");
            orderService.updateOrderStatus(event.orderId(), OrderStatus.PLACED);
        } else {
            log.warn("Stock failed: {}. Updating order to FAILED.", event.message());
            orderService.updateOrderStatus(event.orderId(), OrderStatus.REJECTED);
        }
    }
}
