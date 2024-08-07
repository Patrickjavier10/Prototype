package com.apps.kunalfarmah.omrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    Button btn;
    TextView tv;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buildDialog();

        edUsername = findViewById(R.id.editTextText);
        edPassword = findViewById(R.id.editTextText2);
        btn = findViewById(R.id.button);
        tv = findViewById(R.id.register);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                Database db = new Database(getApplicationContext(), "Checkmate", null, 1);


                if (username.length() == 0 || edPassword.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill All details", Toast.LENGTH_SHORT).show();

                } else {
                    if (db.login(username, password) == 1) {
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this, StartActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();

                    }

                }
            }


        });


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin, menu);


        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.admin) {
            dialog.show();


        }

        return true;
    }


    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_login, null);
        EditText name = view.findViewById(R.id.username);
        EditText pass = view.findViewById(R.id.password);


        builder.setView(view)
                .setTitle(Html.fromHtml("<font color='#000080' >" + "Admin Login"))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                })

                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name.getText().toString();
                        pass.getText().toString();

                        //  String a = "stiadmin";
                        //  String pass = "stiadmin";

                        if (name.equals(name) && pass.equals(pass)) {



                            startActivity(new Intent(LoginActivity.this, AdminPage.class));

                        }


                        // String username = name.getText().toString();
                        // String year = yearlvl.getText().toString();
                        // Intent intent = new Intent(pen.this, Class0.class);
                        // intent.putExtra("keyname", username);
                        //intent.putExtra("year", year);

                    }
                });

        dialog = builder.create();

    }

}

