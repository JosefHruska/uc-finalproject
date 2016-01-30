package com.example.android.studyproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayAdapter<String> defaultAdapter;
    String[] posters;
    // Just a temporary var - it will be set by user in future
    public int countOfMovies = 6;


    private GridAdapterView gridAdapter;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] data = {
                "Two", "Top", "Policemen", "Met", "On", "The", "Line",
                "Two", "Top", "Policemen", "Met", "On", "The", "Line",
                "Two", "Top", "Policemen", "Met", "On", "The", "Line",
                "Two", "Top", "Policemen", "Met", "On", "The", "Line"
        };

        List<String> placeholderData = new ArrayList<String>(Arrays.asList(data));

       gridAdapter = new GridAdapterView(getActivity(), posters);
//        defaultAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                R.layout.basic_layout,
//                R.id.basic_view,
//                placeholderData
//                );

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(gridAdapter);
        FetchMovieDatabase movieTask = new FetchMovieDatabase();
        movieTask.execute();
//        gridView.setAdapter(gridAdapter);


        return rootView;
    }

    public class FetchMovieDatabase extends AsyncTask<Void, Void, String> {

        private final String LOG_TAG =  FetchMovieDatabase.class.getSimpleName();


        private String[] getPostersFromJson(int countOfMovies, String moviesJsonStr)
            throws JSONException {

            String[] posterArray = new String[countOfMovies];

            // These are the names of the JSON objects that need to be extracted.
            final String POSTER = "poster_path";
            final String RESULTS = "results";

            JSONObject movieJson = new JSONObject(moviesJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(RESULTS);

            for (int i = 0; i < countOfMovies; i++ )
            {
                /// Data which we want to get.
                String poster;

                /// Get the JSON object representing the movie
                JSONObject theMovie = movieArray.getJSONObject(i);

                poster = theMovie.getString(POSTER);
                /// Check if poster string get correct data
                Log.d(LOG_TAG,poster);
                posterArray[i] = "http://image.tmdb.org/t/p/w185/" + poster;
            }

            return posterArray;
        }

        @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {
            String baseUrl = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=";
            String api = "433e57f96e89ea06704dd7bca2f88048";
            URL url = new URL(baseUrl.concat(api));

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
                moviesJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                Log.d(LOG_TAG,"output" + moviesJsonStr);
                try {
                  posters = getPostersFromJson(countOfMovies,moviesJsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            return null;
        }

        @Override
        protected void onPostExecute(String posterArray)
        {

        }

    }
}