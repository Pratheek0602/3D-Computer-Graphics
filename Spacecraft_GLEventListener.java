import gmaths.*;

import java.nio.*;
import javax.swing.JButton;
import com.jogamp.common.nio.*; 
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.util.texture.awt.*;
import com.jogamp.opengl.util.texture.spi.JPEGImage;
  
public class Spacecraft_GLEventListener implements GLEventListener {
  
  private static final boolean DISPLAY_SHADERS = false;
  private Camera camera;
  private JButton danceButton;
  private double lastFrameTime = 0.0;
    
  /* The constructor is not used to initialise anything */
  public Spacecraft_GLEventListener(Camera camera) {
    this.camera = camera;
    // Set camera position and target
    Vec3 position = new Vec3(0f, 0.6f, 5.1f);
    Vec3 target = new Vec3(0f, 0f, 0f);
    this.camera.setPosition(position);
    this.camera.setTarget(target);
    
    // Log the camera's position and target
    System.out.println("Camera Position: " + position);
    System.out.println("Camera Target: " + target);
  }
  
  // ***************************************************
  /*
   * METHODS DEFINED BY GLEventListener
   */
  
  /* Initialisation */
  public void init(GLAutoDrawable drawable) {   
    GL3 gl = drawable.getGL().getGL3();
    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LESS);
    gl.glFrontFace(GL.GL_CCW);    // default is 'CCW'
    gl.glEnable(GL.GL_CULL_FACE); // default is 'not enabled' so needs to be enabled
    gl.glCullFace(GL.GL_BACK);   // default is 'back', assuming CCW
    initialise(gl);
    startTime = getSeconds();
    lastFrameTime = getSeconds();
  }
  
  /* Called to indicate the drawing surface has been moved and/or resized  */
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL3 gl = drawable.getGL().getGL3();
    gl.glViewport(x, y, width, height);
    float aspect = (float)width/(float)height;
    camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
  }

  /* Draw */
  public void display(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    render(gl);
  }



  // ***************************************************
  /* THE SCENE */

  // textures
  private TextureLibrary textures;

  private Room room;
  private Skybox skybox;
  // private Container container;
  private Light[] lights = new Light[1];
  private Robot1 robot1;
  private Robot2 robot2;
  private Globe globe; 

  // Define the proximity threshold (adjust as needed)
  private static final float PROXIMITY_THRESHOLD = 1.8f;
  private boolean worldLightEnabled = true;
  private boolean spotlightEnabled = true;
  private float worldLightIntensity = 1.0f;
  private float spotlightIntensity = 1.0f;

  private void loadTextures(GL3 gl) {
    textures = new TextureLibrary();

    // Load skybox textures
    textures.add(gl, "skybox_right", "assets/textures/space_box.jpg");
    textures.add(gl, "skybox_left", "assets/textures/space_box.jpg");
    textures.add(gl, "skybox_top", "assets/textures/space_box.jpg");
    textures.add(gl, "skybox_bottom", "assets/textures/space_box.jpg");
    textures.add(gl, "skybox_front", "assets/textures/space_box.jpg");
    textures.add(gl, "skybox_back", "assets/textures/space_box.jpg");

    textures.add(gl, "floor", "assets/textures/space_floor.jpg");
    textures.add(gl, "ceiling", "assets/textures/space_ceiling.jpg");
    textures.add(gl, "right_wall", "assets/textures/panel2.jpg");
    textures.add(gl, "window", "assets/textures/space_window2.jpg");

    // Diffuse and Specular for the Backwall
    textures.add(gl, "diffuse", "assets/textures/diffuse_pratheek.jpg");
    textures.add(gl, "specular", "assets/textures/specular_pratheek.jpg");

    // Robot1 textures
    textures.add(gl, "robot1_body", "assets/textures/robot_skin2.jpg");
    textures.add(gl, "robot1_antenna", "assets/textures/robot_antenna.jpg");
    textures.add(gl, "robot1_eyes", "assets/textures/robot_eyes4.jpg");

    // Robot2 textures
    textures.add(gl, "robot2_body", "assets/textures/robot2_skin.jpg");
    textures.add(gl, "robot2_eyes", "assets/textures/robot2_eyes.jpg");
    textures.add(gl, "robot2_antenna", "assets/textures/robot2_antenna.jpg");
    textures.add(gl, "robot2_casing", "assets/textures/robot2_casing.jpg");
    textures.add(gl, "robot2_bulb", "assets/textures/robot2_bulb.jpg");

    // Globe Texture
    textures.add(gl, "globe_texture", "assets/textures/earth_texture.jpg");      
    textures.add(gl, "pedestal_texture", "assets/textures/pedestal_texture.jpg");
  }
  
  public void initialise(GL3 gl) {
    loadTextures(gl);

    lights[0] = new Light(gl);
    lights[0].setCamera(camera);

    room = new Room(gl, camera, lights, textures.get("floor"), textures.get("right_wall"), textures.get("window"), textures.get("diffuse"), textures.get("specular"), textures.get("ceiling"));
    robot1 = new Robot1(gl, camera, lights, textures.get("robot1_body"), textures.get("robot1_eyes"), textures.get("robot1_antenna"));
    robot2 = new Robot2(gl, camera, lights, textures.get("robot2_body"), textures.get("robot2_eyes"), textures.get("robot2_antenna"), textures.get("robot2_casing"), textures.get("robot2_bulb"));

    // Initialize skybox
    String[] skyboxTextureFiles = {
        "assets/textures/space_box.jpg",  // Right
        "assets/textures/space_box.jpg",   // Left
        "assets/textures/space_box.jpg",    // Top
        "assets/textures/space_box.jpg", // Bottom
        "assets/textures/space_box.jpg",  // Front
        "assets/textures/space_box.jpg"    // Back
    };
    skybox = new Skybox(gl, camera, skyboxTextureFiles);
    globe = new Globe(gl, camera, lights, textures.get("globe_texture"), textures.get("pedestal_texture"));
  }

  public void startRobotDance() {
    robot1.startManualDancing();
  }

  public void stopRobotDance() {
      robot1.stopManualDancing();
  }

  public void moveRobot2() {
    robot2.startMoving(); 
  }

  public void stopRobot2() {
    robot2.stopMoving();
  }

  public void resetRobot2Position() {
    robot2.resetPosition();
  }

  public boolean toggleWorldLight() {
    worldLightEnabled = !worldLightEnabled;
    setWorldLightIntensity(worldLightEnabled ? worldLightIntensity : 0.0f);
    return worldLightEnabled;
  }
  
  public void setWorldLightIntensity(float intensity) {
    worldLightIntensity = intensity;
    lights[0].setIntensity(intensity);
  }
  
  public boolean toggleSpotlight() {
    spotlightEnabled = !spotlightEnabled;
    setSpotlightIntensity(spotlightEnabled ? spotlightIntensity : 0.0f);
    return spotlightEnabled;
  }
  
  public void setSpotlightIntensity(float intensity) {
    spotlightIntensity = intensity;
    // Update Robot2's spotlight intensity
    robot2.setSpotlightIntensity(intensity);
  }

  public boolean isWorldLightEnabled() {
        return worldLightEnabled;
    }

    public boolean isSpotlightEnabled() {
        return spotlightEnabled;
    }

    public float getWorldLightIntensity() {
        return worldLightIntensity;
    }

    public float getSpotlightIntensity() {
        return spotlightIntensity;
    }


  public void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    double currentTime = getSeconds();
    double elapsedTime = currentTime - startTime;
    double deltaTime = currentTime - lastFrameTime;
    
    // Render skybox
    skybox.render(gl, elapsedTime);

    // Update and render lights
    lights[0].setPosition(new Vec3(0.0f, 0.5f, 6.5f)); 
    lights[0].render(gl);

    // Update and render Robot2 (the one with the spotlight)
    if (robot2.isCurrentlyMoving()) {
        robot2.updatePosition((float) deltaTime);
    }
    robot2.render(gl);

    // Get spotlight parameters from Robot2 
    Vec3 spotlightPosition = robot2.getSpotlightPosition();
    Vec3 spotlightDirection = robot2.getSpotlightDirection();
    float cutOff = robot2.getSpotlightCutOff();
    float outerCutOff = robot2.getSpotlightOuterCutOff();
    Vec3 spotlightAmbient = robot2.getSpotlightAmbient();
    Vec3 spotlightDiffuse = robot2.getSpotlightDiffuse();
    Vec3 spotlightSpecular = robot2.getSpotlightSpecular();
    float constant = robot2.getSpotlightConstant();
    float linear = robot2.getSpotlightLinear();
    float quadratic = robot2.getSpotlightQuadratic();

    // Set spotlight parameters for the floor model
    ModelMultipleLights floorModel = room.getFloorModel();
    floorModel.setSpotlightPosition(spotlightPosition);
    floorModel.setSpotlightDirection(spotlightDirection);
    floorModel.setSpotlightCutOff(cutOff);
    floorModel.setSpotlightOuterCutOff(outerCutOff);
    floorModel.setSpotlightAmbient(spotlightAmbient);
    floorModel.setSpotlightDiffuse(spotlightDiffuse);
    floorModel.setSpotlightSpecular(spotlightSpecular);
    floorModel.setSpotlightAttenuation(constant, linear, quadratic);

    // Perform proximity test and update Robot1's dancing state
    updateRobot1DancingState();

    // Update and render Robot1
    if (robot1.isCurrentlyDancing()) {
        robot1.updateDance((float) deltaTime);
    }
    robot1.render(gl);

    lights[0].setIntensity(worldLightEnabled ? worldLightIntensity : 0.0f);
    robot2.setSpotlightIntensity(spotlightEnabled ? spotlightIntensity : 0.0f);

    // Update and render Globe
    globe.update((float) deltaTime);
    globe.render(gl);
    room.render(gl);
    
    lastFrameTime = currentTime;
  }


  /**
   * Updates Robot1's dancing state based on the proximity of Robot2.
   */
  private void updateRobot1DancingState() {
    // Get Robot1's position
    Vec3 robot1Position = robot1.getRobotPosition();

    // Get Robot2's position
    Vec3 robot2Position = robot2.getRobotPosition();

    // Calculate Euclidean distance between Robot1 and Robot2
    float distance = calculateDistance(robot1Position, robot2Position);


    // Check if Robot2 is within the proximity threshold
    if (distance < PROXIMITY_THRESHOLD) {
        if (!robot1.isAutoDancing()) { // Only start auto dancing if not already auto dancing
            robot1.startAutoDancing();
        }
    } else {
        if (robot1.isAutoDancing()) { // Only stop auto dancing if currently auto dancing
            robot1.stopAutoDancing();
          }
        }
  }

  /**
   * Calculates the Euclidean distance between two Vec3 points.
   * @param a First point
   * @param b Second point
   * @return Distance between a and b
   */
  private float calculateDistance(Vec3 a, Vec3 b) {
      float dx = a.x - b.x;
      float dy = a.y - b.y;
      float dz = a.z - b.z;
      return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
  }

  /* Clean up memory */
  public void dispose(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();

    room.dispose(gl);
    skybox.dispose(gl);
    lights[0].dispose(gl);
    textures.destroy(gl);

    robot1.dispose(gl);
    robot2.dispose(gl);
  }
  

  private double startTime;
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }


}
