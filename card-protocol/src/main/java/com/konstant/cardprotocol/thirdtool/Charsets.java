package com.konstant.cardprotocol.thirdtool;

import java.nio.charset.Charset;

/**
 * 描述:guava包中的字符转换工具
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午3:19
 * 备注:
 */

public class Charsets {

    private Charsets() {}

    /**
     * US-ASCII: seven-bit ASCII, the Basic Latin block of the Unicode character set (ISO646-US).
     *
     * <p><b>Note for Java 7 and later:</b> this constant should be treated as deprecated; use
     * {@link java.nio.charset.StandardCharsets#US_ASCII} instead.
     *
     */
    public static final Charset US_ASCII = Charset.forName("US-ASCII");

    /**
     * ISO-8859-1: ISO Latin Alphabet Number 1 (ISO-LATIN-1).
     *
     * <p><b>Note for Java 7 and later:</b> this constant should be treated as deprecated; use
     * {@link java.nio.charset.StandardCharsets#ISO_8859_1} instead.
     *
     */
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    /**
     * UTF-8: eight-bit UCS Transformation Format.
     *
     * <p><b>Note for Java 7 and later:</b> this constant should be treated as deprecated; use
     * {@link java.nio.charset.StandardCharsets#UTF_8} instead.
     *
     */
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * UTF-16BE: sixteen-bit UCS Transformation Format, big-endian byte order.
     *
     * <p><b>Note for Java 7 and later:</b> this constant should be treated as deprecated; use
     * {@link java.nio.charset.StandardCharsets#UTF_16BE} instead.
     *
     */
    public static final Charset UTF_16BE = Charset.forName("UTF-16BE");

    /**
     * UTF-16LE: sixteen-bit UCS Transformation Format, little-endian byte order.
     *
     * <p><b>Note for Java 7 and later:</b> this constant should be treated as deprecated; use
     * {@link java.nio.charset.StandardCharsets#UTF_16LE} instead.
     *
     */
    public static final Charset UTF_16LE = Charset.forName("UTF-16LE");

    /**
     * UTF-16: sixteen-bit UCS Transformation Format, byte order identified by an optional byte-order
     * mark.
     *
     * <p><b>Note for Java 7 and later:</b> this constant should be treated as deprecated; use
     * {@link java.nio.charset.StandardCharsets#UTF_16} instead.
     *
     */
    public static final Charset UTF_16 = Charset.forName("UTF-16");

    /*
     * Please do not add new Charset references to this class, unless those character encodings are
     * part of the set required to be supported by all Java platform implementations! Any Charsets
     * initialized here may cause unexpected delays when this class is loaded. See the Charset
     * Javadocs for the list of built-in character encodings.
     */

}
