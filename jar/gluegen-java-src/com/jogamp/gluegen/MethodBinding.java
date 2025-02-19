/**
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

package com.jogamp.gluegen;

import com.jogamp.gluegen.cgram.types.FunctionSymbol;
import com.jogamp.gluegen.cgram.types.FunctionType;
import com.jogamp.gluegen.cgram.types.Type;

import java.util.ArrayList;
import java.util.List;

/** Represents the binding of a C function to a Java method. Also used
    to represent calls through function pointers contained in
    structs. */

public class MethodBinding {

  private final FunctionSymbol sym;
  private final String   delegationImplName;
  private final JavaType containingType;
  private final Type     containingCType;
  private String         nativeName;
  private JavaType       javaReturnType;
  private List<JavaType> javaArgumentTypes;
  private boolean        computedSignatureProperties;
  private boolean        argumentsUseNIO;
  private boolean        signatureUsesNIO;
  private boolean        signatureCanUseIndirectNIO;
  private boolean        signatureUsesCompoundTypeWrappers;
  private boolean        signatureUsesArraysOfCompoundTypeWrappers;
  private boolean        signatureUsesCVoidPointers;
  private boolean        signatureUsesCPrimitivePointers;
  private boolean        signatureUsesCArrays;
  private boolean        signatureUsesJavaPrimitiveArrays;
  private int            thisPointerIndex = -1;

  /**
   * Constructs a new MethodBinding that is an exact clone of the
   * argument, including the java return type and java argument
   * types. It's safe to modify this binding after construction.
   */
  public MethodBinding(final MethodBinding bindingToCopy) {
    this.sym                              = bindingToCopy.sym;
    this.delegationImplName               = bindingToCopy.delegationImplName;
    this.containingType                   = bindingToCopy.containingType;
    this.containingCType                  = bindingToCopy.containingCType;

    this.nativeName                       = bindingToCopy.nativeName;
    this.javaReturnType                   = bindingToCopy.javaReturnType;
    this.javaArgumentTypes                = ( null != bindingToCopy.javaArgumentTypes ) ? new ArrayList<JavaType>(bindingToCopy.javaArgumentTypes) : null;
    this.computedSignatureProperties      = bindingToCopy.computedSignatureProperties;
    this.argumentsUseNIO                  = bindingToCopy.argumentsUseNIO;
    this.signatureUsesNIO                 = bindingToCopy.signatureUsesNIO;
    this.signatureCanUseIndirectNIO       = bindingToCopy.signatureCanUseIndirectNIO;
    this.signatureUsesCompoundTypeWrappers = bindingToCopy.signatureUsesCompoundTypeWrappers;
    this.signatureUsesArraysOfCompoundTypeWrappers = bindingToCopy.signatureUsesArraysOfCompoundTypeWrappers;
    this.signatureUsesCVoidPointers       = bindingToCopy.signatureUsesCVoidPointers;
    this.signatureUsesCPrimitivePointers  = bindingToCopy.signatureUsesCPrimitivePointers;
    this.signatureUsesCArrays             = bindingToCopy.signatureUsesCArrays;
    this.signatureUsesJavaPrimitiveArrays = bindingToCopy.signatureUsesJavaPrimitiveArrays;
    this.thisPointerIndex                 = bindingToCopy.thisPointerIndex;
  }

  /**
   * Constructor for calling a C function or a function pointer contained in a struct.
   * <p>
   * In case of the latter, a struct function pointer,
   * the arguments {@code containingType} and {@code containingCType} must not be {@code null}!
   * </p>
   */
  public MethodBinding(final FunctionSymbol sym,
                       final String delegationImplName,
                       final JavaType javaReturnType,
                       final List<JavaType> javaArgumentTypes,
                       final JavaType containingType,
                       final Type containingCType) {
    this.sym = sym;
    this.delegationImplName = delegationImplName;
    this.containingType = containingType;
    this.containingCType = containingCType;

    this.nativeName = null;
    this.javaReturnType = javaReturnType;
    this.javaArgumentTypes = javaArgumentTypes;
  }

