package com.example.layout.todolist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pri on 9/15/2017.
 */

public class ListviewAdapter extends BaseAdapter {
    //extends is the keyword used to inherit the properties of a class.
    //Common base class of common implementation for an Adapter that can be used in both ListView (by implementing the specialized ListAdapter interface)
    // and Spinner (by implementing the specialized SpinnerAdapter interface).
    private Activity context;
    //arraylist holding the integer
    private ArrayList<Integer> taskIds;
    private ArrayList<String> date;
    //arraylist holding the string
    private ArrayList<String> title;
    private ArrayList<String> description;
    private ArrayList<Integer> status;
    ListviewAdapter(Activity context, ArrayList<Integer> ids, ArrayList<String> title, ArrayList<String> description, ArrayList<String> date, ArrayList<Integer> status) {
        super();
        //super keyword in java is a reference variable which is used to refer immediate parent class object.
        //(this) works as a reference to the current Object whose Method or constructor is being invoked.
        this.taskIds = ids;
        this.context = context;
        this.date = date;
        this.title = title;
        this.description = description;
        this.status=status;
    }
    @Override
    //get the count of the ids size
    public int getCount() {
        return taskIds.size();
    }

    @Override
    //get the get the item
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        //viewholder will have all the textview that need to be shown
        TextView textViewId;
        TextView textViewDate;
        TextView textViewTitle;
        TextView textViewDescription;
        ImageView statusView;
        TextView textViewDuedate;
        TextView textViewStatus;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //Get a View that displays the data at the specified position in the data set
        //position	int: The position of the item within the adapter's data set of the item whose view we want.
       // convertView	View: The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate type before using. If it is not possible to convert this view to display the correct data, this method can create a new view. Heterogeneous lists can specify their number of view types, so that this View is always of the right type (see getViewTypeCount() and getItemViewType(int)).
       // parent	ViewGroup: The parent that this view will eventually be attached to
        //Returns
        //View	A View corresponding to the data at the specified position.

        ViewHolder holder;
        LayoutInflater layoutInflater = context.getLayoutInflater();

        if (view == null) {
            //if view is equal yo null then it will set the layout
            view = layoutInflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            //holder pobject holds the values that which need to be shown
            holder.textViewId = (TextView) view.findViewById(R.id.taskId);
            holder.textViewStatus = (TextView) view.findViewById(R.id.taskStatus);
            holder.textViewDate = (TextView) view.findViewById(R.id.date);
            holder.textViewTitle = (TextView) view.findViewById(R.id.title);
            holder.textViewDescription = (TextView) view.findViewById(R.id.description);
            holder.statusView = (ImageView) view.findViewById(R.id.status);
            holder.textViewDuedate = (TextView) view.findViewById(R.id.timestamp);
            view.setTag(holder);
            //Sets the tag associated with this view
            //tag	Object: an Object to tag the view with
        } else {
            holder = (ViewHolder) view.getTag();
            //get the tag into the holder
        }
        holder.textViewId.setText(String.valueOf(taskIds.get(position)));
        holder.textViewStatus.setText(String.valueOf(status.get(position)));
        holder.textViewDate.setText(date.get(position));
        holder.textViewTitle.setText(title.get(position));
        holder.textViewDescription.setText(description.get(position));
        if (status.get(position) == 0) {
            //if position is equal or not to 0 the set the image
            holder.statusView.setImageResource(R.drawable.ic_action_inc);
        } else {
            holder.statusView.setImageResource(R.drawable.ic_action_comp);
        }
        holder.textViewDuedate.setText(date.get(position));
        //set the date
        return view;
        //retuns the view
    }
}