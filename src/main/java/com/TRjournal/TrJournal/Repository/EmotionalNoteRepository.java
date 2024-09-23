package com.TRjournal.TrJournal.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TRjournal.TrJournal.model.EmotionalNote;

@Repository
public interface EmotionalNoteRepository extends JpaRepository<EmotionalNote, Long> {
    List<EmotionalNote> findByTradeId(Long tradeId);
    List<EmotionalNote> findByEmotion(String emotion);
    List<EmotionalNote> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<EmotionalNote> findByEmotionAndTimestampBetween(String emotion, LocalDateTime start, LocalDateTime end);
}
