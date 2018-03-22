package com.konstant.konstanttools.ui.activity.toolactivity.traffic;


import android.util.Log;

import com.jiechic.library.android.snappy.Snappy;

import java.io.IOException;

/**
 * Created by konstant on 2018/3/9.
 */

public class SnappyTool {

    public static byte[] compressString(String origin){
        try {
            byte[] bytes = Snappy.compress(origin.getBytes("UTF-8"));
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String uncompressString(byte[] origin){
        try {
            String s = Snappy.uncompressString(origin);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

}
