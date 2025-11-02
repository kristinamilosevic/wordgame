import { createSlice, createAsyncThunk, PayloadAction } from "@reduxjs/toolkit";
import { fetchWords, addWord, resetWords } from "../../services/wordService";

export interface WordEntry {
  word: string;
  score: number;
  palindrome: boolean;
  almostPalindrome: boolean;
}

interface WordState {
  entities: WordEntry[];
  totalScore: number;
  loading: "idle" | "pending" | "succeeded" | "failed";
  error: string | null;
}

const initialState: WordState = {
  entities: [],
  totalScore: 0,
  loading: "idle",
  error: null,
};

export const addWordAsync = createAsyncThunk<WordEntry, string>(
  "words/addWord",
  async (word, { rejectWithValue }) => {
    try {
      const response = await addWord(word);
      return response;
    } catch (err: unknown) {
      if (err instanceof Error) {
        const axiosError = err as { response?: { data?: { error?: string } } };
        return rejectWithValue(
          axiosError.response?.data?.error ?? err.message
        );
      }
      return rejectWithValue("Something went wrong");
    }
  }
);

export const fetchWordsAsync = createAsyncThunk<WordEntry[]>(
  "words/fetchAll",
  async () => fetchWords()
);

export const resetWordsAsync = createAsyncThunk("words/reset", async () => resetWords());

const wordSlice = createSlice({
  name: "words",
  initialState,
  reducers: {},
  extraReducers: (builder) => {

    builder.addCase(addWordAsync.fulfilled, (state, action: PayloadAction<WordEntry>) => {
      state.entities.push(action.payload);
      state.totalScore = state.entities.reduce((sum, w) => sum + w.score, 0);
      state.loading = "succeeded";
      state.error = null;
    });

    builder.addCase(fetchWordsAsync.fulfilled, (state, action: PayloadAction<WordEntry[]>) => {
      state.entities = action.payload;
      state.totalScore = action.payload.reduce((sum, w) => sum + w.score, 0);
      state.loading = "succeeded";
    });

    builder.addCase(resetWordsAsync.fulfilled, (state) => {
      state.entities = [];
      state.totalScore = 0;
      state.loading = "succeeded";
      state.error = null;
    });

    builder.addMatcher(
      (action): action is ReturnType<typeof addWordAsync.pending> | ReturnType<typeof fetchWordsAsync.pending> | ReturnType<typeof resetWordsAsync.pending> =>
        action.type.endsWith("/pending"),
      (state) => {
        state.loading = "pending";
        state.error = null;
      }
    );

    builder.addMatcher(
      (action): action is ReturnType<typeof addWordAsync.rejected> | ReturnType<typeof fetchWordsAsync.rejected> | ReturnType<typeof resetWordsAsync.rejected> =>
        action.type.endsWith("/rejected"),
      (state, action) => {
        state.loading = "failed";
        state.error = action.payload as string || action.error?.message || "Something went wrong";
      }
    );
  },
});

export default wordSlice.reducer;
