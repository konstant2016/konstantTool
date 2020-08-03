package com.example.retrofit.network;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class CustomGson {

    public static Gson buildGson() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(Integer.class, new GsonIntegerDefaultAdapter())
                .create();
        return gson;
    }

    public static class GsonIntegerDefaultAdapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
        // 这里是重写的反序列化：也就是从json中读取数据
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.getAsString().equals("") || json.getAsString().equals("null")) {
                return 0;
            }
            return json.getAsInt();
        }

        /**
         * 这里重写json的序列化：也就是把对象转换为json
         * 序列化时需要用到这个，如果只是单纯的解析，可以不重写这个方法
         * @param src          写到json里面的内容
         * @param typeOfSrc    src的类型
         * @param context
         * @return
         */
        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }
}
