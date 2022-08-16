package com.vdeliverz_delivery.utils;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MnxPreferenceManager {


    private static SharedPreferences sharedPreferences;

    /**
     * Initialize preference manager.
     *
     * @param preferences the preferences
     */
    public static void initializePreferenceManager(SharedPreferences preferences)
    {
        sharedPreferences = preferences;
    }

    /**
     * Gets boolean.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return value store in key
     */
    public static boolean getBoolean(String key, boolean defaultValue)
    {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * Sets boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public static void setBoolean(String key, boolean value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Gets long.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return value store in key
     */
    public static Long getLong(String key, long defaultValue)
    {
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * Sets long.
     *
     * @param key   the key
     * @param value the value
     */
    public static void setLong(String key, long value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Gets user id.
     *
     * @return user id
     */

    /**
     * Gets string.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return string string
     */
    public static String getString(String key, String defaultValue)
    {
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * Sets string.
     *
     * @param key   the key
     * @param value the value
     */
    public static void setString(String key, String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value == null)
            editor.putString(key, "").apply();
        else
            editor.putString(key, value).apply();
    }

    /**
     * Gets integer.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return integer integer
     */
    public static int getInteger(String key, int defaultValue)
    {
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Sets integer.
     *
     * @param key   the key
     * @param value the value
     */
    public static void setInteger(String key, int value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value).apply();
    }

    /**
     * Sets json.
     *
     * @param prefKey the pref key
     * @param key     the key
     * @param value   the value
     */
    public static void setJson(String prefKey, String key, String value)
    {
        String preValue = getString(prefKey, "{}");
        try
        {
            JSONObject jsonObject = new JSONObject(preValue);
            jsonObject.put(key,value);
            setString(prefKey, jsonObject.toString());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static String getAccessToken()
    {
        return null;
    }


    /**
     * Gets json.
     *
     * @param prefKey the pref key
     * @param key     the key
     * @return the json
     */
    public static String getJson(String prefKey, String key)
    {
        String postValue = "";
        String preValue = getString(prefKey, "{}");
        try
        {
            JSONObject jsonObject = new JSONObject(preValue);
            if(jsonObject.has(key))
                postValue = jsonObject.getString(key);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return postValue;
    }

    /**
     * to clear all the values stored in shared proference
     */
    public static void clearShdPref()
    {
        Log.d("clearShdPref", "clearShdPref: ");
        sharedPreferences.edit().clear().apply();

    }



    public static String getArrayList(String prefKey, String key)
    {
        String postValue = "";
        String preValue = getString(prefKey, "{}");
        try
        {
            JSONObject jsonObject = new JSONObject(preValue);
            if(jsonObject.has(key))
                postValue = jsonObject.getString(key);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return postValue;
    }

    public static void setArrayList(String key, String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value == null)
            editor.putString(key, "").apply();
        else
            editor.putString(key, value).apply();
    }

    public static void clearAllPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }



}
