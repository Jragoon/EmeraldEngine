package Shaders;

import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram {
	private static final String VERTEX_SHADER = "src/Shaders/VertexShader.glsl";
	private static final String FRAGMENT_SHADER = "src/Shaders/FragmentShader.glsl";

	private int location_transformationMatrix;

	public StaticShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
}
