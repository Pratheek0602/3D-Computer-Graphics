import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.util.texture.awt.*;
import com.jogamp.opengl.util.texture.spi.JPEGImage;

public class Robot1 {
    private Camera camera;
    private Light[] light;
    private int[] attributeSizes = {3, 3, 2}; // positions, normals, texture coordinates

    private ModelMultipleLights sphere, cube, triangle, eyes;
    private SGNode robotRoot;
    private float xPosition = -1.1f;
    private float yPosition = 0f;
    private float zPosition = -1.0f;
    private TransformNode robotMoveTranslate, leftArmRotate, rightArmRotate;

    private float moveDuration = 2.0f; // Each move lasts 2 seconds
    private float currentMoveTime = 0.0f;
    private boolean isAutoDancing = false;
    private boolean isManualDancing = false; // New flag for manual dancing

    private float danceTimer = 0;
    private float danceSpeed = 4.0f; 

    private TransformNode robotRotate, headBobTransform;
    private int currentDanceMove = 0;
    private static final int TOTAL_DANCE_MOVES = 3;


    public Robot1(GL3 gl, Camera cameraIn, Light[] lightIn, Texture t1, Texture t2, Texture t3) {
        this.camera = cameraIn;
        this.light = lightIn;

        // Create basic shapes
        sphere = makeSphere(gl, t1);
        cube = makeCube(gl, t3);
        eyes = makeEyes(gl, t2);
        triangle = makePyramid(gl, t1);

        // Robot dimensions (adjusted to match sketch)
        float headRadius = 0.37f;
        float upperBodyRadius = 0.3f;
        float middleBodyRadius = 0.3f;
        float lowerBodyRadius = 0.3f;
        float armLength = 0.2f;
        float armWidth = 0.03f;
        float antennaHeight = 0.15f;
        float antennaWidth = 0.02f;
        float neckHeight = 0.08f;
        float neckWidth = 0.07f;

        robotRotate = new TransformNode("robot rotate", Mat4Transform.rotateAroundY(0));
        headBobTransform = new TransformNode("head bob", Mat4Transform.translate(0, 0, 0));

        leftArmRotate = new TransformNode("left arm rotate", Mat4Transform.rotateAroundX(0));
        rightArmRotate = new TransformNode("right arm rotate", Mat4Transform.rotateAroundX(0)); 

        // Create scene graph
        robotRoot = new NameNode("root");
        robotMoveTranslate = new TransformNode("robot transform", Mat4Transform.translate(xPosition, yPosition, zPosition));

        // Build the robot parts
        NameNode head = makeHead(gl, headRadius, antennaHeight, antennaWidth, neckHeight, neckWidth);
        NameNode topBody = makeBodySegment(gl, upperBodyRadius, true, false);   // Inverted triangle for top
        NameNode middleBody = makeBodySegment(gl, middleBodyRadius, false, true); // Normal triangle for middle
        NameNode bottomBody = makeBodySegment(gl, lowerBodyRadius, false, false);
        NameNode leftArm = makeArm(gl, armLength, armWidth, true);
        NameNode rightArm = makeArm(gl, armLength, armWidth, false);

        // Assemble the robot
        robotRoot.addChild(robotMoveTranslate);
        robotMoveTranslate.addChild(robotRotate);
        robotRotate.addChild(bottomBody);
        bottomBody.addChild(middleBody);
        middleBody.addChild(topBody);
        topBody.addChild(headBobTransform);
        headBobTransform.addChild(head);
        middleBody.addChild(leftArm);
        middleBody.addChild(rightArm);
        
        robotRoot.update();
    }


