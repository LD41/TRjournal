package com.TRjournal.TrJournal.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TRjournal.TrJournal.model.CalendarView;
import com.TRjournal.TrJournal.service.CalendarViewService;

@RestController
@RequestMapping("/api/calendar")
public class CalendarViewController {
    @Autowired
    private CalendarViewService calendarViewService;

    // Retrieves a list of CalendarView objects for a specified date range
    @GetMapping("/view")
    public List<CalendarView> getCalendarView(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return calendarViewService.getCalendarViewForDateRange(startDate, endDate);
    }

    // Retrieves daily performance data for a specific date
    @GetMapping("/daily/{date}")
    public Map<String, Object> getDailyPerformance(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return calendarViewService.getDailyPerformance(date);
    }

    // Retrieves setup performance data for a specified date range and setup type
    @GetMapping("/setup")
    public Map<String, Double> getSetupPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String setup) {
        return calendarViewService.getSetupPerformance(startDate, endDate, setup);
    }

    // Retrieves playbook performance data for a specified date range and playbook type
    @GetMapping("/playbook")
    public Map<String, Double> getPlayBookPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String playBook) {
        return calendarViewService.getPlayBookPerformance(startDate, endDate, playBook);
    }

    // Retrieves indicator performance data for a specified date range and indicator type
    @GetMapping("/indicator")
    public Map<String, Double> getIndicatorPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String indicator) {
        return calendarViewService.getIndicatorPerformance(startDate, endDate, indicator);
    }

    // Retrieves hourly performance data for a specific date
    @GetMapping("/hourly/{date}")
    public Map<Integer, Double> getHourlyPerformance(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return calendarViewService.getHourlyPerformance(date);
    }
}
