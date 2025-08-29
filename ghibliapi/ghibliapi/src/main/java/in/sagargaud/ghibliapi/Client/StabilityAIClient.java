package in.sagargaud.ghibliapi.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import in.sagargaud.ghibliapi.DTO.TextToImageRequest;

@FeignClient(
		name="stabilityAIClient",
		url="${stability.api.base-url}",
		configuration= in.sagargaud.ghibliapi.config.FeignConfig.class
		)

public interface StabilityAIClient {

	@PostMapping(
	value="/v1/generation/{engine_id}/text-to-image",
	consumes=MediaType.APPLICATION_JSON_VALUE,
	headers= {"Accept=image/png"}
	)
	
	byte[] generateImageFromText(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable("engine_id") String engineId,
			@RequestBody TextToImageRequest requestBoby
			);
	@PostMapping(
			value="/v1/generation/{engine_id}/image-to-image",
			consumes=MediaType.MULTIPART_FORM_DATA_VALUE,
			headers= {"Accept=image/png"}
			)
	
	byte[] generateImageFromImage(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable("engine_id") String engineId,
			@RequestPart("init_image") MultipartFile initImage,
			@RequestPart("text_prompts[0][text]") String textPrompt,
			@RequestPart("style_preset")String stylePreset
			);
	
}