package com.TRjournal.TrJournal.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TRjournal.TrJournal.Repository.TradingPlanRepository;
import com.TRjournal.TrJournal.model.PlanEvaluation;
import com.TRjournal.TrJournal.model.TradingPlan;

@Service
public class TradingPlanService {

    @Autowired
    private TradingPlanRepository tradingPlanRepository;
    // Creates a new trading plan
    public TradingPlan createPlan(TradingPlan tradingPlan) {
        return tradingPlanRepository.save(tradingPlan);
    }

    // Updates an existing trading plan
    public TradingPlan updatePlan(Long planId, TradingPlan updatedPlan) {
        TradingPlan existingPlan = tradingPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan not found"));
        
        // Update fields
        existingPlan.setPlanType(updatedPlan.getPlanType());
        existingPlan.setDate(updatedPlan.getDate());
        existingPlan.setAsset(updatedPlan.getAsset());
        existingPlan.setPlanDetails(updatedPlan.getPlanDetails());
        
        return tradingPlanRepository.save(existingPlan);
    }

    // Retrieves a specific trading plan by its ID
    public TradingPlan getPlan(Long planId) {
        return tradingPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    // Deletes a trading plan by its ID
    public void deletePlan(Long planId) {
        tradingPlanRepository.deleteById(planId);
    }

    // Evaluates a trading plan and updates its evaluation details
    public TradingPlan evaluatePlan(Long planId, PlanEvaluation evaluation) {
        TradingPlan plan = tradingPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan not found"));
        
        plan.setEvaluationStatus("EVALUATED");
        plan.setEvaluationNotes(evaluation.getNotes());
        plan.setEvaluationDate(LocalDate.now());
        plan.setSuccessRating(evaluation.getSuccessRating());
        
        return tradingPlanRepository.save(plan);
    }

    // Retrieves a list of trading plans for a user based on filters
    public List<TradingPlan> getUserPlans(Long userId, String planType, String asset, LocalDate date) {
        return tradingPlanRepository.findByUserIdAndFilters(userId, planType, asset, date);
    }

    // Retrieves the history of trading plans for a user within a date range
    public List<TradingPlan> getPlanHistory(Long userId, String planType, String asset, LocalDate startDate, LocalDate endDate) {
        return tradingPlanRepository.findPlanHistory(userId, planType, asset, startDate, endDate);
    }
}
