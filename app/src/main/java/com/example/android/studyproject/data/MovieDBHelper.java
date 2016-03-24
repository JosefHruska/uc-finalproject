package com.example.android.studyproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.studyproject.data.MovieContract.SourceEntry;
import com.example.android.studyproject.data.MovieContract.MoviesEntry;

/**
 * Manages a local database for weather data.
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "movie.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_SOURCE_TABLE = "CREATE TABLE " + SourceEntry.TABLE_NAME + " (" +
                SourceEntry._ID + " INTEGER PRIMARY KEY," +
                SourceEntry.COLUMN_SOURCE_SETTING + " TEXT UNIQUE NOT NULL, " +
                SourceEntry.COLUMN_SOURCE_NAME + " TEXT NOT NULL " +
                //SourceEntry.COLUMN_COORD_LAT + " REAL NOT NULL, " +
                //SourceEntry.COLUMN_COORD_LONG + " REAL NOT NULL " +
                " );";
            //TODO Vrátit zpět kousek kodu
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MoviesEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                MoviesEntry.COLUMN_SOURCE_KEY + " INTEGER NOT NULL, " +
//                MoviesEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                MoviesEntry.COLUMN_PLOT + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +

                MoviesEntry.COLUMN_RATING + " REAL NOT NULL, " +
                MoviesEntry.COLUMN_IMG_DETAIL + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_IMG_MENU + " TEXT NOT NULL, " +

                MoviesEntry.COLUMN_FAVOURITE + " INTEGER NOT NULL, " +
                MoviesEntry.COLUMN_RELEASE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_VIDEO + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +

                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + MoviesEntry.COLUMN_SOURCE_KEY + ") REFERENCES " +
                SourceEntry.TABLE_NAME + " (" + SourceEntry._ID + ") ";

//                // To assure the application have just one weather entry per day
//                // per location, it's created a UNIQUE constraint with REPLACE strategy
//                " UNIQUE (" + MoviesEntry.COLUMN_DATE + ", " +
//                MoviesEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_SOURCE_TABLE);
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SourceEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}