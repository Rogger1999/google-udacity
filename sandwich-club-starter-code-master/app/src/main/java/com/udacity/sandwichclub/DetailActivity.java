package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich;

    @BindView(R.id.image_iv) ImageView ingredientsIv;
    @BindView(R.id.also_know_tv) TextView alsoKnowTV;
    @BindView(R.id.origin_tv) TextView placeOriginTV;
    @BindView(R.id.description_tv) TextView descriptionTV;
    @BindView(R.id.ingredients_tv) TextView incredientsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        alsoKnowTV.setText(sandwich.getAlsoKnownAs().toString()
                .replace("[","")
                .replace("]", "")
                .replace(",", ",  "));
        placeOriginTV.setText(sandwich.getPlaceOfOrigin().toString());
        descriptionTV.setText(sandwich.getDescription().toString());
        incredientsTV.setText(sandwich.getIngredients().toString()
                .replace("]","")
                .replace("[", "")
                .replace(",", ",  "));
    }
}
