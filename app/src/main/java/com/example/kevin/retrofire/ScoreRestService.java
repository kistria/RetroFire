package com.example.kevin.retrofire;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import java.util.Comparator;

public class ScoreRestService {
    private String url = "";

    public String requestContent() {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse response;
        InputStream instream = null;

        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {

        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        return sb.toString();
    }

    public ArrayList<String> getHighScoreList(){
        ArrayList<String> scores = new ArrayList<>();
        String res = requestContent();

        try {
            JSONObject json = new JSONObject(res);
            JSONArray items = json.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                scores.add(items.getString(i));
            }

        } catch (JSONException e) {
        }

        Collections.sort(scores, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int x = Integer.parseInt(s1.split(" ")[1].replaceAll("\n", ""));
                int y = Integer.parseInt(s2.split(" ")[1].replaceAll("\n", ""));
                return x - y;
            }
        });

        return scores;
    }
}
