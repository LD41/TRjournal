package com.TRjournal.TrJournal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TRjournal.TrJournal.model.Setup;

public interface SetupRepository extends JpaRepository<Setup, Long> {
    // Find setups by name (case-insensitive, partial match)
    List<Setup> findByNameContainingIgnoreCase(String name);

    // Find setups by instrument
    List<Setup> findByInstrument(String instrument);

    // Find setups by entry point (assuming it's a String field)
    List<Setup> findByEntryPointContainingIgnoreCase(String entryPoint);

    // Find setups by exit point (assuming it's a String field)
    List<Setup> findByExitPointContainingIgnoreCase(String exitPoint);
}
