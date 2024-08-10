package com.apps.kunalfarmah.omrscanner;

import android.app.AlertDialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class pen extends AppCompatActivity {

    private LinearLayout layout;
    private AlertDialog dialog;
    private BottomNavigationView bottomNavigationView;
    private Button button;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pen);

        layout = findViewById(R.id.container);
        button = findViewById(R.id.AddClass);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Build the dialog for adding a class
        buildDialog();

        // Set up the bottom navigation
        setupBottomNavigation();

        // Set up the add class button
        setupAddClassButton();

        // Load saved classes from the database and display them
        loadSavedClasses();
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        Toast.makeText(pen.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(pen.this, StartActivity.class));
                        break;
                    case R.id.scans:
                        Toast.makeText(pen.this, "Scan Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(pen.this, Scan.class));
                        break;
                    case R.id.classes:
                        Toast.makeText(pen.this, "Classes Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(pen.this, pen.class));
                        break;
                }
                return false;
            }
        });
    }

    private void setupAddClassButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        EditText name = view.findViewById(R.id.btnClass);
        EditText yearlvl = view.findViewById(R.id.yrlvl);

        builder.setView(view)
                .setTitle(Html.fromHtml("<font color='#000080' >Add Class Name</font>"))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String className = name.getText().toString();
                        String yearLevel = yearlvl.getText().toString();

                        if (!className.isEmpty() && !yearLevel.isEmpty()) {
                            // Save the class to the database
                            databaseHelper.addClass(className, yearLevel);

                            // Add the class to the UI
                            addClassCard(className, yearLevel);
                        } else {
                            Toast.makeText(pen.this, "Please enter both class name and year level.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        dialog = builder.create();
    }

    private void addClassCard(String className, String yearLevel) {
        View view = getLayoutInflater().inflate(R.layout.list_item, null);
        TextView nameView = view.findViewById(R.id.className);
        TextView yearLevelView = view.findViewById(R.id.yearLevel);

        nameView.setText(className);
        yearLevelView.setText(yearLevel);

        layout.addView(view);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Remove the class from the database
                databaseHelper.deleteClass(className, yearLevel);

                // Remove the view from the UI
                layout.removeView(view);
                return true;
            }
        });
    }

    private void loadSavedClasses() {
        List<String[]> savedClasses = databaseHelper.getAllClasses();
        for (String[] classData : savedClasses) {
            addClassCard(classData[0], classData[1]);
        }
    }
}
