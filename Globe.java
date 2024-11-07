// Globe.java

import gmaths.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.*;

public class Globe {
    private Camera camera;
    private Light[] lights;
    private Texture globeTexture;
    private Texture pedestalTexture;
    
    private ModelMultipleLights globeSphere;
    private ModelMultipleLights axisSphere;
    private ModelMultipleLights pedestalCube;
    private TransformNode globeTransformNode;
    
    private SGNode globeRoot;
    
    // Group transformation parameters
    private TransformNode globeGroupTransform;


    private float xPosition = 0.8f;
    private float yPosition = 0.1f;
    private float zPosition = 1.8f;
    
    // Rotation parameters for the globe
    private float rotationSpeed = 20.0f; 
    private float currentRotation = 0.0f;

    public Globe(GL3 gl, Camera camera, Light[] lights, Texture globeTexture, Texture pedestalTexture) {
        this.camera = camera;
        this.lights = lights;
        this.globeTexture = globeTexture;
        this.pedestalTexture = pedestalTexture;
        
        // Initialize models
        initialiseModels(gl);
        
        // Build scene graph
        buildSceneGraph();
    }
    
    /**
     * Initializes the models: globe sphere, axis sphere, and pedestal cube.
     */
    private void initialiseModels(GL3 gl) {
        // Create sphere for the globe using existing Sphere class
        globeSphere = makeSphere(gl, globeTexture);
        if (globeSphere == null) {
            System.err.println("Error: globeSphere failed to initialize.");
        }
        
        // Create sphere for the central axis using existing Sphere class
        axisSphere = makeSphere(gl, pedestalTexture);
        if (axisSphere == null) {
            System.err.println("Error: axisSphere failed to initialize.");
        }
        
        // Create cube for the pedestal using existing Cube class
        pedestalCube = makeCube(gl, pedestalTexture);
        if (pedestalCube == null) {
            System.err.println("Error: pedestalCube failed to initialize.");
        }
    }
    
    /**
     * Builds the scene graph, grouping pedestal, axis, and globe under a single TransformNode.
     */
    private void buildSceneGraph() {
        globeRoot = new NameNode("Globe Root");
        
        // Parent TransformNode for grouping
        globeGroupTransform = new TransformNode("Globe Group Transform", 
            Mat4Transform.translate(xPosition, yPosition, zPosition) // Initial position of the group
        );
        
        // Pedestal
        TransformNode pedestalTransform = new TransformNode("Pedestal Transform", 
            Mat4Transform.scale(0.15f, 0.15f, 0.15f) // Increased scale for visibility
        );
        ModelNode pedestalModel = new ModelNode("Pedestal Model", pedestalCube);
        pedestalTransform.addChild(pedestalModel);
        
        // Central Axis
        TransformNode axisTransform = new TransformNode("Axis Transform", 
            Mat4.multiply(
                Mat4Transform.translate(0, 0.23f, 0), // Positioned atop the pedestal
                Mat4Transform.scale(0.02f, 0.47f, 0.02f) // Slender axis
            )
        );
        ModelNode axisModel = new ModelNode("Axis Model", axisSphere);
        axisTransform.addChild(axisModel);
        
        // Globe
        globeTransformNode = new TransformNode("Globe Transform", 
            Mat4.multiply(
                Mat4Transform.translate(0, 0.25f, 0), // Positioned at the end of the axis
                Mat4Transform.scale(0.25f, 0.25f, 0.25f)    // Scaled down for proportion
            )
        );
        ModelNode globeModel = new ModelNode("Globe Model", globeSphere);
        globeTransformNode.addChild(globeModel);
        
        // Assemble the scene graph
        globeGroupTransform.addChild(pedestalTransform);
        globeGroupTransform.addChild(axisTransform);
        globeGroupTransform.addChild(globeTransformNode);
        
        globeRoot.addChild(globeGroupTransform);
        
        globeRoot.update();
    }
    

    public void update(float deltaTime) {
        currentRotation += rotationSpeed * deltaTime;
        if (currentRotation >= 360.0f) {
            currentRotation -= 360.0f;
        }
        
        // Apply rotation to the globeTransformNode (only the globe rotates)
        if (globeTransformNode != null) {
            // Create rotation matrix around Y-axis
            Mat4 rotation = Mat4Transform.rotateAroundY(currentRotation);
            
            // Combine translation and scaling with rotation
            Mat4 finalTransform = Mat4.multiply(
                Mat4Transform.translate(0f, 0.27f, 0), // Ensure consistent positioning
                Mat4.multiply(
                    rotation,
                    Mat4Transform.scale(0.25f, 0.25f, 0.25f) // Maintain scaling
                )
            );
            
            // Set the final transformation to the globe's transform node
            globeTransformNode.setTransform(finalTransform);
        } else {
            System.err.println("Error: 'Globe Transform' node reference is null.");
        }
        
        // Update the entire scene graph
        globeRoot.update();
    }
        

    public void render(GL3 gl) {
        globeRoot.draw(gl);
    }


    public void dispose(GL3 gl) {
        globeSphere.dispose(gl);
        axisSphere.dispose(gl);
        pedestalCube.dispose(gl);
    }

    private ModelMultipleLights makeSphere(GL3 gl, Texture texture) {
        if (texture == null) {
            System.err.println("Error: Texture is null for sphere.");
        }
        // Reuse your existing Sphere class
        // Assuming Sphere.vertices and Sphere.indices are accessible
        Mesh mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone(), new int[] {3, 3, 2});
        Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_standard.txt");
        Material material = new Material(
            new Vec3(1.0f, 1.0f, 1.0f),  // Ambient (Red for visibility)
            new Vec3(1.0f, 1.0f, 1.0f),  // Diffuse
            new Vec3(1.0f, 1.0f, 1.0f),  // Specular
            32.0f,                        // Shininess
            1.0f                          // Alpha
        );
        return new ModelMultipleLights("sphere", mesh, new Mat4(1), shader, material, lights, camera, texture);
    }

    private ModelMultipleLights makeCube(GL3 gl, Texture texture) {
        if (texture == null) {
            System.err.println("Error: Texture is null for cube.");
        }
        // Reuse your existing Cube class
        // Assuming Cube.vertices and Cube.indices are accessible
        Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone(), new int[] {3, 3, 2});
        Shader shader = new Shader(gl, "assets/shaders/vs_standard.txt", "assets/shaders/fs_standard.txt");
        Material material = new Material(
            new Vec3(1.0f, 1.0f, 1.0f),  // Ambient (Red for visibility)
            new Vec3(1.0f, 1.0f, 1.0f),  // Diffuse
            new Vec3(1.0f, 1.0f, 1.0f),// Specular
            32.0f,
            1.0f  
        );
        return new ModelMultipleLights("cube", mesh, new Mat4(1), shader, material, lights, camera, texture);
    }
}
