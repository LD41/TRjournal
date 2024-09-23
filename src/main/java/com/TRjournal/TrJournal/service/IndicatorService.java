package com.TRjournal.TrJournal.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TRjournal.TrJournal.Repository.IndicatorRepository;
import com.TRjournal.TrJournal.Repository.TradeRepository;
import com.TRjournal.TrJournal.model.Indicator;
import com.TRjournal.TrJournal.model.Trade;

@Service
public class IndicatorService {
    @Autowired
    private IndicatorRepository indicatorRepository;

    @Autowired
    private TradeRepository tradeRepository;

    // Retrieves all indicators from the database
    public List<Indicator> getAllIndicators() {
        return indicatorRepository.findAll();
    }

    // Retrieves a specific indicator by its ID
    public Optional<Indicator> getIndicatorById(Long id) {
        return indicatorRepository.findById(id);
    }

    // Creates a new indicator in the database
    public Indicator createIndicator(Indicator indicator) {
        return indicatorRepository.save(indicator);
    }

    // Updates an existing indicator in the database
    public Indicator updateIndicator(Long id, Indicator indicator) {
        Optional<Indicator> existingIndicator = getIndicatorById(id);
        if (existingIndicator.isPresent()) {
            Indicator updatedIndicator = existingIndicator.get();
            updatedIndicator.setName(indicator.getName());
            updatedIndicator.setDescription(indicator.getDescription());
            updatedIndicator.setTimeframe(indicator.getTimeframe());
            updatedIndicator.setParameters(indicator.getParameters());
            return indicatorRepository.save(updatedIndicator);
        }
        return null;
    }

    // Deletes an indicator from the database
    public boolean deleteIndicator(Long id) {
        Optional<Indicator> indicator = getIndicatorById(id);
        if (indicator.isPresent()) {
            indicatorRepository.delete(indicator.get());
            return true;
        }
        return false;
    }

    // Calculates and returns performance statistics for a specific indicator
    public Map<String, Double> getIndicatorPerformanceStats(Long indicatorId) {
        Indicator indicator = indicatorRepository.findById(indicatorId)
            .orElseThrow(() -> new RuntimeException("Indicator not found"));

        Map<String, Double> stats = new HashMap<>();
        stats.put("winPercentage", indicator.getWinRate() * 100);
        stats.put("averageProfit", indicator.getAverageProfit());
        stats.put("totalProfit", indicator.getTotalProfit());
        stats.put("usageCount", (double) indicator.getTotalUsage());

        return stats;
    }

    // Calculates and returns performance statistics for a specific indicator and instrument combination
    public Map<String, Double> getIndicatorPerformanceByInstrument(Long indicatorId, Long instrumentId) {
        List<Trade> trades = tradeRepository.findByIndicatorIdAndInstrumentId(indicatorId, instrumentId);
        
        long winCount = trades.stream().filter(Trade::isWin).count();
        double totalProfit = trades.stream().mapToDouble(Trade::getProfit).sum();
        
        Map<String, Double> stats = new HashMap<>();
        stats.put("winPercentage", trades.isEmpty() ? 0 : (double) winCount / trades.size() * 100);
        stats.put("averageProfit", trades.isEmpty() ? 0 : totalProfit / trades.size());
        stats.put("totalProfit", totalProfit);
        stats.put("usageCount", (double) trades.size());

        return stats;
    }

    // Calculates and returns performance statistics for combinations of indicators
    public List<Map<String, Object>> getIndicatorCombinationPerformance(List<Long> indicatorIds) {
        List<Trade> trades = tradeRepository.findByIndicatorIdsIn(indicatorIds);
        
        return trades.stream()
            .collect(Collectors.groupingBy(trade -> new HashSet<>(trade.getIndicators()),
                Collectors.collectingAndThen(Collectors.toList(), tradeList -> {
                    long winCount = tradeList.stream().filter(Trade::isWin).count();
                    double totalProfit = tradeList.stream().mapToDouble(Trade::getProfit).sum();
                    Map<String, Object> stats = new HashMap<>();
                    stats.put("indicators", tradeList.get(0).getIndicators().stream().map(Indicator::getName).collect(Collectors.toList()));
                    stats.put("winPercentage", (double) winCount / tradeList.size() * 100);
                    stats.put("averageProfit", totalProfit / tradeList.size());
                    stats.put("totalProfit", totalProfit);
                    stats.put("usageCount", tradeList.size());
                    return stats;
                })))
            .values()
            .stream()
            .sorted((a, b) -> Double.compare((Double) b.get("winPercentage"), (Double) a.get("winPercentage")))
            .collect(Collectors.toList());
    }

    // Calculates and returns the usage frequency of all indicators
    public Map<String, Long> getIndicatorUsageFrequency() {
        List<Indicator> indicators = indicatorRepository.findAll();
        return indicators.stream()
            .collect(Collectors.toMap(Indicator::getName, i -> (long) i.getTotalUsage()));
    }

    // Calculates and returns performance statistics for a specific indicator across different timeframes
    public List<Map<String, Object>> getIndicatorPerformanceByTimeframe(Long indicatorId) {
        Indicator indicator = indicatorRepository.findById(indicatorId)
            .orElseThrow(() -> new RuntimeException("Indicator not found"));

        return indicator.getTrades().stream()
            .collect(Collectors.groupingBy(Trade::getTimeframe,
                Collectors.collectingAndThen(Collectors.toList(), tradeList -> {
                    long winCount = tradeList.stream().filter(Trade::isWin).count();
                    double totalProfit = tradeList.stream().mapToDouble(Trade::getProfit).sum();
                    Map<String, Object> stats = new HashMap<>();
                    stats.put("timeframe", tradeList.get(0).getTimeframe());
                    stats.put("winPercentage", (double) winCount / tradeList.size() * 100);
                    stats.put("averageProfit", totalProfit / tradeList.size());
                    stats.put("totalProfit", totalProfit);
                    stats.put("usageCount", tradeList.size());
                    return stats;
                })))
            .values()
            .stream()
            .sorted((a, b) -> Double.compare((Double) b.get("winPercentage"), (Double) a.get("winPercentage")))
            .collect(Collectors.toList());
    }

}
