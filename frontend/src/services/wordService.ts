import axios from "axios";
import type { WordEntry } from "../features/words/wordSlice";

const API_URL = "http://localhost:8080/api/words";

export const addWord = async (word: string): Promise<WordEntry> => {
  const response = await axios.post<WordEntry>(API_URL, { word });
  return response.data;
};

export const fetchWords = async (): Promise<WordEntry[]> => {
  const response = await axios.get<WordEntry[]>(API_URL);
  return response.data;
};

export const fetchTotalScore = async (): Promise<number> => {
  const response = await axios.get<number>(`${API_URL}/total-score`);
  return response.data;
};

export const resetWords = async (): Promise<void> => {
  await axios.delete(API_URL);
};