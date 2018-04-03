package com.example.michal.popularmovie1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.michal.popularmovie1.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String s;
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            s = extras.getString("Test");
            TextView textView = findViewById(R.id.textView);
            textView.setText(s);
        }
    }
}