    public void setJavaReturnType(final JavaType type) {
        javaReturnType = type;
        computedSignatureProperties = false;
    }

    public void addJavaArgumentType(final JavaType type) {
        if (javaArgumentTypes == null) {
            javaArgumentTypes = new ArrayList<JavaType>();
        }
        javaArgumentTypes.add(type);
        computedSignatureProperties = false;
    }

    public JavaType getJavaReturnType() {
        return javaReturnType;
    }

    public int getNumArguments() {
        return sym.getNumArguments();
    }

    public JavaType getJavaArgumentType(final int i) {
        return javaArgumentTypes.get(i);
    }

    public Type getCReturnType() {
        return sym.getReturnType();
    }

    public Type getCArgumentType(final int i) {
        return sym.getArgumentType(i);
    }

    /**
     * Returns the function parameter list, i.e. a comma separated list of argument type and name.
     * @param buf StringBuilder instance
     * @param useTypedef if true and type is typedef'ed, use its name
     * @param callingConvention optional calling-convention
     * @return given StringBuilder instance
     */
    public StringBuilder getCParameterList(final StringBuilder buf, final boolean useTypedef, final String callingConvention) {
        return getCParameterList(buf, useTypedef, callingConvention, null);
    }
    /**
     * Returns the function parameter list, i.e. a comma separated list of argument type and name.
     * @param buf StringBuilder instance
     * @param useTypedef if true and type is typedef'ed, use its name
     * @param callingConvention optional calling-convention
     * @param exclude optional list of excluded parameter indices
     * @return given StringBuilder instance
     */
    public StringBuilder getCParameterList(final StringBuilder buf, final boolean useTypedef, final String callingConvention, final List<Integer> exclude) {
        forEachParameter( ( final int idx, final int consumedCount, final Type cType, final JavaType jType, final String name ) -> {
            if( !cType.isVoid() && ( null == exclude || !exclude.contains(idx) ) ) {
                if( 0 < consumedCount ) {
                    buf.append(", ");
                }
                if( useTypedef && cType.isTypedef() ) {
                    buf.append( cType.getName() );
                    if (name != null) {
                        buf.append(" ");
                        buf.append(name);
                    }
                } else if ( cType.isFunctionPointer() ) {
                    final FunctionType ft = cType.getTargetFunction();
                    buf.append( ft.toString(name, callingConvention, false, true) );
                } else if (cType.isArray()) {
                    buf.append( cType.asArray().toString(name) );
                } else {
                    buf.append( cType.getCName(true) );
                    if (name != null) {
                        buf.append(" ");
                        buf.append(name);
                    }
                }
                return true;
            } else {
                return false;
            }
        } );
        return buf;
    }

