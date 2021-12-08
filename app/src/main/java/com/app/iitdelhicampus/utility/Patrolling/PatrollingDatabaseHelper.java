package com.app.iitdelhicampus.utility.Patrolling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class PatrollingDatabaseHelper extends SQLiteOpenHelper {
    private static PatrollingDatabaseHelper instance;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PunchINOUT";
    private static final String TABLE_NAME="Punch";
    private static final String COLUMN_ID="date";
    public static final String KEY_RESOURCE_ID="response";

    public static PatrollingDatabaseHelper getInstance(Context context){
        if(instance==null){
            instance=new PatrollingDatabaseHelper(context);
        }
        return instance;
    }

    public PatrollingDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER,"
                        + KEY_RESOURCE_ID + " TEXT"
                        + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertOfflineData(PatrollingOfflineStreamModel offlineStreamModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RESOURCE_ID, offlineStreamModel.getDate());
        values.put(KEY_RESOURCE_ID, offlineStreamModel.getResponse());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<PatrollingOfflineStreamModel> getAllOfflineData() {
        List<PatrollingOfflineStreamModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PatrollingOfflineStreamModel model = new PatrollingOfflineStreamModel();
                model.setDate(cursor.getLong(cursor.getColumnIndex(KEY_RESOURCE_ID)));
                model.setResponse(cursor.getString(cursor.getColumnIndex(KEY_RESOURCE_ID)));
                list.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void deleteOfflineTableData(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
}