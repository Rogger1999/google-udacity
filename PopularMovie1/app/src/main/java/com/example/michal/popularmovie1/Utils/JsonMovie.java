package com.example.michal.popularmovie1.Utils;

import org.json.JSONObject;

/**
 * Created by michal on 4/8/18.
 */

public class JsonMovie {
    private JSONObject movieJson;

    public JsonMovie(JSONObject jsonObject) {
        movieJson = jsonObject;
    }

    public String getOriginalTitle() {
        return movieJson.optString(Constants.ORIGINAL_TITLE);
    }

    public String getPostPath() {
        return movieJson.optString(Constants.POSTER_PATH);
    }

    public String getOverview() {
        return movieJson.optString(Constants.OVERVIEW);
    }

    public String getVoteAvarage() {
        return movieJson.optString(Constants.VOTE_AVARAGE);
    }

    public String getReleaseDate() {
        return movieJson.optString(Constants.RELEASE_DATE);
    }
}
