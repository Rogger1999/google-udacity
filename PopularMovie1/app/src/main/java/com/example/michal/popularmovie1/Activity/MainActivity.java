package com.example.michal.popularmovie1.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.michal.popularmovie1.R;
import com.example.michal.popularmovie1.Utils.Constants;
import com.example.michal.popularmovie1.Utils.ImageAdapter;
import com.example.michal.popularmovie1.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String POSTER_PATH = "poster_path";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL temp_url = getURL(Constants.MOVIE_POPULAR);
        getPictures(temp_url);
    }

    private void getPictures(URL temp_url) {
        try {
            StringBuilder allData = new DownloadMovie().execute(temp_url).get();
            JsonUtils jsonUtils = new JsonUtils(allData);
            final JSONArray info = jsonUtils.getJsonArrayResults();

            ArrayList<String> picturesList = new ArrayList<>();

            for(int i = 0; i < info.length(); i++) {
                picturesList.add(info.getJSONObject(i).optString(POSTER_PATH));
            }

            GridView gridview = findViewById(R.id.gridview);
            gridview.setAdapter(new ImageAdapter(this, picturesList));

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

    private URL getURL(String type) {
        try {
            return new URL(Constants.URL + type + Constants.API_KEY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
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
    }
}
