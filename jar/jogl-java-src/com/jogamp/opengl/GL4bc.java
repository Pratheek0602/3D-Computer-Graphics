/* !---- DO NOT EDIT: This file autogenerated by com/jogamp/gluegen/opengl/GLEmitter.java on Fri Aug 18 14:58:47 CEST 2023 ----! */
/* !---- Java-Unit: [pkg com.jogamp.opengl, cls GL4bc], ../build/jogl/gensrc/classes/com/jogamp/opengl/GL4bc.java ----! */

package com.jogamp.opengl;

import java.util.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.*;
import jogamp.opengl.*;
import com.jogamp.opengl.GLES1;
import com.jogamp.opengl.GLES2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GL4ES3;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL3bc;
import com.jogamp.opengl.GL4;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.GLBuffers;
import java.io.PrintStream;
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
  * <p>This interface contains all OpenGL [ 4.0 .. 4.5 ] <i>compatibility</i> profile,
  * as well as most of it's extensions defined at the time of this specification.</p>
  * <p>Note: OpenGL [ 4.0 .. 4.5 ] compatibility profile does includes fixed point functionality.</p>
  */
public interface GL4bc extends GL3bc, GL4{

  /** <code>GL_ARB_sample_locations</code><br>Define "GL_SAMPLE_LOCATION_ARB" with expression '<code>0x8E50</code>', CType: int */
  public static final int GL_SAMPLE_LOCATION_ARB = 0x8e50;
  /** <code>GL_ARB_sample_locations</code><br>Define "GL_SAMPLE_LOCATION_SUBPIXEL_BITS_ARB" with expression '<code>0x933D</code>', CType: int */
  public static final int GL_SAMPLE_LOCATION_SUBPIXEL_BITS_ARB = 0x933d;
  /** <code>GL_ARB_parallel_shader_compile</code><br>Define "GL_COMPLETION_STATUS_ARB" with expression '<code>0x91B1</code>', CType: int */
  public static final int GL_COMPLETION_STATUS_ARB = 0x91b1;
  /** <code>GL_ARB_parallel_shader_compile</code><br>Define "GL_MAX_SHADER_COMPILER_THREADS_ARB" with expression '<code>0x91B0</code>', CType: int */
  public static final int GL_MAX_SHADER_COMPILER_THREADS_ARB = 0x91b0;
  /** <code>GL_ARB_gpu_shader_int64</code><br>Define "GL_INT64_VEC3_ARB" with expression '<code>0x8FEA</code>', CType: int */
  public static final int GL_INT64_VEC3_ARB = 0x8fea;
  /** <code>GL_ARB_gpu_shader_int64</code><br>Define "GL_UNSIGNED_INT64_VEC2_ARB" with expression '<code>0x8FF5</code>', CType: int */
  public static final int GL_UNSIGNED_INT64_VEC2_ARB = 0x8ff5;
  /** <code>GL_ARB_sample_locations</code><br>Define "GL_FRAMEBUFFER_SAMPLE_LOCATION_PIXEL_GRID_ARB" with expression '<code>0x9343</code>', CType: int */
  public static final int GL_FRAMEBUFFER_SAMPLE_LOCATION_PIXEL_GRID_ARB = 0x9343;
  /** <code>GL_ARB_gpu_shader_int64</code><br>Define "GL_UNSIGNED_INT64_VEC3_ARB" with expression '<code>0x8FF6</code>', CType: int */
  public static final int GL_UNSIGNED_INT64_VEC3_ARB = 0x8ff6;
  /** <code>GL_ARB_sample_locations</code><br>Define "GL_SAMPLE_LOCATION_PIXEL_GRID_HEIGHT_ARB" with expression '<code>0x933F</code>', CType: int */
  public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_HEIGHT_ARB = 0x933f;
  /** <code>GL_ARB_sample_locations</code><br>Define "GL_SAMPLE_LOCATION_PIXEL_GRID_WIDTH_ARB" with expression '<code>0x933E</code>', CType: int */
  public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_WIDTH_ARB = 0x933e;
  /** <code>GL_ARB_sample_locations</code><br>Define "GL_PROGRAMMABLE_SAMPLE_LOCATION_ARB" with expression '<code>0x9341</code>', CType: int */
  public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_ARB = 0x9341;
  /** <code>GL_ARB_gpu_shader_int64</code><br>Define "GL_INT64_VEC2_ARB" with expression '<code>0x8FE9</code>', CType: int */
  public static final int GL_INT64_VEC2_ARB = 0x8fe9;
  /** <code>GL_ARB_gpu_shader_int64</code><br>Define "GL_UNSIGNED_INT64_VEC4_ARB" with expression '<code>0x8FF7</code>', CType: int */
  public static final int GL_UNSIGNED_INT64_VEC4_ARB = 0x8ff7;
  /** <code>GL_ARB_gpu_shader_int64</code><br>Define "GL_INT64_ARB" with expression '<code>0x140E</code>', CType: int */
  public static final int GL_INT64_ARB = 0x140e;
  /** <code>GL_ARB_sample_locations</code><br>Define "GL_FRAMEBUFFER_PROGRAMMABLE_SAMPLE_LOCATIONS_ARB" with expression '<code>0x9342</code>', CType: int */
  public static final int GL_FRAMEBUFFER_PROGRAMMABLE_SAMPLE_LOCATIONS_ARB = 0x9342;
  /** <code>GL_ARB_sample_locations</code><br>Define "GL_PROGRAMMABLE_SAMPLE_LOCATION_TABLE_SIZE_ARB" with expression '<code>0x9340</code>', CType: int */
  public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_TABLE_SIZE_ARB = 0x9340;
  /** <code>GL_ARB_gpu_shader_int64</code><br>Define "GL_INT64_VEC4_ARB" with expression '<code>0x8FEB</code>', CType: int */
  public static final int GL_INT64_VEC4_ARB = 0x8feb;

