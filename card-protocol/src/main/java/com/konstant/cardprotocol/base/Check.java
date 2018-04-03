package com.konstant.cardprotocol.base;

import com.konstant.cardprotocol.util.ByteUtils;
import com.konstant.cardprotocol.util.EncryptUtils;

public class Check {

    private byte[] random;
    private byte[] mac;

    public static final int RANDOM_LENGTH = 4;
    public static final int MAC_LENGTH = 4;


    // 对指令进行加密
    public static byte[] calculateDataKey(byte[] sharedKey,byte[] random){
        byte[] negation = ByteUtils.negation(random);

        // 3DES进行加密
        byte[] result = EncryptUtils.encryptThreeDES(negation, random);
        byte[] factor = new byte[8];
        System.arraycopy(result,0,factor,0,8);
        
    }

}
