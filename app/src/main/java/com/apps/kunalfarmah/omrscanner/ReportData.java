package com.apps.kunalfarmah.omrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ReportData extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_data);

        TableView  tableView= findViewById(R.id.table_data_view);

        String []headers = {"Surname", "FirstName", "item", "Score"};
        String [][]data ={ {"Ronan", "Keating", "50", "49"}, {"James", "WhiteSmite", "50", "43"}, {"Ronan", "Keating", "50", "49"},{"Ronan", "Keating", "50", "49"},{"Ronan", "Keating", "50", "49"}};

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, headers));
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, data));



    }
}