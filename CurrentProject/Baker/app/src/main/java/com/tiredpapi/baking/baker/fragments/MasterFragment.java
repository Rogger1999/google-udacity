package com.tiredpapi.baking.baker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.tiredpapi.baking.baker.R;
import com.tiredpapi.baking.baker.utils.Constants;
import com.tiredpapi.baking.baker.utils.CustomAdapter;

import org.json.JSONArray;

public class MasterFragment extends Fragment {
    private JSONArray jsonArray;
    View view;
    GridView gridView;
    OnItemListener callBack;
    FragmentActivity fragmentActivity;

    public interface OnItemListener {
        void OnItemSelected(int position);
    }


    public MasterFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        fragmentActivity = (FragmentActivity) context;

        try {
            callBack = (OnItemListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "implement");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.master_fragment, container, false);
        gridView = view.findViewById(R.id.master_fragment_gridview);

        CustomAdapter customAdapter = new CustomAdapter(getContext(), Constants.jsonUtils.getIngredients(Constants.currentElement));
        gridView.setAdapter(customAdapter);


        if(getResources().getConfiguration().orientation == 2) {
            DetailFragment detailFragment = new DetailFragment();
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, detailFragment).commit();
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Constants.currentStep = position;
                callBack.OnItemSelected(Constants.currentStep);
            }
        });

        return view;
    }
}
