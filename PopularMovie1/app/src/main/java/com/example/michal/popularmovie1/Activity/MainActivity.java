package com.example.michal.popularmovie1.Activity;

import android.content.Intent;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
            final JSONArray info = jsonUtils.getJsonArray();

            ArrayList<String> arrayList = new ArrayList<>();

            for(int i = 0; i < info.length(); i++) {
                arrayList.add(info.getJSONObject(i).optString("poster_path"));
            }


            //String s = "http://image.tmdb.org/t/p/w185" + info.getJSONObject(1).optString("poster_path");

            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new ImageAdapter(this, arrayList));

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Toast.makeText(MainActivity.this, "Test" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), DetailActivity.class);
                    try {
                        intent.putExtra("Test", info.getJSONObject(position).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });

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
