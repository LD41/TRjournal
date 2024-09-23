package com.TRjournal.TrJournal.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TRjournal.TrJournal.model.Instrument;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    Optional<Instrument> findByNameAndType(String name, String type);
}
