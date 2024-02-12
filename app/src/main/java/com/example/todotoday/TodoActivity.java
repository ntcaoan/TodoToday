package com.example.todotoday;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity implements TodoSelectListener {

    private TextView tvUsername;
    private FloatingActionButton btnAddTask;
    private CheckBox checkBox;
    private TextView tvTask;

    //Database connect with username to show the task based on username
    private TodoDBHelper dbHelper;
    private String username;


    private static final int ADD_TASK_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);


        //set up toolbar with the "Erase All" to reset all the todo list
        Toolbar tb = findViewById(R.id.hehe_toolbar);
        tb.setTitle("TodoToday ٩( ᐛ )و");
        tb.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tb);

        /**
         * Database
         */
        dbHelper = new TodoDBHelper(this);



        //set up the name after the user enter their Username
        tvUsername = findViewById(R.id.tvUsername);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        tvUsername.setText(username);

//        // Retrieve the user's tasks from the database
//        ArrayList<Todo> tasks = dbHelper.getTasksForUser(username);

        btnAddTask = findViewById(R.id.btnAddTask);

        // Event handler for button to add more tasks
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open task input window
                Intent intent = new Intent(TodoActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST_CODE);
            }
        });

        // Pass the tasks to the TodoListFragment
        FragmentManager manager = getSupportFragmentManager();
        TodoListFragment listFragment = (TodoListFragment) manager.findFragmentById(R.id.listFragment);
        listFragment.setTodoSelectListener(this);


        /**
         * Notification
         */
        Intent intent1 = new Intent(this, TodoNotification.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1000, intent1, PendingIntent.FLAG_CANCEL_CURRENT);

        //schedule the pending intent / service for 10 sec in the future using Alarm Manager
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, pendingIntent);



    }


    /**
     * Open the Camera App in the Phone to take picture
     * @param view
     */
    public void openCamera(View view) {
        // Open the camera window
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }


    /**
     *
     * 2 if statements
     *                  1st if - relate with AddTaskActivity, update the task name after get added
     *                  2nd if - relate with the Camera App of the Phone, after capture a pic the status of the checkbox will change
     *
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //update the task name after get added
        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String taskName = data.getStringExtra("taskName");
            TodoListFragment todoListFragment = (TodoListFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);
            if (todoListFragment != null) {
                int taskPosition = todoListFragment.getTaskCount();
                todoListFragment.addTask(taskName, taskPosition);
            }
        }

        //after capture a pic the status of the checkbox will change
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            checkBox = findViewById(R.id.checkBox);
            checkBox.setChecked(true);
            //if checkBox status == true, the boolean done in Todo class will change to true not false


        }
    }


    /**
     * Populates the toolbar
     * @param menu The options menu in which you place your items.
     *
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubuthaveerase, menu);

        //Setting Color style for the MenuItem
        MenuItem menuItem = menu.findItem(R.id.btnErase);

        SpannableString spannableString = new SpannableString(menuItem.getTitle());
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannableString.length(), 0);
        menuItem.setTitle(spannableString);

        return true;
    }


    /**
     *  For the menuitem after get clicked "Erase all" it will reset all the todo list
     * @param menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int selectedId = menuItem.getItemId();
        if (selectedId == findViewById(R.id.btnErase).getId()) {
            recreate(); //reload the main page - erase all the todolist
            return true;
        }
        return false;
    }


    /**
     * This one for fixing (edit or add more bla bla) the Task that user just enter
     * @param task
     */
    @Override
    public void setSelectedTask(Todo task) {
        Toast.makeText(this, task.task, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, AddTaskActivity.class);

        this.startActivity(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection when the activity is destroyed
        dbHelper.close();
    }
}