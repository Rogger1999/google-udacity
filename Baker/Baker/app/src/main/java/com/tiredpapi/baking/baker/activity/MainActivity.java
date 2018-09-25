package com.tiredpapi.baking.baker.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.tiredpapi.baking.baker.R;
import com.tiredpapi.baking.baker.fragments.DetailFragment;
import com.tiredpapi.baking.baker.fragments.MasterFragment;
import com.tiredpapi.baking.baker.utils.Constants;
import com.tiredpapi.baking.baker.utils.RecipeAsyncTask;


public class MainActivity extends AppCompatActivity implements MasterFragment.OnItemListener {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void OnItemSelected(int position) {
        if(findViewById(R.id.activity_main_single) != null ) {
            Intent i = new Intent(this, DetailActivity.class);
            startActivity(i);
        } else {
            DetailFragment detailFragment = new DetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, detailFragment).commit();
        }
    }
}
