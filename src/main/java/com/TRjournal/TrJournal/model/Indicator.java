package com.TRjournal.TrJournal.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Indicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String timeframe;
    private String parameters;

    @ManyToMany(mappedBy = "indicators")
    private List<Trade> trades = new ArrayList<>();

    @ManyToMany(mappedBy = "indicators")
    private List<Setup> setups = new ArrayList<>();

    @ManyToMany(mappedBy = "indicators")
    private List<Playbook> playbooks = new ArrayList<>();

    private int totalUsage;
    private int winCount;
    private double totalProfit;

    private double totalLoss;
    private double largestWin;
    private double largestLoss;
    private long totalTradeDuration;

    // Constructors
    public Indicator() {}

    public Indicator(String name, String description, String timeframe, String parameters) {
        this.name = name;
        this.description = description;
        this.timeframe = timeframe;
        this.parameters = parameters;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(String timeframe) {
        this.timeframe = timeframe;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    public List<Setup> getSetups() {
        return setups;
    }

    public void setSetups(List<Setup> setups) {
        this.setups = setups;
    }

    public List<Playbook> getPlaybooks() {
        return playbooks;
    }

    public void setPlaybooks(List<Playbook> playbooks) {
        this.playbooks = playbooks;
    }

    public int getTotalUsage() {
        return totalUsage;
    }

    public void setTotalUsage(int totalUsage) {
        this.totalUsage = totalUsage;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(double totalLoss) {
        this.totalLoss = totalLoss;
    }

    public void addLoss(double loss) {
        this.totalLoss += loss;
    }

    public double getRiskAdjustedReturn() {
        return (totalProfit - totalLoss) / (Math.abs(totalLoss) > 0 ? Math.abs(totalLoss) : 1);
    }

    public double getLargestWin() {
        return largestWin;
    }

    public void setLargestWin(double largestWin) {
        this.largestWin = largestWin;
    }

    public double getLargestLoss() {
        return largestLoss;
    }

    public void setLargestLoss(double largestLoss) {
        this.largestLoss = largestLoss;
    }

    public void updateLargestWinLoss(double profitLoss) {
        if (profitLoss > largestWin) {
            largestWin = profitLoss;
        } else if (profitLoss < largestLoss) {
            largestLoss = profitLoss;
        }
    }

    public double getProfitFactor() {
        return Math.abs(totalLoss) > 0 ? totalProfit / Math.abs(totalLoss) : Double.POSITIVE_INFINITY;
    }

    public long getTotalTradeDuration() {
        return totalTradeDuration;
    }

    public void setTotalTradeDuration(long totalTradeDuration) {
        this.totalTradeDuration = totalTradeDuration;
    }

    public void addTradeDuration(long duration) {
        this.totalTradeDuration += duration;
    }

    public double getAverageTradeDuration() {
        return totalUsage > 0 ? (double) totalTradeDuration / totalUsage : 0;
    }

    // Additional methods for indicator management
    public void incrementUsage() {
        this.totalUsage++;
    }

    public void addWin() {
        this.winCount++;
    }

    public void addProfit(double profit) {
        this.totalProfit += profit;
    }

    public double getWinRate() {
        return totalUsage > 0 ? (double) winCount / totalUsage : 0;
    }

    public double getAverageProfit() {
        return totalUsage > 0 ? totalProfit / totalUsage : 0;
    }

    public void addTrade(Trade trade) {
        if (!this.trades.contains(trade)) {
            this.trades.add(trade);
            incrementUsage();
        }
    }

    public void removeTrade(Trade trade) {
        this.trades.remove(trade);
    }

    public void addSetup(Setup setup) {
        if (!this.setups.contains(setup)) {
            this.setups.add(setup);
        }
    }

    public void removeSetup(Setup setup) {
        this.setups.remove(setup);
    }

    public void addPlaybook(Playbook playbook) {
        if (!this.playbooks.contains(playbook)) {
            this.playbooks.add(playbook);
        }
    }

    public void removePlaybook(Playbook playbook) {
        this.playbooks.remove(playbook);
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", timeframe='" + timeframe + '\'' +
                ", parameters='" + parameters + '\'' +
                ", totalUsage=" + totalUsage +
                ", winCount=" + winCount +
                ", totalProfit=" + totalProfit +
                '}';
    }
}
