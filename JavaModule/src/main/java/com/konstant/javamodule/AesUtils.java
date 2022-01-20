package com.konstant.javamodule;

import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesUtils {

    private static SecretKeySpec createKey(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(32);
        sb.append(password);
        while (sb.length() < 32) {
            sb.append("0");
        }
        if (sb.length() > 32) {
            sb.setLength(32);
        }
        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
    }

    public static byte[] encrypt(byte[] content, String password) {
        try {
            SecretKeySpec key = createKey(password);
            System.out.println(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    /**
     * AES加密，并把结果转成base64后输出
     * @param content 需要转码的内容
     * @param password  AES密码
     * @return
     */
    public static String encrypt(String content, String password) {
        byte[] data = null;
        try {
            data = content.getBytes("UTF-8");
        } catch (Exception var4) {
            var4.printStackTrace();
        }
        data = encrypt(data, password);
        String result = byte2hex(data);
        return result;
    }

    public static byte[] decrypt(byte[] content, String password) {
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String content, String password) {
        byte[] data = null;
        try {
            data = hex2byte(content);
        } catch (Exception var6) {
            var6.printStackTrace();
        }
        data = decrypt(data, password);
        if (data == null) {
            return null;
        } else {
            String result = null;
            try {
                result = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException var5) {
                var5.printStackTrace();
            }
            return result;
        }
    }

    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        String tmp = "";
        for (int n = 0; n < b.length; ++n) {
            tmp = Integer.toHexString(b[n] & 255);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] hex2byte(String inputString) {
        if (inputString != null && inputString.length() >= 2) {
            inputString = inputString.toLowerCase();
            int l = inputString.length() / 2;
            byte[] result = new byte[l];
            for (int i = 0; i < l; ++i) {
                String tmp = inputString.substring(2 * i, 2 * i + 2);
                result[i] = (byte) (Integer.parseInt(tmp, 16) & 255);
            }
            return result;
        } else {
            return new byte[0];
        }
    }
}

