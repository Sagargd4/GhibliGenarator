package in.sagargaud.ghibliapi.DTO;

import lombok.Data;

@Data
public class TextGenerationRequestDTO {

	public String prompt;

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	private String style;
}
