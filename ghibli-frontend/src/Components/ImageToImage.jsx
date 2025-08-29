import { useState } from "react";

export default function ImageToImageForm({ onGenerate }) {
  const [image, setImage] = useState(null);
  const [prompt, setPrompt] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!image || !prompt) return;
    setLoading(true);

    const formData = new FormData();
    formData.append("image", image);
    formData.append("prompt", prompt);

    const res = await fetch("http://localhost:8080/api/v1/generate", {
      method: "POST",
      body: formData,
    });

    const blob = await res.blob();
    setLoading(false);
    onGenerate(URL.createObjectURL(blob));
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 space-y-4 transition">
      <h2 className="text-xl font-semibold text-gray-800 dark:text-white">Image to Image</h2>
      <label className="block w-full text-sm text-white dark:bg-gray-800 bg-gray-100 p-2 rounded-lg cursor-pointer">
        {image ? image.name : "Choose an image having Size 1024x1024, 1152x896, 1216x832, 1344x768, 1536x640,"}
      </label>
      <input
        type="file"
        accept="image/*"

        onChange={(e) => setImage(e.target.files[0])}
        className="block w-full text-sm text-gray-500 dark:text-white file:mr-4 file:py-2 file:px-4
        file:rounded-full file:border-0 file:text-sm file:font-semibold
        file:bg-purple-50 dark:file:bg-gray-700 file:text-purple-700 hover:file:bg-purple-100"
      />


      <input
        type="text"
        placeholder="Modify image with magical prompt..."
        value={prompt}
        onChange={(e) => setPrompt(e.target.value)}
        className="w-full border px-4 py-2 rounded focus:outline-none focus:ring-2 focus:ring-teal-400 dark:bg-gray-700 dark:text-white"
        required
      />

      <button
        type="submit"
        disabled={loading}
        className="w-full bg-gradient-to-r from-green-500 to-teal-500 text-white py-2 rounded hover:opacity-90 flex justify-center"
      >
        {loading ? (
          <span className="loader border-t-2 border-white w-5 h-5 rounded-full animate-spin"></span>
        ) : (
          "Generate Modified Image"
        )}
      </button>
    </form>
  );
}