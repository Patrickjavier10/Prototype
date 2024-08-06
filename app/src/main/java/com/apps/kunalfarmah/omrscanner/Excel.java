/*package com.apps.kunalfarmah.omrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class Excel extends AppCompatActivity {

    private static final String Tag = "Excel";

    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;

    Button btnUpDirectory, btnSDCard;

    ListView lvInternalStorage;

    ArrayList<String> pathHistory;
    ArrayList<XYValue> uploadData;

    String lastDirectory;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);

        lvInternalStorage = findViewById(R.id.lvInternalStorage);
        btnUpDirectory = findViewById(R.id.btnUpDirectory);
        btnSDCard = findViewById(R.id.btnViewSDCard);
        uploadData = new ArrayList<>();


        checkFilePermissions();

        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                lastDirectory = pathHistory.get(count);

                if (lastDirectory.equals(adapterView.getItemAtPosition(i))) {
                    Log.d(Tag, "lvInternalStorage: Selected a file for upload: " + lastDirectory);

                    readExcelData(lastDirectory);
                } else {
                    count++;
                    pathHistory.add(count, (String) adapterView.getItemAtPosition(i));

                    checkInternalStorage();
                    Log.d(Tag, "lvInternalStorage:" + pathHistory.get(count));
                }
            }
        });
    }

    private void readExcelData(String filePath) {
        Log.d(Tag, "readExcelData: Reading Excel File.");

        File inputFile = new File(filePath);

        try {

            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();

            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();

            for (int r = 1; r < rowsCount; r++) {
                Row row = sheet.getRow(r);

                int cellsCount = row.getPhysicalNumberOfCells();
                for (int c = 0; c < cellsCount; c++) {
                    if (c > 2) {
                        Log.e(Tag, "readExcelData; ERROR. ExcelFileFormat is incorrect!");

                        toastMessage("ERROR: Excel file format is incorrect");
                        break;
                    } else {
                        String value = getCellAsString(row, c, formulaEvaluator);

                        String cellInfo = "r:" + r + "; c: " + c + ": v: + value";

                        Log.d(Tag, "readExcelData: Datafromrow: " + cellInfo);
                        sb.append(value + ",");
                    }
                }
                sb.append(":");
            }
            Log.d(Tag, "readExcel: STRINGBUILDER: " + sb.toString());
            parseStringBuilder(sb);
        } catch (FileNotFoundException e) {
            Log.e(Tag, "readExcelData: FileNotFoundException, " + e.getMessage());
        } catch (IOException e) {
            Log.e(Tag, "readExcelData: Error reading inputStream. " + e.getMessage());

        }
    }

    private void parseStringBuilder(StringBuilder mStringBuilder) {
        Log.d(Tag, "pasrseStringBuilder: started parsing.");

        String[] rows = mStringBuilder.toString().split(":");
        for (int i = 0; i < rows.length; i++) {
            String[] columns = rows[i].split(",");
            try {
                double x = Double.parseDouble(columns[0]);
                double y = Double.parseDouble(columns[1]);
                String cellInfo = "(x,y): (" + x + ", " + y + ")";

                Log.d(Tag, "ParseStringBuilder: Data from row: " + cellInfo);
                uploadData.add(new XYValue(x, y));
            } catch (NumberFormatException e) {
                Log.e(Tag, "ParseStringBuilder: NumberFormatExceptionL: " + e.getMessage());
            }
        }
        printDataToLog();
    }



    private void checkInternalStorage() {
        Log.d(Tag, "checkInternalStorage: Started. ");


        try {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                toastMessage("No SD card found");
            } else {
                file = new File(pathHistory.get(count));
                Log.d(Tag, "checkInternalStorage: directorypath: " + pathHistory.get(count));

                listFile = file.listFiles();

                FilePathStrings = new String[listFile.length];

                FileNameStrings = new String[listFile.length];

                for (int i = 0; i < listFile.length; i++) {
                    FilePathStrings[i] = listFile[i].getName();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
                lvInternalStorage.setAdapter(adapter);


            }
        } catch (NullPointerException e) {
            Log.e(Tag, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage());
        }
    }





    private void printDataToLog() {

        Log.d(Tag, "printDataToLog : printing data to log... ");
        for (int i = 0; i < uploadData.size(); i++) {
            double x  = uploadData.get(i).getX();
            double y = uploadData.get(i).getY();
            Log.d(Tag, "printDataToLog: (x,y): ("+x+";"+y+")");
        }
    }


    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";

        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);

            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = "" + cellValue.getBooleanValue();
                    break;

            case Cell.CELL_TYPE_NUMERIC:
                double numericValue = cellValue.getNumberValue();
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    double date = cellValue.getNumberValue();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM//dd//yy");
                    value = formatter.format(HSSFDateUtil.getJavaDate(date));
                } else {
                    value = "" + numericValue;
                }
                break;

            case Cell.CELL_TYPE_STRING:
                value = "" + cellValue.getStringValue();
                break;

            default:
        }
    }catch(NullPointerException e)

    {
        Log.e(Tag, "getCellAsString: NullPointerException: " + e.getMessage());
    }

     return value;
}




    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");

            if (permissionCheck != 0){
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
            }
        }else {
            Log.d(Tag, "checlBTPermissions: No need to check permissions.SDK version < LOLLIPOP" );
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}*/