package com.example.stylemeup;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_super_user extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "super_user.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ACCOUNTS = "ACCOUNTS";



    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";


    public Database_super_user(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAccountsTable = "CREATE TABLE " + TABLE_ACCOUNTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createAccountsTable);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        onCreate(db);
    }

    public boolean isValidAccount(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME};
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_ACCOUNTS, columns, selection, selectionArgs, null, null, null);

        boolean userExists = cursor.moveToFirst();
        cursor.close();
        return userExists;
    }

    public boolean isUserInDb(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME};
        String selection = COLUMN_USERNAME + " = ? ";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_ACCOUNTS, columns, selection, selectionArgs, null, null, null);

        boolean userExists = cursor.moveToFirst();
        cursor.close();
        return userExists;
    }

    public void deleteAccount(String username) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = {username};
        db.delete(TABLE_ACCOUNTS, whereClause, whereArgs);
    }

    public void insertInDb(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        if (isUserInDb(username)) {

        } else {
            // Insert the new user into the database
            db.insert(TABLE_ACCOUNTS, null, values);
        }

    }












}
