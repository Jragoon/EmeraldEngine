package Models;

public class BasicModel {
    private int vaoID;
    private int vertices;

    public BasicModel(int vaoID, int vertices) {
        this.vaoID = vaoID;
        this.vertices = vertices;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertices() {
        return vertices;
    }
}
