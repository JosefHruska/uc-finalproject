package com.example.android.studyproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

final class GridAdapterView extends BaseAdapter {
    private  Context context;
    List<String> urls;
    String[] strg;
    //private final List<String> urls = new ArrayList<String>();

    public GridAdapterView(Context context, String[] str) {
        this.context = context;
        strg = str;
       urls = new ArrayList<String>(Arrays.asList(str));
//        this.posters = posters;

//        // Ensure we get a different ordering of images on each run.
//        Collections.addAll(urls, Data.URLS);
//        Collections.shuffle(urls);

//        // Triple up the list.
//        ArrayList<String> copy = new ArrayList<String>(urls);
//        urls.addAll(copy);
//        urls.addAll(copy);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(context);
            view.setScaleType(CENTER_CROP);
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context) //
                .load(url) //
//                .placeholder(R.drawable.placeholder) //
//                .error(R.drawable.error) //
                .fit() //
                .tag(context) //
                .into(view);

        return view;
    }

    @Override public int getCount() {
        return urls.size();
    }

    @Override public String getItem(int position) {
        return urls.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }
}