package com.TRjournal.TrJournal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TRjournal.TrJournal.model.Playbook;
import com.TRjournal.TrJournal.model.Setup;
import com.TRjournal.TrJournal.service.SetupService; 
@RestController
@RequestMapping("/api/setups")
public class SetupController {
    @Autowired
    private SetupService setupService;

    // Retrieves a list of all setups from the database
    // This method handles GET requests to the /api/setups URL
    // It returns a list of all setups of type Setup
    @GetMapping
    public List<Setup> getAllSetups() {
        return setupService.getAllSetups();
    }

    // Retrieves a specific setup by its ID from the database
    // This method handles GET requests to the /api/setups/{id} URL
    // It returns the setup with the specified ID or null if not found
    @GetMapping("/{id}")
    public Setup getSetupById(@PathVariable Long id) {
        return setupService.getSetupById(id).orElse(null);
    }

    // Creates a new setup in the database
    // This method handles POST requests to the /api/setups URL
    // It accepts a Setup object in the request body and returns the created setup
    @PostMapping
    public Setup createSetup(@RequestBody Setup setup) {
        return setupService.createSetup(setup);
    }

    // Updates an existing setup by its ID in the database
    // This method handles PUT requests to the /api/setups/{id} URL
    // It accepts a Setup object in the request body and returns the updated setup
    @PutMapping("/{id}")
    public Setup updateSetup(@PathVariable Long id, @RequestBody Setup setup) {
        return setupService.updateSetup(id, setup);
    }

    // Deletes a setup by its ID from the database
    // This method handles DELETE requests to the /api/setups/{id} URL
    // It does not return any content upon successful deletion
    @DeleteMapping("/{id}")
    public void deleteSetup(@PathVariable Long id) {
        setupService.deleteSetup(id);
    }

    // Converts a setup to a playbook
    // This method handles POST requests to the /api/setups/{id}/convert-to-playbook URL
    // It returns the converted playbook
    @PostMapping("/{id}/convert-to-playbook")
    public Playbook convertToPlaybook(@PathVariable Long id) {
        return setupService.convertToPlaybook(id);
    }
}
