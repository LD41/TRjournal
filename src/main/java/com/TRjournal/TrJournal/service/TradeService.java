package com.TRjournal.TrJournal.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TRjournal.TrJournal.Repository.IndicatorRepository;
import com.TRjournal.TrJournal.Repository.TradeRepository;
import com.TRjournal.TrJournal.model.Indicator;
import com.TRjournal.TrJournal.model.Trade;
import com.TRjournal.TrJournal.model.User;

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IndicatorRepository indicatorRepository;

    // Logs a new trade with associated photos
    public Trade logTrade(Trade trade, List<MultipartFile> photos) {
        User currentUser = userService.getCurrentUser();
        trade.setUser(currentUser);
        if (photos != null && !photos.isEmpty()) {
            List<String> photoUrls = photos.stream()
                .map(photo -> fileStorageService.store(photo))
                .collect(Collectors.toList());
            trade.setPhotoUrls(photoUrls);
        }
        
        return tradeRepository.save(trade);
    }

    // Retrieves trades by trade plan type
    public List<Trade> getTradesByTradePlanType(String planType) {
        return tradeRepository.findByPlanType(planType);
    }

    // Calculates total profit/loss across multiple accounts
    public BigDecimal calculateTotalProfitLoss(List<String> accountsUsed, BigDecimal manualProfitLoss) {
        return manualProfitLoss.multiply(BigDecimal.valueOf(accountsUsed.size()));
    }

    // Retrieves a specific trade for the current user
    public Trade getTrade(Long id) {
        User currentUser = userService.getCurrentUser();
        return tradeRepository.findByIdAndUserId(id, currentUser.getId())
            .orElseThrow(() -> new NoSuchElementException("Trade not found or you don't have permission to access it"));
    }

    // Retrieves all trades for the current user
    public List<Trade> getAllTradesForUser() {
        User currentUser = userService.getCurrentUser();
        return tradeRepository.findAllByUserId(currentUser.getId());
    }

    // Updates an existing trade
    public Trade updateTrade(Long id, Trade updatedTrade) {
        Trade trade = getTrade(id);
        // Update trade fields
        return tradeRepository.save(trade);
    }

    // Deletes a trade by its ID
    public void deleteTrade(Long id) {
        tradeRepository.deleteById(id);
    }

    // Retrieves trades by instrument
    public List<Trade> getTradesByInstrument(String instrument) {
        return tradeRepository.findByInstrument(instrument);
    }

    // Retrieves trades by setup
    public List<Trade> getTradesBySetup(String setup) {
        return tradeRepository.findBySetup(setup);
    }

    // Adds an indicator to a trade
    public Trade addIndicatorToTrade(Long tradeId, Long indicatorId) {
        Trade trade = tradeRepository.findById(tradeId)
            .orElseThrow(() -> new RuntimeException("Trade not found"));
        Indicator indicator = indicatorRepository.findById(indicatorId)
            .orElseThrow(() -> new RuntimeException("Indicator not found"));

        trade.addIndicator(indicator);
        return tradeRepository.save(trade);
    }

    // Removes an indicator from a trade
    public Trade removeIndicatorFromTrade(Long tradeId, Long indicatorId) {
        Trade trade = tradeRepository.findById(tradeId)
            .orElseThrow(() -> new RuntimeException("Trade not found"));
        Indicator indicator = indicatorRepository.findById(indicatorId)
            .orElseThrow(() -> new RuntimeException("Indicator not found"));

        trade.removeIndicator(indicator);
        return tradeRepository.save(trade);
    }

    // Retrieves trades by a list of indicator IDs
    public List<Trade> getTradesByIndicators(List<Long> indicatorIds) {
        return tradeRepository.findByIndicatorIdsIn(indicatorIds);
    }

    // Calculates trade statistics for a specific indicator
    public Map<String, Object> getTradeStatsByIndicator(Long indicatorId) {
        List<Trade> trades = tradeRepository.findByIndicatorId(indicatorId);
        
        long winCount = trades.stream().filter(Trade::isWin).count();
        double totalProfit = trades.stream().mapToDouble(Trade::getProfit).sum();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("winPercentage", trades.isEmpty() ? 0 : (double) winCount / trades.size() * 100);
        stats.put("averageProfit", trades.isEmpty() ? 0 : totalProfit / trades.size());
        stats.put("totalProfit", totalProfit);
        stats.put("tradeCount", trades.size());

        return stats;
    }

    // Calculates trade statistics by time of day for a specific indicator
    public List<Map<String, Object>> getTradeStatsByTimeOfDay(Long indicatorId) {
        List<Trade> trades = tradeRepository.findByIndicatorId(indicatorId);

        return trades.stream()
            .collect(Collectors.groupingBy(trade -> trade.getEntryTime().getHour(),
                Collectors.collectingAndThen(Collectors.toList(), tradeList -> {
                    long winCount = tradeList.stream().filter(Trade::isWin).count();
                    double totalProfit = tradeList.stream().mapToDouble(Trade::getProfit).sum();
                    Map<String, Object> stats = new HashMap<>();
                    stats.put("hour", tradeList.get(0).getEntryTime().getHour());
                    stats.put("winPercentage", (double) winCount / tradeList.size() * 100);
                    stats.put("averageProfit", totalProfit / tradeList.size());
                    stats.put("tradeCount", tradeList.size());
                    return stats;
                })))
            .values()
            .stream()
            .sorted(Comparator.comparingInt(m -> (Integer) m.get("hour")))
            .collect(Collectors.toList());
    }
}
