package com.example.michal.popularmovie1.Utils;

import org.json.JSONArray;

/**
 * Created by michal on 3/25/18.
 */

public class Constants {
    public static final String TAG = "Movie";
    public static final String APP_NAME = "COM.EXAMPLE.MICHAL.POPULARMOVIE1";

    public static final String URL = "http://api.themoviedb.org/3";
    public static final String API_KEY = "?api_key=your_key";

    public static final String MOVIE_POPULAR = "/movie/popular";
    public static final String MOVIE_RATED = "/movie/top_rated";
    public static final String MOVIE_VIDEO = "/movie/{id}/videos";
    public static final String MOVIE_REVIEW = "/movie/{id}/reviews";
    public static final String IMAGE_PATH = "http://image.tmdb.org/t/p/w342";
    public static final String RESULTS = "results";
    public static final String VIDEO_DETAILS = "video_details";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVARAGE = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSTER_PATH = "poster_path";
    public static final String VIDEO_ID = "id";
    public static final String VIDEO_KEY = "key";
    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";
    public static final String NO_CONNECTION = "No internet connection";

    public static int popularChoosen = 1;

    public static JSONArray favoriteMovies = new JSONArray();


}
