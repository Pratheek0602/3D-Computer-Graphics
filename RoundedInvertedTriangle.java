
import gmaths.*;

public final class RoundedInvertedTriangle {

    public static final float[] vertices = createRoundedInvertedTriangleVertices();
    public static final int[] indices = createRoundedInvertedTriangleIndices();

    private static float[] createRoundedInvertedTriangleVertices() {
        int radialSteps = 30;
        int heightSteps = 30;
        double yMin = 0.5;
        double yMax = 1.0;
        double rMax = 0.4;
        int exponent = 1; // Adjust for how quickly radius decreases
        double roundnessExponent = 4.2; // Adjust for rounding the tip

        int step = 8;
        float[] vertices = new float[radialSteps * heightSteps * step];

        for (int j = 0; j < heightSteps; ++j) {
            double t = (double) j / (heightSteps - 1);
            double y = yMin + t * (yMax - yMin); // From yMin (bottom) to yMax (top)
            // Invert the radius function and adjust for rounding
            double radius = rMax * Math.pow(1 - Math.pow(1-t, roundnessExponent), exponent);
            // double radius = rMax * Math.sin((1 - t) * (Math.PI / 2));

            for (int i = 0; i < radialSteps; ++i) {
                double angle = 2.0 * Math.PI * i / (radialSteps - 1);
                double x = radius * Math.cos(angle);
                double z = radius * Math.sin(angle);

                int base = (j * radialSteps + i) * step;
                vertices[base + 0] = (float) x;
                vertices[base + 1] = (float) y;
                vertices[base + 2] = (float) z;

                // Compute normals
                // Calculate derivatives for normal computation
                double dt = 1.0 / (heightSteps - 1);
                double tNext = Math.min(t + dt, 1.0);
                double radiusNext = rMax * Math.pow(1 - Math.pow(tNext, roundnessExponent), exponent);
                double yNext = yMin + tNext * (yMax - yMin);

                // Tangent vectors
                double dx = radiusNext * Math.cos(angle) - x;
                double dy = yNext - y;
                double dz = radiusNext * Math.sin(angle) - z;

                Vec3 tangent = new Vec3((float) dx, (float) dy, (float) dz);
                Vec3 bitangent = new Vec3((float) (-Math.sin(angle)), 0, (float) Math.cos(angle));

                Vec3 normal = Vec3.crossProduct(tangent, bitangent);
                normal.normalize();

                vertices[base + 3] = normal.x;
                vertices[base + 4] = normal.y;
                vertices[base + 5] = normal.z;

                // Texture coordinates
                vertices[base + 6] = (float) i / (radialSteps - 1);
                vertices[base + 7] = (float) j / (heightSteps - 1);
            }
        }
        return vertices;
    }

    private static int[] createRoundedInvertedTriangleIndices() {
        int radialSteps = 30;
        int heightSteps = 30;
        int[] indices = new int[(radialSteps - 1) * (heightSteps - 1) * 6];

        for (int j = 0; j < heightSteps - 1; ++j) {
            for (int i = 0; i < radialSteps - 1; ++i) {
                int base = (j * (radialSteps - 1) + i) * 6;
                int current = j * radialSteps + i;
                int next = current + radialSteps;

                indices[base + 0] = current;
                indices[base + 1] = current + 1;
                indices[base + 2] = next + 1;

                indices[base + 3] = current;
                indices[base + 4] = next + 1;
                indices[base + 5] = next;
            }
        }
        return indices;
    }
}
