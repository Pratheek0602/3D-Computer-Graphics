/* !---- DO NOT EDIT: This file autogenerated by com/jogamp/gluegen/JavaEmitter.java on Fri Aug 18 14:57:06 CEST 2023 ----! */
/* !---- Java-Unit: [pkg jogamp.nativewindow.windows, cls AccentPolicy], ../build/nativewindow/gensrc/classes/jogamp/nativewindow/windows/AccentPolicy.java ----! */


package jogamp.nativewindow.windows;

import java.nio.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.jogamp.gluegen.runtime.*;
import com.jogamp.common.os.*;
import com.jogamp.common.nio.*;
import jogamp.common.os.MachineDataInfoRuntime;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.nativewindow.NativeWindowException;
import jogamp.nativewindow.NWJNILibLoader;
import jogamp.nativewindow.Debug;

public class AccentPolicy {

  StructAccessor accessor;

  private static final int mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
  private final MachineDataInfo md;

  private static final int[] AccentPolicy_size = new int[] { 16 /* ARM_MIPS_32 */, 16 /* X86_32_UNIX */, 16 /* X86_32_ANDROID */, 16 /* X86_32_MACOS */, 16 /* PPC_32_UNIX */, 16 /* SPARC_32_SUNOS */, 16 /* X86_32_WINDOWS */, 16 /* LP64_UNIX */, 16 /* X86_64_WINDOWS */, 16 /* ARM64_IOS */  };
  private static final int[] AccentState_offset = new int[] { 0 /* ARM_MIPS_32 */, 0 /* X86_32_UNIX */, 0 /* X86_32_ANDROID */, 0 /* X86_32_MACOS */, 0 /* PPC_32_UNIX */, 0 /* SPARC_32_SUNOS */, 0 /* X86_32_WINDOWS */, 0 /* LP64_UNIX */, 0 /* X86_64_WINDOWS */, 0 /* ARM64_IOS */ };
//private static final int[] AccentState_size = new int[] { 4 /* ARM_MIPS_32 */, 4 /* X86_32_UNIX */, 4 /* X86_32_ANDROID */, 4 /* X86_32_MACOS */, 4 /* PPC_32_UNIX */, 4 /* SPARC_32_SUNOS */, 4 /* X86_32_WINDOWS */, 4 /* LP64_UNIX */, 4 /* X86_64_WINDOWS */, 4 /* ARM64_IOS */  };
  private static final int[] AccentFlags_offset = new int[] { 4 /* ARM_MIPS_32 */, 4 /* X86_32_UNIX */, 4 /* X86_32_ANDROID */, 4 /* X86_32_MACOS */, 4 /* PPC_32_UNIX */, 4 /* SPARC_32_SUNOS */, 4 /* X86_32_WINDOWS */, 4 /* LP64_UNIX */, 4 /* X86_64_WINDOWS */, 4 /* ARM64_IOS */ };
//private static final int[] AccentFlags_size = new int[] { 4 /* ARM_MIPS_32 */, 4 /* X86_32_UNIX */, 4 /* X86_32_ANDROID */, 4 /* X86_32_MACOS */, 4 /* PPC_32_UNIX */, 4 /* SPARC_32_SUNOS */, 4 /* X86_32_WINDOWS */, 4 /* LP64_UNIX */, 4 /* X86_64_WINDOWS */, 4 /* ARM64_IOS */  };
  private static final int[] GradientColor_offset = new int[] { 8 /* ARM_MIPS_32 */, 8 /* X86_32_UNIX */, 8 /* X86_32_ANDROID */, 8 /* X86_32_MACOS */, 8 /* PPC_32_UNIX */, 8 /* SPARC_32_SUNOS */, 8 /* X86_32_WINDOWS */, 8 /* LP64_UNIX */, 8 /* X86_64_WINDOWS */, 8 /* ARM64_IOS */ };
//private static final int[] GradientColor_size = new int[] { 4 /* ARM_MIPS_32 */, 4 /* X86_32_UNIX */, 4 /* X86_32_ANDROID */, 4 /* X86_32_MACOS */, 4 /* PPC_32_UNIX */, 4 /* SPARC_32_SUNOS */, 4 /* X86_32_WINDOWS */, 4 /* LP64_UNIX */, 4 /* X86_64_WINDOWS */, 4 /* ARM64_IOS */  };
  private static final int[] AnimationId_offset = new int[] { 12 /* ARM_MIPS_32 */, 12 /* X86_32_UNIX */, 12 /* X86_32_ANDROID */, 12 /* X86_32_MACOS */, 12 /* PPC_32_UNIX */, 12 /* SPARC_32_SUNOS */, 12 /* X86_32_WINDOWS */, 12 /* LP64_UNIX */, 12 /* X86_64_WINDOWS */, 12 /* ARM64_IOS */ };
//private static final int[] AnimationId_size = new int[] { 4 /* ARM_MIPS_32 */, 4 /* X86_32_UNIX */, 4 /* X86_32_ANDROID */, 4 /* X86_32_MACOS */, 4 /* PPC_32_UNIX */, 4 /* SPARC_32_SUNOS */, 4 /* X86_32_WINDOWS */, 4 /* LP64_UNIX */, 4 /* X86_64_WINDOWS */, 4 /* ARM64_IOS */  };

  /** Returns true if this generated implementation uses native code, otherwise false. */
  public static boolean usesNativeCode() {
    return false;
  }

  /** Returns the aligned total size of a native instance. */
  public static int size() {
    return AccentPolicy_size[mdIdx];
  }

