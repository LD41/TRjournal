package com.TRjournal.TrJournal.service;

import com.TRjournal.TrJournal.model.CalendarView;
import com.TRjournal.TrJournal.model.Trade;
import com.TRjournal.TrJournal.Repository.CalendarViewRepository;
import com.TRjournal.TrJournal.Repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

@Service
public class CalendarViewService {
    @Autowired
    private CalendarViewRepository calendarViewRepository;

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Retrieves a list of CalendarView objects for a specified date range.
     */
    public List<CalendarView> getCalendarViewForDateRange(LocalDate startDate, LocalDate endDate) {
        return calendarViewRepository.findByDateBetween(startDate, endDate);
    }

    /**
     * Calculates daily performance metrics for a specific date, including total profit/loss,
     * win rate, and total number of trades.
     */
    public Map<String, Object> getDailyPerformance(LocalDate date) {
        List<Trade> trades = tradeRepository.findByTradeDate(date);
        
        double totalProfitLoss = trades.stream()
            .mapToDouble(trade -> trade.getProfitLoss().doubleValue())
            .sum();
        
        long winningTrades = trades.stream()
            .filter(trade -> trade.getProfitLoss().compareTo(BigDecimal.ZERO) > 0)
            .count();
        
        double winRate = trades.isEmpty() ? 0 : (double) winningTrades / trades.size() * 100;
        
        Map<String, Object> dailyPerformance = new HashMap<>();
        dailyPerformance.put("date", date);
        dailyPerformance.put("totalProfitLoss", totalProfitLoss);
        dailyPerformance.put("winRate", winRate);
        dailyPerformance.put("totalTrades", trades.size());
        
        return dailyPerformance;
    }

    /**
     * Computes performance metrics for trades within a specified date range and setup.
     */
    public Map<String, Double> getSetupPerformance(LocalDate startDate, LocalDate endDate, String setup) {
        List<Trade> trades = tradeRepository.findByTradeDateBetweenAndSetup(startDate, endDate, setup);
        
        double totalProfitLoss = trades.stream()
            .mapToDouble(trade -> trade.getProfitLoss().doubleValue())
            .sum();
        
        long winningTrades = trades.stream()
            .filter(trade -> trade.getProfitLoss().compareTo(BigDecimal.ZERO) > 0)
            .count();
        
        double winRate = trades.isEmpty() ? 0 : (double) winningTrades / trades.size() * 100;
        
        Map<String, Double> performance = new HashMap<>();
        performance.put("totalProfitLoss", totalProfitLoss);
        performance.put("winRate", winRate);
        performance.put("totalTrades", (double) trades.size());
        
        return performance;
    }

    /**
     * Calculates performance metrics for trades within a specified date range and playbook.
     */
    public Map<String, Double> getPlayBookPerformance(LocalDate startDate, LocalDate endDate, String playBook) {
        List<Trade> trades = tradeRepository.findByTradeDateBetweenAndPlayBook(startDate, endDate, playBook);
        
        double totalProfitLoss = trades.stream()
            .mapToDouble(trade -> trade.getProfitLoss().doubleValue())
            .sum();
        
        long winningTrades = trades.stream()
            .filter(trade -> trade.getProfitLoss().compareTo(BigDecimal.ZERO) > 0)
            .count();
        
        double winRate = trades.isEmpty() ? 0 : (double) winningTrades / trades.size() * 100;
        
        Map<String, Double> performance = new HashMap<>();
        performance.put("totalProfitLoss", totalProfitLoss);
        performance.put("winRate", winRate);
        performance.put("totalTrades", (double) trades.size());
        
        return performance;
    }

    /**
     * Computes performance metrics for trades within a specified date range and indicator.
     */
    public Map<String, Double> getIndicatorPerformance(LocalDate startDate, LocalDate endDate, String indicator) {
        List<Trade> trades = tradeRepository.findByTradeDateBetweenAndIndicator(startDate, endDate, indicator);
        
        double totalProfitLoss = trades.stream()
            .mapToDouble(trade -> trade.getProfitLoss().doubleValue())
            .sum();
        
        long winningTrades = trades.stream()
            .filter(trade -> trade.getProfitLoss().compareTo(BigDecimal.ZERO) > 0)
            .count();
        
        double winRate = trades.isEmpty() ? 0 : (double) winningTrades / trades.size() * 100;
        
        Map<String, Double> performance = new HashMap<>();
        performance.put("totalProfitLoss", totalProfitLoss);
        performance.put("winRate", winRate);
        performance.put("totalTrades", (double) trades.size());
        
        return performance;
    }

    /**
     * Generates hourly performance metrics for trades on a specific date.
     */
    public Map<Integer, Double> getHourlyPerformance(LocalDate date) {
        List<Trade> trades = tradeRepository.findByTradeDate(date);
        
        Map<Integer, Double> hourlyPerformance = new HashMap<>();
        
        for (Trade trade : trades) {
            int hour = trade.getTradeTime().getHour();
            double profitLoss = trade.getProfitLoss().doubleValue();
            
            hourlyPerformance.merge(hour, profitLoss, Double::sum);
        }
        
        return hourlyPerformance;
    }
}
