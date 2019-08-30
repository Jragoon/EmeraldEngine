package EvolutionGame;

import Models.BasicModel;
import RenderEngine.*;
import Shaders.StaticShader;

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

		BasicModel model = Loader.loadToVAO(vertices, indices);

		while (!glfwWindowShouldClose(display)) {
			glfwPollEvents();
			renderer.clear();
			shader.start();
			renderer.render(model);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.clean();
		Loader.clean();
		DisplayManager.closeDisplay();
	}
}
