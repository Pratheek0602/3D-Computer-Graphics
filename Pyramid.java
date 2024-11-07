public final class Pyramid {
    public static final float[] vertices = {
        // Positions          // Normals         // Texture Coords
        0f, 0.5f, 0f,         0f, 1f, 0f,       0.5f, 1.0f,  // Top vertex
       -0.5f, -0.5f, -0.5f,  -1f, -1f, -1f,    0.0f, 0.0f,  // Base vertex 1
        0.5f, -0.5f, -0.5f,   1f, -1f, -1f,     1.0f, 0.0f,  // Base vertex 2
        0f, -0.5f, 0.5f,      0f, -1f, 1f,      0.5f, 0.5f   // Base vertex 3
    };

    public static final int[] indices = {
        0, 1, 2,   // Side face 1
        0, 2, 3,   // Side face 2
        0, 3, 1,   // Side face 3
        1, 2, 3    // Base face
    };
}
