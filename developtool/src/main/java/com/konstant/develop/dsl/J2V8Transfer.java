package com.konstant.develop.dsl;

import android.content.Context;
import android.util.Log;
import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class J2V8Transfer {

    static class Plus {
        public int addIntegerOnNative(int a, int b) {
            return a + b;
        }
    }

    /**
     * 对象注入
     * 把java对象注入到JS内部，JS调用方法时，即可利用java对象来执行代码
     * addIntegerOnNative：是java方法名
     * addOnJavaScript：是JS内部的方法名
     * addIntegerOnNative和addOnJavaScript是映射关系
     */
    public void injectObject() {
        V8 runtime = V8.createV8Runtime();
        Plus plus = new Plus();
        runtime.add("a", 1);
        runtime.add("b", 2);
        runtime.registerJavaMethod(plus, "addIntegerOnNative", "addOnJavaScript", new Class[]{int.class, int.class});
        int result = runtime.executeIntegerScript("var a = addOnJavaScript(a,b);\n a;");
        Log.d("J2V8Transfer", "injectObject，执行结果" + result);
    }

    /**
     * 方法回调
     * 监听JS的方法名，当JS执行到我们监听的方法时，即可回调到callback里面，我们在callback里面拿到传递过来的参数，即可执行
     */
    public void methodCallback() {
        V8 runtime = V8.createV8Runtime();
        MemoryManager scope = new MemoryManager(runtime);
        runtime.registerJavaMethod(new JavaCallback() {
            @Override
            public Object invoke(V8Object v8Object, V8Array v8Array) {
                int a = v8Array.getInteger(0);
                int b = v8Array.getInteger(1);
                Plus plus = new Plus();
                return plus.addIntegerOnNative(a, b);
            }
        }, "addOnJavaScript");
        int result = runtime.executeIntegerScript("var a = addOnJavaScript(1,2);\n a;");
        Log.d("J2V8Transfer", "methodCallback，执行结果" + result);
        scope.release();
    }

    /**
     * 加载JS文件
     */
    public void loadJsFile(Context context){
        try {
            String s1 = loadAssetsFile(context,"factorial.js");
            String s2 = loadAssetsFile(context,"factorial1.js");
            V8 runtime = V8.createV8Runtime();
            runtime.executeScript(s1);
            runtime.executeScript(s2);
            Plus plus = new Plus();
            runtime.registerJavaMethod(plus, "addIntegerOnNative", "addOnJavaScript", new Class[]{int.class, int.class});

            V8Array v8Array = new V8Array(runtime).push(2).push(3);
            int result = runtime.executeIntegerFunction("add2", v8Array);
            Log.d("J2V8Transfer", "loadJsFile，执行结果" + result);
            int result1 = runtime.executeIntegerFunction("add3",v8Array);
            Log.d("J2V8Transfer", "loadJsFile，执行结果 result1 " + result1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadAssetsFile(Context context, String assetsFile){
        try {
            InputStream stream = context.getAssets().open(assetsFile);
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            stream.close();
            String javaScript = new String(bytes, Charset.forName("UTF-8"));
            return javaScript;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}