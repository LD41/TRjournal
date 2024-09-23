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
public class Playbook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private String entryPoint;
	private String exitPoint;

	@ManyToOne
	private Instrument instrument;

	private double winRate;
	private int totalTrades;
	private double profitLoss;
	private double avgTradeDuration;

	@ManyToMany
	@JoinTable(
		name = "playbook_indicator",
		joinColumns = @JoinColumn(name = "playbook_id"),
		inverseJoinColumns = @JoinColumn(name = "indicator_id")
	)
	private List<Indicator> indicators;

	@OneToMany(mappedBy = "playbook")
	private List<Trade> trades = new ArrayList<>();

	private double totalProfit;
	private double totalLoss;
	private double largestWin;
	private double largestLoss;
	private long totalTradeDuration;

	private Setup setup;

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

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public double getWinRate() {
		return winRate;
	}

	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}

	public int getTotalTrades() {
		return totalTrades;
	}

	public void setTotalTrades(int totalTrades) {
		this.totalTrades = totalTrades;
	}

	public double getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(double profitLoss) {
		this.profitLoss = profitLoss;
	}

	public double getAvgTradeDuration() {
		return avgTradeDuration;
	}

	public void setAvgTradeDuration(double avgTradeDuration) {
		this.avgTradeDuration = avgTradeDuration;
	}

	public List<Indicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<Indicator> indicators) {
		this.indicators = indicators;
	}

	public List<Trade> getTrades() {
		return trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
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

	public long getTotalTradeDuration() {
		return totalTradeDuration;
	}

	public void setTotalTradeDuration(long totalTradeDuration) {
		this.totalTradeDuration = totalTradeDuration;
	}

	public void addIndicator(Indicator indicator) {
		if (!this.indicators.contains(indicator)) {
			this.indicators.add(indicator);
			indicator.getPlaybooks().add(this);
		}
	}

	public void removeIndicator(Indicator indicator) {
		if (this.indicators.remove(indicator)) {
			indicator.getPlaybooks().remove(this);
		}
	}

	public void addTrade(Trade trade) {
		if (!this.trades.contains(trade)) {
			this.trades.add(trade);
			trade.setPlaybook(this);
			updatePerformanceMetrics(trade);
		}
	}

	public void removeTrade(Trade trade) {
		if (this.trades.remove(trade)) {
			trade.setPlaybook(null);
			// Consider updating performance metrics here if needed
		}
	}

	private void updatePerformanceMetrics(Trade trade) {
		this.totalTrades++;
		this.totalTradeDuration += trade.getDuration().toSeconds();
		this.avgTradeDuration = (double) this.totalTradeDuration / this.totalTrades;

		double tradeProfit = trade.getProfit();
		if (tradeProfit > 0) {
			this.totalProfit += tradeProfit;
			if (tradeProfit > this.largestWin) {
				this.largestWin = tradeProfit;
			}
		} else {
			this.totalLoss += Math.abs(tradeProfit);
			if (Math.abs(tradeProfit) > Math.abs(this.largestLoss)) {
				this.largestLoss = tradeProfit;
			}
		}

		this.profitLoss = this.totalProfit - this.totalLoss;
		this.winRate = (double) this.trades.stream().filter(Trade::isWin).count() / this.totalTrades;
	}

	public double getProfitFactor() {
		return Math.abs(totalLoss) > 0 ? totalProfit / Math.abs(totalLoss) : Double.POSITIVE_INFINITY;
	}

	public double getRiskAdjustedReturn() {
		return (totalProfit - totalLoss) / (Math.abs(totalLoss) > 0 ? Math.abs(totalLoss) : 1);
	}

	public double getAverageProfit() {
		return totalTrades > 0 ? profitLoss / totalTrades : 0;
	}

	public void setSetup(Setup setup) {
		this.setup = setup;
	}

	public Setup getSetup() {
		return this.setup;
	}
}
