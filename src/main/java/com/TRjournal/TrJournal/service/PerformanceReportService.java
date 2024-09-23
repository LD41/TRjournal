package com.TRjournal.TrJournal.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.TRjournal.TrJournal.Repository.EmotionalNoteRepository;
import com.TRjournal.TrJournal.Repository.IndicatorRepository;
import com.TRjournal.TrJournal.Repository.PlaybookRepository;
import com.TRjournal.TrJournal.Repository.SetupRepository;
import com.TRjournal.TrJournal.Repository.TradeRepository;
import com.TRjournal.TrJournal.Repository.TradingAccountRepository;
import com.TRjournal.TrJournal.model.CalendarView;
import com.TRjournal.TrJournal.model.EmotionalNote;
import com.TRjournal.TrJournal.model.Indicator;
import com.TRjournal.TrJournal.model.PerformanceMetrics;
import com.TRjournal.TrJournal.model.PerformanceReportFilter;
import com.TRjournal.TrJournal.model.Playbook;
import com.TRjournal.TrJournal.model.Setup;
import com.TRjournal.TrJournal.model.Trade;
import com.TRjournal.TrJournal.model.TradingAccount;

import jakarta.persistence.criteria.Predicate;

@org.springframework.stereotype.Service
public class PerformanceReportService {

    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private SetupRepository setupRepository;
    @Autowired
    private PlaybookRepository playbookRepository;
    @Autowired
    private IndicatorRepository indicatorRepository;
    @Autowired
    private TradingAccountRepository tradingAccountRepository;
    @Autowired
    private EmotionalNoteRepository emotionalNoteRepository;

    // Generates a comprehensive performance report for a given date range
    public PerformanceMetrics generateReport(LocalDate startDate, LocalDate endDate) {
        List<Trade> trades = tradeRepository.findByTradeDateBetween(startDate, endDate);
        
        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.setWinPercentage(calculateWinPercentage(trades));
        metrics.setProfitLoss(calculateProfitLoss(trades));
        metrics.setRiskRewardRatio(calculateRiskRewardRatio(trades));
        metrics.setAverageTradeDuration(calculateAverageTradeDuration(trades));
        metrics.setTimeOfDayPerformance(calculateHourlyTimeOfDayPerformance(trades));
        metrics.setSetupPerformance(getSetupPerformance(startDate, endDate));
        metrics.setPlaybookPerformance(getPlaybookPerformance(startDate, endDate));
        metrics.setIndicatorPerformance(getIndicatorPerformance(startDate, endDate));
        metrics.setTradePerformance(getTradePerformance(startDate, endDate));
        metrics.setPlanAdherencePerformance(getPlanAdherencePerformance(trades));
        metrics.setInstrumentPerformance(getInstrumentPerformance(startDate, endDate));
        
        return metrics;
    }

    // Calculates performance metrics for each setup within a given date range
    public Map<String, Map<String, Double>> getSetupPerformance(LocalDate startDate, LocalDate endDate) {
        return setupRepository.findAll().stream()
            .collect(Collectors.toMap(
                Setup::getName,
                setup -> calculatePerformanceMetrics(tradeRepository.findBySetupAndTradeDateBetween(setup, startDate, endDate))
            ));
    }

    // Calculates performance metrics for each playbook within a given date range
    public Map<String, Map<String, Double>> getPlaybookPerformance(LocalDate startDate, LocalDate endDate) {
        return playbookRepository.findAll().stream()
            .collect(Collectors.toMap(
                Playbook::getName,
                playbook -> calculatePerformanceMetrics(tradeRepository.findByPlaybookAndTradeDateBetween(playbook, startDate, endDate))
            ));
    }

    // Calculates performance metrics for each indicator within a given date range
    public Map<String, Map<String, Double>> getIndicatorPerformance(LocalDate startDate, LocalDate endDate) {
        return indicatorRepository.findAll().stream()
            .collect(Collectors.toMap(
                Indicator::getName,
                indicator -> calculatePerformanceMetrics(tradeRepository.findByIndicatorsContainingAndTradeDateBetween(indicator, startDate, endDate))
            ));
    }

