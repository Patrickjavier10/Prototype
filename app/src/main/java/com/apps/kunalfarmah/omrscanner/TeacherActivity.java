package com.apps.kunalfarmah.omrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);





    ListView listView = findViewById(R.id.list_item);
    List<String> list = new ArrayList<>();




        list.add("Santos, Richard");
       list.add("Salem, Ron Allan");
        list.add("Gascon, Salvador");
        list.add("Cosme, Mateo");
        list.add("Nuque, Jessie");
        list.add("Perpetua, John Robert");
        list.add("Macaspac, Lean ");
        list.add("...");




    ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);


        listView.setAdapter(arrayAdapter);

        listView.setBackgroundColor(Color.BLUE);






        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                startActivity(new Intent(TeacherActivity.this, Class0.class));
            }
            if (position == 1) {
                startActivity(new Intent(TeacherActivity.this, Class0.class));
            }


        }
            });


        }


        }







