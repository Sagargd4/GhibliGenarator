import { useState } from "react";

export default function TextToImageForm({ onGenerate }) {
  const [prompt, setPrompt] = useState("");
  const [style, setStyle] = useState("anime");
  const [loading, setLoading] = useState(false);
  const [size, setSize] = useState("512x512");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    const res = await fetch("http://localhost:8080/api/v1/generate-from-text", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ prompt, style, size }),
    });

    const blob = await res.blob();
    setLoading(false);
    onGenerate(URL.createObjectURL(blob));
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white rounded-xl shadow-md p-6 space-y-4">
      <h2 className="text-2xl font-semibold text-purple-800">🎨 Text to Image</h2>

      <textarea
        rows="4"
        placeholder="Describe your fantasy scene in detail..."
        className="w-full border px-4 py-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-400"
        value={prompt}
        onChange={(e) => setPrompt(e.target.value)}
        required
      />

      <div className="flex space-x-4">
        <select
          value={style}
          onChange={(e) => setStyle(e.target.value)}
          className="w-1/2 border px-3 py-2 rounded focus:outline-none focus:ring-2 focus:ring-purple-400"
        >
          <option value="anime">Anime</option>
          <option value="fantasy">Fantasy</option>
          <option value="ghibli">Ghibli</option>
          <option value="realistic">Realistic</option>
        </select>
        <select
          value={size}
          onChange={(e) => setSize(e.target.value)}
          className="w-1/2 border px-3 py-2 rounded focus:outline-none focus:ring-2 focus:ring-purple-400"
        >
          <option value="512x512">512x512</option>
          <option value="768x768">768x768</option>
          <option value="1024x1024">1024x1024</option>
        </select>
      </div>

      <button
        type="submit"
        className="w-full flex justify-center items-center bg-gradient-to-r from-purple-600 to-pink-500 text-white py-2 rounded hover:opacity-90"
      >
        {loading ? (
          <svg className="animate-spin h-5 w-5 mr-2 text-white" viewBox="0 0 24 24">
            <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
            <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z" />
          </svg>
        ) : null}
        {loading ? "Generating..." : "Generate Image"}
      </button>
    </form>
  );
}