package com.Stardust.cabicat.helper;

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
    private static final String DATABASE_NAME = "cabicat.db";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_NORMALLAYER = "normallayer";
    private static final String TABLE_SECRETLAYER = "secretlayer";

    // Column names for both TABLE_NORMALLAYER and TABLE_SECRETLAYER
    private static final String PATH = "path";
    private static final String NAME = "name";
    private static final String SIZE = "size";
    private static final String ADDEDDATE = "addeddate";
    private static final String CHECKDATE = "checkdate";
    private static final String PRIORITY = "priority";
    private static final String TIMES = "times";

    // TABLE_NORMALLAYER table create statements
    private static final String CREATE_TABLE_NORMALLAYER =
            "CREATE TABLE " + TABLE_NORMALLAYER
                    + "("
                    + PATH + " TEXT PRIMARY KEY,"
                    + NAME + " TEXT,"
                    + SIZE + " LONG,"
                    + ADDEDDATE + " DATETIME,"
                    + CHECKDATE + " DATETIME,"
                    + PRIORITY + " INTEGER,"
                    + TIMES + " INTEGER"
                    + ")";

    // TABLE_SECRETLAYER table create statements
    private static final String CREATE_TABLE_SECRETLAYER =
            "CREATE TABLE " + TABLE_SECRETLAYER
                    + "("
                    + PATH + " TEXT PRIMARY KEY,"
                    + NAME + " TEXT,"
                    + SIZE + " LONG,"
                    + ADDEDDATE + " DATETIME,"
                    + CHECKDATE + " DATETIME,"
                    + PRIORITY + " INTEGER,"
                    + TIMES + " INTEGER"
                    + ")";

    // Singleton instance
    private static DatabaseHelper sInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    // while create
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NORMALLAYER);
        db.execSQL(CREATE_TABLE_SECRETLAYER);
    }

    // while upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NORMALLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECRETLAYER);

        onCreate(db);
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
        values.put(TIMES, fileItem.getTimes());
        Log.d(TAG, "createFileItem: " + fileItem.getPath());
        switch (i) {
            case 0:
                return db.insert(TABLE_NORMALLAYER, null, values);
            case 1:
                return db.insert(TABLE_SECRETLAYER, null, values);
            default:
                return -1;
        }
    }

    // get all the fileitems in a layer
    // i=0 -> normal layer; i=1 -> secret layer
    public List<FileItem> getAllItems(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<FileItem> fileitemList = new ArrayList<>();
        switch (i) {
            case 0:
                String selectQuery0 = "SELECT * FROM " + TABLE_NORMALLAYER + " ORDER BY " + NAME + "," + PATH;
                Cursor c0 = db.rawQuery(selectQuery0, null);
                if (c0.moveToFirst()) {
                    do {
                        FileItem fileItem = new FileItem(
                                c0.getString(c0.getColumnIndex(NAME)),
                                c0.getString(c0.getColumnIndex(PATH)),
                                c0.getLong(c0.getColumnIndex(SIZE)),
                                c0.getString(c0.getColumnIndex(ADDEDDATE)),
                                c0.getString(c0.getColumnIndex(CHECKDATE)),
                                c0.getInt(c0.getColumnIndex(PRIORITY)),
                                c0.getInt(c0.getColumnIndex(TIMES))
                        );
                        fileitemList.add(fileItem);
                    } while (c0.moveToNext());
                }
                c0.close();
                break;
            case 1:
                String selectQuery1 = "SELECT * FROM " + TABLE_SECRETLAYER + " ORDER BY " + NAME + "," + PATH;
                Cursor c1 = db.rawQuery(selectQuery1, null);
                if (c1.moveToFirst()) {
                    do {
                        FileItem fileItem = new FileItem(
                                c1.getString(c1.getColumnIndex(NAME)),
                                c1.getString(c1.getColumnIndex(PATH)),
                                c1.getLong(c1.getColumnIndex(SIZE)),
                                c1.getString(c1.getColumnIndex(ADDEDDATE)),
                                c1.getString(c1.getColumnIndex(CHECKDATE)),
                                c1.getInt(c1.getColumnIndex(PRIORITY)),
                                c1.getInt(c1.getColumnIndex(TIMES))
                        );
                        fileitemList.add(fileItem);
                    } while (c1.moveToNext());
                }
                c1.close();
                break;
            default:
                break;
        }
        return fileitemList;
    }

    // i=0 -> normal layer; i=1 -> secret layer
    // get a particular item by path
    public FileItem getFileItemByPath(String path, int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        switch (i) {
            case 0:
                String selectQuery0 = "SELECT * FROM " + TABLE_NORMALLAYER
                        + " WHERE " + PATH + " = '" + path + "'";
                Cursor c0 = db.rawQuery(selectQuery0, null);
                if (c0 != null) {
                    c0.moveToFirst();
                }
                FileItem fileItem0 = new FileItem(
                        c0.getString(c0.getColumnIndex(NAME)),
                        c0.getString(c0.getColumnIndex(PATH)),
                        c0.getLong(c0.getColumnIndex(SIZE)),
                        c0.getString(c0.getColumnIndex(ADDEDDATE)),
                        c0.getString(c0.getColumnIndex(CHECKDATE)),
                        c0.getInt(c0.getColumnIndex(PRIORITY)),
                        c0.getInt(c0.getColumnIndex(TIMES))
                );
                c0.close();
                return fileItem0;

            case 1:
                String selectQuery1 = "SELECT * FROM " + TABLE_SECRETLAYER
                        + " WHERE " + PATH + " = '" + path + "'";
                Cursor c1 = db.rawQuery(selectQuery1, null);
                if (c1 != null) {
                    c1.moveToFirst();
                }
                FileItem fileItem1 = new FileItem(
                        c1.getString(c1.getColumnIndex(NAME)),
                        c1.getString(c1.getColumnIndex(PATH)),
                        c1.getLong(c1.getColumnIndex(SIZE)),
                        c1.getString(c1.getColumnIndex(ADDEDDATE)),
                        c1.getString(c1.getColumnIndex(CHECKDATE)),
                        c1.getInt(c1.getColumnIndex(PRIORITY)),
                        c1.getInt(c1.getColumnIndex(TIMES))
                );

                c1.close();
                return fileItem1;
            default:
                return new FileItem();
        }
    }


    // delete item by path
    // i=0 -> normal layer; i=1 -> secret layer
    public void deleteFileItem(String path, int i) {
        SQLiteDatabase db = this.getWritableDatabase();

        switch (i) {
            case 0:
                db.delete(TABLE_NORMALLAYER,
                        PATH + " = ?",
                        new String[]{String.valueOf(path)});
                break;
            case 1:
                db.delete(TABLE_SECRETLAYER,
                        PATH + " = ?",
                        new String[]{String.valueOf(path)});
                break;
            default:
                break;
        }

        Log.d(TAG, "delete: " + path);
    }

    // delete all the info in the secret layer
    public void deleteSecret(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SECRETLAYER,null,null);
    }

    // get related 10 files ordered by added date
    public List<FileItem> getNewestFile() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<FileItem> fileitemList = new ArrayList<>();
        String selectQuery0 = "SELECT * FROM " + TABLE_NORMALLAYER + " ORDER BY " + ADDEDDATE + " DESC"+" LIMIT 10 ";
        Cursor c0 = db.rawQuery(selectQuery0, null);
        if (c0.moveToFirst()) {
            do {
                FileItem fileItem = new FileItem(
                        c0.getString(c0.getColumnIndex(NAME)),
                        c0.getString(c0.getColumnIndex(PATH)),
                        c0.getLong(c0.getColumnIndex(SIZE)),
                        c0.getString(c0.getColumnIndex(ADDEDDATE)),
                        c0.getString(c0.getColumnIndex(CHECKDATE)),
                        c0.getInt(c0.getColumnIndex(PRIORITY)),
                        c0.getInt(c0.getColumnIndex(TIMES))
                );
                fileitemList.add(fileItem);
            } while (c0.moveToNext());
        }
        c0.close();
        return fileitemList;
    }

    // get related 10 files ordered by check date
    public List<FileItem> getRecentCheckedFile() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<FileItem> fileitemList = new ArrayList<>();
        String selectQuery0 = "SELECT * FROM " + TABLE_NORMALLAYER + " ORDER BY " + CHECKDATE+ " DESC"+" LIMIT 10 ";
        Cursor c0 = db.rawQuery(selectQuery0, null);
        if (c0.moveToFirst()) {
            do {
                FileItem fileItem = new FileItem(
                        c0.getString(c0.getColumnIndex(NAME)),
                        c0.getString(c0.getColumnIndex(PATH)),
                        c0.getLong(c0.getColumnIndex(SIZE)),
                        c0.getString(c0.getColumnIndex(ADDEDDATE)),
                        c0.getString(c0.getColumnIndex(CHECKDATE)),
                        c0.getInt(c0.getColumnIndex(PRIORITY)),
                        c0.getInt(c0.getColumnIndex(TIMES))
                );
                fileitemList.add(fileItem);
            } while (c0.moveToNext());
        }
        c0.close();
        return fileitemList;
    }

}