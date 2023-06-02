package rlin.com.rlintodolist.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private static final String TAG = "DBHelper";

    // Setting
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tasks";

    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;

    // Column Names
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DUE_DATE = "due_date";
    public static final String KEY_PRIORITY = "priority";

    // Column index
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_DESCRIPTION = 2;
    public static final int COLUMN_DUE_DATE = 3;
    public static final int COLUMN_PRIORITY = 4;

    // Insert statement
    private static final String INSERT =
            "INSERT INTO " + TABLE_NAME + "(" +
                    KEY_TITLE + ", " +
                    KEY_DESCRIPTION + ", " +
                    KEY_DUE_DATE + ", " +
                    KEY_PRIORITY + ") values (?, ?, ?, ?)";

    public DBHelper(Context context) throws Exception {
        this.context = context;

        try {
            OpenHelper openHelper = new OpenHelper(this.context);

            // Open a database for reading and writing
            db = openHelper.getWritableDatabase();

            // compile a sqlite insert statement into re-usable statement object
            insertStmt = db.compileStatement(INSERT);
        } catch (Exception e) {
            Log.e(TAG, " DBHelper constructor: could not get database " + e.getMessage());
            throw (e);
        }
    }

    public long insert(Task newTask) {
        // bind values to the pre-compiled SQL statement "insertStmt"
        insertStmt.bindString(COLUMN_TITLE, newTask.getTitle());
        insertStmt.bindString(COLUMN_DESCRIPTION, newTask.getDescription());
        insertStmt.bindString(COLUMN_DUE_DATE, newTask.getDueDate());
        insertStmt.bindString(COLUMN_PRIORITY, newTask.getPriority());

        long value = -1;
        try {
            // execute the sqlite statement
            value = insertStmt.executeInsert();

        } catch(Exception e) {
            Log.e(TAG, " execute insert problem: " + e.getMessage());
        }

        Log.d(TAG, " successfully insert value = " + value);
        return value;
    }

    public void deleteAll() {
        db.delete(TABLE_NAME, null, null);
    }

    public boolean deleteTaskById(long id) {
        return db.delete(TABLE_NAME, KEY_ID + "=" + id, null) > 0;
    }

    public List<Task> selectAll() {
        List<Task> list = new ArrayList<>();

        // query takes the following parameters
        // dbName :  the table name
        // columnNames:  a list of which table columns to return
        // whereClause:  filter of selection of data;  null selects all data
        // selectionArg: values to fill in the ? if any are in the whereClause
        // group by:   Filter specifying how to group rows, null means no grouping
        // having:  filter for groups, null means none
        // orderBy:  Table columns used to order the data, null means no order.

        // A Cursor provides read-write access to the result set returned by a database query.
        // A Cursor represents the result of the query and points to one row of the query result.
        Cursor cursor = db.query(TABLE_NAME,
                new String[] { KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_DUE_DATE, KEY_PRIORITY },
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setTitle(cursor.getString(COLUMN_TITLE));
                task.setDescription(cursor.getString(COLUMN_DESCRIPTION));
                task.setDueDate(cursor.getString(COLUMN_DUE_DATE));
                task.setPriority(cursor.getString(COLUMN_PRIORITY));
                task.setId(cursor.getLong(COLUMN_ID));

                list.add(task);
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return list;
    }

    // Helper class for DB creation/update
    // SQLiteOpenHelper provides getReadableDatabase() and getWriteableDatabase() methods
    // to get access to an SQLiteDatabase object; either in read or write mode.
    private static class OpenHelper extends SQLiteOpenHelper {

        private static final String TAG = "OpenHelper";
        private static final String CREATE_TABLE =
                "CREATE TABLE " +
                        TABLE_NAME +
                        " (" + KEY_ID + " integer primary key autoincrement, " +
                        KEY_TITLE + " TEXT, " +
                        KEY_DESCRIPTION + " TEXT, " +
                        KEY_DUE_DATE + " TEXT, " +
                        KEY_PRIORITY + " TEXT);";

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, " onCreate");
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Log.e(TAG, " onCreate: Could not create SQL database");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "onUpgrade: this will drop tables and recreate database");

            try {

            } catch (Exception e) {
                Log.e(TAG, " onUpgrade: Could not update SQL database " + e.getMessage());
            }
        }
    }

}
