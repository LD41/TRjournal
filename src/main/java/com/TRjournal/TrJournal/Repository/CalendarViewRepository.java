package com.TRjournal.TrJournal.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TRjournal.TrJournal.model.CalendarView;

@Repository
public interface CalendarViewRepository extends JpaRepository<CalendarView, Long> {

    // Find CalendarView entries between two dates
    List<CalendarView> findByYearAndMonthBetween(int year, int startMonth, int endMonth);

    // Find a specific CalendarView for a given year and month
    CalendarView findByYearAndMonth(int year, int month);

    // Find CalendarView entries for a specific year
    List<CalendarView> findByYear(int year);

    // Custom query to find CalendarView entries within a date range
    List<CalendarView> findByYearBetweenAndMonthBetween(int startYear, int endYear, int startMonth, int endMonth);

    // You might need to adjust this method based on how you store daily performance data
    // This is just an example and might need modification based on your exact data structure
    List<CalendarView> findByDailyPerformanceContaining(String performanceMetric);

    // Find CalendarView entries between two dates
    List<CalendarView> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
