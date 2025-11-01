package com.example.wordgame.controller;

import com.example.wordgame.dto.WordRequest;
import com.example.wordgame.model.WordEntry;
import com.example.wordgame.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/words")
@CrossOrigin(origins = "*")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping
    public ResponseEntity<?> addWord(@RequestBody WordRequest request) {
        try {
            WordEntry entry = wordService.processWord(request.getWord());
            return ResponseEntity.ok(entry);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<WordEntry>> getAllWords() {
        return ResponseEntity.ok(wordService.getAllWords());
    }

    @GetMapping("/total-score")
    public ResponseEntity<Integer> getTotalScore() {
        int totalScore = wordService.getTotalScore();
        return ResponseEntity.ok(totalScore);
    }

    @DeleteMapping
    public ResponseEntity<Void> resetWords() {
        wordService.resetWords();
        return ResponseEntity.noContent().build();
    }
}
