package Entities;

import RenderEngine.DisplayManager;
import org.joml.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(0, 50, 0);
	private float distanceFromPlayer = 0;
	private float angleAroundPlayer = 0;
	private Player player;
	private float mouseY;
	private float mouseX;
	private float pitch; /* High / low*/
	private float yaw; /* Left / right */
	private float roll; /* Tilted to one side */

	public Camera(Player player) {
		this.player = player;
		this.mouseX = (float) DisplayManager.getMouseX();
		this.mouseY = (float) DisplayManager.getMouseY();
	}

	public void move() {
		calculateZoom();
		calculatePitchAndAngle();
		float horizontalDistance = calcHorizontalDistance();
		float verticalDistance = calcVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotationY() + angleAroundPlayer);
		mouseX = (float) DisplayManager.getMouseX();
		mouseY = (float) DisplayManager.getMouseY();
		DisplayManager.mouseWheelVelocity = 0;
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

	private void calculateCameraPosition(float horizontal, float vertical) {
		float theta = player.getRotationY() + angleAroundPlayer;
		float offsetX = (float) (horizontal * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontal * Math.cos(Math.toRadians(theta)));
		position.y = player.getPosition().y + vertical + 20;
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
	}

	private float calcHorizontalDistance() {
		return (float) ((distanceFromPlayer + 20) * Math.cos(Math.toRadians(pitch)));
	}

	private float calcVerticalDistance() {
		return (float) ((distanceFromPlayer + 20) * Math.sin(Math.toRadians(pitch)));
	}

	private void calculateZoom() {
		distanceFromPlayer -= DisplayManager.mouseWheelVelocity * 75f;
	}

	private void calculatePitchAndAngle() {
		if (DisplayManager.rightClick) {
			float pitchChange = mouseDY() * 0.1f;
			pitch -= pitchChange;
			float angleChange = mouseDX();
			angleAroundPlayer -= angleChange * 0.1f;
		}
	}

	private float mouseDX() {
		return mouseX - (float) DisplayManager.getMouseX();
	}

	private float mouseDY() {
		return mouseY - (float) DisplayManager.getMouseY();
	}
}
