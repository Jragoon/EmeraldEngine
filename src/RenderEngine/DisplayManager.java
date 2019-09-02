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
	private static long window;

	private static long lastFrameTime;
	private static float delta = 0;

	public static long createDisplay() {
		if (!glfwInit())
			throw new IllegalStateException("Could not initialize GLFW.");

		window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create GLFW window.");

		initializeGL(window);
		glClearColor(0.70f, 0.80f, 1.0f, 0.0f);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_DEPTH_TEST);
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();

		return window;
	}

	public static void updateDisplay() {
		glfwPollEvents();
		glfwSwapBuffers(window);
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime);
		lastFrameTime = currentFrameTime;
	}

	public static void closeDisplay() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	public static float getFrameTimeMillis() {
		return delta;
	}

	private static void initializeGL(long window) {
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glfwSwapInterval(1);
	}

	private static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public static boolean keyIsPressed(int key) {
		return glfwGetKey(window, key) == 1;
	}
}
