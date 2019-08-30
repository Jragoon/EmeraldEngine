package Shaders;

public class StaticShader extends ShaderProgram {
	private static final String VERTEX_SHADER = "src/Shaders/VertexShader.glsl";
	private static final String FRAGMENT_SHADER = "src/Shaders/FragmentShader.glsl";

	public StaticShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
