package com.example.michal.popularmovie1.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michal on 3/25/18.
 */

public class JsonUtils {
    private JSONObject jsonObject;

    public JsonUtils(StringBuilder builder) {
        try {
            this.jsonObject = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getJsonArrayResults() {
        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(jsonObject.optString(Constants.RESULTS));
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
