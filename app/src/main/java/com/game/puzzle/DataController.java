package com.game.puzzle;

import android.content.Context;
import android.content.SharedPreferences;

public class DataController {
    public static final String STORAGE_NAME = "Storage";

    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init( Context cntxt ){

        context = cntxt;
        settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }



    public static void addProperty(String name, int value ) {
        if( settings == null ){
            init(context);
        }
        editor.putInt( name, value );
        editor.commit();
    }

    public static Integer getProperty( String name ) {

        if( settings == null ) {
            init(context);
        }

        return settings.getInt( name, 0);
    }

    public static void clearAll() {
        editor.remove("levels");
        addProperty("levels",1);
    }
}
