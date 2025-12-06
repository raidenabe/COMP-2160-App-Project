package com.example.studentspecificproductivityapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {
    Button signOutButton, changeEmailButton, changePasswordButton;
    EditText newEmailText, newPasswordText;
    MaterialSwitch changeTheme;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SessionManagement sessionManagement = new SessionManagement(getContext());

        DatabaseHelper db = new DatabaseHelper(getContext());

        signOutButton = view.findViewById(R.id.signOutButton);
        changeEmailButton = view.findViewById(R.id.changeEmailButton);
        changePasswordButton = view.findViewById(R.id.changePasswordButton);
        changeTheme = view.findViewById(R.id.changeTheme);
        newEmailText = view.findViewById(R.id.newEmailEditText);
        newPasswordText = view.findViewById(R.id.newPasswordEditText);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManagement.logOut();
                startActivity(new Intent(getContext(), StartUpActivity.class));
            }
        });

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmail = newEmailText.getText().toString();
                if (Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                    db.changeEmail(sessionManagement.getUserId(), newEmail);
                    Toast.makeText(getContext(), "Email changed successfully.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordText.getText().toString();
                db.changePassword(sessionManagement.getUserId(), newPassword);
            }
        });

        // Sets initial state of the switch based on the current theme
        changeTheme.setChecked(sessionManagement.isDarkModeEnabled());

        // Listener for if the switch is on or off
        changeTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean b) {
                sessionManagement.saveTheme(b);
                if (b)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        return view;
    }
}