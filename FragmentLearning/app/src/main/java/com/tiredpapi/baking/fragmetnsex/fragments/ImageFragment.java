package com.tiredpapi.baking.fragmetnsex.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tiredpapi.baking.bakerapp.R;
import com.tiredpapi.baking.fragmetnsex.data.ImageData;

public class ImageFragment extends Fragment{

    private int i;

    public ImageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        //inflate the layout
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);

        ImageView imageView = rootView.findViewById(R.id.fragment_iv);

        imageView.setImageResource(ImageData.getImages().get(i));

        return rootView;
    }

    public void setImageId(int i) {
        this.i = i;
    }

}
