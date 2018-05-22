package com.example.michal.popularmovie1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class MainActivity extends AppCompatActivity {


    private StringBuilder allData;
    private GridView gridview;
    private ArrayList<String> picturesList;
    private CheckNetwork checkNetwork;

    private String spNotFound = "SharedPreferences not found";
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
                if (!(Constants.popularChoosen == 1)) {
                    if(checkNetwork.getStatus(this)) {
                        new DownloadMovie().execute(getURL(Constants.MOVIE_POPULAR));
                        Constants.popularChoosen = 1;
                    }
                    else {
                        Toast.makeText(this, Constants.NO_CONNECTION, Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            case R.id.rated_menu:
                if (!(Constants.popularChoosen == 2)) {
                    if(checkNetwork.getStatus(this)) {
                        new DownloadMovie().execute(getURL(Constants.MOVIE_RATED));
                        Constants.popularChoosen = 2;
                    }
                    else {
                        Toast.makeText(this, Constants.NO_CONNECTION, Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            case R.id.favorite_menu:
                if (!(Constants.popularChoosen == 3)) {
                    if(checkNetwork.getStatus(this)) {
                        getPictures(Constants.favoriteMovies);
                        Constants.popularChoosen = 3;
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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Constants.favoriteMovies = new JSONArray(new ArrayList<String>());

        if(preferences.getString(Constants.APP_NAME, null) != null) {
            try {
                Constants.favoriteMovies = new JSONArray(preferences.getString(Constants.APP_NAME, null));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(Constants.TAG, Constants.favoriteMovies.toString());
        }

        checkNetwork = new CheckNetwork();
        gridview = findViewById(R.id.gridview);

        if(checkNetwork.getStatus(this))
            switch (Constants.popularChoosen) {
                case 1:
                    new DownloadMovie().execute(getURL(Constants.MOVIE_POPULAR));
                    return;
                case 2:
                    new DownloadMovie().execute(getURL(Constants.MOVIE_RATED));
                    return;
                case 3:
                    getPictures(Constants.favoriteMovies);
                    return;
                default:
                    new DownloadMovie().execute(getURL(Constants.MOVIE_POPULAR));
            }

        else {
            Toast.makeText(this, Constants.NO_CONNECTION, Toast.LENGTH_LONG).show();
        }
    }

    private void getPictures(final JSONArray info) {
        try {
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
            URL url;
            HttpURLConnection urlConnection;

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
            JsonUtils jsonUtils = new JsonUtils(stringBuilder);
            final JSONArray info = jsonUtils.getJsonArrayResults();
            getPictures(info);
        }

    }
}
