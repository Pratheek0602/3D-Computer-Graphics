/*
 * Copyright (c) 2010-2023 JogAmp Community. All rights reserved.
 * Copyright (c) 2003 Sun Microsystems, Inc. All Rights Reserved.
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
package com.jogamp.common.nio;

import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.security.PrivilegedAction;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.common.util.SecurityUtil;
import com.jogamp.common.util.UnsafeUtil;
import com.jogamp.common.util.ValueConv;

import jogamp.common.Debug;
import jogamp.common.os.PlatformPropsImpl;

/**
 * Utility methods allowing easy {@link java.nio.Buffer} manipulations.
 *
 * @author Kenneth Russel
 * @author Sven Gothel
 * @author Michael Bien
 */
public class Buffers {

    static final boolean DEBUG;
    static {
        DEBUG = Debug.debug("Buffers");
    }

    public static final int SIZEOF_BYTE     = 1;
    public static final int SIZEOF_SHORT    = 2;
    public static final int SIZEOF_CHAR     = 2;
    public static final int SIZEOF_INT      = 4;
    public static final int SIZEOF_FLOAT    = 4;
    public static final int SIZEOF_LONG     = 8;
    public static final int SIZEOF_DOUBLE   = 8;

    protected Buffers() {}

    /**
     * Allocates a new direct ByteBuffer with the specified number of
     * elements. The returned buffer will have its byte order set to
     * the host platform's native byte order.
     */
    public static ByteBuffer newDirectByteBuffer(final int numElements) {
        return nativeOrder(ByteBuffer.allocateDirect(numElements));
    }

    public static ByteBuffer newDirectByteBuffer(final byte[] values, final int offset, final int length) {
        return (ByteBuffer)newDirectByteBuffer(length).put(values, offset, length).rewind();
    }

    public static ByteBuffer newDirectByteBuffer(final byte[] values, final int offset) {
        return newDirectByteBuffer(values, offset, values.length-offset);
    }

    public static ByteBuffer newDirectByteBuffer(final byte[] values) {
        return newDirectByteBuffer(values, 0);
    }

    /**
     * Allocates a new direct DoubleBuffer with the specified number of
     * elements. The returned buffer will have its byte order set to
     * the host platform's native byte order.
     */
    public static DoubleBuffer newDirectDoubleBuffer(final int numElements) {
        return newDirectByteBuffer(numElements * SIZEOF_DOUBLE).asDoubleBuffer();
    }

    public static DoubleBuffer newDirectDoubleBuffer(final double[] values, final int offset, final int length) {
        return (DoubleBuffer)newDirectDoubleBuffer(length).put(values, offset, length).rewind();
    }

    public static DoubleBuffer newDirectDoubleBuffer(final double[] values, final int offset) {
        return newDirectDoubleBuffer(values, offset, values.length - offset);
    }

    public static DoubleBuffer newDirectDoubleBuffer(final double[] values) {
        return newDirectDoubleBuffer(values, 0);
    }

    /**
     * Allocates a new direct FloatBuffer with the specified number of
     * elements. The returned buffer will have its byte order set to
     * the host platform's native byte order.
     */
    public static FloatBuffer newDirectFloatBuffer(final int numElements) {
        return newDirectByteBuffer(numElements * SIZEOF_FLOAT).asFloatBuffer();
    }

    public static FloatBuffer newDirectFloatBuffer(final float[] values, final int offset, final int length) {
        return (FloatBuffer)newDirectFloatBuffer(length).put(values, offset, length).rewind();
    }

    public static FloatBuffer newDirectFloatBuffer(final float[] values, final int offset) {
        return newDirectFloatBuffer(values, offset, values.length - offset);
    }

    public static FloatBuffer newDirectFloatBuffer(final float[] values) {
        return newDirectFloatBuffer(values, 0);
    }

    /**
     * Allocates a new direct IntBuffer with the specified number of
     * elements. The returned buffer will have its byte order set to
     * the host platform's native byte order.
     */
    public static IntBuffer newDirectIntBuffer(final int numElements) {
        return newDirectByteBuffer(numElements * SIZEOF_INT).asIntBuffer();
    }

    public static IntBuffer newDirectIntBuffer(final int[] values, final int offset, final int length) {
        return (IntBuffer)newDirectIntBuffer(length).put(values, offset, length).rewind();
    }

    public static IntBuffer newDirectIntBuffer(final int[] values, final int offset) {
        return newDirectIntBuffer(values, offset, values.length - offset);
    }

    public static IntBuffer newDirectIntBuffer(final int[] values) {
        return newDirectIntBuffer(values, 0);
    }

    /**
     * Allocates a new direct LongBuffer with the specified number of
     * elements. The returned buffer will have its byte order set to
     * the host platform's native byte order.
     */
    public static LongBuffer newDirectLongBuffer(final int numElements) {
        return newDirectByteBuffer(numElements * SIZEOF_LONG).asLongBuffer();
    }

    public static LongBuffer newDirectLongBuffer(final long[] values, final int offset, final int length) {
        return (LongBuffer)newDirectLongBuffer(length).put(values, offset, length).rewind();
    }

    public static LongBuffer newDirectLongBuffer(final long[] values, final int offset) {
        return newDirectLongBuffer(values, offset, values.length - offset);
    }

    public static LongBuffer newDirectLongBuffer(final long[] values) {
        return newDirectLongBuffer(values, 0);
    }

    /**
     * Allocates a new direct ShortBuffer with the specified number of
     * elements. The returned buffer will have its byte order set to
     * the host platform's native byte order.
     */
    public static ShortBuffer newDirectShortBuffer(final int numElements) {
        return newDirectByteBuffer(numElements * SIZEOF_SHORT).asShortBuffer();
    }

    public static ShortBuffer newDirectShortBuffer(final short[] values, final int offset, final int length) {
        return (ShortBuffer)newDirectShortBuffer(length).put(values, offset, length).rewind();
    }

    public static ShortBuffer newDirectShortBuffer(final short[] values, final int offset) {
        return newDirectShortBuffer(values, offset, values.length - offset);
    }

    public static ShortBuffer newDirectShortBuffer(final short[] values) {
        return newDirectShortBuffer(values, 0);
    }

    /**
     * Allocates a new direct CharBuffer with the specified number of
     * elements. The returned buffer will have its byte order set to
     * the host platform's native byte order.
     */
    public static CharBuffer newDirectCharBuffer(final int numElements) {
        return newDirectByteBuffer(numElements * SIZEOF_SHORT).asCharBuffer();
    }

    public static CharBuffer newDirectCharBuffer(final char[] values, final int offset, final int length) {
        return (CharBuffer)newDirectCharBuffer(length).put(values, offset, length).rewind();
    }

    public static CharBuffer newDirectCharBuffer(final char[] values, final int offset) {
        return newDirectCharBuffer(values, offset, values.length - offset);
    }

    public static CharBuffer newDirectCharBuffer(final char[] values) {
        return newDirectCharBuffer(values, 0);
    }

    /**
     * Helper routine to set a ByteBuffer to the native byte order, if
     * that operation is supported by the underlying NIO
     * implementation.
     */
    public static ByteBuffer nativeOrder(final ByteBuffer buf) {
        return buf.order(ByteOrder.nativeOrder());
    }

    /**
     * Returns {@link Buffer} class matching the given lower case `typeName`
     * @param typeName lower-case type name
     * @return matching {@link Buffer} class or `null`
     * @see #sizeOfBufferElem(Class)
     */
    public static Class<? extends Buffer> typeNameToBufferClass(final String typeName) {
        if (typeName == null) {
            return null;
        }
        if( "byte".equals(typeName) ) {
            return ByteBuffer.class;
        } else if( "short".equals(typeName) ) {
            return ShortBuffer.class;
        } else if( "char".equals(typeName) ) {
            return CharBuffer.class;
        } else if( "int".equals(typeName) ) {
            return IntBuffer.class;
        } else if( "float".equals(typeName) ) {
            return FloatBuffer.class;
        } else if( "long".equals(typeName) ) {
            return LongBuffer.class;
        } else if( "double".equals(typeName) ) {
            return DoubleBuffer.class;
        }
        return null;
    }

    /**
     * Returns the size of a single element of the given buffer class in bytes
     * or <code>0</code> if the given buffer is <code>null</code>.
     * @see #typeNameToBufferClass(String)
     * @see #sizeOfBufferElem(Object)
     */
    public static int sizeOfBufferElem(final Class<? extends Buffer> bufferClz) {
        if (bufferClz == null) {
            return 0;
        }
        if (ByteBuffer.class.isAssignableFrom(bufferClz)) {
            return SIZEOF_BYTE;
        } else if (ShortBuffer.class.isAssignableFrom(bufferClz)) {
            return SIZEOF_SHORT;
        } else if (CharBuffer.class.isAssignableFrom(bufferClz)) {
            return SIZEOF_CHAR;
        } else if (IntBuffer.class.isAssignableFrom(bufferClz)) {
            return SIZEOF_INT;
        } else if (FloatBuffer.class.isAssignableFrom(bufferClz)) {
            return SIZEOF_FLOAT;
        } else if (LongBuffer.class.isAssignableFrom(bufferClz)) {
            return SIZEOF_LONG;
        } else if (DoubleBuffer.class.isAssignableFrom(bufferClz)) {
            return SIZEOF_DOUBLE;
        }
        throw new RuntimeException("Unexpected buffer type " + bufferClz.getName());
    }

