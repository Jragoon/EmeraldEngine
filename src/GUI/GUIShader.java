package GUI;

import Shaders.ShaderProgram;
import org.joml.Matrix4f;

public class GUIShader extends ShaderProgram {
	private static final String VERTEX_FILE = "src/GUI/GUIVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/GUI/GUIFragmentShader.glsl";

	private int location_transformationMatrix;

	public GUIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
