package com.tiredpapi.baking.baker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tiredpapi.baking.baker.R;
import com.tiredpapi.baking.baker.utils.Constants;
import com.tiredpapi.baking.baker.utils.CustomAdapterRoot;
import com.tiredpapi.baking.baker.utils.JsonUtils;
import com.tiredpapi.baking.baker.utils.RecipeAsyncTask;

public class RootActivity extends AppCompatActivity implements RecipeAsyncTask.OnTaskCompleted{
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        gridView = findViewById(R.id.root);
        RecipeAsyncTask task = new RecipeAsyncTask(RootActivity.this);
        task.execute();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Constants.currentElement = position;
                Intent i = new Intent(getApplication(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onTaskCompleted(String response) {
        Constants.jsonUtils = new JsonUtils(response);

        if(Constants.currentElement == -1)
            Constants.currentElement = 0;

        CustomAdapterRoot customAdapterRoot = new CustomAdapterRoot(this, Constants.jsonUtils.getAllNames());
        gridView.setAdapter(customAdapterRoot);
    }
}