    /**
     * Returns the function parameter list, i.e. a comma separated list of argument type and name.
     * @param buf StringBuilder instance
     * @return given StringBuilder instance
     */
    public StringBuilder getJavaParameterList(final StringBuilder buf) {
        return getJavaParameterList(buf, null);
    }
    /**
     * Returns the function parameter list, i.e. a comma separated list of argument type and name.
     * @param buf StringBuilder instance
     * @param exclude optional list of excluded parameter indices
     * @return given StringBuilder instance
     */
    public StringBuilder getJavaParameterList(final StringBuilder buf, final List<Integer> exclude) {
        forEachParameter( ( final int idx, final int consumedCount, final Type cType, final JavaType jType, final String name ) -> {
            if( !cType.isVoid() && ( null == exclude || !exclude.contains(idx) ) ) {
                if( 0 < consumedCount ) {
                    buf.append(", ");
                }
                buf.append(jType+" "+name);
                return true;
            } else {
                return false;
            }
        } );
        return buf;
    }
    /**
     * Returns the function parameter list, i.e. a comma separated list of argument type and name.
     * @param buf StringBuilder instance
     * @param include list of explicit included parameter indices
     * @param addTailSeparator add a comma separator in the end if result is not empty
     * @return given StringBuilder instance
     */
    public StringBuilder getJavaSelectParameter(final StringBuilder buf, final List<Integer> include, final boolean addTailSeparator) {
        forEachParameter( ( final int idx, final int consumedCount, final Type cType, final JavaType jType, final String name ) -> {
            if( !cType.isVoid() && include.contains(idx) ) {
                if( 0 < consumedCount ) {
                    buf.append(", ");
                }
                buf.append(jType+" "+name);
                return true;
            } else {
                return false;
            }
        } );
        if( addTailSeparator && buf.length() > 0 ) {
            buf.append(", ");
        }
        return buf;
    }
    public StringBuilder getJavaCallArgumentList(final StringBuilder buf, final List<Integer> exclude) {
        forEachParameter( ( final int idx, final int consumedCount, final Type cType, final JavaType jType, final String name ) -> {
            if( !cType.isVoid() && ( null == exclude || !exclude.contains(idx) ) ) {
                if( 0 < consumedCount ) {
                    buf.append(", ");
                }
                buf.append(name);
                return true;
            } else {
                return false;
            }
        } );
        return buf;
    }
    public StringBuilder getJavaCallSelectArguments(final StringBuilder buf, final List<Integer> include, final boolean addTailSeparator) {
        forEachParameter( ( final int idx, final int consumedCount, final Type cType, final JavaType jType, final String name ) -> {
            if( !cType.isVoid() && include.contains(idx) ) {
                if( 0 < consumedCount ) {
                    buf.append(", ");
                }
                buf.append(name);
                return true;
            } else {
                return false;
            }
        } );
        if( addTailSeparator && buf.length() > 0 ) {
            buf.append(", ");
        }
        return buf;
    }

    /** {@link #forEachParameter(ParameterConsumer)} Consumer */
    public static interface ParameterConsumer  {
        /**
         * Accept the arguments of the traversed collection element
         * and return true if consumed. Consumed elements will increased passed `consumedCount` state.
         * @param idx index of current element, ranges [0 .. size-1]
         * @param consumedCount number of consumed elements, useful for e.g. `boolean needsSeparator =  0 < consumedCount`
         * @param cType C Type of argument
         * @param jType Java Type of argument
         * @param name argument name
         * @return true to signal consumed and have traversing loop increment `consumedCount`, otherwise false
         */
        boolean accept(int idx, int consumedCount, Type cType, JavaType jType, String name);
    }
    public int forEachParameter(final ParameterConsumer c) {
        final int n = getNumArguments();
        int consumedCount = 0;
        for (int i = 0; i < n; i++) {
            if( c.accept(i, consumedCount, getCArgumentType(i), getJavaArgumentType(i), getArgumentName(i)) ) {
                ++consumedCount;
            }
        }
        return consumedCount;
    }

    public final boolean isReturnCompoundByValue() {
        final Type cReturnType = getCReturnType();
        if (cReturnType.isVoid()) {
            return false;
        }
        if (javaReturnType.isPrimitive()) {
            return false;
        }
        return !cReturnType.isPointer() && javaReturnType.isCompoundTypeWrapper();
    }

    /** Returns the {@link FunctionSymbol}. */
    public FunctionSymbol getCSymbol() {
        return sym;
    }

    /** Returns either the argument name specified by the underlying
    FunctionSymbol or a fabricated argument name based on the
    position. Note that it is currently not guaranteed that there
    are no namespace clashes with these fabricated argument
    names. */
    public String getArgumentName(final int i) {
        final String ret = sym.getArgumentName(i);
        if ( null != ret ) {
            return ret;
        }
        return "arg" + i;
    }

