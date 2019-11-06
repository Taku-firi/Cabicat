package com.Stardust.cabicat.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.Stardust.cabicat.item.FileItem;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    // Database name
    private static final String DATABASE_NAME = "fileitem_db";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_NORMALLAYER = "normallayer";
    private static final String TABLE_SECRETLAYER = "secretlayer";

    // Column names for both  TABLE_NORMALLAYER and TABLE_SECRETLAYER table
    private static final String PATH = "PATH";
    private static final String NAME = "NAME";
    private static final String SIZE = "SIZE";
    private static final String ADDDATE = "ADDDATE";
    private static final String CHECKDATE = "CHECKDATE";
    private static final String PRIORITY = "PRIORITY";

    // TABLE_NORMALLAYER table create statements
    private static final String CREATE_TABLE_NORMALLAYER =
            "CREATE TABLE " + TABLE_NORMALLAYER
                    + "("
                    + PATH + " TEXT PRIMARY KEY,"
                    + NAME + " TEXT,"
                    + SIZE + " LONG,"
                    + ADDDATE + " DATETIME"
                    + CHECKDATE + "DATETIME"
                    +PRIORITY + "INTEGER"
                    + ")";

    private static final String CREATE_TABLE_SECRETLAYER =
            "CREATE TABLE " + TABLE_SECRETLAYER
                    + "("
                    + PATH + " TEXT PRIMARY KEY,"
                    + NAME + " TEXT,"
                    + SIZE + " LONG,"
                    + ADDDATE + " DATETIME"
                    + CHECKDATE + "DATETIME"
                    +PRIORITY + "INTEGER"
                    + ")";
    // Singleton instance
    private static DatabaseHelper sInstance;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
// 出错了
//    public static synchronized DatabaseHelper getInstance(Context context) {
//        if (sInstance == null) {
//            sInstance = new DatabaseHelper(context);
//        }
//
//        return sInstance;
//    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NORMALLAYER);
        db.execSQL(CREATE_TABLE_SECRETLAYER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NORMALLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECRETLAYER);

        onCreate(db);
    }

//    private static final String PATH = "PATH";
//    private static final String NAME = "NAME";
//    private static final String SIZE = "SIZE";
//    private static final String ADDDATE = "ADDDATE";
//    private static final String CHECKDATE = "CHECKDATE";
//    private static final String PRIORITY = "PRIORITY";

    // Creates a note
    public long createNote(FileItem fileItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PATH, fileItem.getPath());
        values.put(NAME, fileItem.getName());
        values.put(SIZE, fileItem.getFilesize());
        values.put(ADDDATE, fileItem.getAddeddate());
        values.put(CHECKDATE, fileItem.getCheckdate());
        values.put(PRIORITY, fileItem.getPriority());

        Log.d(TAG, "createFileItem: " + fileItem.getPath());

        return db.insert(TABLE_NORMALLAYER, null, values);
    }

    // Determines whether there exist a fileitem that has the same name
    public boolean isFileItem_normal(String note_name) {
        boolean isFileItem = false;

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NORMALLAYER
                + " WHERE " + NAME + " = '" + note_name + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            isFileItem = true;
        }
        c.close();

        return isFileItem;
    }
    public boolean isFileItem_secret(String note_name) {
        boolean isFileItem = false;

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SECRETLAYER
                + " WHERE " + NAME + " = '" + note_name + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            isFileItem = true;
        }
        c.close();

        return isFileItem;
    }
    // Gets a single note according to its name
    public FileItem getFileItemByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NORMALLAYER
                + " WHERE " + NAME + " = '" + name + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            c.moveToFirst();
        }
        FileItem fileItem = new FileItem(c.getString(c.getColumnIndex(PATH)),c.getString(c.getColumnIndex(NAME)),c.getLong());
        ;
        fileItem.setName((c.getString(c.getColumnIndex(KEY_NOTE_NAME))));
        fileItem.setContent((c.getString(c.getColumnIndex(KEY_NOTE_CONTENT))));
        fileItem.setTimestamp(c.getString(c.getColumnIndex(KEY_TIMESTAMP)));
        c.close();

        return fileItem;
    }


}
