public final class HemiSphere {
  private static final int XLONG = 30;
  private static final int YLAT = 20;
  private static final int BOTTOM_SEGMENTS = 30; // For the circular bottom surface
  
  public static final float[] vertices = createVertices();
  public static final int[] indices = createIndices();
  
  private static float[] createVertices() {
      double r = 0.5;
      int step = 8;
      // Calculate total vertices needed: hemisphere + bottom circle + center point
      int hemisphereVertices = XLONG * YLAT;
      int bottomVertices = BOTTOM_SEGMENTS + 1; // +1 for center point
      float[] vertices = new float[(hemisphereVertices + bottomVertices) * step];
      
      // Create hemisphere vertices
      double START_LATITUDE = -15;  // Start from equator
      double END_LATITUDE = 90;   // End at top
      
      // Create hemisphere
      for (int j = 0; j < YLAT; ++j) {
          double b = Math.toRadians(START_LATITUDE + (END_LATITUDE - START_LATITUDE) * (double) j / (YLAT - 1));
          for (int i = 0; i < XLONG; ++i) {
              double a = Math.toRadians(360 * (double) i / (XLONG - 1));
              double x = Math.cos(b) * Math.sin(a);
              double y = Math.sin(b);
              double z = Math.cos(b) * Math.cos(a);
              int base = j * XLONG * step;
              vertices[base + i * step + 0] = (float) (r * x);
              vertices[base + i * step + 1] = (float) (r * y);
              vertices[base + i * step + 2] = (float) (r * z);
              vertices[base + i * step + 3] = (float) x;
              vertices[base + i * step + 4] = (float) y;
              vertices[base + i * step + 5] = (float) z;
              vertices[base + i * step + 6] = (float) i / (float) (XLONG - 1);
              vertices[base + i * step + 7] = (float) j / (float) (YLAT - 1);
          }
      }
      
      // Create bottom surface vertices
      int baseIndex = hemisphereVertices * step;
      
      // Center point of bottom circle
      vertices[baseIndex + 0] = 0.0f;
      vertices[baseIndex + 1] = 0.0f;
      vertices[baseIndex + 2] = 0.0f;
      vertices[baseIndex + 3] = 0.0f;
      vertices[baseIndex + 4] = -1.0f;
      vertices[baseIndex + 5] = 0.0f;
      vertices[baseIndex + 6] = 0.5f;
      vertices[baseIndex + 7] = 0.5f;
      
      // Create vertices for bottom circle
      for (int i = 0; i < BOTTOM_SEGMENTS; ++i) {
          double angle = Math.toRadians(360.0 * i / BOTTOM_SEGMENTS);
          float x = (float) (r * Math.cos(angle));
          float z = (float) (r * Math.sin(angle));
          int idx = baseIndex + (i + 1) * step;
          
          vertices[idx + 0] = x;
          vertices[idx + 1] = 0.0f;
          vertices[idx + 2] = z;
          vertices[idx + 3] = 0.0f;
          vertices[idx + 4] = -1.0f;
          vertices[idx + 5] = 0.0f;
          vertices[idx + 6] = (float) (Math.cos(angle) * 0.5 + 0.5);
          vertices[idx + 7] = (float) (Math.sin(angle) * 0.5 + 0.5);
      }
      
      return vertices;
  }
  
  private static int[] createIndices() {
      int hemisphereIndices = (XLONG - 1) * (YLAT - 1) * 6;
      int bottomIndices = BOTTOM_SEGMENTS * 3;
      int[] indices = new int[hemisphereIndices + bottomIndices];
      
      // Create hemisphere indices
      for (int j = 0; j < YLAT - 1; ++j) {
          for (int i = 0; i < XLONG - 1; ++i) {
              int base = (j * (XLONG - 1) + i) * 6;
              indices[base + 0] = j * XLONG + i;
              indices[base + 1] = j * XLONG + i + 1;
              indices[base + 2] = (j + 1) * XLONG + i + 1;
              indices[base + 3] = j * XLONG + i;
              indices[base + 4] = (j + 1) * XLONG + i + 1;
              indices[base + 5] = (j + 1) * XLONG + i;
          }
      }
      
      // Create bottom surface indices
      int baseVertex = XLONG * YLAT;  // Start after hemisphere vertices
      int baseIndex = hemisphereIndices;
      
      // Create triangles for bottom circle
      for (int i = 0; i < BOTTOM_SEGMENTS; ++i) {
          indices[baseIndex + i * 3] = baseVertex;  // Center point
          indices[baseIndex + i * 3 + 1] = baseVertex + 1 + i;
          indices[baseIndex + i * 3 + 2] = baseVertex + 1 + ((i + 1) % BOTTOM_SEGMENTS);
      }
      
      return indices;
  }
}