    /** Returns the {@link FunctionSymbol}'s current {@link FunctionSymbol#getName() aliased} API name. */
    public String getName() {
        return sym.getName();
    }
    /**
     * The
     * {@link JavaConfiguration#getDelegatedImplementation(com.jogamp.gluegen.cgram.types.AliasedSymbol) implementation delegation}
     * name, or {@code null} for no delegation.
     * @see #getImplName()
     */
    public String getDelegationImplName() {
        return delegationImplName;
    }

    /** Returns the {@link FunctionSymbol}'s current {@link FunctionSymbol#getName() aliased} API name for the interface. */
    public String getInterfaceName() {
        return sym.getName();
    }
    /**
     * Returns the {@link FunctionSymbol}'s name for the implementation,
     * which is the current {@link FunctionSymbol#getName() aliased} API name per default,
     * or the {@link #getDelegationImplName() delegation} name.
     * @see #getDelegationImplName()
     */
    public String getImplName() {
        return null != delegationImplName ? delegationImplName : sym.getName();
    }
    /**
     * Returns the {@link FunctionSymbol}'s name for the native function
     * which is the {@link FunctionSymbol#getOrigName() original} C API name per default,
     * but may be overridden via {@link #setNativeName(String)}.
     */
    public String getNativeName() {
        return null != nativeName ? nativeName : sym.getOrigName();
    }
    public void setNativeName(final String s) { nativeName = s; }

  /** Creates a new MethodBinding replacing the specified Java
      argument type with a new argument type. If argumentNumber is
      less than 0 then replaces the return type. */
  public MethodBinding replaceJavaArgumentType(final int argumentNumber, final JavaType newArgType) {

    final MethodBinding binding = new MethodBinding(this);
    binding.javaArgumentTypes = null;
    if (argumentNumber < 0) {
      binding.setJavaReturnType(newArgType);
    } else {
      binding.setJavaReturnType(javaReturnType);
    }
    for (int i = 0; i < getNumArguments(); i++) {
      JavaType type = getJavaArgumentType(i);
      if (i == argumentNumber) {
        type = newArgType;
      }
      binding.addJavaArgumentType(type);
    }
    return binding;
  }

  /**
   * Returns true if any of the outgoing arguments in the method's
   * signature require conversion or checking due to the use of New
   * I/O.
   */
  public boolean argumentsUseNIO() {
    computeSignatureProperties();
    return argumentsUseNIO;
  }

  /**
   * Returns true if the return type or any of the outgoing arguments
   * in the method's signature require conversion or checking due to
   * the use of New I/O.
   */
  public boolean signatureUsesNIO() {
    computeSignatureProperties();
    return signatureUsesNIO;
  }

  /**
   * Returns true if it is possible for any of the outgoing arguments
   * to be indirect NIO buffers.
   */
  public boolean signatureCanUseIndirectNIO() {
    computeSignatureProperties();
    return signatureCanUseIndirectNIO;
  }

  /**
   * Returns true if the return type or any of the outgoing arguments
   * in the method's signature use "compound type wrappers", or
   * NIO-based wrappers for C data structures.
   */
  public boolean signatureUsesCompoundTypeWrappers() {
    computeSignatureProperties();
    return signatureUsesCompoundTypeWrappers;
  }

  /**
   * Returns true if the return type or any of the outgoing arguments
   * in the method's signature use arrays of "compound type wrappers",
   * or NIO-based wrappers for C data structures.
   */
  public boolean signatureUsesArraysOfCompoundTypeWrappers() {
    computeSignatureProperties();
    return signatureUsesArraysOfCompoundTypeWrappers;
  }

  /**
   * Returns true if the function needs NIO-related
   * wrapping/unwrapping or conversion of various arguments. Currently
   * this returns the logical OR of signatureUsesNIO(),
   * signatureUsesCompoundTypeWrappers() and signatureUsesArraysOfCompoundTypeWrappers().
   */
  public boolean needsNIOWrappingOrUnwrapping() {
    return (signatureUsesNIO() || signatureUsesCompoundTypeWrappers() || signatureUsesArraysOfCompoundTypeWrappers() );
  }

