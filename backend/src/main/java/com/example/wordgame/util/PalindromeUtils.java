package com.example.wordgame.util;

public class PalindromeUtils {

    public static boolean isPalindrome(String word) {
        if (word == null) return false;
        String cleaned = word.toLowerCase().trim();
        int i = 0;
        int j = cleaned.length() - 1;
        while (i < j) {
            if (cleaned.charAt(i) != cleaned.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    public static boolean isAlmostPalindrome(String word) {
        if (word == null) return false;
        String cleaned = word.toLowerCase().trim();
        int i = 0;
        int j = cleaned.length() - 1;
        while (i < j) {
            if (cleaned.charAt(i) != cleaned.charAt(j)) {
                return isPalindrome(cleaned.substring(i + 1, j + 1)) ||
                        isPalindrome(cleaned.substring(i, j));
            }
            i++;
            j--;
        }
        return true;
    }
}
