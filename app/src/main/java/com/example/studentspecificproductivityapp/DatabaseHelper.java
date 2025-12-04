package com.example.studentspecificproductivityapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class  DatabaseHelper extends SQLiteOpenHelper {

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

    // STUDY SESSION TABLE
    public static final String STUDY_TABLE = "STUDY_TABLE";
    public static final String COLUMN_STUDY_ID = "STUDY_ID";
    public static final String COLUMN_STUDY_USER_ID = "STUDY_USER_ID";
    public static final String COLUMN_STUDY_SUBJECT = "STUDY_SUBJECT";
    public static final String COLUMN_STUDY_START_TIME = "STUDY_START_TIME";
    public static final String COLUMN_STUDY_END_TIME = "STUDY_END_TIME";
    public static final String COLUMN_STUDY_DURATION = "STUDY_DURATION";
    public static final String COLUMN_STUDY_NOTES = "STUDY_NOTES";

    // PLAN EVENT TABLE

    public static final String PLAN_EVENTS_TABLE = "PLAN_EVENTS_TABLE";
    public static final String COLUMN_PLAN_EVENTS_ID = "PLAN_EVENTS_ID";
    public static final String COLUMN_PLAN_EVENTS_USER_ID = "PLAN_EVENTS_USER_ID";
    public static final String COLUMN_PLAN_EVENTS_DATE = "PLAN_EVENTS_DATE";
    public static final String COLUMN_PLAN_EVENTS_NAME = "PLAN_EVENTS_NAME";
    public static final String COLUMN_PLAN_EVENTS_DESC = "PLAN_EVENTS_DESC";
    public static final String COLUMN_PLAN_EVENTS_TIME = "PLAN_EVENTS_TIME";

    // COURSE SCHEDULE TABLE

    public static final String COURSE_TABLE = "COURSE_TABLE";
    public static final String COLUMN_COURSE_ID = "COURSE_ID";
    public static final String COLUMN_COURSE_USER_ID = "COURSE_USER_ID";
    public static final String COLUMN_COURSE_CODE = "COURSE_CODE";
    public static final String COLUMN_COURSE_NAME = "COURSE_NAME";
    public static final String COLUMN_COURSE_DAYS = "COURSE_DAYS";
    public static final String COLUMN_COURSE_HOURS = "COURSE_HOURS";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "UserDb", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT)";

        String createTableSleep = "CREATE TABLE " + SLEEP_TABLE + " ("+ COLUMN_SLEEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SLEEP_TIME + " INTEGER, " + COLUMN_WAKE_TIME + " INTEGER," + COLUMN_DURATION + " INTEGER,"+ COLUMN_USER_ID + " INTEGER)" ;

        String createTableTasks = "CREATE TABLE " + TASKS_TABLE + " ("+ COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TASK_TITLE + " TEXT, " + COLUMN_TASK_DESC + " TEXT," + COLUMN_TASK_COMPLETED + " INTEGER DEFAULT 0," + COLUMN_TASK_USER_ID + " INTEGER)" ;

        String createTablePlanEvents = "CREATE TABLE " + PLAN_EVENTS_TABLE + " (" + COLUMN_PLAN_EVENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PLAN_EVENTS_USER_ID + " INTEGER, " + COLUMN_PLAN_EVENTS_DATE + " TEXT, " + COLUMN_PLAN_EVENTS_NAME + " TEXT," + COLUMN_PLAN_EVENTS_DESC + " TEXT," + COLUMN_PLAN_EVENTS_TIME + " INTEGER)";

        String createTableStudy = "CREATE TABLE " + STUDY_TABLE + " (" + COLUMN_STUDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STUDY_SUBJECT + " TEXT, " + COLUMN_STUDY_START_TIME + " INTEGER, " + COLUMN_STUDY_END_TIME + " INTEGER, " + COLUMN_STUDY_DURATION + " INTEGER, " + COLUMN_STUDY_NOTES + " TEXT, " + COLUMN_STUDY_USER_ID + " INTEGER)";

        String createTableCourses = "CREATE TABLE " + COURSE_TABLE + " (" + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_COURSE_USER_ID + " INTEGER, " + COLUMN_COURSE_CODE + " TEXT, " + COLUMN_COURSE_NAME + " TEXT," + COLUMN_COURSE_DAYS + " TEXT," + COLUMN_COURSE_HOURS + " TEXT)";

        db.execSQL(createTableStatement);
        db.execSQL(createTableSleep);
        db.execSQL(createTableTasks);
        db.execSQL(createTablePlanEvents);
        db.execSQL(createTableStudy);
        db.execSQL(createTableCourses);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SLEEP_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PLAN_EVENTS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUDY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SLEEP_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PLAN_EVENTS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUDY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE);
        onCreate(sqLiteDatabase);
    }

    // USER METHODS
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_EMAIL, user.getEmail());
        cv.put(COLUMN_USER_PASSWORD, user.getPassword());

        long insert = db.insert(USER_TABLE, null, cv);
        return insert != -1;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE + " where " + COLUMN_USER_EMAIL + " = ?", new String[]{email});

        boolean emailFound = cursor.getCount() > 0;
        db.close();
        cursor.close();
        return emailFound;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE + " where " + COLUMN_USER_EMAIL + " = ? and " + COLUMN_USER_PASSWORD + " = ?", new String[]{email, password});

        boolean emailPasswordFound = cursor.getCount() > 0;
        db.close();
        cursor.close();
        return emailPasswordFound;
    }

    public boolean changeEmail(int userId, String newEmail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_EMAIL, newEmail);

        long update = db.update(USER_TABLE, cv, COLUMN_ID + " = ? ", new String[]{String.valueOf(userId)});
        //db.close();
        return update != -1;
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


    // TO DO LIST METHODS
    public boolean addTask(String title, String desc, int userId) {
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

    // PLAN EVENT METHODS
    public boolean addPlanEvent(PlanEventModel event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PLAN_EVENTS_USER_ID, event.getUserId());
        cv.put(COLUMN_PLAN_EVENTS_DATE, event.getDate());
        cv.put(COLUMN_PLAN_EVENTS_NAME, event.getName());
        cv.put(COLUMN_PLAN_EVENTS_DESC, event.getDescription());
        cv.put(COLUMN_PLAN_EVENTS_TIME, event.getTime());

        long insert = db.insert(PLAN_EVENTS_TABLE, null, cv);
        return insert != -1;
    }

    public boolean deletePlanEvent(int userId, String date, String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PLAN_EVENTS_TABLE, COLUMN_PLAN_EVENTS_USER_ID + " = ? and " + COLUMN_PLAN_EVENTS_DATE + " = ? and " + COLUMN_PLAN_EVENTS_TIME + " = ? ", new String[]{String.valueOf(userId), date, time}) > 0;
    }

    public boolean userHasPlannedEventsOnDate(int userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + PLAN_EVENTS_TABLE + " where " + COLUMN_PLAN_EVENTS_USER_ID + " = ? and " + COLUMN_PLAN_EVENTS_DATE + " = ? ", new String[]{String.valueOf(userId), date});

        boolean dateFound = cursor.getCount() > 0;
        db.close();
        cursor.close();
        return dateFound;
    }

    public ArrayList<PlanEventModel> getAllPlannedEventsOnDateForUser(int userId, String date) {
        ArrayList<PlanEventModel> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + PLAN_EVENTS_TABLE + " WHERE " + COLUMN_PLAN_EVENTS_USER_ID + " = ? and " + COLUMN_PLAN_EVENTS_DATE + " = ? ", new String[]{String.valueOf(userId), date});
        while (cursor.moveToNext()) {
            PlanEventModel rec = new PlanEventModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PLAN_EVENTS_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLAN_EVENTS_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLAN_EVENTS_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLAN_EVENTS_DESC)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLAN_EVENTS_TIME)));
            arr.add(rec);
        }
        db.close();
        cursor.close();
        return arr;
    }

    // STUDY SESSION METHODS
    public boolean addStudySession(String subject, long startTime, long endTime, long duration, String notes, int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDY_SUBJECT, subject);
        cv.put(COLUMN_STUDY_START_TIME, startTime);
        cv.put(COLUMN_STUDY_END_TIME, endTime);
        cv.put(COLUMN_STUDY_DURATION, duration);
        cv.put(COLUMN_STUDY_NOTES, notes);
        cv.put(COLUMN_STUDY_USER_ID, userId);
        long insert = db.insert(STUDY_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<StudySessionModel> getAllStudySessions(int userId){
        ArrayList<StudySessionModel> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + STUDY_TABLE + " WHERE " + COLUMN_STUDY_USER_ID + " =?", new String[]{String.valueOf(userId)});
        while(cursor.moveToNext()){
            StudySessionModel rec = new StudySessionModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SUBJECT)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_STUDY_START_TIME)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_STUDY_END_TIME)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_STUDY_DURATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_NOTES)));
            arr.add(rec);
        }
        cursor.close();
        return arr;
    }

    public boolean deleteStudySession(int studyId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(STUDY_TABLE, COLUMN_STUDY_ID+"=?", new String[]{String.valueOf(studyId)})>0;
    }

    // COURSE SCHEDULE METHODS
    public boolean addCourse(CourseModel course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COURSE_USER_ID, course.getUserId());
        cv.put(COLUMN_COURSE_CODE, course.getCourseCode());
        cv.put(COLUMN_COURSE_NAME, course.getCourseName());
        cv.put(COLUMN_COURSE_DAYS, course.getCourseDaysOfTheWeek());
        cv.put(COLUMN_COURSE_HOURS, course.getCourseHours());

        long insert = db.insert(COURSE_TABLE, null, cv);
        return insert != -1;
    }

    public boolean deleteCourse(int userId, String courseCode){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(COURSE_TABLE, COLUMN_COURSE_USER_ID + " = ? and " + COLUMN_COURSE_CODE + " = ? ", new String[]{String.valueOf(userId), courseCode}) > 0;
    }

    public ArrayList<CourseModel> getAllCoursesForUser(int userId) {
        ArrayList<CourseModel> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + COURSE_TABLE + " WHERE " + COLUMN_COURSE_USER_ID + " = ? ", new String[]{String.valueOf(userId)});
        while (cursor.moveToNext()) {
            CourseModel course = new CourseModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COURSE_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_CODE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_DAYS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_HOURS)));
            arr.add(course);
        }
        db.close();
        cursor.close();
        return arr;
    }
}
