package com.eenet.androidbase.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by xiaoma on 2017/9/14.
 */

public class JsonTool {

    public static <T> T fromJson(String json, Class<T> classOfT){
        if(TextUtils.isEmpty(json)){
            return null;
        }
        try {
            return new Gson().fromJson(json, classOfT);
        } catch (JsonSyntaxException e){
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Type type){
        if(TextUtils.isEmpty(json)){
            return null;
        }
        try {
            return new Gson().fromJson(json, type);
        } catch (JsonSyntaxException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(Object object){
        if(object == null){
            return "";
        }
        try {
            return new Gson().toJson(object);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
