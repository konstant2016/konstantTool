package com.konstant.cardprotocol.util;

import com.konstant.cardprotocol.thirdtool.Preconditions;
import com.konstant.cardprotocol.thirdtool.Strings;

import java.util.Random;

public class ByteUtils {

    // int转byte
    public static byte intToByte(int i) {
        return (byte) i;
    }

    // byte转int
    public static int byteToInt(byte b) {
        return b;
    }

    // short类型转为16进制byte数组
    public static byte[] shortToByte2(short i) {
        byte[] bytes = new byte[2];
        bytes[1] = (byte) (0xff & i);
        bytes[0] = (byte) ((0xff00 & i) >> 8);
        return bytes;
    }

    public static short byteToShort(byte[] bytes) {
        Preconditions.checkNotNull(bytes);
        Preconditions.checkArgument(bytes.length == 2);

        Integer i = (0xff & bytes[0]) << 8 | 0xff & bytes[1];
        return i.shortValue();
    }

    /**
     * @param i
     * @return
     */
    public static byte[] intToBytes4(int i) {
        byte bytes[] = new byte[4];
        bytes[3] = (byte) (0xff & i);
        bytes[2] = (byte) ((0xff00 & i) >> 8);
        bytes[1] = (byte) ((0xff0000 & i) >> 16);
        bytes[0] = (byte) ((0xff000000 & i) >> 24);
        return bytes;
    }

    /**
     * 4 bytes to integer
     */
    public static int Bytes4ToInt(byte bytes[]) {
        Preconditions.checkNotNull(bytes);
        Preconditions.checkArgument(bytes.length == 4);

        int ret = (0xff & bytes[0]) << 24
                | (0xff & bytes[1]) << 16
                | (0xff & bytes[2]) << 8
                | 0xff & bytes[3];

        return ret;
    }

    /**
     * @param i
     * @return
     */
    public static byte[] longToBytes8(long i) {
        byte bytes[] = new byte[8];
        bytes[7] = (byte) (0xffL & i);
        bytes[6] = (byte) ((0xff00L & i) >> 8);
        bytes[5] = (byte) ((0xff0000L & i) >> 16);
        bytes[4] = (byte) ((0xff000000L & i) >> 24);
        bytes[3] = (byte) ((0xff00000000L & i) >> 32);
        bytes[2] = (byte) ((0xff0000000000L & i) >> 40);
        bytes[1] = (byte) ((0xff000000000000L & i) >> 48);
        bytes[0] = (byte) ((0xff00000000000000L & i) >> 56);
        return bytes;
    }

    /**
     * 8 bytes to long
     */
    public static long Bytes8ToLong(byte bytes[]) {
        Preconditions.checkNotNull(bytes);
        Preconditions.checkArgument(bytes.length == 8);

        long ret = (0xffL & bytes[0]) << 56
                | (0xffL & bytes[1]) << 48
                | (0xffL & bytes[2]) << 40
                | (0xffL & bytes[3]) << 32
                | (0xffL & bytes[4]) << 24
                | (0xffL & bytes[5]) << 16
                | (0xffL & bytes[6]) << 8
                | 0xffL & bytes[7];

        return ret;
    }


    public static byte[] xor(byte[] src1, byte[] src2) {
        byte[] res = new byte[src1.length];
        for (int i = 0; i < src1.length; i++) {
            res[i] = (byte) (src1[i] ^ src2[i]);
        }
        return res;
    }

    //start copied from source code in Java8
    //HEX <-> Binary Array
    public static byte[] parseHexBinary(String s) {
        final int len = s.length();

        // "111" is not a valid hex encoding.
        if (len % 2 != 0) {
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
        }

        byte[] out = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            int h = hexToBin(s.charAt(i));
            int l = hexToBin(s.charAt(i + 1));
            if (h == -1 || l == -1) {
                throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
            }

            out[i / 2] = (byte) (h * 16 + l);
        }

        return out;
    }

    private static int hexToBin(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        return -1;
    }

    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    public static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }
    //end copied from source code in Java8

    public static String printHexSingleByte(byte b) {
        StringBuilder r = new StringBuilder(2);
        r.append(hexCode[(b >> 4) & 0xF]);
        r.append(hexCode[(b & 0xF)]);
        return r.toString().toUpperCase();
    }

    public static byte parseHexSingleByte(String s) {
        if (Strings.isNullOrEmpty(s) || s.length() != 2) {
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
        }

        int h = hexToBin(s.charAt(0));
        int l = hexToBin(s.charAt(1));

        if (h == -1 || l == -1) {
            throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
        }

        return (byte) (h * 16 + l);
    }


    public static byte[] random(int length) {
        byte[] result = new byte[length];
        Random random = new Random();
        random.nextBytes(result);
        return result;
    }


    public static byte[] negation(byte[] data) {

        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (~data[i]);
        }
        return result;
    }

    public static byte[] padding(byte[] data, int length, Byte num) {
        if (num == null) {
            num = 0x00;
        }
        byte[] result = new byte[length];
        if (data.length < length) {
            System.arraycopy(data, 0, result, 0, data.length);
            byte[] zeros = new byte[length - data.length];
            for (int i = 0; i < zeros.length; i++) {
                zeros[i] = num;
            }
            System.arraycopy(zeros, 0, result, data.length, zeros.length);
        } else {
            System.arraycopy(data, 0, result, 0, length);
        }
        return result;
    }
}
