package Entities;

import org.joml.Vector3f;

import static RenderEngine.DisplayManager.keyIsPressed;
import static org.lwjgl.glfw.GLFW.*;

public class Camera {
	private static final float MOVEMENT_SPEED = 1.05f;
	private Vector3f position = new Vector3f(0, 5, 0);
	private float pitch; /* High / low*/
	private float yaw; /* Left / right */
	private float roll; /* Tilted to one side */

	public void move() {
		if (keyIsPressed(GLFW_KEY_W)) {
			position.z -= MOVEMENT_SPEED;
		}
		if (keyIsPressed(GLFW_KEY_S)) {
			position.z += MOVEMENT_SPEED;
		}
		if (keyIsPressed(GLFW_KEY_A)) {
			position.x -= MOVEMENT_SPEED;
		}
		if (keyIsPressed(GLFW_KEY_D)) {
			position.x += MOVEMENT_SPEED;
		}
		if (keyIsPressed(GLFW_KEY_SPACE)) {
			position.y += MOVEMENT_SPEED;
		}
		if (keyIsPressed(GLFW_KEY_LEFT_ALT)) {
			position.y -= MOVEMENT_SPEED;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}


}
