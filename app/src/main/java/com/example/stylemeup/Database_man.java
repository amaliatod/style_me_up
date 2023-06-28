package com.example.stylemeup;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Database_man extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "man_clothing.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TOPS = "TOPS";
    private static final String TABLE_BOTTOMS = "BOTTOMS";
    private static final String TABLE_SHOES = "SHOES";
    private static final String TABLE_ACCESSORIES = "ACCESSORIES";



    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_IMAGE_URL = "image_url";
    private static final String COLUMN_DOMINANT_COLOR = "color";
    private static final String COLUMN_STYLE = "style";

    public Database_man(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTopsTable = "CREATE TABLE " + TABLE_TOPS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_DOMINANT_COLOR + " TEXT, " +
                COLUMN_STYLE + " TEXT)";
        db.execSQL(createTopsTable);

        String createBottomsTable = "CREATE TABLE " + TABLE_BOTTOMS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT," +
                COLUMN_DOMINANT_COLOR + " TEXT," +
                COLUMN_STYLE + " TEXT)";
        db.execSQL(createBottomsTable);

        String createShoesTable = "CREATE TABLE " + TABLE_SHOES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT," +
                COLUMN_DOMINANT_COLOR + " TEXT," +
                COLUMN_STYLE + " TEXT)";
        db.execSQL(createShoesTable);

        String createAccessoriesTable = "CREATE TABLE " + TABLE_ACCESSORIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT," +
                COLUMN_DOMINANT_COLOR + " TEXT," +
                COLUMN_STYLE + " TEXT)";
        db.execSQL(createAccessoriesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOTTOMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCESSORIES);


        onCreate(db);
    }

    public void insertInMenDb(String category, String imageUrl, String dominantColor, String style) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_IMAGE_URL, imageUrl);
        values.put(COLUMN_DOMINANT_COLOR, dominantColor);
        values.put(COLUMN_STYLE, style);
        if (category.equals("tops")) {
            db.insert(TABLE_TOPS, null, values);
        }
        if (category.equals("bottoms")) {
            db.insert(TABLE_BOTTOMS, null, values);
        }
        if (category.equals("shoes")) {
            db.insert(TABLE_SHOES, null, values);
        }
        if (category.equals("accessories")) {
            db.insert(TABLE_ACCESSORIES, null, values);
        }

    }

    public void deleteDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TOPS, null, null);
        db.delete(TABLE_BOTTOMS, null, null);
        db.delete(TABLE_SHOES, null, null);
        db.delete(TABLE_ACCESSORIES, null, null);
        db.close();
    }

    public List<String> getHexCodesFromDatabase(String table_name) {
        SQLiteDatabase db = getWritableDatabase();
        List<String> hexCodes = new ArrayList<>();


        Cursor tableCursor = db.query(table_name, new String[]{COLUMN_DOMINANT_COLOR}, null, null, null, null, null);
        if (tableCursor.moveToFirst()) {
            int colorColumnIndex = tableCursor.getColumnIndex(COLUMN_DOMINANT_COLOR);
            do {
                String hexCode = tableCursor.getString(colorColumnIndex);
                hexCodes.add(hexCode);
            } while (tableCursor.moveToNext());
        }
        tableCursor.close();

        return hexCodes;

    }

    public List<String> getBrightColorsList(String table_name) {
        List<String> brightColors = new ArrayList<>();
        List<String> colors = this.getHexCodesFromDatabase(table_name);


        for (String color : colors) {
            if (isBrightColor(color)) {
                if(!(brightColors.contains(color))) {
                    brightColors.add(color);
                }
            }
        }
        return brightColors;
    }

    public List<String> getNeutralColorsList(String table_name) {
        List<String> neutralColors = new ArrayList<>();
        List<String> colors = this.getHexCodesFromDatabase(table_name);

        for (String color : colors) {
            if (!(isBrightColor(color))) {
                if (!(neutralColors.contains(color))) {
                    neutralColors.add(color);
                }
            }
        }
        return neutralColors;
    }



    private static boolean isBrightColor(String color) {

        int r = Integer.parseInt(color.substring(1, 3), 16);
        int g = Integer.parseInt(color.substring(3, 5), 16);
        int b = Integer.parseInt(color.substring(5, 7), 16);

        // Determine the threshold values for brightness and saturation to classify as a bright color
        int brightnessThreshold = 200;
        int saturationThreshold = 90;

        int max = Math.max(r, Math.max(g, b));
        int min = Math.min(r, Math.min(g, b));
        int brightness = (max + min) / 2;
        int saturation;

        if (max == 0) {
            saturation = 0;
        } else {
            saturation = (int) (((double) (Math.max(max, 255 - max)) / Math.max(max, 255 - max)) * (Math.max(max, 255 - max) - Math.min(min, 255 - min)) / 255 * 100);

        }

        return brightness >= brightnessThreshold && saturation >= saturationThreshold;
    }

    public String getRandomImageUrl(String tableName, String selectedStyle, String selectedColor) {
        List<String> brightColors = getBrightColorsList(tableName);
        List<String> neutralColors = getNeutralColorsList(tableName);

        String imageUrl = null;
        boolean foundImageUrl = false;

        while (!foundImageUrl) {
            if (selectedColor.equals("Neutral")) {
                if (neutralColors.size() > 0) {
                    String randomNeutralColor = neutralColors.get(new Random().nextInt(neutralColors.size()));
                    imageUrl = executeQueryInBackground(tableName, selectedStyle, randomNeutralColor);
                } else {
                    // Handle the case when there are no neutral colors available
                    imageUrl = null;
                }
            } else if (selectedColor.equals("Colorful")) {
                if (brightColors.size() > 0) {
                    String randomBrightColor = brightColors.get(new Random().nextInt(brightColors.size()));
                    imageUrl = executeQueryInBackground(tableName, selectedStyle, randomBrightColor);
                } else {
                    // Handle the case when there are no bright colors available
                    imageUrl = null;
                }
            } else {
                // Handle the case when selectedColor is neither "Neutral" nor "Bright"
                imageUrl = null;
            }

            if (imageUrl != null) {
                foundImageUrl = true;
            }
        }

        return imageUrl;
    }


    private String executeQueryInBackground(String tableName, String selectedStyle, String selectedColor) {
        AsyncTask<Void, Void, String> queryTask = new AsyncTask<Void, Void, String>() {
            @SuppressLint("Range")
            @Override
            protected String doInBackground(Void... voids) {
                SQLiteDatabase db = getWritableDatabase();
                Cursor cursor = null;
                try {
                    cursor = db.rawQuery("SELECT image_url FROM " + tableName +
                                    " WHERE style = ? AND color = ? ORDER BY RANDOM() LIMIT 1",
                            new String[]{selectedStyle.toLowerCase(), selectedColor});
                    if (cursor != null && cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex("image_url"));
                    }
                    return null;
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        };

        try {
            return queryTask.execute().get(); // Execute the query task and wait for the result
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean isDatabaseEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query the "tops" table
            String queryTops = "SELECT * FROM tops LIMIT 1";
            cursor = db.rawQuery(queryTops, null);
            if (cursor.moveToFirst()) {
                return false; // Tops table is not empty
            }

            // Query the "bottoms" table
            String queryBottoms = "SELECT * FROM bottoms LIMIT 1";
            cursor = db.rawQuery(queryBottoms, null);
            if (cursor.moveToFirst()) {
                return false; // Bottoms table is not empty
            }

            // Query the "shoes" table
            String queryShoes = "SELECT * FROM shoes LIMIT 1";
            cursor = db.rawQuery(queryShoes, null);
            if (cursor.moveToFirst()) {
                return false; // Shoes table is not empty
            }

            // All tables are empty
            return true;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