    /**
     * Returns the size of a single element of the given buffer in bytes
     * or <code>0</code> if the given buffer is <code>null</code>.
     * @see #sizeOfBufferElem(Class)
     */
    public static int sizeOfBufferElem(final Object buffer) {
        if (buffer == null) {
            return 0;
        }
        if (buffer instanceof ByteBuffer) {
            return SIZEOF_BYTE;
        } else if (buffer instanceof IntBuffer) {
            return SIZEOF_INT;
        } else if (buffer instanceof ShortBuffer) {
            return SIZEOF_SHORT;
        } else if (buffer instanceof FloatBuffer) {
            return SIZEOF_FLOAT;
        } else if (buffer instanceof DoubleBuffer) {
            return SIZEOF_DOUBLE;
        } else if (buffer instanceof LongBuffer) {
            return SIZEOF_LONG;
        } else if (buffer instanceof CharBuffer) {
            return SIZEOF_CHAR;
        } else if (buffer instanceof NativeBuffer) {
            return ((NativeBuffer<?>) buffer).elementSize();
        }
        throw new RuntimeException("Unexpected buffer type " + buffer.getClass().getName());
    }

    /**
     * Returns the number of remaining elements of the given anonymous <code>buffer</code>.
     *
     * @param buffer Anonymous <i>Buffer</i> of type {@link NativeBuffer} or a derivation of {@link Buffer}.
     * @return If <code>buffer</code> is null, returns <code>0<code>, otherwise the remaining size in elements.
     * @throws IllegalArgumentException if <code>buffer</code> is of invalid type.
     */
    public static int remainingElem(final Object buffer) throws IllegalArgumentException {
        if (buffer == null) {
            return 0;
        }
        if (buffer instanceof Buffer) {
            return ((Buffer) buffer).remaining();
        } else if (buffer instanceof NativeBuffer) {
            return ((NativeBuffer<?>) buffer).remaining();
        } else {
            throw new IllegalArgumentException("Unsupported anonymous buffer type: "+buffer.getClass().getCanonicalName());
        }
    }

    /**
     * Returns the number of remaining bytes of the given anonymous <code>buffer</code>.
     *
     * @param buffer Anonymous <i>Buffer</i> of type {@link NativeBuffer} or a derivation of {@link Buffer}.
     * @return If <code>buffer</code> is null, returns <code>0<code>, otherwise the remaining size in bytes.
     * @throws IllegalArgumentException if <code>buffer</code> is of invalid type.
     */
    public static int remainingBytes(final Object buffer) throws IllegalArgumentException {
        if (buffer == null) {
            return 0;
        }
        final int bytesRemaining;
        if (buffer instanceof Buffer) {
            final int elementsRemaining = ((Buffer) buffer).remaining();
            if (buffer instanceof ByteBuffer) {
                bytesRemaining = elementsRemaining;
            } else if (buffer instanceof FloatBuffer) {
                bytesRemaining = elementsRemaining * SIZEOF_FLOAT;
            } else if (buffer instanceof IntBuffer) {
                bytesRemaining = elementsRemaining * SIZEOF_INT;
            } else if (buffer instanceof ShortBuffer) {
                bytesRemaining = elementsRemaining * SIZEOF_SHORT;
            } else if (buffer instanceof DoubleBuffer) {
                bytesRemaining = elementsRemaining * SIZEOF_DOUBLE;
            } else if (buffer instanceof LongBuffer) {
                bytesRemaining = elementsRemaining * SIZEOF_LONG;
            } else if (buffer instanceof CharBuffer) {
                bytesRemaining = elementsRemaining * SIZEOF_CHAR;
            } else {
                throw new InternalError("Unsupported Buffer type: "+buffer.getClass().getCanonicalName());
            }
        } else if (buffer instanceof NativeBuffer) {
            final NativeBuffer<?> nb = (NativeBuffer<?>) buffer;
            bytesRemaining = nb.remaining() * nb.elementSize();
        } else {
            throw new IllegalArgumentException("Unsupported anonymous buffer type: "+buffer.getClass().getCanonicalName());
        }
        return bytesRemaining;
    }

    /**
     * Helper routine to tell whether a buffer is direct or not. Null
     * pointers <b>are</b> considered direct.
     */
    public static boolean isDirect(final Object buf) {
        if (buf == null) {
            return true;
        }
        if (buf instanceof Buffer) {
            return ((Buffer) buf).isDirect();
        } else if (buf instanceof PointerBuffer) {
            return ((PointerBuffer) buf).isDirect();
        }
        throw new IllegalArgumentException("Unexpected buffer type " + buf.getClass().getName());
    }

    /**
     * Helper routine to get the Buffer byte offset by taking into
     * account the Buffer position and the underlying type.  This is
     * the total offset for Direct Buffers.
     */
    public static int getDirectBufferByteOffset(final Object buf) {
        if (buf == null) {
            return 0;
        }
        if (buf instanceof Buffer) {
            final int pos = ((Buffer) buf).position();
            if (buf instanceof ByteBuffer) {
                return pos;
            } else if (buf instanceof FloatBuffer) {
                return pos * SIZEOF_FLOAT;
            } else if (buf instanceof IntBuffer) {
                return pos * SIZEOF_INT;
            } else if (buf instanceof ShortBuffer) {
                return pos * SIZEOF_SHORT;
            } else if (buf instanceof DoubleBuffer) {
                return pos * SIZEOF_DOUBLE;
            } else if (buf instanceof LongBuffer) {
                return pos * SIZEOF_LONG;
            } else if (buf instanceof CharBuffer) {
                return pos * SIZEOF_CHAR;
            }
        } else if (buf instanceof NativeBuffer) {
            final NativeBuffer<?> nb = (NativeBuffer<?>) buf;
            return nb.position() * nb.elementSize() ;
        }

        throw new IllegalArgumentException("Disallowed array backing store type in buffer " + buf.getClass().getName());
    }

    /**
     * Helper routine to return the array backing store reference from
     * a Buffer object.
     * @throws UnsupportedOperationException if the passed Object does not have an array backing store
     * @throws IllegalArgumentException if the passed Object is neither of type {@link java.nio.Buffer} or {@link NativeBuffer}.
     */
    public static Object getArray(final Object buf) throws UnsupportedOperationException, IllegalArgumentException {
        if (buf == null) {
            return null;
        }
        if (buf instanceof Buffer) {
            return ((Buffer) buf).array();
        } else if (buf instanceof NativeBuffer) {
            return ((NativeBuffer<?>) buf).array();
        }

        throw new IllegalArgumentException("Disallowed array backing store type in buffer " + buf.getClass().getName());
    }

    /**
     * Helper routine to get the full byte offset from the beginning of
     * the array that is the storage for the indirect Buffer
     * object.  The array offset also includes the position offset
     * within the buffer, in addition to any array offset.
     */
    public static int getIndirectBufferByteOffset(final Object buf) {
        if (buf == null) {
            return 0;
        }
        if (buf instanceof Buffer) {
            final int pos = ((Buffer) buf).position();
            if (buf instanceof ByteBuffer) {
                return (((ByteBuffer) buf).arrayOffset() + pos);
            } else if (buf instanceof FloatBuffer) {
                return (SIZEOF_FLOAT * (((FloatBuffer) buf).arrayOffset() + pos));
            } else if (buf instanceof IntBuffer) {
                return (SIZEOF_INT * (((IntBuffer) buf).arrayOffset() + pos));
            } else if (buf instanceof ShortBuffer) {
                return (SIZEOF_SHORT * (((ShortBuffer) buf).arrayOffset() + pos));
            } else if (buf instanceof DoubleBuffer) {
                return (SIZEOF_DOUBLE * (((DoubleBuffer) buf).arrayOffset() + pos));
            } else if (buf instanceof LongBuffer) {
                return (SIZEOF_LONG * (((LongBuffer) buf).arrayOffset() + pos));
            } else if (buf instanceof CharBuffer) {
                return (SIZEOF_CHAR * (((CharBuffer) buf).arrayOffset() + pos));
            }
        } else if (buf instanceof NativeBuffer) {
            final NativeBuffer<?> nb = (NativeBuffer<?>) buf;
            return nb.elementSize() * ( nb.arrayOffset() + nb.position() );
        }

        throw new IllegalArgumentException("Unknown buffer type " + buf.getClass().getName());
    }


