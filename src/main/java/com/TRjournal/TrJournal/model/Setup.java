package com.TRjournal.TrJournal.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Setup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String entryPoint;
    private String exitPoint;

    @ManyToOne
    private Instrument instrument;

    @ManyToMany
    @JoinTable(
        name = "setup_indicator",
        joinColumns = @JoinColumn(name = "setup_id"),
        inverseJoinColumns = @JoinColumn(name = "indicator_id")
    )
    private List<Indicator> indicators = new ArrayList<>();

    @OneToMany(mappedBy = "setup")
    private List<Trade> trades = new ArrayList<>();

    private int totalUsage;
    private int winCount;
    private double totalProfit;

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

    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getExitPoint() {
        return exitPoint;
    }

    public void setExitPoint(String exitPoint) {
        this.exitPoint = exitPoint;
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    public void addIndicator(Indicator indicator) {
        if (!this.indicators.contains(indicator)) {
            this.indicators.add(indicator);
            indicator.getSetups().add(this);
        }
    }

    public void removeIndicator(Indicator indicator) {
        if (this.indicators.remove(indicator)) {
            indicator.getSetups().remove(this);
        }
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
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
}
