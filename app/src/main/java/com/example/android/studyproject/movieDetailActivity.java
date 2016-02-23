package com.example.android.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class movieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //Intent intent = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        String[] array = bundle.getStringArray("key");

        String release = array[0];
        String title = array[1];
        String plot = array[2];
        String rating = array[3];
        String poster_scnd = array[4];




        //String message = intent.getStringExtra(intent.EXTRA_TEXT);
        TextView viewMovieTitle = (TextView) findViewById(R.id.movie_title);
        TextView viewMovieRating = (TextView) findViewById(R.id.movie_rating);
        TextView viewMoviePlot = (TextView) findViewById(R.id.movie_plot);
        TextView viewMovieRelease = (TextView) findViewById(R.id.movie_release);
        ImageView viewMoviePosterScnd = (ImageView) findViewById(R.id.movie_poster_scnd);

        Picasso.with(this).load(poster_scnd).fit().into(viewMoviePosterScnd);

        viewMovieTitle.setText(title);
        viewMovieRating.setText(rating);
        viewMoviePlot.setText(plot);
        viewMovieRelease.setText(release);

//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.detail_layout);
//        layout.addView(textView);
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
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
