import gmaths.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.util.texture.awt.*;
import com.jogamp.opengl.util.texture.spi.JPEGImage;

public class ModelMultipleLights {

  private String name;
  private Mesh mesh;
  private Mat4 modelMatrix;
  private Shader shader;
  private Material material;
  private Camera camera;
  private Light[] lights;
  private Texture diffuse;
  private Texture specular;

  // Spotlight parameters
  private Vec3 spotlightPosition = null;
  private Vec3 spotlightDirection = null;
  private float spotlightCutOff = 0.0f;
  private float spotlightOuterCutOff = 0.0f;
  private Vec3 spotlightAmbient = new Vec3(0.0f, 0.0f, 0.0f);
  private Vec3 spotlightDiffuse = new Vec3(0.0f, 0.0f, 0.0f);
  private Vec3 spotlightSpecular = new Vec3(0.0f, 0.0f, 0.0f);
  private float spotlightConstant = 1.0f;
  private float spotlightLinear = 0.0f;
  private float spotlightQuadratic = 0.0f;

  public ModelMultipleLights() {
    name = null;
    mesh = null;
    modelMatrix = null;
    material = null;
    camera = null;
    lights = null;
    shader = null;
  }

  public ModelMultipleLights(String name, Mesh mesh, Mat4 modelMatrix, Shader shader, Material material, Light[] lights,
      Camera camera, Texture diffuse, Texture specular) {
    this.name = name;
    this.mesh = mesh;
    this.modelMatrix = modelMatrix;
    this.shader = shader;
    this.material = material;
    this.lights = lights;
    this.camera = camera;
    this.diffuse = diffuse;
    this.specular = specular;
  }

  public ModelMultipleLights(String name, Mesh mesh, Mat4 modelMatrix, Shader shader, Material material, Light[] lights,
      Camera camera, Texture diffuse) {
    this(name, mesh, modelMatrix, shader, material, lights, camera, diffuse, null);
  }

  public ModelMultipleLights(String name, Mesh mesh, Mat4 modelMatrix, Shader shader, Material material, Light[] lights,
      Camera camera) {
    this(name, mesh, modelMatrix, shader, material, lights, camera, null, null);
  }

  public void setName(String s) {
    this.name = s;
  }

  public void setMesh(Mesh m) {
    this.mesh = m;
  }

  public void setModelMatrix(Mat4 m) {
    modelMatrix = m;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  public void setShader(Shader shader) {
    this.shader = shader;
  }

  public void setCamera(Camera camera) {
    this.camera = camera;
  }

  public void setLights(Light[] lights) {
    this.lights = lights;
  }

  public void setDiffuse(Texture t) {
    this.diffuse = t;
  }

  public void setSpecular(Texture t) {
    this.specular = t;
  }

  public void setSpotlightPosition(Vec3 position) {
    this.spotlightPosition = position;
  }

  public void setSpotlightDirection(Vec3 direction) {
    this.spotlightDirection = direction;
  }

  public void setSpotlightCutOff(float cutOff) {
    this.spotlightCutOff = cutOff;
  }

  public void setSpotlightOuterCutOff(float outerCutOff) {
    this.spotlightOuterCutOff = outerCutOff;
  }

  public void setSpotlightAmbient(Vec3 ambient) {
    this.spotlightAmbient = ambient;
  }

  public void setSpotlightDiffuse(Vec3 diffuse) {
    this.spotlightDiffuse = diffuse;
  }

  public void setSpotlightSpecular(Vec3 specular) {
    this.spotlightSpecular = specular;
  }

  public void setSpotlightAttenuation(float constant, float linear, float quadratic) {
    this.spotlightConstant = constant;
    this.spotlightLinear = linear;
    this.spotlightQuadratic = quadratic;
  }

  public void render(GL3 gl) {
    render(gl, modelMatrix);
  }

  public void render(GL3 gl, Mat4 modelMatrix) {
    if (mesh_null()) {
        System.out.println("Error: null in model render");
        return;
    }
    Mat4 mvpMatrix = Mat4.multiply(camera.getPerspectiveMatrix(), Mat4.multiply(camera.getViewMatrix(), modelMatrix));
    shader.use(gl);
    shader.setFloatArray(gl, "model", modelMatrix.toFloatArrayForGLSL());
    shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());

    shader.setVec3(gl, "viewPos", camera.getPosition());

    shader.setInt(gl, "numLights", lights.length);

    for (int i = 0; i < lights.length; i++) {
        shader.setVec3(gl, "lights[" + i + "].position", lights[i].getPosition());
        shader.setVec3(gl, "lights[" + i + "].ambient", lights[i].getMaterial().getAmbient());
        shader.setVec3(gl, "lights[" + i + "].diffuse", lights[i].getMaterial().getDiffuse());
        shader.setVec3(gl, "lights[" + i + "].specular", lights[i].getMaterial().getSpecular());
    }

    // Set spotlight uniforms if they are not null
    if (spotlightPosition != null && spotlightDirection != null) {
        shader.setVec3(gl, "spotlight.position", spotlightPosition);
        shader.setVec3(gl, "spotlight.direction", spotlightDirection);
        shader.setFloat(gl, "spotlight.cutOff", spotlightCutOff);
        shader.setFloat(gl, "spotlight.outerCutOff", spotlightOuterCutOff);
        shader.setVec3(gl, "spotlight.ambient", spotlightAmbient);
        shader.setVec3(gl, "spotlight.diffuse", spotlightDiffuse);
        shader.setVec3(gl, "spotlight.specular", spotlightSpecular);
        shader.setFloat(gl, "spotlight.constant", spotlightConstant);
        shader.setFloat(gl, "spotlight.linear", spotlightLinear);
        shader.setFloat(gl, "spotlight.quadratic", spotlightQuadratic);
    }

    shader.setVec3(gl, "material.ambient", material.getAmbient());
    shader.setVec3(gl, "material.diffuse", material.getDiffuse());
    shader.setVec3(gl, "material.specular", material.getSpecular());
    shader.setFloat(gl, "material.shininess", material.getShininess());
    shader.setFloat(gl, "alpha", material.getAlpha()); 

    if (diffuse != null) {
        shader.setInt(gl, "first_texture", 0);  
        gl.glActiveTexture(GL.GL_TEXTURE0);
        diffuse.bind(gl);
    }
    if (specular != null) {
        shader.setInt(gl, "second_texture", 1);
        gl.glActiveTexture(GL.GL_TEXTURE1);
        specular.bind(gl);
    }

    // Render the mesh
    mesh.render(gl);
  }

  private boolean mesh_null() {
    return (mesh == null);
  }

  public void dispose(GL3 gl) {
    mesh.dispose(gl);  
  }
}
