package Entities;

import org.joml.Vector3f;

public class Camera {
	private static final float MOVEMENT_SPEED = 1.05f;
	private Vector3f position = new Vector3f(0, 50, 0);
	private float pitch; /* High / low*/
	private float yaw; /* Left / right */
	private float roll; /* Tilted to one side */

	public void move() {

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
