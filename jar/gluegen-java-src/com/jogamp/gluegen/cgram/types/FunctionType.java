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
package com.jogamp.gluegen.cgram.types;

import java.util.*;

import com.jogamp.gluegen.ASTLocusTag;

/** Describes a function type, used to model both function
declarations and (via PointerType) function pointers. */
public class FunctionType extends Type implements Cloneable {

    private final Type returnType;
    private ArrayList<Type> argumentTypes;
    private ArrayList<String> argumentNames;

    public FunctionType(final String name, final SizeThunk size, final Type returnType,
                        final int cvAttributes) {
        this(name, size, returnType, cvAttributes, null);
    }
    public FunctionType(final String name, final SizeThunk size, final Type returnType,
                        final int cvAttributes, final ASTLocusTag astLocus) {
        super(name, size, cvAttributes, astLocus);
        this.returnType = returnType;
    }

    private FunctionType(final FunctionType o, final ASTLocusTag astLocus) {
        super(o, o.getCVAttributes(), astLocus);
        returnType = o.returnType;
        if(null != o.argumentTypes) {
            argumentTypes = new ArrayList<Type>(o.argumentTypes);
        }
        if(null != o.argumentNames) {
            argumentNames = new ArrayList<String>(o.argumentNames);
        }
    }

    @Override
    Type newVariantImpl(final boolean newCVVariant, final int cvAttributes, final ASTLocusTag astLocus) {
        if( newCVVariant ) {
            // Functions don't have const/volatile attributes
            return this;
        } else {
            return new FunctionType(this, astLocus);
        }
    }

    @Override
    protected int hashCodeImpl() {
        // 31 * x == (x << 5) - x
        final int hash = returnType.hashCode();
        return ((hash << 5) - hash) + TypeComparator.listsHashCode(argumentTypes);
    }

    @Override
    protected boolean equalsImpl(final Type arg) {
        final FunctionType t = (FunctionType) arg;
        return returnType.equals(t.returnType) &&
               TypeComparator.listsEqual(argumentTypes, t.argumentTypes);
    }

    @Override
    protected int hashCodeSemanticsImpl() {
        // 31 * x == (x << 5) - x
        final int hash = returnType.hashCodeSemantics();
        return ((hash << 5) - hash) + TypeComparator.listsHashCodeSemantics(argumentTypes);
    }

    @Override
    protected boolean equalSemanticsImpl(final Type arg) {
        final FunctionType t = (FunctionType) arg;
        return returnType.equalSemantics(t.returnType) &&
               TypeComparator.listsEqualSemantics(argumentTypes, t.argumentTypes);
    }

    @Override
    public FunctionType asFunction() {
        return this;
    }

    /** Returns the return type of this function. */
    public Type getReturnType() {
        return returnType;
    }

    public int getNumArguments() {
        return ((argumentTypes == null) ? 0 : argumentTypes.size());
    }

    /** Returns the name of the <i>i</i>th argument. May return null if
    no argument names were available during parsing. */
    public String getArgumentName(final int i) {
        return argumentNames.get(i);
    }

    /** Returns the type of the <i>i</i>th argument. */
    public Type getArgumentType(final int i) {
        return argumentTypes.get(i);
    }

    /**
     * Returns the function parameter list, i.e. a comma separated list of argument type and name.
     * @param buf StringBuilder instance
     * @param useTypedef if true and type is typedef'ed, use its name
     * @param callingConvention optional calling-convention
     * @return given StringBuilder instance
     */
    public StringBuilder getParameterList(final StringBuilder buf, final boolean useTypedef, final String callingConvention) {
        return getParameterList(buf, useTypedef, callingConvention, null);
    }
    /**
     * Returns the function parameter list, i.e. a comma separated list of argument type and name.
     * @param buf StringBuilder instance
     * @param useTypedef if true and type is typedef'ed, use its name
     * @param callingConvention optional calling-convention
     * @param exclude optional list of excluded parameter indices
     * @return given StringBuilder instance
     */
    public StringBuilder getParameterList(final StringBuilder buf, final boolean useTypedef, final String callingConvention, final List<Integer> exclude) {
        forEachParameter( ( final int idx, final int consumedCount, final Type cType, final String name ) -> {
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
                } else  if ( cType.isFunctionPointer() ) {
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

    /** {@link #forEachParameter(ParameterConsumer)} Consumer */
    public static interface ParameterConsumer  {
        /**
         * Accept the arguments of the traversed collection element
         * and return true if consumed. Consumed elements will increased passed `consumedCount` state.
         * @param idx index of current element, ranges [0 .. size-1]
         * @param consumedCount number of consumed elements, useful for e.g. `boolean needsSeparator =  0 < consumedCount`
         * @param cType C Type of argument
         * @param name argument name
         * @return true to signal consumed and have traversing loop increment `consumedCount`, otherwise false
         */
        boolean accept(int idx, int consumedCount, Type cType, String name);
    }
    public int forEachParameter(final ParameterConsumer c) {
        final int n = getNumArguments();
        int consumedCount = 0;
        for (int i = 0; i < n; i++) {
            if( c.accept(i, consumedCount, getArgumentType(i), getArgumentName(i)) ) {
                ++consumedCount;
            }
        }
        return consumedCount;
    }

    /**
     * Add an argument's name and type. Use null for unknown argument names.
     */
    public void addArgument(final Type argumentType, final String argumentName) {
        if (argumentTypes == null) {
            argumentTypes = new ArrayList<Type>();
            argumentNames = new ArrayList<String>();
        }
        argumentTypes.add(argumentType);
        argumentNames.add(argumentName);
        clearCache();
    }

    public void setArgumentName(final int i, final String name) {
        argumentNames.set(i, name);
        clearCache();
    }

    @Override
    public String toString() {
        return toString(null, false);
    }

    public String toString(final String functionName, final boolean emitNativeTag) {
        return toString(functionName, null, emitNativeTag, false);
    }
    public String toString(final String functionName, final boolean emitNativeTag, final boolean isPointer) {
        return toString(functionName, null, emitNativeTag, isPointer);
    }

    public String toString(final String functionName, final String callingConvention,
                           final boolean emitNativeTag, final boolean isPointer) {
        final StringBuilder res = new StringBuilder();
        res.append(getReturnType().getCName(true));
        res.append(" ");
        if (isPointer) {
            res.append("(");
            if (callingConvention != null) {
                res.append(callingConvention);
            }
            res.append("*");
        }
        if (functionName != null) {
            if (emitNativeTag) {
                // Emit @native tag for javadoc purposes
                res.append("{@native ");
            }
            res.append(functionName);
            if (emitNativeTag) {
                res.append("}");
            }
        }
        if (isPointer) {
            res.append(")");
        }
        res.append("(");
        getParameterList(res, true, callingConvention);
        res.append(")");
        return res.toString();
    }

    @Override
    public void visit(final TypeVisitor arg) {
        super.visit(arg);
        returnType.visit(arg);
        final int n = getNumArguments();
        for (int i = 0; i < n; i++) {
            getArgumentType(i).visit(arg);
        }
    }
}
