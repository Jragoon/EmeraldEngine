package RenderEngine;

import Models.BasicModel;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Loader {
	private static final int TRIANGLE = 3;

	private static List<Integer> vaos = new ArrayList<>();
	private static List<Integer> vbos = new ArrayList<>();

	public static BasicModel loadToVAO(float[] positions, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataAsVBO(0, positions);
		unbindVAO();
		return new BasicModel(vaoID, indices.length);
	}

	private static int createVAO() {
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}

	private static void unbindVAO() {
		glBindVertexArray(0);
	}

	private static void storeDataAsVBO(int indexInVAO, float[] data) {
		generateArrayVBO();
		FloatBuffer buffer = storeInBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(indexInVAO, TRIANGLE, GL_FLOAT, false, 0, 0);
		unbindVBO();
	}

	private static void bindIndicesBuffer(int[] indices) {
		generateElementArrayVBO();
		IntBuffer buffer = storeInBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	private static void generateArrayVBO() {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
	}

	private static void generateElementArrayVBO() {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
	}

	private static void unbindVBO() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private static IntBuffer storeInBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip(); /* Finished writing, prepare to be read from */
		return buffer;
	}

	private static FloatBuffer storeInBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip(); /* Finished writing, prepare to be read from */
		return buffer;
	}

	public static void clean() {
		for (Integer vao : vaos) glDeleteVertexArrays(vao);
		for (Integer vbo : vbos) glDeleteBuffers(vbo);
	}
}
