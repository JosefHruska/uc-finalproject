package com.example.android.studyproject.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Pepa on 13.03.2016.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_MOVIES = "movies";
    public static final String PATH_FAVOURITE = "favourite";





    public static final class FavouriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;

        // Table name
        public static final String TABLE_NAME = "favourite";

       // MID = ID which is used on TMDB
        public static final String COLUMN_MID = "favourite_id";

        //Favourite defines if the movie is point as a favourite by an user.
        public static final String COLUMN_FAVOURITE = "favourite_favourite";

        // Short description of the main plot of the movie.
        public static final String COLUMN_PLOT = "plot";

        // Average rating of the movie from TMDB
        public static final String COLUMN_RATING = "rating";


        // Poster which will be showed in a main activity
        public static final String COLUMN_IMG_MENU = "img_menu";

        // Poster which will be showed in a detail activity
        public static final String COLUMN_IMG_DETAIL = "img_detail";


        // String which is equal to date of release in US.
        public static final String COLUMN_RELEASE = "release";

        // URL of video (trailer, spot) connected to this movie
        public static final String COLUMN_VIDEO = "video";

        // Name of movie
        public static final String COLUMN_TITLE = "title";

        public static Uri buildSourceUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents of the weather table */
    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        //Table name
        public static final String TABLE_NAME = "movies";

        //TODO Later we will probably need to add foreign key.




        // MID = ID which is used on TMDB
        public static final String COLUMN_MID = "movie_id";

        // Short description of the main plot of the movie.
        public static final String COLUMN_PLOT = "plot";

        // Average rating of the movie from TMDB
        public static final String COLUMN_RATING = "rating";


        // Poster which will be showed in a main activity
        public static final String COLUMN_IMG_MENU = "img_menu";

        // Poster which will be showed in a detail activity
        public static final String COLUMN_IMG_DETAIL = "img_detail";


        // String which is equal to date of release in US.
        public static final String COLUMN_RELEASE = "release";

        // URL of video (trailer, spot) connected to this movie
        public static final String COLUMN_VIDEO = "video";

        // Name of movie
        public static final String COLUMN_TITLE = "title";

        public static Uri buildMovierUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildWeatherLocation(String sourceSetting) {
            return CONTENT_URI.buildUpon().appendPath(sourceSetting).build();
        }

//        public static Uri buildWeatherLocationWithStartDate(
//                String locationSetting, long startDate) {
//            long normalizedDate = normalizeDate(startDate);
//            return CONTENT_URI.buildUpon().appendPath(locationSetting)
//                    .appendQueryParameter(COLUMN_DATE, Long.toString(normalizedDate)).build();
//        }
//
//        public static Uri buildWeatherLocationWithDate(String locationSetting, long date) {
//            return CONTENT_URI.buildUpon().appendPath(locationSetting)
//                    .appendPath(Long.toString(normalizeDate(date))).build();
//        }

//        public static String getLocationSettingFromUri(Uri uri) {
//            return uri.getPathSegments().get(1);
//        }
//
//        public static long getDateFromUri(Uri uri) {
//            return Long.parseLong(uri.getPathSegments().get(2));
//        }
//
//        public static long getStartDateFromUri(Uri uri) {
//            String dateString = uri.getQueryParameter(COLUMN_DATE);
//            if (null != dateString && dateString.length() > 0)
//                return Long.parseLong(dateString);
//            else
//                return 0;
//        }
    }
}