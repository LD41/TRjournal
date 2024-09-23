package com.TRjournal.TrJournal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TRjournal.TrJournal.Repository.TradingAccountRepository;
import com.TRjournal.TrJournal.Repository.UserRepository;
import com.TRjournal.TrJournal.model.TradingAccount;
import com.TRjournal.TrJournal.model.User;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TradingAccountRepository tradingAccountRepository;

    // Registers a new user
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Retrieves a user by their username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    // Updates an existing user's information
    public User updateUser(User user) {
        User existingUser = getUserByUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(existingUser);
    }

    // Loads user details for Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    // Creates a new trading account for a user
    public TradingAccount createTradingAccount(Long userId, TradingAccount account) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        account.setUser(user);
        return tradingAccountRepository.save(account);
    }

    // Updates an existing trading account
    public TradingAccount updateTradingAccount(Long userId, Long accountId, TradingAccount updatedAccount) {
        TradingAccount account = tradingAccountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Trading account not found"));
        
        if (!account.getUser().getId().equals(userId)) {
            throw new RuntimeException("Account does not belong to the user");
        }

        account.setAccountName(updatedAccount.getAccountName());
        account.setAccountNumber(updatedAccount.getAccountNumber());
        account.setBroker(updatedAccount.getBroker());
        account.setStartingBalance(updatedAccount.getStartingBalance());
        account.setCurrency(updatedAccount.getCurrency());

        return tradingAccountRepository.save(account);
    }

    // Deactivates a trading account
    public void deactivateTradingAccount(Long userId, Long accountId) {
        TradingAccount account = tradingAccountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Trading account not found"));
        
        if (!account.getUser().getId().equals(userId)) {
            throw new RuntimeException("Account does not belong to the user");
        }

        account.setActive(false);
        tradingAccountRepository.save(account);
    }

    // Retrieves all trading accounts for a user
    public List<TradingAccount> getUserTradingAccounts(Long userId) {
        return tradingAccountRepository.findByUserId(userId);
    }

    // Deletes a trading account by its ID
    public void deleteTradingAccount(Long accountId) {
        tradingAccountRepository.deleteById(accountId);
    }

    // Deletes a trading account for a specific user
    public void deleteTradingAccount(Long userId, Long accountId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        TradingAccount account = tradingAccountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Trading account not found"));
        
        if (!account.getUser().getId().equals(userId)) {
            throw new RuntimeException("Account does not belong to the user");
        }
        
        user.removeTradingAccount(account);
        userRepository.save(user);
    }

    // Retrieves the currently authenticated user
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