  /** Entry point to C language function: <code> void {@native glUniform1i64ARB}(GLint location, GLint64 x) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform1i64ARB(int location, long x);

  /** Entry point to C language function: <code> void {@native glUniform2i64ARB}(GLint location, GLint64 x, GLint64 y) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform2i64ARB(int location, long x, long y);

  /** Entry point to C language function: <code> void {@native glUniform3i64ARB}(GLint location, GLint64 x, GLint64 y, GLint64 z) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform3i64ARB(int location, long x, long y, long z);

  /** Entry point to C language function: <code> void {@native glUniform4i64ARB}(GLint location, GLint64 x, GLint64 y, GLint64 z, GLint64 w) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform4i64ARB(int location, long x, long y, long z, long w);

  /** Entry point to C language function: <code> void {@native glUniform1i64vARB}(GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glUniform1i64vARB(int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glUniform1i64vARB}(GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform1i64vARB(int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glUniform2i64vARB}(GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glUniform2i64vARB(int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glUniform2i64vARB}(GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform2i64vARB(int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glUniform3i64vARB}(GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glUniform3i64vARB(int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glUniform3i64vARB}(GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform3i64vARB(int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glUniform4i64vARB}(GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glUniform4i64vARB(int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glUniform4i64vARB}(GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform4i64vARB(int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glUniform1ui64ARB}(GLint location, GLuint64 x) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform1ui64ARB(int location, long x);

  /** Entry point to C language function: <code> void {@native glUniform2ui64ARB}(GLint location, GLuint64 x, GLuint64 y) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform2ui64ARB(int location, long x, long y);

  /** Entry point to C language function: <code> void {@native glUniform3ui64ARB}(GLint location, GLuint64 x, GLuint64 y, GLuint64 z) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform3ui64ARB(int location, long x, long y, long z);

  /** Entry point to C language function: <code> void {@native glUniform4ui64ARB}(GLint location, GLuint64 x, GLuint64 y, GLuint64 z, GLuint64 w) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform4ui64ARB(int location, long x, long y, long z, long w);

  /** Entry point to C language function: <code> void {@native glUniform1ui64vARB}(GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glUniform1ui64vARB(int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glUniform1ui64vARB}(GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform1ui64vARB(int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glUniform2ui64vARB}(GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glUniform2ui64vARB(int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glUniform2ui64vARB}(GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform2ui64vARB(int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glUniform3ui64vARB}(GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glUniform3ui64vARB(int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glUniform3ui64vARB}(GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform3ui64vARB(int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glUniform4ui64vARB}(GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glUniform4ui64vARB(int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glUniform4ui64vARB}(GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glUniform4ui64vARB(int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glGetUniformi64vARB}(GLuint program, GLint location, GLint64 *  params) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param params a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glGetUniformi64vARB(int program, int location, LongBuffer params);

  /** Entry point to C language function: <code> void {@native glGetUniformi64vARB}(GLuint program, GLint location, GLint64 *  params) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glGetUniformi64vARB(int program, int location, long[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glGetUniformui64vARB}(GLuint program, GLint location, GLuint64 *  params) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param params a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glGetUniformui64vARB(int program, int location, LongBuffer params);

  /** Entry point to C language function: <code> void {@native glGetUniformui64vARB}(GLuint program, GLint location, GLuint64 *  params) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glGetUniformui64vARB(int program, int location, long[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glGetnUniformi64vARB}(GLuint program, GLint location, GLsizei bufSize, GLint64 *  params) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param params a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glGetnUniformi64vARB(int program, int location, int bufSize, LongBuffer params);

  /** Entry point to C language function: <code> void {@native glGetnUniformi64vARB}(GLuint program, GLint location, GLsizei bufSize, GLint64 *  params) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glGetnUniformi64vARB(int program, int location, int bufSize, long[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glGetnUniformui64vARB}(GLuint program, GLint location, GLsizei bufSize, GLuint64 *  params) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param params a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glGetnUniformui64vARB(int program, int location, int bufSize, LongBuffer params);

  /** Entry point to C language function: <code> void {@native glGetnUniformui64vARB}(GLuint program, GLint location, GLsizei bufSize, GLuint64 *  params) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glGetnUniformui64vARB(int program, int location, int bufSize, long[] params, int params_offset);

  /** Entry point to C language function: <code> void {@native glProgramUniform1i64ARB}(GLuint program, GLint location, GLint64 x) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform1i64ARB(int program, int location, long x);

  /** Entry point to C language function: <code> void {@native glProgramUniform2i64ARB}(GLuint program, GLint location, GLint64 x, GLint64 y) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform2i64ARB(int program, int location, long x, long y);

  /** Entry point to C language function: <code> void {@native glProgramUniform3i64ARB}(GLuint program, GLint location, GLint64 x, GLint64 y, GLint64 z) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform3i64ARB(int program, int location, long x, long y, long z);

  /** Entry point to C language function: <code> void {@native glProgramUniform4i64ARB}(GLuint program, GLint location, GLint64 x, GLint64 y, GLint64 z, GLint64 w) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform4i64ARB(int program, int location, long x, long y, long z, long w);

  /** Entry point to C language function: <code> void {@native glProgramUniform1i64vARB}(GLuint program, GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glProgramUniform1i64vARB(int program, int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glProgramUniform1i64vARB}(GLuint program, GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform1i64vARB(int program, int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glProgramUniform2i64vARB}(GLuint program, GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glProgramUniform2i64vARB(int program, int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glProgramUniform2i64vARB}(GLuint program, GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform2i64vARB(int program, int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glProgramUniform3i64vARB}(GLuint program, GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glProgramUniform3i64vARB(int program, int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glProgramUniform3i64vARB}(GLuint program, GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform3i64vARB(int program, int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glProgramUniform4i64vARB}(GLuint program, GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glProgramUniform4i64vARB(int program, int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glProgramUniform4i64vARB}(GLuint program, GLint location, GLsizei count, const GLint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform4i64vARB(int program, int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glProgramUniform1ui64ARB}(GLuint program, GLint location, GLuint64 x) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform1ui64ARB(int program, int location, long x);

  /** Entry point to C language function: <code> void {@native glProgramUniform2ui64ARB}(GLuint program, GLint location, GLuint64 x, GLuint64 y) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform2ui64ARB(int program, int location, long x, long y);

  /** Entry point to C language function: <code> void {@native glProgramUniform3ui64ARB}(GLuint program, GLint location, GLuint64 x, GLuint64 y, GLuint64 z) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform3ui64ARB(int program, int location, long x, long y, long z);

  /** Entry point to C language function: <code> void {@native glProgramUniform4ui64ARB}(GLuint program, GLint location, GLuint64 x, GLuint64 y, GLuint64 z, GLuint64 w) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform4ui64ARB(int program, int location, long x, long y, long z, long w);

  /** Entry point to C language function: <code> void {@native glProgramUniform1ui64vARB}(GLuint program, GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glProgramUniform1ui64vARB(int program, int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glProgramUniform1ui64vARB}(GLuint program, GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform1ui64vARB(int program, int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glProgramUniform2ui64vARB}(GLuint program, GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glProgramUniform2ui64vARB(int program, int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glProgramUniform2ui64vARB}(GLuint program, GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform2ui64vARB(int program, int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glProgramUniform3ui64vARB}(GLuint program, GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glProgramUniform3ui64vARB(int program, int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glProgramUniform3ui64vARB}(GLuint program, GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform3ui64vARB(int program, int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glProgramUniform4ui64vARB}(GLuint program, GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>
      @param value a direct or array-backed {@link java.nio.LongBuffer}   */
  public void glProgramUniform4ui64vARB(int program, int location, int count, LongBuffer value);

