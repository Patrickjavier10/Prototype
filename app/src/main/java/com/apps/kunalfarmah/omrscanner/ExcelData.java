package com.apps.kunalfarmah.omrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExcelData extends AppCompatActivity {
    
    TextView tv, bc;
Button ha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_data);

        tv = (TextView) findViewById(R.id.editTextText4);
        bc = (TextView) findViewById(R.id.abcd);
        ha = findViewById(R.id.button2);

        ha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {

                        //  String sqlinsert = "Insert Into ClassName values('" + view + "')";
                        String sqlinsert = "Insert Into Classes values('" + tv.getText().toString() + "', '" + bc.getText().toString() + "')";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());

                }

            }
        });

    }




    
    @SuppressLint("NewApi")
    public Connection connectionclass() {
        Connection con = null;
        String ip = "192.168.100.9", port = "1433", username = "sa", password = "androidstudio", databasename = "ClassName";
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.driver");
            String connectionUrl = "jdbc:jtds:sqlserver://"+ip+":"+port+";databasename="+databasename+";User="+username+";password="+password+";";
            con = DriverManager.getConnection(connectionUrl);

        } catch (Exception exception) {
            Log.e("Error", exception.getMessage());
        }
        return con;
    }



}