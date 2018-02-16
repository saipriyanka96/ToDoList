package com.example.layout.todolist;
//Package objects contain version information about the implementation and specification of a Java package
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //public keyword is used in the declaration of a class,method or field;public classes,method and fields can be accessed by the members of any class.
//extends is for extending a class. implements is for implementing an interface
//AppCompatActivity is a class from e v7 appcompat library. This is a compatibility library that back ports some features of recent versions of
// Android to older devices.
    ListviewAdapter listViewAdapter;
    //An adapter manages the data model and adapts it to the individual entries in the widget.
    ListView listView;
    //ListView is a view group that displays a list of scrollable items
    public String title;
    ArrayList<Integer> ids = new ArrayList<>();
    ArrayList<String> titlelist = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<Integer> status = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Variables, methods, and constructors, which are declared protected in a superclass can be accessed only by the subclasses
        // in other package or any class within the package of the protected members class.
        //void is a Java keyword.  Used at method declaration and definition to specify that the method does not return any type,
        // the method returns void.
        //onCreate Called when the activity is first created. This is where you should do all of your normal static set up: create views,
        // bind data to lists, etc. This method also provides you with a Bundle containing the activity's previously frozen state,
        // if there was one.Always followed by onStart().
        //Bundle is most often used for passing data through various Activities.
// This callback is called only when there is a saved instance previously saved using onSaveInstanceState().
// We restore some state in onCreate() while we can optionally restore other state here, possibly usable after onStart() has
// completed.The savedInstanceState Bundle is same as the one used in onCreate().

        super.onCreate(savedInstanceState);
// call the super class onCreate to complete the creation of activity like the view hierarchy
        setContentView(R.layout.activity_main);
        //R means Resource
        //layout means design
        //  main is the xml you have created under res->layout->main.xml
        //  Whenever you want to change your current Look of an Activity or when you move from one Activity to another .
        // The other Activity must have a design to show . So we call this method in onCreate and this is the second statement to set
        // the design
        ///findViewById:A user interface element that displays text to the user.
        listView = (ListView) findViewById(R.id.list_todo);
        updateUI();
        //Interface definition for a callback to be invoked when an item in this view has been clicked and held.
        //Callback method to be invoked when an item in this view has been clicked and held. Implementers can call getItemAtPosition(position) if they need to access the data associated with the selected item.

       // Parameters
        //parent	AdapterView: The AbsListView where the click happened
        //view	View: The view within the AbsListView that was clicked
        //position	int: The position of the view in the list
        //id	long: The row id of the item that was clicked
               // Returns
//boolean	true if the callback consumed the long click, false otherwise

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoListAdapter myTodo = new ToDoListAdapter(getApplicationContext()).open();
//findViewById:A user interface element that displays text to the user.
                //getText:Return the text that TextView is displaying
                //toString():Returns a string representation of the object.
                //final keyword:it’s value can’t be modified,essentially, a constant.
                final String idText = ((TextView) view.findViewById(R.id.taskId)).getText().toString();
                final String statusText = ((TextView) view.findViewById(R.id.taskStatus)).getText().toString();
                final String titleText = ((TextView) view.findViewById(R.id.title)).getText().toString();
                final String descriptionText = ((TextView) view.findViewById(R.id.description)).getText().toString();
                final String dateText = ((TextView) view.findViewById(R.id.timestamp)).getText().toString();
                //The user-visible SDK version of the framework; its possible values are defined in Build.VERSION_CODES
                //Enumeration of the currently known SDK version codes. These are the values that can be found in SDK. Version numbers increment monotonically with each official platform release.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //Indicates whether some other object is "equal to" this one.
                    //here it checks the status of the text before we create the list the status will be zero once the list is created then the value will be one
                    if (Objects.equals(statusText, "0")) {
                        //here we convert string into integer  and
                        // valueOf(String s) − This returns an Integer object holding the value of the specified string representation.
                        myTodo.update(Integer.parseInt(String.valueOf(idText)), titleText, descriptionText, dateText, 1);
                        //Make a standard toast that just contains a text view with the text from a resource.

                        //Parameters
                       // context	Context: The context to use. Usually your Application or Activity object.
                        //resId	int: The resource id of the string resource to use. Can be formatted text.
                        //duration	int: How long to display the message. Either LENGTH_SHORT or LENGTH_LONG
                        Toast.makeText(MainActivity.this, "This task is marked complete.", Toast.LENGTH_SHORT).show();
                    } else {
                        //if the data it not added in list then it will be zero
                        myTodo.update(Integer.parseInt(String.valueOf(idText)), titleText, descriptionText, dateText, 0);
                        Toast.makeText(MainActivity.this, "This task is marked incomplete.", Toast.LENGTH_SHORT).show();
                    }
                }

                updateUI();
                myTodo.close();//closes the adapter
                return true;
            }
        });
       //Register a callback to be invoked when an item in this AdapterView has been clicked.

       // Parameters
        //listener	AdapterView.OnItemClickListener: The callback that will be invoked.
        //This value may be null.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           // Interface definition for a callback to be invoked when an item in this AdapterView has been clicked.
            /**Callback method to be invoked when an item in this AdapterView has been clicked.

             Implementers can call getItemAtPosition(position) if they need to access the data associated with the selected item.

             Parameters
             parent	AdapterView: The AdapterView where the click happened.
             view	View: The view within the AdapterView that was clicked (this will be a view provided by the adapter)
             position	int: The position of the view in the adapter.
             id	long: The row id of the item that was clicked.
**/
             @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog dialog = new Dialog(MainActivity.this);
                //it will call the main activity and layout
                dialog.setContentView(R.layout.layout_show_item_details);
                TextView textView = dialog.findViewById(R.id.textView);
                //it will show the text view in a dialog
                TextView textView2 = dialog.findViewById(R.id.textView2);
                TextView textView3 = dialog.findViewById(R.id.textView3);
                ImageView imageView = dialog.findViewById(R.id.imageView);
                textView.setText(titlelist.get(i));
                //here we get the postion of the textviews
                textView2.setText(description.get(i));
                textView3.setText(date.get(i));
                if(status.get(i)==0){
                    //if status is zero then it will show incomplete image
                    //setImageResource-is postion where our image is
                    imageView.setImageResource(R.drawable.ic_action_inc);
                }else if(status.get(i)==1){
                    //if status is one then it will show complete image
                    imageView.setImageResource(R.drawable.ic_action_comp);
                }
                dialog.show();
