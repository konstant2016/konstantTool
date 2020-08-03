package com.konstant.tool.httpsender.sender;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class SenderManager {

    private static SenderManager manager = new SenderManager();

    public static SenderManager getInstance(){
        return manager;
    }

    private Map<String, Object> mMap = new HashMap<>();

    public <T> T getApi(Class<T> clazz) {
        String clazzName = clazz.getName();
        if (mMap.containsKey(clazzName)) {
            return (T) mMap.get(clazz.getName());
        }
        T object = buildObject(clazz);
        mMap.put(clazzName, object);
        return object;
    }

    private <T> T buildObject(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MethodHandler());
    }

}