  /** Returns a new instance with all bytes set to zero. */
  public static AccentPolicy create() {
    return create(Buffers.newDirectByteBuffer(size()));
  }

  /** Returns a new instance using the given ByteBuffer having at least {#link size()} bytes capacity. The ByteBuffer will be {@link ByteBuffer#rewind()} and native-order set. */
  public static AccentPolicy create(java.nio.ByteBuffer buf) {
      return new AccentPolicy(buf);
  }

  /** Returns new instance dereferencing ByteBuffer at given native address `addr` with size {@link #size()}. */
  public static AccentPolicy derefPointer(final long addr) {
      return create( ElementBuffer.derefPointer(size(), addr, 1).getByteBuffer() );
  }

  AccentPolicy(java.nio.ByteBuffer buf) {
    md = MachineDataInfo.StaticConfig.values()[mdIdx].md;
    accessor = new StructAccessor(buf);
  }

  /** Return the underlying native direct ByteBuffer */
  public final java.nio.ByteBuffer getBuffer() {
    return accessor.getBuffer();
  }

  /** Returns the native address of the underlying native ByteBuffer {@link #getBuffer()} */
  public final long getDirectBufferAddress() {
    return accessor.getDirectBufferAddress();
  }

  /**
   * Setter for native field <code>AccentState</code>, being a <i>struct</i> owned EnumType.
   * <p>
   * Native Field Signature <code>(EnumType) typedef 'AccentState', size[fixed true, lnx64 4], const[typedef, false], is[primitive, enum  [const _AccentState] {5: [ACCENT_DISABLED = [0, [int: 0]], [ACCENT_ENABLE_GRADIENT = [1, [int: 1]], [ACCENT_ENABLE_TRANSPARENTGRADIENT = [2, [int: 2]], [ACCENT_ENABLE_BLURBEHIND = [3, [int: 3]], [ACCENT_INVALID_STATE = [4, [int: 4]], }, int]</code>
   * </p>
   */
  public final AccentPolicy setAccentState(int src) {
    accessor.setIntAt(AccentState_offset[mdIdx], src);
    return this;
  }

  /**
   * Getter for native field <code>AccentState</code>, being a <i>struct</i> owned EnumType.
   * <p>
   * Native Field Signature <code>(EnumType) typedef 'AccentState', size[fixed true, lnx64 4], const[typedef, false], is[primitive, enum  [const _AccentState] {5: [ACCENT_DISABLED = [0, [int: 0]], [ACCENT_ENABLE_GRADIENT = [1, [int: 1]], [ACCENT_ENABLE_TRANSPARENTGRADIENT = [2, [int: 2]], [ACCENT_ENABLE_BLURBEHIND = [3, [int: 3]], [ACCENT_INVALID_STATE = [4, [int: 4]], }, int]</code>
   * </p>
   */
  public final int getAccentState() {
    return accessor.getIntAt(AccentState_offset[mdIdx]);
  }

  /**
   * Setter for native field <code>AccentFlags</code>, being a <i>struct</i> owned IntType.
   * <p>
   * Native Field Signature <code>(IntType) typedef 'int32_t', size[fixed true, lnx64 4], const[false], is[primitive, int]</code>
   * </p>
   */
  public final AccentPolicy setAccentFlags(int src) {
    accessor.setIntAt(AccentFlags_offset[mdIdx], src);
    return this;
  }

  /**
   * Getter for native field <code>AccentFlags</code>, being a <i>struct</i> owned IntType.
   * <p>
   * Native Field Signature <code>(IntType) typedef 'int32_t', size[fixed true, lnx64 4], const[false], is[primitive, int]</code>
   * </p>
   */
  public final int getAccentFlags() {
    return accessor.getIntAt(AccentFlags_offset[mdIdx]);
  }

  /**
   * Setter for native field <code>GradientColor</code>, being a <i>struct</i> owned IntType.
   * <p>
   * Native Field Signature <code>(IntType) typedef 'int32_t', size[fixed true, lnx64 4], const[false], is[primitive, int]</code>
   * </p>
   */
  public final AccentPolicy setGradientColor(int src) {
    accessor.setIntAt(GradientColor_offset[mdIdx], src);
    return this;
  }

  /**
   * Getter for native field <code>GradientColor</code>, being a <i>struct</i> owned IntType.
   * <p>
   * Native Field Signature <code>(IntType) typedef 'int32_t', size[fixed true, lnx64 4], const[false], is[primitive, int]</code>
   * </p>
   */
  public final int getGradientColor() {
    return accessor.getIntAt(GradientColor_offset[mdIdx]);
  }

  /**
   * Setter for native field <code>AnimationId</code>, being a <i>struct</i> owned IntType.
   * <p>
   * Native Field Signature <code>(IntType) typedef 'int32_t', size[fixed true, lnx64 4], const[false], is[primitive, int]</code>
   * </p>
   */
  public final AccentPolicy setAnimationId(int src) {
    accessor.setIntAt(AnimationId_offset[mdIdx], src);
    return this;
  }

  /**
   * Getter for native field <code>AnimationId</code>, being a <i>struct</i> owned IntType.
   * <p>
   * Native Field Signature <code>(IntType) typedef 'int32_t', size[fixed true, lnx64 4], const[false], is[primitive, int]</code>
   * </p>
   */
  public final int getAnimationId() {
    return accessor.getIntAt(AnimationId_offset[mdIdx]);
  }

}
