package com.ldv.inventoryservice.controller;

import com.ldv.inventoryservice.controller.dto.InventoryRequest;
import com.ldv.inventoryservice.entity.Inventory;
import com.ldv.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/stock-update")
    public ResponseEntity<Inventory> updateStock(@Valid @RequestBody InventoryRequest request) {
        Inventory updatedInventory = inventoryService.addStock(request.productId(), request.quantity());
        return ResponseEntity.ok(updatedInventory);
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        List<Inventory> inventoryList = inventoryService.getAllInventory();
        return ResponseEntity.ok(inventoryList);
    }
}