package com.udacity.sandwichclub.utils;

import android.util.Log;

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
            JSONObject name = jsonObject.getJSONObject("name");

            String mainName = name.getString("mainName");
            List<String> alsoKnowAs = Arrays.asList(name.getString("alsoKnownAs"));
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");
            List<String> ingredients = Arrays.asList(jsonObject.getString("ingredients"));

            return new Sandwich(mainName, alsoKnowAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
