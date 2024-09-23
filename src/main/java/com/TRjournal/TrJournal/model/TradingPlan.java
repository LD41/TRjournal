package com.TRjournal.TrJournal.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trading_plans")
public class TradingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String planType; // "DAILY" or "WEEKLY"

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String asset;

    @Column(columnDefinition = "TEXT")
    private String planDetails;

    @Column(nullable = false)
    private String evaluationStatus;

    private String evaluationNotes;

    private Integer successRating;

    private LocalDate evaluationDate;

    // Constructors
    public TradingPlan() {}

    public TradingPlan(Long userId, String planType, LocalDate date, String asset, String planDetails) {
        this.userId = userId;
        this.planType = planType;
        this.date = date;
        this.asset = asset;
        this.planDetails = planDetails;
        this.evaluationStatus = "NOT_EVALUATED";
    }

    // Getters and Setters
    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(String planDetails) {
        this.planDetails = planDetails;
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public void setEvaluationNotes(String evaluationNotes) {
        this.evaluationNotes = evaluationNotes;
    }

    public String getEvaluationNotes() {
        return this.evaluationNotes;
    }

    public void setSuccessRating(Integer successRating) {
        this.successRating = successRating;
    }

    public Integer getSuccessRating() {
        return successRating;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    // Additional methods
    public boolean isDaily() {
        return "DAILY".equalsIgnoreCase(this.planType);
    }

    public boolean isWeekly() {
        return "WEEKLY".equalsIgnoreCase(this.planType);
    }

    public boolean isEvaluated() {
        return "EVALUATED".equalsIgnoreCase(this.evaluationStatus);
    }

    public void markAsEvaluated() {
        this.evaluationStatus = "EVALUATED";
    }

    // toString method for easy logging and debugging
    @Override
    public String toString() {
        return "TradingPlan{" +
                "planId=" + planId +
                ", userId=" + userId +
                ", planType='" + planType + '\'' +
                ", date=" + date +
                ", asset='" + asset + '\'' +
                ", planDetails='" + planDetails + '\'' +
                ", evaluationStatus='" + evaluationStatus + '\'' +
                '}';
    }

    // equals and hashCode methods for proper object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradingPlan that = (TradingPlan) o;
        return Objects.equals(planId, that.planId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(planType, that.planType) &&
                Objects.equals(date, that.date) &&
                Objects.equals(asset, that.asset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, userId, planType, date, asset);
    }
}
