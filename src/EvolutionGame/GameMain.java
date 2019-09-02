package EvolutionGame;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Entities.Player;
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
		Light light = new Light(new Vector3f(10000, 10000, 0), new Vector3f(1, 1, 1));

		List<Terrain> terrains = new ArrayList<>();
		terrains.add(new Terrain(0, 0, new ModelTexture(Loader.loadTexture("dirtSnow"))));
		terrains.add(new Terrain(-1, -1, new ModelTexture(Loader.loadTexture("dirtSnow"))));
		terrains.add(new Terrain(-1, 0, new ModelTexture(Loader.loadTexture("dirtSnow"))));
		terrains.add(new Terrain(0, -1, new ModelTexture(Loader.loadTexture("dirtSnow"))));
		ModelTexture snowTexture = new ModelTexture(Loader.loadTexture("AlienTree"), 1, 1);
		TexturedModel snowyTree = new TexturedModel(OBJLoader.loadModel("PineTree1Snowy"), snowTexture);
		TexturedModel dragon = new TexturedModel(OBJLoader.loadModel("dragon"), snowTexture);
		List<Entity> entities = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < 10000; i++) {
			float x = r.nextFloat() * 1600 - 900;
			float z = r.nextFloat() * 1600 - 900;
			entities.add(new Entity(snowyTree, new Vector3f(x, 0, z), 0,
					r.nextFloat() * 180f, 0f, r.nextFloat()*5));
		}
		Player player = new Player(dragon, new Vector3f(0, 0, -120), 0, 0, 0, 7);

		MasterRenderer renderer = new MasterRenderer();
		while (!glfwWindowShouldClose(display)) {
			player.move();
			renderer.addEntity(player);
			for (Entity entity : entities) renderer.addEntity(entity);
			for (Terrain terrain : terrains) renderer.addTerrain(terrain);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.clean();
		Loader.clean();
		DisplayManager.closeDisplay();
	}
}
