package com.tiredpapi.baking.fragmetnsex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tiredpapi.baking.bakerapp.R;
import com.tiredpapi.baking.fragmetnsex.fragments.ImageFragment;
import com.tiredpapi.baking.fragmetnsex.fragments.MasterFragment;
import com.tiredpapi.baking.fragmetnsex.utils.Constants;

public class MainActivity extends AppCompatActivity implements MasterFragment.OnImageClickListener {
    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.linaer_me) != null) {
            mTwoPane = true;

            ImageFragment imageFragment1 = new ImageFragment();
            imageFragment1.setImageId(0);
            ImageFragment imageFragment2 = new ImageFragment();
            imageFragment2.setImageId(1);
            ImageFragment imageFragment3 = new ImageFragment();
            imageFragment3.setImageId(0);

            FragmentManager fragmentManager = getSupportFragmentManager();

            Log.i(Constants.TAG, "Not");

            fragmentManager.beginTransaction().add(R.id.image_container, imageFragment1).commit();
            fragmentManager.beginTransaction().add(R.id.image_container1, imageFragment2).commit();
            fragmentManager.beginTransaction().add(R.id.image_container2, imageFragment3).commit();
        } else {
            mTwoPane = false;
        }

    }

    @Override
    public void OnImageSelected(int position) {
        Toast.makeText(this, "CLICKED: " + position, Toast.LENGTH_LONG).show();

        Intent i = new Intent(this, MainActivityMe.class);
        startActivity(i);
    }
}
