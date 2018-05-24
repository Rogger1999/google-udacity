package com.tiredpapi.baking.fragmetnsex.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tiredpapi.baking.bakerapp.R;
import com.tiredpapi.baking.fragmetnsex.data.ImageData;
import com.tiredpapi.baking.fragmetnsex.data.MasterListAdapter;

public class MasterFragment extends Fragment {
    OnImageClickListener callBack;

    public interface OnImageClickListener {
        void OnImageSelected(int position);
    }

    public MasterFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callBack = (OnImageClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_master, container, false);

        GridView gridView = view.findViewById(R.id.images_grid_view);
        MasterListAdapter masterListAdapter = new MasterListAdapter(getContext(), ImageData.getAll());

        gridView.setAdapter(masterListAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callBack.OnImageSelected(position);
            }
        });

        return view;

    }
}
