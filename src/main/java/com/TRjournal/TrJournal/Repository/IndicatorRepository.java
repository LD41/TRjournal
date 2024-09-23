package com.TRjournal.TrJournal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TRjournal.TrJournal.model.Indicator;

public interface IndicatorRepository extends JpaRepository<Indicator, Long> {
    List<Indicator> findByName(String name);
    List<Indicator> findByNameContainingIgnoreCase(String name);
    
    List<Indicator> findByCategory(String category);
    List<Indicator> findByIsActive(boolean isActive);
    List<Indicator> findByCategoryAndIsActive(String category, boolean isActive);
    long countByCategory(String category);
}
