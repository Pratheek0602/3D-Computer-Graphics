/* !---- DO NOT EDIT: This file autogenerated by com/jogamp/gluegen/opengl/GLEmitter.java on Fri Aug 18 14:57:39 CEST 2023 ----! */
/* !---- Java-Unit: [pkg com.jogamp.opengl, cls GL2ES1], ../build/jogl/gensrc/classes/com/jogamp/opengl/GL2ES1.java ----! */

package com.jogamp.opengl;

import java.util.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.*;
import jogamp.opengl.*;
import com.jogamp.gluegen.runtime.*;
import com.jogamp.common.os.*;
import com.jogamp.common.nio.*;
import java.nio.*;
import com.jogamp.common.util.*;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

 /**
  * <p> 
  * Interface containing the common subset of GL2 and GLES1.<br/>
  * This interface reflects only the fixed functionality of OpenGL<br/>
  * </p>
  */
public interface GL2ES1 extends GL, GLMatrixFunc, GLPointerFunc, GLLightingFunc{

  /** <code>GL_VERSION_1_4</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_SGIS_point_parameters</code>, <code>GL_EXT_point_parameters</code>, <code>GL_ARB_point_parameters</code><br>Alias for: <code>GL_POINT_SIZE_MAX_SGIS</code>, <code>GL_POINT_SIZE_MAX_EXT</code>, <code>GL_POINT_SIZE_MAX_ARB</code><br>
Define "GL_POINT_SIZE_MAX" with expression '<code>0x8127</code>', CType: int */
  public static final int GL_POINT_SIZE_MAX = 0x8127;
  /** <code>GL_VERSION_1_5</code>, <code>GL_VERSION_ES_1_0</code><br>Define "GL_SRC2_ALPHA" with expression '<code>0x858A</code>', CType: int */
  public static final int GL_SRC2_ALPHA = 0x858a;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_MODELVIEW_STACK_DEPTH" with expression '<code>0x0BA3</code>', CType: int */
  public static final int GL_MODELVIEW_STACK_DEPTH = 0xba3;
  /** <code>GL_VERSION_1_5</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_ARB_vertex_buffer_object</code><br>Alias for: <code>GL_NORMAL_ARRAY_BUFFER_BINDING_ARB</code><br>
Define "GL_NORMAL_ARRAY_BUFFER_BINDING" with expression '<code>0x8897</code>', CType: int */
  public static final int GL_NORMAL_ARRAY_BUFFER_BINDING = 0x8897;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_COLOR_ARRAY_POINTER_EXT</code><br>
Define "GL_COLOR_ARRAY_POINTER" with expression '<code>0x8090</code>', CType: int */
  public static final int GL_COLOR_ARRAY_POINTER = 0x8090;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_PREVIOUS_EXT</code>, <code>GL_PREVIOUS_ARB</code><br>
Define "GL_PREVIOUS" with expression '<code>0x8578</code>', CType: int */
  public static final int GL_PREVIOUS = 0x8578;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_OPERAND0_RGB_EXT</code>, <code>GL_OPERAND0_RGB_ARB</code><br>
Define "GL_OPERAND0_RGB" with expression '<code>0x8590</code>', CType: int */
  public static final int GL_OPERAND0_RGB = 0x8590;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_VERTEX_ARRAY_SIZE_EXT</code><br>
Define "GL_VERTEX_ARRAY_SIZE" with expression '<code>0x807A</code>', CType: int */
  public static final int GL_VERTEX_ARRAY_SIZE = 0x807a;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_FOG_HINT" with expression '<code>0x0C54</code>', CType: int */
  public static final int GL_FOG_HINT = 0xc54;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_CURRENT_TEXTURE_COORDS" with expression '<code>0x0B03</code>', CType: int */
  public static final int GL_CURRENT_TEXTURE_COORDS = 0xb03;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_CURRENT_COLOR" with expression '<code>0x0B00</code>', CType: int */
  public static final int GL_CURRENT_COLOR = 0xb00;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_VERTEX_ARRAY_TYPE_EXT</code><br>
Define "GL_VERTEX_ARRAY_TYPE" with expression '<code>0x807B</code>', CType: int */
  public static final int GL_VERTEX_ARRAY_TYPE = 0x807b;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_ARB_multitexture</code><br>Alias for: <code>GL_MAX_TEXTURE_UNITS_ARB</code><br>
Define "GL_MAX_TEXTURE_UNITS" with expression '<code>0x84E2</code>', CType: int */
  public static final int GL_MAX_TEXTURE_UNITS = 0x84e2;
  /** <code>GL_VERSION_1_5</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_ARB_vertex_buffer_object</code><br>Alias for: <code>GL_VERTEX_ARRAY_BUFFER_BINDING_ARB</code><br>
Define "GL_VERTEX_ARRAY_BUFFER_BINDING" with expression '<code>0x8896</code>', CType: int */
  public static final int GL_VERTEX_ARRAY_BUFFER_BINDING = 0x8896;
  /** <code>GL_VERSION_1_2</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_rescale_normal</code><br>Alias for: <code>GL_RESCALE_NORMAL_EXT</code><br>
Define "GL_RESCALE_NORMAL" with expression '<code>0x803A</code>', CType: int */
  public static final int GL_RESCALE_NORMAL = 0x803a;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_VERTEX_ARRAY_STRIDE_EXT</code><br>
Define "GL_VERTEX_ARRAY_STRIDE" with expression '<code>0x807C</code>', CType: int */
  public static final int GL_VERTEX_ARRAY_STRIDE = 0x807c;
  /** <code>GL_VERSION_1_5</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_ARB_vertex_buffer_object</code><br>Alias for: <code>GL_COLOR_ARRAY_BUFFER_BINDING_ARB</code><br>
Define "GL_COLOR_ARRAY_BUFFER_BINDING" with expression '<code>0x8898</code>', CType: int */
  public static final int GL_COLOR_ARRAY_BUFFER_BINDING = 0x8898;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_IMG_user_clip_plane</code><br>Alias for: <code>GL_CLIP_PLANE0_IMG</code><br>
Define "GL_CLIP_PLANE0" with expression '<code>0x3000</code>', CType: int */
  public static final int GL_CLIP_PLANE0 = 0x3000;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_IMG_user_clip_plane</code><br>Alias for: <code>GL_CLIP_PLANE1_IMG</code><br>
Define "GL_CLIP_PLANE1" with expression '<code>0x3001</code>', CType: int */
  public static final int GL_CLIP_PLANE1 = 0x3001;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_IMG_user_clip_plane</code><br>Alias for: <code>GL_CLIP_PLANE2_IMG</code><br>
Define "GL_CLIP_PLANE2" with expression '<code>0x3002</code>', CType: int */
  public static final int GL_CLIP_PLANE2 = 0x3002;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_IMG_user_clip_plane</code><br>Alias for: <code>GL_CLIP_PLANE3_IMG</code><br>
Define "GL_CLIP_PLANE3" with expression '<code>0x3003</code>', CType: int */
  public static final int GL_CLIP_PLANE3 = 0x3003;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_RGB_SCALE_EXT</code>, <code>GL_RGB_SCALE_ARB</code><br>
Define "GL_RGB_SCALE" with expression '<code>0x8573</code>', CType: int */
  public static final int GL_RGB_SCALE = 0x8573;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_IMG_user_clip_plane</code><br>Alias for: <code>GL_CLIP_PLANE4_IMG</code><br>
Define "GL_CLIP_PLANE4" with expression '<code>0x3004</code>', CType: int */
  public static final int GL_CLIP_PLANE4 = 0x3004;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_IMG_user_clip_plane</code><br>Alias for: <code>GL_CLIP_PLANE5_IMG</code><br>
Define "GL_CLIP_PLANE5" with expression '<code>0x3005</code>', CType: int */
  public static final int GL_CLIP_PLANE5 = 0x3005;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_QCOM_alpha_test</code><br>Alias for: <code>GL_ALPHA_TEST_QCOM</code><br>
Define "GL_ALPHA_TEST" with expression '<code>0x0BC0</code>', CType: int */
  public static final int GL_ALPHA_TEST = 0xbc0;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_COLOR_ARRAY_STRIDE_EXT</code><br>
Define "GL_COLOR_ARRAY_STRIDE" with expression '<code>0x8083</code>', CType: int */
  public static final int GL_COLOR_ARRAY_STRIDE = 0x8083;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_MAX_LIGHTS" with expression '<code>0x0D31</code>', CType: int */
  public static final int GL_MAX_LIGHTS = 0xd31;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_COMBINE_RGB_EXT</code>, <code>GL_COMBINE_RGB_ARB</code><br>
Define "GL_COMBINE_RGB" with expression '<code>0x8571</code>', CType: int */
  public static final int GL_COMBINE_RGB = 0x8571;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_PROJECTION_STACK_DEPTH" with expression '<code>0x0BA4</code>', CType: int */
  public static final int GL_PROJECTION_STACK_DEPTH = 0xba4;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_OPERAND1_ALPHA_EXT</code>, <code>GL_OPERAND1_ALPHA_ARB</code><br>
Define "GL_OPERAND1_ALPHA" with expression '<code>0x8599</code>', CType: int */
  public static final int GL_OPERAND1_ALPHA = 0x8599;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_NORMAL_ARRAY_POINTER_EXT</code><br>
Define "GL_NORMAL_ARRAY_POINTER" with expression '<code>0x808F</code>', CType: int */
  public static final int GL_NORMAL_ARRAY_POINTER = 0x808f;
  /** <code>GL_ES_VERSION_3_0</code>, <code>GL_ARB_framebuffer_object</code>, <code>GL_VERSION_3_0</code>, <code>GL_EXT_sRGB</code><br>Alias for: <code>GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING_EXT</code><br>
Define "GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING" with expression '<code>0x8210</code>', CType: int */
  public static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 0x8210;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_ARB_multitexture</code><br>Alias for: <code>GL_CLIENT_ACTIVE_TEXTURE_ARB</code><br>
Define "GL_CLIENT_ACTIVE_TEXTURE" with expression '<code>0x84E1</code>', CType: int */
  public static final int GL_CLIENT_ACTIVE_TEXTURE = 0x84e1;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_TEXTURE_STACK_DEPTH" with expression '<code>0x0BA5</code>', CType: int */
  public static final int GL_TEXTURE_STACK_DEPTH = 0xba5;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_PERSPECTIVE_CORRECTION_HINT" with expression '<code>0x0C50</code>', CType: int */
  public static final int GL_PERSPECTIVE_CORRECTION_HINT = 0xc50;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_LIGHT_MODEL_TWO_SIDE" with expression '<code>0x0B52</code>', CType: int */
  public static final int GL_LIGHT_MODEL_TWO_SIDE = 0xb52;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_TEXTURE_COORD_ARRAY_TYPE_EXT</code><br>
Define "GL_TEXTURE_COORD_ARRAY_TYPE" with expression '<code>0x8089</code>', CType: int */
  public static final int GL_TEXTURE_COORD_ARRAY_TYPE = 0x8089;
  /** <code>GL_VERSION_1_5</code>, <code>GL_VERSION_ES_1_0</code><br>Define "GL_SRC0_RGB" with expression '<code>0x8580</code>', CType: int */
  public static final int GL_SRC0_RGB = 0x8580;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_FOG_MODE" with expression '<code>0x0B65</code>', CType: int */
  public static final int GL_FOG_MODE = 0xb65;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_QCOM_alpha_test</code><br>Alias for: <code>GL_ALPHA_TEST_FUNC_QCOM</code><br>
Define "GL_ALPHA_TEST_FUNC" with expression '<code>0x0BC1</code>', CType: int */
  public static final int GL_ALPHA_TEST_FUNC = 0xbc1;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_IMG_user_clip_plane</code><br>Alias for: <code>GL_MAX_CLIP_PLANES_IMG</code><br>
Define "GL_MAX_CLIP_PLANES" with expression '<code>0x0D32</code>', CType: int */
  public static final int GL_MAX_CLIP_PLANES = 0xd32;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_VERTEX_ARRAY_POINTER_EXT</code><br>
Define "GL_VERTEX_ARRAY_POINTER" with expression '<code>0x808E</code>', CType: int */
  public static final int GL_VERTEX_ARRAY_POINTER = 0x808e;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_TEXTURE_COORD_ARRAY_SIZE_EXT</code><br>
Define "GL_TEXTURE_COORD_ARRAY_SIZE" with expression '<code>0x8088</code>', CType: int */
  public static final int GL_TEXTURE_COORD_ARRAY_SIZE = 0x8088;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_MODULATE" with expression '<code>0x2100</code>', CType: int */
  public static final int GL_MODULATE = 0x2100;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_OPERAND2_ALPHA_EXT</code>, <code>GL_OPERAND2_ALPHA_ARB</code><br>
Define "GL_OPERAND2_ALPHA" with expression '<code>0x859A</code>', CType: int */
  public static final int GL_OPERAND2_ALPHA = 0x859a;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_MAX_TEXTURE_STACK_DEPTH" with expression '<code>0x0D39</code>', CType: int */
  public static final int GL_MAX_TEXTURE_STACK_DEPTH = 0xd39;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_TEXTURE_ENV" with expression '<code>0x2300</code>', CType: int */
  public static final int GL_TEXTURE_ENV = 0x2300;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_NORMAL_ARRAY_STRIDE_EXT</code><br>
Define "GL_NORMAL_ARRAY_STRIDE" with expression '<code>0x807F</code>', CType: int */
  public static final int GL_NORMAL_ARRAY_STRIDE = 0x807f;
  /** <code>GL_VERSION_1_5</code>, <code>GL_VERSION_ES_1_0</code><br>Define "GL_SRC1_RGB" with expression '<code>0x8581</code>', CType: int */
  public static final int GL_SRC1_RGB = 0x8581;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_FOG_COLOR" with expression '<code>0x0B66</code>', CType: int */
  public static final int GL_FOG_COLOR = 0xb66;
  /** <code>GL_VERSION_1_4</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_point_parameters</code>, <code>GL_SGIS_point_parameters</code>, <code>GL_ARB_point_parameters</code><br>Alias for: <code>GL_POINT_SIZE_MIN_EXT</code>, <code>GL_POINT_SIZE_MIN_SGIS</code>, <code>GL_POINT_SIZE_MIN_ARB</code><br>
Define "GL_POINT_SIZE_MIN" with expression '<code>0x8126</code>', CType: int */
  public static final int GL_POINT_SIZE_MIN = 0x8126;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_OPERAND0_ALPHA_EXT</code>, <code>GL_OPERAND0_ALPHA_ARB</code><br>
Define "GL_OPERAND0_ALPHA" with expression '<code>0x8598</code>', CType: int */
  public static final int GL_OPERAND0_ALPHA = 0x8598;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_POINT_SMOOTH" with expression '<code>0x0B10</code>', CType: int */
  public static final int GL_POINT_SMOOTH = 0xb10;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_EXP2" with expression '<code>0x0801</code>', CType: int */
  public static final int GL_EXP2 = 0x801;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_EXP" with expression '<code>0x0800</code>', CType: int */
  public static final int GL_EXP = 0x800;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_COLOR_ARRAY_SIZE_EXT</code><br>
Define "GL_COLOR_ARRAY_SIZE" with expression '<code>0x8081</code>', CType: int */
  public static final int GL_COLOR_ARRAY_SIZE = 0x8081;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_LIGHT_MODEL_AMBIENT" with expression '<code>0x0B53</code>', CType: int */
  public static final int GL_LIGHT_MODEL_AMBIENT = 0xb53;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_TEXTURE_COORD_ARRAY_POINTER_EXT</code><br>
Define "GL_TEXTURE_COORD_ARRAY_POINTER" with expression '<code>0x8092</code>', CType: int */
  public static final int GL_TEXTURE_COORD_ARRAY_POINTER = 0x8092;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_CURRENT_NORMAL" with expression '<code>0x0B02</code>', CType: int */
  public static final int GL_CURRENT_NORMAL = 0xb02;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_MAX_PROJECTION_STACK_DEPTH" with expression '<code>0x0D38</code>', CType: int */
  public static final int GL_MAX_PROJECTION_STACK_DEPTH = 0xd38;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code>, <code>GL_NV_path_rendering</code>, <code>GL_NV_register_combiners</code><br>Alias for: <code>GL_PRIMARY_COLOR_EXT</code>, <code>GL_PRIMARY_COLOR_ARB</code>, <code>GL_PRIMARY_COLOR_NV</code><br>
Define "GL_PRIMARY_COLOR" with expression '<code>0x8577</code>', CType: int */
  public static final int GL_PRIMARY_COLOR = 0x8577;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_TEXTURE_ENV_COLOR" with expression '<code>0x2201</code>', CType: int */
  public static final int GL_TEXTURE_ENV_COLOR = 0x2201;
  /** <code>GL_VERSION_1_5</code>, <code>GL_VERSION_ES_1_0</code><br>Define "GL_SRC2_RGB" with expression '<code>0x8582</code>', CType: int */
  public static final int GL_SRC2_RGB = 0x8582;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_INTERPOLATE_EXT</code>, <code>GL_INTERPOLATE_ARB</code><br>
Define "GL_INTERPOLATE" with expression '<code>0x8575</code>', CType: int */
  public static final int GL_INTERPOLATE = 0x8575;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_DECAL" with expression '<code>0x2101</code>', CType: int */
  public static final int GL_DECAL = 0x2101;
  /** <code>GL_VERSION_1_5</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_blend_func_extended</code><br>Alias for: <code>GL_SRC1_ALPHA_EXT</code><br>
Define "GL_SRC1_ALPHA" with expression '<code>0x8589</code>', CType: int */
  public static final int GL_SRC1_ALPHA = 0x8589;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_TEXTURE_ENV_MODE" with expression '<code>0x2200</code>', CType: int */
  public static final int GL_TEXTURE_ENV_MODE = 0x2200;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_QCOM_alpha_test</code><br>Alias for: <code>GL_ALPHA_TEST_REF_QCOM</code><br>
Define "GL_ALPHA_TEST_REF" with expression '<code>0x0BC2</code>', CType: int */
  public static final int GL_ALPHA_TEST_REF = 0xbc2;
  /** <code>GL_KHR_robustness</code>, <code>GL_KHR_robustness</code>, <code>GL_EXT_robustness</code><br>Alias for: <code>GL_CONTEXT_ROBUST_ACCESS_KHR</code>, <code>GL_CONTEXT_ROBUST_ACCESS_EXT</code><br>
Define "GL_CONTEXT_ROBUST_ACCESS" with expression '<code>0x90F3</code>', CType: int */
  public static final int GL_CONTEXT_ROBUST_ACCESS = 0x90f3;
  /** <code>GL_VERSION_1_5</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_ARB_vertex_buffer_object</code><br>Alias for: <code>GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING_ARB</code><br>
Define "GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING" with expression '<code>0x889A</code>', CType: int */
  public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 0x889a;
  /** <code>GL_VERSION_1_4</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_ARB_point_parameters</code><br>Alias for: <code>GL_POINT_DISTANCE_ATTENUATION_ARB</code><br>
Define "GL_POINT_DISTANCE_ATTENUATION" with expression '<code>0x8129</code>', CType: int */
  public static final int GL_POINT_DISTANCE_ATTENUATION = 0x8129;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_ALPHA_SCALE" with expression '<code>0x0D1C</code>', CType: int */
  public static final int GL_ALPHA_SCALE = 0xd1c;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code>, <code>GL_NV_path_rendering</code><br>Alias for: <code>GL_CONSTANT_EXT</code>, <code>GL_CONSTANT_ARB</code>, <code>GL_CONSTANT_NV</code><br>
Define "GL_CONSTANT" with expression '<code>0x8576</code>', CType: int */
  public static final int GL_CONSTANT = 0x8576;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_COLOR_ARRAY_TYPE_EXT</code><br>
Define "GL_COLOR_ARRAY_TYPE" with expression '<code>0x8082</code>', CType: int */
  public static final int GL_COLOR_ARRAY_TYPE = 0x8082;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_ARB_texture_env_dot3</code>, <code>GL_EXT_texture_env_dot3</code><br>Alias for: <code>GL_DOT3_RGB_ARB</code>, <code>GL_DOT3_RGB_EXT</code><br>
Define "GL_DOT3_RGB" with expression '<code>0x86AE</code>', CType: int */
  public static final int GL_DOT3_RGB = 0x86ae;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_POINT_SMOOTH_HINT" with expression '<code>0x0C51</code>', CType: int */
  public static final int GL_POINT_SMOOTH_HINT = 0xc51;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_SHADE_MODEL" with expression '<code>0x0B54</code>', CType: int */
  public static final int GL_SHADE_MODEL = 0xb54;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_OPERAND1_RGB_EXT</code>, <code>GL_OPERAND1_RGB_ARB</code><br>
Define "GL_OPERAND1_RGB" with expression '<code>0x8591</code>', CType: int */
  public static final int GL_OPERAND1_RGB = 0x8591;
  /** <code>GL_ES_VERSION_3_2</code>, <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_KHR_debug</code><br>Alias for: <code>GL_STACK_OVERFLOW_KHR</code><br>
Define "GL_STACK_OVERFLOW" with expression '<code>0x0503</code>', CType: int */
  public static final int GL_STACK_OVERFLOW = 0x503;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_ARB_texture_env_dot3</code>, <code>GL_EXT_texture_env_dot3</code>, <code>GL_IMG_texture_env_enhanced_fixed_function</code><br>Alias for: <code>GL_DOT3_RGBA_ARB</code>, <code>GL_DOT3_RGBA_EXT</code>, <code>GL_DOT3_RGBA_IMG</code><br>
Define "GL_DOT3_RGBA" with expression '<code>0x86AF</code>', CType: int */
  public static final int GL_DOT3_RGBA = 0x86af;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_FOG_END" with expression '<code>0x0B64</code>', CType: int */
  public static final int GL_FOG_END = 0xb64;
  /** <code>GL_VERSION_2_0</code>, <code>GL_OES_point_sprite</code>, <code>GL_NV_point_sprite</code>, <code>GL_ARB_point_sprite</code><br>Alias for: <code>GL_COORD_REPLACE_OES</code>, <code>GL_COORD_REPLACE_NV</code>, <code>GL_COORD_REPLACE_ARB</code><br>
Define "GL_COORD_REPLACE" with expression '<code>0x8862</code>', CType: int */
  public static final int GL_COORD_REPLACE = 0x8862;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_SUBTRACT_ARB</code><br>
Define "GL_SUBTRACT" with expression '<code>0x84E7</code>', CType: int */
  public static final int GL_SUBTRACT = 0x84e7;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_TEXTURE_COORD_ARRAY_STRIDE_EXT</code><br>
Define "GL_TEXTURE_COORD_ARRAY_STRIDE" with expression '<code>0x808A</code>', CType: int */
  public static final int GL_TEXTURE_COORD_ARRAY_STRIDE = 0x808a;
  /** <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_vertex_array</code><br>Alias for: <code>GL_NORMAL_ARRAY_TYPE_EXT</code><br>
Define "GL_NORMAL_ARRAY_TYPE" with expression '<code>0x807E</code>', CType: int */
  public static final int GL_NORMAL_ARRAY_TYPE = 0x807e;
  /** <code>GL_VERSION_1_5</code>, <code>GL_VERSION_ES_1_0</code><br>Define "GL_SRC0_ALPHA" with expression '<code>0x8588</code>', CType: int */
  public static final int GL_SRC0_ALPHA = 0x8588;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_COMBINE_EXT</code>, <code>GL_COMBINE_ARB</code><br>
Define "GL_COMBINE" with expression '<code>0x8570</code>', CType: int */
  public static final int GL_COMBINE = 0x8570;
  /** <code>GL_VERSION_2_0</code>, <code>GL_ARB_point_sprite</code>, <code>GL_NV_point_sprite</code>, <code>GL_OES_point_sprite</code><br>Alias for: <code>GL_POINT_SPRITE_ARB</code>, <code>GL_POINT_SPRITE_NV</code>, <code>GL_POINT_SPRITE_OES</code><br>
Define "GL_POINT_SPRITE" with expression '<code>0x8861</code>', CType: int */
  public static final int GL_POINT_SPRITE = 0x8861;
  /** <code>GL_ES_VERSION_3_2</code>, <code>GL_VERSION_1_1</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_KHR_debug</code><br>Alias for: <code>GL_STACK_UNDERFLOW_KHR</code><br>
Define "GL_STACK_UNDERFLOW" with expression '<code>0x0504</code>', CType: int */
  public static final int GL_STACK_UNDERFLOW = 0x504;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code>, <code>GL_ATI_fragment_shader</code><br>Alias for: <code>GL_ADD_ATI</code><br>
Define "GL_ADD" with expression '<code>0x0104</code>', CType: int */
  public static final int GL_ADD = 0x104;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_FOG_DENSITY" with expression '<code>0x0B62</code>', CType: int */
  public static final int GL_FOG_DENSITY = 0xb62;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_OPERAND2_RGB_EXT</code>, <code>GL_OPERAND2_RGB_ARB</code><br>
Define "GL_OPERAND2_RGB" with expression '<code>0x8592</code>', CType: int */
  public static final int GL_OPERAND2_RGB = 0x8592;
  /** <code>GL_VERSION_1_4</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_SGIS_generate_mipmap</code><br>Alias for: <code>GL_GENERATE_MIPMAP_SGIS</code><br>
Define "GL_GENERATE_MIPMAP" with expression '<code>0x8191</code>', CType: int */
  public static final int GL_GENERATE_MIPMAP = 0x8191;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_FOG" with expression '<code>0x0B60</code>', CType: int */
  public static final int GL_FOG = 0xb60;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_FOG_START" with expression '<code>0x0B63</code>', CType: int */
  public static final int GL_FOG_START = 0xb63;
  /** <code>GL_VERSION_ES_1_0</code>, <code>GL_VERSION_1_0</code><br>Define "GL_MAX_MODELVIEW_STACK_DEPTH" with expression '<code>0x0D36</code>', CType: int */
  public static final int GL_MAX_MODELVIEW_STACK_DEPTH = 0xd36;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_COMBINE_ALPHA_EXT</code>, <code>GL_COMBINE_ALPHA_ARB</code><br>
Define "GL_COMBINE_ALPHA" with expression '<code>0x8572</code>', CType: int */
  public static final int GL_COMBINE_ALPHA = 0x8572;
  /** <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_1_0</code>, <code>GL_EXT_texture_env_combine</code>, <code>GL_ARB_texture_env_combine</code><br>Alias for: <code>GL_ADD_SIGNED_EXT</code>, <code>GL_ADD_SIGNED_ARB</code><br>
Define "GL_ADD_SIGNED" with expression '<code>0x8574</code>', CType: int */
  public static final int GL_ADD_SIGNED = 0x8574;