    //----------------------------------------------------------------------
    // Slice routines (mapping buffer to typed-buffer w/o copy)
    //
    /**
     * Calls slice on the specified buffer while maintaining the byteorder.
     * @see #slice(java.nio.Buffer, int, int)
     */
    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B slice(final B buffer) {
        if (buffer instanceof ByteBuffer) {
            final ByteBuffer bb = (ByteBuffer) buffer;
            return (B) bb.slice().order(bb.order()); // slice and duplicate may change byte order
        } else if (buffer instanceof IntBuffer) {
            return (B) ((IntBuffer) buffer).slice();
        } else if (buffer instanceof ShortBuffer) {
            return (B) ((ShortBuffer) buffer).slice();
        } else if (buffer instanceof FloatBuffer) {
            return (B) ((FloatBuffer) buffer).slice();
        } else if (buffer instanceof DoubleBuffer) {
            return (B) ((DoubleBuffer) buffer).slice();
        } else if (buffer instanceof LongBuffer) {
            return (B) ((LongBuffer) buffer).slice();
        } else if (buffer instanceof CharBuffer) {
            return (B) ((CharBuffer) buffer).slice();
        }
        throw new IllegalArgumentException("unexpected buffer type: " + buffer.getClass());
    }

    /**
     * Slices the specified buffer with offset as position and offset+size as limit
     * while maintaining the byteorder.
     * Concurrency warning: this method changes the buffers position and limit but
     * will restore it before return.
     */
    public static <B extends Buffer> B slice(final B buffer, final int offset, final int size) {
        final int pos = buffer.position();
        final int limit = buffer.limit();

        B slice = null;
        try {
            buffer.position(offset).limit(offset+size);
            slice = slice(buffer);
        } finally {
            buffer.position(pos).limit(limit);
        }

        return slice;
    }

    /**
     * Slices a ByteBuffer <i>or</i> a FloatBuffer to a FloatBuffer
     * at the given `elementStartPos` with the given `elementCount` in float-space.
     * <p>
     * The returned sliced buffer's start position is zero.
     * </p>
     * <p>
     * The returned sliced buffer is {@link FloatBuffer#mark() marked} at it's {@link FloatBuffer#position() start position}. Hence
     * {@link FloatBuffer#reset()} will rewind it to start after applying relative operations like {@link FloatBuffer#get()}.
     * </p>
     * <p>
     * Using a ByteBuffer as the source guarantees
     * keeping the source native order programmatically.
     * This works around <a href="http://code.google.com/p/android/issues/detail?id=16434">Honeycomb / Android 3.0 Issue 16434</a>.
     * This bug is resolved at least in Android 3.2.
     * </p>
     *
     * @param buf source Buffer, maybe ByteBuffer (recommended) or FloatBuffer.
     *            Buffer's position is ignored and `elementStartPos` is being used.
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_FLOAT}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_FLOAT}
     * @return FloatBuffer w/ native byte order as given ByteBuffer
     */
    public static final FloatBuffer slice2Float(final Buffer buf, final int elementStartPos, final int elementCount) {
        if( null == buf ) {
            throw new IllegalArgumentException("Buffer is null");
        }
        final int pos = buf.position();
        final int limit = buf.limit();
        final FloatBuffer res;
        try {
            if(buf instanceof ByteBuffer) {
                final ByteBuffer bb = (ByteBuffer) buf;
                bb.position( elementStartPos * Buffers.SIZEOF_FLOAT );
                bb.limit( (elementStartPos + elementCount) * Buffers.SIZEOF_FLOAT );
                res = bb.slice().order(bb.order()).asFloatBuffer(); // slice and duplicate may change byte order
            } else if(buf instanceof FloatBuffer) {
                final FloatBuffer fb = (FloatBuffer) buf;
                fb.position( elementStartPos );
                fb.limit( elementStartPos + elementCount );
                res = fb.slice(); // slice and duplicate may change byte order
            } else {
                throw new IllegalArgumentException("Buffer not ByteBuffer, nor FloarBuffer, nor backing array given");
            }
        } finally {
            buf.position(pos).limit(limit);
        }
        res.mark();
        return res;
    }
    /**
     * Slices a primitive float backing array to a FloatBuffer at the given `elementStartPos` with the given `elementCount`
     * in float-space by {@link FloatBuffer#wrap(float[], int, int) wrapping} the backing array.
     * <p>
     * Due to {@link FloatBuffer#wrap(float[], int, int) wrapping} the backing array,
     * the returned sliced buffer's {@link FloatBuffer#position() start position} equals
     * the given <code>floatStartPos</code> within the given backing array
     * while it's {@link FloatBuffer#arrayOffset() array-offset} is zero.
     * This has the advantage of being able to dismiss the {@link FloatBuffer#arrayOffset() array-offset}
     * in user code, while only being required to consider it's {@link FloatBuffer#position() position}.
     * </p>
     * <p>
     * The returned sliced buffer is {@link FloatBuffer#mark() marked} at it's {@link FloatBuffer#position() start position}. Hence
     * {@link FloatBuffer#reset()} will rewind it to start after applying relative operations like {@link FloatBuffer#get()}.
     * </p>
     *
     * @param backing source float array
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_FLOAT}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_FLOAT}
     * @return FloatBuffer w/ native byte order as given ByteBuffer
     */
    public static final FloatBuffer slice2Float(final float[] backing, final int elementStartPos, final int elementCount) {
        return (FloatBuffer) FloatBuffer.wrap(backing, elementStartPos, elementCount).mark();
    }

    /**
     * Slices a ByteBuffer <i>or</i> a ShortBuffer to a ShortBuffer
     * at the given `elementStartPos` with the given `elementCount` in short-space.
     * <p>
     * The returned sliced buffer's start position is zero.
     * </p>
     * <p>
     * See {@link #slice2Float(Buffer, int, int)} for details.
     * </p>
     *
     * @param buf source Buffer, maybe ByteBuffer (recommended) or ShortBuffer.
     *            Buffer's position is ignored and `elementStartPos` is being used.
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_SHORT}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_SHORT}
     * @return ShortBuffer w/ native byte order as given ByteBuffer
     * @see #slice2Float(Buffer, int, int)
     */
    public static final ShortBuffer slice2Short(final Buffer buf, final int elementStartPos, final int elementCount) {
        if( null == buf ) {
            throw new IllegalArgumentException("Buffer is null");
        }
        final int pos = buf.position();
        final int limit = buf.limit();
        final ShortBuffer res;
        try {
            if(buf instanceof ByteBuffer) {
                final ByteBuffer bb = (ByteBuffer) buf;
                bb.position( elementStartPos * Buffers.SIZEOF_SHORT );
                bb.limit( (elementStartPos + elementCount) * Buffers.SIZEOF_SHORT );
                res = bb.slice().order(bb.order()).asShortBuffer(); // slice and duplicate may change byte order
            } else if(buf instanceof ShortBuffer) {
                final ShortBuffer fb = (ShortBuffer) buf;
                fb.position( elementStartPos );
                fb.limit( elementStartPos + elementCount );
                res = fb.slice(); // slice and duplicate may change byte order
            } else {
                throw new IllegalArgumentException("Buffer not ByteBuffer, nor ShortBuffer");
            }
        } finally {
            buf.position(pos).limit(limit);
        }
        res.mark();
        return res;
    }
    /**
     * Slices a primitive float backing array to a ShortBuffer at the given `elementStartPos` with the given `elementCount`
     * in short-space by {@link ShortBuffer#wrap(float[], int, int) wrapping} the backing array.
     * <p>
     * See {@link #slice2Float(float[], int, int)} for details.
     * </p>
     * @param backing source float array
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_SHORT}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_SHORT}
     * @return ShortBuffer w/ native byte order as given ByteBuffer
     * @see #slice2Float(float[], int, int)
     */
    public static final ShortBuffer slice2Short(final short[] backing, final int elementStartPos, final int elementCount) {
        return (ShortBuffer) ShortBuffer.wrap(backing, elementStartPos, elementCount).mark();
    }

