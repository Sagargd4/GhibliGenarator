package in.sagargaud.ghibliapi.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.sagargaud.ghibliapi.Client.StabilityAIClient;
import in.sagargaud.ghibliapi.DTO.TextToImageRequest;

@Service
public class GhibliArtService {

	private final StabilityAIClient stabilityAIClient;
	private final String apiKey;

	public GhibliArtService(StabilityAIClient stabilityAIClient, @Value("${stability.api.key}") String apiKey) {
		this.stabilityAIClient = stabilityAIClient;
		this.apiKey = apiKey;
	}

	// Allowed SDXL dimensions
	private final List<int[]> allowedSizes = Arrays.asList(new int[] { 1024, 1024 }, new int[] { 1152, 896 },
			new int[] { 1216, 832 }, new int[] { 1344, 768 }, new int[] { 1536, 640 }, new int[] { 640, 1536 },
			new int[] { 768, 1344 }, new int[] { 832, 1216 }, new int[] { 896, 1152 });

	// IMAGE TO IMAGE
	public byte[] createGhibliArt(MultipartFile image, String prompt) throws IOException {
		BufferedImage originalImage = ImageIO.read(image.getInputStream());

		// Resize to nearest allowed SDXL dimension
		int[] targetSize = getNearestAllowedSize(originalImage.getWidth(), originalImage.getHeight());
		BufferedImage resizedImage = resizeImage(originalImage, targetSize[0], targetSize[1]);

		// Convert resized image to byte array (if needed)
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizedImage, "png", baos);
		byte[] imageBytes = baos.toByteArray();

		String finalPrompt = prompt + ", in the beautiful, detailed anime style of studio ghibli.";
		String engineId = "stable-diffusion-xl-1024-v1-0";
		String stylePreset = "anime";

		// Call Stability AI
		return stabilityAIClient.generateImageFromImage("Bearer " + apiKey, engineId, image, // you can also send
																								// resized image bytes
																								// depending on client
																								// method
				finalPrompt, stylePreset);
	}

	// TEXT TO IMAGE
	public byte[] createGhibliArtFromText(String prompt, String style) {
		String finalPrompt = prompt + ", in the beautiful, detailed anime style of studio ghibli.";
		String engineId = "stable-diffusion-xl-1024-v1-0";
		String stylePreset = style.equals("general") ? "anime" : style.replace("_", "_");

		TextToImageRequest requestPayload = new TextToImageRequest(finalPrompt, stylePreset);
		return stabilityAIClient.generateImageFromText("Bearer " + apiKey, engineId, requestPayload);
	}

	// Helper: get nearest allowed dimension
	private int[] getNearestAllowedSize(int width, int height) {
		double targetRatio = (double) width / height;
		int[] nearest = allowedSizes.get(0);
		double minDiff = Math.abs(targetRatio - (double) nearest[0] / nearest[1]);

		for (int[] size : allowedSizes) {
			double ratio = (double) size[0] / size[1];
			double diff = Math.abs(targetRatio - ratio);
			if (diff < minDiff) {
				minDiff = diff;
				nearest = size;
			}
		}
		return nearest;
	}

	// Helper: resize image
	private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resizedImage.createGraphics();
		graphics.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		graphics.dispose();
		return resizedImage;
	}
}