  /** Entry point to C language function: <code> void {@native glAlphaFunc}(GLenum func, GLclampf ref) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code>, <code>GL_QCOM_alpha_test</code><br>Alias for: <code>glAlphaFuncQCOM</code>   */
  public void glAlphaFunc(int func, float ref);

  /** Entry point to C language function: <code> void {@native glFogf}(GLenum pname, GLfloat param) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glFogf(int pname, float param);

  /** Entry point to C language function: <code> void {@native glFogfv}(GLenum pname, const GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>
      @param params a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void glFogfv(int pname, FloatBuffer params);

  /** Entry point to C language function: <code> void {@native glFogfv}(GLenum pname, const GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glFogfv(int pname, float[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glGetLightfv}(GLenum light, GLenum pname, GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>
      @param params a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void glGetLightfv(int light, int pname, FloatBuffer params);

  /** Entry point to C language function: <code> void {@native glGetLightfv}(GLenum light, GLenum pname, GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glGetLightfv(int light, int pname, float[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glGetMaterialfv}(GLenum face, GLenum pname, GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>
      @param params a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void glGetMaterialfv(int face, int pname, FloatBuffer params);

  /** Entry point to C language function: <code> void {@native glGetMaterialfv}(GLenum face, GLenum pname, GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glGetMaterialfv(int face, int pname, float[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glGetTexEnvfv}(GLenum tenv, GLenum pname, GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>
      @param params a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void glGetTexEnvfv(int tenv, int pname, FloatBuffer params);

  /** Entry point to C language function: <code> void {@native glGetTexEnvfv}(GLenum tenv, GLenum pname, GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glGetTexEnvfv(int tenv, int pname, float[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glLightModelf}(GLenum pname, GLfloat param) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glLightModelf(int pname, float param);

  /** Entry point to C language function: <code> void {@native glLightModelfv}(GLenum pname, const GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>
      @param params a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void glLightModelfv(int pname, FloatBuffer params);

  /** Entry point to C language function: <code> void {@native glLightModelfv}(GLenum pname, const GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glLightModelfv(int pname, float[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glLightf}(GLenum light, GLenum pname, GLfloat param) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glLightf(int light, int pname, float param);

  /** Entry point to C language function: <code> void {@native glMultiTexCoord4f}(GLenum target, GLfloat s, GLfloat t, GLfloat r, GLfloat q) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_3</code>, <code>GL_ARB_multitexture</code><br>Alias for: <code>glMultiTexCoord4fARB</code>   */
  public void glMultiTexCoord4f(int target, float s, float t, float r, float q);

