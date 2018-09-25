package com.tiredpapi.baking.baker.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private JSONArray jsonArray;


    public JsonUtils(String s) {
        try {
            this.jsonArray = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public JSONObject getObject(int i) {
        try {
            Log.i(Constants.TAG, jsonArray.getJSONObject(i).toString());
            return jsonArray.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Spannable getJsonIngredentsTitle(int i) {
        Spannable inc = new SpannableString(Constants.INGREDIENTS_TITLE);
        inc.setSpan(new ForegroundColorSpan(Color.RED), 0, inc.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return inc;
    }

    public Spannable getJsonStepsTitle(int i) {
        Spannable inc = new SpannableString(Constants.STEPS_TITLE);
        inc.setSpan(new ForegroundColorSpan(Color.RED), 0, inc.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return inc;
    }

    public String getInstructions() {
        try {
            JSONArray ingredients = jsonArray.getJSONObject(Constants.currentElement).getJSONArray(Constants.STEPS);
            return ingredients.getJSONObject(Constants.currentStep).getString(Constants.DESCRIPTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getVideo() {
        try {
            JSONArray ingredients = jsonArray.getJSONObject(Constants.currentElement).getJSONArray(Constants.STEPS);
            return ingredients.getJSONObject(Constants.currentStep).getString(Constants.VIDEO_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getIngredients(int current) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONArray ingredients = jsonArray.getJSONObject(current).getJSONArray(Constants.STEPS);
            for(int i = 0; i < ingredients.length(); i++) {
                arrayList.add(ingredients.getJSONObject(i).getString(Constants.SHORT_DESCRIPTION));
            }

            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



    public StringBuilder getJsonIngredients(int current) {
        StringBuilder s = new StringBuilder();
        try {
            JSONArray ingredients = jsonArray.getJSONObject(current).getJSONArray(Constants.INGREDIENTS);
            for(int i = 0; i < ingredients.length(); i++) {
                s.append("\n" + ingredients.getJSONObject(i).getString(Constants.INGREDIENT) + ": " +
                        ingredients.getJSONObject(i).getString(Constants.QUANTITY) + " " +
                        ingredients.getJSONObject(i).getString(Constants.MEASURE));
            }
            Log.i(Constants.TAG, "+" +  ingredients);

            return s;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getJsonLenght() {
        return  jsonArray.length();
    }

    public ArrayList<String> getAllNames() {
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList.add(jsonObject.optString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return arrayList;
    }
}
