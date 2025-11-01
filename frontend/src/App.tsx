import React from "react";
import { WordForm } from "./components/WordForm";
import { WordList } from "./components/WordList";

function App() {
  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h1 className="text-3xl font-bold text-center mb-6">Word Game</h1>
      <WordForm />
      <WordList />
    </div>
  );
}

export default App;
