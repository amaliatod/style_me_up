package com.example.stylemeup;

import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

public class Delete_account extends AppCompatActivity {


    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonDelete;
    Context context;
    Database_super_user db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_account);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonDelete = findViewById(R.id.buttonDelete);


        // Set click listener for the Create Account button
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                context = Delete_account.this;
                db = new Database_super_user(context);
                // Insert the username and password into the database
                if (db.isValidAccount(username, password)) {
                    db.deleteAccount(username);
                    ErrorUtils.showErrorDialog(Delete_account.this, "Account deleted", "We are sorry that you leave. Hope to see you again soon! :)");

                } else {
                    ErrorUtils.showErrorDialog(Delete_account.this, "", "This is not a valid account. Please try again.");
                }

                // Clear the input fields
                editTextUsername.setText("");
                editTextPassword.setText("");
            }
        });
    }
}