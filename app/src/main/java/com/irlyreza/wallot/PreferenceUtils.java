package com.irlyreza.wallot;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

    private static final String PREFERENCES_FILE = "com.irlyreza.wallot.preferences";
    private static final String KEY_PROFILE_IMAGE_URI = "profile_image_uri";

    public static void saveProfileImageUri(Context context, String uri) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PROFILE_IMAGE_URI, uri);
        editor.apply();
    }

    public static String getProfileImageUri(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PROFILE_IMAGE_URI, null);
    }
}
