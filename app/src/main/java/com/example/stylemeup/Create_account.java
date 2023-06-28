package com.example.stylemeup;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Create_account extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword, editTextPasswordAgain;
    private Button buttonCreateAccount;
    Context context;
    Database_super_user db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordAgain = findViewById(R.id.editTextPasswordAgain);
        buttonCreateAccount = findViewById(R.id.buttonLogin);

        // Set click listener for the Create Account button
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String passwordAgain = editTextPasswordAgain.getText().toString();

                context = Create_account.this;
                db = new Database_super_user(context);
                // Insert the username and password into the database

                if (db.isUserInDb(username)) {
                    ErrorUtils.showErrorDialog(Create_account.this, "", "This username is already taken. Try another one.");
                    return;
                }
                if (!isPasswordValid(password)) {
                    ErrorUtils.showErrorDialog(Create_account.this, "", "Password should have a minimum of 8 characters, one uppercase letter, and one special character.");
                    return;
                }
                if (password.equals(passwordAgain)) {
                    db.insertInDb(username, password);
                    ErrorUtils.showErrorDialog(Create_account.this, "", "Your account has been created successfully!.");

                }
                else {
                    ErrorUtils.showErrorDialog(Create_account.this, "", "The passwords don't match. Please try again.");
                }

                // Clear the input fields
                editTextUsername.setText("");
                editTextPassword.setText("");
                editTextPasswordAgain.setText("");
            }
        });
    }
    private boolean isPasswordValid(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+])(?=.*[0-9])(?=.*[a-z]).{8,}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(password);
        return matcher.matches();
    }
}

