package com.apps.kunalfarmah.omrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminPage extends AppCompatActivity {

    CardView iv, bc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
         iv = findViewById(R.id.figure12);
       //  bc = findViewById(R.id.figure23);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                  startActivity(new Intent(AdminPage.this, TeacherActivity.class));
              }
            });

    }
}