package com.TRjournal.TrJournal.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Remove this line:
    // private String instrument;
    private BigDecimal entryPrice;
    private BigDecimal exitPrice;
    private Integer positionSize;
    private BigDecimal manualProfitLoss;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Duration duration;
    @Enumerated(EnumType.STRING)
    private TradePlanType tradePlanType;
    private String setup;
    @ElementCollection
    private List<String> accountsUsed;
    @Lob
    private String tradeNotes;
    @ElementCollection
    private List<String> photoUrls;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    @PreUpdate
    public void calculateDuration() {
        if (entryTime != null && exitTime != null) {
            this.duration = Duration.between(entryTime, exitTime);
        }
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<String> getPhotoUrls() {
        return this.photoUrls;
    }

    // Add this getter method
    public Duration getDuration() {
        return duration;
    }

    @ManyToMany
    @JoinTable(
        name = "trade_indicator",
        joinColumns = @JoinColumn(name = "trade_id"),
        inverseJoinColumns = @JoinColumn(name = "indicator_id")
    )
    private List<Indicator> indicators = new ArrayList<>();

    // Add getter and setter for indicators
    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    // Additional helper methods for managing indicators
    public void addIndicator(Indicator indicator) {
        if (!this.indicators.contains(indicator)) {
            this.indicators.add(indicator);
            indicator.addTrade(this);
        }
    }

    public void removeIndicator(Indicator indicator) {
        if (this.indicators.remove(indicator)) {
            indicator.removeTrade(this);
        }
    }

    // New fields
    private boolean isWin;
    private double profit;
    private String timeframe;

    @ManyToOne
    private Instrument instrument;

    // New methods
    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public String getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(String timeframe) {
        this.timeframe = timeframe;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    private BigDecimal risk;
    private BigDecimal reward;
    private boolean followedDailyPlan;
    private boolean followedWeeklyPlan;

    @ManyToOne
    @JoinColumn(name = "playbook_id")
    private Playbook playbook;

    public BigDecimal getRisk() {
        return risk;
    }

    public void setRisk(BigDecimal risk) {
        this.risk = risk;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }

    public boolean isFollowedDailyPlan() {
        return followedDailyPlan;
    }

    public void setFollowedDailyPlan(boolean followedDailyPlan) {
        this.followedDailyPlan = followedDailyPlan;
    }

    public boolean isFollowedWeeklyPlan() {
        return followedWeeklyPlan;
    }

    public void setFollowedWeeklyPlan(boolean followedWeeklyPlan) {
        this.followedWeeklyPlan = followedWeeklyPlan;
    }

    public Playbook getPlaybook() {
        return playbook;
    }

    public void setPlaybook(Playbook playbook) {
        this.playbook = playbook;
    }

    public double getRiskRewardRatio() {
        return (risk != null && reward != null && risk.compareTo(BigDecimal.ZERO) != 0) 
            ? reward.divide(risk, 2, RoundingMode.HALF_UP).doubleValue() 
            : 0.0;
    }

    public boolean isWinning() {
        return this.getProfitLoss().compareTo(BigDecimal.ZERO) > 0;
    }

    public BigDecimal getProfitLoss() {
        if (this.exitPrice == null || this.entryPrice == null) {
            return BigDecimal.ZERO; // Handle null cases
        }
        return this.exitPrice.subtract(this.entryPrice);
    }

    @ManyToOne
    private TradingAccount tradingAccount;

    public TradingAccount getTradingAccount() {
        return tradingAccount;
    }

    public void setTradingAccount(TradingAccount tradingAccount) {
        this.tradingAccount = tradingAccount;
    }

    private LocalDateTime tradeTime;

    public LocalDateTime getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(LocalDateTime tradeTime) {
        this.tradeTime = tradeTime;
    }
}

enum TradePlanType {
    DAILY, WEEKLY
}
