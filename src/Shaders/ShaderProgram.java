package Shaders;

import Entities.Camera;
import Entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;

	/* Allocating space for uniform 4x4 Matrices */
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	public ShaderProgram(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		glLinkProgram(programID);
		glValidateProgram(programID);
		getAllUniformLocations();
	}

	protected abstract void getAllUniformLocations();

	public void loadLights(List<Light> light) {};

	public void loadViewMatrix(Camera camera) {};

	public int getUniformLocation(String uniformName) {
		return glGetUniformLocation(programID, uniformName);
	}

	public void start() {
		glUseProgram(programID);
	}

	public void stop() {
		glUseProgram(0);
	}

	public void clean() {
		stop();
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}

	public void bindAttribute(int attribute, String variable) {
		glBindAttribLocation(programID, attribute, variable);
	}

	public void loadFloat(int location, float value) {
		glUniform1f(location, value);
	}

	public void loadVector(int location, Vector3f vector) {
		glUniform3f(location, vector.x, vector.y, vector.z);
	}

	public void loadBoolean(int location, boolean value) {
		float toLoad = 0;
		if (value)
			toLoad = 1;
		glUniform1f(location, toLoad);
	}

	public void loadMatrix(int location, Matrix4f matrix) {
		matrix.get(matrixBuffer);
		glUniformMatrix4fv(location, false, matrixBuffer);
	}

	protected abstract void bindAttributes();

	private int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		return shaderID;
	}
}