    private NameNode makeHead(GL3 gl, float radius, float antennaHeight, float antennaWidth, float neckHeight, float neckWidth) {
        NameNode head = new NameNode("head");
        
        // Head dome
        Mat4 headTransform = new Mat4(1);
        headTransform = Mat4.multiply(headTransform, Mat4Transform.translate(0, 0.46f, 0));
        headTransform = Mat4.multiply(headTransform, Mat4Transform.scale(radius, radius * 0.5f, radius));
        TransformNode headNode = new TransformNode("head dome", headTransform);
        ModelNode headShape = new ModelNode("head shape", sphere);
        
        // Antenna1
        Mat4 antennaTransform = new Mat4(1);
        antennaTransform = Mat4.multiply(antennaTransform, Mat4Transform.translate(0.10f, 0.56f, 0f));
        antennaTransform = Mat4.multiply(antennaTransform, Mat4Transform.scale(antennaWidth, antennaHeight, antennaWidth));
        TransformNode antennaNode = new TransformNode("antenna", antennaTransform);
        ModelNode antennaShape = new ModelNode("antenna shape", cube);

        // Antenna2
        Mat4 antennaTransform2 = new Mat4(1);
        antennaTransform2 = Mat4.multiply(antennaTransform2, Mat4Transform.translate(-0.10f, 0.56f, 0));
        antennaTransform2 = Mat4.multiply(antennaTransform2, Mat4Transform.scale(antennaWidth, antennaHeight, antennaWidth));
        TransformNode antennaNode2 = new TransformNode("antenna", antennaTransform2);
        ModelNode antennaShape2 = new ModelNode("antenna shape", cube);

        // Neck
        Mat4 neckTransform = new Mat4(1);
        neckTransform = Mat4.multiply(neckTransform, Mat4Transform.translate(0, 0.39f, 0));
        neckTransform = Mat4.multiply(neckTransform, Mat4Transform.scale(neckWidth, neckHeight, neckWidth));
        TransformNode neckNode = new TransformNode("neck", neckTransform);
        ModelNode neckShape = new ModelNode("neck shape", cube);

        // Eyes - made larger and more rectangular
        Mat4 leftEyeTransform = new Mat4(1);
        leftEyeTransform = Mat4.multiply(leftEyeTransform, Mat4Transform.translate(-0.13f, 0.50f, 0.14f));
        leftEyeTransform = Mat4.multiply(leftEyeTransform, Mat4Transform.scale(0.08f, 0.02f, 0.08f));
        TransformNode leftEyeNode = new TransformNode("left eye", leftEyeTransform);
        ModelNode leftEyeShape = new ModelNode("left eye shape", eyes);
        
        Mat4 rightEyeTransform = new Mat4(1);
        rightEyeTransform = Mat4.multiply(rightEyeTransform, Mat4Transform.translate(0.13f, 0.50f, 0.14f));
        rightEyeTransform = Mat4.multiply(rightEyeTransform, Mat4Transform.scale(0.08f, 0.02f, 0.08f));
        TransformNode rightEyeNode = new TransformNode("right eye", rightEyeTransform);
        ModelNode rightEyeShape = new ModelNode("right eye shape", eyes);
        
        // Assemble head
        head.addChild(headNode);
        headNode.addChild(headShape);

        head.addChild(antennaNode);
        antennaNode.addChild(antennaShape);

        head.addChild(antennaNode2);
        antennaNode2.addChild(antennaShape2);

        head.addChild(neckNode);
        neckNode.addChild(neckShape);

        head.addChild(leftEyeNode);
        leftEyeNode.addChild(leftEyeShape);

        head.addChild(rightEyeNode);
        rightEyeNode.addChild(rightEyeShape);
        
        return head;
    }


    private ModelMultipleLights makeSphere(GL3 gl, Texture t1) {
        Mesh mesh = new Mesh(gl, HemiSphere.vertices.clone(), HemiSphere.indices.clone(), attributeSizes);
        Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_standard.txt");
        Material material = new Material(new Vec3(1.2f, 1.2f, 1.2f), new Vec3(1.2f, 1.2f, 1.2f), new Vec3(1.2f, 1.2f, 1.2f), 4.0f, 1.0f);
        return new ModelMultipleLights("sphere", mesh, new Mat4(1), shader, material, light, camera, t1);
    }

