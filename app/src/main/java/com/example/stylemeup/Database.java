package com.example.stylemeup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "woman_clothing.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_bluze = "bluze";
    private static final String TABLE_pantaloni = "pantaloni";
    private static final String TABLE_tricouri = "tricouri";
    private static final String TABLE_jeans = "jeans";
    private static final String TABLE_rochii = "rochii";
    private static final String TABLE_pulovere = "pulovere";
    private static final String TABLE_fuste = "fuste";
    private static final String TABLE_geci = "geci";


    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_IMAGE_URL = "image_url";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createBluzeTable = "CREATE TABLE " + TABLE_bluze + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createBluzeTable);

        String createPantaloniTable = "CREATE TABLE " + TABLE_pantaloni + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createPantaloniTable);

        String createTricouriTable = "CREATE TABLE " + TABLE_tricouri + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createTricouriTable);

        String createJeansTable = "CREATE TABLE " + TABLE_jeans + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createJeansTable);

        String createRochiiTable = "CREATE TABLE " + TABLE_rochii + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createRochiiTable);

        String createPulovereTable = "CREATE TABLE " + TABLE_pulovere + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createPulovereTable);

        String createFusteTable = "CREATE TABLE " + TABLE_fuste + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createFusteTable);

        String createGeciTable = "CREATE TABLE " + TABLE_geci + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createGeciTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_bluze);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_pantaloni);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_tricouri);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_jeans);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_rochii);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_pulovere);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_fuste);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_geci);

        onCreate(db);
    }

    public void insertInWomenDb(String category, String imageUrl) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_IMAGE_URL, imageUrl);
        if (category == "bluze") {
            db.insert(TABLE_bluze, null, values);
        }
        if (category == "pantaloni") {
            db.insert(TABLE_pantaloni, null, values);
        }
        if (category == "tricouri") {
            db.insert(TABLE_tricouri, null, values);
        }
        if (category == "jeans") {
            db.insert(TABLE_jeans, null, values);
        }
        if (category == "rochii") {
            db.insert(TABLE_rochii, null, values);
        }
        if (category == "pulovere") {
            db.insert(TABLE_pulovere, null, values);
        }
        if (category == "fuste") {
            db.insert(TABLE_fuste, null, values);
        }
        if (category == "geci") {
            db.insert(TABLE_geci, null, values);
        }
    }



//    public Cursor getWomenImages(String category, String subcategory) {
//        SQLiteDatabase db = getReadableDatabase();
//        String[] columns = {COLUMN_IMAGE_URL};
//        String selection = COLUMN_CATEGORY + " = ? AND " + COLUMN_SUBCATEGORY + " = ?";
//        String[] selectionArgs = {category, subcategory};
//        return db.query(TABLE_WOMEN, columns, selection, selectionArgs, null, null, null);
//    }


}