  /** Entry point to C language function: <code> void {@native glNormal3f}(GLfloat nx, GLfloat ny, GLfloat nz) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glNormal3f(float nx, float ny, float nz);

  /** Entry point to C language function: <code> void {@native glPointParameterf}(GLenum pname, GLfloat param) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_4</code>, <code>GL_EXT_point_parameters</code>, <code>GL_ARB_point_parameters</code>, <code>GL_SGIS_point_parameters</code><br>Alias for: <code>glPointParameterfEXT</code>, <code>glPointParameterfARB</code>, <code>glPointParameterfSGIS</code>   */
  public void glPointParameterf(int pname, float param);

  /** Entry point to C language function: <code> void {@native glPointParameterfv}(GLenum pname, const GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_4</code>, <code>GL_SGIS_point_parameters</code>, <code>GL_EXT_point_parameters</code>, <code>GL_ARB_point_parameters</code><br>Alias for: <code>glPointParameterfvSGIS</code>, <code>glPointParameterfvEXT</code>, <code>glPointParameterfvARB</code>
      @param params a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void glPointParameterfv(int pname, FloatBuffer params);

  /** Entry point to C language function: <code> void {@native glPointParameterfv}(GLenum pname, const GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_4</code>, <code>GL_SGIS_point_parameters</code>, <code>GL_EXT_point_parameters</code>, <code>GL_ARB_point_parameters</code><br>Alias for: <code>glPointParameterfvSGIS</code>, <code>glPointParameterfvEXT</code>, <code>glPointParameterfvARB</code>   */
  public void glPointParameterfv(int pname, float[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glPointSize}(GLfloat size) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glPointSize(float size);

  /** Entry point to C language function: <code> void {@native glTexEnvf}(GLenum target, GLenum pname, GLfloat param) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glTexEnvf(int target, int pname, float param);

  /** Entry point to C language function: <code> void {@native glTexEnvfv}(GLenum target, GLenum pname, const GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>
      @param params a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void glTexEnvfv(int target, int pname, FloatBuffer params);

  /** Entry point to C language function: <code> void {@native glTexEnvfv}(GLenum target, GLenum pname, const GLfloat *  params) </code> <br>Part of <code>GL_VERSION_ES_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glTexEnvfv(int target, int pname, float[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glClientActiveTexture}(GLenum texture) </code> <br>Part of <code>GL_VERSION_1_3</code>, <code>GL_VERSION_ES_CL_CM</code>, <code>GL_ARB_multitexture</code><br>Alias for: <code>glClientActiveTextureARB</code>   */
  public void glClientActiveTexture(int texture);

  /** Entry point to C language function: <code> void {@native glColor4ub}(GLubyte red, GLubyte green, GLubyte blue, GLubyte alpha) </code> <br>Part of <code>GL_VERSION_ES_CL_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glColor4ub(byte red, byte green, byte blue, byte alpha);

  /** Entry point to C language function: <code> void {@native glGetTexEnviv}(GLenum tenv, GLenum pname, GLint *  params) </code> <br>Part of <code>GL_VERSION_ES_CL_CM</code>, <code>GL_VERSION_1_0</code><br>
      @param params a direct or array-backed {@link java.nio.IntBuffer}   */
  public void glGetTexEnviv(int tenv, int pname, IntBuffer params);

  /** Entry point to C language function: <code> void {@native glGetTexEnviv}(GLenum tenv, GLenum pname, GLint *  params) </code> <br>Part of <code>GL_VERSION_ES_CL_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glGetTexEnviv(int tenv, int pname, int[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glLogicOp}(GLenum opcode) </code> <br>Part of <code>GL_VERSION_ES_CL_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glLogicOp(int opcode);

  /** Entry point to C language function: <code> void {@native glTexEnvi}(GLenum target, GLenum pname, GLint param) </code> <br>Part of <code>GL_VERSION_ES_CL_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glTexEnvi(int target, int pname, int param);

  /** Entry point to C language function: <code> void {@native glTexEnviv}(GLenum target, GLenum pname, const GLint *  params) </code> <br>Part of <code>GL_VERSION_ES_CL_CM</code>, <code>GL_VERSION_1_0</code><br>
      @param params a direct or array-backed {@link java.nio.IntBuffer}   */
  public void glTexEnviv(int target, int pname, IntBuffer params);

  /** Entry point to C language function: <code> void {@native glTexEnviv}(GLenum target, GLenum pname, const GLint *  params) </code> <br>Part of <code>GL_VERSION_ES_CL_CM</code>, <code>GL_VERSION_1_0</code><br>   */
  public void glTexEnviv(int target, int pname, int[] params, int params_offset);


  // --- Begin CustomJavaCode .cfg declarations
 public void glOrtho(double left, double right, double bottom, double top, double near_val, double far_val);
 public void glFrustum(double left, double right, double bottom, double top, double zNear, double zFar);
    /** Entry point to C language function: <code> void {@native glDrawElements}(GLenum mode, GLsizei count, GLenum type, const GLvoid *  indices); </code> <br>Part of <code>GL_VERSION_ES_CL_CM</code>, <code>GL_VERSION_1_1</code>, <code>GL_ES_VERSION_2_0</code>
        @param indices a direct or array-backed {@link java.nio.Buffer}   */
    public void glDrawElements(int mode, int count, int type, Buffer indices);
  
  // ---- End CustomJavaCode .cfg declarations
} // end of class GL2ES1
