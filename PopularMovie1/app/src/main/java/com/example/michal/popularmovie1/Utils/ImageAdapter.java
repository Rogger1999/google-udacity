package com.example.michal.popularmovie1.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.michal.popularmovie1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by michal on 4/3/18.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> arrayList;

    public ImageAdapter(Context c, ArrayList<String> al) {
        mContext = c;
        arrayList = al;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342" + arrayList.get(position)).into(imageView);
        return imageView;
    }

}