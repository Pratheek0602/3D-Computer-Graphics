/**
 * Copyright 2013 JogAmp Community. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY JogAmp Community ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JogAmp Community OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of JogAmp Community.
 */

package com.jogamp.opengl.test.junit.jogl.math;

import org.junit.Assert;
import org.junit.Test;

import com.jogamp.junit.util.JunitTracer;
import com.jogamp.opengl.math.Binary64;

public class TestBinary64NOUI extends JunitTracer
{
  @SuppressWarnings("static-method") @Test public void testInfinityExponent()
  {
    Assert.assertEquals(
      1024,
      Binary64.unpackGetExponentUnbiased(Double.POSITIVE_INFINITY));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInfinityNegativeExponent()
  {
    Assert.assertEquals(
      1024,
      Binary64.unpackGetExponentUnbiased(Double.NEGATIVE_INFINITY));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInfinityNegativeSign()
  {
    Assert.assertEquals(1, Binary64.unpackGetSign(Double.NEGATIVE_INFINITY));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInfinityNegativeSignificand()
  {
    Assert.assertEquals(
      0,
      Binary64.unpackGetSignificand(Double.NEGATIVE_INFINITY));
  }

  @SuppressWarnings("static-method") @Test public void testInfinitySign()
  {
    Assert.assertEquals(0, Binary64.unpackGetSign(Double.POSITIVE_INFINITY));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInfinitySignificand()
  {
    Assert.assertEquals(
      0,
      Binary64.unpackGetSignificand(Double.POSITIVE_INFINITY));
  }

  @SuppressWarnings("static-method") @Test public void testNaNExponent()
  {
    Assert.assertEquals(1024, Binary64.unpackGetExponentUnbiased(Double.NaN));
  }

  @SuppressWarnings("static-method") @Test public void testNaNSignificand()
  {
    Assert.assertTrue(Binary64.unpackGetSignificand(Double.NaN) > 0);
  }
}
