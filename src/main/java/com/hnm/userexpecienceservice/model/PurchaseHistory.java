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
@Table(name = "purchase_history")
public class PurchaseHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "variation_id", nullable = false)
    private ItemVariation variation;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
