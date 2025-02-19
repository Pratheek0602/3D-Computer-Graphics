/*

 ============================================================================
                   The Apache Software License, Version 1.1
 ============================================================================

 Copyright (C) 1999-2003 The Apache Software Foundation. All rights reserved.

 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:

 1. Redistributions of  source code must  retain the above copyright  notice,
    this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 3. The end-user documentation included with the redistribution, if any, must
    include  the following  acknowledgment:  "This product includes  software
    developed  by the  Apache Software Foundation  (http://www.apache.org/)."
    Alternately, this  acknowledgment may  appear in the software itself,  if
    and wherever such third-party acknowledgments normally appear.

 4. The names "Batik" and  "Apache Software Foundation" must  not  be
    used to  endorse or promote  products derived from  this software without
    prior written permission. For written permission, please contact
    apache@apache.org.

 5. Products  derived from this software may not  be called "Apache", nor may
    "Apache" appear  in their name,  without prior written permission  of the
    Apache Software Foundation.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
 DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 This software  consists of voluntary contributions made  by many individuals
 on  behalf of the Apache Software  Foundation. For more  information on the
 Apache Software Foundation, please see <http://www.apache.org/>.

*/

package jogamp.graph.font.typecast.ot.table;

import java.io.DataInput;
import java.io.IOException;

/**
 * @author <a href="mailto:david.schweinsberg@gmail.com">David Schweinsberg</a>
 */
public class Os2Table implements Table {

    private int _version;
    private short _xAvgCharWidth;
    private int _usWeightClass;
    private int _usWidthClass;
    private short _fsType;
    private short _ySubscriptXSize;
    private short _ySubscriptYSize;
    private short _ySubscriptXOffset;
    private short _ySubscriptYOffset;
    private short _ySuperscriptXSize;
    private short _ySuperscriptYSize;
    private short _ySuperscriptXOffset;
    private short _ySuperscriptYOffset;
    private short _yStrikeoutSize;
    private short _yStrikeoutPosition;
    private short _sFamilyClass;
    private Panose _panose;
    private int _ulUnicodeRange1;
    private int _ulUnicodeRange2;
    private int _ulUnicodeRange3;
    private int _ulUnicodeRange4;
    private int _achVendorID;
    private short _fsSelection;
    private int _usFirstCharIndex;
    private int _usLastCharIndex;
    private short _sTypoAscender;
    private short _sTypoDescender;
    private short _sTypoLineGap;
    private int _usWinAscent;
    private int _usWinDescent;
    private int _ulCodePageRange1;
    private int _ulCodePageRange2;
    private short _sxHeight;
    private short _sCapHeight;
    private int _usDefaultChar;
    private int _usBreakChar;
    private int _usMaxContext;

    public Os2Table(DataInput di) throws IOException {
        _version = di.readUnsignedShort();
        _xAvgCharWidth = di.readShort();
        _usWeightClass = di.readUnsignedShort();
        _usWidthClass = di.readUnsignedShort();
        _fsType = di.readShort();
        _ySubscriptXSize = di.readShort();
        _ySubscriptYSize = di.readShort();
        _ySubscriptXOffset = di.readShort();
        _ySubscriptYOffset = di.readShort();
        _ySuperscriptXSize = di.readShort();
        _ySuperscriptYSize = di.readShort();
        _ySuperscriptXOffset = di.readShort();
        _ySuperscriptYOffset = di.readShort();
        _yStrikeoutSize = di.readShort();
        _yStrikeoutPosition = di.readShort();
        _sFamilyClass = di.readShort();
        byte[] buf = new byte[10];
        di.readFully(buf);
        _panose = new Panose(buf);
        _ulUnicodeRange1 = di.readInt();
        _ulUnicodeRange2 = di.readInt();
        _ulUnicodeRange3 = di.readInt();
        _ulUnicodeRange4 = di.readInt();
        _achVendorID = di.readInt();
        _fsSelection = di.readShort();
        _usFirstCharIndex = di.readUnsignedShort();
        _usLastCharIndex = di.readUnsignedShort();
        _sTypoAscender = di.readShort();
        _sTypoDescender = di.readShort();
        _sTypoLineGap = di.readShort();
        _usWinAscent = di.readUnsignedShort();
        _usWinDescent = di.readUnsignedShort();
        _ulCodePageRange1 = di.readInt();
        _ulCodePageRange2 = di.readInt();
        
        // OpenType 1.3
        if (_version == 2) {
            _sxHeight = di.readShort();
            _sCapHeight = di.readShort();
            _usDefaultChar = di.readUnsignedShort();
            _usBreakChar = di.readUnsignedShort();
            _usMaxContext = di.readUnsignedShort();
        }
    }

    public int getVersion() {
        return _version;
    }

    public short getAvgCharWidth() {
        return _xAvgCharWidth;
    }

    public int getWeightClass() {
        return _usWeightClass;
    }

    public int getWidthClass() {
        return _usWidthClass;
    }

    public short getLicenseType() {
        return _fsType;
    }

    public short getSubscriptXSize() {
        return _ySubscriptXSize;
    }

    public short getSubscriptYSize() {
        return _ySubscriptYSize;
    }

    public short getSubscriptXOffset() {
        return _ySubscriptXOffset;
    }

    public short getSubscriptYOffset() {
        return _ySubscriptYOffset;
    }

    public short getSuperscriptXSize() {
        return _ySuperscriptXSize;
    }

