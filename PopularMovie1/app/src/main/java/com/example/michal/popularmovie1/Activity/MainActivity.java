package com.example.michal.popularmovie1.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.michal.popularmovie1.R;
import com.example.michal.popularmovie1.Utils.Constants;
import com.example.michal.popularmovie1.Utils.ImageAdapter;
import com.example.michal.popularmovie1.Utils.JsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL temp_url = null;
        try {
            temp_url = new URL(Constants.URL + Constants.MOVIE_POPULAR + Constants.API_KEY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            StringBuilder sb = new DownloadMovie().execute(temp_url).get();
            JsonUtils jsonUtils = new JsonUtils(sb);
            JSONArray info = jsonUtils.getJsonArray();

            for(int i = 0; i < info.length(); i++) {
                Log.i(Constants.TAG, info.getJSONObject(i).optString("vote_count"));
                Log.i(Constants.TAG, info.getJSONObject(i).optString("poster_path"));
            }


            String s = "http://image.tmdb.org/t/p/w185" + info.getJSONObject(1).optString("poster_path");

            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new ImageAdapter(this));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class DownloadMovie extends AsyncTask<URL, Integer, StringBuilder> {

        @Override
        protected StringBuilder doInBackground(URL... urls) {
            String result = "";
            URL url = null;
            HttpURLConnection urlConnection = null;

            try {
                url = urls[0];
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream stream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                return builder;

            } catch (Exception e) {
                Log.e(Constants.TAG, e.toString());
            }


            return null;
        }
    }
}
