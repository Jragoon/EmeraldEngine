package Textures;

public class ModelTexture {
	private int textureID;
	private float shineDamper = 1; /* Visibility for specular lighting */
	private float reflectivity = 0;

	public ModelTexture(int id) {
		this.textureID = id;
	}

	public ModelTexture(int id, float shineDamper, float reflectivity) {
		this.textureID = id;
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
	}

	public int getTextureID() {
		return this.textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
}