    public short getSuperscriptYSize() {
        return _ySuperscriptYSize;
    }

    public short getSuperscriptXOffset() {
        return _ySuperscriptXOffset;
    }

    public short getSuperscriptYOffset() {
        return _ySuperscriptYOffset;
    }

    public short getStrikeoutSize() {
        return _yStrikeoutSize;
    }

    public short getStrikeoutPosition() {
        return _yStrikeoutPosition;
    }

    public short getFamilyClass() {
        return _sFamilyClass;
    }

    public Panose getPanose() {
        return _panose;
    }

    public int getUnicodeRange1() {
        return _ulUnicodeRange1;
    }

    public int getUnicodeRange2() {
        return _ulUnicodeRange2;
    }

    public int getUnicodeRange3() {
        return _ulUnicodeRange3;
    }

    public int getUnicodeRange4() {
        return _ulUnicodeRange4;
    }

    public int getVendorID() {
        return _achVendorID;
    }

    public short getSelection() {
        return _fsSelection;
    }

    public int getFirstCharIndex() {
        return _usFirstCharIndex;
    }

    public int getLastCharIndex() {
        return _usLastCharIndex;
    }

    public short getTypoAscender() {
        return _sTypoAscender;
    }

    public short getTypoDescender() {
        return _sTypoDescender;
    }

    public short getTypoLineGap() {
        return _sTypoLineGap;
    }

    public int getWinAscent() {
        return _usWinAscent;
    }

    public int getWinDescent() {
        return _usWinDescent;
    }

    public int getCodePageRange1() {
        return _ulCodePageRange1;
    }

    public int getCodePageRange2() {
        return _ulCodePageRange2;
    }

    public short getXHeight() {
        return _sxHeight;
    }
    
    public short getCapHeight() {
        return _sCapHeight;
    }
    
    public int getDefaultChar() {
        return _usDefaultChar;
    }
    
    public int getBreakChar() {
        return _usBreakChar;
    }
    
    public int getMaxContext() {
        return _usMaxContext;
    }

    public String toString() {
        return "'OS/2' Table - OS/2 and Windows Metrics\n---------------------------------------" +
                "\n  'OS/2' version:      " + _version +
                "\n  xAvgCharWidth:       " + _xAvgCharWidth +
                "\n  usWeightClass:       " + _usWeightClass +
                "\n  usWidthClass:        " + _usWidthClass +
                "\n  fsType:              0x" + Integer.toHexString(_fsType).toUpperCase() +
                "\n  ySubscriptXSize:     " + _ySubscriptXSize +
                "\n  ySubscriptYSize:     " + _ySubscriptYSize +
                "\n  ySubscriptXOffset:   " + _ySubscriptXOffset +
                "\n  ySubscriptYOffset:   " + _ySubscriptYOffset +
                "\n  ySuperscriptXSize:   " + _ySuperscriptXSize +
                "\n  ySuperscriptYSize:   " + _ySuperscriptYSize +
                "\n  ySuperscriptXOffset: " + _ySuperscriptXOffset +
                "\n  ySuperscriptYOffset: " + _ySuperscriptYOffset +
                "\n  yStrikeoutSize:      " + _yStrikeoutSize +
                "\n  yStrikeoutPosition:  " + _yStrikeoutPosition +
                "\n  sFamilyClass:        " + (_sFamilyClass >> 8) +
                "    subclass = " + (_sFamilyClass & 0xff) +
                "\n  PANOSE:              " + _panose.toString() +
                "\n  Unicode Range 1( Bits 0 - 31 ): " + Integer.toHexString(_ulUnicodeRange1).toUpperCase() +
                "\n  Unicode Range 2( Bits 32- 63 ): " + Integer.toHexString(_ulUnicodeRange2).toUpperCase() +
                "\n  Unicode Range 3( Bits 64- 95 ): " + Integer.toHexString(_ulUnicodeRange3).toUpperCase() +
                "\n  Unicode Range 4( Bits 96-127 ): " + Integer.toHexString(_ulUnicodeRange4).toUpperCase() +
                "\n  achVendID:           '" + getVendorIDAsString() +
                "'\n  fsSelection:         0x" + Integer.toHexString(_fsSelection).toUpperCase() +
                "\n  usFirstCharIndex:    0x" + Integer.toHexString(_usFirstCharIndex).toUpperCase() +
                "\n  usLastCharIndex:     0x" + Integer.toHexString(_usLastCharIndex).toUpperCase() +
                "\n  sTypoAscender:       " + _sTypoAscender +
                "\n  sTypoDescender:      " + _sTypoDescender +
                "\n  sTypoLineGap:        " + _sTypoLineGap +
                "\n  usWinAscent:         " + _usWinAscent +
                "\n  usWinDescent:        " + _usWinDescent +
                "\n  CodePage Range 1( Bits 0 - 31 ): " + Integer.toHexString(_ulCodePageRange1).toUpperCase() +
                "\n  CodePage Range 2( Bits 32- 63 ): " + Integer.toHexString(_ulCodePageRange2).toUpperCase();
    }
    
    private String getVendorIDAsString() {
        return String.valueOf((char) ((_achVendorID >> 24) & 0xff)) +
                (char) ((_achVendorID >> 16) & 0xff) +
                (char) ((_achVendorID >> 8) & 0xff) +
                (char) ((_achVendorID) & 0xff);
    }
    
}
