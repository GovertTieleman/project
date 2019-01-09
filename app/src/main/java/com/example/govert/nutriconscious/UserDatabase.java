package com.example.govert.nutriconscious;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {
    // declare DB_NAME and TABLE_NAME
    private static final String DB_NAME = "userDatabase.db";
    private static final String TABLE_NAME_USER = "user";
//    private static final String TABLE_NAME_FOOD = "food";

    // declare CREATE_TABLE_SQL query for user and food
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME_USER + " (_id " +
            "INTEGER PRIMARY KEY AUTOINCREMENT,gender TEXT,height TEXT," +
            "weight INTEGER, activity TEXT, goal TEXT)";

//    private static final String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_NAME_FOOD + " (_id " +
//            "INTEGER PRIMARY KEY AUTOINCREMENT,date TEXT,title TEXT," +
//            "mood TEXT,entry TEXT)";

    // create static instance of EntryDatabase
    private static UserDatabase instance;

    private UserDatabase(Context context) {
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

    public static UserDatabase getInstance(Context context) {
        // if instance exists, return it
        if (instance != null)
            return instance;

        // if instance doesn't exist, created it and return it
        instance = new UserDatabase(context);
        return instance;
    }

    public Cursor selectALL() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT _id, gender, height, weight, activity, goal FROM journal_entries",
                null);
    }

    public void insert(User user) {
        // get db
        SQLiteDatabase db = this.getWritableDatabase();

        // declare values
        ContentValues values = new ContentValues();

        // put values
        values.put("gender", user.getGender());
        values.put("height", user.getHeight());
        values.put("weight", user.getWeight());
        values.put("activity", user.getActivity());
        values.put("goal", user.getGoal());

        // insert entry into db
        db.insert(TABLE_NAME_USER, null, values);
    }

    public void delete(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQLDeleteQuery = "DELETE FROM " + TABLE_NAME_USER + " WHERE _id=" + id;

        db.execSQL(SQLDeleteQuery);
    }
}
