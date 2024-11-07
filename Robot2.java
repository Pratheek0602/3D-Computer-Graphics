import gmaths.*;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.*;

public class Robot2 {
    private Camera camera;
    private Light[] light;
    private ModelMultipleLights sphere, cube;
    private SGNode robot2Root;
    private TransformNode robot2MoveTranslate, robot2Rotate, spotlightRotate;
    private float moveSpeed = 0.6f;
    private boolean isMoving = false;
    private float intensity = 1.0f;
    private Material material;


    private List<Vec3> pathPoints;
    private int currentPathIndex = 0;
    private Vec3 currentDirection;

    // Spotlight parameters
    private Vec3 spotlightPosition;
    private Vec3 spotlightDirection;

    private float xPosition = -1.46f;
    private float yPosition = 0.12f;
    private float zPosition = 2.5f;

    private Vec3 spotlightAmbient = new Vec3(0.2f, 0.2f, 0.2f);
    private Vec3 spotlightDiffuse = new Vec3(0.8f, 0.8f, 0.8f);
    private Vec3 spotlightSpecular = new Vec3(1.0f, 1.0f, 1.0f);

    // Preserve original spotlight light components
    private final Vec3 originalSpotlightAmbient = new Vec3(0.2f, 0.2f, 0.2f);
    private final Vec3 originalSpotlightDiffuse = new Vec3(0.8f, 0.8f, 0.8f);
    private final Vec3 originalSpotlightSpecular = new Vec3(1.0f, 1.0f, 1.0f);

    // For spotlight rotation
    private float spotlightRotationAngle = 0;

    // Robot position and rotation
    private Vec3 robotPosition = new Vec3(xPosition, yPosition, zPosition);
    private float robotRotationAngle = 0;

    private Shader robotShader;

    public Robot2(GL3 gl, Camera cameraIn, Light[] lightIn, Texture bodyTexture, Texture eyeTexture, Texture antennaTexture, Texture casingTexture, Texture bulbTexture) {
        this.camera = cameraIn;
        this.light = lightIn;

        // Initialize shapes with textures
        sphere = makeSphere(gl, bodyTexture);
        cube = makeCube(gl, eyeTexture);
        material = new Material();

        // Build the robot body
        robot2Root = new NameNode("root");
        robot2MoveTranslate = new TransformNode("robot2 translate", Mat4Transform.translate(xPosition, yPosition, zPosition));
        robot2Rotate = new TransformNode("robot2 rotate", Mat4Transform.rotateAroundY(0));

        NameNode body = makeBody(gl);
        NameNode eyes = makeEyes(gl);
        NameNode robot_antenna = makeAntenna(gl, antennaTexture, casingTexture, bulbTexture);

        robot2Root.addChild(robot2MoveTranslate);
        robot2MoveTranslate.addChild(robot2Rotate);
        robot2Rotate.addChild(body);
        body.addChild(eyes);
        body.addChild(robot_antenna);

        robot2Root.update();

        // Initialize spotlight parameters with original values
        spotlightAmbient = new Vec3(originalSpotlightAmbient);
        spotlightDiffuse = new Vec3(originalSpotlightDiffuse);
        spotlightSpecular = new Vec3(originalSpotlightSpecular);

        // Initialize path
        initPath();
        // Set initial direction
        updateCurrentDirection();
        updateRobotOrientation();
    }

    private void initPath() {
        pathPoints = new ArrayList<>();
        // Define the path points (adjust coordinates based on your scene)
        pathPoints.add(new Vec3(xPosition, yPosition, zPosition));
        pathPoints.add(new Vec3(-xPosition, yPosition, zPosition));
        pathPoints.add(new Vec3(-xPosition, yPosition, -zPosition));
        pathPoints.add(new Vec3(xPosition, yPosition, -zPosition));
    }

    private void updateCurrentDirection() {
        if (pathPoints.size() > 1) {
            Vec3 currentPos = pathPoints.get(currentPathIndex);
            Vec3 nextPos = pathPoints.get((currentPathIndex + 1) % pathPoints.size());
            currentDirection = Vec3.subtract(nextPos, currentPos);
            currentDirection.normalize();
        } else {
            currentDirection = new Vec3(0, 0, 0);
        }
    }

    private NameNode makeBody(GL3 gl) {
        NameNode body = new NameNode("body");
        Mat4 bodyTransform = Mat4Transform.scale(0.17f, 0.17f, 0.17f);
        TransformNode bodyScale = new TransformNode("body scale", bodyTransform);
        ModelNode bodyShape = new ModelNode("body shape", sphere);

        body.addChild(bodyScale);
        bodyScale.addChild(bodyShape);
        return body;
    }

