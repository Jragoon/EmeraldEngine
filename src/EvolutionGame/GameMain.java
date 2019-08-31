package EvolutionGame;

import Entities.Camera;
import Entities.Entity;
import Models.BasicModel;
import Models.TexturedModel;
import RenderEngine.*;
import Shaders.StaticShader;
import Textures.ModelTexture;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class GameMain {
	public static void main(String[] args) {
		long display = DisplayManager.createDisplay();

		Camera camera = new Camera();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		float[] vertices = {
				-.5f, .5f, 0f, -.5f, -.5f, 0f, .5f, -.5f, 0f, 0.5f, 0.5f, 0
		};
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };

		BasicModel model = Loader.loadToVAO(vertices, indices, textureCoords);
		ModelTexture texture = new ModelTexture(Loader.loadTexture("debian"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);

		while (!glfwWindowShouldClose(display)) {
			entity.increasePosition(0f, 0, 0f);
			entity.increaseRotation(0f, 1f, 1f);
			glfwPollEvents();
			camera.move();
			renderer.clear();
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.clean();
		Loader.clean();
		DisplayManager.closeDisplay();
	}
}
