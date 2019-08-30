package RenderEngine;

import Models.BasicModel;
import Models.TexturedModel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(TexturedModel texturedModel) {
		BasicModel model = texturedModel.getModel();
		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
		glDrawElements(GL_TRIANGLES, model.getVertices(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}
}
