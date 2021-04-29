package com.example.projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    private SQLiteDatabase database;

    // Table Name
    public static final String TABLE_NAME = "MOMENTS";
    // Table columns
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String MDATE= "date";
    public static final String TIME = "time";
    public static final String ADDRESS= "address";
    public static final String CONTACT = "contact";
    public static final String PHONE= "phone";

    // Database Information
    static final String DB_NAME = "PreciousMoments.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT NOT NULL, " +  MDATE + " TEXT NOT NULL, "+
             TIME+ " TEXT, "+ CONTACT + " TEXT, " + ADDRESS + " TEXT, "  + PHONE + " TEXT);";

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
    }

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }
    public void close() {
        database.close();
    }

    public void add(Moment moment){
        ContentValues contentValues= new ContentValues();
        contentValues.put(TITLE,moment.getTitle());
        contentValues.put(MDATE,moment.getDate());
        contentValues.put(TIME,moment.getTime());
        contentValues.put(CONTACT,moment.getContact());
        contentValues.put(ADDRESS,moment.getAddress());
        contentValues.put(PHONE,moment.getPhone());
        database.insert(TABLE_NAME,null,contentValues);
    }

    public Cursor getAllMoments(){
        String[] projection = {_ID,TITLE,MDATE,TIME,CONTACT,ADDRESS,PHONE};
        Cursor cursor = database.query(TABLE_NAME,projection,null,null,null,null,null,null);
        return cursor;
    }

    public int update(Moment moment) {
        Long _id= moment.getId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE,moment.getTitle());
        contentValues.put(MDATE,moment.getDate());
        contentValues.put(TIME,moment.getTime());
        contentValues.put(CONTACT,moment.getContact());
        contentValues.put(ADDRESS,moment.getAddress());
        contentValues.put(PHONE,moment.getPhone());
        int count = database.update(TABLE_NAME, contentValues, this._ID + " = " + _id, null);
        return count;
    }

    public void delete(long _id)
    {
        database.delete(TABLE_NAME, _ID + "=" + _id, null);
    }

}
