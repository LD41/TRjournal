package com.TRjournal.TrJournal.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "plan_evaluations")
public class PlanEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trading_plan_id", nullable = false)
    private TradingPlan tradingPlan;

    @Column(nullable = false)
    private LocalDateTime evaluationDate;

    @Column(nullable = false)
    private String outcome; // e.g., "SUCCESS", "FAILURE", "PARTIAL_SUCCESS"

    @Column(length = 1000)
    private String comments;

    @Column
    private Double profitLoss;

    @Column
    private String lessonsLearned;

    @Column
    private String notes;

    @Column
    private Integer successRating;

    // Add these methods if they don't exist
    public String getNotes() {
        return notes;
    }

    // Keep the existing Integer getter
    public Integer getSuccessRating() {
        return successRating;
    }

    // Constructors
    public PlanEvaluation() {}

    public PlanEvaluation(TradingPlan tradingPlan, String outcome, String comments, Double profitLoss, String lessonsLearned) {
        this.tradingPlan = tradingPlan;
        this.evaluationDate = LocalDateTime.now();
        this.outcome = outcome;
        this.comments = comments;
        this.profitLoss = profitLoss;
        this.lessonsLearned = lessonsLearned;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TradingPlan getTradingPlan() {
        return tradingPlan;
    }

    public void setTradingPlan(TradingPlan tradingPlan) {
        this.tradingPlan = tradingPlan;
    }

    public LocalDateTime getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(Double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public String getLessonsLearned() {
        return lessonsLearned;
    }

    public void setLessonsLearned(String lessonsLearned) {
        this.lessonsLearned = lessonsLearned;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

  
    public void setSuccessRating(Integer successRating) {
        this.successRating = successRating;
    }
}
