package com.TRjournal.TrJournal.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TRjournal.TrJournal.model.CalendarView;
import com.TRjournal.TrJournal.model.EmotionalNote;
import com.TRjournal.TrJournal.model.Indicator;
import com.TRjournal.TrJournal.model.PerformanceMetrics;
import com.TRjournal.TrJournal.model.PerformanceReportFilter;
import com.TRjournal.TrJournal.model.TradingAccount;
import com.TRjournal.TrJournal.service.PerformanceReportService;

@RestController
@RequestMapping("/api/performance")
public class PerformanceReportController {

    @Autowired
    private PerformanceReportService performanceReportService;

    // Retrieves a list of all performance reports from the database
    @GetMapping("/generate")
    public ResponseEntity<PerformanceMetrics> generateReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        PerformanceReportFilter filter = new PerformanceReportFilter();
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);
        return ResponseEntity.ok(performanceReportService.generateReport(filter));
    }

    // Retrieves a custom performance report from the database
    @GetMapping("/custom")
    public ResponseEntity<PerformanceMetrics> generateCustomReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(performanceReportService.generateCustomReport(startDate, endDate));
    }

    // Retrieves setup performance data from the database
    @GetMapping("/setups")
    public ResponseEntity<Map<String, Map<String, Double>>> getSetupPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(performanceReportService.getSetupPerformance(startDate, endDate));
    }

    // Retrieves playbook performance data from the database
    @GetMapping("/playbooks")
    public ResponseEntity<Map<String, Map<String, Double>>> getPlaybookPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(performanceReportService.getPlaybookPerformance(startDate, endDate));
    }

    // Retrieves indicator performance data from the database
    @GetMapping("/indicators")
    public ResponseEntity<Map<String, Map<String, Double>>> getIndicatorPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(performanceReportService.getIndicatorPerformance(startDate, endDate));
    }

    // Retrieves instrument performance data from the database
    @GetMapping("/instruments")
    public ResponseEntity<Map<String, Double>> getInstrumentPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(performanceReportService.getInstrumentPerformance(startDate, endDate));
    }

    // Retrieves plan association performance data from the database
    @GetMapping("/plan-association")
    public ResponseEntity<PerformanceMetrics> getPlanAssociationPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam boolean isDaily) {
        return ResponseEntity.ok(performanceReportService.getPlanAssociationPerformance(startDate, endDate, isDaily));
    }

    // Retrieves trade performance data from the database
    @GetMapping("/trades")
    public ResponseEntity<List<Map<String, Object>>> getTradePerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(performanceReportService.getTradePerformance(startDate, endDate));
    }

    // Retrieves calendar view data from the database
    @GetMapping("/calendar")
    public ResponseEntity<CalendarView> getCalendarView(
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(performanceReportService.getCalendarView(year, month));
    }

    // Retrieves indicator combination performance data from the database
    @GetMapping("/indicator-combinations")
    public ResponseEntity<Map<List<Indicator>, PerformanceMetrics>> getIndicatorCombinationPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(performanceReportService.getIndicatorCombinationPerformance(startDate, endDate));
    }

    // Retrieves account performance data from the database
    @GetMapping("/accounts")
    public ResponseEntity<Map<TradingAccount, PerformanceMetrics>> getAccountPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(performanceReportService.getAccountPerformance(startDate, endDate));
    }

    // Retrieves a filtered performance report from the database
    @PostMapping("/filtered")
    public ResponseEntity<PerformanceMetrics> getFilteredReport(@RequestBody PerformanceReportFilter filter) {
        return ResponseEntity.ok(performanceReportService.getFilteredReport(filter));
    }

    // Adds an emotional note to the database
    @PostMapping("/emotional-notes")
    public ResponseEntity<EmotionalNote> addEmotionalNote(@RequestBody EmotionalNote note) {
        EmotionalNote savedNote = performanceReportService.addEmotionalNote(note);
        return ResponseEntity.ok(savedNote);
    }

    // Retrieves emotional notes by trade ID from the database
    @GetMapping("/emotional-notes/trade/{tradeId}")
    public ResponseEntity<List<EmotionalNote>> getEmotionalNotesByTradeId(@PathVariable Long tradeId) {
        List<EmotionalNote> notes = performanceReportService.getEmotionalNotesByTradeId(tradeId);
        return ResponseEntity.ok(notes);
    }

    // Retrieves performance data by emotion from the database
    @GetMapping("/performance/by-emotion/{emotion}")
    public ResponseEntity<Map<String, Double>> getPerformanceByEmotion(@PathVariable String emotion) {
        Map<String, Double> performance = performanceReportService.analyzePerformanceByEmotion(emotion);
        return ResponseEntity.ok(performance);
    }
}
