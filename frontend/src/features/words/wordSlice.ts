import { createSlice, createAsyncThunk, PayloadAction } from "@reduxjs/toolkit";
import { fetchWords, addWord, fetchTotalScore, resetWords } from "../../services/wordService";

export interface WordEntry {
    word: string;
    score: number;
    palindrome: boolean;
    almostPalindrome: boolean;
}

interface WordState {
    words: WordEntry[];
    totalScore: number;
    loading: boolean;
    error: string | null;
}

const initialState: WordState = {
    words: [],
    totalScore: 0,
    loading: false,
    error: null,
};

interface ApiError {
  response?: {
    data?: {
      error?: string;
      message?: string;
    };
  };
}

export const addWordAsync = createAsyncThunk<
  WordEntry,
  string,
  { rejectValue: string }
>("words/addWord", async (word, { rejectWithValue }) => {
  try {
    const response = await addWord(word);
    return response;
  } catch (err: unknown) {
    const error = err as ApiError;
    if (error.response?.data?.error) {
      return rejectWithValue(error.response.data.error);
    }
    return rejectWithValue("Failed to add word");
  }
});

export const fetchWordsAsync = createAsyncThunk<
    WordEntry[],
    void,
    { rejectValue: string }
>("words/fetchWords", async (_, { rejectWithValue }) => {
    try {
        return await fetchWords();
    } catch (err) {
        const error = err as { response?: { data?: { message?: string } } };
        return rejectWithValue(
            error.response?.data?.message || "Failed to add word"
        );
    }
});

export const fetchTotalScoreAsync = createAsyncThunk<
    number,
    void,
    { rejectValue: string }
>("words/fetchTotalScore", async (_, { rejectWithValue }) => {
    try {
        return await fetchTotalScore();
    } catch (err) {
        const error = err as { response?: { data?: { message?: string } } };
        return rejectWithValue(
            error.response?.data?.message || "Failed to add word"
        );
    }
});

export const resetWordsAsync = createAsyncThunk<
    void,
    void,
    { rejectValue: string }
>("words/resetWords", async (_, { rejectWithValue }) => {
    try {
        await resetWords();
    } catch (err) {
        const error = err as { response?: { data?: { message?: string } } };
        return rejectWithValue(
            error.response?.data?.message || "Failed to add word"
        );
    }
});

const wordSlice = createSlice({
    name: "words",
    initialState,
    reducers: {
    },
    extraReducers: (builder) => {
        builder
            .addCase(addWordAsync.pending, (state) => { state.loading = true; })
            .addCase(addWordAsync.fulfilled, (state, action: PayloadAction<WordEntry>) => {
                state.loading = false;
                const existingIndex = state.words.findIndex(
                    (w) => w.word.toLowerCase() === action.payload.word.toLowerCase()
                );
                if (existingIndex >= 0) {
                    state.words[existingIndex] = action.payload;
                } else {
                    state.words.push(action.payload);
                }
                state.totalScore = state.words.reduce((sum, w) => sum + w.score, 0);
                state.error = null;
            })
            .addCase(addWordAsync.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload || "Failed to add word";
            })

            .addCase(fetchWordsAsync.pending, (state) => { state.loading = true; })
            .addCase(fetchWordsAsync.fulfilled, (state, action: PayloadAction<WordEntry[]>) => {
                state.loading = false;
                state.words = action.payload;
                state.totalScore = action.payload.reduce((sum, w) => sum + w.score, 0);
                state.error = null;
            })
            .addCase(fetchWordsAsync.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload || "Failed to fetch words";
            })

            .addCase(fetchTotalScoreAsync.pending, (state) => { state.loading = true; })
            .addCase(fetchTotalScoreAsync.fulfilled, (state, action: PayloadAction<number>) => {
                state.loading = false;
                state.totalScore = action.payload;
                state.error = null;
            })
            .addCase(fetchTotalScoreAsync.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload || "Failed to fetch total score";
            })

            .addCase(resetWordsAsync.pending, (state) => { state.loading = true; })
            .addCase(resetWordsAsync.fulfilled, (state) => {
                state.loading = false;
                state.words = [];
                state.totalScore = 0;
                state.error = null;
            })
            .addCase(resetWordsAsync.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload || "Failed to reset words";
            });
    },
});

export default wordSlice.reducer;
