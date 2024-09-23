package com.TRjournal.TrJournal.model;

import java.util.Map;
import java.util.HashMap;

public class CalendarView {
    private int year;
    private int month;
    private Map<Integer, Map<String, Object>> dailyPerformance;
    private Map<String, Double> setupPerformance;
    private Map<String, Double> playBookPerformance;
    private Map<String, Double> indicatorPerformance;

    // Default constructor
    public CalendarView() {
        this.dailyPerformance = new HashMap<>();
        this.setupPerformance = new HashMap<>();
        this.playBookPerformance = new HashMap<>();
        this.indicatorPerformance = new HashMap<>();
    }

    // Parameterized constructor
    public CalendarView(int year, int month) {
        this();
        this.year = year;
        this.month = month;
    }

    // Existing getters and setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Map<Integer, Map<String, Object>> getDailyPerformance() {
        return dailyPerformance;
    }

    public void setDailyPerformance(Map<Integer, Map<String, Object>> dailyPerformance) {
        this.dailyPerformance = dailyPerformance;
    }

    // New getters and setters
    public Map<String, Double> getSetupPerformance() {
        return setupPerformance;
    }

    public void setSetupPerformance(Map<String, Double> setupPerformance) {
        this.setupPerformance = setupPerformance;
    }

    public Map<String, Double> getPlayBookPerformance() {
        return playBookPerformance;
    }

    public void setPlayBookPerformance(Map<String, Double> playBookPerformance) {
        this.playBookPerformance = playBookPerformance;
    }

    public Map<String, Double> getIndicatorPerformance() {
        return indicatorPerformance;
    }

    public void setIndicatorPerformance(Map<String, Double> indicatorPerformance) {
        this.indicatorPerformance = indicatorPerformance;
    }

    // Helper methods
    public void addDailyPerformance(int day, Map<String, Object> performance) {
        this.dailyPerformance.put(day, performance);
    }

    public void addSetupPerformance(String setup, Double performance) {
        this.setupPerformance.put(setup, performance);
    }

    public void addPlayBookPerformance(String playBook, Double performance) {
        this.playBookPerformance.put(playBook, performance);
    }

    public void addIndicatorPerformance(String indicator, Double performance) {
        this.indicatorPerformance.put(indicator, performance);
    }

    @Override
    public String toString() {
        return "CalendarView{" +
                "year=" + year +
                ", month=" + month +
                ", dailyPerformance=" + dailyPerformance +
                ", setupPerformance=" + setupPerformance +
                ", playBookPerformance=" + playBookPerformance +
                ", indicatorPerformance=" + indicatorPerformance +
                '}';
    }
}
