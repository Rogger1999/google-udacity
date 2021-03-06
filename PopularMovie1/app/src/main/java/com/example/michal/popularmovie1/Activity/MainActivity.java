package com.example.michal.popularmovie1.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.michal.popularmovie1.R;
import com.example.michal.popularmovie1.Utils.CheckNetwork;
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

    private boolean popularChoosen = true;
    private StringBuilder allData;
    private GridView gridview;
    private ArrayList<String> picturesList;
    private CheckNetwork checkNetwork;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular_menu:
                if (popularChoosen == false) {
                    if(checkNetwork.getStatus(this)) {
                        new DownloadMovie().execute(getURL(Constants.MOVIE_POPULAR));
                        popularChoosen = true;
                    }
                    else {
                        Toast.makeText(this, Constants.NO_CONNECTION, Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            case R.id.rated_menu:
                if (popularChoosen == true) {
                    if(checkNetwork.getStatus(this)) {
                        new DownloadMovie().execute(getURL(Constants.MOVIE_RATED));
                        popularChoosen = false;
                    }
                    else {
                        Toast.makeText(this, Constants.NO_CONNECTION, Toast.LENGTH_LONG).show();
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetwork = new CheckNetwork();

        gridview = findViewById(R.id.gridview);

        if(checkNetwork.getStatus(this))
            new DownloadMovie().execute(getURL(Constants.MOVIE_POPULAR));
        else {
            Toast.makeText(this, Constants.NO_CONNECTION, Toast.LENGTH_LONG).show();
        }
    }

    private void getPictures(StringBuilder stringBuilder) {
        try {
            JsonUtils jsonUtils = new JsonUtils(stringBuilder);
            final JSONArray info = jsonUtils.getJsonArrayResults();

            picturesList = new ArrayList<>();

            for(int i = 0; i < info.length(); i++) {
                picturesList.add(info.getJSONObject(i).optString(Constants.POSTER_PATH));
            }

            gridview.setAdapter(new ImageAdapter(this, picturesList));

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), DetailActivity.class);
                    try {
                        intent.putExtra(Constants.VIDEO_DETAILS, info.getJSONObject(position).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });

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

        @Override
        protected void onPostExecute(StringBuilder stringBuilder) {
            getPictures(stringBuilder);
        }

    }
}
