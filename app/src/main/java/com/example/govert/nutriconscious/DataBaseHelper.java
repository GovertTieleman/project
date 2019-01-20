package com.example.govert.nutriconscious;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    // declare DB_NAME and TABLE_NAMES
    private static final String DB_NAME = "userDatabase.db";
    private static final String TABLE_NAME_USER = "user";
    private static final String TABLE_NAME_FOOD = "food";

    // declare CREATE_TABLE_SQL query for user and food
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME_USER + " (_id " +
            "INTEGER PRIMARY KEY AUTOINCREMENT, gender TEXT, height INTEGER," +
            "weight INTEGER, age INTEGER, activity TEXT, goal TEXT)";

    private static final String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_NAME_FOOD +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, item_name TEXT, date DATE, calories FLOAT, protein FLOAT, " +
            "carbohydrate FLOAT, fat FLOAT, serving_quantity FLOAT, serving_size TEXT, " +
            "serving_weight FLOAT)";

    // create static instance of EntryDatabase
    private static DataBaseHelper instance;

    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_FOOD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FOOD);

        // recreate table
        onCreate(db);
    }

    public static DataBaseHelper getInstance(Context context) {
        // if instance exists, return it
        if (instance != null)
            return instance;

        // if instance doesn't exist, created it and return it
        instance = new DataBaseHelper(context);
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

    public Cursor selectFood(List dates) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT WHERE date IN " + dates + "_id, item_name, calories, " +
                        "protein, carbohydrate, fat, serving_quantity, serving_size, " +
                        "serving_weight FROM food",
                null);
    }

    public void insertFood(FoodItem foodItem) {
        // get db
        SQLiteDatabase db = this.getWritableDatabase();

        // declare values
        ContentValues values = new ContentValues();

        // put values
        values.put("item_name", foodItem.getName());
        values.put("calories", foodItem.getCalories());
        values.put("protein", foodItem.getProtein());
        values.put("carbohydrate", foodItem.getCarbohydrate());
        values.put("fat", foodItem.getFat());
        values.put("serving_quantity", foodItem.getServingQTY());
        values.put("serving_size", foodItem.getServingSize());
        values.put("serving_weight", foodItem.getServingWeight());

        // insert entry into db
        db.insert(TABLE_NAME_USER, null, values);
    }

    public void deleteFood(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQLDeleteQuery = "DELETE FROM " + TABLE_NAME_USER + " WHERE _id=" + id;

        db.execSQL(SQLDeleteQuery);
    }
}
