package EvolutionGame;

import Models.BasicModel;
import RenderEngine.*;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class GameMain {
	public static void main(String[] args) {
		long display = DisplayManager.createDisplay();

		Renderer renderer = new Renderer();
		float[] vertices = {
				-.5f, .5f, 0f, -.5f, -.5f, 0f, .5f, -.5f, 0f, 0.5f, 0.5f, 0
		};
		int[] indices = { 0, 1, 3, 3, 1, 2 };

		BasicModel model = Loader.loadToVAO(vertices, indices);

		while (!glfwWindowShouldClose(display)) {
			glfwPollEvents();
			renderer.clear();
			//render game world objects, terrains, etc.
			renderer.render(model);
			DisplayManager.updateDisplay();
		}

		Loader.clean();
		DisplayManager.closeDisplay();
	}
}
