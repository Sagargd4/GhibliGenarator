import { useState } from "react";
import TextToImageForm from "./Components/TextToImage";
import ImageToImageForm from "./Components/ImageToImage";

export default function App() {
  const [imageUrl, setImageUrl] = useState("");
  const [activeTab, setActiveTab] = useState("text");

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-100 to-purple-200 p-6">
      <header className="flex justify-between items-center mb-10">
        <h1 className="text-4xl font-bold text-purple-800">🌟 Ghibli AI Generator</h1>
        {/* Dark/light toggle can be added here */}
      </header>

      <div className="max-w-2xl mx-auto">
        <div className="flex justify-center space-x-4 mb-6">
          <button
            onClick={() => setActiveTab("text")}
            className={`px-4 py-2 rounded-lg font-medium ${activeTab === "text" ? "bg-purple-500 text-white" : "bg-white text-purple-600 border"
              }`}
          >
            Text to Image
          </button>
          <button
            onClick={() => setActiveTab("image")}
            className={`px-4 py-2 rounded-lg font-medium ${activeTab === "image" ? "bg-purple-500 text-white" : "bg-white text-purple-600 border"
              }`}
          >
            Image to Image
          </button>
        </div>

        {activeTab === "text" ? (
          <TextToImageForm onGenerate={setImageUrl} />
        ) : (
          <ImageToImageForm onGenerate={setImageUrl} />
        )}

        {imageUrl && (
          <div className="mt-10 text-center">
            <h2 className="text-2xl font-semibold text-gray-800 mb-4">🖼️ Result</h2>
            <img src={imageUrl} alt="Generated" className="rounded-lg shadow-xl max-w-full max-h-[500px] mx-auto" />
          </div>
        )}
      </div>
    </div>
  );
}