//it will show the task in the dialog
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Initialize the contents of the Activity's standard options menu. You should place your menu items in to menu.
        //Parameter menu	Menu: The options menu in which you place your items.
               // Returns boolean	You must return true for the menu to be displayed; if you return false it will not be shown.
        getMenuInflater().inflate(R.menu.menu, menu);
        //Returns a MenuInflater with this context.
        //Returns MenuInflater:This value will never be null.
        return super.onCreateOptionsMenu(menu);
        //returns the menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This hook is called whenever an item in your options menu is selected.
        switch (item.getItemId()) {
            //gets the item id
            case R.id.add:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                LayoutInflater inflater = getLayoutInflater();
                final View inflaterView = inflater.inflate(R.layout.dialog, null);
                dialog.setView(inflaterView);


                final EditText title = (EditText) inflaterView.findViewById(R.id.input_title);
                final EditText description = (EditText) inflaterView.findViewById(R.id.input_description);
                final DatePicker date = (DatePicker) inflaterView.findViewById(R.id.datePicker);

                dialog.setPositiveButton("Add", null);
                dialog.setNegativeButton("Cancel", null);

                final AlertDialog alertDialog = dialog.create();
                alertDialog.show();

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                    String getDate() {
                        StringBuilder builder = new StringBuilder();
                        builder.append(date.getDayOfMonth() + "/");
                        String dateMonth = String.valueOf(date.getMonth() + 1);
                        if (dateMonth.length() <= 1) {
                            dateMonth = "0" + dateMonth;
                        }
                        builder.append((dateMonth) + "/");
                        builder.append(date.getYear());
                        return builder.toString();
                    }

                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            if (Objects.equals(title.getText().toString(), "") && Objects.equals(description.getText().toString(), "")) {
                                title.setError("Title cannot be empty.");
                                description.setError("Description cannot be empty.");
                            } else if (Objects.equals(title.getText().toString(), "")) {
                                title.setError("Title cannot be empty.");
                            } else if (Objects.equals(description.getText().toString(), "")) {
                                description.setError("Description cannot be empty.");
                            } else {
                                String date = getDate();
                                final   ToDoListAdapter myDatabaseManager = new ToDoListAdapter(getApplicationContext());
                                myDatabaseManager.open();
                                myDatabaseManager.insertData(title.getText().toString(), description.getText().toString(), date, 0);
                                myDatabaseManager.close();
                                alertDialog.dismiss();
                                updateUI();
                                Toast.makeText(MainActivity.this, "New Task created.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                return true;
            case R.id.complete:
                Intent intent = new Intent(MainActivity.this, CompletedTaskActivity.class);
                startActivity(intent);
                return true;
                //once the data is entered and completes it's task then it will open in CompletedTaskActivity
            //starts the intent
            default:
                return super.onOptionsItemSelected(item);
                //default option is to open options item selected
        }
    }

    private void updateUI() {
        ToDoListAdapter db = new ToDoListAdapter(getApplicationContext()).open();
    //Return the context of the single, global Application object of the current process and opens the adapter
        Cursor cursor = db.fetch();
//cursor:This interface provides random read-write access to the result set returned by a database query.
        //here we get the values from the database
        //creating the array list for every coloumns and save the data in respective string
         ids = new ArrayList<>();
       titlelist = new ArrayList<>();
        description = new ArrayList<>();
         date = new ArrayList<>();
        status = new ArrayList<>();

        while (cursor.moveToNext()) {
            //Move the cursor to the next row.
            //This method will return false if the cursor is already past the last entry in the result set.
            //Returns:boolean	whether the move succeeded.
            //get the values from the cursors and add them to respective coloumns
            ids.add(cursor.getInt(cursor.getColumnIndex(ToDoListAdapter.TodoListHelper.COL_ID)));
            titlelist.add(cursor.getString(cursor.getColumnIndex(ToDoListAdapter.TodoListHelper.COL_TITLE)));
            description.add(cursor.getString(cursor.getColumnIndex(ToDoListAdapter.TodoListHelper.COL_DESCRIPTION)));
            date.add(cursor.getString(cursor.getColumnIndex(ToDoListAdapter.TodoListHelper.COL_DATE)));
            status.add(cursor.getInt(cursor.getColumnIndex(ToDoListAdapter.TodoListHelper.COL_STATUS)));
        }
        listViewAdapter = new ListviewAdapter(this, ids, titlelist, description, date, status);
        listView.setAdapter(listViewAdapter);
        //set the adapter
        cursor.close();
        //close the cursor and database
        db.close();
    }
}


