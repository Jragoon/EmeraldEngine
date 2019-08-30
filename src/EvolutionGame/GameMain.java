package EvolutionGame;

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

		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		float[] vertices = {
				-.5f, .5f, 0f, -.5f, -.5f, 0f, .5f, -.5f, 0f, 0.5f, 0.5f, 0
		};
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };

		BasicModel model = Loader.loadToVAO(vertices, indices, textureCoords);
		ModelTexture texture = new ModelTexture(Loader.loadTexture("debian"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3f(-1, 0, 0), 0, 0, 0, 1);

		while (!glfwWindowShouldClose(display)) {
			glfwPollEvents();
			renderer.clear();
			shader.start();
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.clean();
		Loader.clean();
		DisplayManager.closeDisplay();
	}
}
