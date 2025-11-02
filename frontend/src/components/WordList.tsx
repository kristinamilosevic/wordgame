import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchWordsAsync, resetWordsAsync } from "../features/words/wordSlice";
import type { RootState, AppDispatch } from "../app/store";

export const WordList: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>(); 
  const words = useSelector((state: RootState) => state.words.entities);
  const loading = useSelector((state: RootState) => state.words.loading);
  const totalScore = useSelector((state: RootState) => state.words.totalScore);
  const [highlightedWord, setHighlightedWord] = useState<string | null>(null);

  useEffect(() => {
    dispatch(fetchWordsAsync());
  }, [dispatch]);

  useEffect(() => {
    if (words.length > 0) {
      setHighlightedWord(words[words.length - 1].word);
    }
  }, [words]);

  const handleReset = () => {
    dispatch(resetWordsAsync());
    setHighlightedWord(null);
  };

  const sortedWords = [...words].sort((a, b) => b.score - a.score);

  if (loading === "pending")
    return <p className="mt-6 text-center text-gray-500 italic">Loading words...</p>;

  if (words.length === 0)
    return <p className="mt-6 text-center text-gray-400 italic">No words added yet.</p>;

  const calculateBaseScore = (word: string) => {
    const uniqueLetters = new Set(word.toLowerCase().split(""));
    return uniqueLetters.size;
  };

  return (
    <div className="mt-10">
      <h2 className="text-2xl font-bold mb-4 text-center text-gray-700">Word Scores</h2>
      <div className="overflow-x-auto">
        <table className="min-w-full bg-white shadow-xl rounded-2xl overflow-hidden">
          <thead className="bg-cyan-600 text-white">
            <tr>
              <th className="py-3 px-4 text-left font-semibold uppercase tracking-wide">Word</th>
              <th className="py-3 px-4 text-center font-semibold uppercase tracking-wide">Unique Letters</th>
              <th className="py-3 px-4 text-center font-semibold uppercase tracking-wide">Extra Points</th>
              <th className="py-3 px-4 text-center font-semibold uppercase tracking-wide">Total Points</th>
            </tr>
          </thead>
          <tbody>
            {sortedWords.map((w) => {
              const base = calculateBaseScore(w.word);
              const extra = w.palindrome ? 3 : w.almostPalindrome ? 2 : 0;
              return (
                <tr
                  key={w.word}
                  className={`border-b transition-colors ${
                    w.word === highlightedWord ? "bg-cyan-50" : "bg-white"
                  } hover:bg-cyan-50`}
                >
                  <td className="py-3 px-4 font-medium text-gray-800">{w.word}</td>
                  <td className="py-3 px-4 text-center text-gray-600">{base}</td>
                  <td
                    className={`py-3 px-4 text-center font-semibold ${
                      w.palindrome ? "text-cyan-600" : w.almostPalindrome ? "text-cyan-400" : "text-gray-400"
                    }`}
                  >
                    {extra > 0 ? `+${extra}` : "-"}
                  </td>
                  <td className="py-3 px-4 text-center font-bold text-gray-800">{w.score}</td>
                </tr>
              );
            })}
            <tr className="bg-gray-200 font-bold text-gray-900">
              <td className="py-3 px-4 text-left">Total</td>
              <td className="py-3 px-4 text-center"></td>
              <td className="py-3 px-4 text-center"></td>
              <td className="py-3 px-4 text-center">{totalScore}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div className="flex justify-center mt-4 gap-2">
        <button
          onClick={handleReset}
          className="px-6 py-2 rounded-xl bg-cyan-600 text-white font-semibold shadow-md hover:bg-cyan-700 active:scale-95 transition-transform duration-150"
        >
          Reset Game
        </button>
      </div>
    </div>
  );
};
