package com.example.todotoday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private ArrayList<Todo> tasks;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    //constructor
    public TodoListAdapter (Context context, ArrayList<Todo> tasks, ItemClickListener listener)
    {
        this.mInflater = LayoutInflater.from(context); //will inflate from XML our layout

        this.tasks = tasks;

        this.mClickListener = listener;
    }


    /*
   Inflates the layout we created that will show one row of data
    */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.todo_layout, parent, false);

        return new ViewHolder(view);
    }

    /*
Binds the data to the TextView in each row
*/
    @Override
    public void onBindViewHolder(ViewHolder holder, int nPos)
    {
        Todo task = tasks.get(nPos);

        holder.tvTasks.setText(task.task);
    }

    /*
    Total number of rows
     */
    @Override
    public int getItemCount()
    {
        return tasks.size();
    }



    //In the CourseListAdapter add methods to help the adapter deal with clicks:
    public Todo getItem(int nId)
    {
        return tasks.get(nId);
    }

    //allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener;
    }




    /**
     * Create an inside class named ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvTasks;

        public ViewHolder(View itemView)
        {
            super(itemView);

            tvTasks = itemView.findViewById(R.id.tvTaskLayout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
            {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

    }


}
