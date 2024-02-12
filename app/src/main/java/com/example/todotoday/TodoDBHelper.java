package com.example.todotoday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TodoDBHelper extends SQLiteOpenHelper
{
    public static final int DB_VERSION = 1; // keep track number of the database
    private static final String DB_NAME = "todo.db";

    public static final String TABLE_NAME = "users";
    private static final String ID = "_id"; // SQLite typically will have tables with _id set as an auto incrementing integer
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_TASK_ID = "_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_TASK_CHECKED = "task_checked";



    public TodoDBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME + " TEXT, " +
                PASSWORD + " TEXT)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(PASSWORD, password);

        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public boolean checkPassword(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {PASSWORD};
        String selection = USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean passwordMatches = false;

        if (cursor.moveToFirst()) {
            int passwordColumnIndex = cursor.getColumnIndex(PASSWORD);
            if (passwordColumnIndex != -1) {
                String storedPassword = cursor.getString(passwordColumnIndex);
                if (password.equals(storedPassword)) {
                    passwordMatches = true;
                }
            }
        }

        cursor.close();
        db.close();

        return passwordMatches;
    }
//
//
//    public ArrayList<String> getAllUsernames() {
//        SQLiteDatabase db = getReadableDatabase();
//
//        String[] projection = {USERNAME};
//
//        Cursor cursor = db.query(
//                TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        ArrayList<String> usernames = new ArrayList<>();
//
//        while (cursor.moveToNext()) {
//            int usernameColumnIndex = cursor.getColumnIndex(USERNAME);
//            if (usernameColumnIndex != -1) {
//                String username = cursor.getString(usernameColumnIndex);
//                usernames.add(username);
//            }
//        }
//
//        cursor.close();
//        db.close();
//
//        return usernames;
//    }
//
//    public long addTask(long userId, String taskName) {
//        SQLiteDatabase db = getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_USER_ID, userId);
//        values.put(COLUMN_TASK_NAME, taskName);
//        values.put(COLUMN_TASK_CHECKED, 0); // Default task checked status is 0 (unchecked)
//
//        long newRowId = db.insert(TABLE_TASKS, null, values);
//        db.close();
//
//        return newRowId;
//    }
//
//    public ArrayList<Todo> getTasksForUser(String username) {
//        SQLiteDatabase db = getReadableDatabase();
//
//        String[] projection = {COLUMN_TASK_NAME, COLUMN_TASK_CHECKED};
//        String selection = COLUMN_USER_ID + " = ?";
//        String[] selectionArgs = {String.valueOf(username)};
//
//        Cursor cursor = db.query(
//                TABLE_TASKS,
//                projection,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                null
//        );
//
//        ArrayList<Todo> tasks = new ArrayList<>();
//
//        while (cursor.moveToNext()) {
//            int taskNameColumnIndex = cursor.getColumnIndex(COLUMN_TASK_NAME);
//            int taskCheckedColumnIndex = cursor.getColumnIndex(COLUMN_TASK_CHECKED);
//
//            if (taskNameColumnIndex != -1 && taskCheckedColumnIndex != -1) {
//                String taskName = cursor.getString(taskNameColumnIndex);
//                int taskCheckedValue = cursor.getInt(taskCheckedColumnIndex);
//                boolean taskChecked = (taskCheckedValue == 1); // Convert to boolean
//
//                Todo task = new Todo(taskName, taskChecked);
//                tasks.add(task);
//            }
//        }
//
//        cursor.close();
//        db.close();
//
//        return tasks;
//    }


}
