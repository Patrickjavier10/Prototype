package com.apps.kunalfarmah.omrscanner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class StartActivity extends AppCompatActivity  {

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

        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Home: {
                        Toast.makeText(StartActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartActivity.this, StartActivity.class));

                        break;
                    }
                    case R.id.scans: {
                        Toast.makeText(StartActivity.this, "Scan Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartActivity.this, Scan.class));
                        break;
                    }
                    case R.id.classes: {


                        //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScanFragment()).commit();
                        Toast.makeText(StartActivity.this, "Classes Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartActivity.this, pen.class));
                        break;
                    }
                }

                return false;
            }
        });


//if(savedInstanceState == null){
  //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScanFragment()).commit();
//}
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {

                    case R.id.Home:{
                        Toast.makeText(StartActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(StartActivity.this, StartActivity.class));

                        break;                    }
                    case R.id.scans:{
                        Toast.makeText(StartActivity.this, "Scan Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartActivity.this, Scan.class));
                        break;
                    }
                    case R.id.classes:{


                   //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScanFragment()).commit();
                        Toast.makeText(StartActivity.this, "Classes Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartActivity.this, pen.class));
                        break;
                    }

                    case R.id.about:{
                        Toast.makeText(StartActivity.this, "About Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartActivity.this, AboutPage.class));
                        break;                    }
                    case R.id.login:{
                        Toast.makeText(StartActivity.this, "LogIn Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartActivity.this, LoginActivity.class));
                        break;
                    }
                    case R.id.share: {


                        //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScanFragment()).commit();
                        Toast.makeText(StartActivity.this, "Share Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.rateUs: {


                        //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScanFragment()).commit();
                        Toast.makeText(StartActivity.this, "RateUs Selected", Toast.LENGTH_SHORT).show();
                        break;
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