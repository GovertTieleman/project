package com.example.govert.nutriconscious;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class UserDataBaseHelper extends SQLiteOpenHelper {

    // declare DB_NAME and TABLE_NAMES
    private static final String DB_NAME = "userDatabase.db";
    private static final String TABLE_NAME_USER = "user";
    private static final String TABLE_NAME_FOOD = "food";

    // declare CREATE_TABLE_SQL query for user and food
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME_USER + " (_id " +
            "INTEGER PRIMARY KEY AUTOINCREMENT, gender TEXT, height INTEGER," +
            "weight INTEGER, age INTEGER, activity TEXT, goal TEXT)";

    // create static instance of EntryDatabase
    private static UserDataBaseHelper instance;

    private UserDataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);

        // recreate table
        onCreate(db);
    }

    public static UserDataBaseHelper getInstance(Context context) {
        // if instance exists, return it
        if (instance != null)
            return instance;

        // if instance doesn't exist, created it and return it
        instance = new UserDataBaseHelper(context);
        return instance;
    }

    public Cursor selectUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT _id, gender, height, weight, age, activity, goal FROM user",
                null);
    }

    public void insertUser(User user) {
        // get db
        SQLiteDatabase db = this.getWritableDatabase();

        // declare values
        ContentValues values = new ContentValues();

        // put values
        values.put("gender", user.getGender());
        values.put("height", user.getHeight());
        values.put("weight", user.getWeight());
        values.put("age", user.getAge());
        values.put("activity", user.getActivity());
        values.put("goal", user.getGoal());

        // insert entry into db
        db.insert(TABLE_NAME_USER, null, values);
    }

    public void updateUser(User user) {
        // get db
        SQLiteDatabase db = this.getWritableDatabase();

        // declare values
        ContentValues values = new ContentValues();

        // put values
        values.put("gender", user.getGender());
        values.put("height", user.getHeight());
        values.put("weight", user.getWeight());
        values.put("age", user.getAge());
        values.put("activity", user.getActivity());
        values.put("goal", user.getGoal());

        // get id string
        int id = user.getId();
        String[] whereArgs = new String[] {String.valueOf(id)};

        // update entry
        db.update(TABLE_NAME_USER, values, "_id=?", whereArgs);
    }
}
