/* !---- DO NOT EDIT: This file autogenerated by com/jogamp/gluegen/procaddress/ProcAddressEmitter.java on Fri Aug 18 14:49:18 CEST 2023 ----! */
/* !---- Java-Unit: [pkg com.jogamp.openal, cls AL], ../build/gensrc/classes/com/jogamp/openal/AL.java ----! */

package com.jogamp.openal;

import com.jogamp.openal.*;
import jogamp.openal.*;
import java.security.PrivilegedAction;
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

public interface AL extends ALConstants{

  /** Define "HAS_STDDEF" with expression '<code>1</code>', CType: int */
  public static final int HAS_STDDEF = 0x1;

  /** Entry point (through function pointer) to C language function: <br> <code>void alDopplerFactor(ALfloat value)</code><br>   */
  public void alDopplerFactor(float value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alDopplerVelocity(ALfloat value)</code><br>   */
  public void alDopplerVelocity(float value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSpeedOfSound(ALfloat value)</code><br>   */
  public void alSpeedOfSound(float value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alDistanceModel(ALenum distanceModel)</code><br>   */
  public void alDistanceModel(int distanceModel);

  /** Entry point (through function pointer) to C language function: <br> <code>void alEnable(ALenum capability)</code><br>   */
  public void alEnable(int capability);

  /** Entry point (through function pointer) to C language function: <br> <code>void alDisable(ALenum capability)</code><br>   */
  public void alDisable(int capability);

  /** Entry point (through function pointer) to C language function: <br> <code>ALboolean alIsEnabled(ALenum capability)</code><br>   */
  public boolean alIsEnabled(int capability);

  /** Entry point (through function pointer) to C language function: <br> <code>const ALchar *  alGetString(ALenum param)</code><br>   */
  public String alGetString(int param);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBooleanv(ALenum param, ALboolean *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.ByteBuffer}   */
  public void alGetBooleanv(int param, ByteBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBooleanv(ALenum param, ALboolean *  values)</code><br>   */
  public void alGetBooleanv(int param, byte[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetIntegerv(ALenum param, ALint *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGetIntegerv(int param, IntBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetIntegerv(ALenum param, ALint *  values)</code><br>   */
  public void alGetIntegerv(int param, int[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetFloatv(ALenum param, ALfloat *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alGetFloatv(int param, FloatBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetFloatv(ALenum param, ALfloat *  values)</code><br>   */
  public void alGetFloatv(int param, float[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetDoublev(ALenum param, ALdouble *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.DoubleBuffer}   */
  public void alGetDoublev(int param, DoubleBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetDoublev(ALenum param, ALdouble *  values)</code><br>   */
  public void alGetDoublev(int param, double[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>ALboolean alGetBoolean(ALenum param)</code><br>   */
  public boolean alGetBoolean(int param);

  /** Entry point (through function pointer) to C language function: <br> <code>ALint alGetInteger(ALenum param)</code><br>   */
  public int alGetInteger(int param);

  /** Entry point (through function pointer) to C language function: <br> <code>ALfloat alGetFloat(ALenum param)</code><br>   */
  public float alGetFloat(int param);

  /** Entry point (through function pointer) to C language function: <br> <code>ALdouble alGetDouble(ALenum param)</code><br>   */
  public double alGetDouble(int param);

  /** Entry point (through function pointer) to C language function: <br> <code>ALenum alGetError()</code><br>   */
  public int alGetError();

  /** Entry point (through function pointer) to C language function: <br> <code>ALboolean alIsExtensionPresent(const ALchar *  extname)</code><br>   */
  public boolean alIsExtensionPresent(String extname);

  /** Entry point (through function pointer) to C language function: <br> <code>ALenum alGetEnumValue(const ALchar *  ename)</code><br>   */
  public int alGetEnumValue(String ename);

  /** Entry point (through function pointer) to C language function: <br> <code>void alListenerf(ALenum param, ALfloat value)</code><br>   */
  public void alListenerf(int param, float value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alListener3f(ALenum param, ALfloat value1, ALfloat value2, ALfloat value3)</code><br>   */
  public void alListener3f(int param, float value1, float value2, float value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alListenerfv(ALenum param, const ALfloat *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alListenerfv(int param, FloatBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alListenerfv(ALenum param, const ALfloat *  values)</code><br>   */
  public void alListenerfv(int param, float[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alListeneri(ALenum param, ALint value)</code><br>   */
  public void alListeneri(int param, int value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alListener3i(ALenum param, ALint value1, ALint value2, ALint value3)</code><br>   */
  public void alListener3i(int param, int value1, int value2, int value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alListeneriv(ALenum param, const ALint *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alListeneriv(int param, IntBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alListeneriv(ALenum param, const ALint *  values)</code><br>   */
  public void alListeneriv(int param, int[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListenerf(ALenum param, ALfloat *  value)</code><br>
      @param value a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alGetListenerf(int param, FloatBuffer value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListenerf(ALenum param, ALfloat *  value)</code><br>   */
  public void alGetListenerf(int param, float[] value, int value_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListener3f(ALenum param, ALfloat *  value1, ALfloat *  value2, ALfloat *  value3)</code><br>
      @param value1 a direct or array-backed {@link java.nio.FloatBuffer}
      @param value2 a direct or array-backed {@link java.nio.FloatBuffer}
      @param value3 a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alGetListener3f(int param, FloatBuffer value1, FloatBuffer value2, FloatBuffer value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListener3f(ALenum param, ALfloat *  value1, ALfloat *  value2, ALfloat *  value3)</code><br>   */
  public void alGetListener3f(int param, float[] value1, int value1_offset, float[] value2, int value2_offset, float[] value3, int value3_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListenerfv(ALenum param, ALfloat *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alGetListenerfv(int param, FloatBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListenerfv(ALenum param, ALfloat *  values)</code><br>   */
  public void alGetListenerfv(int param, float[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListeneri(ALenum param, ALint *  value)</code><br>
      @param value a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGetListeneri(int param, IntBuffer value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListeneri(ALenum param, ALint *  value)</code><br>   */
  public void alGetListeneri(int param, int[] value, int value_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListener3i(ALenum param, ALint *  value1, ALint *  value2, ALint *  value3)</code><br>
      @param value1 a direct or array-backed {@link java.nio.IntBuffer}
      @param value2 a direct or array-backed {@link java.nio.IntBuffer}
      @param value3 a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGetListener3i(int param, IntBuffer value1, IntBuffer value2, IntBuffer value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListener3i(ALenum param, ALint *  value1, ALint *  value2, ALint *  value3)</code><br>   */
  public void alGetListener3i(int param, int[] value1, int value1_offset, int[] value2, int value2_offset, int[] value3, int value3_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListeneriv(ALenum param, ALint *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGetListeneriv(int param, IntBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetListeneriv(ALenum param, ALint *  values)</code><br>   */
  public void alGetListeneriv(int param, int[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGenSources(ALsizei n, ALuint *  sources)</code><br>
      @param sources a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGenSources(int n, IntBuffer sources);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGenSources(ALsizei n, ALuint *  sources)</code><br>   */
  public void alGenSources(int n, int[] sources, int sources_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alDeleteSources(ALsizei n, const ALuint *  sources)</code><br>
      @param sources a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alDeleteSources(int n, IntBuffer sources);

  /** Entry point (through function pointer) to C language function: <br> <code>void alDeleteSources(ALsizei n, const ALuint *  sources)</code><br>   */
  public void alDeleteSources(int n, int[] sources, int sources_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>ALboolean alIsSource(ALuint source)</code><br>   */
  public boolean alIsSource(int source);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourcef(ALuint source, ALenum param, ALfloat value)</code><br>   */
  public void alSourcef(int source, int param, float value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSource3f(ALuint source, ALenum param, ALfloat value1, ALfloat value2, ALfloat value3)</code><br>   */
  public void alSource3f(int source, int param, float value1, float value2, float value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourcefv(ALuint source, ALenum param, const ALfloat *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alSourcefv(int source, int param, FloatBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourcefv(ALuint source, ALenum param, const ALfloat *  values)</code><br>   */
  public void alSourcefv(int source, int param, float[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourcei(ALuint source, ALenum param, ALint value)</code><br>   */
  public void alSourcei(int source, int param, int value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSource3i(ALuint source, ALenum param, ALint value1, ALint value2, ALint value3)</code><br>   */
  public void alSource3i(int source, int param, int value1, int value2, int value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceiv(ALuint source, ALenum param, const ALint *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alSourceiv(int source, int param, IntBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceiv(ALuint source, ALenum param, const ALint *  values)</code><br>   */
  public void alSourceiv(int source, int param, int[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSourcef(ALuint source, ALenum param, ALfloat *  value)</code><br>
      @param value a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alGetSourcef(int source, int param, FloatBuffer value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSourcef(ALuint source, ALenum param, ALfloat *  value)</code><br>   */
  public void alGetSourcef(int source, int param, float[] value, int value_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSource3f(ALuint source, ALenum param, ALfloat *  value1, ALfloat *  value2, ALfloat *  value3)</code><br>
      @param value1 a direct or array-backed {@link java.nio.FloatBuffer}
      @param value2 a direct or array-backed {@link java.nio.FloatBuffer}
      @param value3 a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alGetSource3f(int source, int param, FloatBuffer value1, FloatBuffer value2, FloatBuffer value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSource3f(ALuint source, ALenum param, ALfloat *  value1, ALfloat *  value2, ALfloat *  value3)</code><br>   */
  public void alGetSource3f(int source, int param, float[] value1, int value1_offset, float[] value2, int value2_offset, float[] value3, int value3_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSourcefv(ALuint source, ALenum param, ALfloat *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alGetSourcefv(int source, int param, FloatBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSourcefv(ALuint source, ALenum param, ALfloat *  values)</code><br>   */
  public void alGetSourcefv(int source, int param, float[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSourcei(ALuint source, ALenum param, ALint *  value)</code><br>
      @param value a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGetSourcei(int source, int param, IntBuffer value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSourcei(ALuint source, ALenum param, ALint *  value)</code><br>   */
  public void alGetSourcei(int source, int param, int[] value, int value_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSource3i(ALuint source, ALenum param, ALint *  value1, ALint *  value2, ALint *  value3)</code><br>
      @param value1 a direct or array-backed {@link java.nio.IntBuffer}
      @param value2 a direct or array-backed {@link java.nio.IntBuffer}
      @param value3 a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGetSource3i(int source, int param, IntBuffer value1, IntBuffer value2, IntBuffer value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSource3i(ALuint source, ALenum param, ALint *  value1, ALint *  value2, ALint *  value3)</code><br>   */
  public void alGetSource3i(int source, int param, int[] value1, int value1_offset, int[] value2, int value2_offset, int[] value3, int value3_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSourceiv(ALuint source, ALenum param, ALint *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGetSourceiv(int source, int param, IntBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetSourceiv(ALuint source, ALenum param, ALint *  values)</code><br>   */
  public void alGetSourceiv(int source, int param, int[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourcePlayv(ALsizei n, const ALuint *  sources)</code><br>
      @param sources a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alSourcePlayv(int n, IntBuffer sources);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourcePlayv(ALsizei n, const ALuint *  sources)</code><br>   */
  public void alSourcePlayv(int n, int[] sources, int sources_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceStopv(ALsizei n, const ALuint *  sources)</code><br>
      @param sources a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alSourceStopv(int n, IntBuffer sources);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceStopv(ALsizei n, const ALuint *  sources)</code><br>   */
  public void alSourceStopv(int n, int[] sources, int sources_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceRewindv(ALsizei n, const ALuint *  sources)</code><br>
      @param sources a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alSourceRewindv(int n, IntBuffer sources);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceRewindv(ALsizei n, const ALuint *  sources)</code><br>   */
  public void alSourceRewindv(int n, int[] sources, int sources_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourcePausev(ALsizei n, const ALuint *  sources)</code><br>
      @param sources a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alSourcePausev(int n, IntBuffer sources);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourcePausev(ALsizei n, const ALuint *  sources)</code><br>   */
  public void alSourcePausev(int n, int[] sources, int sources_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourcePlay(ALuint source)</code><br>   */
  public void alSourcePlay(int source);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceStop(ALuint source)</code><br>   */
  public void alSourceStop(int source);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceRewind(ALuint source)</code><br>   */
  public void alSourceRewind(int source);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourcePause(ALuint source)</code><br>   */
  public void alSourcePause(int source);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceQueueBuffers(ALuint source, ALsizei nb, const ALuint *  buffers)</code><br>
      @param buffers a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alSourceQueueBuffers(int source, int nb, IntBuffer buffers);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceQueueBuffers(ALuint source, ALsizei nb, const ALuint *  buffers)</code><br>   */
  public void alSourceQueueBuffers(int source, int nb, int[] buffers, int buffers_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceUnqueueBuffers(ALuint source, ALsizei nb, ALuint *  buffers)</code><br>
      @param buffers a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alSourceUnqueueBuffers(int source, int nb, IntBuffer buffers);

  /** Entry point (through function pointer) to C language function: <br> <code>void alSourceUnqueueBuffers(ALuint source, ALsizei nb, ALuint *  buffers)</code><br>   */
  public void alSourceUnqueueBuffers(int source, int nb, int[] buffers, int buffers_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGenBuffers(ALsizei n, ALuint *  buffers)</code><br>
      @param buffers a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGenBuffers(int n, IntBuffer buffers);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGenBuffers(ALsizei n, ALuint *  buffers)</code><br>   */
  public void alGenBuffers(int n, int[] buffers, int buffers_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alDeleteBuffers(ALsizei n, const ALuint *  buffers)</code><br>
      @param buffers a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alDeleteBuffers(int n, IntBuffer buffers);

  /** Entry point (through function pointer) to C language function: <br> <code>void alDeleteBuffers(ALsizei n, const ALuint *  buffers)</code><br>   */
  public void alDeleteBuffers(int n, int[] buffers, int buffers_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>ALboolean alIsBuffer(ALuint buffer)</code><br>   */
  public boolean alIsBuffer(int buffer);

  /** Entry point (through function pointer) to C language function: <br> <code>void alBufferData(ALuint buffer, ALenum format, const ALvoid *  data, ALsizei size, ALsizei freq)</code><br>
      @param data a direct or array-backed {@link java.nio.Buffer}   */
  public void alBufferData(int buffer, int format, Buffer data, int size, int freq);

  /** Entry point (through function pointer) to C language function: <br> <code>void alBufferf(ALuint buffer, ALenum param, ALfloat value)</code><br>   */
  public void alBufferf(int buffer, int param, float value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alBuffer3f(ALuint buffer, ALenum param, ALfloat value1, ALfloat value2, ALfloat value3)</code><br>   */
  public void alBuffer3f(int buffer, int param, float value1, float value2, float value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alBufferfv(ALuint buffer, ALenum param, const ALfloat *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alBufferfv(int buffer, int param, FloatBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alBufferfv(ALuint buffer, ALenum param, const ALfloat *  values)</code><br>   */
  public void alBufferfv(int buffer, int param, float[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alBufferi(ALuint buffer, ALenum param, ALint value)</code><br>   */
  public void alBufferi(int buffer, int param, int value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alBuffer3i(ALuint buffer, ALenum param, ALint value1, ALint value2, ALint value3)</code><br>   */
  public void alBuffer3i(int buffer, int param, int value1, int value2, int value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alBufferiv(ALuint buffer, ALenum param, const ALint *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alBufferiv(int buffer, int param, IntBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alBufferiv(ALuint buffer, ALenum param, const ALint *  values)</code><br>   */
  public void alBufferiv(int buffer, int param, int[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBufferf(ALuint buffer, ALenum param, ALfloat *  value)</code><br>
      @param value a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alGetBufferf(int buffer, int param, FloatBuffer value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBufferf(ALuint buffer, ALenum param, ALfloat *  value)</code><br>   */
  public void alGetBufferf(int buffer, int param, float[] value, int value_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBuffer3f(ALuint buffer, ALenum param, ALfloat *  value1, ALfloat *  value2, ALfloat *  value3)</code><br>
      @param value1 a direct or array-backed {@link java.nio.FloatBuffer}
      @param value2 a direct or array-backed {@link java.nio.FloatBuffer}
      @param value3 a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alGetBuffer3f(int buffer, int param, FloatBuffer value1, FloatBuffer value2, FloatBuffer value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBuffer3f(ALuint buffer, ALenum param, ALfloat *  value1, ALfloat *  value2, ALfloat *  value3)</code><br>   */
  public void alGetBuffer3f(int buffer, int param, float[] value1, int value1_offset, float[] value2, int value2_offset, float[] value3, int value3_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBufferfv(ALuint buffer, ALenum param, ALfloat *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.FloatBuffer}   */
  public void alGetBufferfv(int buffer, int param, FloatBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBufferfv(ALuint buffer, ALenum param, ALfloat *  values)</code><br>   */
  public void alGetBufferfv(int buffer, int param, float[] values, int values_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBufferi(ALuint buffer, ALenum param, ALint *  value)</code><br>
      @param value a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGetBufferi(int buffer, int param, IntBuffer value);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBufferi(ALuint buffer, ALenum param, ALint *  value)</code><br>   */
  public void alGetBufferi(int buffer, int param, int[] value, int value_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBuffer3i(ALuint buffer, ALenum param, ALint *  value1, ALint *  value2, ALint *  value3)</code><br>
      @param value1 a direct or array-backed {@link java.nio.IntBuffer}
      @param value2 a direct or array-backed {@link java.nio.IntBuffer}
      @param value3 a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGetBuffer3i(int buffer, int param, IntBuffer value1, IntBuffer value2, IntBuffer value3);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBuffer3i(ALuint buffer, ALenum param, ALint *  value1, ALint *  value2, ALint *  value3)</code><br>   */
  public void alGetBuffer3i(int buffer, int param, int[] value1, int value1_offset, int[] value2, int value2_offset, int[] value3, int value3_offset);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBufferiv(ALuint buffer, ALenum param, ALint *  values)</code><br>
      @param values a direct or array-backed {@link java.nio.IntBuffer}   */
  public void alGetBufferiv(int buffer, int param, IntBuffer values);

  /** Entry point (through function pointer) to C language function: <br> <code>void alGetBufferiv(ALuint buffer, ALenum param, ALint *  values)</code><br>   */
  public void alGetBufferiv(int buffer, int param, int[] values, int values_offset);

} // end of class AL
