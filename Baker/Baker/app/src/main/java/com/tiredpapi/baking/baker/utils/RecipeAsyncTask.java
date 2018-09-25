package com.tiredpapi.baking.baker.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tiredpapi.baking.baker.activity.MainActivity;

import org.json.JSONArray;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipeAsyncTask extends AsyncTask<Void, Void, String> {
    private OkHttpClient client;
    private Request request;
    private OnTaskCompleted taskCompleted;

    public interface OnTaskCompleted {
        void onTaskCompleted(String response);
    }

    public RecipeAsyncTask(OnTaskCompleted taskCompleted) {
        this.taskCompleted = taskCompleted;
        client = new OkHttpClient();

        try {
            request = new Request.Builder().url(Constants.URL_DATA).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
    }

    @Override
    protected void onPostExecute(String s) {
        taskCompleted.onTaskCompleted(s);
    }
}
