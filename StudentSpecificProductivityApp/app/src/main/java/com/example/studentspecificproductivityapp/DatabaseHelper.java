package com.example.studentspecificproductivityapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_ID = "ID";

    public static final String SLEEP_TABLE = "SLEEP_TABLE";

    public static final String COLUMN_SLEEP_ID = "SLEEP_ID";

    public static final String COLUMN_SLEEP_TIME = "SLEEP_TIME";

    public static final String COLUMN_WAKE_TIME = "WAKE_TIME";

    public static final String COLUMN_DURATION = "DURATION";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "UserDb", null, 2);
    }

    // when database is first accessed.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT)";

        String createTableSleep = "CREATE TABLE " + SLEEP_TABLE + " ("+ COLUMN_SLEEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SLEEP_TIME + " INTEGER, " + COLUMN_WAKE_TIME + " INTEGER," + COLUMN_DURATION + " INTEGER)";

        db.execSQL(createTableStatement);
        db.execSQL(createTableSleep);
    }

    // when database version number changes.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SLEEP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean addUser (User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_EMAIL, user.getEmail());
        cv.put(COLUMN_USER_PASSWORD, user.getPassword());

        long insert = db.insert(USER_TABLE, null, cv);
        return insert != -1;
    }

    public boolean checkEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE + " where " + COLUMN_USER_EMAIL + " = ?", new String[]{email});

        boolean emailFound = cursor.getCount() > 0;
        db.close();
        cursor.close();
        return emailFound;
    }

    public boolean checkEmailPassword(String email, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE + " where " + COLUMN_USER_EMAIL + " = ? and " + COLUMN_USER_PASSWORD + " = ?", new String[]{email, password});

        boolean emailPasswordFound = cursor.getCount() > 0;
        db.close();
        cursor.close();
        return emailPasswordFound;
    }


    // SLEEP METHODS

    public boolean addSleepRecord(long sleepTime, long wakeTime, long duration){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SLEEP_TIME, sleepTime);
        cv.put(COLUMN_WAKE_TIME, wakeTime);
        cv.put(COLUMN_DURATION, duration);

        long insert = db.insert(SLEEP_TABLE, null, cv);
        return insert!=-1;
    }

    public ArrayList<SleepModel> getAllSleepRecords(){

        ArrayList<SleepModel> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + SLEEP_TABLE,null);
        while(cursor.moveToNext()){
            SleepModel rec = new SleepModel(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SLEEP_ID)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SLEEP_TIME)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_WAKE_TIME)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
            arr.add(rec);
        }
        cursor.close();
        return arr;
    }

    public boolean deleteSleepRecord(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SLEEP_TABLE, COLUMN_SLEEP_ID + "=?", new String[]{String.valueOf(id)})>0;
    }
}
