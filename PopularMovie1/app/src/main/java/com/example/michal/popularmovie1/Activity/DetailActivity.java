package com.example.michal.popularmovie1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michal.popularmovie1.R;
import com.example.michal.popularmovie1.Utils.Constants;
import com.example.michal.popularmovie1.Utils.JsonMovie;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    private final String VOTE_AVARAGE = "Avarage vote: ";
    private final String RELEASE_DATE = "Release date: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView titleTV = findViewById(R.id.title_tv);
        ImageView posterIV = findViewById(R.id.details_iv);
        TextView overviewTV = findViewById(R.id.overview_tv);
        TextView voteTV = findViewById(R.id.vote_tv);
        TextView releaseTV = findViewById(R.id.release_tv);


        JSONObject videoDetails;
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            try {
                videoDetails = new JSONObject(extras.getString(Constants.VIDEO_DETAILS));
                JsonMovie jsonMovie = new JsonMovie(videoDetails);

                titleTV.setText(jsonMovie.getOriginalTitle());
                Picasso.with(this).load(Constants.IMAGE_PATH + jsonMovie.getPostPath()).into(posterIV);
                overviewTV.setText(jsonMovie.getOverview());
                voteTV.setText(VOTE_AVARAGE + jsonMovie.getVoteAvarage());
                releaseTV.setText(RELEASE_DATE + jsonMovie.getReleaseDate());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
