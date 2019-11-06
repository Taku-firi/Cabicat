package com.Stardust.cabicat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.Stardust.cabicat.item.FileItem;
import java.util.ArrayList;
import java.util.List;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    // Database name
    private static final String DATABASE_NAME = "fileitem.db";
    // Database version
    private static final int DATABASE_VERSION = 1;
    // Table names
    private static final String TABLE_NORMALLAYER = "normallayer";
    private static final String TABLE_SECRETLAYER = "secretlayer";
    // Column names for both  TABLE_NORMALLAYER and TABLE_SECRETLAYER table
    private static final String PATH = "path";
    private static final String NAME = "name";
    private static final String SIZE = "size";
    private static final String ADDEDDATE = "addeddate";
    private static final String CHECKDATE = "checkdate";
    private static final String PRIORITY = "priority";
    // TABLE_NORMALLAYER table create statements
    private static final String CREATE_TABLE_NORMALLAYER =
            "CREATE TABLE " + TABLE_NORMALLAYER
                    + "("
                    + PATH + " TEXT PRIMARY KEY,"
                    + NAME + " TEXT,"
                    + SIZE + " LONG,"
                    + ADDEDDATE + " DATETIME,"
                    + CHECKDATE + "DATETIME,"
                    + PRIORITY + "INTEGER"
                    + ")";
    private static final String CREATE_TABLE_SECRETLAYER =
            "CREATE TABLE " + TABLE_SECRETLAYER
                    + "("
                    + PATH + " TEXT PRIMARY KEY,"
                    + NAME + " TEXT,"
                    + SIZE + " LONG,"
                    + ADDEDDATE + " DATETIME,"
                    + CHECKDATE + "DATETIME,"
                    + PRIORITY + "INTEGER"
                    + ")";
    //
    private static DatabaseHelper sInstance;
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        loadDB();
    }
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }
//     Initialisation
    public List<FileItem> loadDB() {
        List<FileItem> fileitems = new ArrayList<>();
        for (FileItem fileItem : getAllTable(0)) {
            fileitems.add(fileItem);
        }
        return fileitems;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NORMALLAYER);
        db.execSQL(CREATE_TABLE_SECRETLAYER);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NORMALLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECRETLAYER);

    }
    // i=0 -> add to normal layer,  i=1 -> add to secret layer
    public long createFileItem(FileItem fileItem, int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PATH, fileItem.getPath());
        values.put(NAME, fileItem.getName());
        values.put(SIZE, fileItem.getFilesize());
        values.put(ADDEDDATE, fileItem.getAddeddate());
        values.put(CHECKDATE, fileItem.getCheckdate());
        values.put(PRIORITY, fileItem.getPriority());
        Log.d(TAG, "createFileItem: " + fileItem.getPath());
        if (i == 0) {
            return db.insert(TABLE_NORMALLAYER, null, values);
        } else {
            return db.insert(TABLE_SECRETLAYER, null, values);
        }
    }
    // get a whole table (one of two)
    // i=0 -> normal layer; i=1 -> secret layer
    public List<FileItem> getAllTable(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (i == 0) {
            String selectQuery = "SELECT * FROM " + TABLE_NORMALLAYER;
            Cursor c = db.rawQuery(selectQuery, null);
            List<FileItem> fileitemList = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    FileItem fileItem = new FileItem(
                            c.getString(c.getColumnIndex(PATH)),
                            c.getString(c.getColumnIndex(NAME)),
                            c.getLong(c.getColumnIndex(SIZE)),
                            c.getString(c.getColumnIndex(ADDEDDATE)),
                            c.getString(c.getColumnIndex(CHECKDATE)),
                            c.getInt(c.getColumnIndex(PRIORITY))
                    );
                    fileitemList.add(fileItem);
                } while (c.moveToNext());
            }
            c.close();
            return fileitemList;
        } else {
            String selectQuery = "SELECT * FROM " + TABLE_SECRETLAYER;
            Cursor c = db.rawQuery(selectQuery, null);
            List<FileItem> fileitemList = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    FileItem fileItem = new FileItem(
                            c.getString(c.getColumnIndex(PATH)),
                            c.getString(c.getColumnIndex(NAME)),
                            c.getLong(c.getColumnIndex(SIZE)),
                            c.getString(c.getColumnIndex(ADDEDDATE)),
                            c.getString(c.getColumnIndex(CHECKDATE)),
                            c.getInt(c.getColumnIndex(PRIORITY))
                    );
                    fileitemList.add(fileItem);
                } while (c.moveToNext());
            }
            c.close();
            return fileitemList;
        }
    }

    // i=0 -> normal layer; i=1 -> secret layer
    // from a path to get all item
    public FileItem getFileItemByPath(String path, int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (i == 0) {

            String selectQuery = "SELECT * FROM " + TABLE_NORMALLAYER
                    + " WHERE " + PATH + " = '" + path + "'";
            Cursor c = db.rawQuery(selectQuery, null);
            if (c != null) {
                c.moveToFirst();
            }
            FileItem fileItem = new FileItem(
                    c.getString(c.getColumnIndex(PATH)),
                    c.getString(c.getColumnIndex(NAME)),
                    c.getLong(c.getColumnIndex(SIZE)),
                    c.getString(c.getColumnIndex(ADDEDDATE)),
                    c.getString(c.getColumnIndex(CHECKDATE)),
                    c.getInt(c.getColumnIndex(PRIORITY))
            );
            c.close();
            return fileItem;
        } else {
            String selectQuery = "SELECT * FROM " + TABLE_SECRETLAYER
                    + " WHERE " + PATH + " = '" + path + "'";
            Cursor c = db.rawQuery(selectQuery, null);
            if (c != null) {
                c.moveToFirst();
            }
            FileItem fileItem = new FileItem(
                    c.getString(c.getColumnIndex(PATH)),
                    c.getString(c.getColumnIndex(NAME)),
                    c.getLong(c.getColumnIndex(SIZE)),
                    c.getString(c.getColumnIndex(ADDEDDATE)),
                    c.getString(c.getColumnIndex(CHECKDATE)),
                    c.getInt(c.getColumnIndex(PRIORITY))
            );
            c.close();
            return fileItem;
        }
    }

    public String getNameByPath(String path, int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (i == 0) {

            String selectQuery = "SELECT * FROM " + TABLE_NORMALLAYER
                    + " WHERE " + PATH + " = '" + path + "'";
            Cursor c = db.rawQuery(selectQuery, null);
            if (c != null) {
                c.moveToFirst();
            }
            FileItem fileItem = new FileItem(
                    c.getString(c.getColumnIndex(PATH)),
                    c.getString(c.getColumnIndex(NAME))

            );
            c.close();
            return fileItem.getName();
        } else {
            String selectQuery = "SELECT * FROM " + TABLE_SECRETLAYER
                    + " WHERE " + PATH + " = '" + path + "'";
            Cursor c = db.rawQuery(selectQuery, null);
            if (c != null) {
                c.moveToFirst();
            }
            FileItem fileItem = new FileItem(
                    c.getString(c.getColumnIndex(PATH)),
                    c.getString(c.getColumnIndex(NAME))

            );
            c.close();
            return fileItem.getName();
        }
    }

    // delete item by path
    // i=0 -> normal layer; i=1 -> secret layer
    public void deleteFileItem(String path, int i) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (i == 0) {
            db.delete(TABLE_NORMALLAYER,
                    PATH + " = ?",
                    new String[]{String.valueOf(path)});
            Log.d(TAG, "deleteNote: ");
        } else {
            db.delete(TABLE_SECRETLAYER,
                    PATH + " = ?",
                    new String[]{String.valueOf(path)});
            Log.d(TAG, "deleteNote: ");
        }
    }
}

