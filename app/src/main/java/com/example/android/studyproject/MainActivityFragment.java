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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

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

    CharSequence text = "hoj";
    int duration = Toast.LENGTH_SHORT;
    static String[][] filmy;


    String[] posters;
    // Just a temporary var - it will be set by user in future
    public int countOfMovies = 6;

    public static void setFilmy(String[][] filmy) {
        MainActivityFragment.filmy = filmy;
    }

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

        defaultAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.basic_layout,
                R.id.basic_view,
                placeholderData
                );

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(defaultAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movie = defaultAdapter.getItem(position);

                Toast toast = Toast.makeText(getActivity(), filmy[position][0], duration);
                toast.show();
            }
        });
        FetchMovieDatabase movieTask = new FetchMovieDatabase();
        movieTask.execute();

        return rootView;
    }
    public class FetchMovieDatabase extends AsyncTask<Void, Void, String> {

        private final String LOG_TAG =  FetchMovieDatabase.class.getSimpleName();


        private String[] getPostersFromJson(int countOfMovies, String moviesJsonStr)
                throws JSONException {

            String[] posterArray = new String[countOfMovies];
            String[][] movieDataArray = new String[countOfMovies][6];

            // These are the names of the JSON objects that need to be extracted.
            final String POSTER = "poster_path";
            final String RESULTS = "results";
            final String RELEASE = "release_date";
            final String TITLE = "title";
            final String PLOT = "overview";
            final String RATING = "vote_average";
            final String POSTER_SCND = "backdrop_path";

            JSONObject movieJson = new JSONObject(moviesJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(RESULTS);

            for (int i = 0; i < countOfMovies; i++ )
            {
                /// Data which we want to get.
                String poster; // Movie poster
                String release; // Date of release
                String title; // The full title of movie
                String plot; // A plot summary
                String rating; // An average user rating
                String poster_scnd;

                /// Get the JSON object representing the movie
                JSONObject theMovie = movieArray.getJSONObject(i);

                //Saves every single information to string;
                poster = theMovie.getString(POSTER);
                release = theMovie.getString(RELEASE);
                title = theMovie.getString(TITLE);
                plot = theMovie.getString(PLOT);
                rating = theMovie.getString(RATING);
                poster_scnd = theMovie.getString(POSTER_SCND);

                /// Check if poster string get correct data
                Log.d(LOG_TAG,rating);
                posterArray[i] = "http://image.tmdb.org/t/p/w185/" + poster;
                movieDataArray[i][0] = release;
                movieDataArray[i][1] = title;
                movieDataArray[i][2] = plot;
                movieDataArray[i][3] = rating;
                movieDataArray[i][4] = poster_scnd;

            }
            setFilmy(movieDataArray);



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