  /** Entry point to C language function: <code> void {@native glProgramUniform4ui64vARB}(GLuint program, GLint location, GLsizei count, const GLuint64 *  value) </code> <br>Part of <code>GL_ARB_gpu_shader_int64</code><br>   */
  public void glProgramUniform4ui64vARB(int program, int location, int count, long[] value, int value_offset);

  /** Entry point to C language function: <code> void {@native glMaxShaderCompilerThreadsARB}(GLuint count) </code> <br>Part of <code>GL_ARB_parallel_shader_compile</code><br>   */
  public void glMaxShaderCompilerThreadsARB(int count);

  /** Entry point to C language function: <code> void {@native glFramebufferSampleLocationsfvARB}(GLenum target, GLuint start, GLsizei count, const GLfloat *  v) </code> <br>Part of <code>GL_ARB_sample_locations</code><br>
      @param v a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void glFramebufferSampleLocationsfvARB(int target, int start, int count, FloatBuffer v);

  /** Entry point to C language function: <code> void {@native glFramebufferSampleLocationsfvARB}(GLenum target, GLuint start, GLsizei count, const GLfloat *  v) </code> <br>Part of <code>GL_ARB_sample_locations</code><br>   */
  public void glFramebufferSampleLocationsfvARB(int target, int start, int count, float[] v, int v_offset);

