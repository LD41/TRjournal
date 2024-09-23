package com.TRjournal.TrJournal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.TRjournal.TrJournal.model.Trade;
import com.TRjournal.TrJournal.service.TradeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/trades")
public class TradeController {
    @Autowired
    private TradeService tradeService;

    @PostMapping
    public ResponseEntity<?> logTrade(@Valid @RequestPart("trade") Trade trade, 
                                  @RequestPart("photos") List<MultipartFile> photos) {
        try {
            Trade savedTrade = tradeService.logTrade(trade, photos);
            return ResponseEntity.ok(savedTrade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trade> getTrade(@PathVariable Long id) {
        Trade trade = tradeService.getTrade(id);
        return ResponseEntity.ok(trade);
    }

    @GetMapping
    public ResponseEntity<List<Trade>> getAllTrades() {
        List<Trade> trades = tradeService.getAllTradesForUser();
        return ResponseEntity.ok(trades);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trade> updateTrade(@PathVariable Long id, @RequestBody Trade trade) {
        Trade updatedTrade = tradeService.updateTrade(id, trade);
        return ResponseEntity.ok(updatedTrade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long id) {
        tradeService.deleteTrade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/instrument/{instrument}")
    public ResponseEntity<List<Trade>> getTradesByInstrument(@PathVariable String instrument) {
        List<Trade> trades = tradeService.getTradesByInstrument(instrument);
        return ResponseEntity.ok(trades);
    }

    @GetMapping("/setup/{setup}")
    public ResponseEntity<List<Trade>> getTradesBySetup(@PathVariable String setup) {
        List<Trade> trades = tradeService.getTradesBySetup(setup);
        return ResponseEntity.ok(trades);
    }

    // New endpoints
    @PostMapping("/{tradeId}/indicators/{indicatorId}")
    public ResponseEntity<Trade> addIndicatorToTrade(@PathVariable Long tradeId, @PathVariable Long indicatorId) {
        try {
            return ResponseEntity.ok(tradeService.addIndicatorToTrade(tradeId, indicatorId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{tradeId}/indicators/{indicatorId}")
    public ResponseEntity<Trade> removeIndicatorFromTrade(@PathVariable Long tradeId, @PathVariable Long indicatorId) {
        try {
            return ResponseEntity.ok(tradeService.removeIndicatorFromTrade(tradeId, indicatorId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/by-indicators")
    public ResponseEntity<List<Trade>> getTradesByIndicators(@RequestBody @Valid List<Long> indicatorIds) {
        return ResponseEntity.ok(tradeService.getTradesByIndicators(indicatorIds));
    }

    @GetMapping("/stats/by-indicator/{indicatorId}")
    public ResponseEntity<Map<String, Object>> getTradeStatsByIndicator(@PathVariable Long indicatorId) {
        return ResponseEntity.ok(tradeService.getTradeStatsByIndicator(indicatorId));
    }

    @GetMapping("/stats/by-time-of-day/{indicatorId}")
    public ResponseEntity<List<Map<String, Object>>> getTradeStatsByTimeOfDay(@PathVariable Long indicatorId) {
        return ResponseEntity.ok(tradeService.getTradeStatsByTimeOfDay(indicatorId));
    }
}
