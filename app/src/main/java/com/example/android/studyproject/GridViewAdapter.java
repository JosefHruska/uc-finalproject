package com.example.android.studyproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Pepa on 03.02.2016.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private String[] items;

        public GridViewAdapter(Context context, String[] posters){
            super();
            this.context = context;
            this.items = posters;
        }


        @Override
        public int getCount() {
            return items.length;
        }


        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView img = null;
            if (convertView == null) {

                img = new ImageView(context);

                convertView = img;
                img.setPadding(5, 5, 5, 5);
            } else {
                img = (ImageView) convertView;
            }



            Picasso.with(context)
                    .load(items[position])
                    .resize(200, 300)
                    .into(img);

            return convertView;
        }

}
