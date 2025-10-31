package com.example.wordgame.model;

public class WordEntry {
    private String word;
    private int score;
    private boolean palindrome;
    private boolean almostPalindrome;

    public WordEntry(String word, int score, boolean palindrome, boolean almostPalindrome) {
        this.word = word;
        this.score = score;
        this.palindrome = palindrome;
        this.almostPalindrome = almostPalindrome;
    }

    public String getWord() {
        return word;
    }

    public int getScore() {
        return score;
    }

    public boolean isPalindrome() {
        return palindrome;
    }

    public boolean isAlmostPalindrome() {
        return almostPalindrome;
    }
}
