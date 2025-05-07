package org.mch.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inventory_items")
public class InventoryItem extends PanacheEntity {

    // Nombre del producto
    @Column(nullable = false)
    public String productName;

    // Descripción del producto
    @Column(length = 1000)
    public String productDescription;

    // Cantidad disponible en stock
    public Integer quantity;

    // Precio del producto
    public BigDecimal price;

    // Fecha de expiración (opcional)
    public LocalDate expirationDate;
}

