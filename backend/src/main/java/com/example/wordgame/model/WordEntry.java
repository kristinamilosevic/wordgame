package com.example.wordgame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordEntry {

    private String word;
    private int score;
    private boolean palindrome;
    private boolean almostPalindrome;

}
