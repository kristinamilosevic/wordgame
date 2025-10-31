package com.example.wordgame.controller;

import com.example.wordgame.dto.WordRequest;
import com.example.wordgame.model.WordEntry;
import com.example.wordgame.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@CrossOrigin(origins = "*")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping
    public ResponseEntity<WordEntry> addWord(@RequestBody WordRequest request) {
        WordEntry entry = wordService.processWord(request.getWord());
        return ResponseEntity.ok(entry);
    }

    @GetMapping
    public ResponseEntity<List<WordEntry>> getAllWords() {
        return ResponseEntity.ok(wordService.getAllWords());
    }
}
