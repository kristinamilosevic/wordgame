import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { addWordAsync } from "../features/words/wordSlice";
import type { RootState, AppDispatch } from "../app/store";

export const WordForm: React.FC = () => {
  const [word, setWord] = useState("");
  const dispatch = useDispatch<AppDispatch>(); 
  const error = useSelector((state: RootState) => state.words.error);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!word.trim()) return;
    dispatch(addWordAsync(word)); 
    setWord("");
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="flex flex-col sm:flex-row gap-3 mt-8 max-w-lg mx-auto bg-white p-5 rounded-2xl shadow-lg border"
    >
      <div className="flex-1 flex flex-col">
        <input
          type="text"
          value={word}
          onChange={(e) => setWord(e.target.value)}
          placeholder="Enter a new word..."
          className="px-4 py-2 rounded-xl border-2 border-cyan-300 focus:outline-none focus:ring-2 focus:ring-cyan-400 placeholder-gray-400 text-gray-800 transition-all w-full"
        />
        {error && (
          <span className="text-red-500 mt-1 text-sm self-center text-center">
            {error}
          </span>
        )}
      </div>

      <button
        type="submit"
        className="px-6 py-2 rounded-xl bg-cyan-600 text-white font-semibold shadow-md hover:bg-cyan-700 active:scale-95 transition-transform duration-150"
      >
        Add
      </button>
    </form>
  );
};
