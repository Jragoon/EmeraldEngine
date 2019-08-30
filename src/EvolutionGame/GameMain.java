package EvolutionGame;

import RenderEngine.*;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class GameMain {
    public static void main(String[] args) {
        long display = DisplayManager.createDisplay();

        while (!glfwWindowShouldClose(display)) {
            glfwPollEvents();
            //render game world objects, terrains, etc.
            //renderer.render()
            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();
    }
}
