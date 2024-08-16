package com.apps.kunalfarmah.omrscanner;

import static androidx.constraintlayout.widget.ConstraintSet.GONE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class StartActivity extends AppCompatActivity  {

    SwitchCompat switchMode;
    boolean nigthMode;

    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor;

    Button scan,answer;

    Button tab;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    private BottomNavigationView bottomNavigationView;

    ListView listView;

    ArrayAdapter<String> arrayAdapter;
    String[]codinglist = {"BSCS - 602", "BSCS - 701", "BSIT - 506", "BSTM - 101", "BSCRIM - 401", "BSCPE - 502", "BSCS - 301", "BSIT - 702" , "BACOMM - 301", "BMMA - 501"};


    boolean isCamera = true;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actionbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listView.setVisibility(View.VISIBLE);
                tab.setVisibility(View.GONE);
                scan.setVisibility(View.GONE);
                answer.setVisibility(View.GONE);
                bottomNavigationView.setVisibility(View.GONE);

                //imageView.setVisibility(View.GONE);
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        scan = findViewById(R.id.scan);
        answer = findViewById(R.id.setAnswers);

        switchMode = findViewById(R.id.swithMode);



       // imageView.findViewById(R.id.StiLogo);

        listView = findViewById(R.id.listView);

        listView.setVisibility(View.GONE);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_customtext, codinglist);

        listView.setAdapter(arrayAdapter);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        tab = findViewById(R.id.reportAnalysis);

        sharedPreferences1 = getSharedPreferences("Mode", Context.MODE_PRIVATE);
        nigthMode = sharedPreferences1.getBoolean("nightMode", false);

        if (nigthMode){
            switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nigthMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences1.edit();
                    editor.putBoolean("nightMode", false);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences1.edit();
                    editor.putBoolean("nightMode", true);
                }

                editor.apply();
            }
        });

        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, ReportData.class));

            }
        });



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
                        startActivity(new Intent(StartActivity.this, Search.class));
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