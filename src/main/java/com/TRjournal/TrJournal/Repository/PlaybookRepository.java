package com.TRjournal.TrJournal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TRjournal.TrJournal.model.Playbook;

public interface PlaybookRepository extends JpaRepository<Playbook, Long> {
    // Find playbooks by name (could be useful for searching)
    List<Playbook> findByNameContainingIgnoreCase(String name);

    // Find playbooks by setup ID (useful for the createPlaybookFromSetup method)
    List<Playbook> findBySetupId(Long setupId);

    // Find playbooks with win rate above a certain threshold
    List<Playbook> findByWinRateGreaterThanEqual(double minWinRate);

    // Find playbooks with profit/loss above a certain threshold
    List<Playbook> findByProfitLossGreaterThanEqual(double minProfitLoss);

    // Custom query to find playbooks meeting both win rate and profit/loss criteria
    @Query("SELECT p FROM Playbook p WHERE p.winRate >= :minWinRate AND p.profitLoss >= :minProfitLoss")
    List<Playbook> findPlaybooksMeetingCriteria(@Param("minWinRate") double minWinRate, @Param("minProfitLoss") double minProfitLoss);
}
