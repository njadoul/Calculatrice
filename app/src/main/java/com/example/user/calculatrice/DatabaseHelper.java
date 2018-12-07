package com.example.user.calculatrice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;



public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderHisto.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderHisto.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderHisto.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedReaderHisto.FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)"
            ;


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderHisto.FeedEntry.TABLE_NAME;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Calculs.db";

     DatabaseHelper(Context context){
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);

        onCreate(db);
//        String sql = "DROP TABLE IF EXISTS ";
//        sqLiteDatabase.execSQL(sql + DB_TABLE);
//        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    void insertData(String title, String subtitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderHisto.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderHisto.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);



         db.insert(FeedReaderHisto.FeedEntry.TABLE_NAME, null, values);
         db.close();
    }


   /* Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + FeedReaderHisto.FeedEntry._ID
                + " FROM " + FeedReaderHisto.FeedEntry.TABLE_NAME
                + " WHERE " + FeedReaderHisto.FeedEntry._ID
                + " = '" + name + "'";
        return db.rawQuery(query,null);
    }*/



    Cursor viewData(Boolean viewAll, String title){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor dbQuery = null;

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FeedReaderHisto.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderHisto.FeedEntry.COLUMN_NAME_SUBTITLE,
               
        };

        if (viewAll){
            dbQuery = db.query(
                    FeedReaderHisto.FeedEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null               // The sort order
            );
        }

        return dbQuery;
    }


    /*Cursor getLastIDCalcul() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + FeedReaderHisto.FeedEntry._ID
                + " FROM " + FeedReaderHisto.FeedEntry.TABLE_NAME
                + " ORDER BY " + FeedReaderHisto.FeedEntry._ID + " DESC LIMIT 1";
        return db.rawQuery(query,null);
    }*/

    boolean deleteData(){
         SQLiteDatabase bd = this.getWritableDatabase();
         int deleterow = bd.delete(FeedReaderHisto.FeedEntry.TABLE_NAME, null, null);

         bd.close();

         return deleterow != -1;
    }
}
