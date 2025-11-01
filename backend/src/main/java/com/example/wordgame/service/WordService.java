package com.example.wordgame.service;

import com.example.wordgame.model.WordEntry;
import com.example.wordgame.util.PalindromeUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class WordService {

    private final List<WordEntry> words = new CopyOnWriteArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();

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

    protected  boolean isEnglishWord(String word) {
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word.toLowerCase();
        try {
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
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

    public int getTotalScore() {
        return words.stream()
                .mapToInt(WordEntry::getScore)
                .sum();
    }

    public void resetWords() {
        words.clear();
    }
}
