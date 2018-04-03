package com.konstant.cardprotocol.capsule;

import com.konstant.cardprotocol.info.Info;

/**
 * 描述:用于构建capsule的工具
 * 创建人:菜籽
 * 创建时间:2018/3/30 下午12:04
 * 备注:
 */

public class CapsuleHelper {

    // 构建密文的capsule
    public static Capsule buildCommandCapsule(){

    }

    // 解析密文的capsule


    // 构建明文的capsule
    public static Capsule buildInfoCapsule(Info info){
        Capsule capsule = new Capsule();
        capsule.setInfo(info.getBytes());
        return capsule;
    }

    // 解析明文的capsule
    public static Info getInfoFromCapsule(Capsule capsule){
        if(!capsule.isPlain()){
            throw new IllegalArgumentException("这不是明文指令");
        }
        Info info = new Info();
        info.fromBytes(capsule.getData());
        return info;
    }

}
