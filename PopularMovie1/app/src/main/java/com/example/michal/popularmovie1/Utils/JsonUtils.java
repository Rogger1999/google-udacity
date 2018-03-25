package com.example.michal.popularmovie1.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michal on 3/25/18.
 */

public class JsonUtils {
    JSONObject jsonObject;

    public JsonUtils(StringBuilder builder) throws JSONException {
        this.jsonObject = new JSONObject(builder.toString());
        JSONArray jsonArray = new JSONArray(jsonObject.optString("results"));
        for(int i = 0; i < jsonArray.length(); i++)
            Log.i(Constants.TAG, String.valueOf(jsonArray.get(i)) + "\n----------\n");

    }
}
