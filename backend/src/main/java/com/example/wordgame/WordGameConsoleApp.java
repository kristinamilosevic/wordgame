package com.example.wordgame;

import com.example.wordgame.model.WordEntry;
import com.example.wordgame.service.WordService;

import java.util.List;
import java.util.Scanner;

public class WordGameConsoleApp {

    public static void main(String[] args) {
        WordService wordService = new WordService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Welcome to the Word Game ---");
        System.out.println("Type 'exit' to quit, 'reset' to clear all words.");

        while (true) {
            System.out.print("\n-- Enter a word: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the game. Total score: " + wordService.getTotalScore());
                break;
            }

            if (input.equalsIgnoreCase("reset")) {
                wordService.resetWords();
                System.out.println("All words cleared.");
                continue;
            }

            try {
                WordEntry entry = wordService.processWord(input);

                int uniqueScore = calculateUniqueLetterScore(entry.getWord());
                int palindromeBonus = entry.isPalindrome() ? uniqueScore : entry.isAlmostPalindrome() ? 2 : 0;

                System.out.println("\nWord added: " + entry.getWord());
                System.out.println("Unique letters score: " + uniqueScore);
                System.out.println("Palindrome/Almost Palindrome bonus: " + palindromeBonus);
                System.out.println("Total score for this word: " + entry.getScore());
                System.out.println("Total score overall: " + wordService.getTotalScore());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

            List<WordEntry> allWords = wordService.getAllWords();
            System.out.println("\n--- Words List (sorted by score) ---");
            allWords.forEach(w -> {
                int uniqueScore = calculateUniqueLetterScore(w.getWord());
                int palindromeBonus = w.isPalindrome() ? uniqueScore : w.isAlmostPalindrome() ? 2 : 0;

                System.out.println(
                        w.getWord() +
                                " | Score: " + w.getScore() +
                                " | Unique: " + uniqueScore +
                                " | Bonus: " + palindromeBonus +
                                " | Palindrome: " + w.isPalindrome() +
                                " | Almost Palindrome: " + w.isAlmostPalindrome()
                );
            });
        }

        scanner.close();
    }
    private static int calculateUniqueLetterScore(String word) {
        return (int) word.toLowerCase().chars().distinct().count();
    }
}
