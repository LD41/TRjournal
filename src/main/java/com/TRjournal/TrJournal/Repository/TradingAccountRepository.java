package com.TRjournal.TrJournal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TRjournal.TrJournal.model.TradingAccount;

public interface TradingAccountRepository extends JpaRepository<TradingAccount, Long> {
    List<TradingAccount> findByUserId(Long userId);
}
