package com.ldv.inventoryservice.repository;

import com.ldv.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
}