    // Calculates performance metrics for each instrument within a given date range
    public Map<String, Double> getInstrumentPerformance(LocalDate startDate, LocalDate endDate) {
        List<Trade> trades = tradeRepository.findByTradeDateBetween(startDate, endDate);
        return calculateInstrumentPerformance(trades);
    }

    // Calculates plan adherence performance metrics for a list of trades
    public Map<String, Double> getPlanAdherencePerformance(List<Trade> trades) {
        Map<String, Double> performance = new HashMap<>();
        performance.put("dailyPlanAdherence", calculatePlanAdherence(trades, Trade::isFollowedDailyPlan));
        performance.put("weeklyPlanAdherence", calculatePlanAdherence(trades, Trade::isFollowedWeeklyPlan));
        return performance;
    }

    // Generates a custom performance report based on a date range
    public PerformanceMetrics generateCustomReport(LocalDate startDate, LocalDate endDate) {
        PerformanceReportFilter filter = new PerformanceReportFilter();
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);
        return getFilteredReport(filter);
    }

    // Calculates various performance metrics for a list of trades
    private Map<String, Double> calculatePerformanceMetrics(List<Trade> trades) {
        Map<String, Double> metrics = new HashMap<>();
        metrics.put("winPercentage", calculateWinPercentage(trades));
        metrics.put("usageCount", (double) trades.size());
        metrics.put("profitLoss", calculateProfitLoss(trades));
        metrics.put("averageTradeDuration", calculateAverageTradeDuration(trades));
        metrics.put("riskRewardRatio", calculateRiskRewardRatio(trades));
        metrics.put("profitFactor", calculateProfitFactor(trades));
        metrics.put("largestWin", calculateLargestWin(trades));
        metrics.put("largestLoss", calculateLargestLoss(trades));
        return metrics;
    }

    // Calculates the profit factor for a list of trades
    private double calculateProfitFactor(List<Trade> trades) {
        double totalProfit = trades.stream().filter(t -> t.getProfit() > 0).mapToDouble(Trade::getProfit).sum();
        double totalLoss = Math.abs(trades.stream().filter(t -> t.getProfit() < 0).mapToDouble(Trade::getProfit).sum());
        return totalLoss != 0 ? totalProfit / totalLoss : Double.POSITIVE_INFINITY;
    }

    // Finds the largest winning trade from a list of trades
    private double calculateLargestWin(List<Trade> trades) {
        return trades.stream().mapToDouble(Trade::getProfit).max().orElse(0);
    }

    // Finds the largest losing trade from a list of trades
    private double calculateLargestLoss(List<Trade> trades) {
        return trades.stream().mapToDouble(Trade::getProfit).min().orElse(0);
    }

    // Calculates the performance of each instrument for a list of trades
    private Map<String, Double> calculateInstrumentPerformance(List<Trade> trades) {
        return trades.stream()
            .collect(Collectors.groupingBy(
                trade -> trade.getInstrument().getName(),
                Collectors.summingDouble(Trade::getProfit)
            ));
    }

    // Calculates the hourly performance for a list of trades
    private Map<Integer, Double> calculateHourlyTimeOfDayPerformance(List<Trade> trades) {
        return trades.stream()
            .collect(Collectors.groupingBy(
                trade -> trade.getEntryTime().getHour(),
                HashMap::new,
                Collectors.averagingDouble(trade -> trade.isWinning() ? 100.0 : 0.0)
            ));
    }

    // Retrieves performance data for individual trades within a date range
    public List<Map<String, Object>> getTradePerformance(LocalDate startDate, LocalDate endDate) {
        return tradeRepository.findByTradeDateBetween(startDate, endDate).stream()
            .map(this::mapTradeToPerformance)
            .collect(Collectors.toList());
    }

    // Generates a calendar view of trading performance for a specific month
    public CalendarView getCalendarView(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<Trade> trades = tradeRepository.findByTradeDateBetween(startDate, endDate);
        
        Map<Integer, List<Trade>> tradesByDay = new HashMap<>();
        for (Trade trade : trades) {
            int day = trade.getEntryTime().toLocalDate().getDayOfMonth();
            tradesByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(trade);
        }

        Map<Integer, Map<String, Object>> dailyPerformance = new HashMap<>();
        for (Map.Entry<Integer, List<Trade>> entry : tradesByDay.entrySet()) {
            dailyPerformance.put(entry.getKey(), calculateDailyPerformance(entry.getValue()));
        }
        
        CalendarView calendarView = new CalendarView();
        calendarView.setYear(year);
        calendarView.setMonth(month);
        calendarView.setDailyPerformance(dailyPerformance);
        return calendarView;
    }

    // Calculates the win percentage for a list of trades
    private double calculateWinPercentage(List<Trade> trades) {
        long winningTrades = trades.stream().filter(Trade::isWinning).count();
        return (double) winningTrades / trades.size() * 100;
    }

    // Calculates the total profit/loss for a list of trades
    private double calculateProfitLoss(List<Trade> trades) {
        return trades.stream().mapToDouble(trade -> trade.getProfitLoss().doubleValue()).sum();
    }

    // Calculates the risk-reward ratio for a list of trades
    private double calculateRiskRewardRatio(List<Trade> trades) {
        double totalRisk = trades.stream().mapToDouble(trade -> trade.getRisk().doubleValue()).sum();
        double totalReward = trades.stream().mapToDouble(trade -> trade.getReward().doubleValue()).sum();
        return totalRisk != 0 ? totalReward / totalRisk : 0;
    }

    // Calculates the average trade duration for a list of trades
    private double calculateAverageTradeDuration(List<Trade> trades) {
        return trades.stream()
            .mapToLong(trade -> trade.getExitTime().toEpochSecond(ZoneOffset.UTC) - trade.getEntryTime().toEpochSecond(ZoneOffset.UTC))
            .average()
            .orElse(0) / 3600; // Convert seconds to hours
    }

    // Maps a trade to a performance data object
    private Map<String, Object> mapTradeToPerformance(Trade trade) {
        Map<String, Object> performance = new HashMap<>();
        performance.put("tradeId", trade.getId());
        performance.put("profitLoss", trade.getProfitLoss());
        performance.put("account", trade.getTradingAccount().getAccountName());
        performance.put("followedDailyPlan", trade.isFollowedDailyPlan());
        performance.put("followedWeeklyPlan", trade.isFollowedWeeklyPlan());
        performance.put("setupOrPlaybook", trade.getSetup() != null ? trade.getSetup() : trade.getPlaybook());
        performance.put("indicators", trade.getIndicators().stream().map(Indicator::getName).collect(Collectors.toList()));
        performance.put("tradeTime", trade.getEntryTime());
        return performance;
    }

    // Calculates daily performance metrics for a list of trades
    private Map<String, Object> calculateDailyPerformance(List<Trade> trades) {
        Map<String, Object> performance = new HashMap<>();
        performance.put("totalProfitLoss", calculateProfitLoss(trades));
        performance.put("accountProfitLoss", trades.stream().collect(Collectors.groupingBy(
            t -> t.getTradingAccount().getAccountName(),
            Collectors.summingDouble(t -> t.getProfitLoss().doubleValue())
        )));
        performance.put("trades", trades.stream().map(this::mapTradeToPerformance).collect(Collectors.toList()));
        return performance;
    }

    // Calculates performance metrics for different indicator combinations
    public Map<List<Indicator>, PerformanceMetrics> getIndicatorCombinationPerformance(LocalDate startDate, LocalDate endDate) {
        List<Trade> trades = tradeRepository.findByTradeDateBetween(startDate, endDate);
        
        return trades.stream()
            .collect(Collectors.groupingBy(
                Trade::getIndicators,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    this::calculatePerformanceMetricsObject
                )
            ));
    }

    // Calculates performance metrics for a list of trades and returns a PerformanceMetrics object
    private PerformanceMetrics calculatePerformanceMetricsObject(List<Trade> trades) {
        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.setWinPercentage(calculateWinPercentage(trades));
        metrics.setProfitLoss(calculateProfitLoss(trades));
        // Set other metrics...
        return metrics;
    }

    // Calculates performance metrics for each trading account
    public Map<TradingAccount, PerformanceMetrics> getAccountPerformance(LocalDate startDate, LocalDate endDate) {
        List<TradingAccount> accounts = tradingAccountRepository.findAll();
        
        return accounts.stream()
            .collect(Collectors.toMap(
                Function.identity(),
                account -> {
                    List<Trade> accountTrades = tradeRepository.findByTradingAccountAndTradeDateBetween(account, startDate, endDate);
                    return calculatePerformanceMetricsObject(accountTrades);
                },
                (a, b) -> a,
                HashMap::new
            ));
    }

    // Generates a filtered performance report based on specified criteria
    public PerformanceMetrics getFilteredReport(PerformanceReportFilter filter) {
        List<Trade> trades = tradeRepository.findAll(createSpecification(filter));
        return calculatePerformanceMetricsObject(trades);
    }

    // Creates a specification for filtering trades based on various criteria
    private Specification<Trade> createSpecification(PerformanceReportFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (filter.getStartDate() != null && filter.getEndDate() != null) {
                predicates.add(cb.between(root.get("tradeDate"), filter.getStartDate(), filter.getEndDate()));
            }
            
            if (filter.getSetupIds() != null && !filter.getSetupIds().isEmpty()) {
                predicates.add(root.get("setup").get("id").in(filter.getSetupIds()));
            }
            
            if (filter.getPlaybookIds() != null && !filter.getPlaybookIds().isEmpty()) {
                predicates.add(root.get("playbook").get("id").in(filter.getPlaybookIds()));
            }
            
            if (filter.getIndicatorIds() != null && !filter.getIndicatorIds().isEmpty()) {
                predicates.add(root.join("indicators").get("id").in(filter.getIndicatorIds()));
            }
            
            if (filter.getInstruments() != null && !filter.getInstruments().isEmpty()) {
                predicates.add(root.get("instrument").in(filter.getInstruments()));
            }
            
            if (filter.getAccountIds() != null && !filter.getAccountIds().isEmpty()) {
                predicates.add(root.get("account").get("id").in(filter.getAccountIds()));
            }
            
            if (filter.getFollowedDailyPlan() != null) {
                predicates.add(cb.equal(root.get("followedDailyPlan"), filter.getFollowedDailyPlan()));
            }
            
            if (filter.getFollowedWeeklyPlan() != null) {
                predicates.add(cb.equal(root.get("followedWeeklyPlan"), filter.getFollowedWeeklyPlan()));
            }
            
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    // Calculates the percentage of trades that adhere to a specific plan
    private double calculatePlanAdherence(List<Trade> trades, java.util.function.Predicate<Trade> planAdherencePredicate) {
        long adherentTrades = trades.stream().filter(planAdherencePredicate).count();
        return trades.isEmpty() ? 0 : (double) adherentTrades / trades.size() * 100;
    }

    // Generates a comprehensive performance report based on specified filter criteria
    public PerformanceMetrics generateReport(PerformanceReportFilter filter) {
        List<Trade> trades = tradeRepository.findAll(createSpecification(filter));
        
        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.setWinPercentage(calculateWinPercentage(trades));
        metrics.setProfitLoss(calculateProfitLoss(trades));
        metrics.setRiskRewardRatio(calculateRiskRewardRatio(trades));
        metrics.setAverageTradeDuration(calculateAverageTradeDuration(trades));
        metrics.setTimeOfDayPerformance(calculateHourlyTimeOfDayPerformance(trades));
        metrics.setSetupPerformance(getSetupPerformance(filter.getStartDate(), filter.getEndDate()));
        metrics.setPlaybookPerformance(getPlaybookPerformance(filter.getStartDate(), filter.getEndDate()));
        metrics.setIndicatorPerformance(getIndicatorPerformance(filter.getStartDate(), filter.getEndDate()));
        metrics.setTradePerformance(getTradePerformance(filter.getStartDate(), filter.getEndDate()));
        metrics.setPlanAdherencePerformance(getPlanAdherencePerformance(trades));
        metrics.setInstrumentPerformance(getInstrumentPerformance(filter.getStartDate(), filter.getEndDate()));
        
        return metrics;
    }

    // Calculates performance metrics for trades that adhere to daily or weekly plans
    public PerformanceMetrics getPlanAssociationPerformance(LocalDate startDate, LocalDate endDate, boolean isDaily) {
        List<Trade> trades = tradeRepository.findByTradeDateBetween(startDate, endDate);
        
        PerformanceMetrics metrics = new PerformanceMetrics();
        
        if (isDaily) {
            double dailyPlanAdherence = calculatePlanAdherence(trades, Trade::isFollowedDailyPlan);
            metrics.setDailyPlanAdherence(dailyPlanAdherence);
            
            List<Trade> adherentTrades = trades.stream().filter(Trade::isFollowedDailyPlan).collect(Collectors.toList());
            List<Trade> nonAdherentTrades = trades.stream().filter(t -> !t.isFollowedDailyPlan()).collect(Collectors.toList());
            
            metrics.setAdherentTradesPerformance(calculatePerformanceMetrics(adherentTrades));
            metrics.setNonAdherentTradesPerformance(calculatePerformanceMetrics(nonAdherentTrades));
        } else {
            double weeklyPlanAdherence = calculatePlanAdherence(trades, Trade::isFollowedWeeklyPlan);
            metrics.setWeeklyPlanAdherence(weeklyPlanAdherence);
            
            List<Trade> adherentTrades = trades.stream().filter(Trade::isFollowedWeeklyPlan).collect(Collectors.toList());
            List<Trade> nonAdherentTrades = trades.stream().filter(t -> !t.isFollowedWeeklyPlan()).collect(Collectors.toList());
            
            metrics.setAdherentTradesPerformance(calculatePerformanceMetrics(adherentTrades));
            metrics.setNonAdherentTradesPerformance(calculatePerformanceMetrics(nonAdherentTrades));
        }
        
        return metrics;
    }

    // Emotional analysis methods
    public EmotionalNote addEmotionalNote(EmotionalNote note) {
        return emotionalNoteRepository.save(note);
    }

    public List<EmotionalNote> getEmotionalNotesByTradeId(Long tradeId) {
        return emotionalNoteRepository.findByTradeId(tradeId);
    }

    public List<EmotionalNote> getEmotionalNotesByEmotion(String emotion) {
        return emotionalNoteRepository.findByEmotion(emotion);
    }

    // New method to analyze performance based on emotions
    public Map<String, Double> analyzePerformanceByEmotion(String emotion) {
        List<EmotionalNote> notes = emotionalNoteRepository.findByEmotion(emotion);
        Map<String, Double> performanceMetrics = new HashMap<>();
        
        if (notes.isEmpty()) {
            return performanceMetrics;
        }

        List<Trade> associatedTrades = notes.stream()
                .map(EmotionalNote::getTrade)
                .distinct()
                .collect(Collectors.toList());

        int totalTrades = associatedTrades.size();
        int winningTrades = 0;
        BigDecimal totalProfitLoss = BigDecimal.ZERO;
        for (Trade trade : associatedTrades) {
            if (trade.getProfitLoss().compareTo(BigDecimal.ZERO) > 0) {
                winningTrades++;
            }
            totalProfitLoss = totalProfitLoss.add(trade.getProfitLoss());
        }

        double winRate = (double) winningTrades / totalTrades * 100;
        double averageProfitLoss = totalProfitLoss.doubleValue() / totalTrades;

        performanceMetrics.put("winRate", winRate);
        performanceMetrics.put("averageProfitLoss", averageProfitLoss);
        performanceMetrics.put("totalProfitLoss", totalProfitLoss.doubleValue());

        return performanceMetrics;
    }

    // New method to get emotional trends over time
    public Map<String, Integer> getEmotionalTrends(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        
        List<EmotionalNote> notes = emotionalNoteRepository.findByTimestampBetween(startDateTime, endDateTime);
        
        Map<String, Integer> emotionCounts = new HashMap<>();
        
        for (EmotionalNote note : notes) {
            String emotion = note.getEmotion();
            emotionCounts.put(emotion, emotionCounts.getOrDefault(emotion, 0) + 1);
        }
        
        return emotionCounts;
    }

    // Add more emotional analysis methods as needed
}
