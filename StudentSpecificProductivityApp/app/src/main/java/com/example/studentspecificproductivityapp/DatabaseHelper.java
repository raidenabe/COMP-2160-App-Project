package com.example.studentspecificproductivityapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    //USER TABLE
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_ID = "ID";

    //SLEEP TABLE
    public static final String SLEEP_TABLE = "SLEEP_TABLE";

    public static final String COLUMN_SLEEP_ID = "SLEEP_ID";

    public static final String COLUMN_SLEEP_TIME = "SLEEP_TIME";

    public static final String COLUMN_WAKE_TIME = "WAKE_TIME";

    public static final String COLUMN_DURATION = "DURATION";

    public static final String COLUMN_USER_ID = "USER_ID";

    // TO DO LIST TABLE

    public static final String TASKS_TABLE = "TASKS_TABLE";
    public static final String COLUMN_TASK_ID = "TASK_ID";
    public static final String COLUMN_TASK_USER_ID = "TASK_USER_ID";
    public static final String COLUMN_TASK_TITLE = "TASK_TITLE";
    public static final String COLUMN_TASK_DESC = "TASK_DESC";
    public static final String COLUMN_TASK_COMPLETED = "TASK_COMPLETED";



    public DatabaseHelper(@Nullable Context context) {
        super(context, "UserDb", null, 2);
    }

    // when database is first accessed.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT)";

        String createTableSleep = "CREATE TABLE " + SLEEP_TABLE + " ("+ COLUMN_SLEEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SLEEP_TIME + " INTEGER, " + COLUMN_WAKE_TIME + " INTEGER," + COLUMN_DURATION + " INTEGER,"+ COLUMN_USER_ID + " INTEGER)" ;

        String createTableTasks = "CREATE TABLE " + TASKS_TABLE + " ("+ COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TASK_TITLE + " TEXT, " + COLUMN_TASK_DESC + " TEXT," + COLUMN_TASK_COMPLETED + " INTEGER DEFAULT 0," + COLUMN_TASK_USER_ID + " INTEGER)" ;

        db.execSQL(createTableStatement);
        db.execSQL(createTableSleep);
        db.execSQL(createTableTasks);
    }

    // when database version number changes.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SLEEP_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE);
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

    public int getUserIdByEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE + " where " + COLUMN_USER_EMAIL + " = ? " , new String[]{email});
        int userId = -1;
        if(cursor.moveToFirst()){
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
        }
        cursor.close();
        return userId;
    }

    // SLEEP METHODS

    public boolean addSleepRecord(long sleepTime, long wakeTime, long duration, int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SLEEP_TIME, sleepTime);
        cv.put(COLUMN_WAKE_TIME, wakeTime);
        cv.put(COLUMN_DURATION, duration);
        cv.put(COLUMN_USER_ID,userId);

        long insert = db.insert(SLEEP_TABLE, null, cv);
        return insert!=-1;
    }

    public ArrayList<SleepModel> getAllSleepRecords(int userId){

        ArrayList<SleepModel> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + SLEEP_TABLE + " WHERE " + COLUMN_USER_ID + " =?",new String[]{String.valueOf(userId)});
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


    //To Do List
    public boolean addTask(String title, String desc, int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK_USER_ID, userId);
        cv.put(COLUMN_TASK_TITLE, title);
        cv.put(COLUMN_TASK_DESC,desc);
        long insert = db.insert(TASKS_TABLE, null, cv);
        return insert!=-1;
    }

    public ArrayList<TaskModel> getAllTasks(int userId){
        ArrayList<TaskModel> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TASKS_TABLE + " WHERE " + COLUMN_TASK_USER_ID + " =?",new String[]{String.valueOf(userId)});
        while(cursor.moveToNext()){
            TaskModel rec = new TaskModel(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_DESC)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_COMPLETED))==1);
            arr.add(rec);
        }
        cursor.close();
        return arr;
    }

    public boolean setTaskCompleted (int taskId, boolean completed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK_COMPLETED, completed ? 1 : 0);
        int rows = db.update(TASKS_TABLE, cv, COLUMN_TASK_ID + "=?", new String[]{String.valueOf(taskId)});
        return rows > 0;
    }

    public boolean deleteTask(int taskId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TASKS_TABLE, COLUMN_TASK_ID+"=?",new String[]{String.valueOf(taskId)})>0;
    }
}
