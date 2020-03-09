package com.example.retrofit.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class CustomGson1 {

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Integer.class, new TypeInteger())
                .create();
    }

    public static class TypeInteger extends TypeAdapter<Integer> {

        // 对象写到json里面:序列化时需要用到这个，如果只是单纯的解析，可以不重写这个方法
        @Override
        public void write(JsonWriter out, Integer value) throws IOException {
            out.value(String.valueOf(value));
        }

        // 从json里面读取数据
        @Override
        public Integer read(JsonReader in) throws IOException {
            try {
                return Integer.parseInt(in.nextString());
            } catch (NumberFormatException exception) {
                return 0;
            }
        }
    }
}
