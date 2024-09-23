package com.TRjournal.TrJournal.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TRjournal.TrJournal.model.Instrument;
import com.TRjournal.TrJournal.service.InstrumentService;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;

    @PostMapping("/{id}/tick-value")
    public ResponseEntity<Instrument> setTickValue(@PathVariable Long id, 
                                                   @RequestParam BigDecimal tickValue,
                                                   @RequestParam BigDecimal tickSize) {
        Instrument updatedInstrument = instrumentService.setTickValue(id, tickValue, tickSize);
        return ResponseEntity.ok(updatedInstrument);
    }
}
