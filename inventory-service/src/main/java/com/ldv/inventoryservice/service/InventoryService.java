package com.ldv.inventoryservice.service;

import com.ldv.inventoryservice.entity.Inventory;
import com.ldv.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public Inventory addStock(UUID productId, Integer quantity) {
        log.info("Updating stock for product {}. Adding: {}", productId, quantity);

        Inventory inventory = inventoryRepository.findById(productId)
                .orElse(Inventory.builder()
                        .productId(productId)
                        .stockQuantity(0)
                        .build());

        inventory.setStockQuantity(inventory.getStockQuantity() + quantity);
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public void deductStock(UUID productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        if (inventory.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + productId); //TODO improve this
        }

        inventory.setStockQuantity(inventory.getStockQuantity() - quantity);
        inventoryRepository.save(inventory);
        log.info("Stock updated for product {}. New quantity: {}", productId, inventory.getStockQuantity());
    }

    @Transactional(readOnly = true)
    public List<Inventory> getAllInventory() {
        log.info("Fetching all inventory items");
        return inventoryRepository.findAll();
    }
}
