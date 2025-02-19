/* !---- DO NOT EDIT: This file autogenerated by com/jogamp/gluegen/opengl/GLEmitter.java on Fri Aug 18 14:58:37 CEST 2023 ----! */
/* !---- Java-Unit: [pkg jogamp.opengl.es3, cls GLES3ProcAddressTable], ../build/jogl/gensrc/classes/jogamp/opengl/es3/GLES3ProcAddressTable.java ----! */

package jogamp.opengl.es3;

import java.util.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.*;
import jogamp.opengl.*;
import com.jogamp.opengl.GLBase;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GL4ES3;
import com.jogamp.opengl.GLES3;
import com.jogamp.opengl.GLArrayData;
import com.jogamp.opengl.GLUniformData;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.GLBuffers;
import java.io.PrintStream;
import com.jogamp.gluegen.runtime.ProcAddressTable;
import com.jogamp.common.util.SecurityUtil;

/**
 * This table is a cache of pointers to the dynamically-linkable C library.
 * @see ProcAddressTable
 */
public final class GLES3ProcAddressTable extends ProcAddressTable {

  /* pp */ long _addressof_glDebugMessageCallbackKHR;

  public GLES3ProcAddressTable(){ super(); }

  public GLES3ProcAddressTable(com.jogamp.gluegen.runtime.FunctionAddressResolver resolver){ super(resolver); }

