package in.sagargaud.ghibliapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.sagargaud.ghibliapi.DTO.TextGenerationRequestDTO;
import in.sagargaud.ghibliapi.service.GhibliArtService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
@RequiredArgsConstructor
public class GenerationController {

	private final GhibliArtService ghibliArtService;

	@Autowired
	public GenerationController(GhibliArtService ghibliArtServices) {
		this.ghibliArtService = ghibliArtServices;
	}// singular name to match service

	@PostMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> generateGhibliArt(@RequestParam("image") MultipartFile image,
			@RequestParam("prompt") String prompt) {
		try {
			byte[] imageBytes = ghibliArtService.createGhibliArt(image, prompt);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping(value = "/generate-from-text", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> generateGhibliArtFromText(@RequestBody TextGenerationRequestDTO request) {
		try {
			byte[] imageBytes = ghibliArtService.createGhibliArtFromText(request.getPrompt(), request.getStyle());
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
}