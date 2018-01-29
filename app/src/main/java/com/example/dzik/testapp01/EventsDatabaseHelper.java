package com.example.dzik.testapp01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class EventsDatabaseHelper extends SQLiteOpenHelper {

    //singleton instance
    private static EventsDatabaseHelper sInstance;

    private static final String DATABASE_NAME = "eventsDatabase";
    private static final int DATABASE_VERSION = 1;

    //tables
    private static final String TABLE_EVENTS = "events";

    //events columns
    private static final String KEY_EVENT_LOCAL_ID = "localId";
    private static final String KEY_EVENT_SERVER_ID = "serverId";
    private static final String KEY_EVENT_TITLE = "title";
    private static final String KEY_EVENT_DESCRIPTION = "description";
    private static final String KEY_EVENT_EXECUTION_TIME = "executionTime";

    //private constructor
    private EventsDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "(" +
            KEY_EVENT_LOCAL_ID + " INTEGER PRIMARY KEY, " +
            KEY_EVENT_SERVER_ID + " INTEGER, " +
            KEY_EVENT_TITLE + " TEXT, " +
            KEY_EVENT_DESCRIPTION + " TEXT, " +
            KEY_EVENT_EXECUTION_TIME + " TEXT" + ");";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS + ";");
            onCreate(db);
        }
    }

    public static synchronized EventsDatabaseHelper getsInstance(Context context) {
        if (sInstance == null){
            sInstance = new EventsDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    //CRUD
    public void addEvent(EventDB eventDB){
        //create or open existing db
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_EVENT_SERVER_ID, eventDB.getServerId());
            values.put(KEY_EVENT_TITLE, eventDB.getTitle());
            values.put(KEY_EVENT_DESCRIPTION, eventDB.getDescription());
            values.put(KEY_EVENT_EXECUTION_TIME, eventDB.getExecutionTime());

            db.insertOrThrow(TABLE_EVENTS, null, values);
            db.setTransactionSuccessful();
        } catch(Exception e){
            Log.wtf("DB_WTF", "Add post error");
            Log.wtf("DB_WTF", e.toString());
        } finally {
            db.endTransaction();
        }
    }
    public List<EventDB> getAllEvents(){
        List<EventDB> eventDBs = new ArrayList<>();
        String EVENTS_SELECT_QUERY = "SELECT * FROM " + TABLE_EVENTS;

        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor = db.rawQuery(EVENTS_SELECT_QUERY, null);
        try{
            if(cursor.moveToFirst()) {
                do {
                    EventDB eventDB = new EventDB();
                    eventDB.setServerId(cursor.getInt(cursor.getColumnIndex(KEY_EVENT_SERVER_ID)));
                    eventDB.setTitle(cursor.getString(cursor.getColumnIndex(KEY_EVENT_TITLE)));
                    eventDB.setDescription(cursor.getString(cursor.getColumnIndex(KEY_EVENT_DESCRIPTION)));
                    eventDB.setExecutionTime(cursor.getString(cursor.getColumnIndex(KEY_EVENT_EXECUTION_TIME)));
                    eventDBs.add(eventDB);
                } while(cursor.moveToNext());
            }
        } catch (Exception e){
            Log.wtf("DB_WTF", "Get all eventDBs error");
        } finally {
            if ( (cursor != null) && (!cursor.isClosed()) ) {
                cursor.close();
            }
        }
        return eventDBs;
    }

    public void deleteAllEvents() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.delete(TABLE_EVENTS, null, null);
        } catch (Exception e) {
            Log.wtf("DB_WTF", "Delete all events error");
        }
    }
}
