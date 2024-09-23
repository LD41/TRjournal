package com.TRjournal.TrJournal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TRjournal.TrJournal.model.Indicator;
import com.TRjournal.TrJournal.service.IndicatorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/indicators")
public class IndicatorController {
    @Autowired
    private IndicatorService indicatorService;

    // Retrieves a list of all indicators
    @GetMapping
    public List<Indicator> getAllIndicators() {
        return indicatorService.getAllIndicators();
    }

    // Retrieves a specific indicator by its ID
    @GetMapping("/{id}")
    public Indicator getIndicatorById(@PathVariable Long id) {
        return indicatorService.getIndicatorById(id).orElse(null);
    }

    // Creates a new indicator
    @PostMapping
    public Indicator createIndicator(@RequestBody Indicator indicator) {
        return indicatorService.createIndicator(indicator);
    }

    // Updates an existing indicator by its ID
    @PutMapping("/{id}")
    public Indicator updateIndicator(@PathVariable Long id, @RequestBody Indicator indicator) {
        return indicatorService.updateIndicator(id, indicator);
    }

    // Deletes an indicator by its ID
    @DeleteMapping("/{id}")
    public void deleteIndicator(@PathVariable Long id) {
        indicatorService.deleteIndicator(id);
    }

    // Retrieves performance statistics for a specific indicator
    @GetMapping("/{id}/performance")
    public ResponseEntity<Map<String, Double>> getIndicatorPerformance(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(indicatorService.getIndicatorPerformanceStats(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Retrieves performance statistics for a specific indicator by instrument ID
    @GetMapping("/{indicatorId}/performance/{instrumentId}")
    public ResponseEntity<Map<String, Double>> getIndicatorPerformanceByInstrument(
            @PathVariable Long indicatorId, @PathVariable Long instrumentId) {
        return ResponseEntity.ok(indicatorService.getIndicatorPerformanceByInstrument(indicatorId, instrumentId));
    }

    // Retrieves performance statistics for a combination of indicators
    @PostMapping("/combination-performance")
    public ResponseEntity<List<Map<String, Object>>> getIndicatorCombinationPerformance(
            @RequestBody @Valid List<Long> indicatorIds) {
        return ResponseEntity.ok(indicatorService.getIndicatorCombinationPerformance(indicatorIds));
    }

    // Retrieves the usage frequency of indicators
    @GetMapping("/usage-frequency")
    public ResponseEntity<Map<String, Long>> getIndicatorUsageFrequency() {
        return ResponseEntity.ok(indicatorService.getIndicatorUsageFrequency());
    }

    // Retrieves performance statistics for a specific indicator by timeframe
    @GetMapping("/{id}/performance-by-timeframe")
    public ResponseEntity<List<Map<String, Object>>> getIndicatorPerformanceByTimeframe(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(indicatorService.getIndicatorPerformanceByTimeframe(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
