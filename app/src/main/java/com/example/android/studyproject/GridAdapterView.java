package com.example.android.studyproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Pepa on 30.01.2016.
 */
public class GridAdapterView extends BaseAdapter{

    private Context context;
    private String[] items;

    //Constructor de dos parámetros
    public GridAdapterView(Context context, String[] posters){
        super();
        this.context = context;
        this.items = posters;
    }

    //Obetenemos la cantidad de imágenes
    @Override
    public int getCount() {
        return items.length;
    }

    //Obtenemos el objeto a partir de su posición
    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //Generamos la vista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Declaramos el ImageView
        ImageView img = null;
        if (convertView == null) {
            //Referenciamos el ImageView
            img = new ImageView(context);
            //Referenciamos el ImageView al convertView
            convertView = img;
            img.setPadding(5, 5, 5, 5);
        } else {
            img = (ImageView) convertView;
        }


        //Context pro instanci knihovny - možná tam pasuje MainActivityFragment.this
        Picasso.with(context)
                //Cargamos la imagen sobre la que se esté iterando
                .load(items[position])
                        //Imagen por defecto usada mientras se cargan las imágenes
                //.placeholder(R.drawable.picture)
                .resize(200, 300)
                        //Se aplica sobre la imagen (ImageView - se hizo referencia a "convertView")
                .into(img);

        return convertView;
    }

}
