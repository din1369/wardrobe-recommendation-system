package com.hnm.userexpecienceservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "item_variation")
public class ItemVariation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variation_id")
    private Long variationId;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "sku", unique = true)
    private String sku;

    @Column(name = "color")
    private String color;

    @Column(name = "size")
    private String size;

    @Column(name = "additional_price")
    private BigDecimal additionalPrice;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "material")
    private String material;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
