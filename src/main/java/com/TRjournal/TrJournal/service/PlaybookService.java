package com.TRjournal.TrJournal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TRjournal.TrJournal.Repository.PlaybookRepository;
import com.TRjournal.TrJournal.Repository.SetupRepository;
import com.TRjournal.TrJournal.model.PerformanceMetrics;
import com.TRjournal.TrJournal.model.Playbook;
import com.TRjournal.TrJournal.model.Setup;

@Service
public class PlaybookService {
    @Autowired
    private PlaybookRepository playbookRepository;
    @Autowired
    private SetupRepository setupRepository;
    @Autowired
    private PerformanceReportService performanceReportService; // Inject the PerformanceReportService

    // Retrieves all playbooks
    public List<Playbook> getAllPlaybooks() {
        return playbookRepository.findAll();
    }

    // Fetches a playbook by its ID
    public Optional<Playbook> getPlaybookById(Long id) {
        return playbookRepository.findById(id);
    }

    // Creates a new playbook
    public Playbook createPlaybook(Playbook playbook) {
        return playbookRepository.save(playbook);
    }

    // Updates an existing playbook
    public Playbook updatePlaybook(Long id, Playbook playbook) {
        Optional<Playbook> existingPlaybook = getPlaybookById(id);
        if (existingPlaybook.isPresent()) {
            Playbook updatedPlaybook = existingPlaybook.get();
            // Update fields
            updatedPlaybook.setName(playbook.getName());
            updatedPlaybook.setDescription(playbook.getDescription());
            updatedPlaybook.setEntryPoint(playbook.getEntryPoint());
            updatedPlaybook.setExitPoint(playbook.getExitPoint());
            updatedPlaybook.setIndicators(playbook.getIndicators());
            updatedPlaybook.setInstrument(playbook.getInstrument());
            updatedPlaybook.setWinRate(playbook.getWinRate());
            updatedPlaybook.setTotalTrades(playbook.getTotalTrades());
            updatedPlaybook.setProfitLoss(playbook.getProfitLoss());
            updatedPlaybook.setAvgTradeDuration(playbook.getAvgTradeDuration());
            return playbookRepository.save(updatedPlaybook);
        }
        return null; // Or handle this case as appropriate for your application
    }

    // Deletes a playbook by its ID
    public boolean deletePlaybook(Long id) {
        Optional<Playbook> playbook = getPlaybookById(id);
        if (playbook.isPresent()) {
            playbookRepository.delete(playbook.get());
            return true;
        }
        return false;
    }

    // Creates a new playbook from an existing setup if it meets performance criteria
    public Playbook createPlaybookFromSetup(Long setupId, double minWinRate, double minProfitLoss) {
        Setup setup = setupRepository.findById(setupId)
                .orElseThrow(() -> new RuntimeException("Setup not found"));

        // Define the date range for performance metrics
        LocalDate startDate = LocalDate.now().minusYears(1);
        LocalDate endDate = LocalDate.now();

        // Generate performance metrics for the specified date range
        PerformanceMetrics metrics = performanceReportService.generateReport(startDate, endDate);

        // Use the metrics to create the playbook
        if (metrics.getWinPercentage() >= minWinRate && metrics.getProfitLoss() >= minProfitLoss) {
            // Logic to create and save the playbook
            Playbook newPlaybook = new Playbook();
            newPlaybook.setName(setup.getName()); // Example of using setup
            newPlaybook.setDescription(setup.getDescription());
            // ... set other properties as needed ...
            
            // Save the new playbook to the repository
            return playbookRepository.save(newPlaybook);
        } else {
            throw new RuntimeException("Performance criteria not met for creating a playbook.");
        }
    }
}
