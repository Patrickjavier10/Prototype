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

public class Class0 extends AppCompatActivity {
    Button button;
    LinearLayout Layout;

    AlertDialog dialog;
    BottomNavigationView bottomNavigationView;

    TextView section, yearlvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class0);

        buildDialog();
        Layout = findViewById(R.id.container);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        button = findViewById(R.id.AddClass);

        section = findViewById(R.id.section);
     //   yearlvl = findViewById(R.id.year);


        String username = getIntent().getStringExtra("keyname");
        String year = getIntent().getStringExtra("year");
        section.setText(username );
       // yearlvl.setText(year);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

            }
        });



}











        private void buildDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.layout_student, null);
            EditText name = view.findViewById(R.id.studentName);

            builder.setView(view)
                    .setTitle(Html.fromHtml("<font color='#000080' >"+"Student Name"))
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    })

                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addcard(name.getText().toString());


                        }

                    });


            dialog = builder.create();
        }
    private void addcard(String btnClass) {

        View view = getLayoutInflater().inflate(R.layout.list_student, null);
        TextView nameView = view.findViewById(R.id.className);

        nameView.setText(btnClass);


        Layout.addView(view);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Layout.removeView(view);
                return true;
            }
        });

    }
}