import java.io.File;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.*;
import gmaths.*;

public class Skybox {
    private Mesh mesh;
    private Shader shader;
    private Mat4 modelMatrix;
    private Camera camera;
    private Texture[] textures;
    private int[] textureIDs;
    private float rotationSpeedX = 3.0f; // degrees per second around X-axis
    private float rotationSpeedY = 3.0f;

    // Vertices for a cube (centered at origin)
    private float[] vertices = {
        // Positions          
        -1.0f,  1.0f, -1.0f, // 0
        -1.0f, -1.0f, -1.0f, // 1
         1.0f, -1.0f, -1.0f, // 2
         1.0f,  1.0f, -1.0f, // 3
        -1.0f,  1.0f,  1.0f, // 4
        -1.0f, -1.0f,  1.0f, // 5
         1.0f, -1.0f,  1.0f, // 6
         1.0f,  1.0f,  1.0f  // 7
    };

    private int[] indices = {
        // Back face
        0, 1, 2, 2, 3, 0,
        // Front face
        7, 6, 5, 5, 4, 7,
        // Left face
        4, 5, 1, 1, 0, 4,
        // Right face
        3, 2, 6, 6, 7, 3,
        // Top face
        4, 0, 3, 3, 7, 4,
        // Bottom face
        1, 5, 6, 6, 2, 1
    };

    public Skybox(GL3 gl, Camera camera, String[] textureFiles) {
        this.camera = camera;
        int[] attributeSizes = {3};

        // Create mesh for the skybox cube
        mesh = new Mesh(gl, vertices, indices, attributeSizes);

        // Load shaders
        shader = new Shader(gl, "assets/shaders/vs_skybox.txt", "assets/shaders/fs_skybox.txt");
        shader.use(gl);

        // Set up model matrix (scale up the cube to cover the entire scene)
        modelMatrix = Mat4Transform.scale(100, 100, 100);

        // Load the 6 textures for the skybox
        textures = new Texture[6];
        textureIDs = new int[6];

        for (int i = 0; i < 6; i++) {
            try {
                textures[i] = TextureIO.newTexture(new File(textureFiles[i]), false);
                textureIDs[i] = textures[i].getTextureObject();
            } catch (Exception e) {
                System.out.println("Error loading texture " + textureFiles[i]);
                e.printStackTrace();
            }
        }

        // Set texture parameters
        for (Texture texture : textures) {
            texture.setTexParameteri(gl, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP_TO_EDGE);
            texture.setTexParameteri(gl, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
            texture.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            texture.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        }
    }

    public void render(GL3 gl, double elapsedTime) {
        gl.glDepthFunc(GL.GL_LEQUAL);

        shader.use(gl);

        // Remove translation from the view matrix to keep skybox centered around the camera
        Mat4 view = camera.getViewMatrix();
        view = new Mat4(view); // Clone the view matrix

        // Zero out translation components
        view.set(0, 3, 0.0f);
        view.set(1, 3, 0.0f);
        view.set(2, 3, 0.0f);

        Mat4 projection = camera.getPerspectiveMatrix();

        // Compute rotation angles based on elapsed time
        float angleY = (float)(elapsedTime * rotationSpeedY);
        float angleX = (float)(elapsedTime * rotationSpeedX);

        // Create rotation matrices
        Mat4 rotationY = Mat4Transform.rotateAroundY(angleY);
        Mat4 rotationX = Mat4Transform.rotateAroundX(angleX);

        Mat4 modelMatrix = Mat4Transform.scale(100, 100, 100);
        // modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(angle), modelMatrix);

        modelMatrix = Mat4.multiply(modelMatrix, rotationY);
        modelMatrix = Mat4.multiply(modelMatrix, rotationX);

        Mat4 mvpMatrix = Mat4.multiply(projection, Mat4.multiply(view, modelMatrix));

        shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());

        // Bind textures
        for (int i = 0; i < 6; i++) {
            gl.glActiveTexture(GL.GL_TEXTURE0 + i);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureIDs[i]);
            shader.setInt(gl, "skyboxTextures[" + i + "]", i);
        }

        // Render the skybox cube
        mesh.render(gl);

        // Restore default depth testing
        gl.glDepthFunc(GL.GL_LESS);
    }


    public void dispose(GL3 gl) {
        mesh.dispose(gl);
        for (Texture texture : textures) {
            if (texture != null) texture.destroy(gl);
        }
    }
}
