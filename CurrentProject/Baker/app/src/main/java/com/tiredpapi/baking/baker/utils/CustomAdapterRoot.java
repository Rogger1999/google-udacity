package com.tiredpapi.baking.baker.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiredpapi.baking.baker.R;

import java.util.ArrayList;

public class CustomAdapterRoot extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> gridViewString;

    public CustomAdapterRoot(Context context, ArrayList<String> gridViewString) {
        mContext = context;
        this.gridViewString = gridViewString;
    }

    @Override
    public int getCount() {
        return gridViewString.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridView;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridView = new View(mContext);
            gridView = inflater.inflate(R.layout.custom_grid_view, null);
            TextView textViewAndroid = (TextView) gridView.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) gridView.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(gridViewString.get(i));
            imageViewAndroid.setImageResource(R.drawable.ic_cake_black_24dp);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}