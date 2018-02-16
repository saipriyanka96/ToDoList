package com.example.layout.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ToDoListAdapter {
    //Exposes methods to manage a SQLite database.
    //SQLiteDatabase has methods to create, delete, execute SQL commands, and perform other common database management tasks.
    private SQLiteDatabase sqLiteDatabase;
    //Interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system.
    private Context context;
    TodoListHelper helper;

    public ToDoListAdapter(Context c) {
        context = c;
        helper = new TodoListHelper(c);
    }

    public ToDoListAdapter open() throws SQLException {
        //opens the adapter and throws the sql exception
        //An exception that provides information on a database access error or other errors.
        helper = new TodoListHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();
        //getWritableDatabase()
        //Create and/or open a database that will be used for reading and writing.
        return this;
        //returns the adapter
    }

    public void close() {
        helper.close();
    }
//to close the helper

    public long insertData(String title, String description, String date, Integer status) {
        //insert the data in database with tje variables
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //This class is used to store a set of values that the ContentResolver can process.
        //Adds a value to the set.
        //Parameters
       // key	String: the name of the value to put
       // value	Long: the data for the value to put
        contentValues.put(TodoListHelper.COL_TITLE, title);
        contentValues.put(TodoListHelper.COL_DESCRIPTION, description);
        contentValues.put(TodoListHelper.COL_DATE, date);
        contentValues.put(TodoListHelper.COL_STATUS, status);
        //insert the values into the respective tabel and whose value is null
        long id = db.insert(TodoListHelper.TABLE_NAME, null, contentValues);
        return id;
        //returns the id
    }

    public Cursor fetch(Integer status) {
        //statis of the list
        //columns which we need in string
        //cursor will take the values from the sqlite database and rows will be queried
        String[] columns = new String[]{TodoListHelper.COL_ID, TodoListHelper.COL_TITLE, TodoListHelper.COL_DESCRIPTION, TodoListHelper.COL_DATE, TodoListHelper.COL_STATUS};
        Cursor cursor = sqLiteDatabase.query(TodoListHelper.TABLE_NAME, columns, TodoListHelper.COL_STATUS + "=" + status, null, null, null, TodoListHelper.COL_DATE + " ASC");
        if (cursor != null) {
        }
        return cursor;
        //if cursor is not null then return the value
    }

    public Cursor fetch() {
        //here we will get the values into cursor
        String[] columns = new String[]{TodoListHelper.COL_ID, TodoListHelper.COL_TITLE, TodoListHelper.COL_DESCRIPTION, TodoListHelper.COL_DATE, TodoListHelper.COL_STATUS};
        Cursor cursor = sqLiteDatabase.query(TodoListHelper.TABLE_NAME, columns, null, null, null, null, TodoListHelper.COL_DATE + " ASC");
        if (cursor != null) {
        }
        return cursor;
    }

    public void update(Integer _id, String title, String description, String date, Integer status) {
        //updating the database
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoListHelper.COL_TITLE, title);
        contentValues.put(TodoListHelper.COL_DESCRIPTION, description);
        contentValues.put(TodoListHelper.COL_DATE, date);
        contentValues.put(TodoListHelper.COL_STATUS, status);

        int i = sqLiteDatabase.update(TodoListHelper.TABLE_NAME, contentValues, TodoListHelper.COL_ID + " = " + _id, null);
       if (i!=-1){
           Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
       }else {
           Toast.makeText(context, "not updated", Toast.LENGTH_SHORT).show();
       }
        //returns the database
    }

    public void delete(long _id) {
        //delete the respective list valu
        sqLiteDatabase.delete(TodoListHelper.TABLE_NAME, TodoListHelper.COL_ID + " = " + _id, null);
    }

    class TodoListHelper extends SQLiteOpenHelper {
        //A helper class to manage database creation and version management.
        //giving title to each column
        public static final String DATABASE_NAME = "tododatabase";
        public static final String TABLE_NAME = "todotable";
        public static final String COL_ID = "keyid";
        public static final String COL_TITLE = "keytitle";
        public static final String COL_DESCRIPTION = "keydescription";
        public static final String COL_DATE = "keydate";
        public static final String COL_STATUS = "keystatus";
        public static final int DATABASE_VERSION = 3;
        //database version
        //creating the table with the parameters
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TITLE + " TEXT, " + COL_DESCRIPTION + " TEXT, " + COL_DATE + " TEXT, " + COL_STATUS + " INTEGER DEFAULT '0' " + " ) ";
        public static final String DROP_TABLE = "DROP TABLE IF EXITS" + TABLE_NAME;
//drop/delete the table if not exits
        public TodoListHelper(Context context) {
            //context will have database name and version
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //creates the database and get the table if not send the exception
            //Called when the database is created for the first time. This is where the creation of tables and the initial population of the tables should happen.
            //Parameters db	SQLiteDatabase: The database.
            try {
                //executed the table
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
//Called when the database needs to be upgraded. The implementation should use this method to drop tables, add tables, or do anything else it needs to upgrade to the new schema version.
        //Parameters db	SQLiteDatabase: The database.
       // oldVersion	int: The old database version.
        //newVersion	int: The new database version.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}