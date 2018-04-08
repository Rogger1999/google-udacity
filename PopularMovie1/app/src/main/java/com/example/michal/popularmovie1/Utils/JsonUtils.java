package com.example.michal.popularmovie1.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by michal on 3/25/18.
 */

public class JsonUtils {
    JSONObject jsonObject;

    public JsonUtils(StringBuilder builder) {
        try {
            this.jsonObject = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getJsonArrayResults() {
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(jsonObject.optString(Constants.RESULTS));
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