    /**
     * Slices a ByteBuffer <i>or</i> a CharBuffer to a CharBuffer
     * at the given `elementStartPos` with the given `elementCount` in short-space.
     * <p>
     * The returned sliced buffer's start position is zero.
     * </p>
     * <p>
     * See {@link #slice2Float(Buffer, int, int)} for details.
     * </p>
     *
     * @param buf source Buffer, maybe ByteBuffer (recommended) or CharBuffer.
     *            Buffer's position is ignored and `elementStartPos` is being used.
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_CHAR}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_CHAR}
     * @return CharBuffer w/ native byte order as given ByteBuffer
     * @see #slice2Float(Buffer, int, int)
     */
    public static final CharBuffer slice2Char(final Buffer buf, final int elementStartPos, final int elementCount) {
        if( null == buf ) {
            throw new IllegalArgumentException("Buffer is null");
        }
        final int pos = buf.position();
        final int limit = buf.limit();
        final CharBuffer res;
        try {
            if(buf instanceof ByteBuffer) {
                final ByteBuffer bb = (ByteBuffer) buf;
                bb.position( elementStartPos * Buffers.SIZEOF_CHAR );
                bb.limit( (elementStartPos + elementCount) * Buffers.SIZEOF_CHAR );
                res = bb.slice().order(bb.order()).asCharBuffer(); // slice and duplicate may change byte order
            } else if(buf instanceof CharBuffer) {
                final CharBuffer fb = (CharBuffer) buf;
                fb.position( elementStartPos );
                fb.limit( elementStartPos + elementCount );
                res = fb.slice(); // slice and duplicate may change byte order
            } else {
                throw new IllegalArgumentException("Buffer not ByteBuffer, nor CharBuffer");
            }
        } finally {
            buf.position(pos).limit(limit);
        }
        res.mark();
        return res;
    }
    /**
     * Slices a primitive float backing array to a CharBuffer at the given `elementStartPos` with the given `elementCount`
     * in short-space by {@link CharBuffer#wrap(float[], int, int) wrapping} the backing array.
     * <p>
     * See {@link #slice2Float(float[], int, int)} for details.
     * </p>
     * @param backing source float array
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_CHAR}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_CHAR}
     * @return CharBuffer w/ native byte order as given ByteBuffer
     * @see #slice2Float(float[], int, int)
     */
    public static final CharBuffer slice2Char(final char[] backing, final int elementStartPos, final int elementCount) {
        return (CharBuffer) CharBuffer.wrap(backing, elementStartPos, elementCount).mark();
    }

    /**
     * Slices a ByteBuffer <i>or</i> a IntBuffer to a IntBuffer
     * at the given `elementStartPos` with the given `elementCount` in int-space.
     * <p>
     * The returned sliced buffer's start position is zero.
     * </p>
     * <p>
     * See {@link #slice2Float(Buffer, int, int)} for details.
     * </p>
     *
     * @param buf source Buffer, maybe ByteBuffer (recommended) or IntBuffer.
     *            Buffer's position is ignored and `elementStartPos` is being used.
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_INT}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_INT}
     * @return IntBuffer w/ native byte order as given ByteBuffer
     * @see #slice2Float(Buffer, int, int)
     */
    public static final IntBuffer slice2Int(final Buffer buf, final int elementStartPos, final int elementCount) {
        if( null == buf ) {
            throw new IllegalArgumentException("Buffer is null");
        }
        final int pos = buf.position();
        final int limit = buf.limit();
        final IntBuffer res;
        try {
            if(buf instanceof ByteBuffer) {
                final ByteBuffer bb = (ByteBuffer) buf;
                bb.position( elementStartPos * Buffers.SIZEOF_INT );
                bb.limit( (elementStartPos + elementCount) * Buffers.SIZEOF_INT );
                res = bb.slice().order(bb.order()).asIntBuffer(); // slice and duplicate may change byte order
            } else if(buf instanceof IntBuffer) {
                final IntBuffer fb = (IntBuffer) buf;
                fb.position( elementStartPos );
                fb.limit( elementStartPos + elementCount );
                res = fb.slice(); // slice and duplicate may change byte order
            } else {
                throw new IllegalArgumentException("Buffer not ByteBuffer, nor IntBuffer");
            }
        } finally {
            buf.position(pos).limit(limit);
        }
        res.mark();
        return res;
    }
    /**
     * Slices a primitive float backing array to a IntBuffer at the given `elementStartPos` with the given `elementCount`
     * in int-space by {@link IntBuffer#wrap(float[], int, int) wrapping} the backing array.
     * <p>
     * See {@link #slice2Float(float[], int, int)} for details.
     * </p>
     * @param backing source float array
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_INT}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_INT}
     * @return IntBuffer w/ native byte order as given ByteBuffer
     * @see #slice2Float(float[], int, int)
     */
    public static final IntBuffer slice2Int(final int[] backing, final int elementStartPos, final int elementCount) {
        return (IntBuffer) IntBuffer.wrap(backing, elementStartPos, elementCount).mark();
    }

    /**
     * Slices a ByteBuffer <i>or</i> a LongBuffer to a LongBuffer
     * at the given `elementStartPos` with the given `elementCount` in long-space.
     * <p>
     * The returned sliced buffer's start position is zero.
     * </p>
     * <p>
     * See {@link #slice2Float(Buffer, int, int)} for details.
     * </p>
     *
     * @param buf source Buffer, maybe ByteBuffer (recommended) or LongBuffer.
     *            Buffer's position is ignored and `elementStartPos` is being used.
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_LONG}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_LONG}
     * @return LongBuffer w/ native byte order as given ByteBuffer
     * @see #slice2Float(Buffer, int, int)
     */
    public static final LongBuffer slice2Long(final Buffer buf, final int elementStartPos, final int elementCount) {
        if( null == buf ) {
            throw new IllegalArgumentException("Buffer is null");
        }
        final int pos = buf.position();
        final int limit = buf.limit();
        final LongBuffer res;
        try {
            if(buf instanceof ByteBuffer) {
                final ByteBuffer bb = (ByteBuffer) buf;
                bb.position( elementStartPos * Buffers.SIZEOF_LONG );
                bb.limit( (elementStartPos + elementCount) * Buffers.SIZEOF_LONG );
                res = bb.slice().order(bb.order()).asLongBuffer(); // slice and duplicate may change byte order
            } else if(buf instanceof LongBuffer) {
                final LongBuffer fb = (LongBuffer) buf;
                fb.position( elementStartPos );
                fb.limit( elementStartPos + elementCount );
                res = fb.slice(); // slice and duplicate may change byte order
            } else {
                throw new IllegalArgumentException("Buffer not ByteBuffer, nor LongBuffer");
            }
        } finally {
            buf.position(pos).limit(limit);
        }
        res.mark();
        return res;
    }
    /**
     * Slices a primitive float backing array to a LongBuffer at the given `elementStartPos` with the given `elementCount`
     * in long-space by {@link LongBuffer#wrap(float[], int, int) wrapping} the backing array.
     * <p>
     * See {@link #slice2Float(float[], int, int)} for details.
     * </p>
     * @param backing source float array
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_LONG}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_LONG}
     * @return LongBuffer w/ native byte order as given ByteBuffer
     * @see #slice2Float(float[], int, int)
     */
    public static final LongBuffer slice2Long(final long[] backing, final int elementStartPos, final int elementCount) {
        return (LongBuffer) LongBuffer.wrap(backing, elementStartPos, elementCount).mark();
    }

    /**
     * Slices a ByteBuffer <i>or</i> a DoubleBuffer to a DoubleBuffer
     * at the given `elementStartPos` with the given `elementCount` in double-space.
     * <p>
     * The returned sliced buffer's start position is zero.
     * </p>
     * <p>
     * See {@link #slice2Float(Buffer, int, int)} for details.
     * </p>
     *
     * @param buf source Buffer, maybe ByteBuffer (recommended) or DoubleBuffer.
     *            Buffer's position is ignored and `elementStartPos` is being used.
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_DOUBLE}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_DOUBLE}
     * @return DoubleBuffer w/ native byte order as given ByteBuffer
     * @see #slice2Float(Buffer, int, int)
     */
    public static final DoubleBuffer slice2Double(final Buffer buf, final int elementStartPos, final int elementCount) {
        if( null == buf ) {
            throw new IllegalArgumentException("Buffer is null");
        }
        final int pos = buf.position();
        final int limit = buf.limit();
        final DoubleBuffer res;
        try {
            if(buf instanceof ByteBuffer) {
                final ByteBuffer bb = (ByteBuffer) buf;
                bb.position( elementStartPos * Buffers.SIZEOF_DOUBLE );
                bb.limit( (elementStartPos + elementCount) * Buffers.SIZEOF_DOUBLE );
                res = bb.slice().order(bb.order()).asDoubleBuffer(); // slice and duplicate may change byte order
            } else if(buf instanceof DoubleBuffer) {
                final DoubleBuffer fb = (DoubleBuffer) buf;
                fb.position( elementStartPos );
                fb.limit( elementStartPos + elementCount );
                res = fb.slice(); // slice and duplicate may change byte order
            } else {
                throw new IllegalArgumentException("Buffer not ByteBuffer, nor DoubleBuffer");
            }
        } finally {
            buf.position(pos).limit(limit);
        }
        res.mark();
        return res;
    }
    /**
     * Slices a primitive float backing array to a DoubleBuffer at the given `elementStartPos` with the given `elementCount`
     * in double-space by {@link DoubleBuffer#wrap(float[], int, int) wrapping} the backing array.
     * <p>
     * See {@link #slice2Float(float[], int, int)} for details.
     * </p>
     * @param backing source float array
     * @param elementStartPos element start position w/ element of size {@link Buffers#SIZEOF_DOUBLE}
     * @param elementCount element count for element of size {@link Buffers#SIZEOF_DOUBLE}
     * @return DoubleBuffer w/ native byte order as given ByteBuffer
     * @see #slice2Float(float[], int, int)
     */
    public static final DoubleBuffer slice2Double(final double[] backing, final int elementStartPos, final int elementCount) {
        return (DoubleBuffer) DoubleBuffer.wrap(backing, elementStartPos, elementCount).mark();
    }
    //----------------------------------------------------------------------
    // Copy routines (type-to-type)
    //
    /**
     * Copies the <i>remaining</i> elements (as defined by
     * <code>limit() - position()</code>) in the passed ByteBuffer into
     * a newly-allocated direct ByteBuffer. The returned buffer will
     * have its byte order set to the host platform's native byte
     * order. The position of the newly-allocated buffer will be zero,
     * and the position of the passed buffer is unchanged.
     */
    public static ByteBuffer copyByteBuffer(final ByteBuffer orig) {
        final int op0 = orig.position();
        final ByteBuffer dest = newDirectByteBuffer(orig.remaining());
        dest.put(orig);
        dest.rewind();
        orig.position(op0);
        return dest;
    }

