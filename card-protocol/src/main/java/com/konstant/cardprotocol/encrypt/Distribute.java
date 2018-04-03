package com.konstant.cardprotocol.encrypt;

import com.konstant.cardprotocol.util.EncryptUtils;

public class Distribute {

    public static byte[] encrypt(byte[] src, byte[] key) {
        if (null == src || src.length != 16 || key == null || key.length < 8) {
            throw new IllegalArgumentException("参数长度错误");
        }
        try {
            byte[] byteRandomLeft = new byte[8];
            byte[] byteRandomRight = new byte[8];

            System.arraycopy(key, 8, byteRandomLeft, 0, 8);

            if (key.length == 16) {
                System.arraycopy(key, 8, byteRandomRight, 0, 8);
            } else {
                for (int i = 0; i < byteRandomRight.length; i++) {
                    byteRandomRight[i] = (byte) ~byteRandomLeft[i];
                }
            }

            byte[] wkLeft = EncryptUtils.encryptThreeDES(byteRandomLeft,src);
            byte[] wkRight = EncryptUtils.encryptThreeDES(byteRandomRight,src);

            byte[] result = new byte[16];
            System.arraycopy(wkLeft,0,result,0,8);
            System.arraycopy(wkRight,0,result,0,8);

            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