    private ModelMultipleLights makeCube(GL3 gl, Texture t1) {
        Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone(), attributeSizes);
        Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_standard.txt");
        Material material = new Material(new Vec3(0.7f, 0.7f, 0.7f), new Vec3(0.7f, 0.7f, 0.7f), new Vec3(0.7f, 0.7f, 0.7f), 4.0f, 1.0f);
        return new ModelMultipleLights("cube", mesh, new Mat4(1), shader, material, light, camera, t1);
    }

    private ModelMultipleLights makePyramid(GL3 gl, Texture t1) {
        Mesh mesh = new Mesh(gl, Pyramid.vertices.clone(), Pyramid.indices.clone(), attributeSizes);
        Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_standard.txt");
        
        // Set the material to match the head's brightness levels
        Material material = new Material(new Vec3(2.0f, 2.0f, 2.0f), new Vec3(2.0f, 2.0f, 2.0f), new Vec3(2.0f, 2.0f, 2.0f), 4.0f, 1.0f);
        return new ModelMultipleLights("pyramid", mesh, new Mat4(1), shader, material, light, camera, t1);
    }

    private NameNode makeBodySegment(GL3 gl, float radius, boolean isTop, boolean isMiddle) {
        NameNode segment = new NameNode(isTop ? "top body" : (isMiddle ? "middle body" : "bottom body"));
        
        // Set Y-offset based on position in the body
        float yOffset = isTop ? radius * 1.0f : (isMiddle ? radius * 0.6f : radius * 0.2f);
        
        // Apply rotation for the top (inverted) segment
        Mat4 transform = new Mat4(1);
        transform = Mat4.multiply(transform, Mat4Transform.translate(0, yOffset, 0));
        if (isTop) {
            transform = Mat4.multiply(transform, Mat4Transform.rotateAroundZ(180));  // Invert the top triangle
        }
        transform = Mat4.multiply(transform, Mat4Transform.scale(radius, radius * 0.4f, radius));

        TransformNode transformNode = new TransformNode("body segment transform", transform);
        ModelNode shape = new ModelNode("triangle shape", triangle);  // Use the pyramid shape for each segment
        
        segment.addChild(transformNode);
        transformNode.addChild(shape);
        
        return segment;
    }

    private NameNode makeArm(GL3 gl, float length, float width, boolean isLeft) {
        NameNode arm = new NameNode(isLeft ? "left arm" : "right arm");

        // Create translation node for arm position
        TransformNode armTranslate = new TransformNode("arm translate",
            Mat4Transform.translate(isLeft ? -0.2f : 0.2f, 0.27f, 0));

        // Create rotation node for arm movement
        TransformNode armRotate = new TransformNode("arm rotate", Mat4Transform.rotateAroundZ(isLeft ? 40f : -40f));
        if (isLeft) {
            leftArmRotate = armRotate; 
        } else {
            rightArmRotate = armRotate; 
        }

        // Scale the arm
        Mat4 armScale = Mat4Transform.scale(length, width, width);
        TransformNode armScaleNode = new TransformNode("arm scale", armScale);

        // Arm shape
        ModelNode armShape = new ModelNode("arm shape", cube);

        // Assemble arm
        arm.addChild(armTranslate);
        armTranslate.addChild(armRotate);
        armRotate.addChild(armScaleNode);
        armScaleNode.addChild(armShape);

        return arm;
    }


    private ModelMultipleLights makeEyes(GL3 gl, Texture t1) {
        Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone(), attributeSizes);
        Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_standard.txt");
        Material material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 4.0f, 1.0f);
        return new ModelMultipleLights("eyes", mesh, new Mat4(1), shader, material, light, camera, t1);
    }


    public boolean isCurrentlyDancing() {
        return isAutoDancing || isManualDancing;
    }

    public void updateDance(float deltaTime) {
        if (!isCurrentlyDancing()) return;
        
        danceTimer += deltaTime * danceSpeed;
        currentMoveTime += deltaTime;
        
        // Change move every moveDuration seconds
        if (currentMoveTime >= moveDuration) {
            currentMoveTime = 0;
            changeDanceMove();
        }
        
        switch (currentDanceMove) {
            case 0: // The Robot
                robotStyleDance(deltaTime);
                break;
            case 1: // The Twist
                twistDance(deltaTime);
                break;
            case 2: // The Bounce
                bounceDance(deltaTime);
                break;
        }
        
        robotRoot.update();
    }

    /**
     * Starts dancing automatically based on proximity.
     */
    public void startAutoDancing() {
        isAutoDancing = true;
        currentDanceMove = 0;
        currentMoveTime = 0;
        danceTimer = 0;
        resetDanceTransforms();
        robotRoot.update();
    }

    /**
     * Stops dancing automatically based on proximity.
     */
    public void stopAutoDancing() {
        isAutoDancing = false;
        resetDanceTransforms();
        robotRoot.update();
    }

    /**
     * Starts dancing manually, independent of proximity.
     */
    public void startManualDancing() {
        isManualDancing = true;
        currentDanceMove = 0;
        currentMoveTime = 0;
        danceTimer = 0;
        resetDanceTransforms();
        robotRoot.update();
    }
        
    /**
     * Stops dancing manually.
     */
    public void stopManualDancing() {
        isManualDancing = false;
        resetDanceTransforms();
        robotRoot.update();
    }

    private void robotStyleDance(float deltaTime) {
        // Smooth arm movement
        float armAngle = (float) Math.sin(danceTimer * 2) * 45;
        leftArmRotate.setTransform(Mat4Transform.rotateAroundX(armAngle));
        rightArmRotate.setTransform(Mat4Transform.rotateAroundX(-armAngle));
        
        // Smooth head bob
        float headBob = (float) Math.sin(danceTimer * 2) * 0.03f;
        headBobTransform.setTransform(Mat4Transform.translate(0, headBob, 0));
        
        // Continuous body rotation
        float bodyAngle = (float) (Math.sin(danceTimer) * 45); // Adjust the factor (45) for desired smooth rotation
        robotRotate.setTransform(Mat4Transform.rotateAroundY(bodyAngle));
    }

    private void twistDance(float deltaTime) {
        // Smooth twisting motion
        float twist = (float) Math.sin(danceTimer) * 45;
        robotRotate.setTransform(Mat4Transform.rotateAroundY(twist));
        
        // Arms swing in opposite directions
        float armAngle = (float) Math.sin(danceTimer) * 30;
        leftArmRotate.setTransform(Mat4Transform.rotateAroundZ(-armAngle));
        rightArmRotate.setTransform(Mat4Transform.rotateAroundZ(armAngle));
    }


    private void bounceDance(float deltaTime) {
        // Bouncing motion
        float bounce = Math.abs((float) Math.sin(danceTimer)) * 0.2f;
        robotMoveTranslate.setTransform(Mat4Transform.translate(xPosition, bounce, zPosition));
        
        // Arms bounce up and down
        float armAngle = (float) Math.sin(danceTimer * 2) * 20;

        leftArmRotate.setTransform(Mat4Transform.rotateAroundZ(-armAngle));  // Moving towards front
        rightArmRotate.setTransform(Mat4Transform.rotateAroundZ(armAngle));
    }

    public void changeDanceMove() {
        currentDanceMove = (currentDanceMove + 1) % TOTAL_DANCE_MOVES;
        // Reset transforms when changing moves
        resetDanceTransforms();
    }

    private void resetDanceTransforms() {
        robotRotate.setTransform(Mat4Transform.rotateAroundY(0));
        headBobTransform.setTransform(Mat4Transform.translate(0, 0, 0));
        leftArmRotate.setTransform(Mat4Transform.rotateAroundZ(40f));
        rightArmRotate.setTransform(Mat4Transform.rotateAroundZ(-40f));
        robotMoveTranslate.setTransform(Mat4Transform.translate(xPosition, yPosition, zPosition));
    }


    public void render(GL3 gl) {
        robotRoot.draw(gl);
    }

    // Getter method for Robot1's position
    public Vec3 getRobotPosition() {
        return new Vec3(xPosition, yPosition, zPosition);
    }


    public boolean isAutoDancing() {
        return isAutoDancing;
    }

    public boolean isManualDancing() {
        return isManualDancing;
    }

    // Added dispose method
    public void dispose(GL3 gl) {
        sphere.dispose(gl);
        cube.dispose(gl);
        triangle.dispose(gl);
    }
}
