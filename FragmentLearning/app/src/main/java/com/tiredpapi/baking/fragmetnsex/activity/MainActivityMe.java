package com.tiredpapi.baking.fragmetnsex.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tiredpapi.baking.bakerapp.R;
import com.tiredpapi.baking.fragmetnsex.fragments.ImageFragment;
import com.tiredpapi.baking.fragmetnsex.utils.Constants;

public class MainActivityMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_me);

        ImageFragment imageFragment1 = new ImageFragment();
        imageFragment1.setImageId(0);
        ImageFragment imageFragment2 = new ImageFragment();
        imageFragment2.setImageId(1);
        ImageFragment imageFragment3 = new ImageFragment();
        imageFragment3.setImageId(0);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Log.i(Constants.TAG, "HERE");

        fragmentManager.beginTransaction().add(R.id.image_container, imageFragment1).commit();
        fragmentManager.beginTransaction().add(R.id.image_container1, imageFragment2).commit();
        fragmentManager.beginTransaction().add(R.id.image_container2, imageFragment3).commit();
    }
}
