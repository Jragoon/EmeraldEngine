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
		Light light = new Light(new Vector3f(10000, 10000, 0), new Vector3f(.3f, .3f, .3f));
		Light light1 = new Light(new Vector3f(40, 10, -110), new Vector3f(1, 0, 0), new Vector3f(1, 0.01f, 0.002f));
		Light light2 = new Light(new Vector3f(20, 10, -160), new Vector3f(0, 0, 1), new Vector3f(1, 0.01f, 0.002f));
		Light light3 = new Light(new Vector3f(-20, 10, -120), new Vector3f(0, 1, 0), new Vector3f(1, 0.01f, 0.002f));
		lights.add(light);
		lights.add(light1);
		lights.add(light2);
		lights.add(light3);

		List<Terrain> terrains = new ArrayList<>();
		terrains.add(new Terrain(0, 0, new ModelTexture(Loader.loadTexture("grassFlowers")), "heightmap"));
		terrains.add(new Terrain(-1, -1, new ModelTexture(Loader.loadTexture("grassFlowers")), "heightmap"));
		terrains.add(new Terrain(-1, 0, new ModelTexture(Loader.loadTexture("grassFlowers")), "heightmap"));
		terrains.add(new Terrain(0, -1, new ModelTexture(Loader.loadTexture("grassFlowers")), "heightmap"));
		ModelTexture treeTexture = new ModelTexture(Loader.loadTexture("lowPolyTree"), 1, 1);
		TexturedModel tree = new TexturedModel(OBJLoader.loadModel("lowPolyTree"), treeTexture);
		TexturedModel person = new TexturedModel(OBJLoader.loadModel("person"), new ModelTexture(Loader.loadTexture("player"), 1, 0.4f));
		List<Entity> entities = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < 700; i++) {
			float x = r.nextFloat() * 1600 - 900;
			float z = r.nextFloat() * 1600 - 900;
			float y = getCurrentTerrainHeight(terrains, x, z);
			entities.add(new Entity(tree, new Vector3f(x, y, z), 0,
					r.nextFloat() * 180f, 0f, (r.nextFloat()+1)*3));
		}
		Player player = new Player(person, new Vector3f(0, 0, -120), 0, 45, 0, 1.5f);
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
