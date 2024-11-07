import gmaths.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
import com.jogamp.opengl.util.texture.*;

public class Room {

  private ModelMultipleLights[] wall;
  private Camera camera;
  private Light[] lights;
  private Texture t0, t1, t2, t3, t4, t5;

  // Adjust room dimensions for a more spaceship-like appearance
  private float roomLength = 6f;  // Increased length for a more stretched room (Z direction)
  private float roomWidth = 4f;   // Moderate width (X direction)
  private float roomHeight = 3f;   // Lower ceiling for a more compact spaceship feel (Y direction)

  public Room(GL3 gl, Camera c, Light[] l, Texture t0, Texture t1, Texture t2,Texture t3,Texture t4,Texture t5) {
    camera = c;
    lights = l;
    this.t0 = t0;
    this.t1 = t1;
    this.t2 = t2;
    this.t3 = t3;
    this.t4 = t4;
    this.t5 = t5;
    wall = new ModelMultipleLights[5];  // Walls, ceiling, and floor
    wall[0] = makeFloor(gl);  // Floor
    wall[1] = makeBackWall(gl);  // Back Wall
    wall[2] = makeLeftWall(gl);  // Left Wall
    wall[3] = makeRightWall(gl);  // Right Wall
    wall[4] = makeCeiling(gl);  // Ceiling
  }

  private ModelMultipleLights makeFloor(GL3 gl) {
    String name = "floor";
    Vec3 basecolor = new Vec3(0.5f, 0.5f, 0.5f); // grey (you can change this to a more metallic color)
    Material material = new Material(new Vec3(2.2f, 2.2f, 2.2f), new Vec3(2.2f, 2.2f, 2.2f), new Vec3(1.5f, 1.5f, 1.5f), 4.0f, 1.0f);
    Mat4 modelMatrix = new Mat4(1);
    int[] attributeSizes = {3, 3, 2};
    // Adjusted to create a wider and longer floor for a spaceship corridor
    modelMatrix = Mat4.multiply(Mat4Transform.scale(roomWidth, 1f, roomLength), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone(),attributeSizes);
    Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_standard.txt");
    ModelMultipleLights model = new ModelMultipleLights(name, mesh, modelMatrix, shader, material, lights, camera, t0);
    return model;
  }

  private ModelMultipleLights makeBackWall(GL3 gl) {
    String name = "back_wall";
    Material material = new Material(new Vec3(1.0f, 0.5f, 1.0f), new Vec3(1.0f, 0.5f, 1.0f), new Vec3(1.1f, 1.1f, 1.1f), 30.0f, 1.0f);

    Mat4 modelMatrix = new Mat4(1);
    int[] attributeSizes = {3, 3, 2};
    modelMatrix = Mat4.multiply(Mat4Transform.scale(roomWidth, 1f, 1f), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0, 1f * 0.5f, -roomLength * 0.5f), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone(),attributeSizes);
    Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_backwall.txt");

    ModelMultipleLights model = new ModelMultipleLights(name, mesh, modelMatrix, shader, material, lights, camera, t3, t4);
    return model;
  }


  private ModelMultipleLights makeLeftWall(GL3 gl) {
    String name = "left_window";
    Material material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 4.0f, 0.4f); // 0.4f alpha for transparency

    Mat4 modelMatrix = new Mat4(1);
    int[] attributeSizes = {3, 3, 2};
    // Scale to match the length of the room and height for the window
    modelMatrix = Mat4.multiply(Mat4Transform.scale(roomLength, roomHeight, 1f), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-roomWidth * 0.5f, 1f * 0.5f, 0), modelMatrix);
    
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone(),attributeSizes);
    Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_standard.txt");
    ModelMultipleLights model = new ModelMultipleLights(name, mesh, modelMatrix, shader, material, lights, camera, t2);


    return model;
  }

  private ModelMultipleLights makeRightWall(GL3 gl) {
    String name = "right_wall";
    Material material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 4.0f, 1.0f);
    Mat4 modelMatrix = new Mat4(1);
    int[] attributeSizes = {3, 3, 2};
    modelMatrix = Mat4.multiply(Mat4Transform.scale(roomLength, roomHeight, 1f), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(180), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(roomWidth * 0.5f, 1f * 0.5f, 0f), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone(),attributeSizes);
    Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_right_wall.txt");

    // Bind the texture for the right wall and set texture wrapping mode
    gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject(gl));  // Assuming t1 is the texture for the right wall
    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);

    ModelMultipleLights model = new ModelMultipleLights(name, mesh, modelMatrix, shader, material, lights, camera, t1);
    return model;
  }



  private ModelMultipleLights makeCeiling(GL3 gl) {
    String name = "ceiling";
    Vec3 basecolor = new Vec3(0.9f, 0.9f, 0.9f); // light grey
    Material material = new Material(new Vec3(2.2f, 2.2f, 2.2f), new Vec3(2.2f, 2.2f, 2.2f), new Vec3(1.5f, 1.5f, 1.5f), 4.0f, 1.0f);
    Mat4 modelMatrix = new Mat4(1);
    int[] attributeSizes = {3, 3, 2};
    modelMatrix = Mat4.multiply(Mat4Transform.scale(roomWidth, 1f, roomLength), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(180), modelMatrix);  // Place at top
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0, 1f, 0), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone(),attributeSizes);
    Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_standard.txt");
    ModelMultipleLights model = new ModelMultipleLights(name, mesh, modelMatrix, shader, material, lights, camera, t5);
    return model;
  }
  
  public ModelMultipleLights getFloorModel() {
    return wall[0];  
  }

  // public ModelMultipleLights getBackWallModel() {
  //   return wall[1];  
  // }

  public void render(GL3 gl) {
    // Render opaque walls first
    for (int i = 0; i < wall.length; i++) {
        if (i != 2) { // Skip the transparent left wall
            wall[i].render(gl);
        }
    }

    // Enable blending for transparency
    gl.glEnable(GL.GL_BLEND);
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

    // Disable depth writing but keep depth testing
    gl.glDepthMask(false);

    // Render transparent left wall
    wall[2].render(gl);

    // Restore OpenGL state
    gl.glDepthMask(true);
    gl.glDisable(GL.GL_BLEND);
  }



  public void dispose(GL3 gl) {
    for (int i = 0; i < 5; i++) {
      wall[i].dispose(gl);
    }
  }
}
