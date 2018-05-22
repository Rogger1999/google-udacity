package com.example.michal.popularmovie1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michal.popularmovie1.R;
import com.example.michal.popularmovie1.Utils.CheckNetwork;
import com.example.michal.popularmovie1.Utils.Constants;
import com.example.michal.popularmovie1.Utils.JsonMovie;
import com.example.michal.popularmovie1.Utils.JsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    private final String SP_SAVED = "SharePreferences saved";

    private final String VOTE_AVARAGE = "Avarage vote: ";
    private final String RELEASE_DATE = "Release date: ";
    private final String YOUTUBE_LINK = "vnd.youtube://";
    private final String ID = "{id}";
    private CheckNetwork checkNetwork;
    private JSONObject videoDetails;

    private ImageView playIV;
    private TextView notAvalaibleTV;
    private String youtubeId;
    private TextView reviewTV;
    private ImageView starIV;
    private JsonMovie jsonMovie;

    public void starOnClick(View view) {
        if(starIV.getTag().equals(1)) {
            for(int i = 0; i < Constants.favoriteMovies.length(); i++) {
                try {
                    if(Constants.favoriteMovies.getJSONObject(i).getString(Constants.VIDEO_ID).equals(jsonMovie.getVideoID())) {
                        Constants.favoriteMovies.remove(i);
                        starIV.setTag(0);
                        starIV.setImageResource(R.drawable.ic_star_border_black_48dp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Constants.favoriteMovies.put(videoDetails);
            starIV.setTag(1);
            starIV.setImageResource(R.drawable.ic_star_black_48dp);
        }

        SaveSharedPreferences();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        starIV = findViewById(R.id.star_iv);
        TextView titleTV = findViewById(R.id.title_tv);
        ImageView posterIV = findViewById(R.id.details_iv);
        TextView overviewTV = findViewById(R.id.overview_tv);
        TextView voteTV = findViewById(R.id.vote_tv);
        TextView releaseTV = findViewById(R.id.release_tv);
        playIV = findViewById(R.id.video_iv);
        notAvalaibleTV = findViewById(R.id.not_avalaible_tv);
        reviewTV = findViewById(R.id.reviews_tv);

        playIV.setVisibility(View.GONE);
        notAvalaibleTV.setVisibility(View.VISIBLE);
        checkNetwork = new CheckNetwork();


        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            try {
                videoDetails = new JSONObject(extras.getString(Constants.VIDEO_DETAILS));
                jsonMovie = new JsonMovie(videoDetails);

                for(int i = 0; i < Constants.favoriteMovies.length(); i++) {
                    try {
                        if(Constants.favoriteMovies.getJSONObject(i).getString(Constants.VIDEO_ID).equals(jsonMovie.getVideoID())) {
                            starIV.setTag(1);
                            starIV.setImageResource(R.drawable.ic_star_black_48dp);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                String movieLink = Constants.MOVIE_VIDEO.replace(ID, jsonMovie.getVideoID());
                String reviewLink = Constants.MOVIE_REVIEW.replace(ID, jsonMovie.getVideoID());

                if(checkNetwork.getStatus(this)) {
                    new DownloadVideos().execute(getURL(movieLink));
                    new DownloadVideos().execute(getURL(reviewLink));
                }
                else {
                    Toast.makeText(this, Constants.NO_CONNECTION, Toast.LENGTH_LONG).show();
                }


                titleTV.setText(jsonMovie.getOriginalTitle());
                Picasso.with(this).load(Constants.IMAGE_PATH + jsonMovie.getPostPath()).into(posterIV);
                overviewTV.setText(jsonMovie.getOverview());
                voteTV.setText(VOTE_AVARAGE + jsonMovie.getVoteAvarage());
                releaseTV.setText(RELEASE_DATE + jsonMovie.getReleaseDate());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        playIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_LINK + youtubeId));
                startActivity(intent);
            }
        });

    }

    private URL getURL(String type) {
        try {
            return new URL(Constants.URL + type + Constants.API_KEY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class DownloadVideos extends AsyncTask<URL, Integer, StringBuilder> {

        @Override
        protected StringBuilder doInBackground(URL... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = urls[0];
                Log.i(Constants.TAG, url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputString);
                }

                return stringBuilder;

            } catch (Exception e) {
                Log.e(Constants.TAG, e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(StringBuilder stringBuilder) {
            JsonUtils jsonUtils = new JsonUtils(stringBuilder);
            final JSONArray info = jsonUtils.getJsonArrayResults();

            try {
                if(!info.getJSONObject(0).optString(Constants.VIDEO_KEY).isEmpty()) {
                    notAvalaibleTV.setVisibility(View.INVISIBLE);
                    playIV.setVisibility(View.VISIBLE);
                    youtubeId = info.getJSONObject(0).optString(Constants.VIDEO_KEY);
                }

                if(!info.getJSONObject(0).optString(Constants.AUTHOR).isEmpty()) {
                    reviewTV.setText("");

                    for(int i = 0; i < info.length(); i++) {
                        Spannable author = new SpannableString(Constants.AUTHOR + ": ");
                        author.setSpan(new ForegroundColorSpan(Color.BLUE), 0, author.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        reviewTV.append(author);
                        reviewTV.append(info.getJSONObject(i).optString(Constants.AUTHOR) + "\n");
                        reviewTV.append(info.getJSONObject(i).optString(Constants.CONTENT) + "\n\n");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void SaveSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(Constants.APP_NAME);
        editor.putString(Constants.APP_NAME, Constants.favoriteMovies.toString());
        editor.apply();

        Log.i(Constants.TAG, SP_SAVED);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
