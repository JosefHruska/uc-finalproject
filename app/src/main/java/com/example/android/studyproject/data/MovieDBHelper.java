package com.example.android.studyproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.studyproject.data.MovieContract.FavouriteEntry;
import com.example.android.studyproject.data.MovieContract.MoviesEntry;

/**
 * Manages a local database for weather data.
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    // The name of the database
    static final String DATABASE_NAME = "movie.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE " + FavouriteEntry.TABLE_NAME + " (" +
                FavouriteEntry._ID + " INTEGER PRIMARY KEY," +
                FavouriteEntry.COLUMN_MID + " INTEGER UNIQUE NOT NULL, " +
                FavouriteEntry.COLUMN_FAV + " INTEGER NOT NULL, " +
                FavouriteEntry.COLUMN_PLOT + " TEXT, " +
                FavouriteEntry.COLUMN_RATING + " REAL NOT NULL, " +
                FavouriteEntry.COLUMN_IMG_MENU + " TEXT, " +
                FavouriteEntry.COLUMN_IMG_DETAIL + " TEXT , " +
                FavouriteEntry.COLUMN_RELEASE + " TEXT NOT NULL, " +
                FavouriteEntry.COLUMN_VIDEO + " TEXT, " +
                FavouriteEntry.COLUMN_TITLE + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MoviesEntry.TABLE_NAME + " (" +

                FavouriteEntry._ID + " INTEGER PRIMARY KEY," +
                FavouriteEntry.COLUMN_MID + " INTEGER UNIQUE NOT NULL, " +
                FavouriteEntry.COLUMN_PLOT + " TEXT, " +
                FavouriteEntry.COLUMN_RATING + " REAL NOT NULL, " +
                FavouriteEntry.COLUMN_IMG_MENU + " TEXT, " +
                FavouriteEntry.COLUMN_IMG_DETAIL + " TEXT , " +
                FavouriteEntry.COLUMN_RELEASE + " TEXT NOT NULL, " +
                FavouriteEntry.COLUMN_VIDEO + " TEXT, " +
                FavouriteEntry.COLUMN_TITLE + " TEXT NOT NULL " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}