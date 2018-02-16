package com.example.layout.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Pri on 9/15/2017.
 */

public class CompletedTaskActivity extends AppCompatActivity {
    ListviewAdapter listViewAdapter;
    ListView listView;
    Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_todo);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(CompletedTaskActivity.this);
//setmessage will set the alert diaog when we want to delete the completed task
                dialog.setMessage("Are you sure? You want to delete this task?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ToDoListAdapter db = new ToDoListAdapter(getApplicationContext()).open();
                       //opnes the adapter
                        String ids = ((TextView) view.findViewById(R.id.taskId)).getText().toString();
                        db.delete(Long.parseLong(ids));
                        //delete the databse once we click long press
                        Toast.makeText(CompletedTaskActivity.this, "Task is deleted.", Toast.LENGTH_LONG).
                                show();
                        updateUI();
                        db.close();
                    }
                });
                dialog.setNegativeButton("Cancel", null);
                dialog.create();
                //create the dialog and show the dialog
                dialog.show();
                return true;
                //returns value true
            }

        });
        updateUI();
    }

    private void updateUI() {
        ToDoListAdapter db = new ToDoListAdapter(getApplicationContext()).open();
        Cursor cursor = db.fetch(1);
        final ArrayList<Integer> id = new ArrayList<>();
        final ArrayList<String> title = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> status = new ArrayList<>();

        while (cursor.moveToNext()) {
            id.add(cursor.getInt(cursor.getColumnIndex(ToDoListAdapter.TodoListHelper.COL_ID)));
            title.add(cursor.getString(cursor.getColumnIndex(ToDoListAdapter.TodoListHelper.COL_TITLE)));
            description.add(cursor.getString(cursor.getColumnIndex(ToDoListAdapter.TodoListHelper.COL_DESCRIPTION)));
            date.add(cursor.getString(cursor.getColumnIndex(ToDoListAdapter.TodoListHelper.COL_DATE)));
            status.add(cursor.getInt(cursor.getColumnIndex(ToDoListAdapter.TodoListHelper.COL_STATUS)));
        }
        listViewAdapter = new ListviewAdapter(this, id, title, description, date, status);
        listView.setAdapter(listViewAdapter);
        cursor.close();
        db.close();
    }
}