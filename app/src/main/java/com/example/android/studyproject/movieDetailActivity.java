package com.example.android.studyproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class movieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //Intent intent = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        String[] array = bundle.getStringArray("key");

        String datum = array[0];
        String datum = array[1;
        String datum = array[2];
        String datum = array[3];


        //String message = intent.getStringExtra(intent.EXTRA_TEXT);
        TextView viewMovieTitle = (TextView) findViewById(R.id.movie_title);
        TextView viewMovieRating = (TextView) findViewById(R.id.movie_rating);
        TextView viewMoviePlot = (TextView) findViewById(R.id.movie_plot);
        TextView viewMovieRelease = (TextView) findViewById(R.id.movie_release);


        viewMovieTitle.setText(datum);
        viewMovieRating.setText(datum)
                    viewMoviePlot.setText(datum)
        viewMovieRelease.setText(datum)

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.detail_layout);
        layout.addView(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