  /**
   * Returns true if the return type or any of the outgoing arguments
   * in the method's signature represent C void* pointers.
   */
  public boolean signatureUsesCVoidPointers() {
    computeSignatureProperties();
    return signatureUsesCVoidPointers;
  }

  /**
   * Returns true if the return type or any of the outgoing arguments
   * in the method's signature represent C primitive pointers.
   */
  public boolean signatureUsesCPrimitivePointers() {
    computeSignatureProperties();
    return signatureUsesCPrimitivePointers;
  }

  /**
   * Returns true if the return type or any of the outgoing arguments
   * in the method's signature represent C arrays.
   */
  public boolean signatureUsesCArrays() {
    computeSignatureProperties();
    return signatureUsesCArrays;
  }

  /**
   * Returns true if the return type or any of the outgoing arguments
   * in the method's signature represent Java primitive arrays.
   */
  public boolean signatureUsesJavaPrimitiveArrays() {
    computeSignatureProperties();
    return signatureUsesJavaPrimitiveArrays;
  }

  /**
   * Computes summary information about the method's C and Java
   * signatures.
   */
  protected void computeSignatureProperties() {
    if (computedSignatureProperties)
      return;

    argumentsUseNIO = false;
    signatureUsesNIO = false;
    signatureCanUseIndirectNIO = false;
    signatureUsesCompoundTypeWrappers = false;
    signatureUsesArraysOfCompoundTypeWrappers = false;
    signatureUsesCVoidPointers = false;
    signatureUsesCPrimitivePointers = false;
    signatureUsesCArrays = false;
    signatureUsesJavaPrimitiveArrays = false;

    if ( javaReturnType.isCompoundTypeWrapper() ) {
      // Needs wrapping and/or setting of byte order (neither of which
      // can be done easily from native code)
      signatureUsesCompoundTypeWrappers = true;
    }

    if (javaReturnType.isNIOBuffer() ||
        javaReturnType.isArrayOfCompoundTypeWrappers()) {
      // Needs setting of byte order and possibly viewing as a
      // different buffer type which can't be done easily from native
      // code
      signatureUsesNIO = true;
    }

    final Type cRetType = sym.getReturnType();
    if (cRetType.isArray()) {
      // Needs checking of array lengths
      signatureUsesCArrays = true;
      if (cRetType.asArray().getTargetType().isPrimitive()) {
        signatureUsesCPrimitivePointers = true;
      }
    }

    if (cRetType.isPointer()) {
      if (cRetType.asPointer().getTargetType().isPrimitive()) {
        signatureUsesCPrimitivePointers = true;
      } else if (cRetType.asPointer().getTargetType().isVoid()) {
        signatureUsesCVoidPointers = true;
      }
    }

    for (int i = 0; i < getNumArguments(); i++) {
      final JavaType javaArgType = getJavaArgumentType(i);
      final Type cArgType = getCArgumentType(i);
      if (javaArgType.isCompoundTypeWrapper()) {
        // Needs unwrapping of accessors
        signatureUsesCompoundTypeWrappers = true;
      }

      if (javaArgType.isArrayOfCompoundTypeWrappers()) {
        // Needs to be duplicated and this array lowered to an array
        // of Buffers for code emission
        signatureUsesArraysOfCompoundTypeWrappers = true;
      }

      if (javaArgType.isNIOBuffer() ||
          javaArgType.isNIOBufferArray()) {
        // Needs checking of direct buffer property
        signatureUsesNIO = true;
        argumentsUseNIO = true;

        if (javaArgType.isNIOBuffer()) {
          // Potential conversion to indirect buffer
          signatureCanUseIndirectNIO = true;
        }
      }

      if (cArgType.isArray()) {
        // Needs checking of array lengths
        signatureUsesCArrays = true;
        if (cArgType.asArray().getTargetType().isPrimitive()) {
          signatureUsesCPrimitivePointers = true;
        }
      }

      if (cArgType.isPointer()) {
        // Handle both real C primitive pointers and any constructions
        // due to opaque directives
        if (cArgType.asPointer().getTargetType().isPrimitive() ||
            javaArgType.isCPrimitivePointerType()) {
          signatureUsesCPrimitivePointers = true;
        } else if (cArgType.asPointer().getTargetType().isVoid()) {
          signatureUsesCVoidPointers = true;
        }
      }

      if (javaArgType.isPrimitiveArray()) {
        // Needs getPrimitiveArrayCritical or similar construct
        // depending on native code calling convention
        signatureUsesJavaPrimitiveArrays = true;
      }
    }

    computedSignatureProperties = true;
  }

