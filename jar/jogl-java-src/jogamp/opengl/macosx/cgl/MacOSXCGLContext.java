/*
 * Copyright (c) 2003 Sun Microsystems, Inc. All Rights Reserved.
 * Copyright (c) 2010 JogAmp Community. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed or intended for use
 * in the design, construction, operation or maintenance of any nuclear
 * facility.
 *
 * Sun gratefully acknowledges that this software was originally authored
 * and developed by Kenneth Bradley Russell and Christopher John Kline.
 */

package jogamp.opengl.macosx.cgl;

import java.nio.IntBuffer;
import java.util.Map;

import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.nativewindow.OffscreenLayerSurface;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLUniformData;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

import jogamp.nativewindow.macosx.OSXUtil;
import jogamp.opengl.Debug;
import jogamp.opengl.GLContextImpl;
import jogamp.opengl.GLDrawableImpl;
import jogamp.opengl.GLDynamicLookupHelper;
import jogamp.opengl.GLFBODrawableImpl;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import jogamp.opengl.macosx.cgl.MacOSXCGLDrawable.GLBackendType;

import com.jogamp.common.nio.Buffers;
import com.jogamp.common.nio.PointerBuffer;
import com.jogamp.common.os.Platform;
import com.jogamp.common.util.VersionNumber;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.gluegen.runtime.ProcAddressTable;
import com.jogamp.gluegen.runtime.opengl.GLProcAddressResolver;
import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class MacOSXCGLContext extends GLContextImpl
{
  // Abstract interface for implementation of this context (either
  // NSOpenGL-based or CGL-based)
  protected interface GLBackendImpl {
        boolean isNSContext();
        /** Indicating CALayer, i.e. onscreen rendering using offscreen layer. */
        boolean isUsingCALayer();
        long create(long share, int ctp, int major, int minor);
        boolean destroy(long ctx);
        void associateDrawable(boolean bound);
        boolean copyImpl(long src, int mask);
        boolean makeCurrent(long ctx);
        void contextMadeCurrent(final boolean current);
        boolean release(long ctx);
        boolean detachPBuffer();
        boolean setSwapInterval(int interval);
        boolean swapBuffers();
  }

  /* package */ static final boolean isLionOrLater; // >= 10.7.0
  /* package */ static final boolean isMavericksOrLater; // >= 10.9.0
  /* package */ static final boolean isMojaveOrLater; // >= 10.14.0
  private static final boolean DEBUG1398;

  static {
    final VersionNumber osvn = Platform.getOSVersionNumber();
    if( osvn.compareTo(MacOSVersion.Mojave) >= 0 ) {
        isLionOrLater = true;
        isMavericksOrLater = true;
        isMojaveOrLater = true;
    } else if( osvn.compareTo(MacOSVersion.Mavericks) >= 0 ) {
        isLionOrLater = true;
        isMavericksOrLater = true;
        isMojaveOrLater = false;
    } else if( osvn.compareTo(MacOSVersion.Lion) >= 0 ) {
        isLionOrLater = true;
        isMavericksOrLater = false;
        isMojaveOrLater = false;
    } else {
        isLionOrLater = false;
        isMavericksOrLater = false;
        isMojaveOrLater = false;
    }
    DEBUG1398 = Debug.debugNotAll("Bug1398");
  }

  static boolean isGLProfileSupported(final int ctp, final int major, final int minor) {
    if( 0 != ( CTX_PROFILE_ES & ctp ) ) {
        return false;
    }
    final boolean ctBwdCompat = 0 != ( CTX_PROFILE_COMPAT & ctp ) ;
    final boolean ctCore      = 0 != ( CTX_PROFILE_CORE & ctp ) ;

    // We exclude 3.0, since we would map it's core to GL2. Hence we force mapping 2.1 to GL2
    if( 3 < major || 3 == major && 1 <= minor ) {
        if(ctBwdCompat || !ctCore) {
            // No compatibility profile on OS X
            // Only core is supported
            return false;
        }
        if(!isLionOrLater) {
            // no GL Profile >= GL3 core on pre lion
            return false;
        }
        if(3 < major && !isMavericksOrLater) {
            // no GL Profile >= GL4 core on pre mavericks
            return false;
        }
        // [3.1..3.x] -> GL3
        // [4.0..4.x] -> GL4
        return true;
    } else if( major < 3 ) {
        // < 3.0 -> GL2
        return true;
    }
    return false; // 3.0 && > 3.2
  }
  static int GLProfile2CGLOGLProfileValue(final AbstractGraphicsDevice device, final int ctp, final int major, final int minor) {
    if(!MacOSXCGLContext.isGLProfileSupported(ctp, major, minor)) {
        throw new GLException("OpenGL profile not supported: "+getGLVersion(major, minor, ctp, "@GLProfile2CGLOGLProfileVersion"));
    }
    final boolean ctCore = 0 != ( CTX_PROFILE_CORE & ctp ) ;

    if( major == 4 && ctCore ) {
        if( GLRendererQuirks.existStickyDeviceQuirk(device, GLRendererQuirks.GL4NeedsGL3Request) ) {
            // Thread safe GLRendererQuirks sticky access, since we are only interested of the result _after_ GL version mapping,
            // i.e. after single threaded initialization!
            return CGL.kCGLOGLPVersion_GL3_Core;
        } else {
            return CGL.kCGLOGLPVersion_GL4_Core;
        }
    } else if( major == 3 && minor >= 1 && ctCore ) {
        return CGL.kCGLOGLPVersion_GL3_Core;
    } else {
        return CGL.kCGLOGLPVersion_Legacy;
    }
  }

  private static final String shaderBasename = "texture01_xxx";

  private static ShaderProgram createCALayerShader(final GL3ES3 gl) {
      // Create & Link the shader program
      final ShaderProgram sp = new ShaderProgram();
      final ShaderCode vp = ShaderCode.create(gl, GL2ES2.GL_VERTEX_SHADER, MacOSXCGLContext.class,
                                              "../../shader", "../../shader/bin", shaderBasename, true);
      final ShaderCode fp = ShaderCode.create(gl, GL2ES2.GL_FRAGMENT_SHADER, MacOSXCGLContext.class,
                                              "../../shader", "../../shader/bin", shaderBasename, true);
      vp.defaultShaderCustomization(gl, true, true);
      fp.defaultShaderCustomization(gl, true, true);
      sp.add(vp);
      sp.add(fp);
      if(!sp.link(gl, System.err)) {
          throw new GLException("Couldn't link program: "+sp);
      }
      sp.useProgram(gl, true);

      // setup mgl_PMVMatrix
      final PMVMatrix pmvMatrix = new PMVMatrix();
      pmvMatrix.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
      pmvMatrix.glLoadIdentity();
      pmvMatrix.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
      pmvMatrix.glLoadIdentity();
      final GLUniformData pmvMatrixUniform = new GLUniformData("mgl_PMVMatrix", 4, 4, pmvMatrix.getSyncPMvMat()); // P, Mv
      pmvMatrixUniform.setLocation(gl, sp.program());
      gl.glUniform(pmvMatrixUniform);

      sp.useProgram(gl, false);
      return sp;
  }


  private boolean haveSetOpenGLMode = false;
  private GLBackendType openGLMode = GLBackendType.NSOPENGL;

  // Implementation object (either NSOpenGL-based or CGL-based)
  protected GLBackendImpl impl;

  private CGLExt _cglExt;
  // Table that holds the addresses of the native C-language entry points for
  // CGL extension functions.
  private CGLExtProcAddressTable cglExtProcAddressTable;

  private long updateHandle = 0;
  private int lastWidth, lastHeight;

  protected MacOSXCGLContext(final GLDrawableImpl drawable,
                   final GLContext shareWith) {
    super(drawable, shareWith);
    initOpenGLImpl(getOpenGLMode());
  }

  @Override
  protected void resetStates(final boolean isInit) {
    // no inner state _cglExt = null;
    cglExtProcAddressTable = null;
    super.resetStates(isInit);
  }

  @Override
  public Object getPlatformGLExtensions() {
    return getCGLExt();
  }

  protected boolean isNSContext() {
      return (null != impl) ? impl.isNSContext() : this.openGLMode == GLBackendType.NSOPENGL;
  }

  public CGLExt getCGLExt() {
    if (_cglExt == null) {
      _cglExt = new CGLExtImpl(this);
    }
    return _cglExt;
  }

  @Override
  public final ProcAddressTable getPlatformExtProcAddressTable() {
    return getCGLExtProcAddressTable();
  }

  public final CGLExtProcAddressTable getCGLExtProcAddressTable() {
    return cglExtProcAddressTable;
  }

  @Override
  protected Map<String, String> getFunctionNameMap() { return null; }

  @Override
  protected Map<String, String> getExtensionNameMap() { return null; }

  @Override
  protected long createContextARBImpl(final long share, final boolean direct, final int ctp, final int major, final int minor) {
    if(!isGLProfileSupported(ctp, major, minor)) {
        if(DEBUG) {
            System.err.println(getThreadName() + ": createContextARBImpl: Not supported "+getGLVersion(major, minor, ctp, "@creation on OSX "+Platform.getOSVersionNumber()));
        }
        return 0;
    }

    // Will throw exception upon error
    long ctx = impl.create(share, ctp, major, minor);
    if(0 != ctx) {
        if (!impl.makeCurrent(ctx)) {
          if(DEBUG) {
              System.err.println(getThreadName() + ": createContextARB couldn't make current "+getGLVersion(major, minor, ctp, "@creation"));
          }
          impl.release(ctx);
          impl.destroy(ctx);
          ctx = 0;
        } else if(DEBUG) {
            System.err.println(getThreadName() + ": createContextARBImpl: OK "+getGLVersion(major, minor, ctp, "@creation")+", share "+share+", direct "+direct+" on OSX "+Platform.getOSVersionNumber());
        }
    } else if(DEBUG) {
        System.err.println(getThreadName() + ": createContextARBImpl: NO "+getGLVersion(major, minor, ctp, "@creation on OSX "+Platform.getOSVersionNumber()));
    }
    return ctx;
  }

  @Override
  protected void destroyContextARBImpl(final long _context) {
      impl.release(_context);
      impl.destroy(_context);
  }

  @Override
  public final boolean isGLReadDrawableAvailable() {
    return false;
  }

  @Override
  protected boolean createImpl(final long shareWithHandle) throws GLException {
    final MacOSXCGLGraphicsConfiguration config = (MacOSXCGLGraphicsConfiguration) drawable.getNativeSurface().getGraphicsConfiguration();
    final AbstractGraphicsDevice device = config.getScreen().getDevice();
    final GLCapabilitiesImmutable glCaps = (GLCapabilitiesImmutable) config.getChosenCapabilities();
    final GLProfile glp = glCaps.getGLProfile();
    final boolean createContextARBAvailable = isCreateContextARBAvail(device);
    if(DEBUG) {
        System.err.println(getThreadName() + ": MacOSXCGLContext.createImpl: START "+glCaps+", share "+toHexString(shareWithHandle));
        System.err.println(getThreadName() + ": Use ARB[avail["+getCreateContextARBAvailStr(device)+
                "] -> "+createContextARBAvailable+"]]");
    }
    if( glp.isGLES() ||
        ( glp.isGL3() && !isLionOrLater ) || ( glp.isGL4() && !isMavericksOrLater ) ) {
        throw new GLException("OpenGL profile not supported on MacOSX "+Platform.getOSVersionNumber()+": "+glp);
    }
    if( 0 != shareWithHandle && GLBackendType.NSOPENGL != getOpenGLMode() ) {
        throw new GLException("Context sharing only supported in mode "+GLBackendType.NSOPENGL+": "+this);
    }
    contextHandle = createContextARB(shareWithHandle, true);
    return 0 != contextHandle;
  }

  @Override
  protected void makeCurrentImpl() throws GLException {
    /** FIXME: won't work w/ special drawables (like FBO) - check for CGL mode regressions!
     *
    if (getOpenGLMode() != ((MacOSXCGLDrawable)drawable).getOpenGLMode()) {
      setOpenGLMode(((MacOSXCGLDrawable)drawable).getOpenGLMode());
    } */
    if ( !impl.makeCurrent(contextHandle) ) {
      throw new GLException("Error making Context current: "+this);
    }
    drawableUpdatedNotify();
  }

  @Override
  protected void contextMadeCurrent(final boolean current) {
      impl.contextMadeCurrent(current);
      super.contextMadeCurrent(current);
  }

  @Override
  protected void releaseImpl() throws GLException {
    if (!impl.release(contextHandle)) {
      throw new GLException("Error releasing OpenGL Context: "+this);
    }
  }

  @Override
  protected void destroyImpl() throws GLException {
    releaseUpdateHandle();
    if(!impl.destroy(contextHandle)) {
        throw new GLException("Error destroying OpenGL Context: "+this);
    }
  }

  private final long getUpdateHandle() {
    if( 0 == updateHandle ) {
        lastWidth = -1;
        lastHeight = -1;
        if( isCreated() && drawable.getChosenGLCapabilities().isOnscreen() && isNSContext() ) {
            final boolean isSurfaceless, isIncompleteView;
            final NativeSurface surface = drawable.getNativeSurface();
            if( surface instanceof ProxySurface ) {
              final ProxySurface ps = (ProxySurface)surface;
              isSurfaceless = ps.containsUpstreamOptionBits( ProxySurface.OPT_UPSTREAM_SURFACELESS );
              isIncompleteView = isSurfaceless || ps.containsUpstreamOptionBits( ProxySurface.OPT_UPSTREAM_WINDOW_INVISIBLE );
            } else {
              isSurfaceless = false;
              isIncompleteView = false;
            }
            if( !isIncompleteView ) {
                updateHandle = CGL.updateContextRegister(contextHandle, drawable.getHandle(), false /* useAppKit .. onMain */);
                if(0 == updateHandle) {
                    throw new InternalError("XXX2");
                }
            }
        }
    }
    return updateHandle;
  }

  private final void releaseUpdateHandle() {
    if ( 0 != updateHandle ) {
        CGL.updateContextUnregister(updateHandle);
        updateHandle = 0;
    }
  }

  @Override
  protected void drawableUpdatedNotify() throws GLException {
    if( drawable.getChosenGLCapabilities().isOnscreen() ) {
        final long _updateHandle = getUpdateHandle();
        final int w = drawable.getSurfaceWidth();
        final int h = drawable.getSurfaceHeight();
        final boolean updateContext = ( 0!=_updateHandle && CGL.updateContextNeedsUpdate(_updateHandle) ) ||
                                      w != lastWidth || h != lastHeight;
        if(updateContext) {
            lastWidth = w;
            lastHeight = h;
            if (contextHandle == 0) {
              throw new GLException("Context not created");
            }
            CGL.updateContext(contextHandle, true /* useAppKit .. onMain */);
        }
    }
  }

  @Override
  protected void associateDrawable(final boolean bound) {
      // context stuff depends on drawable stuff
      if(bound) {
          super.associateDrawable(true);   // 1) init drawable stuff
          impl.associateDrawable(true);    // 2) init context stuff
          getUpdateHandle();
      } else {
          releaseUpdateHandle();
          impl.associateDrawable(false);   // 1) free context stuff
          super.associateDrawable(false);  // 2) free drawable stuff
      }
  }

  /* pp */ void detachPBuffer() {
      impl.detachPBuffer();
  }


  @Override
  protected void copyImpl(final GLContext source, final int mask) throws GLException {
    if( isNSContext() != ((MacOSXCGLContext)source).isNSContext() ) {
        throw new GLException("Source/Destination OpenGL Context type mismatch: source "+source+", dest: "+this);
    }
    if(!impl.copyImpl(source.getHandle(), mask)) {
        throw new GLException("Error copying OpenGL Context: source "+source+", dest: "+this);
    }
  }

  protected void swapBuffers() {
    // single-buffer is already filtered out @ GLDrawableImpl#swapBuffers()
    if(!impl.swapBuffers()) {
        throw new GLException("Error swapping buffers: "+this);
    }
  }

  @Override
  protected final Integer setSwapIntervalImpl2(final int interval) {
      if( !impl.isUsingCALayer() && !drawable.getChosenGLCapabilities().isOnscreen() ) {
          return null;
      }
      final int useInterval;
      if( 0 > interval ) {
          useInterval = Math.abs(interval);
      } else {
          useInterval = interval;
      }
      if( impl.setSwapInterval(useInterval) ) {
          return Integer.valueOf(useInterval);
      }
      return null;
  }

  /**
   * {@inheritDoc}
   * <p>
   * Ignoring {@code contextFQN}, using {@code MacOSX}-{@link AbstractGraphicsDevice#getUniqueID()}.
   * </p>
   */
  @Override
  protected final void updateGLXProcAddressTable(final String contextFQN, final GLDynamicLookupHelper dlh) {
    if( null == dlh ) {
        throw new GLException("No GLDynamicLookupHelper for "+this);
    }
    final AbstractGraphicsConfiguration aconfig = drawable.getNativeSurface().getGraphicsConfiguration();
    final AbstractGraphicsDevice adevice = aconfig.getScreen().getDevice();
    final String key = "MacOSX-"+adevice.getUniqueID();
    if (DEBUG) {
      System.err.println(getThreadName() + ": Initializing CGL extension address table: "+key);
    }
    ProcAddressTable table = null;
    synchronized(mappedContextTypeObjectLock) {
        table = mappedGLXProcAddress.get( key );
    }
    if(null != table) {
        cglExtProcAddressTable = (CGLExtProcAddressTable) table;
        if(DEBUG) {
            System.err.println(getThreadName() + ": GLContext CGL ProcAddressTable reusing key("+key+") -> "+toHexString(table.hashCode()));
        }
    } else {
        cglExtProcAddressTable = new CGLExtProcAddressTable(new GLProcAddressResolver());
        resetProcAddressTable(getCGLExtProcAddressTable(), dlh);
        synchronized(mappedContextTypeObjectLock) {
            mappedGLXProcAddress.put(key, getCGLExtProcAddressTable());
            if(DEBUG) {
                System.err.println(getThreadName() + ": GLContext CGL ProcAddressTable mapping key("+key+") -> "+toHexString(getCGLExtProcAddressTable().hashCode()));
            }
        }
    }
  }

  @Override
  protected final StringBuilder getPlatformExtensionsStringImpl() {
    return new StringBuilder();
  }

  // Support for "mode switching" as described in MacOSXCGLDrawable
  public void setOpenGLMode(final GLBackendType mode) {
      if (mode == openGLMode) {
        return;
      }
      if (haveSetOpenGLMode) {
        throw new GLException("Can't switch between using NSOpenGLPixelBuffer and CGLPBufferObj more than once");
      }
      destroyImpl();
      ((MacOSXCGLDrawable)drawable).setOpenGLMode(mode);
      if (DEBUG) {
        System.err.println("MacOSXCGLContext: Switching context mode " + openGLMode + " -> " + mode);
      }
      initOpenGLImpl(mode);
      openGLMode = mode;
      haveSetOpenGLMode = true;
  }
  public final GLBackendType getOpenGLMode() { return openGLMode; }

  protected void initOpenGLImpl(final GLBackendType backend) {
    switch (backend) {
      case NSOPENGL:
        impl = new NSOpenGLImpl();
        break;
      case CGL:
        impl = new CGLImpl();
        break;
      default:
        throw new InternalError("Illegal implementation mode " + backend);
    }
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    super.append(sb);
    sb.append(", mode ");
    sb.append(openGLMode);
    sb.append("] ");
    return sb.toString();
  }

  static class NSViewDescriptor {
      final boolean isSurfaceless;
      final boolean isIncomplete;
      final boolean isPBuffer;
      final boolean isFBO;
      /** Only returns a valid NSView. If !NSView, return null and mark either isPBuffer, isFBO or isSurfaceless. */
      final long nsViewHandle;

      NSViewDescriptor(final GLDrawableImpl drawable) {
          final NativeSurface surface = drawable.getNativeSurface();
          if( surface instanceof ProxySurface ) {
              final ProxySurface ps = (ProxySurface)surface;
              isSurfaceless = ps.containsUpstreamOptionBits( ProxySurface.OPT_UPSTREAM_SURFACELESS );
              isIncomplete = isSurfaceless || ps.containsUpstreamOptionBits( ProxySurface.OPT_UPSTREAM_WINDOW_INVISIBLE );
          } else {
              isSurfaceless = false;
              isIncomplete = false;
          }

          if( drawable instanceof GLFBODrawableImpl ) {
              nsViewHandle = 0;
              isPBuffer = false;
              isFBO = true;
          } else if( isSurfaceless || isIncomplete ) {
              nsViewHandle = 0;
              isPBuffer = false;
              isFBO = false;
          } else {
              final long drawableHandle = drawable.getHandle();
              final boolean isNSView = OSXUtil.isNSView(drawableHandle);
              final boolean isNSWindow = OSXUtil.isNSWindow(drawableHandle);
              isPBuffer = CGL.isNSOpenGLPixelBuffer(drawableHandle);
              isFBO = false;

              if( isPBuffer ) {
                  nsViewHandle = 0;
              } else if( isNSView ) {
                  nsViewHandle = drawableHandle;
              } else if( isNSWindow ) {
                  nsViewHandle = OSXUtil.GetNSView(drawableHandle);
              } else {
                  throw new GLException("Drawable's handle neither NSView, NSWindow nor PBuffer: drawableHandle "+toHexString(drawableHandle)+", isNSView "+isNSView+", isNSWindow "+isNSWindow+", isFBO "+isFBO+", isPBuffer "+isPBuffer+", "+drawable.getClass().getName()+",\n\t"+drawable);
              }
          }
      }

      @Override
      public String toString() {
          return "NSViewDescr[nsViewHandle "+toHexString(nsViewHandle)+", isSurfaceless "+isSurfaceless+", isIncomplete "+isIncomplete+", isFBO "+isFBO+", isPBuffer "+isPBuffer+"]";
      }
  }

  private static final boolean isAWTEventDispatchThread() {
      if( NativeWindowFactory.isAWTAvailable() ) {
          return java.awt.EventQueue.isDispatchThread();
      } else {
          return false;
      }
  }

  // NSOpenGLContext-based implementation
  class NSOpenGLImpl implements GLBackendImpl {
      private OffscreenLayerSurface backingLayerHost;
      /** lifecycle:  [create - destroy] */
      private long pixelFormat;
      /** microSec - defaults to 1/60s */
      private int screenVSyncTimeout;
      /** microSec - for nsOpenGLLayer mode - defaults to 1/60s + 1ms */
      private volatile int vsyncTimeout;
      private int lastWidth, lastHeight; // allowing to detect size change
      private boolean needsSetContextPBuffer;
      private ShaderProgram gl3ShaderProgram;
      private boolean drawableAssociated;
      private AttachGLLayerCmd attachGLLayerCmd;
      private NSViewDescriptor lastNSViewDescr; // Bug 1398
      private SetNSViewCmd lastSetNSViewCmd; // Bug 1398
      private boolean cglContextLocked; // Bug 1398

      NSOpenGLImpl() { resetState(); }

      private void resetState() {
          backingLayerHost = null;
          pixelFormat = 0;
          screenVSyncTimeout = 16666;
          vsyncTimeout = 16666 + 1000;
          lastWidth=0; lastHeight=0;
          needsSetContextPBuffer = false;
          gl3ShaderProgram = null;
          drawableAssociated = false;
          attachGLLayerCmd = null;
          lastNSViewDescr = null;
          lastSetNSViewCmd = null;
          cglContextLocked = false;
      }

      @Override
      public boolean isNSContext() { return true; }

      @Override
      public boolean isUsingCALayer() { return null != backingLayerHost; }

      @Override
      public long create(final long share, final int ctp, final int major, final int minor) {
          long ctx = 0;
          final NativeSurface surface = drawable.getNativeSurface();
          final MacOSXCGLGraphicsConfiguration config = (MacOSXCGLGraphicsConfiguration) surface.getGraphicsConfiguration();
          final GLCapabilitiesImmutable chosenCaps = (GLCapabilitiesImmutable) config.getChosenCapabilities();
          final OffscreenLayerSurface backingLayerHost = NativeWindowFactory.getOffscreenLayerSurface(surface, true);
          final NSViewDescriptor nsViewDescr = new NSViewDescriptor(drawable);
          needsSetContextPBuffer = nsViewDescr.isPBuffer;
          {
              final GLCapabilitiesImmutable targetCaps;
              if( nsViewDescr.isFBO ) {
                  // Use minimum GLCapabilities for the target surface w/ same profile
                  targetCaps = new GLCapabilities( chosenCaps.getGLProfile() );
              } else {
                  targetCaps = chosenCaps;
              }
              pixelFormat = MacOSXCGLGraphicsConfiguration.GLCapabilities2NSPixelFormat(config.getScreen().getDevice(), targetCaps, ctp, major, minor);
          }
          if (pixelFormat == 0) {
              if(DEBUG) {
                  System.err.println("Unable to allocate pixel format with requested GLCapabilities: "+chosenCaps);
              }
              return 0;
          }
          final GLCapabilitiesImmutable fixedCaps;
          if( nsViewDescr.isFBO ) {
              // pixelformat of target doesn't affect caps w/ FBO
              fixedCaps = chosenCaps;
          } else {
              final GLCapabilities _fixedCaps = MacOSXCGLGraphicsConfiguration.NSPixelFormat2GLCapabilities(chosenCaps.getGLProfile(), pixelFormat);
              if( !_fixedCaps.isPBuffer() && nsViewDescr.isPBuffer ) {
                  throw new InternalError("handle is PBuffer, fixedCaps not: "+drawable);
              }
              // determine on-/offscreen caps, since pformat is ambiguous
              _fixedCaps.setPBuffer( nsViewDescr.isPBuffer ); // exclusive
              _fixedCaps.setBitmap( false );      // n/a in our OSX impl.
              _fixedCaps.setOnscreen( !nsViewDescr.isFBO && !nsViewDescr.isPBuffer && !nsViewDescr.isSurfaceless );
              fixedCaps = GLGraphicsConfigurationUtil.fixOpaqueGLCapabilities(_fixedCaps, chosenCaps.isBackgroundOpaque());
          }
          final int sRefreshRate = OSXUtil.GetScreenRefreshRate(drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getIndex());
          if( 0 < sRefreshRate ) {
              screenVSyncTimeout = 1000000 / sRefreshRate;
          }
          if(DEBUG) {
              System.err.println("NS create OSX>=lion "+isLionOrLater+", OSX>=mavericks "+isMavericksOrLater+" - isAWTEDT "+isAWTEventDispatchThread()+", "+Thread.currentThread());
              System.err.println("NS create drawable type: "+drawable.getClass().getName());
              System.err.println("NS create surface type: "+surface.getClass().getName());
              System.err.println("NS create drawable native-handle: "+toHexString(drawable.getHandle()));
              System.err.println("NS create NSViewDescriptor: "+nsViewDescr);
              System.err.println("NS create backingLayerHost: "+backingLayerHost);
              System.err.println("NS create share: "+share);
              System.err.println("NS create pixelFormat: "+toHexString(pixelFormat));
              System.err.println("NS create chosenCaps: "+chosenCaps);
              System.err.println("NS create fixedCaps: "+fixedCaps);
              System.err.println("NS create screen refresh-rate: "+sRefreshRate+" hz, "+screenVSyncTimeout+" micros");
              // Thread.dumpStack();
          }
          config.setChosenCapabilities(fixedCaps);

          // Bug 1398: Associate NSView with NSOpenGLContext in makeCurrent
          ctx = CGL.createContext(share, pixelFormat, chosenCaps.isBackgroundOpaque());
          if (0 == ctx) {
              if(DEBUG) {
                  System.err.println("NS createContext failed: share "+toHexString(share)+", pfmt "+toHexString(pixelFormat)+", opaque "+chosenCaps.isBackgroundOpaque());
              }
              return 0;
          }

          if (chosenCaps.isOnscreen() && !chosenCaps.isBackgroundOpaque()) {
              // Set the context opacity
              CGL.setContextOpacity(ctx, 0);
          }
          return ctx;
      }

      @Override
      public boolean destroy(final long ctx) {
          if(0!=pixelFormat) {
              CGL.deletePixelFormat(pixelFormat);
              pixelFormat = 0;
          }
          resetState();
          return CGL.deleteContext(ctx, true);
      }

      /**
       * NSOpenGLLayer creation and it's attachment is performed on the main-thread w/o [infinite] blocking.
       * <p>
       * Since NSOpenGLLayer creation requires this context for it's shared context creation,
       * this method attempts to acquire the surface and context lock with {@link #screenVSyncTimeout}/2 maximum wait time.
       * If the surface and context lock could not be acquired, this runnable is being re-queued for later execution.
       * </p>
       * <p>
       * Hence this method blocks the main-thread only for a short period of time.
       * </p>
       */
      class AttachGLLayerCmd implements Runnable {
          final OffscreenLayerSurface ols;
          final long ctx;
          final int shaderProgram;
          final long pfmt;
          final long pbuffer;
          final int texID;
          final boolean isOpaque;
          final int texWidth;
          final int texHeight;
          final int winWidth;
          final int winHeight;
          /** Synchronized by instance's monitor */
          long nsOpenGLLayer;
          /** Synchronized by instance's monitor */
          boolean done;
          /** Synchronized by instance's monitor */
          boolean revoke;

          AttachGLLayerCmd(final OffscreenLayerSurface ols, final long ctx, final int shaderProgram, final long pfmt, final long pbuffer, final int texID,
                           final boolean isOpaque, final int texWidth, final int texHeight, final int winWidth, final int winHeight) {
              this.ols = ols;
              this.ctx = ctx;
              this.shaderProgram = shaderProgram;
              this.pfmt = pfmt;
              this.pbuffer = pbuffer;
              this.texID = texID;
              this.isOpaque = isOpaque;
              this.texWidth = texWidth;
              this.texHeight = texHeight;
              this.winWidth = winWidth;
              this.winHeight = winHeight;
              this.done = false;
              this.revoke = false;
              this.nsOpenGLLayer = 0;
          }

          public final String contentToString() {
              return "done "+done+", revoke "+revoke+", size tex["+texWidth+"x"+texHeight+"], win["+winWidth+"x"+winHeight+"], ctx "+toHexString(ctx)+", opaque "+isOpaque+", texID "+texID+", pbuffer "+toHexString(pbuffer)+", nsOpenGLLayer "+toHexString(nsOpenGLLayer);
          }

          @Override
          public final String toString() {
              return "AttachGLLayerCmd["+contentToString()+"]";
          }

          @Override
          public void run() {
              synchronized(this) {
                  if( !done && !revoke ) {
                      try {
                          final int maxwait = screenVSyncTimeout/2000; // TO 1/2 of current screen-vsync in [ms]
                          final RecursiveLock surfaceLock = ols.getLock();
                          if( surfaceLock.tryLock( maxwait ) ) {
                              try {
                                  if( MacOSXCGLContext.this.lock.tryLock( maxwait ) ) {
                                      try {
                                          nsOpenGLLayer = CGL.createNSOpenGLLayer(ctx, shaderProgram, pfmt, pbuffer, texID, isOpaque,
                                                                                  texWidth, texHeight, winWidth, winHeight);
                                          ols.attachSurfaceLayer(nsOpenGLLayer);
                                          final int currentInterval = MacOSXCGLContext.this.getSwapInterval();
                                          final int interval = 0 <= currentInterval ? currentInterval : 1;
                                          setSwapIntervalImpl(nsOpenGLLayer, interval); // enabled per default in layered surface
                                          done = true;
                                          if (DEBUG) {
                                              System.err.println("NSOpenGLLayer.Attach: OK, layer "+toHexString(nsOpenGLLayer)+" w/ pbuffer "+toHexString(pbuffer)+", texID "+texID+", texSize "+lastWidth+"x"+lastHeight+", drawableHandle "+toHexString(drawable.getHandle())+" - "+getThreadName());
                                          }
                                      } finally {
                                          MacOSXCGLContext.this.lock.unlock();
                                      }
                                  }
                              } finally {
                                  surfaceLock.unlock();
                              }
                          }
                      } catch (final InterruptedException e) {
                          e.printStackTrace();
                      }
                      if( !done ) {
                          // could not acquire lock, re-queue
                          if (DEBUG) {
                              System.err.println("NSOpenGLLayer.Attach: Re-Queue, drawableHandle "+toHexString(drawable.getHandle())+" - "+getThreadName());
                          }
                          OSXUtil.RunLater(true /* onMain */, this, 1);
                      }
                  }
                  this.notifyAll();
              }
          }
      }

      class DetachGLLayerCmd implements Runnable {
        final AttachGLLayerCmd cmd;

        DetachGLLayerCmd(final AttachGLLayerCmd cmd) {
            this.cmd = cmd;
        }

        @Override
        public final String toString() {
            return "DetachGLLayerCmd["+cmd.contentToString()+"]";
        }

        @Override
        public void run() {
            synchronized( cmd ) {
                if( cmd.done ) {
                    // still having a valid OLS attached to surface (parent OLS could have been removed)
                    try {
                        final OffscreenLayerSurface ols = cmd.ols;
                        final long l = ols.getAttachedSurfaceLayer();
                        if( 0 != l ) {
                            ols.detachSurfaceLayer();
                        }
                    } catch(final Throwable t) {
                        System.err.println("Caught exception on thread "+getThreadName());
                        t.printStackTrace();
                    }
                    if( 0 != cmd.nsOpenGLLayer ) {
                        CGL.releaseNSOpenGLLayer(cmd.nsOpenGLLayer);
                    }
                    if(DEBUG) {
                        System.err.println("NSOpenGLLayer.Detach: OK, layer "+toHexString(cmd.nsOpenGLLayer)+" - "+getThreadName());
                    }
                    cmd.nsOpenGLLayer = 0;
                    cmd.done = false;
                } else if(DEBUG) {
                    System.err.println("NSOpenGLLayer.Detach: Skipped "+toHexString(cmd.nsOpenGLLayer)+" - "+getThreadName());
                }
                cmd.revoke = true; // revoke is essential if attach hasn't been done yet.
                cmd.notifyAll();
            }
        }
      }

      @Override
      public void associateDrawable(final boolean bound) {
          backingLayerHost = NativeWindowFactory.getOffscreenLayerSurface(drawable.getNativeSurface(), true);

          if(DEBUG) {
              System.err.println("MacOSXCGLContext.NSOpenGLImpl.associateDrawable: "+bound+", ctx "+toHexString(contextHandle)+
                                 ", hasBackingLayerHost "+(null!=backingLayerHost)+", attachGLLayerCmd "+attachGLLayerCmd);
              // Thread.dumpStack();
          }

          if( bound ) {
              drawableAssociated = true;
              if( null != backingLayerHost ) {
                  final GLCapabilitiesImmutable chosenCaps;
                  final long ctx;
                  final int texID;
                  final long pbufferHandle;
                  final int gl3ShaderProgramName;

                  //
                  // handled layered surface
                  //
                  chosenCaps = drawable.getChosenGLCapabilities();
                  ctx = MacOSXCGLContext.this.getHandle();
                  final long drawableHandle = drawable.getHandle();
                  if(drawable instanceof GLFBODrawableImpl) {
                      final GLFBODrawableImpl fbod = (GLFBODrawableImpl)drawable;
                      texID = fbod.getColorbuffer(GL.GL_FRONT).getName();
                      pbufferHandle = 0;
                      fbod.setSwapBufferContext(new GLFBODrawableImpl.SwapBufferContext() {
                          @Override
                          public void swapBuffers(final boolean doubleBuffered) {
                              MacOSXCGLContext.NSOpenGLImpl.this.swapBuffers();
                          } } ) ;
                  } else if( CGL.isNSOpenGLPixelBuffer(drawableHandle) ) {
                      texID = 0;
                      pbufferHandle = drawableHandle;
                      if(0 != drawableHandle) { // complete 'validatePBufferConfig(..)' procedure
                          CGL.setContextPBuffer(ctx, pbufferHandle);
                          needsSetContextPBuffer = false;
                      }
                  } else {
                      throw new GLException("BackingLayerHost w/ unknown handle (!FBO, !PBuffer): "+drawable);
                  }
                  lastWidth = drawable.getSurfaceWidth();
                  lastHeight = drawable.getSurfaceHeight();
                  if(0>=lastWidth || 0>=lastHeight || !drawable.isRealized()) {
                      throw new GLException("Drawable not realized yet or invalid texture size, texSize "+lastWidth+"x"+lastHeight+", "+drawable);
                  }
                  if( MacOSXCGLContext.this.isGL3core() ) {
                      if( null == gl3ShaderProgram) {
                          gl3ShaderProgram = createCALayerShader(MacOSXCGLContext.this.gl.getGL3ES3());
                      }
                      gl3ShaderProgramName = gl3ShaderProgram.program();
                  } else {
                      gl3ShaderProgramName = 0;
                  }

                  // All CALayer lifecycle ops are deferred on main-thread
                  final int[] winSize = drawable.getNativeSurface().convertToWindowUnits(new int[]{ lastWidth, lastHeight });
                  attachGLLayerCmd = new AttachGLLayerCmd(
                          backingLayerHost, ctx, gl3ShaderProgramName, pixelFormat, pbufferHandle, texID,
                          chosenCaps.isBackgroundOpaque(), lastWidth, lastHeight, winSize[0], winSize[1] );
                  if(DEBUG) {
                      System.err.println("MacOSXCGLContext.NSOpenGLImpl.associateDrawable(true).calayer: "+attachGLLayerCmd);
                  }
                  OSXUtil.RunOnMainThread(false /* wait */, false /* kickNSApp */, attachGLLayerCmd);
              } else { // -> null == backingLayerHost
                  lastWidth = drawable.getSurfaceWidth();
                  lastHeight = drawable.getSurfaceHeight();
              }
          } else { // -> !bound
              drawableAssociated = false;
              if( null != backingLayerHost ) {
                  final AttachGLLayerCmd cmd = attachGLLayerCmd;
                  attachGLLayerCmd = null;
                  if( null == cmd ) {
                      throw new GLException("Null attachGLLayerCmd: "+drawable);
                  }
                  if( 0 != cmd.pbuffer ) {
                      CGL.setContextPBuffer(contextHandle, 0);
                  }
                  synchronized(cmd) {
                      if( !cmd.done ) {
                          cmd.done = true; // skip pending creation
                      } else {
                          // All CALayer lifecycle ops are deferred on main-thread
                          final DetachGLLayerCmd dCmd = new DetachGLLayerCmd(cmd);
                          if(DEBUG) {
                              System.err.println("MacOSXCGLContext.NSOpenGLImpl.associateDrawable(false).calayer: "+dCmd+" - "+Thread.currentThread().getName());
                          }
                          OSXUtil.RunOnMainThread(false /* wait */, true /* kickNSApp */, dCmd);
                          if( null != gl3ShaderProgram ) {
                              gl3ShaderProgram.destroy(MacOSXCGLContext.this.gl.getGL3());
                              gl3ShaderProgram = null;
                          }
                      }
                  }
              }
              CGL.clearDrawable(contextHandle);
              backingLayerHost = null;
          }
      }

      private final void validatePBufferConfig(final long ctx) {
          final long drawableHandle = drawable.getHandle();
          if( needsSetContextPBuffer && 0 != drawableHandle && CGL.isNSOpenGLPixelBuffer(drawableHandle) ) {
              // Must associate the pbuffer with our newly-created context
              needsSetContextPBuffer = false;
              CGL.setContextPBuffer(ctx, drawableHandle);
              if(DEBUG) {
                  System.err.println("NS.validateDrawableConfig bind pbuffer "+toHexString(drawableHandle)+" -> ctx "+toHexString(ctx));
              }
          }
      }

      /** Returns true if size has been updated, otherwise false (same size). */
      private final boolean validateDrawableSizeConfig(final long ctx) {
          final int width = drawable.getSurfaceWidth();
          final int height = drawable.getSurfaceHeight();
          if( lastWidth != width || lastHeight != height ) {
              lastWidth = drawable.getSurfaceWidth();
              lastHeight = drawable.getSurfaceHeight();
              if(DEBUG) {
                  System.err.println("NS.validateDrawableConfig size changed");
              }
              return true;
          }
          return false;
      }

      @Override
      public boolean copyImpl(final long src, final int mask) {
          CGL.copyContext(contextHandle, src, mask);
          return true;
      }

      @Override
      public boolean makeCurrent(final long ctx) {
          // Bug 1398: Perform SetNSViewCmd's on Main-Thread w/o blocking other tasks
          // - Only issue SetNSViewCmd if changed (boolean nsViewChanged).
          // - Skip CGLLockContext if SetNSViewCmd is pending (bool lockCGLContext -> cglContextLocked).
          // - Potentially skipped CGLLockContext gets cured in contextMadeCurrent(true) below
          // - See SetNSViewCmd API-doc
          //
          final NSViewDescriptor nsViewDescr = new NSViewDescriptor(drawable);
          needsSetContextPBuffer = nsViewDescr.isPBuffer;
          final boolean nsViewChanged = null == lastNSViewDescr && 0 != nsViewDescr.nsViewHandle || /** only if initial nsView is onscreen */
                                        null != lastNSViewDescr && lastNSViewDescr.nsViewHandle != nsViewDescr.nsViewHandle; /** if nsView has changed */

          if( DEBUG1398 ) {
              if(!insideContextMadeCurrent) {
                  System.err.println();
              }
              System.err.println("MacOSXCGLContext.makeCurrent Bug1398: recursive "+insideContextMadeCurrent+", nsViewChanged "+nsViewChanged+", isAWTEDT "+isAWTEventDispatchThread()+", "+Thread.currentThread());
              System.err.println("  NSViewDescriptor: last "+lastNSViewDescr);
              System.err.println("  NSViewDescriptor: curr "+nsViewDescr);
          }
          lastNSViewDescr = nsViewDescr;

          if( nsViewChanged ) {
              final SetNSViewCmd cmd = new SetNSViewCmd(ctx, nsViewDescr);
              lastSetNSViewCmd = cmd;
              OSXUtil.RunOnMainThread(false /* wait */, false /* kickNSApp */, cmd);
          }
          final boolean lockCGLContext;
          final SetNSViewCmd _lastSetNSViewCmd = lastSetNSViewCmd;
          if( null != _lastSetNSViewCmd ) {
              synchronized( _lastSetNSViewCmd ) {
                  lockCGLContext = _lastSetNSViewCmd.done;
                  if( lockCGLContext ) {
                      lastSetNSViewCmd = null; // done, no more required
                  } else if( DEBUG1398 ) {
                      System.err.println("MacOSXCGLContext.makeCurrent Bug1398: Skip CGLLockContext, "+Thread.currentThread());
                  }
              }
          } else {
              lockCGLContext = true;
          }

          final long cglCtx = CGL.getCGLContext(ctx);
          if(0 == cglCtx) {
              throw new InternalError("Null CGLContext for: "+this);
          }
          final int err = lockCGLContext ? CGL.CGLLockContext(cglCtx) : CGL.kCGLNoError;
          if(CGL.kCGLNoError == err) {
              cglContextLocked = lockCGLContext;
              validatePBufferConfig(ctx); // required to handle pbuffer change ASAP
              return CGL.makeCurrentContext(ctx);
          } else {
              cglContextLocked = false;
              if(DEBUG) {
                  System.err.println("MacOSXCGLContext.makeCurrent: Could not lock context: err 0x"+Integer.toHexString(err)+": "+this);
              }
              return false;
          }
      }

      boolean insideContextMadeCurrent = false; // ensure no recursion occurs

      @Override
      public void contextMadeCurrent(final boolean current) {
          if( current && !insideContextMadeCurrent && !cglContextLocked && !isAWTEventDispatchThread() ) {
              // Bug 1398: Cure missing CGLContextLock by context release/makeCurrent cycle outside context acquisition code,
              // only for user makeCurrent calls, not createContext*() only.
              // See SetNSViewCmd API-doc.
              //
              // Notice: We can't block on AWTEDT until SetNSViewCmd is done on AppKit,
              // as there is a feedback flush loop AppKit -> AWTEDT in the AWT-AppKit code.
              // See sun.lwawt.macosx.CPlatformWindow.flushBuffers(CPlatformWindow.java:957) and TestBug1398Deadlock02AWT
              insideContextMadeCurrent = true;
              try {
                  final RecursiveLock surfaceLock = drawable.getNativeSurface().getLock();
                  final int surfaceLockCount = null != surfaceLock ? surfaceLock.getHoldCount() : 1;

                  if(DEBUG1398) {
                      System.err.println("MacOSXCGLContext.contextMadeCurrent.0 Bug1398: Cure missing CGLContextLock, "+Thread.currentThread());
                      System.err.println("  SurfaceLock: "+surfaceLock);
                  }
                  // Reduce lock-count so context.release()'s surface.unlockSurface() will actually release the lock
                  for(int i=1; i<surfaceLockCount; i++) {
                      surfaceLock.unlock();
                  }
                  MacOSXCGLContext.this.release(); // implies final surface.unlockSurface();

                  final SetNSViewCmd _lastSetNSViewCmd = lastSetNSViewCmd;
                  if( null != _lastSetNSViewCmd ) {
                      synchronized( _lastSetNSViewCmd ) {
                          final long t0 = Platform.currentTimeMillis();
                          long t1 = t0;
                          while( !_lastSetNSViewCmd.done && SetNSViewCmd.Timeout > t1-t0 ) {
                              try {
                                  _lastSetNSViewCmd.wait(SetNSViewCmd.Timeout); // last resort avoiding deadlock via timeout
                              } catch (final InterruptedException e) { }
                              t1 = Platform.currentTimeMillis();
                          }
                          if(DEBUG1398) {
                              System.err.println("MacOSXCGLContext.contextMadeCurrent.1 Bug1398: SetNSViewCmd[waited "+(t1-t0)+"ms, done "+_lastSetNSViewCmd.done+"], surfaceLock "+surfaceLock);
                          }
                          lastSetNSViewCmd = null; // if !done due to timeout, avoid another !cglContextLocked and hence SetNSViewCmd issuance
                      }
                  }
                  MacOSXCGLContext.this.makeCurrent(); // makes current again w/ cglContextLocked
                  // Repair original lock-count
                  for(int i=1; i<surfaceLockCount; i++) {
                      surfaceLock.lock();
                  }
              } finally {
                  insideContextMadeCurrent = false;
              }
          }
      }

      @Override
      public boolean release(final long ctx) {
          try {
              if( hasRendererQuirk(GLRendererQuirks.GLFlushBeforeRelease) && null != MacOSXCGLContext.this.getGLProcAddressTable() ) {
                  gl.glFlush();
              }
          } catch (final GLException gle) {
              if(DEBUG) {
                  System.err.println("MacOSXCGLContext.NSOpenGLImpl.release: INFO: glFlush() caught exception:");
                  gle.printStackTrace();
              }
          }
          if( DEBUG1398 ) {
              System.err.println("MacOSXCGLContext.release Bug1398: recursive "+insideContextMadeCurrent+", cglContextLocked "+cglContextLocked+", isAWTEDT "+isAWTEventDispatchThread()+", "+Thread.currentThread());
              if(!insideContextMadeCurrent) {
                  System.err.println();
              }
          }
          final boolean res = CGL.clearCurrentContext(ctx);
          final long cglCtx = CGL.getCGLContext(ctx);
          if(0 == cglCtx) {
              throw new InternalError("Null CGLContext for: "+this);
          }
          final int err = cglContextLocked ? CGL.CGLUnlockContext(cglCtx) : CGL.kCGLNoError;
          cglContextLocked = false;
          if(DEBUG && CGL.kCGLNoError != err) {
              System.err.println("CGL: Could not unlock context: err 0x"+Integer.toHexString(err)+": "+this);
          }
          if( !drawableAssociated ) {
              lastNSViewDescr = null;
              lastSetNSViewCmd = null;
              final boolean wait = !MacOSXCGLContext.isMojaveOrLater; // wait if < 10.14
              OSXUtil.RunOnMainThread(wait, true /* kickNSApp */, new Runnable() {
                  @Override
                  public void run() {
                      CGL.setContextView(ctx, 0);
                  } } );
          }
          return res && CGL.kCGLNoError == err;
      }

      @Override
      public boolean detachPBuffer() {
          needsSetContextPBuffer = true;
          // CGL.setContextPBuffer(contextHandle, 0); // doesn't work, i.e. not taking nil
          return true;
      }

      @Override
      public boolean setSwapInterval(final int interval) {
          final AttachGLLayerCmd cmd = attachGLLayerCmd;
          if(null != cmd) {
              synchronized(cmd) {
                  if( cmd.done && 0 != cmd.nsOpenGLLayer) {
                      setSwapIntervalImpl(cmd.nsOpenGLLayer, interval);
                      return true;
                  }
              }
          }
          setSwapIntervalImpl(0, interval);
          return true;
      }

      private void setSwapIntervalImpl(final long l, final int interval) {
          if( 0 != l ) {
              CGL.setNSOpenGLLayerSwapInterval(l, interval);
              if( 0 < interval ) {
                  vsyncTimeout = interval * screenVSyncTimeout + 1000; // +1ms
              } else {
                  vsyncTimeout = 1 * screenVSyncTimeout + 1000; // +1ms
              }
              if(DEBUG) { System.err.println("NS setSwapInterval: "+interval+" -> "+vsyncTimeout+" micros"); }
          }
          if(DEBUG) { System.err.println("CGL setSwapInterval: "+interval); }
          CGL.setSwapInterval(contextHandle, interval);
      }

      private int skipSync=0;
      /** TODO: Remove after discussion
      private boolean perfIterReset = false;
      private int perfIter = 0;
      private long waitGLS = 0;
      private long finishGLS = 0;
      private long frameXS = 0;
      private long lastFrameStart = 0;
      */

      @Override
      public boolean swapBuffers() {
          final AttachGLLayerCmd cmd = attachGLLayerCmd;
          if(null != cmd) {
              synchronized(cmd) {
                  if( cmd.done && 0 != cmd.nsOpenGLLayer) {
                      if( validateDrawableSizeConfig(contextHandle) ) {
                          // skip wait-for-vsync for a few frames if size has changed,
                          // allowing to update the texture IDs ASAP.
                          skipSync = 10;
                      }

                      final boolean res;
                      final int texID;
                      final boolean valid;
                      final boolean isFBO = drawable instanceof GLFBODrawableImpl;
                      if( isFBO ){
                          texID = ((GLFBODrawableImpl)drawable).getColorbuffer(GL.GL_FRONT).getName();
                          valid = 0 != texID;
                      } else {
                          texID = 0;
                          valid = 0 != drawable.getHandle();
                      }
                      if(valid) {
                          res = CGL.flushBuffer(contextHandle);
                          if(res) {
                              if(0 == skipSync) {
                                  /** TODO: Remove after discussion
                                  perfIter++;
                                  if( !perfIterReset && 100 == perfIter ) {
                                      perfIterReset = true;
                                      perfIter = 1;
                                      waitGLS = 0;
                                      finishGLS = 0;
                                      frameXS = 0;
                                  }
                                  final long lastFramePeriod0 = TimeUnit.NANOSECONDS.toMicros(System.nanoTime()) - lastFrameStart;
                                  gl.glFinish(); // Require to finish previous GL rendering to give CALayer proper result
                                  final long lastFramePeriod1 = TimeUnit.NANOSECONDS.toMicros(System.nanoTime()) - lastFrameStart;

                                  // If v-sync is disabled, frames will be drawn as quickly as possible w/o delay,
                                  // while still synchronizing w/ CALayer.
                                  // If v-sync is enabled wait until next swap interval (v-sync).
                                  CGL.waitUntilNSOpenGLLayerIsReady(cmd.nsOpenGLLayer, vsyncTimeout);
                                  final long lastFramePeriodX = TimeUnit.NANOSECONDS.toMicros(System.nanoTime()) - lastFrameStart;

                                  final long finishGL = lastFramePeriod1 - lastFramePeriod0;
                                  final long waitGL = lastFramePeriodX - lastFramePeriod1;
                                  finishGLS += finishGL;
                                  waitGLS += waitGL;
                                  frameXS += lastFramePeriodX;

                                  System.err.println("XXX["+perfIter+"] TO "+vsyncTimeout/1000+" ms, "+
                                                     "lFrame0 "+lastFramePeriod0/1000+" ms, "+
                                                     "lFrameX "+lastFramePeriodX/1000+" / "+frameXS/1000+" ~"+(frameXS/perfIter)/1000.0+" ms, "+
                                                     "finishGL "+finishGL/1000+" / "+finishGLS/1000+" ~"+(finishGLS/perfIter)/1000.0+" ms, "+
                                                     "waitGL "+waitGL/1000+" / "+waitGLS/1000+" ~"+(waitGLS/perfIter)/1000.0+" ms");
                                  */
                                  //
                                  // Required(?) to finish previous GL rendering to give CALayer proper result,
                                  // i.e. synchronize both threads each w/ their GLContext sharing same resources.
                                  //
                                  // FIXME: IMHO this synchronization should be implicitly performed via 'CGL.flushBuffer(contextHandle)' above,
                                  // in case this will be determined a driver bug - use a QUIRK entry in GLRendererQuirks!
                                  gl.glFinish();

                                  // If v-sync is disabled, frames will be drawn as quickly as possible w/o delay,
                                  // while still synchronizing w/ CALayer.
                                  // If v-sync is enabled wait until next swap interval (v-sync).
                                  CGL.waitUntilNSOpenGLLayerIsReady(cmd.nsOpenGLLayer, vsyncTimeout);
                              } else {
                                  skipSync--;
                              }
                              if(isFBO) {
                                  // trigger CALayer to update incl. possible surface change (texture)
                                  CGL.setNSOpenGLLayerNeedsDisplayFBO(cmd.nsOpenGLLayer, texID);
                              } else {
                                  // trigger CALayer to update incl. possible surface change (new pbuffer handle)
                                  CGL.setNSOpenGLLayerNeedsDisplayPBuffer(cmd.nsOpenGLLayer, drawable.getHandle());
                              }
                              // lastFrameStart = TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
                          }
                      } else {
                          res = true;
                      }
                      return res;
                  }
              }
          }
          return CGL.flushBuffer(contextHandle);
      }

      /**
       * Command sets NSOpenGLContext's NSView via [NSOpenGLContext setView:]
       * on the main-thread as enforced since XCode 11 using SDK macosx10.15,
       * see Bug 1398.
       * <p>
       * This command is injected into OSX's main-thread @ {@link NSOpenGLImpl#makeCurrent(long)}
       * only if required, i.e. issued only for a newly bound NSView and
       * skipped for surface-less or offscreen 'surfaces'.
       * </p>
       * <p>
       * This operation must be performed w/o blocking
       * other tasks locking the {@link NativeSurface} on main-thread to complete.
       * </p>
       * <p>
       * Since [NSOpenGLContext setView:] acquires the CGLContext lock on the main-thread,
       * it can't be locked by the calling thread until this task has been completed.
       * <br>
       * Command issuer {@link NSOpenGLImpl#makeCurrent(long)} will not acquire the CGLContext lock
       * if this command is pending.
       * </p>
       * <p>
       * {@link NSOpenGLImpl#contextMadeCurrent(boolean) contextMadeCurrent(true)} cures the potential unlocked CGLContext
       * by issuing a whole {@link GLContext#release()} and {@link GLContext#makeCurrent()} cycle
       * while waiting for this command to be completed in-between.
       * <br>
       * This {@link GLContext} cycle also ensures an unlocked {@link NativeSurface#getLock()}
       * in-between, allowing potentially blocked other tasks on the main-thread to complete
       * and hence this queued command to execute.
       * </p>
       * <p>
       * Notable test provoking critical multithreading issues is
       * {@link com.jogamp.opengl.test.junit.jogl.demos.es2.newt.TestGearsES2NewtCanvasSWT}.
       * <br>
       * Notable test exposing issues with an unlocked CGLContext is
       * {@link com.jogamp.opengl.test.junit.jogl.glsl.TestGLSLShaderState02NEWT}.
       * </p>
       */
      class SetNSViewCmd implements Runnable {
          /** 500ms ~30 frames @ 60hz timeout */
          static final long Timeout = 500;

          final long ctx;
          final NSViewDescriptor nsViewDescr;
          boolean done;

          SetNSViewCmd(final long ctx, final NSViewDescriptor nsViewDescr) {
              this.ctx = ctx;
              this.nsViewDescr = nsViewDescr;
              this.done = false;
          }

          @Override
          public final String toString() {
              return "SetNSViewCmd[ctx "+toHexString(ctx)+", drawable "+toHexString(drawable.hashCode())+", "+nsViewDescr+"]";
          }

          @Override
          public void run() {
              synchronized(this) {
                  if( !done ) {
                      try {
                          CGL.setContextView(ctx, nsViewDescr.nsViewHandle);
                          if (DEBUG1398) {
                              System.err.println("MacOSXCGLContext.SetNSViewCmd Bug1398: OK, drawable "+toHexString(drawable.hashCode())+", "+nsViewDescr+" - "+getThreadName());
                          }
                      } catch (final Throwable t) {
                          System.err.println("Caught exception on thread "+getThreadName());
                          t.printStackTrace();
                      }
                      done = true;
                      this.notifyAll();
                  }
              }
          }
      }
  }

  class CGLImpl implements GLBackendImpl {
      @Override
      public boolean isNSContext() { return false; }

      @Override
      public boolean isUsingCALayer() { return false; }

      @Override
      public long create(final long share, final int ctp, final int major, final int minor) {
          long ctx = 0;
          final MacOSXCGLGraphicsConfiguration config = (MacOSXCGLGraphicsConfiguration) drawable.getNativeSurface().getGraphicsConfiguration();
          final GLCapabilitiesImmutable chosenCaps = (GLCapabilitiesImmutable)config.getChosenCapabilities();
          final long pixelFormat = MacOSXCGLGraphicsConfiguration.GLCapabilities2CGLPixelFormat(config.getScreen().getDevice(),
                                                                      chosenCaps, ctp, major, minor);
          if (pixelFormat == 0) {
              throw new GLException("Unable to allocate pixel format with requested GLCapabilities");
          }
          try {
              // Create new context
              final PointerBuffer ctxPB = PointerBuffer.allocateDirect(1);
              if (DEBUG) {
                  System.err.println("Share context for CGL-based pbuffer context is " + toHexString(share));
              }
              int res = CGL.CGLCreateContext(pixelFormat, share, ctxPB);
              if (res != CGL.kCGLNoError) {
                  throw new GLException("Error code " + res + " while creating context");
              }
              ctx = ctxPB.get(0);

              if (0 != ctx) {
                  GLCapabilities fixedCaps = MacOSXCGLGraphicsConfiguration.CGLPixelFormat2GLCapabilities(pixelFormat);
                  fixedCaps = GLGraphicsConfigurationUtil.fixOpaqueGLCapabilities(fixedCaps, chosenCaps.isBackgroundOpaque());
                  { // determine on-/offscreen caps, since pformat is ambiguous
                      fixedCaps.setFBO( false );         // n/a for CGLImpl
                      fixedCaps.setPBuffer( fixedCaps.isPBuffer() && !chosenCaps.isOnscreen() );
                      fixedCaps.setBitmap( false );      // n/a in our OSX impl.
                      fixedCaps.setOnscreen( !fixedCaps.isPBuffer() );
                  }
                  config.setChosenCapabilities(fixedCaps);
                  if(DEBUG) {
                      System.err.println("CGL create fixedCaps: "+fixedCaps);
                  }
                  if(fixedCaps.isPBuffer()) {
                      // Must now associate the pbuffer with our newly-created context
                      res = CGL.CGLSetPBuffer(ctx, drawable.getHandle(), 0, 0, 0);
                      if (res != CGL.kCGLNoError) {
                          throw new GLException("Error code " + res + " while attaching context to pbuffer");
                      }
                  }
              }
          } finally {
              CGL.CGLDestroyPixelFormat(pixelFormat);
          }
          return ctx;
      }

      @Override
      public boolean destroy(final long ctx) {
          return CGL.CGLDestroyContext(ctx) == CGL.kCGLNoError;
      }

      @Override
      public void associateDrawable(final boolean bound) {
      }

      @Override
      public boolean copyImpl(final long src, final int mask) {
          CGL.CGLCopyContext(src, contextHandle, mask);
          return true;
      }

      @Override
      public boolean makeCurrent(final long ctx) {
          int err = CGL.CGLLockContext(ctx);
          if(CGL.kCGLNoError == err) {
              err = CGL.CGLSetCurrentContext(ctx);
              if(CGL.kCGLNoError == err) {
                  return true;
              } else if(DEBUG) {
                  System.err.println("CGL: Could not make context current: err 0x"+Integer.toHexString(err)+": "+this);
              }
          } else if(DEBUG) {
              System.err.println("CGL: Could not lock context: err 0x"+Integer.toHexString(err)+": "+this);
          }
          return false;
      }

      @Override
      public void contextMadeCurrent(final boolean current) {
      }

      @Override
      public boolean release(final long ctx) {
          try {
              if( hasRendererQuirk(GLRendererQuirks.GLFlushBeforeRelease) && null != MacOSXCGLContext.this.getGLProcAddressTable() ) {
                  gl.glFlush();
              }
          } catch (final GLException gle) {
              if(DEBUG) {
                  System.err.println("MacOSXCGLContext.CGLImpl.release: INFO: glFlush() caught exception:");
                  gle.printStackTrace();
              }
          }
          final int err = CGL.CGLSetCurrentContext(0);
          if(DEBUG && CGL.kCGLNoError != err) {
              System.err.println("CGL: Could not release current context: err 0x"+Integer.toHexString(err)+": "+this);
          }
          final int err2 = CGL.CGLUnlockContext(ctx);
          if(DEBUG && CGL.kCGLNoError != err2) {
              System.err.println("CGL: Could not unlock context: err 0x"+Integer.toHexString(err2)+": "+this);
          }
          return CGL.kCGLNoError == err && CGL.kCGLNoError == err2;
      }

      @Override
      public boolean detachPBuffer() {
          /* Doesn't work, i.e. not taking NULL
          final int res = CGL.CGLSetPBuffer(contextHandle, 0, 0, 0, 0);
          if (res != CGL.kCGLNoError) {
              throw new GLException("Error code " + res + " while detaching context from pbuffer");
          } */
          return true;
      }

      @Override
      public boolean setSwapInterval(final int interval) {
          final IntBuffer lval = Buffers.newDirectIntBuffer(1);
          lval.put(0, interval);
          CGL.CGLSetParameter(contextHandle, CGL.kCGLCPSwapInterval, lval);
          return true;
      }
      @Override
      public boolean swapBuffers() {
          return CGL.kCGLNoError == CGL.CGLFlushDrawable(contextHandle);
      }
  }
}
