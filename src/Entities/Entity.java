package Entities;

import Models.TexturedModel;
import org.joml.Vector3f;

/* Instance of a textured model that we give to the renderer */
public class Entity {
	private TexturedModel model;
	protected Vector3f position;
	private float rotationX, rotationY, rotationZ;
	private float scale;

	public Entity(TexturedModel model, Vector3f position, float rotationX, float rotationY,
				  float rotationZ, float scale) {
		this.model = model;
		this.position = position;
		this.rotationX = rotationX;
		this.rotationY = rotationY;
		this.rotationZ = rotationZ;
		this.scale = scale;
	}

	public void increasePosition(float distanceX, float distanceY, float distanceZ) {
		this.position.x += distanceX;
		this.position.y += distanceY;
		this.position.z += distanceZ;
	}

	public void increaseRotation(float degreeX, float degreeY, float degreeZ) {
		this.rotationX += degreeX;
		this.rotationY += degreeY;
		this.rotationZ += degreeZ;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotationX() {
		return rotationX;
	}

	public void setRotationX(float rotationX) {
		this.rotationX = rotationX;
	}

	public float getRotationY() {
		return rotationY;
	}

	public void setRotationY(float rotationY) {
		this.rotationY = rotationY;
	}

	public float getRotationZ() {
		return rotationZ;
	}

	public void setRotationZ(float rotationZ) {
		this.rotationZ = rotationZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
