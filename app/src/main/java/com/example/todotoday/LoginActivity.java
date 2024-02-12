package com.example.todotoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //attach the toolbar to the main activity / tell the activity to use the toolbar as the action bar
        Toolbar myToolbar = findViewById(R.id.todo_toolbar);
        myToolbar.setTitle("TodoToday ٩( ᐛ )و");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        //store information of Username and Password to Database
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        //start button function to open the TODOLIST
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            /**
             * When the button "Login" get clicked it will  - check if the username field is blank -> throw a Toast to remind user enter their name
             *                                              - check if the password field is blank -> throw a Toast to remind user enter their password
             *                                              - store the username and password into database
             * @param view
             */
            @Override
            public void onClick(View view) {
                // Get the entered username

                if (etUsername != null && etPassword != null) {
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();


                    // Check if the username is empty
                    if (TextUtils.isEmpty(username))
                    {
                        Toast.makeText(LoginActivity.this, "enter your username", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(password))
                    {
                        Toast.makeText(LoginActivity.this, "enter your password", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        // Check if the username exists in the database
                        if (isUserExists(username)) {
                            // Check if the password matches
                            TodoDBHelper tododb = new TodoDBHelper(LoginActivity.this);
                            if (tododb.checkPassword(username, password)) {
                                // Open TODOActivity with the username
                                Intent intent = new Intent(LoginActivity.this, TodoActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "invalid password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Username does not exist in the database
                            // You can choose to allow login with the entered password in this case
                            //Toast.makeText(LoginActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();


                            // Open TODOActivity with the entered username
                            Intent intent = new Intent(LoginActivity.this, TodoActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }


                    }
                }
                
            }
        });

    }

    /**
     * Take in the username to check if its match in database then check on the password
     * @param username
     * @return
     */
    private boolean isUserExists(String username) {
        TodoDBHelper databaseHelper = new TodoDBHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {TodoDBHelper.USERNAME};
        String selection = TodoDBHelper.USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TodoDBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        boolean userExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return userExists;
    }

}