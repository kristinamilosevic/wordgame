package com.example.wordgame;

import com.example.wordgame.model.WordEntry;
import com.example.wordgame.service.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordgameUnitTest {

    private WordService wordService;

    @BeforeEach
    void setUp() {
        wordService = new WordService();
    }

    @Test
    void testAddPalindromeWord() {
        WordEntry entry = wordService.processWord("level");
        assertEquals("level", entry.getWord());
        assertTrue(entry.isPalindrome());
        assertEquals(3 + 3, entry.getScore());
    }

    @Test
    void testAddAlmostPalindromeWord() {
        WordEntry entry = wordService.processWord("engage");
        assertFalse(entry.isPalindrome());
        assertTrue(entry.isAlmostPalindrome());
        assertEquals(4 + 2, entry.getScore());
    }

    @Test
    void testAddWordNotInDictionary() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wordService.processWord("leved");
        });
        assertEquals("Word not found in English dictionary", exception.getMessage());
    }

    @Test
    void testGetAllWordsSorted() {
        wordService.processWord("moon");
        wordService.processWord("level");
        List<WordEntry> allWords = wordService.getAllWords();
        assertEquals("level", allWords.get(0).getWord());
    }

    @Test
    void testGetTotalScore() {
        wordService.processWord("moon");
        wordService.processWord("level");
        assertEquals((3) + (3 + 3), wordService.getTotalScore());
    }

    @Test
    void testResetWords() {
        wordService.processWord("moon");
        wordService.resetWords();
        assertTrue(wordService.getAllWords().isEmpty());
        assertEquals(0, wordService.getTotalScore());
    }
}