  /** Entry point to C language function: <code> void {@native glNamedFramebufferSampleLocationsfvARB}(GLuint framebuffer, GLuint start, GLsizei count, const GLfloat *  v) </code> <br>Part of <code>GL_ARB_sample_locations</code><br>
      @param v a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void glNamedFramebufferSampleLocationsfvARB(int framebuffer, int start, int count, FloatBuffer v);

  /** Entry point to C language function: <code> void {@native glNamedFramebufferSampleLocationsfvARB}(GLuint framebuffer, GLuint start, GLsizei count, const GLfloat *  v) </code> <br>Part of <code>GL_ARB_sample_locations</code><br>   */
  public void glNamedFramebufferSampleLocationsfvARB(int framebuffer, int start, int count, float[] v, int v_offset);

  /** Entry point to C language function: <code> void {@native glEvaluateDepthValuesARB}() </code> <br>Part of <code>GL_ARB_sample_locations</code><br>   */
  public void glEvaluateDepthValuesARB();


  // --- Begin CustomJavaCode .cfg declarations
  
    /** Entry point to C language function: <code> void {@native glDrawElementsInstancedBaseInstance}(GLenum mode, GLsizei count, GLenum type, const void *  indices, GLsizei instancecount, GLuint baseinstance); </code> <br>Part of <code>GL_VERSION_4_2</code>, <code>GL_ARB_base_instance</code>
        @param indices a direct or array-backed {@link java.nio.Buffer}   */
    public void glDrawElementsInstancedBaseInstance(int mode, int count, int type, Buffer indices, int instancecount, int baseinstance);
  
    /** Entry point to C language function: <code> void {@native glDrawElementsInstancedBaseVertexBaseInstance}(GLenum mode, GLsizei count, GLenum type, const void *  indices, GLsizei instancecount, GLint basevertex, GLuint baseinstance); </code> <br>Part of <code>GL_VERSION_4_2</code>, <code>GL_ARB_base_instance</code>
        @param indices a direct or array-backed {@link java.nio.Buffer}   */
    public void glDrawElementsInstancedBaseVertexBaseInstance(int mode, int count, int type, Buffer indices, int instancecount, int basevertex, int baseinstance);
  
    /** Entry point to C language function: <code> void {@native glVertexAttribLPointer}(GLuint index, GLint size, GLenum type, GLsizei stride, const GLvoid *  pointer); </code> <br>Part of <code>GL_VERSION_4_1</code>, <code>GL_ARB_vertex_attrib_64bit</code>
        @param pointer a direct only {@link java.nio.Buffer}   */
    public void glVertexAttribLPointer(int index, int size, int type, int stride, Buffer pointer);
  
  // ---- End CustomJavaCode .cfg declarations
} // end of class GL4bc
