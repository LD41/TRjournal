package com.TRjournal.TrJournal.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TRjournal.TrJournal.model.Indicator;
import com.TRjournal.TrJournal.model.Playbook;
import com.TRjournal.TrJournal.model.Setup;
import com.TRjournal.TrJournal.model.Trade;
import com.TRjournal.TrJournal.model.TradingAccount;

public interface TradeRepository extends JpaRepository<Trade, Long>, JpaSpecificationExecutor<Trade> {
    List<Trade> findAllByUserId(Long userId);
    List<Trade> findByInstrument(String instrument);
    List<Trade> findBySetup(String setup);
    List<Trade> findByPlanType(String planType);
    
    Optional<Trade> findByIdAndUserId(Long id, Long userId);

    // New methods
    @Query("SELECT t FROM Trade t JOIN t.indicators i WHERE i.id IN :indicatorIds")
    List<Trade> findByIndicatorIdsIn(@Param("indicatorIds") List<Long> indicatorIds);

    @Query("SELECT t FROM Trade t JOIN t.indicators i WHERE i.id = :indicatorId AND t.instrument.id = :instrumentId")
    List<Trade> findByIndicatorIdAndInstrumentId(@Param("indicatorId") Long indicatorId, @Param("instrumentId") Long instrumentId);

    List<Trade> findByIndicatorId(Long indicatorId);

    List<Trade> findByIndicatorsIdIn(List<Long> indicatorIds);

    List<Trade> findByTradeDateBetween(LocalDate startDate, LocalDate endDate);
    List<Trade> findBySetupAndTradeDateBetween(Setup setup, LocalDate startDate, LocalDate endDate);
    List<Trade> findByPlaybookAndTradeDateBetween(Playbook playbook, LocalDate startDate, LocalDate endDate);
    List<Trade> findByIndicatorsContainingAndTradeDateBetween(Indicator indicator, LocalDate startDate, LocalDate endDate);

    List<Trade> findByTradingAccountAndTradeDateBetween(TradingAccount account, LocalDate startDate, LocalDate endDate);

    List<Trade> findByTradeDate(LocalDate tradeDate);

    List<Trade> findByTradeDateBetweenAndSetup(LocalDate startDate, LocalDate endDate, String setup);

    List<Trade> findByTradeDateBetweenAndPlayBook(LocalDate startDate, LocalDate endDate, String playBook);

    List<Trade> findByTradeDateBetweenAndIndicator(LocalDate startDate, LocalDate endDate, String indicator);
}
