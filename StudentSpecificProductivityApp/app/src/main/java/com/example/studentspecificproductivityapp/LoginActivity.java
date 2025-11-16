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

public class LoginActivity extends AppCompatActivity {
    TextView createAccountRedirect;
    Button loginButton;
    EditText loginEmail, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton = findViewById(R.id.loginButton);
        createAccountRedirect = findViewById(R.id.createAccountRedirectText);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        SessionManagement sessionManagement = new SessionManagement(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // gets email and password from user
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                // makes sure email and password fields were not empty
                if (email.isEmpty() || password.isEmpty())
                    Toast.makeText(LoginActivity.this, "One of the fields is empty. Please try again.", Toast.LENGTH_SHORT).show();
                else {
                    // checks database for matching email and password
                    try (DatabaseHelper db = new DatabaseHelper(LoginActivity.this))
                    {
                        boolean checkEmailAndPassword = db.checkEmailPassword(email, password);

                        // if email and password are found and match in database, creates login session and sends to main activity
                        if (checkEmailAndPassword)
                        {
                            int userId = db.getUserIdByEmail(email);
                            sessionManagement.createLoginSession(userId,email);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Email and Password does not match. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        // redirects to create account activity
        createAccountRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}