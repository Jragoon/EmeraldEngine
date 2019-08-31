package RenderEngine;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayManager {
	static final int WIDTH = 1280;
	static final int HEIGHT = 720;
	private static final String TITLE = "Emerald Engine";
	public static long window;

	public static long createDisplay() {
		if (!glfwInit())
			throw new IllegalStateException("Could not initialize GLFW.");

		window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create GLFW window.");

		initializeGL(window);
		glClearColor(0.1f, 0.4f, 0.1f, 0.0f);
		glEnable(GL_DEPTH_TEST);
		GL11.glViewport(0, 0, WIDTH, HEIGHT);

		return window;
	}

	public static void updateDisplay() {
		glfwSwapBuffers(window);
	}

	public static void closeDisplay() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	private static void initializeGL(long window) {
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glfwSwapInterval(1);
	}

	public static boolean keyIsPressed(int key) {
		return glfwGetKey(window, key) == 1;
	}
}
