package com.TRjournal.TrJournal.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PerformanceMetrics {
    private double winPercentage;
    private int usageCount;
    private double profitLoss;
    private double averageTradeDuration;
    private Map<String, Double> instrumentPerformance;
    private Map<Integer, Double> timeOfDayPerformance;
    private double riskRewardRatio;
    private Map<String, Double> planAdherencePerformance;
    private List<Map<String, Object>> tradePerformance;
    private Map<String, Map<String, Double>> indicatorPerformance;
    private Map<String, Map<String, Double>> playbookPerformance;
    private Map<String, Map<String, Double>> setupPerformance;
    private double dailyPlanAdherence;
    private Map<String, Double> adherentTradesPerformance;
    private Map<String, Double> nonAdherentTradesPerformance;
    private double weeklyPlanAdherence;

    // Default constructor
    public PerformanceMetrics() {
        this.instrumentPerformance = new HashMap<>();
        this.timeOfDayPerformance = new HashMap<>();
    }

    // Constructor with all fields
    public PerformanceMetrics(double winPercentage, int usageCount, double profitLoss, double averageTradeDuration,
                              Map<String, Double> instrumentPerformance, Map<Integer, Double> timeOfDayPerformance) {
        this.winPercentage = winPercentage;
        this.usageCount = usageCount;
        this.profitLoss = profitLoss;
        this.averageTradeDuration = averageTradeDuration;
        this.instrumentPerformance = instrumentPerformance != null ? instrumentPerformance : new HashMap<>();
        this.timeOfDayPerformance = timeOfDayPerformance != null ? timeOfDayPerformance : new HashMap<>();
    }

    // Getters and setters
    public double getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(double winPercentage) {
        this.winPercentage = winPercentage;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public double getAverageTradeDuration() {
        return averageTradeDuration;
    }

    public void setAverageTradeDuration(double averageTradeDuration) {
        this.averageTradeDuration = averageTradeDuration;
    }

    public Map<String, Double> getInstrumentPerformance() {
        return instrumentPerformance;
    }

    public void setInstrumentPerformance(Map<String, Double> instrumentPerformance) {
        this.instrumentPerformance = instrumentPerformance;
    }

    public Map<Integer, Double> getTimeOfDayPerformance() {
        return timeOfDayPerformance;
    }

    public void setTimeOfDayPerformance(Map<Integer, Double> timeOfDayPerformance) {
        this.timeOfDayPerformance = timeOfDayPerformance;
    }

    public void setRiskRewardRatio(double riskRewardRatio) {
        this.riskRewardRatio = riskRewardRatio;
    }

    public double getRiskRewardRatio() {
        return riskRewardRatio;
    }

    public void setPlanAdherencePerformance(Map<String, Double> planAdherencePerformance) {
        this.planAdherencePerformance = planAdherencePerformance;
    }

    public Map<String, Double> getPlanAdherencePerformance() {
        return planAdherencePerformance;
    }

    public void setTradePerformance(List<Map<String, Object>> tradePerformance) {
        this.tradePerformance = tradePerformance;
    }

    public List<Map<String, Object>> getTradePerformance() {
        return tradePerformance;
    }

    public void setIndicatorPerformance(Map<String, Map<String, Double>> indicatorPerformance) {
        this.indicatorPerformance = indicatorPerformance;
    }

    public Map<String, Map<String, Double>> getIndicatorPerformance() {
        return indicatorPerformance;
    }

    public void setPlaybookPerformance(Map<String, Map<String, Double>> playbookPerformance) {
        this.playbookPerformance = playbookPerformance;
    }

    public Map<String, Map<String, Double>> getPlaybookPerformance() {
        return playbookPerformance;
    }

    public void setSetupPerformance(Map<String, Map<String, Double>> setupPerformance) {
        this.setupPerformance = setupPerformance;
    }

    public Map<String, Map<String, Double>> getSetupPerformance() {
        return setupPerformance;
    }

    public void setDailyPlanAdherence(double dailyPlanAdherence) {
        this.dailyPlanAdherence = dailyPlanAdherence;
    }

    public double getDailyPlanAdherence() {
        return dailyPlanAdherence;
    }

    public void setAdherentTradesPerformance(Map<String, Double> adherentTradesPerformance) {
        this.adherentTradesPerformance = adherentTradesPerformance;
    }

    public void setNonAdherentTradesPerformance(Map<String, Double> nonAdherentTradesPerformance) {
        this.nonAdherentTradesPerformance = nonAdherentTradesPerformance;
    }

    public void setWeeklyPlanAdherence(double weeklyPlanAdherence) {
        this.weeklyPlanAdherence = weeklyPlanAdherence;
    }

    public double getWeeklyPlanAdherence() {
        return weeklyPlanAdherence;
    }

    // Utility methods
    public void addInstrumentPerformance(String instrument, Double performance) {
        this.instrumentPerformance.put(instrument, performance);
    }

    public void addTimeOfDayPerformance(Integer hour, Double performance) {
        this.timeOfDayPerformance.put(hour, performance);
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "PerformanceMetrics{" +
                "winPercentage=" + winPercentage +
                ", usageCount=" + usageCount +
                ", profitLoss=" + profitLoss +
                ", averageTradeDuration=" + averageTradeDuration +
                ", instrumentPerformance=" + instrumentPerformance +
                ", timeOfDayPerformance=" + timeOfDayPerformance +
                '}';
    }

    // equals and hashCode methods for comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerformanceMetrics that = (PerformanceMetrics) o;
        return Double.compare(that.winPercentage, winPercentage) == 0 &&
                usageCount == that.usageCount &&
                Double.compare(that.profitLoss, profitLoss) == 0 &&
                Double.compare(that.averageTradeDuration, averageTradeDuration) == 0 &&
                Objects.equals(instrumentPerformance, that.instrumentPerformance) &&
                Objects.equals(timeOfDayPerformance, that.timeOfDayPerformance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(winPercentage, usageCount, profitLoss, averageTradeDuration, instrumentPerformance, timeOfDayPerformance);
    }

    // Added getter methods for adherentTradesPerformance and nonAdherentTradesPerformance
    public Map<String, Double> getAdherentTradesPerformance() {
        return adherentTradesPerformance;
    }

    public Map<String, Double> getNonAdherentTradesPerformance() {
        return nonAdherentTradesPerformance;
    }
}
