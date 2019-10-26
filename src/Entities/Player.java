package Entities;

import Models.TexturedModel;
import Terrains.Terrain;
import org.joml.Vector3f;

import java.util.List;

import static RenderEngine.DisplayManager.getFrameTimeMillis;
import static RenderEngine.DisplayManager.keyIsPressed;
import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {
	private static final float MOVEMENT_SPEED = .1f;
	private static final float TURN_SPEED = .2f;
	private static final float GRAVITY = -.001f;
	private static final float JUMP = .3f;

	private float speed = 0;
	private float turnSpeed = 0;
	private float upwardsSpeed = 0;
	private boolean isJumping = false;

	public Player(TexturedModel model, Vector3f pos, float rotX, float rotY, float rotZ, float scale) {
		super(model, pos, rotX, rotY, rotZ, scale);
	}

	public void move(List<Terrain> terrains) {
		getMovements();
		super.increaseRotation(0, turnSpeed * getFrameTimeMillis(), 0);
		float distance = speed * getFrameTimeMillis();
		float dx = distance * (float) Math.sin(Math.toRadians(super.getRotationY()));
		float dz = distance * (float) Math.cos(Math.toRadians(super.getRotationY()));
		super.increasePosition(dx, 0, dz);
		float terrainHeight = getCurrentTerrainHeight(terrains);
		upwardsSpeed += GRAVITY * getFrameTimeMillis();
		super.increasePosition(0, upwardsSpeed * getFrameTimeMillis(), 0);
		if (super.position.y < terrainHeight) {
			upwardsSpeed = 0;
			super.position.y = terrainHeight;
			isJumping = false;
		}
	}

	private float getCurrentTerrainHeight(List<Terrain> terrains) {
		for (Terrain terrain : terrains) {
			float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
			if (terrainHeight != 999) return terrainHeight;
		}
		return 0;
	}

	private void getMovements() {
		this.speed = 0;
		this.turnSpeed = 0;
		if (keyIsPressed(GLFW_KEY_W)) {
			this.speed += MOVEMENT_SPEED;
		}
		if (keyIsPressed(GLFW_KEY_S)) {
			this.speed -= MOVEMENT_SPEED;
		}
		if (keyIsPressed(GLFW_KEY_A)) {
			this.turnSpeed += TURN_SPEED;
		}
		if (keyIsPressed(GLFW_KEY_D)) {
			this.turnSpeed -= TURN_SPEED;
		}
		if (keyIsPressed(GLFW_KEY_SPACE) && !isJumping) {
			this.upwardsSpeed = JUMP;
			isJumping = true;
		}
	}
}
