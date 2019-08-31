package Models;

import Textures.ModelTexture;

public class TexturedModel {
	private BasicModel model;
	private ModelTexture texture;

	public TexturedModel(BasicModel model, ModelTexture texture) {
		this.model = model;
		this.texture = texture;
	}

	public BasicModel getModel() {
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}
}
