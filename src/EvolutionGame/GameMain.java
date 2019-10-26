package EvolutionGame;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Entities.Player;
import GUI.GUIRenderer;
import GUI.GUITexture;
import Models.TexturedModel;
import RenderEngine.*;
import Terrains.Terrain;
import Textures.ModelTexture;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class GameMain {
	public static void main(String[] args) {
		long display = DisplayManager.createDisplay();

		List<Light> lights = new ArrayList<>();
		Light light = new Light(new Vector3f(10000, 10000, 0), new Vector3f(1, 1, 1));
		Light light1 = new Light(new Vector3f(200, 10, -150), new Vector3f(1, 0, 0));
		Light light2 = new Light(new Vector3f(100, 10, -100), new Vector3f(0, 0, 1));
		lights.add(light);
		lights.add(light1);
		lights.add(light2);

		List<Terrain> terrains = new ArrayList<>();
		terrains.add(new Terrain(0, 0, new ModelTexture(Loader.loadTexture("rockMoss")), "heightmap"));
		terrains.add(new Terrain(-1, -1, new ModelTexture(Loader.loadTexture("rockMoss")), "heightmap"));
		terrains.add(new Terrain(-1, 0, new ModelTexture(Loader.loadTexture("rockMoss")), "heightmap"));
		terrains.add(new Terrain(0, -1, new ModelTexture(Loader.loadTexture("rockMoss")), "heightmap"));
		ModelTexture mossTexture = new ModelTexture(Loader.loadTexture("rockMoss"), 1, 1);
		TexturedModel snowyTree = new TexturedModel(OBJLoader.loadModel("PineTree1Snowy"), mossTexture);
		TexturedModel dragon = new TexturedModel(OBJLoader.loadModel("dragon"), new ModelTexture(Loader.loadTexture("stall"), 1, 1));
		List<Entity> entities = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < 1000; i++) {
			float x = r.nextFloat() * 1600 - 900;
			float z = r.nextFloat() * 1600 - 900;
			float y = getCurrentTerrainHeight(terrains, x, z);
			entities.add(new Entity(snowyTree, new Vector3f(x, y, z), 0,
					r.nextFloat() * 180f, 0f, (r.nextFloat()+1)*5));
		}
		Player player = new Player(dragon, new Vector3f(0, 0, -120), 0, 45, 0, 1.5f);
		Camera camera = new Camera(player);
		List<GUITexture> interfaces = new ArrayList<>();
		GUITexture gui = new GUITexture(Loader.loadTexture("stall"), new Vector2f(.5f, .5f), new Vector2f(0.25f, 0.25f));
		interfaces.add(gui);
		GUIRenderer interfaceRenderer = new GUIRenderer();

		MasterRenderer renderer = new MasterRenderer();
		while (!glfwWindowShouldClose(display)) {
			player.move(terrains);
			renderer.addEntity(player);
			for (Entity entity : entities) renderer.addEntity(entity);
			for (Terrain terrain : terrains) renderer.addTerrain(terrain);
			renderer.render(lights, camera);
			interfaceRenderer.render(interfaces);
			DisplayManager.updateDisplay();
		}

		interfaceRenderer.clean();
		renderer.clean();
		Loader.clean();
		DisplayManager.closeDisplay();
	}

	private static float getCurrentTerrainHeight(List<Terrain> terrains, float x, float z) {
		for (Terrain terrain : terrains) {
			float terrainHeight = terrain.getHeightOfTerrain(x, z);
			if (terrainHeight != 999) return terrainHeight;
		}
		return 0;
	}
}
