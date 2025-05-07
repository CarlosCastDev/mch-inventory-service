package org.mch.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mch.repository.InventoryRepository;

@ApplicationScoped
public class InventoryService {

    @Inject
    InventoryRepository inventoryRepository;

    public boolean hasSufficientStock(Long medicineId, int quantity) {
        return inventoryRepository.findByIdOptional(medicineId)
                .map(inventory -> inventory.quantity >= quantity)
                .orElse(false);
    }
}

