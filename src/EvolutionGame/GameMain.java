package EvolutionGame;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.BasicModel;
import Models.TexturedModel;
import RenderEngine.*;
import Terrains.Terrain;
import Textures.ModelTexture;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class GameMain {
	public static void main(String[] args) {
		long display = DisplayManager.createDisplay();

		Camera camera = new Camera();
		Light light = new Light(new Vector3f(0, 1000, 0), new Vector3f(1, 1, 1));

		BasicModel model = OBJLoader.loadModel("dragon");
		ModelTexture texture = new ModelTexture(Loader.loadTexture("stall"), 10, 1);
		TexturedModel texturedModel = new TexturedModel(model, texture);
		List<Entity> entities = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < 200; i++) {
			float x = r.nextFloat() * 100 - 50;
			float y = r.nextFloat() * 100 - 50;
			float z = r.nextFloat() * -300;
			entities.add(new Entity(texturedModel, new Vector3f(x, y, z), r.nextFloat() * 180f,
							r.nextFloat() * 180f, 0f, 1f));
		}
		List<Terrain> terrains = new ArrayList<>();
		terrains.add(new Terrain(0, 0, new ModelTexture(Loader.loadTexture("stall"))));
		terrains.add(new Terrain(-1, -1, new ModelTexture(Loader.loadTexture("stall"))));
		terrains.add(new Terrain(-1, 0, new ModelTexture(Loader.loadTexture("stall"))));
		terrains.add(new Terrain(0, -1, new ModelTexture(Loader.loadTexture("stall"))));

		MasterRenderer renderer = new MasterRenderer();
		while (!glfwWindowShouldClose(display)) {
			renderer.render(light, camera);
			for (Entity entity : entities) renderer.addEntity(entity);
			for (Terrain terrain : terrains) renderer.addTerrain(terrain);
			DisplayManager.updateDisplay();
		}

		renderer.clean();
		Loader.clean();
		DisplayManager.closeDisplay();
	}
}
