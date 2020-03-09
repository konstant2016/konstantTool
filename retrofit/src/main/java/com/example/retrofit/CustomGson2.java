package com.example.retrofit;

import com.example.retrofit.network.CustomGson1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class CustomGson2 {

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Integer.class, new CustomGson1.TypeInteger())
                .create();
    }

    public static class TypeInteger implements JsonSerializer<String>, JsonDeserializer<String> {
        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return null;
        }

        @Override
        public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }

}
