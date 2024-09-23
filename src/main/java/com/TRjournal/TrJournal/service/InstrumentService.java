package com.TRjournal.TrJournal.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TRjournal.TrJournal.Repository.InstrumentRepository;
import com.TRjournal.TrJournal.model.Instrument;

@Service
public class InstrumentService {

    @Autowired
    private InstrumentRepository instrumentRepository;
    /**
     * Sets the tick value and tick size for a given instrument.
     * 
     * @param instrumentId The ID of the instrument to update
     * @param tickValue The new tick value to set
     * @param tickSize The new tick size to set
     * @return The updated Instrument object
     * @throws RuntimeException if the instrument is not found
     */
    public Instrument setTickValue(Long instrumentId, BigDecimal tickValue, BigDecimal tickSize) {
        Instrument instrument = instrumentRepository.findById(instrumentId)
            .orElseThrow(() -> new RuntimeException("Instrument not found"));
        
        instrument.setTickValue(tickValue);
        instrument.setTickSize(tickSize);
        return instrumentRepository.save(instrument);
    }

    /**
     * Retrieves an existing instrument by name and type, or creates a new one if it doesn't exist.
     * 
     * @param name The name of the instrument
     * @param type The type of the instrument
     * @return The existing or newly created Instrument object
     */
    public Instrument getOrCreateInstrument(String name, String type) {
        return instrumentRepository.findByNameAndType(name, type)
            .orElseGet(() -> {
                Instrument newInstrument = new Instrument();
                newInstrument.setName(name);
                newInstrument.setType(type);
                return instrumentRepository.save(newInstrument);
            });
    }
}
