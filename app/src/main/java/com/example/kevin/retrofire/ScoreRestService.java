package com.example.kevin.retrofire;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;


public class ScoreRestService {
    private String url = "http://192.168.0.18:5002/";
    private String res ="";

    public ArrayList<String> getHighScoreList() throws ExecutionException, InterruptedException {
        ArrayList<String> scores = new ArrayList<>();

        res = new HttpAsyncTaskGet().execute(url).get();

        try {
            JSONArray items = new JSONArray(res);
            for (int i = 0; i < items.length(); i++) {
                scores.add(items.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Collections.sort(scores, (s1, s2) -> {
            int x = Integer.parseInt(s1.split(" ")[1].replaceAll("\n", ""));
            int y = Integer.parseInt(s2.split(" ")[1].replaceAll("\n", ""));
            return y-x;
        });

        return scores;
    }

    private class HttpAsyncTaskGet extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return httpGet();
        }

        @Override
        protected void onPostExecute(String result) {
            res = result;
        }
    }

    private String httpGet(){
        InputStream inputStream;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "error";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line;
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public void sendScore(String username, String score) throws ExecutionException, InterruptedException {
        res = new HttpAsyncTaskPost(username, score).execute(url).get();
    }

    private class HttpAsyncTaskPost extends AsyncTask<String, Void, String> {
        private String score;
        private String username;

        HttpAsyncTaskPost(String username, String score){
            this.score = score;
            this.username = username;
        }

        @Override
        protected String doInBackground(String... urls) {
            httpPost(username, score);
            return "";
        }
    }

    private void httpPost(String username, String  score){
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("username", username);
            jsonObject.accumulate("score", score);
            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpclient.execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
