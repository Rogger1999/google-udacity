package com.example.michal.popularmovie1.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.michal.popularmovie1.R;
import com.example.michal.popularmovie1.Utils.Constants;
import com.squareup.picasso.Downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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

        new DownloadMovie().execute(temp_url);
    }

    private class DownloadMovie extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls) {
            String result = "";
            URL url = null;
            HttpURLConnection urlConnection = null;

            try {
                url = urls[0];
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream stream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);
                long data = reader.read();
                Log.i(Constants.TAG, String.valueOf(data));

                while(data != -1 ) {
                    char current = (char) data;
                    result = result + current;
                    data = reader.read();
                }

                Log.i(Constants.TAG, result);

            } catch (Exception e) {
                Log.e(Constants.TAG, e.toString());
            }


            return null;
        }
    }
}
