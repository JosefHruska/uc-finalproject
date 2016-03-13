package com.example.android.studyproject.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by Pepa on 13.03.2016.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_MOVIES = "weather";
    public static final String PATH_SOURCE = "source";


    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }


    public static final class SourceEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SOURCE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SOURCE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SOURCE;

        // Table name
        public static final String TABLE_NAME = "source";

        // The location setting string is what will be sent to openweathermap
        // as the location query.
        public static final String COLUMN_SOURCE_SETTING = "source_setting";

        // Human readable location string, provided by the API.  Because for styling,
        // "Mountain View" is more recognizable than 94043.
        public static final String COLUMN_SOURCE_NAME = "source_name";

        // In order to uniquely pinpoint the location on the map when we launch the
        // map intent, we store the latitude and longitude as returned by openweathermap.
        //  public static final String COLUMN_COORD_LAT = "coord_lat";
        // public static final String COLUMN_COORD_LONG = "coord_long";

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

        public static final String TABLE_NAME = "movies";

        // Column with the foreign key into the location table.
        public static final String COLUMN_SOURCE_KEY = "source_id";
        // Date, stored as long in milliseconds since the epoch
        // public static final String COLUMN_DATE = "date";


        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_MOVIE_ID = "movie_id";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_PLOT = "plot";

        // Min and max temperatures for the day (stored as floats)
        public static final String COLUMN_RATING = "rating";


        // Humidity is stored as a float representing percentage
        public static final String COLUMN_IMG_MENU = "img_menu";

        // Humidity is stored as a float representing percentage
        public static final String COLUMN_IMG_DETAIL = "img_detail";

        // It is 1 if it was marked as favourite, otherwise it's equal to 0
        public static final String COLUMN_FAVOURITE = "favourite";

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