    private NameNode makeEyes(GL3 gl) {
        NameNode eyes = new NameNode("eyes");

        // Left Eye
        TransformNode leftEyeTransform = new TransformNode("left eye transform",
            Mat4.multiply(
                Mat4Transform.translate(-0.05f, 0.07f, 0.04f),
                Mat4Transform.scale(0.03f, 0.03f, 0.03f)
            )
        );
        ModelNode leftEyeShape = new ModelNode("left eye shape", cube);

        // Right Eye
        TransformNode rightEyeTransform = new TransformNode("right eye transform",
            Mat4.multiply(
                Mat4Transform.translate(0.05f, 0.07f, 0.04f),
                Mat4Transform.scale(0.03f, 0.03f, 0.03f)
            )
        );
        ModelNode rightEyeShape = new ModelNode("right eye shape", cube);

        eyes.addChild(leftEyeTransform);
        leftEyeTransform.addChild(leftEyeShape);

        eyes.addChild(rightEyeTransform);
        rightEyeTransform.addChild(rightEyeShape);

        return eyes;
    }

    private NameNode makeAntenna(GL3 gl, Texture antennaTexture, Texture casingTexture, Texture bulbTexture) {
        NameNode antenna = new NameNode("antenna");
        
        // Bulb
        TransformNode bulbTranslate = new TransformNode("bulb translate", Mat4Transform.translate(0f, 0.19f, 0.03f));
        TransformNode bulbScale = new TransformNode("bulb scale", Mat4Transform.scale(0.03f, 0.03f, 0.03f)); 
        ModelNode bulbShape = new ModelNode("bulb shape", makeSphere(gl, bulbTexture)); // Use bulb texture

        // Spotlight (bulb and casing and antenna)
        spotlightRotate = new TransformNode("spotlight rotate", Mat4Transform.rotateAroundY(0));

        // Casing
        TransformNode casingTranslate = new TransformNode("casing translate", Mat4Transform.translate(0, 0.2f, 0));
        TransformNode casingScale = new TransformNode("casing scale", Mat4Transform.scale(0.05f, 0.05f, 0.05f)); 
        ModelNode casingShape = new ModelNode("casing shape", makeSphere(gl, casingTexture)); // Use casing texture

        // Antenna
        TransformNode antennaTranslate = new TransformNode("antenna translate", Mat4Transform.translate(0, 0.1f, 0));
        TransformNode antennaScale = new TransformNode("antenna scale", Mat4Transform.scale(0.015f, 0.15f, 0.015f)); 
        ModelNode antennaShape = new ModelNode("antenna shape", makeCube(gl, antennaTexture));

        antenna.addChild(bulbTranslate);
        bulbTranslate.addChild(bulbScale);
        bulbScale.addChild(bulbShape);
        antenna.addChild(spotlightRotate);

        spotlightRotate.addChild(bulbTranslate);
        bulbTranslate.addChild(bulbScale);
        bulbScale.addChild(bulbShape);

        spotlightRotate.addChild(casingTranslate);
        casingTranslate.addChild(casingScale);
        casingScale.addChild(casingShape);

        spotlightRotate.addChild(antennaTranslate);
        antennaTranslate.addChild(antennaScale);
        antennaScale.addChild(antennaShape);
        
        return antenna;
    }

