package com.TRjournal.TrJournal.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "trading_accounts")
public class TradingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String broker;

    @Column(nullable = false)
    private BigDecimal startingBalance;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private boolean active = true;

    // ... getters and setters
}