  /**
   * Indicates whether this MethodBinding is for a function pointer
   * contained in a struct, or to access array- or pointer-data from a struct.
   * <p>
   * The native calling convention, i.e. via a 'this' function pointer
   * or by a static native function must be decided in the
   * {@link JavaEmitter} handling structs and
   * passed to the {@link CMethodBindingEmitter#setIsCStructFunctionPointer(boolean)}.
   * </p>
   */
  public boolean hasContainingType() {
    return (getContainingType() != null);
  }

  /** Retrieves the containing type of this MethodBinding if it is for
      a function pointer contained in a struct. */
  public JavaType getContainingType() {
    return containingType;
  }

  /** Retrieves the containing C type of this MethodBinding if it is for
      a function pointer contained in a struct. */
  public Type getContainingCType() {
    return containingCType;
  }

  /** Find the leftmost argument matching the type of the containing
      type (for function pointer MethodBindings) and record that as a
      "this" pointer, meaning that it does not need to be explicitly
      passed at the Java level. */
  public void findThisPointer() {
    clearThisPointer();
    for (int i = 0; i < getNumArguments(); i++) {
      final JavaType arg = getJavaArgumentType(i);
      if (arg.equals(containingType)) {
        thisPointerIndex = i;
        break;
      }

      if (!arg.isJNIEnv()) {
        break; // this pointer must be leftmost argument excluding JNIEnvs
      }
    }
  }

  /** Clears any record of a this pointer for this MethodBinding. */
  public void clearThisPointer() {
    thisPointerIndex = -1;
  }

