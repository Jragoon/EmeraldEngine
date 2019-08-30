package EvolutionGame;

import Models.BasicModel;
import RenderEngine.*;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class GameMain {
    public static void main(String[] args) {
        long display = DisplayManager.createDisplay();

        Renderer renderer = new Renderer();
        float[] vertices = {
                -.5f, .5f, 0f, -.5f, -.5f, 0f, .5f, -.5f, 0f,
                0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f, -0.5f, 0.5f, 0f
        };
        BasicModel model = Loader.loadToVAO(vertices);

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
