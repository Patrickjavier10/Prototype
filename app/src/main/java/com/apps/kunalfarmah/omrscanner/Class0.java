package com.apps.kunalfarmah.omrscanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Class0 extends AppCompatActivity {
    Button button;
    LinearLayout Layout;

    AlertDialog dialog;
    BottomNavigationView bottomNavigationView;

    TextView section;

    // Create an instance of DatabaseHelper1
    ClassDatabaseHelper databaseHelper1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class0);

        // Initialize the DatabaseHelper1 instance
        databaseHelper1 = new ClassDatabaseHelper(this);

        buildDialog();
        Layout = findViewById(R.id.container);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        button = findViewById(R.id.AddClass);
        section = findViewById(R.id.section);

        String username = getIntent().getStringExtra("keyname");
        section.setText(username);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        // Load saved students from the database and display them
        loadSavedStudents();
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_student, null);
        EditText name = view.findViewById(R.id.studentName);

        builder.setView(view)
                .setTitle(Html.fromHtml("<font color='#000080'>Student Name</font>"))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addStudent(name.getText().toString());
                    }
                });

        dialog = builder.create();
    }

    private void addStudent(String studentName) {
        if (!studentName.isEmpty()) {
            // Add student to the database
            databaseHelper1.addStudent(studentName);

            // Add the student to the UI
            addStudentCard(studentName);
        }
    }

    private void addStudentCard(final String studentName) {
        View view = getLayoutInflater().inflate(R.layout.list_student, null);
        TextView nameView = view.findViewById(R.id.className);

        nameView.setText(studentName);

        Layout.addView(view);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(Class0.this)
                        .setTitle("Delete Record")
                        .setMessage("Are you sure you want to delete this record?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Remove the student from the database
                                databaseHelper1.deleteStudent(studentName);

                                // Remove the view from the UI
                                Layout.removeView(view);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                return true;
            }
        });
    }

    private void loadSavedStudents() {
        // Retrieve all students from the database
        List<String> savedStudents = databaseHelper1.getAllStudents();

        // Add each student to the UI
        for (String studentName : savedStudents) {
            addStudentCard(studentName);
        }
    }
}
