package com.TRjournal.TrJournal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TRjournal.TrJournal.model.Playbook;
import com.TRjournal.TrJournal.service.PlaybookService;

@RestController
@RequestMapping("/api/playbooks")
public class PlaybookController {
    @Autowired
    private PlaybookService playbookService;

    // Creates a new playbook in the database
    // This method handles POST requests to the /api/playbooks URL
    // It accepts a Playbook object in the request body and returns the created playbook
    @PostMapping
    public Playbook createPlaybook(@RequestBody Playbook playbook) {
        return playbookService.createPlaybook(playbook);
    }

    // Updates an existing playbook by its ID in the database
    // This method handles PUT requests to the /api/playbooks/{id} URL
    // It accepts a Playbook object in the request body and returns the updated playbook
    @PutMapping("/{id}")
    public Playbook updatePlaybook(@PathVariable Long id, @RequestBody Playbook playbook) {
        return playbookService.updatePlaybook(id, playbook);
    }

    // Deletes a playbook by its ID from the database
    // This method handles DELETE requests to the /api/playbooks/{id} URL
    // It does not return any content upon successful deletion
    @DeleteMapping("/{id}")
    public void deletePlaybook(@PathVariable Long id) {
        playbookService.deletePlaybook(id);
    }

    // Creates a new playbook from a setup
    // This method handles POST requests to the /api/playbooks/create-from-setup URL
    // It accepts setupId, minWinRate, and minProfitLoss as query parameters and returns the created playbook
    @PostMapping("/create-from-setup")
    public ResponseEntity<Playbook> createPlaybookFromSetup(
            @RequestParam Long setupId,
            @RequestParam double minWinRate,
            @RequestParam double minProfitLoss) {
        return ResponseEntity.ok(playbookService.createPlaybookFromSetup(setupId, minWinRate, minProfitLoss));
    }
}
