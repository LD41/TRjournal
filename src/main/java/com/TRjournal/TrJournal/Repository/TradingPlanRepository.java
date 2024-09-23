package com.TRjournal.TrJournal.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.TRjournal.TrJournal.model.TradingPlan;

@Repository
public interface TradingPlanRepository extends JpaRepository<TradingPlan, Long> {

    @Query("SELECT tp FROM TradingPlan tp WHERE tp.userId = :userId " +
           "AND (:planType IS NULL OR tp.planType = :planType) " +
           "AND (:asset IS NULL OR tp.asset = :asset) " +
           "AND (:date IS NULL OR tp.date = :date)")
    List<TradingPlan> findByUserIdAndFilters(@Param("userId") Long userId, 
                                               @Param("planType") String planType, 
                                               @Param("asset") String asset, 
                                               @Param("date") LocalDate date);

    @Query("SELECT tp FROM TradingPlan tp WHERE tp.userId = :userId " +
           "AND (:planType IS NULL OR tp.planType = :planType) " +
           "AND (:asset IS NULL OR tp.asset = :asset) " +
           "AND tp.date BETWEEN :startDate AND :endDate")
    List<TradingPlan> findPlanHistory(@Param("userId") Long userId, 
                                      @Param("planType") String planType, 
                                      @Param("asset") String asset, 
                                      @Param("startDate") LocalDate startDate, 
                                      @Param("endDate") LocalDate endDate);
}
