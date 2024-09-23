package com.TRjournal.TrJournal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TRjournal.TrJournal.Repository.PlaybookRepository;
import com.TRjournal.TrJournal.Repository.SetupRepository;
import com.TRjournal.TrJournal.model.Playbook;
import com.TRjournal.TrJournal.model.Setup; // {{ edit_1 }}

@Service
public class SetupService {
    @Autowired
    private SetupRepository setupRepository;

    @Autowired
    private PlaybookRepository playbookRepository;

    // Creates a new setup and saves it to the database
    public Setup createSetup(Setup setup) {
        return setupRepository.save(setup);
    }

    // Updates an existing setup with new information
    public Setup updateSetup(Long id, Setup setup) {
        Setup existingSetup = setupRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Setup not found with id: " + id));
        // Update fields
        existingSetup.setName(setup.getName());
        existingSetup.setDescription(setup.getDescription());
        existingSetup.setEntryPoint(setup.getEntryPoint());
        existingSetup.setExitPoint(setup.getExitPoint());
        existingSetup.setIndicators(setup.getIndicators());
        existingSetup.setInstrument(setup.getInstrument());
        return setupRepository.save(existingSetup);
    }

    // Deletes a setup from the database by its ID
    public void deleteSetup(Long id) {
        setupRepository.deleteById(id);
    }

    // Converts a setup to a playbook and saves it to the database
    public Playbook convertToPlaybook(Long setupId) {
        Setup setup = setupRepository.findById(setupId)
            .orElseThrow(() -> new IllegalArgumentException("Setup not found with id: " + setupId));
        Playbook playbook = new Playbook();
        // Copy setup details to playbook
        playbook.setName(setup.getName());
        playbook.setDescription(setup.getDescription());
        playbook.setEntryPoint(setup.getEntryPoint());
        playbook.setExitPoint(setup.getExitPoint());
        playbook.setIndicators(setup.getIndicators());
        playbook.setInstrument(setup.getInstrument());
        return playbookRepository.save(playbook);
    }

    // Retrieves a setup by its ID
    public Optional<Setup> getSetupById(Long id) {
        return setupRepository.findById(id);
    }

    // Retrieves all setups from the database
    public List<Setup> getAllSetups() {
        return setupRepository.findAll(); // Ensure your SetupRepository has a findAll method
    }
}
