package RenderEngine;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.TexturedModel;
import Shaders.EntityShader;
import Shaders.ShaderProgram;
import Shaders.TerrainShader;
import Terrains.Terrain;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static RenderEngine.DisplayManager.HEIGHT;
import static RenderEngine.DisplayManager.WIDTH;
import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer {
	private static final float FOV = 70f;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 10000f;

	private Matrix4f projectionMatrix;
	private EntityShader entityShader = new EntityShader();
	private TerrainShader terrainShader = new TerrainShader();
	private HashMap<TexturedModel, List<Entity>> entities = new HashMap<>();
	private List<Terrain> terrains = new ArrayList<>();
	private EntityRenderer entityRenderer;
	private TerrainRenderer terrainRenderer;

	public MasterRenderer() {
		createProjectionMatrix();
		this.entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
		this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
	}

	public void render(Light sun, Camera camera) {
		clear();
		camera.move();
		startShader(entityShader, sun, camera);
		entityRenderer.render(entities);
		stopShader(entityShader);
		startShader(terrainShader, sun, camera);
		terrainRenderer.render(terrains);
		stopShader(terrainShader);
		clearGameObjects();
	}

	private void startShader(ShaderProgram shader, Light light, Camera camera) {
		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
	}

	private void stopShader(ShaderProgram shader) {
		shader.stop();
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void addEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		boolean batchExistsForThisObject = batch != null;
		if (batchExistsForThisObject) {
			batch.add(entity);
		}
		else {
			List<Entity> newBatch = new ArrayList<>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}

	public void addTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	private void createProjectionMatrix() {
		float aspectRatio = WIDTH / HEIGHT;
		float y_scale = (1f / (float) Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio;
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00(x_scale);
		projectionMatrix.m11(y_scale);
		projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
		projectionMatrix.m23(-1);
		projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
		projectionMatrix.m33(0);
	}

	private void clearGameObjects() {
		entities.clear();
		terrains.clear();
	}

	public void clean() {
		entityShader.clean();
		terrainShader.clean();
	}
}
