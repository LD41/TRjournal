package com.TRjournal.TrJournal.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TRjournal.TrJournal.model.PlanEvaluation;
import com.TRjournal.TrJournal.model.TradingPlan;
import com.TRjournal.TrJournal.service.TradingPlanService;

@RestController
@RequestMapping("/api/trading-plans")
public class TradingPlanController {

    @Autowired
    private TradingPlanService tradingPlanService;

    @PostMapping("/create")
    public ResponseEntity<TradingPlan> createPlan(@RequestBody TradingPlan tradingPlan) {
        return ResponseEntity.ok(tradingPlanService.createPlan(tradingPlan));
    }

    @PutMapping("/update/{planId}")
    public ResponseEntity<TradingPlan> updatePlan(@PathVariable Long planId, @RequestBody TradingPlan tradingPlan) {
        return ResponseEntity.ok(tradingPlanService.updatePlan(planId, tradingPlan));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<TradingPlan> getPlan(@PathVariable Long planId) {
        return ResponseEntity.ok(tradingPlanService.getPlan(planId));
    }

    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planId) {
        tradingPlanService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/evaluate/{planId}")
    public ResponseEntity<TradingPlan> evaluatePlan(@PathVariable Long planId, @RequestBody PlanEvaluation evaluation) {
        return ResponseEntity.ok(tradingPlanService.evaluatePlan(planId, evaluation));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TradingPlan>> getUserPlans(@PathVariable Long userId,
                                                          @RequestParam(required = false) String planType,
                                                          @RequestParam(required = false) String asset,
                                                          @RequestParam(required = false) LocalDate date) {
        return ResponseEntity.ok(tradingPlanService.getUserPlans(userId, planType, asset, date));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<TradingPlan>> getPlanHistory(@PathVariable Long userId,
                                                            @RequestParam(required = false) String planType,
                                                            @RequestParam(required = false) String asset,
                                                            @RequestParam(required = false) LocalDate startDate,
                                                            @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(tradingPlanService.getPlanHistory(userId, planType, asset, startDate, endDate));
    }
}