    /**
     * Copies the <i>remaining</i> elements (as defined by
     * <code>limit() - position()</code>) in the passed FloatBuffer
     * into a newly-allocated direct FloatBuffer. The returned buffer
     * will have its byte order set to the host platform's native byte
     * order. The position of the newly-allocated buffer will be zero,
     * and the position of the passed buffer is unchanged.
     */
    public static FloatBuffer copyFloatBuffer(final FloatBuffer orig) {
        return copyFloatBufferAsByteBuffer(orig).asFloatBuffer();
    }

    /**
     * Copies the <i>remaining</i> elements (as defined by
     * <code>limit() - position()</code>) in the passed IntBuffer
     * into a newly-allocated direct IntBuffer. The returned buffer
     * will have its byte order set to the host platform's native byte
     * order. The position of the newly-allocated buffer will be zero,
     * and the position of the passed buffer is unchanged.
     */
    public static IntBuffer copyIntBuffer(final IntBuffer orig) {
        return copyIntBufferAsByteBuffer(orig).asIntBuffer();
    }

    /**
     * Copies the <i>remaining</i> elements (as defined by
     * <code>limit() - position()</code>) in the passed ShortBuffer
     * into a newly-allocated direct ShortBuffer. The returned buffer
     * will have its byte order set to the host platform's native byte
     * order. The position of the newly-allocated buffer will be zero,
     * and the position of the passed buffer is unchanged.
     */
    public static ShortBuffer copyShortBuffer(final ShortBuffer orig) {
        return copyShortBufferAsByteBuffer(orig).asShortBuffer();
    }

    //----------------------------------------------------------------------
    // Copy routines (type-to-ByteBuffer)
    //
    /**
     * Copies the <i>remaining</i> elements (as defined by
     * <code>limit() - position()</code>) in the passed FloatBuffer
     * into a newly-allocated direct ByteBuffer. The returned buffer
     * will have its byte order set to the host platform's native byte
     * order. The position of the newly-allocated buffer will be zero,
     * and the position of the passed buffer is unchanged.
     */
    public static ByteBuffer copyFloatBufferAsByteBuffer(final FloatBuffer orig) {
        final int op0 = orig.position();
        final ByteBuffer dest = newDirectByteBuffer(orig.remaining() * SIZEOF_FLOAT);
        dest.asFloatBuffer().put(orig);
        dest.rewind();
        orig.position(op0);
        return dest;
    }

    /**
     * Copies the <i>remaining</i> elements (as defined by
     * <code>limit() - position()</code>) in the passed IntBuffer into
     * a newly-allocated direct ByteBuffer. The returned buffer will
     * have its byte order set to the host platform's native byte
     * order. The position of the newly-allocated buffer will be zero,
     * and the position of the passed buffer is unchanged.
     */
    public static ByteBuffer copyIntBufferAsByteBuffer(final IntBuffer orig) {
        final int op0 = orig.position();
        final ByteBuffer dest = newDirectByteBuffer(orig.remaining() * SIZEOF_INT);
        dest.asIntBuffer().put(orig);
        dest.rewind();
        orig.position(op0);
        return dest;
    }

    /**
     * Copies the <i>remaining</i> elements (as defined by
     * <code>limit() - position()</code>) in the passed ShortBuffer
     * into a newly-allocated direct ByteBuffer. The returned buffer
     * will have its byte order set to the host platform's native byte
     * order. The position of the newly-allocated buffer will be zero,
     * and the position of the passed buffer is unchanged.
     */
    public static ByteBuffer copyShortBufferAsByteBuffer(final ShortBuffer orig) {
        final int op0 = orig.position();
        final ByteBuffer dest = newDirectByteBuffer(orig.remaining() * SIZEOF_SHORT);
        dest.asShortBuffer().put(orig);
        dest.rewind();
        orig.position(op0);
        return dest;
    }

    //----------------------------------------------------------------------
    // Conversion routines
    //

    /**
     * @param source the source array
     * @param soffset the offset
     * @param dest the target array, if null, a new array is being created with size len.
     * @param doffset the offset in the dest array
     * @param len the payload of elements to be copied, if <code>len < 0</code> then <code>len = source.length - soffset</code>
     * @return the passed or newly created target array
     */
    public static float[] getFloatArray(final double[] source, final int soffset, float[] dest, int doffset, int len) {
        if(0>len) {
            len = source.length - soffset;
        }
        if(len > source.length - soffset) {
            throw new IllegalArgumentException("payload ("+len+") greater than remaining source bytes [len "+source.length+", offset "+soffset+"]");
        }
        if(null==dest) {
            dest = new float[len];
            doffset = 0;
        }
        if(len > dest.length - doffset) {
            throw new IllegalArgumentException("payload ("+len+") greater than remaining dest bytes [len "+dest.length+", offset "+doffset+"]");
        }
        for(int i=0; i<len; i++) {
            dest[doffset+i] = (float) source[soffset+i];
        }
        return dest;
    }

    /**
     * No rewind or repositioning is performed.
     * @param source the source buffer, which elements from it's current position and it's limit are being copied
     * @param dest the target buffer, if null, a new buffer is being created with size </code>source.remaining()</code>
     * @return the passed or newly created target buffer
     */
    public static FloatBuffer getFloatBuffer(final DoubleBuffer source, FloatBuffer dest) {
        if(null == dest) {
            dest = newDirectFloatBuffer(source.remaining());
        }
        if( dest.remaining() < source.remaining() ) {
            throw new IllegalArgumentException("payload ("+source.remaining()+") is greater than remaining dest bytes: "+dest.remaining());
        }
        while (source.hasRemaining()) {
            dest.put((float) source.get());
        }
        return dest;
    }

    /**
     * @param source the source array
     * @param soffset the offset
     * @param dest the target array, if null, a new array is being created with size len.
     * @param doffset the offset in the dest array
     * @param len the payload of elements to be copied, if <code>len < 0</code> then <code>len = source.length - soffset</code>
     * @return the passed or newly created target array
     */
    public static double[] getDoubleArray(final float[] source, final int soffset, double[] dest, int doffset, int len) {
        if(0>len) {
            len = source.length - soffset;
        }
        if(len > source.length - soffset) {
            throw new IllegalArgumentException("payload ("+len+") greater than remaining source bytes [len "+source.length+", offset "+soffset+"]");
        }
        if(null==dest) {
            dest = new double[len];
            doffset = 0;
        }
        if(len > dest.length - doffset) {
            throw new IllegalArgumentException("payload ("+len+") greater than remaining dest bytes [len "+dest.length+", offset "+doffset+"]");
        }
        for(int i=0; i<len; i++) {
            dest[doffset+i] = source[soffset+i];
        }
        return dest;
    }

    /**
     * No rewind or repositioning is performed.
     * @param source the source buffer, which elements from it's current position and it's limit are being copied
     * @param dest the target buffer, if null, a new buffer is being created with size </code>source.remaining()</code>
     * @return the passed or newly created target buffer
     */
    public static DoubleBuffer getDoubleBuffer(final FloatBuffer source, DoubleBuffer dest) {
        if(null == dest) {
            dest = newDirectDoubleBuffer(source.remaining());
        }
        if( dest.remaining() < source.remaining() ) {
            throw new IllegalArgumentException("payload ("+source.remaining()+") is greater than remaining dest bytes: "+dest.remaining());
        }
        while (source.hasRemaining()) {
            dest.put(source.get());
        }
        return dest;
    }


