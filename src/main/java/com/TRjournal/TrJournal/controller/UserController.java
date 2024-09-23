package com.TRjournal.TrJournal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TRjournal.TrJournal.model.TradingAccount;
import com.TRjournal.TrJournal.model.User;
import com.TRjournal.TrJournal.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProfile(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String username, @RequestBody User user) {
        if (!username.equals(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username mismatch");
        }
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{userId}/trading-accounts")
    public ResponseEntity<TradingAccount> createTradingAccount(@PathVariable Long userId, @RequestBody TradingAccount account) {
        TradingAccount createdAccount = userService.createTradingAccount(userId, account);
        return ResponseEntity.ok(createdAccount);
    }

    @PutMapping("/{userId}/trading-accounts/{accountId}")
    public ResponseEntity<TradingAccount> updateTradingAccount(@PathVariable Long userId, @PathVariable Long accountId, @RequestBody TradingAccount account) {
        TradingAccount updatedAccount = userService.updateTradingAccount(userId, accountId, account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{userId}/trading-accounts/{accountId}")
    public ResponseEntity<Void> deactivateTradingAccount(@PathVariable Long userId, @PathVariable Long accountId) {
        userService.deactivateTradingAccount(userId, accountId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/trading-accounts")
    public ResponseEntity<List<TradingAccount>> getUserTradingAccounts(@PathVariable Long userId) {
        List<TradingAccount> accounts = userService.getUserTradingAccounts(userId);
        return ResponseEntity.ok(accounts);
    }
}
