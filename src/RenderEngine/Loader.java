package RenderEngine;

import Models.BasicModel;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Loader {
	private static List<Integer> vaos = new ArrayList<>();
	private static List<Integer> vbos = new ArrayList<>();
	private static List<Integer> textures = new ArrayList<>();

	public static BasicModel loadToVAO(float[] positions, int[] indices, float[] normals, float[] textureCoords) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataAsVBO(0,3, positions);
		storeDataAsVBO(1, 2, textureCoords);
		storeDataAsVBO(2, 3, normals);
		unbindVAO();
		return new BasicModel(vaoID, indices.length);
	}

	public static int loadTexture(String fileName) {
		int id = 0;
		try {
			PNGDecoder decoder = new PNGDecoder(new FileInputStream("res/textures/"+fileName+".png"));
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			buffer.flip();
			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(),
					0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			glGenerateMipmap(GL_TEXTURE_2D);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		textures.add(id);
		return id;
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

	private static void storeDataAsVBO(int indexInVAO, int coordinateSize, float[] data) {
		generateArrayVBO();
		FloatBuffer buffer = storeInBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(indexInVAO, coordinateSize, GL_FLOAT, false, 0, 0);
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
		for (Integer texture : textures) glDeleteTextures(texture);
	}
}