  /** Indicates whether the <i>i</i>th argument to this MethodBinding
      is actually a "this" pointer. */
  public boolean isArgumentThisPointer(final int i) {
    return (thisPointerIndex == i);
  }
  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }

    if (obj == null || ! (obj instanceof MethodBinding)) {
      return false;
    }

    final MethodBinding other = (MethodBinding)obj;
    if ( !getName().equals(other.getName()) ||
         !sym.getType().equals(other.sym.getType()) ) { return false; }
    if (!(javaReturnType.equals(other.getJavaReturnType()))) { return false; }
    if (containingCType != null &&
        other.getContainingCType() != null &&
        (!(containingCType.equals(other.getContainingCType())))) {
        return false;
      }
    if (javaArgumentTypes.size() != other.javaArgumentTypes.size()) {
      return false;
    }

    for (int i = 0; i < javaArgumentTypes.size(); ++i) {
      final Object typeThis = javaArgumentTypes.get(i);
      final Object typeOther = other.getJavaArgumentType(i);
      if (!(typeThis.equals(typeOther))) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    final StringBuilder buf = new StringBuilder(200);
    buf.append(getName());
    buf.append(sym.getType().getName(true));
    buf.append(getJavaReturnType().getName());
    if (containingCType != null) {
        buf.append(containingCType.getName(true));
    }

    for (int i = 0; i < getNumArguments(); i++) {
      final JavaType type = getJavaArgumentType(i);
      if (type.isVoid()) {
        // Make sure this is the only param to the method; if it isn't,
        // there's something wrong with our parsing of the headers.
        assert(getNumArguments() == 1);
        continue;
      }

      buf.append(type.getName());
    }
    return buf.toString().hashCode();
  }

  /** Returns the signature of this binding. */
  @Override
  public String toString() {
    final StringBuilder buf = new StringBuilder(200);
    buf.append(getJavaReturnType().getName());
    buf.append(' ');
    buf.append(getName());
    buf.append('(');
    boolean needComma = false;
    for (int i = 0; i < getNumArguments(); i++) {
      final JavaType type = getJavaArgumentType(i);
      if (type.isVoid()) {
        // Make sure this is the only param to the method; if it isn't,
        // there's something wrong with our parsing of the headers.
        assert(getNumArguments() == 1);
        continue;
      }
      if (type.isJNIEnv() || isArgumentThisPointer(i)) {
        // Don't need to expose these at the Java level
        continue;
      }

      if (needComma) {
        buf.append(", ");
      }

      buf.append(type.getName());
      buf.append(' ');
      buf.append(getArgumentName(i));
      needComma = true;
    }
    buf.append(')');
    return buf.toString();
  }

  /** Returns a String containing the descriptor (signature in
      internal format) of this MethodBinding as it will be
      emitted. This is used to disambiguate between overloadings when
      manually specifying prologue and epilogue code, for example. */
  public String getDescriptor(final boolean forImplementingMethodCall,
                              final boolean eraseBufferAndArrayTypes) {
    final StringBuilder buf = new StringBuilder();

    buf.append('(');

    if (forImplementingMethodCall && hasContainingType()) {
      // Always emit outgoing "this" argument
      buf.append("Ljava/nio/ByteBuffer;");
    }

    for (int i = 0; i < getNumArguments(); i++) {
      final JavaType type = getJavaArgumentType(i);
      if (type.isVoid()) {
        // Make sure this is the only param to the method; if it isn't,
        // there's something wrong with our parsing of the headers.
        if (getNumArguments() != 1) {
          throw new InternalError(
            "\"void\" argument type found in " +
            "multi-argument function \"" + this + "\"");
        }
        continue;
      }

      if (type.isJNIEnv() || isArgumentThisPointer(i)) {
        // Don't need to expose these at the Java level
        continue;
      }

      buf.append(erasedTypeDescriptor(type, eraseBufferAndArrayTypes, false));

      // Add Buffer and array index offset arguments after each associated argument
      if (forImplementingMethodCall) {
        if (type.isNIOBuffer()) {
          buf.append('I');
        } else if (type.isNIOBufferArray()) {
          buf.append("[I");
        }
      }

      // Add offset argument after each primitive array
      if (type.isPrimitiveArray()) {
        buf.append('I');
      }
    }

    buf.append(')');

    // Emit return type for completeness even though we can't overload
    // based solely on return type
    buf.append(erasedTypeDescriptor(getJavaReturnType(), eraseBufferAndArrayTypes, false));

    return buf.toString();
  }

  protected String erasedTypeDescriptor(final JavaType type, final boolean eraseBufferAndArrayTypes, final boolean skipBuffers) {
    if (eraseBufferAndArrayTypes) {
      if (type.isNIOBuffer() ||
          type.isPrimitiveArray()) {
        if (!skipBuffers) {
          // Direct buffers and arrays sent down as Object (but
          // returned as e.g. ByteBuffer)
          return "Ljava/lang/Object;";
        }
      } else if (type.isCompoundTypeWrapper()) {
        // Compound type wrappers are unwrapped to ByteBuffer
        return "Ljava/nio/ByteBuffer;";
      } else if (type.isArrayOfCompoundTypeWrappers()) {
        return "Ljava/nio/ByteBuffer;";
      }
    }
    return type.getDescriptor();
  }
}

