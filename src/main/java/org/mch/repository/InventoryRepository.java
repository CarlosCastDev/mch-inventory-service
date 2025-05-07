package org.mch.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mch.entity.InventoryItem;

@ApplicationScoped
public class InventoryRepository implements PanacheRepository<InventoryItem> {
}