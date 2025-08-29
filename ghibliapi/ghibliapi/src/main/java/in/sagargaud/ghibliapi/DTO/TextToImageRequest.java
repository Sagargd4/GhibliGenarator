package in.sagargaud.ghibliapi.DTO;

import java.util.List;

public class TextToImageRequest {

	private List<TextPrompt> text_prompts;
	private double cfg_scale = 7;
	private int height = 1024;
	private int width = 1024;
	private int samples = 1;
	private int steps = 30;

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	private String style_preset;

	public static class TextPrompt {
		private String text;

		private TextPrompt(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	public TextToImageRequest(String text, String style) {
		this.text_prompts = List.of(new TextPrompt(text));
		this.style_preset = style;
	}

	public List<TextPrompt> getText_prompts() {
		return text_prompts;
	}

	public double getCfg_scale() {
		return cfg_scale;
	}

	public void setCfg_scale(double cfg_scale) {
		this.cfg_scale = cfg_scale;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getSamples() {
		return samples;
	}

	public void setSamples(int samples) {
		this.samples = samples;
	}

	public String getStyle_preset() {
		return style_preset;
	}

	public void setStyle_preset(String style_preset) {
		this.style_preset = style_preset;
	}

	public void setText_prompts(List<TextPrompt> text_prompts) {
		this.text_prompts = text_prompts;
	}

}