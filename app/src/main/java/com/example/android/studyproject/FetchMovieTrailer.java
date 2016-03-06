package com.example.android.studyproject;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pepa on 06.03.2016.
 */
public class FetchMovieTrailer extends AsyncTask<String,Void,String> {

    private final String LOG_TAG =  FetchMovieTrailer.class.getSimpleName();
@Override
protected String doInBackground(String... params){
        // At first we get url of movie's trailer
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        // Will contain the raw JSON response as a string.
        String trailerJsonStr = null;

        String id = params[0];

        final String BASE_URL = "http://api.themoviedb.org/3/movie/" + id + "/videos?";
        final String API_KEY = "api_key";


        try {


            // API key - it should be stored somewhere else
            final String api = "433e57f96e89ea06704dd7bca2f88048";
            Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY,api)
                    .build();

            URL url = new URL(buildUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            trailerJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            Log.d(LOG_TAG,"output" + trailerJsonStr);
//            try {
//                posters = getPostersFromJson(countOfMovies,moviesJsonStr);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    //TODO : We got the String with reponse from server and we must solve what to do with it.
    return trailerJsonStr;
}
    }

