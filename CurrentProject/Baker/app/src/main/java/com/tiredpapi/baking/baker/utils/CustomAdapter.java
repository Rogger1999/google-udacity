package com.tiredpapi.baking.baker.utils;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiredpapi.baking.baker.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> gridViewString;

    public CustomAdapter(Context context, ArrayList<String> gridViewString) {
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
            gridView = inflater.inflate(R.layout.custom, null);
            gridView.setBackgroundColor(Color.parseColor("#C1CAC2"));
            TextView textViewAndroid = (TextView) gridView.findViewById(R.id.android_gridview_text);
            textViewAndroid.setText(gridViewString.get(i));
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}