    //----------------------------------------------------------------------
    // Convenient put methods with generic target Buffer w/o value range conversion, i.e. normalization
    //

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put(final B dest, final Buffer src) {
        if ((dest instanceof ByteBuffer) && (src instanceof ByteBuffer)) {
            return (B) ((ByteBuffer) dest).put((ByteBuffer) src);
        } else if ((dest instanceof ShortBuffer) && (src instanceof ShortBuffer)) {
            return (B) ((ShortBuffer) dest).put((ShortBuffer) src);
        } else if ((dest instanceof IntBuffer) && (src instanceof IntBuffer)) {
            return (B) ((IntBuffer) dest).put((IntBuffer) src);
        } else if ((dest instanceof FloatBuffer) && (src instanceof FloatBuffer)) {
            return (B) ((FloatBuffer) dest).put((FloatBuffer) src);
        } else if ((dest instanceof LongBuffer) && (src instanceof LongBuffer)) {
            return (B) ((LongBuffer) dest).put((LongBuffer) src);
        } else if ((dest instanceof DoubleBuffer) && (src instanceof DoubleBuffer)) {
            return (B) ((DoubleBuffer) dest).put((DoubleBuffer) src);
        } else if ((dest instanceof CharBuffer) && (src instanceof CharBuffer)) {
            return (B) ((CharBuffer) dest).put((CharBuffer) src);
        }
        throw new IllegalArgumentException("Incompatible Buffer classes: dest = " + dest.getClass().getName() + ", src = " + src.getClass().getName());
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B putb(final B dest, final byte v) {
        if (dest instanceof ByteBuffer) {
            return (B) ((ByteBuffer) dest).put(v);
        } else if (dest instanceof ShortBuffer) {
            return (B) ((ShortBuffer) dest).put(v);
        } else if (dest instanceof IntBuffer) {
            return (B) ((IntBuffer) dest).put(v);
        } else if (dest instanceof FloatBuffer) {
            return (B) ((FloatBuffer) dest).put(v);
        } else if (dest instanceof LongBuffer) {
            return (B) ((LongBuffer) dest).put(v);
        } else if (dest instanceof DoubleBuffer) {
            return (B) ((DoubleBuffer) dest).put(v);
        } else if (dest instanceof CharBuffer) {
            return (B) ((CharBuffer) dest).put((char) v);
        } else {
            throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put3b(final B dest, final byte v1, final byte v2, final byte v3) {
        if (dest instanceof ByteBuffer) {
            final ByteBuffer b = (ByteBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof ShortBuffer) {
            final ShortBuffer b = (ShortBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof IntBuffer) {
            final IntBuffer b = (IntBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof LongBuffer) {
            final LongBuffer b = (LongBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof CharBuffer) {
            final CharBuffer b = (CharBuffer) dest;
            b.put((char)v1);
            b.put((char)v2);
            b.put((char)v3);
            return (B)b;
        } else {
            throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put4b(final B dest, final byte v1, final byte v2, final byte v3, final byte v4) {
        if (dest instanceof ByteBuffer) {
            final ByteBuffer b = (ByteBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof ShortBuffer) {
            final ShortBuffer b = (ShortBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof IntBuffer) {
            final IntBuffer b = (IntBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof LongBuffer) {
            final LongBuffer b = (LongBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof CharBuffer) {
            final CharBuffer b = (CharBuffer) dest;
            b.put((char)v1);
            b.put((char)v2);
            b.put((char)v3);
            b.put((char)v4);
            return (B)b;
        } else {
            throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B putb(final B dest, final byte[] src, final int offset, final int length) {
        if (dest instanceof ByteBuffer) {
            return (B) ((ByteBuffer) dest).put(src, offset, length);
        } else if (dest instanceof ShortBuffer) {
            final ShortBuffer b = (ShortBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else if (dest instanceof IntBuffer) {
            final IntBuffer b = (IntBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else if (dest instanceof LongBuffer) {
            final LongBuffer b = (LongBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else if (dest instanceof CharBuffer) {
            final CharBuffer b = (CharBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put((char) src[offset+i]);
            }
            return (B)b;
        } else {
            throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B puts(final B dest, final short v) {
        if (dest instanceof ShortBuffer) {
            return (B) ((ShortBuffer) dest).put(v);
        } else if (dest instanceof IntBuffer) {
            return (B) ((IntBuffer) dest).put(v);
        } else if (dest instanceof FloatBuffer) {
            return (B) ((FloatBuffer) dest).put(v);
        } else if (dest instanceof LongBuffer) {
            return (B) ((LongBuffer) dest).put(v);
        } else if (dest instanceof DoubleBuffer) {
            return (B) ((DoubleBuffer) dest).put(v);
        } else {
            throw new IllegalArgumentException("Short doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put3s(final B dest, final short v1, final short v2, final short v3) {
        if (dest instanceof ShortBuffer) {
            final ShortBuffer b = (ShortBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof IntBuffer) {
            final IntBuffer b = (IntBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof LongBuffer) {
            final LongBuffer b = (LongBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else {
            throw new IllegalArgumentException("Short doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put4s(final B dest, final short v1, final short v2, final short v3, final short v4) {
        if (dest instanceof ShortBuffer) {
            final ShortBuffer b = (ShortBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof IntBuffer) {
            final IntBuffer b = (IntBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof LongBuffer) {
            final LongBuffer b = (LongBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else {
            throw new IllegalArgumentException("Short doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B puts(final B dest, final short[] src, final int offset, final int length) {
        if (dest instanceof ShortBuffer) {
            return (B) ((ShortBuffer) dest).put(src, offset, length);
        } else if (dest instanceof IntBuffer) {
            final IntBuffer b = (IntBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else if (dest instanceof LongBuffer) {
            final LongBuffer b = (LongBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else {
            throw new IllegalArgumentException("Short doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B puti(final B dest, final int v) {
        if (dest instanceof IntBuffer) {
            return (B) ((IntBuffer) dest).put(v);
        } else if (dest instanceof FloatBuffer) {
            return (B) ((FloatBuffer) dest).put(v);
        } else if (dest instanceof LongBuffer) {
            return (B) ((LongBuffer) dest).put(v);
        } else if (dest instanceof DoubleBuffer) {
            return (B) ((DoubleBuffer) dest).put(v);
        } else {
            throw new IllegalArgumentException("Integer doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put3i(final B dest, final int v1, final int v2, final int v3) {
        if (dest instanceof IntBuffer) {
            final IntBuffer b = (IntBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof LongBuffer) {
            final LongBuffer b = (LongBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else {
            throw new IllegalArgumentException("Integer doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put4i(final B dest, final int v1, final int v2, final int v3, final int v4) {
        if (dest instanceof IntBuffer) {
            final IntBuffer b = (IntBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof LongBuffer) {
            final LongBuffer b = (LongBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else {
            throw new IllegalArgumentException("Integer doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B puti(final B dest, final int[] src, final int offset, final int length) {
        if (dest instanceof IntBuffer) {
            return (B) ((IntBuffer) dest).put(src, offset, length);
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else if (dest instanceof LongBuffer) {
            final LongBuffer b = (LongBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else {
            throw new IllegalArgumentException("Integer doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B putf(final B dest, final float v) {
        if (dest instanceof FloatBuffer) {
            return (B) ((FloatBuffer) dest).put(v);
        } else if (dest instanceof DoubleBuffer) {
            return (B) ((DoubleBuffer) dest).put(v);
/* TODO FixedPoint required
        } else if (dest instanceof IntBuffer) {
            return (B) ((IntBuffer) dest).put(FixedPoint.toFixed(v));
*/
        } else {
            throw new IllegalArgumentException("Float doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put3f(final B dest, final float v1, final float v2, final float v3) {
        if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else {
            throw new IllegalArgumentException("Float doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put4f(final B dest, final float v1, final float v2, final float v3, final float v4) {
        if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else {
            throw new IllegalArgumentException("Float doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B putf(final B dest, final float[] src, final int offset, final int length) {
        if (dest instanceof FloatBuffer) {
            return (B) ((FloatBuffer) dest).put(src, offset, length);
        } else if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put(src[offset+i]);
            }
            return (B)b;
        } else {
            throw new IllegalArgumentException("Float doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B putd(final B dest, final double v) {
        if (dest instanceof DoubleBuffer) {
            return (B) ((DoubleBuffer) dest).put(v);
        } else if (dest instanceof FloatBuffer) {
            return (B) ((FloatBuffer) dest).put((float) v);
        } else {
            throw new IllegalArgumentException("Double doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put3d(final B dest, final double v1, final double v2, final double v3) {
        if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            return (B)b;
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            b.put((float) v1);
            b.put((float) v2);
            b.put((float) v3);
            return (B)b;
        } else {
            throw new IllegalArgumentException("Double doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B put4d(final B dest, final double v1, final double v2, final double v3, final double v4) {
        if (dest instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer) dest;
            b.put(v1);
            b.put(v2);
            b.put(v3);
            b.put(v4);
            return (B)b;
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            b.put((float) v1);
            b.put((float) v2);
            b.put((float) v3);
            b.put((float) v4);
            return (B)b;
        } else {
            throw new IllegalArgumentException("Double doesn't match Buffer Class: " + dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B putd(final B dest, final double[] src, final int offset, final int length) {
        if (dest instanceof DoubleBuffer) {
            return (B) ((DoubleBuffer) dest).put(src, offset, length);
        } else if (dest instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer) dest;
            for(int i=0; i<length; ++i) {
                b.put((float) src[offset+i]);
            }
            return (B)b;
        } else {
            throw new IllegalArgumentException("Float doesn't match Buffer Class: " + dest);
        }
    }

    //----------------------------------------------------------------------
    // Convenient put methods with generic target Buffer and value range conversion, i.e. normalization
    //

    /**
     * Store byte source value in given buffer after normalizing it to the destination value range
     * considering signed and unsigned source and destination representation.
     *
     * @param dest One of {@link ByteBuffer}, {@link ShortBuffer}, {@link IntBuffer}, {@link FloatBuffer}
     * @param dSigned  true if destination buffer holds signed values, false if destination buffer holds unsigned values
     * @param v source byte value to be put in dest buffer
     * @param sSigned  true if source represents a signed value, false if source represents an unsigned value
     */
    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B putNb(final B dest, final boolean dSigned, final byte v, final boolean sSigned) {
        if (dest instanceof ByteBuffer) {
            return (B) ((ByteBuffer) dest).put( v );
        } else if (dest instanceof ShortBuffer) {
            return (B) ((ShortBuffer) dest).put( ValueConv.byte_to_short(v, sSigned, dSigned) );
        } else if (dest instanceof IntBuffer) {
            return (B) ((IntBuffer) dest).put( ValueConv.byte_to_int(v, sSigned, dSigned) );
        } else if (dest instanceof FloatBuffer) {
            return (B) ((FloatBuffer) dest).put( ValueConv.byte_to_float(v, sSigned) );
        } else {
            throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + dest);
        }
    }

    /**
     * Store short source value in given buffer after normalizing it to the destination value range
     * considering signed and unsigned source and destination representation.
     *
     * @param dest One of {@link ByteBuffer}, {@link ShortBuffer}, {@link IntBuffer}, {@link FloatBuffer}
     * @param dSigned  true if destination buffer holds signed values, false if destination buffer holds unsigned values
     * @param v source short value to be put in dest buffer
     * @param sSigned  true if source represents a signed value, false if source represents an unsigned value
     */
    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B putNs(final B dest, final boolean dSigned, final short v, final boolean sSigned) {
        if (dest instanceof ByteBuffer) {
            return (B) ((ByteBuffer) dest).put( ValueConv.short_to_byte(v, sSigned, dSigned) );
        } else if (dest instanceof ShortBuffer) {
            return (B) ((ShortBuffer) dest).put( v );
        } else if (dest instanceof IntBuffer) {
            return (B) ((IntBuffer) dest).put( ValueConv.short_to_int(v, sSigned, dSigned) );
        } else if (dest instanceof FloatBuffer) {
            return (B) ((FloatBuffer) dest).put( ValueConv.short_to_float(v, sSigned) );
        } else {
            throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + dest);
        }
    }

    /**
     * Store short source value in given buffer after normalizing it to the destination value range
     * considering signed and unsigned source and destination representation.
     *
     * @param dest One of {@link ByteBuffer}, {@link ShortBuffer}, {@link IntBuffer}, {@link FloatBuffer}
     * @param dSigned  true if destination buffer holds signed values, false if destination buffer holds unsigned values
     * @param v source short value to be put in dest buffer
     * @param sSigned  true if source represents a signed value, false if source represents an unsigned value
     */
    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B putNi(final B dest, final boolean dSigned, final int v, final boolean sSigned) {
        if (dest instanceof ByteBuffer) {
            return (B) ((ByteBuffer) dest).put( ValueConv.int_to_byte(v, sSigned, dSigned) );
        } else if (dest instanceof ShortBuffer) {
            return (B) ((ShortBuffer) dest).put( ValueConv.int_to_short(v, sSigned, dSigned) );
        } else if (dest instanceof IntBuffer) {
            return (B) ((IntBuffer) dest).put( v );
        } else if (dest instanceof FloatBuffer) {
            return (B) ((FloatBuffer) dest).put( ValueConv.int_to_float(v, sSigned) );
        } else {
            throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + dest);
        }
    }

    /**
     * Store float source value in given buffer after normalizing it to the destination value range
     * considering signed and unsigned destination representation.
     *
     * @param dest One of {@link ByteBuffer}, {@link ShortBuffer}, {@link IntBuffer}, {@link FloatBuffer}
     * @param dSigned  true if destination buffer holds signed values, false if destination buffer holds unsigned values
     * @param v source float value to be put in dest buffer
     */
    @SuppressWarnings("unchecked")
    public static <B extends Buffer> B putNf(final B dest, final boolean dSigned, final float v) {
        if (dest instanceof ByteBuffer) {
            return (B) ((ByteBuffer) dest).put( ValueConv.float_to_byte(v, dSigned) );
        } else if (dest instanceof ShortBuffer) {
            return (B) ((ShortBuffer) dest).put( ValueConv.float_to_short(v, dSigned) );
        } else if (dest instanceof IntBuffer) {
            return (B) ((IntBuffer) dest).put( ValueConv.float_to_int(v, dSigned) );
        } else if (dest instanceof FloatBuffer) {
            return (B) ((FloatBuffer) dest).put( v );
        } else {
            throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + dest);
        }
    }

    //----------------------------------------------------------------------
    // Range check methods
    //

    public static void rangeCheck(final byte[] array, final int offset, final int minElementsRemaining) {
        if (array == null) {
            return;
        }

        if (array.length < offset + minElementsRemaining) {
            throw new ArrayIndexOutOfBoundsException("Required " + minElementsRemaining + " elements in array, only had " + (array.length - offset));
        }
    }

    public static void rangeCheck(final char[] array, final int offset, final int minElementsRemaining) {
        if (array == null) {
            return;
        }

        if (array.length < offset + minElementsRemaining) {
            throw new ArrayIndexOutOfBoundsException("Required " + minElementsRemaining + " elements in array, only had " + (array.length - offset));
        }
    }

    public static void rangeCheck(final short[] array, final int offset, final int minElementsRemaining) {
        if (array == null) {
            return;
        }

        if (array.length < offset + minElementsRemaining) {
            throw new ArrayIndexOutOfBoundsException("Required " + minElementsRemaining + " elements in array, only had " + (array.length - offset));
        }
    }

    public static void rangeCheck(final int[] array, final int offset, final int minElementsRemaining) {
        if (array == null) {
            return;
        }

        if (array.length < offset + minElementsRemaining) {
            throw new ArrayIndexOutOfBoundsException("Required " + minElementsRemaining + " elements in array, only had " + (array.length - offset));
        }
    }

    public static void rangeCheck(final long[] array, final int offset, final int minElementsRemaining) {
        if (array == null) {
            return;
        }

        if (array.length < offset + minElementsRemaining) {
            throw new ArrayIndexOutOfBoundsException("Required " + minElementsRemaining + " elements in array, only had " + (array.length - offset));
        }
    }

    public static void rangeCheck(final float[] array, final int offset, final int minElementsRemaining) {
        if (array == null) {
            return;
        }

        if (array.length < offset + minElementsRemaining) {
            throw new ArrayIndexOutOfBoundsException("Required " + minElementsRemaining + " elements in array, only had " + (array.length - offset));
        }
    }

    public static void rangeCheck(final double[] array, final int offset, final int minElementsRemaining) {
        if (array == null) {
            return;
        }

        if (array.length < offset + minElementsRemaining) {
            throw new ArrayIndexOutOfBoundsException("Required " + minElementsRemaining + " elements in array, only had " + (array.length - offset));
        }
    }

    public static void rangeCheck(final Buffer buffer, final int minElementsRemaining) {
        if (buffer == null) {
            return;
        }

        if (buffer.remaining() < minElementsRemaining) {
            throw new IndexOutOfBoundsException("Required " + minElementsRemaining + " remaining elements in buffer, only had " + buffer.remaining());
        }
    }

    /**
     * @param buffer buffer to test for minimum
     * @param minBytesRemaining minimum bytes remaining
     * @throws IllegalArgumentException if <code>buffer</code> is of invalid type.
     * @throws IndexOutOfBoundsException if {@link #remainingBytes(Object)} is &lt; <code>minBytesRemaining<code>.
     */
    public static void rangeCheckBytes(final Object buffer, final int minBytesRemaining) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (buffer == null) {
            return;
        }
        final int bytesRemaining = remainingBytes(buffer);
        if (bytesRemaining < minBytesRemaining) {
            throw new IndexOutOfBoundsException("Required " + minBytesRemaining + " remaining bytes in buffer, only had " + bytesRemaining);
        }
    }

    /**
     * Appends Buffer details inclusive data to a StringBuilder instance.
     * @param sb optional pass through StringBuilder
     * @param f optional format string of one element, i.e. "%10.5f" for {@link FloatBuffer}, see {@link java.util.Formatter},
     *          or <code>null</code> for unformatted output.
     *          <b>Note:</b> Caller is responsible to match the format string w/ the data type as expected in the given buffer.
     * @param buffer Any valid Buffer instance
     * @return the modified StringBuilder containing the Buffer details
     */
    public static StringBuilder toString(StringBuilder sb, final String f, final Buffer buffer) {
        if(null == sb) {
            sb = new StringBuilder();
        }
        sb.append(buffer.getClass().getSimpleName());
        sb.append("[pos ").append(buffer.position()).append(", lim ").append(buffer.limit()).append(", cap ").append(buffer.capacity());
        sb.append(", remaining ").append(buffer.remaining());
        sb.append("; array ").append(buffer.hasArray()).append(", direct ").append(buffer.isDirect());
        sb.append(", r/w ").append(!buffer.isReadOnly()).append(": ");
        if (buffer instanceof ByteBuffer) {
            final ByteBuffer b = (ByteBuffer)buffer;
            for(int i=0; i<b.limit(); i++) {
                if(0<i) { sb.append(", "); }
                if(null == f) {
                    sb.append(b.get(i));
                } else {
                    sb.append(String.format(f, b.get(i)));
                }
            }
        } else if (buffer instanceof FloatBuffer) {
            final FloatBuffer b = (FloatBuffer)buffer;
            for(int i=0; i<b.limit(); i++) {
                if(0<i) { sb.append(", "); }
                if(null == f) {
                    sb.append(b.get(i));
                } else {
                    sb.append(String.format(f, b.get(i)));
                }
            }
        } else if (buffer instanceof IntBuffer) {
            final IntBuffer b = (IntBuffer)buffer;
            for(int i=0; i<b.limit(); i++) {
                if(0<i) { sb.append(", "); }
                if(null == f) {
                    sb.append(b.get(i));
                } else {
                    sb.append(String.format(f, b.get(i)));
                }
            }
        } else if (buffer instanceof ShortBuffer) {
            final ShortBuffer b = (ShortBuffer)buffer;
            for(int i=0; i<b.limit(); i++) {
                if(0<i) { sb.append(", "); }
                if(null == f) {
                    sb.append(b.get(i));
                } else {
                    sb.append(String.format(f, b.get(i)));
                }
            }
        } else if (buffer instanceof DoubleBuffer) {
            final DoubleBuffer b = (DoubleBuffer)buffer;
            for(int i=0; i<b.limit(); i++) {
                if(0<i) { sb.append(", "); }
                if(null == f) {
                    sb.append(b.get(i));
                } else {
                    sb.append(String.format(f, b.get(i)));
                }
            }
        } else if (buffer instanceof LongBuffer) {
            final LongBuffer b = (LongBuffer)buffer;
            for(int i=0; i<b.limit(); i++) {
                if(0<i) { sb.append(", "); }
                if(null == f) {
                    sb.append(b.get(i));
                } else {
                    sb.append(String.format(f, b.get(i)));
                }
            }
        } else if (buffer instanceof CharBuffer) {
            final CharBuffer b = (CharBuffer)buffer;
            for(int i=0; i<b.limit(); i++) {
                if(0<i) { sb.append(", "); }
                if(null == f) {
                    sb.append(b.get(i));
                } else {
                    sb.append(String.format(f, b.get(i)));
                }
            }
        }
        sb.append("]");
        return sb;
    }

    /**
     * Access to NIO {@link sun.misc.Cleaner}, allowing caller to deterministically clean a given {@link sun.nio.ch.DirectBuffer}.
     */
    public static class Cleaner {
        private static final Method mbbCleaner;
        private static final Method cClean;
        /** OK to be lazy on thread synchronization, just for early out **/
        private static volatile boolean cleanerError;
        static {
            final Method[] _mbbCleaner = { null };
            final Method[] _cClean = { null };
            final boolean hasCleaner;
            if( SecurityUtil.doPrivileged(new PrivilegedAction<Boolean>() {
                @Override
                public Boolean run() {
                    try {
                        if( PlatformPropsImpl.JAVA_9 ) {
                            return UnsafeUtil.hasInvokeCleaner();
                        } else {
                            _mbbCleaner[0] = ReflectionUtil.getMethod("sun.nio.ch.DirectBuffer", "cleaner", null, Buffers.class.getClassLoader());
                            _mbbCleaner[0].setAccessible(true);
                            final Class<?> cleanerType = _mbbCleaner[0].getReturnType();
                            // Java >= 9: jdk.internal.ref.Cleaner (NOT accessible!)
                            //            "Unable to make public void jdk.internal.ref.Cleaner.clean() accessible:
                            //             module java.base does not "exports jdk.internal.ref" to unnamed module"
                            // Java <= 8: sun.misc.Cleaner OK
                            _cClean[0] = cleanerType.getMethod("clean");
                            _cClean[0].setAccessible(true);
                            return Boolean.TRUE;
                        }
                    } catch(final Throwable t) {
                        if( DEBUG ) {
                            ExceptionUtils.dumpThrowable("Buffers", t);
                        }
                        return Boolean.FALSE;
                    } } } ).booleanValue() ) {
                mbbCleaner = _mbbCleaner[0];
                cClean = _cClean[0];
                hasCleaner = PlatformPropsImpl.JAVA_9 || ( null != mbbCleaner && null != cClean );
            } else {
                mbbCleaner = null;
                cClean = null;
                hasCleaner = false;
            }
            cleanerError = !hasCleaner;
            if( DEBUG ) {
                System.err.print("Buffers.Cleaner.init: hasCleaner: "+hasCleaner+", cleanerError "+cleanerError);
                if( null != mbbCleaner ) {
                    System.err.print(", using Cleaner class: "+mbbCleaner.getReturnType().getName());
                }
                System.err.println();
            }
        }
        /**
         * If {@code b} is an direct NIO buffer, i.e {@link sun.nio.ch.DirectBuffer},
         * calls it's {@link sun.misc.Cleaner} instance {@code clean()} method once.
         * @return {@code true} if successful, otherwise {@code false}.
         */
        public static boolean clean(final ByteBuffer bb) {
            if( cleanerError || !bb.isDirect() ) {
                return false;
            }
            try {
                if( PlatformPropsImpl.JAVA_9 ) {
                    UnsafeUtil.invokeCleaner(bb);
                } else {
                    cClean.invoke(mbbCleaner.invoke(bb));
                }
                return true;
            } catch(final Throwable t) {
                cleanerError = true;
                if( DEBUG ) {
                    ExceptionUtils.dumpThrowable("Buffers", t);
                }
                return false;
            }
        }
    }

    /**
     * Copy `len` native bytes @ `source_address` into a newly created direct {@link ByteBuffer}.
     * @param source_address memory address of bytes to copy from
     * @param len number of bytes to copy
     * @return newly created direct {@link ByteBuffer} holding the copied bytes
     */
    public static ByteBuffer copyNativeToDirectByteBuffer(final long source_address, final long len) {
        if( Integer.MAX_VALUE < len ) {
            throw new IllegalArgumentException("length "+len+" > MAX_INT");
        }
        final int lenI = (int)len;
        final ByteBuffer bb = Buffers.newDirectByteBuffer(lenI);
        if( null == bb ) {
            throw new RuntimeException("New direct ByteBuffer is NULL");
        }
        if( 0 < lenI ) {
            final long byteBufferPtr = getDirectBufferAddressImpl(bb);
            memcpyImpl(byteBufferPtr, source_address, lenI);
        }
        return bb;
    }

    /* pp */ static ByteBuffer getDirectByteBuffer(final long aptr, final int byteCount) {
        final ByteBuffer r = getDirectByteBufferImpl(aptr, byteCount);
        return null != r ? nativeOrder( r ) : null;
    }

    /* pp */ static void storeDirectAddress(final long addr, final ByteBuffer dest, final int destBytePos, final int nativeSizeInBytes) {
        switch(nativeSizeInBytes) {
            case 4:
                dest.putInt(destBytePos, (int) ( addr & 0x00000000FFFFFFFFL ) );
                break;
            case 8:
                dest.putLong(destBytePos, addr);
                break;
            default:
                throw new InternalError("invalid nativeSizeInBytes "+nativeSizeInBytes);
        }
    }

    /**
     * Returns <code>strnlen(cstrptr, maxlen)</code> according to POSIX.1-2008.
     * <p>
     * The `strnlen()` function returns the number of bytes in the string pointed to by `cstrptr`, excluding the terminating null byte ('\0'), but at most `maxlen`.
     * In doing this, `strnlen()` looks only at the first `maxlen` characters in the string pointed to by `cstrptr` and never beyond `cstrptr[maxlen-1]`.
     * </p>
     */
    public static int strnlen(final long cstrptr, final int maxlen) {
        return strnlenImpl(cstrptr, maxlen);
    }

    /**
     * Returns <code>memcpy(dest, src, len)</code> according to POSIX.1-2001, POSIX.1-2008.
     * <p>
     * The `memcpy()` function copies `len` bytes from memory area `src` to memory area `dest`.  The memory areas must not overlap.<br/>
     * The `memcpy()` function returns a pointer to `dest`.
     * </p>
    public static long memcpy(final long dest, final long src, final long len) {
        return memcpyImpl(dest, src, len);
    }
     */

    /* pp */ static native long getDirectBufferAddressImpl(Object directBuffer);
    private static native ByteBuffer getDirectByteBufferImpl(long aptr, int byteCount);
    private static native int strnlenImpl(long cstrptr, int maxlen);
    private static native long memcpyImpl(long dest, long src, long len);
}
