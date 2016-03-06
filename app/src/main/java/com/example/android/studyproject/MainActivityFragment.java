package com.example.android.studyproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


    int duration = Toast.LENGTH_SHORT;
    static String[][] filmy;


    static String[] posters = {"http://image.tmdb.org/t/p/w780///jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","http://image.tmdb.org/t/p/w780///jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","http://image.tmdb.org/t/p/w780///jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","http://image.tmdb.org/t/p/w780///jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","http://image.tmdb.org/t/p/w780///jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","http://image.tmdb.org/t/p/w780///jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","http://image.tmdb.org/t/p/w780///jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","http://image.tmdb.org/t/p/w780///jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","http://image.tmdb.org/t/p/w780///jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","http://image.tmdb.org/t/p/w780///jjBgi2r5cRt36xF6iNUEhzscEcb.jpg"};
    // Just a temporary var - it will be set by user in future
    public int countOfMovies = 10;

    public static void setFilmy(String[][] filmy) {
        MainActivityFragment.filmy = filmy;
    }
    public static void setPosters(String[] posters) {
        MainActivityFragment.posters = posters;
    }

    public MainActivityFragment() {
    }



    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_refresh) {
            updateMovies();
            return true;
        }

        if (id == R.id.action_cinema_map) {
            showMap();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMap() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:0,0?q=Cinestar"));
        startActivity(intent);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Just a placeholder which is used to fill up list of movies.
        String[] data = {
                "Movie #1", "Movie #2", "Movie #3", "Movie #4", "Movie #5", "Movie #6",
                "Movie #7", "Movie #8", "Movie #9", "Movie #10", "Movie #11"};

        List<String> placeholderData = new ArrayList<String>(Arrays.asList(data));

        defaultAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.basic_layout,
                R.id.basic_view,
                placeholderData
                );

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


       updateMovies();

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridView gridView = (GridView) getActivity().findViewById(R.id.grid_view);
       // gridView.setAdapter(defaultAdapter);
        gridView.setAdapter(new GridViewAdapter(getContext(),posters));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String movie = defaultAdapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putStringArray("key", new String[]{filmy[position][0],filmy[position][1],filmy[position][2],filmy[position][3],filmy[position][4]});

                Intent intent = new Intent(getActivity(), movieDetailActivity.class);
                //intent.putExtra(Intent.EXTRA_TEXT, filmy[position]);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast toast = Toast.makeText(getActivity(), filmy[position][0], duration);
                toast.show();
            }
        });
    }

    public void updateMovies()
    {
        FetchMovieDatabase movieTask = new FetchMovieDatabase();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sorting = prefs.getString(getString(R.string.pref_sorting),
                getString(R.string.pref_sorting_default));
        movieTask.execute(sorting);
    }

    public class FetchMovieDatabase extends AsyncTask<String, Void, String> {

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
            final String ID = "id";

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
                String poster_scnd; // URL of secondary poster
                String id; // ID of the movie

                /// Get the JSON object representing the movie
                JSONObject theMovie = movieArray.getJSONObject(i);

                //Saves every single information to string;
                poster = theMovie.getString(POSTER);
                release = theMovie.getString(RELEASE);
                title = theMovie.getString(TITLE);
                plot = theMovie.getString(PLOT);
                rating = theMovie.getString(RATING);
                poster_scnd = theMovie.getString(POSTER_SCND);
                id = String.valueOf(theMovie.getInt(ID));

                FetchMovieTrailer fmt = new FetchMovieTrailer();
                fmt.execute(id);

                /// Check if poster string get correct data
                Log.d(LOG_TAG,"Rating = " + rating);
                posterArray[i] = "http://image.tmdb.org/t/p/w185/" + poster;
                movieDataArray[i][0] = release;
                movieDataArray[i][1] = title;
                movieDataArray[i][2] = plot;
                movieDataArray[i][3] = rating;
                movieDataArray[i][4] = "http://image.tmdb.org/t/p/w780/" + poster_scnd;
                movieDataArray[i][5] =  id;

            }
            setFilmy(movieDataArray);



            return posterArray;


        }


        @Override
        protected String doInBackground(String... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT = "sort_by";
            final String API_KEY = "api_key";

            try {

                // Original url
                // "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=";

                // API key - it should be stored somewhere else
                String api = "433e57f96e89ea06704dd7bca2f88048";
                Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(SORT, params[0])
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
        MainActivityFragment.setPosters(posters);
            GridView gv = (GridView) getActivity().findViewById(R.id.grid_view);
            gv.setAdapter(new GridViewAdapter(getContext(),posters));        }

    }
}