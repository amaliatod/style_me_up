package com.example.stylemeup;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {


    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView create, delete;
    private Button buttonLogin;
    Context context;
    Database_super_user db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        create = findViewById(R.id.textViewCreateAccount);
        delete = findViewById(R.id.textViewDeleteAccount);

        // Set click listener for the Create Account button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                context = Login.this;
                db = new Database_super_user(context);
                // Insert the username and password into the database
                if (db.isValidAccount(username,password)) {
                    Intent intent = new Intent(Login.this, Super_user.class);
                    startActivity(intent);

                } else {
                    ErrorUtils.showErrorDialog(Login.this, "", "This is not a valid account. Please try again or create one.");
                }

                // Clear the input fields
                editTextUsername.setText("");
                editTextPassword.setText("");
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Create_account.class);
                startActivity(intent);
            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Delete_account.class);
                startActivity(intent);
            }

        });


    }
}