  /* pp */ long _addressof_glActiveTexture;
  /* pp */ long _addressof_glAttachShader;
  /* pp */ long _addressof_glBindAttribLocation;
  /* pp */ long _addressof_glBindBuffer;
  /* pp */ long _addressof_glBindFramebuffer;
  /* pp */ long _addressof_glBindRenderbuffer;
  /* pp */ long _addressof_glBindTexture;
  /* pp */ long _addressof_glBlendColor;
  /* pp */ long _addressof_glBlendEquation;
  /* pp */ long _addressof_glBlendEquationSeparate;
  /* pp */ long _addressof_glBlendFunc;
  /* pp */ long _addressof_glBlendFuncSeparate;
  /* pp */ long _addressof_glBufferData;
  /* pp */ long _addressof_glBufferSubData;
  /* pp */ long _addressof_glCheckFramebufferStatus;
  /* pp */ long _addressof_glClear;
  /* pp */ long _addressof_glClearColor;
  /* pp */ long _addressof_glClearDepthf;
  /* pp */ long _addressof_glClearStencil;
  /* pp */ long _addressof_glColorMask;
  /* pp */ long _addressof_glCompileShader;
  /* pp */ long _addressof_glCompressedTexImage2D;
  /* pp */ long _addressof_glCompressedTexSubImage2D;
  /* pp */ long _addressof_glCopyTexImage2D;
  /* pp */ long _addressof_glCopyTexSubImage2D;
  /* pp */ long _addressof_glCreateProgram;
  /* pp */ long _addressof_glCreateShader;
  /* pp */ long _addressof_glCullFace;
  /* pp */ long _addressof_glDeleteBuffers;
  /* pp */ long _addressof_glDeleteFramebuffers;
  /* pp */ long _addressof_glDeleteProgram;
  /* pp */ long _addressof_glDeleteRenderbuffers;
  /* pp */ long _addressof_glDeleteShader;
  /* pp */ long _addressof_glDeleteTextures;
  /* pp */ long _addressof_glDepthFunc;
  /* pp */ long _addressof_glDepthMask;
  /* pp */ long _addressof_glDepthRangef;
  /* pp */ long _addressof_glDetachShader;
  /* pp */ long _addressof_glDisable;
  /* pp */ long _addressof_glDisableVertexAttribArray;
  /* pp */ long _addressof_glDrawArrays;
  /* pp */ long _addressof_glDrawElements;
  /* pp */ long _addressof_glEnable;
  /* pp */ long _addressof_glEnableVertexAttribArray;
  /* pp */ long _addressof_glFinish;
  /* pp */ long _addressof_glFlush;
  /* pp */ long _addressof_glFramebufferRenderbuffer;
  /* pp */ long _addressof_glFramebufferTexture2D;
  /* pp */ long _addressof_glFrontFace;
  /* pp */ long _addressof_glGenBuffers;
  /* pp */ long _addressof_glGenerateMipmap;
  /* pp */ long _addressof_glGenFramebuffers;
  /* pp */ long _addressof_glGenRenderbuffers;
  /* pp */ long _addressof_glGenTextures;
  /* pp */ long _addressof_glGetActiveAttrib;
  /* pp */ long _addressof_glGetActiveUniform;
  /* pp */ long _addressof_glGetAttachedShaders;
  /* pp */ long _addressof_glGetAttribLocation;
  /* pp */ long _addressof_glGetBooleanv;
  /* pp */ long _addressof_glGetBufferParameteriv;
  /* pp */ long _addressof_glGetError;
  /* pp */ long _addressof_glGetFloatv;
  /* pp */ long _addressof_glGetFramebufferAttachmentParameteriv;
  /* pp */ long _addressof_glGetIntegerv;
  /* pp */ long _addressof_glGetProgramiv;
  /* pp */ long _addressof_glGetProgramInfoLog;
  /* pp */ long _addressof_glGetRenderbufferParameteriv;
  /* pp */ long _addressof_glGetShaderiv;
  /* pp */ long _addressof_glGetShaderInfoLog;
  /* pp */ long _addressof_glGetShaderPrecisionFormat;
  /* pp */ long _addressof_glGetShaderSource;
  /* pp */ long _addressof_glGetString;
  /* pp */ long _addressof_glGetTexParameterfv;
  /* pp */ long _addressof_glGetTexParameteriv;
  /* pp */ long _addressof_glGetUniformfv;
  /* pp */ long _addressof_glGetUniformiv;
  /* pp */ long _addressof_glGetUniformLocation;
  /* pp */ long _addressof_glGetVertexAttribfv;
  /* pp */ long _addressof_glGetVertexAttribiv;
  /* pp */ long _addressof_glHint;
  /* pp */ long _addressof_glIsBuffer;
  /* pp */ long _addressof_glIsEnabled;
  /* pp */ long _addressof_glIsFramebuffer;
  /* pp */ long _addressof_glIsProgram;
  /* pp */ long _addressof_glIsRenderbuffer;
  /* pp */ long _addressof_glIsShader;
  /* pp */ long _addressof_glIsTexture;
  /* pp */ long _addressof_glLineWidth;
  /* pp */ long _addressof_glLinkProgram;
  /* pp */ long _addressof_glPixelStorei;
  /* pp */ long _addressof_glPolygonOffset;
  /* pp */ long _addressof_glReadPixels;
  /* pp */ long _addressof_glReleaseShaderCompiler;
  /* pp */ long _addressof_glRenderbufferStorage;
  /* pp */ long _addressof_glSampleCoverage;
  /* pp */ long _addressof_glScissor;
  /* pp */ long _addressof_glShaderBinary;
  /* pp */ long _addressof_glShaderSource;
  /* pp */ long _addressof_glStencilFunc;
  /* pp */ long _addressof_glStencilFuncSeparate;
  /* pp */ long _addressof_glStencilMask;
  /* pp */ long _addressof_glStencilMaskSeparate;
  /* pp */ long _addressof_glStencilOp;
  /* pp */ long _addressof_glStencilOpSeparate;
  /* pp */ long _addressof_glTexImage2D;
  /* pp */ long _addressof_glTexParameterf;
  /* pp */ long _addressof_glTexParameterfv;
  /* pp */ long _addressof_glTexParameteri;
  /* pp */ long _addressof_glTexParameteriv;
  /* pp */ long _addressof_glTexSubImage2D;
  /* pp */ long _addressof_glUniform1f;
  /* pp */ long _addressof_glUniform1fv;
  /* pp */ long _addressof_glUniform1i;
  /* pp */ long _addressof_glUniform1iv;
  /* pp */ long _addressof_glUniform2f;
  /* pp */ long _addressof_glUniform2fv;
  /* pp */ long _addressof_glUniform2i;
  /* pp */ long _addressof_glUniform2iv;
  /* pp */ long _addressof_glUniform3f;
  /* pp */ long _addressof_glUniform3fv;
  /* pp */ long _addressof_glUniform3i;
  /* pp */ long _addressof_glUniform3iv;
  /* pp */ long _addressof_glUniform4f;
  /* pp */ long _addressof_glUniform4fv;
  /* pp */ long _addressof_glUniform4i;
  /* pp */ long _addressof_glUniform4iv;
  /* pp */ long _addressof_glUniformMatrix2fv;
  /* pp */ long _addressof_glUniformMatrix3fv;
  /* pp */ long _addressof_glUniformMatrix4fv;
  /* pp */ long _addressof_glUseProgram;
  /* pp */ long _addressof_glValidateProgram;
  /* pp */ long _addressof_glVertexAttrib1f;
  /* pp */ long _addressof_glVertexAttrib1fv;
  /* pp */ long _addressof_glVertexAttrib2f;
  /* pp */ long _addressof_glVertexAttrib2fv;
  /* pp */ long _addressof_glVertexAttrib3f;
  /* pp */ long _addressof_glVertexAttrib3fv;
  /* pp */ long _addressof_glVertexAttrib4f;
  /* pp */ long _addressof_glVertexAttrib4fv;
  /* pp */ long _addressof_glVertexAttribPointer;
  /* pp */ long _addressof_glViewport;
  /* pp */ long _addressof_glReadBuffer;
  /* pp */ long _addressof_glDrawRangeElements;
  /* pp */ long _addressof_glTexImage3D;
  /* pp */ long _addressof_glTexSubImage3D;
  /* pp */ long _addressof_glCopyTexSubImage3D;
  /* pp */ long _addressof_glCompressedTexImage3D;
  /* pp */ long _addressof_glCompressedTexSubImage3D;
  /* pp */ long _addressof_glGenQueries;
  /* pp */ long _addressof_glDeleteQueries;
  /* pp */ long _addressof_glIsQuery;
  /* pp */ long _addressof_glBeginQuery;
  /* pp */ long _addressof_glEndQuery;
  /* pp */ long _addressof_glGetQueryiv;
  /* pp */ long _addressof_glGetQueryObjectuiv;
  /* pp */ long _addressof_glUnmapBuffer;
  /* pp */ long _addressof_glDrawBuffers;
  /* pp */ long _addressof_glUniformMatrix2x3fv;
  /* pp */ long _addressof_glUniformMatrix3x2fv;
  /* pp */ long _addressof_glUniformMatrix2x4fv;
  /* pp */ long _addressof_glUniformMatrix4x2fv;
  /* pp */ long _addressof_glUniformMatrix3x4fv;
  /* pp */ long _addressof_glUniformMatrix4x3fv;
  /* pp */ long _addressof_glBlitFramebuffer;
  /* pp */ long _addressof_glRenderbufferStorageMultisample;
  /* pp */ long _addressof_glFramebufferTextureLayer;
  /* pp */ long _addressof_glMapBufferRange;
  /* pp */ long _addressof_glFlushMappedBufferRange;
  /* pp */ long _addressof_glBindVertexArray;
  /* pp */ long _addressof_glDeleteVertexArrays;
  /* pp */ long _addressof_glGenVertexArrays;
  /* pp */ long _addressof_glIsVertexArray;
  /* pp */ long _addressof_glGetIntegeri_v;
  /* pp */ long _addressof_glBeginTransformFeedback;
  /* pp */ long _addressof_glEndTransformFeedback;
  /* pp */ long _addressof_glBindBufferRange;
  /* pp */ long _addressof_glBindBufferBase;
  /* pp */ long _addressof_glTransformFeedbackVaryings;
  /* pp */ long _addressof_glGetTransformFeedbackVarying;
  /* pp */ long _addressof_glVertexAttribIPointer;
  /* pp */ long _addressof_glGetVertexAttribIiv;
  /* pp */ long _addressof_glGetVertexAttribIuiv;
  /* pp */ long _addressof_glVertexAttribI4i;
  /* pp */ long _addressof_glVertexAttribI4ui;
  /* pp */ long _addressof_glVertexAttribI4iv;
  /* pp */ long _addressof_glVertexAttribI4uiv;
  /* pp */ long _addressof_glGetUniformuiv;
  /* pp */ long _addressof_glGetFragDataLocation;
  /* pp */ long _addressof_glUniform1ui;
  /* pp */ long _addressof_glUniform2ui;
  /* pp */ long _addressof_glUniform3ui;
  /* pp */ long _addressof_glUniform4ui;
  /* pp */ long _addressof_glUniform1uiv;
  /* pp */ long _addressof_glUniform2uiv;
  /* pp */ long _addressof_glUniform3uiv;
  /* pp */ long _addressof_glUniform4uiv;
  /* pp */ long _addressof_glClearBufferiv;
  /* pp */ long _addressof_glClearBufferuiv;
  /* pp */ long _addressof_glClearBufferfv;
  /* pp */ long _addressof_glClearBufferfi;
  /* pp */ long _addressof_glGetStringi;
  /* pp */ long _addressof_glCopyBufferSubData;
  /* pp */ long _addressof_glGetUniformIndices;
  /* pp */ long _addressof_glGetActiveUniformsiv;
  /* pp */ long _addressof_glGetUniformBlockIndex;
  /* pp */ long _addressof_glGetActiveUniformBlockiv;
  /* pp */ long _addressof_glGetActiveUniformBlockName;
  /* pp */ long _addressof_glUniformBlockBinding;
  /* pp */ long _addressof_glDrawArraysInstanced;
  /* pp */ long _addressof_glDrawElementsInstanced;
  /* pp */ long _addressof_glFenceSync;
  /* pp */ long _addressof_glIsSync;
  /* pp */ long _addressof_glDeleteSync;
  /* pp */ long _addressof_glClientWaitSync;
  /* pp */ long _addressof_glWaitSync;
  /* pp */ long _addressof_glGetInteger64v;
  /* pp */ long _addressof_glGetSynciv;
  /* pp */ long _addressof_glGetInteger64i_v;
  /* pp */ long _addressof_glGetBufferParameteri64v;
  /* pp */ long _addressof_glGenSamplers;
  /* pp */ long _addressof_glDeleteSamplers;
  /* pp */ long _addressof_glIsSampler;
  /* pp */ long _addressof_glBindSampler;
  /* pp */ long _addressof_glSamplerParameteri;
  /* pp */ long _addressof_glSamplerParameteriv;
  /* pp */ long _addressof_glSamplerParameterf;
  /* pp */ long _addressof_glSamplerParameterfv;
  /* pp */ long _addressof_glGetSamplerParameteriv;
  /* pp */ long _addressof_glGetSamplerParameterfv;
  /* pp */ long _addressof_glVertexAttribDivisor;
  /* pp */ long _addressof_glBindTransformFeedback;
  /* pp */ long _addressof_glDeleteTransformFeedbacks;
  /* pp */ long _addressof_glGenTransformFeedbacks;
  /* pp */ long _addressof_glIsTransformFeedback;
  /* pp */ long _addressof_glPauseTransformFeedback;
  /* pp */ long _addressof_glResumeTransformFeedback;
  /* pp */ long _addressof_glGetProgramBinary;
  /* pp */ long _addressof_glProgramBinary;
  /* pp */ long _addressof_glProgramParameteri;
  /* pp */ long _addressof_glInvalidateFramebuffer;
  /* pp */ long _addressof_glInvalidateSubFramebuffer;
  /* pp */ long _addressof_glTexStorage2D;
  /* pp */ long _addressof_glTexStorage3D;
  /* pp */ long _addressof_glGetInternalformativ;
  /* pp */ long _addressof_glDispatchCompute;
  /* pp */ long _addressof_glDispatchComputeIndirect;
  /* pp */ long _addressof_glDrawArraysIndirect;
  /* pp */ long _addressof_glDrawElementsIndirect;
  /* pp */ long _addressof_glFramebufferParameteri;
  /* pp */ long _addressof_glGetFramebufferParameteriv;
  /* pp */ long _addressof_glGetProgramInterfaceiv;
  /* pp */ long _addressof_glGetProgramResourceIndex;
  /* pp */ long _addressof_glGetProgramResourceName;
  /* pp */ long _addressof_glGetProgramResourceiv;
  /* pp */ long _addressof_glGetProgramResourceLocation;
  /* pp */ long _addressof_glUseProgramStages;
  /* pp */ long _addressof_glActiveShaderProgram;
  /* pp */ long _addressof_glCreateShaderProgramv;
  /* pp */ long _addressof_glBindProgramPipeline;
  /* pp */ long _addressof_glDeleteProgramPipelines;
  /* pp */ long _addressof_glGenProgramPipelines;
  /* pp */ long _addressof_glIsProgramPipeline;
  /* pp */ long _addressof_glGetProgramPipelineiv;
  /* pp */ long _addressof_glProgramUniform1i;
  /* pp */ long _addressof_glProgramUniform2i;
  /* pp */ long _addressof_glProgramUniform3i;
  /* pp */ long _addressof_glProgramUniform4i;
  /* pp */ long _addressof_glProgramUniform1ui;
  /* pp */ long _addressof_glProgramUniform2ui;
  /* pp */ long _addressof_glProgramUniform3ui;
  /* pp */ long _addressof_glProgramUniform4ui;
  /* pp */ long _addressof_glProgramUniform1f;
  /* pp */ long _addressof_glProgramUniform2f;
  /* pp */ long _addressof_glProgramUniform3f;
  /* pp */ long _addressof_glProgramUniform4f;
  /* pp */ long _addressof_glProgramUniform1iv;
  /* pp */ long _addressof_glProgramUniform2iv;
  /* pp */ long _addressof_glProgramUniform3iv;
  /* pp */ long _addressof_glProgramUniform4iv;
  /* pp */ long _addressof_glProgramUniform1uiv;
  /* pp */ long _addressof_glProgramUniform2uiv;
  /* pp */ long _addressof_glProgramUniform3uiv;
  /* pp */ long _addressof_glProgramUniform4uiv;
  /* pp */ long _addressof_glProgramUniform1fv;
  /* pp */ long _addressof_glProgramUniform2fv;
  /* pp */ long _addressof_glProgramUniform3fv;
  /* pp */ long _addressof_glProgramUniform4fv;
  /* pp */ long _addressof_glProgramUniformMatrix2fv;
  /* pp */ long _addressof_glProgramUniformMatrix3fv;
  /* pp */ long _addressof_glProgramUniformMatrix4fv;
  /* pp */ long _addressof_glProgramUniformMatrix2x3fv;
  /* pp */ long _addressof_glProgramUniformMatrix3x2fv;
  /* pp */ long _addressof_glProgramUniformMatrix2x4fv;
  /* pp */ long _addressof_glProgramUniformMatrix4x2fv;
  /* pp */ long _addressof_glProgramUniformMatrix3x4fv;
  /* pp */ long _addressof_glProgramUniformMatrix4x3fv;
  /* pp */ long _addressof_glValidateProgramPipeline;
  /* pp */ long _addressof_glGetProgramPipelineInfoLog;
  /* pp */ long _addressof_glBindImageTexture;
  /* pp */ long _addressof_glGetBooleani_v;
  /* pp */ long _addressof_glMemoryBarrier;
  /* pp */ long _addressof_glMemoryBarrierByRegion;
  /* pp */ long _addressof_glTexStorage2DMultisample;
  /* pp */ long _addressof_glGetMultisamplefv;
  /* pp */ long _addressof_glSampleMaski;
  /* pp */ long _addressof_glGetTexLevelParameteriv;
  /* pp */ long _addressof_glGetTexLevelParameterfv;
  /* pp */ long _addressof_glBindVertexBuffer;
  /* pp */ long _addressof_glVertexAttribFormat;
  /* pp */ long _addressof_glVertexAttribIFormat;
  /* pp */ long _addressof_glVertexAttribBinding;
  /* pp */ long _addressof_glVertexBindingDivisor;
  /* pp */ long _addressof_glBlendBarrier;
  /* pp */ long _addressof_glCopyImageSubData;
  /* pp */ long _addressof_glDebugMessageControl;
  /* pp */ long _addressof_glDebugMessageInsert;
  /* pp */ long _addressof_glGetDebugMessageLog;
  /* pp */ long _addressof_glPushDebugGroup;
  /* pp */ long _addressof_glPopDebugGroup;
  /* pp */ long _addressof_glObjectLabel;
  /* pp */ long _addressof_glGetObjectLabel;
  /* pp */ long _addressof_glObjectPtrLabel;
  /* pp */ long _addressof_glGetObjectPtrLabel;
  /* pp */ long _addressof_glEnablei;
  /* pp */ long _addressof_glDisablei;
  /* pp */ long _addressof_glBlendEquationi;
  /* pp */ long _addressof_glBlendEquationSeparatei;
  /* pp */ long _addressof_glBlendFunci;
  /* pp */ long _addressof_glBlendFuncSeparatei;
  /* pp */ long _addressof_glColorMaski;
  /* pp */ long _addressof_glIsEnabledi;
  /* pp */ long _addressof_glDrawElementsBaseVertex;
  /* pp */ long _addressof_glDrawRangeElementsBaseVertex;
  /* pp */ long _addressof_glDrawElementsInstancedBaseVertex;
  /* pp */ long _addressof_glFramebufferTexture;
  /* pp */ long _addressof_glPrimitiveBoundingBox;
  /* pp */ long _addressof_glGetGraphicsResetStatus;
  /* pp */ long _addressof_glReadnPixels;
  /* pp */ long _addressof_glGetnUniformfv;
  /* pp */ long _addressof_glGetnUniformiv;
  /* pp */ long _addressof_glGetnUniformuiv;
  /* pp */ long _addressof_glMinSampleShading;
  /* pp */ long _addressof_glPatchParameteri;
  /* pp */ long _addressof_glTexParameterIiv;
  /* pp */ long _addressof_glTexParameterIuiv;
  /* pp */ long _addressof_glGetTexParameterIiv;
  /* pp */ long _addressof_glGetTexParameterIuiv;
  /* pp */ long _addressof_glSamplerParameterIiv;
  /* pp */ long _addressof_glSamplerParameterIuiv;
  /* pp */ long _addressof_glGetSamplerParameterIiv;
  /* pp */ long _addressof_glGetSamplerParameterIuiv;
  /* pp */ long _addressof_glTexBuffer;
  /* pp */ long _addressof_glTexBufferRange;
  /* pp */ long _addressof_glTexStorage3DMultisample;
  /* pp */ long _addressof_glTexStorage1D;
  /* pp */ long _addressof_glTextureStorage1DEXT;
  /* pp */ long _addressof_glTextureStorage2DEXT;
  /* pp */ long _addressof_glTextureStorage3DEXT;
  /* pp */ long _addressof_glTexImage2DMultisample;
  /* pp */ long _addressof_glTexImage3DMultisample;
  /* pp */ long _addressof_glEGLImageTargetTexture2DOES;
  /* pp */ long _addressof_glEGLImageTargetRenderbufferStorageOES;
  /* pp */ long _addressof_glEnableiOES;
  /* pp */ long _addressof_glDisableiOES;
  /* pp */ long _addressof_glBlendEquationiOES;
  /* pp */ long _addressof_glBlendEquationSeparateiOES;
  /* pp */ long _addressof_glBlendFunciOES;
  /* pp */ long _addressof_glBlendFuncSeparateiOES;
  /* pp */ long _addressof_glColorMaskiOES;
  /* pp */ long _addressof_glIsEnablediOES;
  /* pp */ long _addressof_glDrawElementsBaseVertexOES;
  /* pp */ long _addressof_glDrawRangeElementsBaseVertexOES;
  /* pp */ long _addressof_glDrawElementsInstancedBaseVertexOES;
  /* pp */ long _addressof_glMultiDrawElementsBaseVertexOES;
  /* pp */ long _addressof_glFramebufferTextureOES;
  /* pp */ long _addressof_glMapBuffer;
  /* pp */ long _addressof_glPrimitiveBoundingBoxOES;
  /* pp */ long _addressof_glMinSampleShadingOES;
  /* pp */ long _addressof_glPatchParameteriOES;
  /* pp */ long _addressof_glFramebufferTexture3D;
  /* pp */ long _addressof_glTexBufferOES;
  /* pp */ long _addressof_glTexBufferRangeOES;
  /* pp */ long _addressof_glTexStorage3DMultisampleOES;
  /* pp */ long _addressof_glTextureView;
  /* pp */ long _addressof_glBindVertexArrayOES;
  /* pp */ long _addressof_glDeleteVertexArraysOES;
  /* pp */ long _addressof_glGenVertexArraysOES;
  /* pp */ long _addressof_glIsVertexArrayOES;
  /* pp */ long _addressof_glBlitFramebufferANGLE;
  /* pp */ long _addressof_glDrawArraysInstancedANGLE;
  /* pp */ long _addressof_glDrawElementsInstancedANGLE;
  /* pp */ long _addressof_glVertexAttribDivisorANGLE;
  /* pp */ long _addressof_glGetTranslatedShaderSourceANGLE;
  /* pp */ long _addressof_glCopyTextureLevelsAPPLE;
  /* pp */ long _addressof_glResolveMultisampleFramebuffer;
  /* pp */ long _addressof_glDrawArraysInstancedBaseInstance;
  /* pp */ long _addressof_glDrawElementsInstancedBaseInstance;
  /* pp */ long _addressof_glDrawElementsInstancedBaseVertexBaseInstance;
  /* pp */ long _addressof_glBindFragDataLocationIndexedEXT;
  /* pp */ long _addressof_glBindFragDataLocationEXT;
  /* pp */ long _addressof_glGetProgramResourceLocationIndexEXT;
  /* pp */ long _addressof_glGetFragDataIndexEXT;
  /* pp */ long _addressof_glBufferStorageEXT;
  /* pp */ long _addressof_glDiscardFramebufferEXT;
  /* pp */ long _addressof_glQueryCounter;
  /* pp */ long _addressof_glGetQueryObjectiv;
  /* pp */ long _addressof_glGetQueryObjecti64v;
  /* pp */ long _addressof_glGetQueryObjectui64v;
  /* pp */ long _addressof_glEnableiEXT;
  /* pp */ long _addressof_glDisableiEXT;
  /* pp */ long _addressof_glBlendEquationiEXT;
  /* pp */ long _addressof_glBlendEquationSeparateiEXT;
  /* pp */ long _addressof_glBlendFunciEXT;
  /* pp */ long _addressof_glBlendFuncSeparateiEXT;
  /* pp */ long _addressof_glColorMaskiEXT;
  /* pp */ long _addressof_glIsEnablediEXT;
  /* pp */ long _addressof_glDrawElementsBaseVertexEXT;
  /* pp */ long _addressof_glDrawRangeElementsBaseVertexEXT;
  /* pp */ long _addressof_glDrawElementsInstancedBaseVertexEXT;
  /* pp */ long _addressof_glMultiDrawElementsBaseVertexEXT;
  /* pp */ long _addressof_glFramebufferTextureEXT;
  /* pp */ long _addressof_glMultiDrawArraysIndirectEXT;
  /* pp */ long _addressof_glMultiDrawElementsIndirectEXT;
  /* pp */ long _addressof_glRenderbufferStorageMultisampleEXT;
  /* pp */ long _addressof_glFramebufferTexture2DMultisampleEXT;
  /* pp */ long _addressof_glReadBufferIndexedEXT;
  /* pp */ long _addressof_glDrawBuffersIndexedEXT;
  /* pp */ long _addressof_glGetIntegeri_vEXT;
  /* pp */ long _addressof_glPrimitiveBoundingBoxEXT;
  /* pp */ long _addressof_glRasterSamplesEXT;
  /* pp */ long _addressof_glTexPageCommitmentEXT;
  /* pp */ long _addressof_glPatchParameteriEXT;
  /* pp */ long _addressof_glTexBufferEXT;
  /* pp */ long _addressof_glTexBufferRangeEXT;
  /* pp */ long _addressof_glRenderbufferStorageMultisampleIMG;
  /* pp */ long _addressof_glFramebufferTexture2DMultisampleIMG;
  /* pp */ long _addressof_glApplyFramebufferAttachmentCMAAINTEL;
  /* pp */ long _addressof_glBeginConditionalRender;
  /* pp */ long _addressof_glEndConditionalRender;
  /* pp */ long _addressof_glSubpixelPrecisionBiasNV;
  /* pp */ long _addressof_glCopyBufferSubDataNV;
  /* pp */ long _addressof_glCoverageMaskNV;
  /* pp */ long _addressof_glCoverageOperationNV;
  /* pp */ long _addressof_glDrawArraysInstancedNV;
  /* pp */ long _addressof_glDrawElementsInstancedNV;
  /* pp */ long _addressof_glFragmentCoverageColorNV;
  /* pp */ long _addressof_glBlitFramebufferNV;
  /* pp */ long _addressof_glCoverageModulationTableNV;
  /* pp */ long _addressof_glGetCoverageModulationTableNV;
  /* pp */ long _addressof_glCoverageModulationNV;
  /* pp */ long _addressof_glRenderbufferStorageMultisampleNV;
  /* pp */ long _addressof_glVertexAttribDivisorNV;
  /* pp */ long _addressof_glUniformMatrix2x3fvNV;
  /* pp */ long _addressof_glUniformMatrix3x2fvNV;
  /* pp */ long _addressof_glUniformMatrix2x4fvNV;
  /* pp */ long _addressof_glUniformMatrix4x2fvNV;
  /* pp */ long _addressof_glUniformMatrix3x4fvNV;
  /* pp */ long _addressof_glUniformMatrix4x3fvNV;
  /* pp */ long _addressof_glPolygonModeNV;
  /* pp */ long _addressof_glReadBufferNV;
  /* pp */ long _addressof_glFramebufferSampleLocationsfvNV;
  /* pp */ long _addressof_glNamedFramebufferSampleLocationsfvNV;
  /* pp */ long _addressof_glResolveDepthValuesNV;
  /* pp */ long _addressof_glViewportArrayvNV;
  /* pp */ long _addressof_glViewportIndexedfNV;
  /* pp */ long _addressof_glViewportIndexedfvNV;
  /* pp */ long _addressof_glScissorArrayvNV;
  /* pp */ long _addressof_glScissorIndexedNV;
  /* pp */ long _addressof_glScissorIndexedvNV;
  /* pp */ long _addressof_glDepthRangeArrayfvNV;
  /* pp */ long _addressof_glDepthRangeIndexedfNV;
  /* pp */ long _addressof_glGetFloati_vNV;
  /* pp */ long _addressof_glEnableiNV;
  /* pp */ long _addressof_glDisableiNV;
  /* pp */ long _addressof_glIsEnablediNV;
  /* pp */ long _addressof_glFramebufferTextureMultiviewOVR;
  /* pp */ long _addressof_glAlphaFuncQCOM;
  /* pp */ long _addressof_glGetDriverControlsQCOM;
  /* pp */ long _addressof_glGetDriverControlStringQCOM;
  /* pp */ long _addressof_glEnableDriverControlQCOM;
  /* pp */ long _addressof_glDisableDriverControlQCOM;
  /* pp */ long _addressof_glExtGetTexturesQCOM;
  /* pp */ long _addressof_glExtGetBuffersQCOM;
  /* pp */ long _addressof_glExtGetRenderbuffersQCOM;
  /* pp */ long _addressof_glExtGetFramebuffersQCOM;
  /* pp */ long _addressof_glExtGetTexLevelParameterivQCOM;
  /* pp */ long _addressof_glExtTexObjectStateOverrideiQCOM;
  /* pp */ long _addressof_glExtGetTexSubImageQCOM;
  /* pp */ long _addressof_glExtGetBufferPointervQCOM;
  /* pp */ long _addressof_glExtGetShadersQCOM;
  /* pp */ long _addressof_glExtGetProgramsQCOM;
  /* pp */ long _addressof_glExtIsProgramBinaryQCOM;
  /* pp */ long _addressof_glExtGetProgramBinarySourceQCOM;
  /* pp */ long _addressof_glStartTilingQCOM;
  /* pp */ long _addressof_glEndTilingQCOM;
  @Override
  protected boolean isFunctionAvailableImpl(String functionNameUsr) throws IllegalArgumentException  {
    final String functionNameBase = com.jogamp.gluegen.runtime.opengl.GLNameResolver.normalizeVEN(com.jogamp.gluegen.runtime.opengl.GLNameResolver.normalizeARB(functionNameUsr, true), true);
    final String addressFieldNameBase = "_addressof_" + functionNameBase;
    final int funcNamePermNum = com.jogamp.gluegen.runtime.opengl.GLNameResolver.getFuncNamePermutationNumber(functionNameBase);
    final java.lang.reflect.Field addressField = com.jogamp.common.util.SecurityUtil.doPrivileged(new java.security.PrivilegedAction<java.lang.reflect.Field>() {
        public final java.lang.reflect.Field run() {
            java.lang.reflect.Field addressField = null;
            for(int i = 0; i < funcNamePermNum; i++) {
                final String addressFieldName = com.jogamp.gluegen.runtime.opengl.GLNameResolver.getFuncNamePermutation(addressFieldNameBase, i);
                try {
                    addressField = GLES3ProcAddressTable.class.getDeclaredField( addressFieldName );
                    addressField.setAccessible(true); // we need to read the protected value!
                    return addressField;
                } catch (NoSuchFieldException ex) { }
            }
            return null;
        } } );

    if(null==addressField) {
      // The user is calling a bogus function or one which is not
      // runtime linked
      throw new RuntimeException(
          "WARNING: Address field query failed for \"" + functionNameBase + "\"/\"" + functionNameUsr +
          "\"; it's either statically linked or address field is not a known " +
          "function");
    } 
    try {
      return 0 != addressField.getLong(this);
    } catch (Exception e) {
      throw new RuntimeException(
          "WARNING: Address query failed for \"" + functionNameBase + "\"/\"" + functionNameUsr +
          "\"; it's either statically linked or is not a known " +
          "function", e);
    }
  }
  @Override
  public long getAddressFor(String functionNameUsr) throws SecurityException, IllegalArgumentException {
    SecurityUtil.checkAllLinkPermission();
    final String functionNameBase = com.jogamp.gluegen.runtime.opengl.GLNameResolver.normalizeVEN(com.jogamp.gluegen.runtime.opengl.GLNameResolver.normalizeARB(functionNameUsr, true), true);
    final String addressFieldNameBase = "_addressof_" + functionNameBase;
    final int  funcNamePermNum = com.jogamp.gluegen.runtime.opengl.GLNameResolver.getFuncNamePermutationNumber(functionNameBase);
    final java.lang.reflect.Field addressField = com.jogamp.common.util.SecurityUtil.doPrivileged(new java.security.PrivilegedAction<java.lang.reflect.Field>() {
        public final java.lang.reflect.Field run() {
            java.lang.reflect.Field addressField = null;
            for(int i = 0; i < funcNamePermNum; i++) {
                final String addressFieldName = com.jogamp.gluegen.runtime.opengl.GLNameResolver.getFuncNamePermutation(addressFieldNameBase, i);
                try {
                    addressField = GLES3ProcAddressTable.class.getDeclaredField( addressFieldName );
                    addressField.setAccessible(true); // we need to read the protected value!
                    return addressField;
                } catch (NoSuchFieldException ex) { }
            }
            return null;
        } } );

    if(null==addressField) {
      // The user is calling a bogus function or one which is not
      // runtime linked
      throw new RuntimeException(
          "WARNING: Address field query failed for \"" + functionNameBase + "\"/\"" + functionNameUsr +
          "\"; it's either statically linked or address field is not a known " +
          "function");
    } 
    try {
      return addressField.getLong(this);
    } catch (Exception e) {
      throw new RuntimeException(
          "WARNING: Address query failed for \"" + functionNameBase + "\"/\"" + functionNameUsr +
          "\"; it's either statically linked or is not a known " +
          "function", e);
    }
  }
} // end of class GLES3ProcAddressTable
