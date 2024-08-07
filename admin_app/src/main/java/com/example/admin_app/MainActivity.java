package com.example.admin_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildDialog();


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

                        if (name.equals("Sti admin") && pass.equals("adminpass")){

                            startActivity(new Intent(MainActivity.this, HomeView.class));

                        }
                        // String username = name.getText().toString();
                        // String year = yearlvl.getText().toString();
                        // Intent intent = new Intent(pen.this, Class0.class);
                        // intent.putExtra("keyname", username);
                        //intent.putExtra("year", year);

                    }
                });

      dialog = builder.create();

      dialog.show();

    }
}