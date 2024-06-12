package com.apps.kunalfarmah.omrscanner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class StartActivity extends AppCompatActivity {

    Button scan,answer;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    private BottomNavigationView bottomNavigationView;

    boolean isCamera = true;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        scan = findViewById(R.id.scan);
        answer = findViewById(R.id.setAnswers);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:{
                        Toast.makeText(getApplicationContext(), "Home Selected", Toast.LENGTH_SHORT).show();
                    }
                    case R.id.scans:{
                        Toast.makeText(getApplicationContext(), "Scan Selected", Toast.LENGTH_SHORT).show();
                    }
                    case R.id.classes:{
                        Toast.makeText(getApplicationContext(), "Classes Selected", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        scan.setOnClickListener(v -> {
            Dialog dialog = new Dialog(StartActivity.this);
            dialog.setContentView(R.layout.selection_dialog);
            dialog.findViewById(R.id.camera).setOnClickListener(view->{isCamera=true; dialog.cancel(); openActivity();});
            dialog.findViewById(R.id.gallery).setOnClickListener(view->{isCamera=false; dialog.cancel();openActivity();});
            dialog.show();
//            openActivity();
        });


        answer.setOnClickListener(v -> startActivity(new Intent(StartActivity.this, AnswersActivity.class)));
    }

    public void openActivity() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.putExtra("isCamera", isCamera);
        startActivity(intent);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Home) {
                    startActivity(new Intent(StartActivity.this, StartActivity.class));
                }
                else if (id == R.id.classes) {
                    startActivity(new Intent(StartActivity.this, pen.class));
                }
                return false;
            }

        });


        }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }
}