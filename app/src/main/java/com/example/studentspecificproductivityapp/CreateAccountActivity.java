package com.example.studentspecificproductivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAccountActivity extends AppCompatActivity {
    TextView signInRedirect;
    Button createAccountButton;
    EditText createAccountEmail, createAccountPassword;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signInRedirect = findViewById(R.id.signInRedirectText);
        createAccountButton = findViewById(R.id.createAccountButton);
        createAccountEmail = findViewById(R.id.createAccountEmail);
        createAccountPassword = findViewById(R.id.createAccountPassword);

        db = new DatabaseHelper(this);

        // creates new user and moves to sign in page
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user;
                String userEmail = createAccountEmail.getText().toString();
                String userPassword = createAccountPassword.getText().toString();

                if (db.checkEmail(userEmail))
                    Toast.makeText(CreateAccountActivity.this, "Email is already in use. Please login or try a different email.", Toast.LENGTH_SHORT).show();
                // validates that none of the fields are empty
                 else if (!(userEmail.isEmpty() || userPassword.isEmpty()))
                {
                    user = new User(userEmail, userPassword);

                    boolean success;
                    try (DatabaseHelper databaseHelper = new DatabaseHelper(CreateAccountActivity.this)) {

                        success = databaseHelper.addUser(user);
                    }

                    Toast.makeText(CreateAccountActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();

                    if (success)
                    {
                        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Invalid Account Details. Please try again.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        // redirects to sign in page
        signInRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}