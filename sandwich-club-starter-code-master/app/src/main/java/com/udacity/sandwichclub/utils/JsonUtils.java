package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.constants.Constants;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonObject   = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject(Constants.KEY_NAME);

            String mainName = name.optString(Constants.KEY_MAIN_NAME, "");
            List<String> alsoKnowAs = Arrays.asList(name.optString(Constants.KEY_ALSO_KNOW_AS, ""));
            String placeOfOrigin = jsonObject.optString(Constants.KEY_PLACE_OF_ORIGIN, "");
            String description = jsonObject.optString(Constants.KEY_DESCRIPTION, "");
            String image = jsonObject.optString(Constants.KEY_IMAGE, "");
            List<String> ingredients = Arrays.asList(jsonObject.optString(Constants.KEY_INCREDIENTS, ""));

            return new Sandwich(mainName, alsoKnowAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
