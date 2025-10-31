package com.example.wordgame.service;

import com.example.wordgame.model.WordEntry;
import com.example.wordgame.util.PalindromeUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class WordService {

    private final List<WordEntry> words = new CopyOnWriteArrayList<>();

    public WordEntry processWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty");
        }

        if (!isEnglishWord(word)) {
            throw new IllegalArgumentException("Word not found in English dictionary");
        }

        int score = calculateScore(word);
        boolean isPal = PalindromeUtils.isPalindrome(word);
        boolean isAlmostPal = !isPal && PalindromeUtils.isAlmostPalindrome(word);

        Optional<WordEntry> existing = words.stream()
                .filter(w -> w.getWord().equalsIgnoreCase(word))
                .findFirst();

        if (existing.isPresent()) {
            WordEntry oldEntry = existing.get();
            int newScore = oldEntry.getScore() + score;

            WordEntry updated = new WordEntry(word, newScore, isPal, isAlmostPal);

            words.remove(oldEntry);
            words.add(updated);
            return updated;
        } else {
            WordEntry entry = new WordEntry(word, score, isPal, isAlmostPal);
            words.add(entry);
            return entry;
        }
    }

    public List<WordEntry> getAllWords() {
        return words.stream()
                .sorted(Comparator.comparingInt(WordEntry::getScore).reversed())
                .toList();
    }

    private boolean isEnglishWord(String word) {
        return word.matches("^[a-zA-Z]+$");
    }

    private int calculateScore(String word) {
        int uniqueLetters = (int) word.toLowerCase().chars().distinct().count();
        int score = uniqueLetters;

        if (PalindromeUtils.isPalindrome(word)) {
            score += 3;
        } else if (PalindromeUtils.isAlmostPalindrome(word)) {
            score += 2;
        }

        return score;
    }
}
