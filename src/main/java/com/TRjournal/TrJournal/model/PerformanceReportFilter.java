package com.TRjournal.TrJournal.model;

import java.time.LocalDate;
import java.util.List;

public class PerformanceReportFilter {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> setupIds;
    private List<Long> playbookIds;
    private List<Long> indicatorIds;
    private List<String> instruments;
    private List<Long> accountIds;
    private Boolean followedDailyPlan;
    private Boolean followedWeeklyPlan;

    // Getters
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public List<Long> getSetupIds() { return setupIds; }
    public List<Long> getPlaybookIds() { return playbookIds; }
    public List<Long> getIndicatorIds() { return indicatorIds; }
    public List<String> getInstruments() { return instruments; }
    public List<Long> getAccountIds() { return accountIds; }
    public Boolean getFollowedDailyPlan() { return followedDailyPlan; }
    public Boolean getFollowedWeeklyPlan() { return followedWeeklyPlan; }

    // Setters
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public void setSetupIds(List<Long> setupIds) { this.setupIds = setupIds; }
    public void setPlaybookIds(List<Long> playbookIds) { this.playbookIds = playbookIds; }
    public void setIndicatorIds(List<Long> indicatorIds) { this.indicatorIds = indicatorIds; }
    public void setInstruments(List<String> instruments) { this.instruments = instruments; }
    public void setAccountIds(List<Long> accountIds) { this.accountIds = accountIds; }
    public void setFollowedDailyPlan(Boolean followedDailyPlan) { this.followedDailyPlan = followedDailyPlan; }
    public void setFollowedWeeklyPlan(Boolean followedWeeklyPlan) { this.followedWeeklyPlan = followedWeeklyPlan; }
}