    private ModelMultipleLights makeSphere(GL3 gl, Texture texture) {
        Mesh mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone(), new int[] {3, 3, 2});
        robotShader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_standard.txt");
        Material material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 32.0f, 1.0f);
        return new ModelMultipleLights("sphere", mesh, new Mat4(1), robotShader, material, light, camera, texture);
    }

    private ModelMultipleLights makeCube(GL3 gl, Texture texture) {
        Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone(), new int[] {3, 3, 2});
        Material material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 32.0f, 1.0f);
        return new ModelMultipleLights("cube", mesh, new Mat4(1), robotShader, material, light, camera, texture);
    }

    private void updateSpotlightParameters() {
        float totalRotationAngle = robotRotationAngle + spotlightRotationAngle;
        float rad = (float) Math.toRadians(totalRotationAngle);

        float offset = 0.1f; // Move the spotlight further ahead
        spotlightPosition = new Vec3(
            robotPosition.x + (float) Math.sin(rad) * offset,
            robotPosition.y + 0.25f,  // Raise the spotlight
            robotPosition.z - (float) Math.cos(rad) * offset
        );

        spotlightDirection = new Vec3(
            (float) Math.sin(rad),
            -0.5f,  // Slight downward angle
            (float) Math.cos(rad)
        );
        spotlightDirection.normalize();
    }

    private void updateRobotOrientation() {
        robotRotationAngle = (float) Math.toDegrees(Math.atan2(currentDirection.x, currentDirection.z));
        robot2Rotate.setTransform(Mat4Transform.rotateAroundY(robotRotationAngle));
        robot2Rotate.update();
    }


    private void setRobotPosition(Vec3 position) {
        robotPosition = position;
        robot2MoveTranslate.setTransform(Mat4Transform.translate(position.x, position.y, position.z));
        robot2MoveTranslate.update();
    }

    public void updatePosition(float deltaTime) {
        if (isMoving && pathPoints.size() > 0) {
            Vec3 currentPos = getRobotPosition();
            Vec3 targetPos = pathPoints.get((currentPathIndex + 1) % pathPoints.size());
            Vec3 direction = Vec3.subtract(targetPos, currentPos);
            float distanceToTarget = direction.length();

            if (distanceToTarget < moveSpeed * deltaTime) {
                // Reached the next point
                setRobotPosition(targetPos);
                currentPathIndex = (currentPathIndex + 1) % pathPoints.size();
                updateCurrentDirection();
                updateRobotOrientation();
            } else {
                // Move towards target
                direction.normalize();
                Vec3 movement = Vec3.multiply(direction, moveSpeed * deltaTime);
                Vec3 newPos = Vec3.add(currentPos, movement);
                setRobotPosition(newPos);
            }

            // Rotate the spotlight
            if (isMoving){
                spotlightRotationAngle += deltaTime * 90; // Adjust rotation speed as needed
                spotlightRotate.setTransform(Mat4Transform.rotateAroundY(spotlightRotationAngle));
                spotlightRotate.update();

                // Update spotlight parameters for shader
                updateSpotlightParameters();
            }

            robot2Root.update();
        }
    }

    public void resetPosition() {
        isMoving = false; // Stop movement
        currentPathIndex = 0; // Reset path index
        spotlightRotationAngle = 0; // Reset spotlight rotation
        robotRotationAngle = 0; // Reset robot rotation angle

        // Reset robot's position to initial values
        robotPosition = new Vec3(xPosition, yPosition, zPosition);
        setRobotPosition(robotPosition);

        // Reset transformations
        robot2Rotate.setTransform(Mat4Transform.rotateAroundY(robotRotationAngle));
        robot2Rotate.update();

        spotlightRotate.setTransform(Mat4Transform.rotateAroundY(spotlightRotationAngle));
        spotlightRotate.update();

        updateCurrentDirection();
        updateRobotOrientation();
        updateSpotlightParameters();

        robot2Root.update();
    }

    public void setSpotlightIntensity(float intensity) {
        // Clamp the intensity between 0.0f and 1.0f
        this.intensity = Math.max(0.0f, Math.min(1.0f, intensity));
        
        // Scale the original light components by intensity
        spotlightAmbient = Vec3.multiply(originalSpotlightAmbient, intensity);
        spotlightDiffuse = Vec3.multiply(originalSpotlightDiffuse, intensity);
        spotlightSpecular = Vec3.multiply(originalSpotlightSpecular, intensity);
    }

    public void startMoving() {
        isMoving = true;
    }

    public void stopMoving() {
        isMoving = false;
        // Optionally, stop spotlight rotation or reset parameters
        spotlightRotationAngle = 0;
        spotlightRotate.setTransform(Mat4Transform.rotateAroundY(spotlightRotationAngle));
        spotlightRotate.update();
    }

    public boolean isCurrentlyMoving() {
        return isMoving;
    }

    public void render(GL3 gl) {
        updateSpotlightParameters();  // Update spotlight position and direction

        robot2Root.draw(gl);  // Render the robot
    }

    public Vec3 getRobotPosition() {
        return robotPosition;
    }

    // Getter methods for spotlight parameters

    public float getSpotlightIntensity() {
        return intensity;
    }
    
    public Vec3 getSpotlightAmbient() {
        return spotlightAmbient;
    }
    
    public Vec3 getSpotlightDiffuse() {
        return spotlightDiffuse;
    }
    
    public Vec3 getSpotlightSpecular() {
        return spotlightSpecular;
    }

    public Vec3 getSpotlightPosition() {
        return spotlightPosition;
    }

    public Vec3 getSpotlightDirection() {
        return spotlightDirection;
    }

    public float getSpotlightCutOff() {
        return (float) Math.cos(Math.toRadians(6f));  // Use the same value as in your render method
    }

    public float getSpotlightOuterCutOff() {
        return (float) Math.cos(Math.toRadians(8f));  // Use the same value as in your render method
    }

    public float getSpotlightConstant() {
        return 1.0f;
    }

    public float getSpotlightLinear() {
        return 0.30f;
    }

    public float getSpotlightQuadratic() {
        return 0.40f;
    }

    public void dispose(GL3 gl) {
        sphere.dispose(gl);
        cube.dispose(gl);
    }
}
