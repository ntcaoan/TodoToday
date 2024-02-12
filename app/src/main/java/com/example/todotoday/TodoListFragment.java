package com.example.todotoday;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoListFragment extends Fragment {

    private RecyclerView todoList;
    private RecyclerView.Adapter todoAdapter;
    private ItemClickListener mClickListener;
    private ArrayList<Todo> tasks;
    private TodoSelectListener listener;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View todoListLayout = inflater.inflate(R.layout.todo_list_fragment, container, false);

        //TODO
        tasks = new ArrayList<>();
        //create some dummy lists

//        tasks.add(
//                new Todo(
//                        "make some peanut butter cookies",
//                        false
//                )
//        );
//        tasks.add(
//                new Todo(
//                        "finish miss Michelle Tremblay's book",
//                        false
//                )
//        );
//        tasks.add(
//                new Todo(
//                        "picnic date <3",
//                        false
//                )
//        );
//        tasks.add(
//                new Todo(
//                        "Do Christeen Shlimoon",
//                        false
//                )
//        );


        todoList = (RecyclerView) todoListLayout.findViewById(R.id.todoList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        todoList.setLayoutManager(manager);
        todoAdapter = new TodoListAdapter(getContext(), tasks, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int nPosition) {
                Toast.makeText(getContext(), tasks.get(nPosition).task, Toast.LENGTH_SHORT).show();

            }
        });
        todoList.setAdapter(todoAdapter);

        return todoListLayout;
    }

    public void setTodoSelectListener(TodoSelectListener listener)
    {
        this.listener = listener;
    }

    public void addTask(String taskName, int taskPosition)
    {
        if (taskPosition >= 0 && taskPosition <= tasks.size()) {
            Todo newTask = new Todo(taskName, false);
            tasks.add(taskPosition, newTask);
            todoAdapter.notifyItemInserted(taskPosition);
        }
    }

    public int getTaskCount()
    {
        return tasks.size();
    }

}
