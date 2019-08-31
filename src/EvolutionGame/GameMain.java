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


		BasicModel model = OBJLoader.loadModel("stall");
		ModelTexture texture = new ModelTexture(Loader.loadTexture("stall"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -20), 0, 0, 0, 1);

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
