package com.example.smallnetworkandroom.viewmodels;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;

public class Converters {
    @TypeConverter
    public static HashMap<Date, Float> stringToMap(String value) {
        Type mapType = new TypeToken<HashMap<Date, Float>>() {}.getType();

        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        return  gson.fromJson(value, mapType);
    }

    @TypeConverter
    public static String mapToString(HashMap<Date, Float> map) {

        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        String json = gson.toJson(map);
        return json;
    }
}
