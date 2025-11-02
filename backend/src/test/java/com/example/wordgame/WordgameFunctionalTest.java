package com.example.wordgame;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WordgameFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addWord_success() throws Exception {
        String json = "{ \"word\": \"level\" }";

        mockMvc.perform(post("/api/words")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("level"))
                .andExpect(jsonPath("$.score").exists());
    }

    @Test
    public void getAllWords_success() throws Exception {
        mockMvc.perform(get("/api/words"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getTotalScore_success() throws Exception {
        mockMvc.perform(get("/api/words/total-score"))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }

    @Test
    public void resetWords_success() throws Exception {
        mockMvc.perform(delete("/api/words"))
                .andExpect(status().isNoContent());
    }
}
