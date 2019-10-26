package Tools;

import Entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.lang.Math;

public class MathLibrary {

	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f().identity();
		Vector3f adjustedTranslation = new Vector3f(translation.x, translation.y, 0f);
		matrix.translate(adjustedTranslation);
		matrix.scale(new Vector3f(scale.x, scale.y, 1f));
		return matrix;
	}

	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx,
													  float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.translate(translation);
		matrix.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0));
		matrix.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0));
		matrix.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1));
		matrix.scale(scale);
		return matrix;
	}

	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0));
		viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
		viewMatrix.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1));
		Vector3f camPos = camera.getPosition();
		Vector3f negCamPos = new Vector3f(-camPos.x, -camPos.y, -camPos.z);
		viewMatrix.translate(negCamPos);
		return viewMatrix;
	}
}
