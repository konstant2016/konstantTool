package com.konstant.cardprotocol.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 描述:加解密工具
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午3:41
 * 备注:
 */

public class EncryptUtils {

    private static final String desKeySpec = "DES";
    private static final String desAlgorithm = "DES/ECB/NoPadding";

    private static final String threeDesKeySpec = "DESede";
    private static final String threeDesAlgorithm = "DESede/";
    private static final String threeDesCbcAlgorithm = "desede/CBC/NoPadding";


    /**
     * des加密
     *
     * @return
     */
    public static byte[] encryptECBDES(byte[] src, byte[] key) {
        if (key.length != 8) {
            throw new IllegalArgumentException("key长度错误");
        }
        int length = (src.length / 8 + ((src.length % 8) == 0 ? 0 : 1)) * 8;
        byte[] data = new byte[length];
        System.arraycopy(src, 0, data, 0, src.length);

        return calculate(data, key, Cipher.ENCRYPT_MODE, desKeySpec, desAlgorithm);
    }

    /**
     * des解密
     *
     * @return
     */
    public static byte[] decryptECBDES(byte[] src, byte[] key) {
        if (key.length != 8) {
            throw new IllegalArgumentException("key长度错误");
        }
        return calculate(src, key, Cipher.DECRYPT_MODE, desKeySpec, desAlgorithm);
    }

    /**
     * 3des加密
     *
     * @return
     */
    public static byte[] encryptThreeDES(byte[] src, byte[] key) {
        byte[] temKey = cutThreeDesKey(key);

        int length = ((src.length % 8 == 0 ? 0 : 1) + src.length / 8) * 8;
        byte[] bytes = new byte[length];
        System.arraycopy(src, 0, bytes, 0, src.length);

        byte[] result = calculate(bytes, temKey, Cipher.ENCRYPT_MODE, threeDesKeySpec, threeDesAlgorithm);
        return result;

    }

    /**
     * 3des加密CBC
     *
     * @return
     */
    public static byte[] encryptThreeDESCBC(byte[] src, byte[] key) {
        byte[] temKey = cutThreeDesKey(key);

        int length = ((src.length % 8 == 0 ? 0 : 1) + src.length / 8) * 8;
        byte[] bytes = new byte[length];
        System.arraycopy(src, 0, bytes, 0, src.length);

        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0};
        byte[] result = calculateWithIv(bytes, temKey, Cipher.ENCRYPT_MODE, threeDesKeySpec, threeDesCbcAlgorithm, iv);

        return result;

    }

    /**
     * 3des解密CBC
     *
     * @return
     */
    public static byte[] decryptThreeDESCBC(byte[] src, byte[] key) {
        byte[] temKey = cutThreeDesKey(key);

        int length = ((src.length % 8 == 0 ? 0 : 1) + src.length / 8) * 8;
        byte[] bytes = new byte[length];
        System.arraycopy(src, 0, bytes, 0, src.length);

        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0};
        byte[] result = calculateWithIv(bytes, temKey, Cipher.DECRYPT_MODE, threeDesKeySpec, threeDesCbcAlgorithm, iv);

        return result;

    }

    /**
     * 3des解密
     *
     * @return
     */
    public static byte[] decryptThreeDES(byte[] src, byte[] key) {
        byte[] temKey = cutThreeDesKey(key);

        return calculate(src, temKey, Cipher.DECRYPT_MODE, threeDesKeySpec, threeDesAlgorithm);
    }


    // 根据指定的key和加密算法进行计算
    private static byte[] calculate(byte[] src, byte[] key, int mode, String keySpec, String algorithm) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, keySpec);
            Cipher c1 = Cipher.getInstance(algorithm);
            c1.init(mode, secretKey);
            return c1.doFinal(src);

        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    private static byte[] calculateWithIv(byte[] src, byte[] key, int mode, String keySpec, String algorithm, byte[] iv) {

        IvParameterSpec ips = new IvParameterSpec(iv);

        SecretKey secretKey = new SecretKeySpec(key, keySpec);
        try {
            Cipher c1 = Cipher.getInstance(algorithm);
            c1.init(mode, secretKey, ips);
            return c1.doFinal(src);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] cutThreeDesKey(byte[] key){
        byte[] temKey = new byte[24];

        if (key.length==16){
            System.arraycopy(key, 0, temKey, 0, 16);
            System.arraycopy(key, 0, temKey, 16, 8);
        }else if (key.length>=24){
            System.arraycopy(key, 0, temKey, 0, 24);
        }else {
            throw new IllegalArgumentException("key长度错误");
        }
        return temKey;
    }

}
