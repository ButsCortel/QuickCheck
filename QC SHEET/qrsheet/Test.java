package com.example.qrsheet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Test extends AppCompatActivity {
    //public static final String param = "com.example.qrsheet.";
    public String id = "";
    public String test = "";
    public ArrayList<String> testStrings = null;
    public ArrayList<String[]> testRecords = null;
    public ListView listview;
    public ArrayAdapter arrayAdapter;
    private ScrollView scrollView;
    FileOutputStream fstream;
    FileInputStream fistream;
    List<File> fileList;
    private Button open;
    private Button scan;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        open = findViewById(R.id.open_button);
        scan = findViewById(R.id.scan_button);
        delete = findViewById(R.id.delete_button);




       // SharedPreferences sharedPref = getSharedPreferences("com.example.qrsheet.RECORDS", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPref.edit();
        //Map<String,?> keys = sharedPref.getAll();
        //boolean dup = false;
        testRecords = new ArrayList<String[]>();
        testStrings = new ArrayList<String>();
        listview = (ListView) findViewById(R.id.listView);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        arrayAdapter = new ArrayAdapter(
                Test.this,
                android.R.layout.simple_list_item_activated_1,
                testStrings);
        listview.setAdapter(arrayAdapter);
        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        listview.setEmptyView(emptyText);
        /*int i = 0;
        while (sharedPref.contains("RECORDS" + i)) {
            String sf = sharedPref.getString("RECORDS" + i, null);
            if (sf != null) {
                testRecords.add(sf.split("\\s+"));
            }
            i++;
        }*/
        updateList();
        //Collections.sort(testStrings);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = listview.getCheckedItemPosition();
                if (i != -1) {
                    String[] testData = testRecords.get(listview.getCheckedItemPosition());
                    String test_name = testData[0].replaceAll("/", " ");
                    String test_date = testData[1].replaceAll("/", " ");
                    String student_answer = testData[2];
                    String right_answer = testData[3];
                    String correct_answer = testData[4];
                    String test_items = testData[5];
                    String student_name = testData[6].replaceAll("/", " ");;
                    String student_code = testData[7];
                    String student_course = testData[8].replaceAll("/", " ");;
                    String student_subject = testData[9].replaceAll("/", " ");;


                    Intent intent = new Intent(Test.this, ResultsActivity.class);
                    intent.putExtra("test_name", test_name);
                    intent.putExtra("test_date", test_date);
                    intent.putExtra("test_items", test_items);
                    intent.putExtra("student_answer", student_answer);
                    intent.putExtra("right_answer", right_answer);
                    intent.putExtra("correct_answer", correct_answer);
                    intent.putExtra("student_name", student_name);
                    intent.putExtra("student_course", student_course);
                    intent.putExtra("student_code", student_code);
                    intent.putExtra("student_subject", student_subject);

                    startActivity(intent);

                }

            }
        });







        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  IntentIntegrator integrator = new IntentIntegrator(Test.this);
                //integrator.initiateScan();
                /*Toast.makeText(Home.this,"Scan QR code from your proctor", Toast.LENGTH_LONG).show();
                mode = "result";
                Intent intent = new Intent(Home.this, MainActivity.class);
                intent.putExtra("mode", mode);
                startActivity(intent);*/
                Intent intent = new Intent(Test.this, MainActivity.class);
                //intent.putExtra("id_number_key", id);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("mode", "result");
                startActivityForResult(intent, 2);

            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(Test.this);
        builder.setTitle("Delete this test?");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(fileList.get(listview.getCheckedItemPosition()).delete()) {
                    testStrings.clear();
                    testRecords.clear();
                    updateList();

                    Log.d("Deleted?", "yes" );
                }
                else {
                    Log.d("Deleted?", "no" );
                }




            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Test.this,Integer.toString(listview.getCheckedItemPosition()) , Toast.LENGTH_LONG).show();
                if (listview.getCheckedItemPosition() != -1) {

                    builder.show();
                    }
                }

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            testStrings.clear();
            testRecords.clear();
            updateList();
        }
    }
    void updateList() {

        //arrayAdapter.clear();
        //listview.setSelection(-1);

            /*SharedPreferences sharedPref = getSharedPreferences("com.example.qrsheet.RECORDS", Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor = sharedPref.edit();
            Map<String,?> keys = sharedPref.getAll();
            //boolean dup = false;


            int i = 0;
            while (sharedPref.contains("RECORDS" + i)) {
                String sf = sharedPref.getString("RECORDS" + i, null);
                if (sf != null) {
                    testRecords.add(sf.split("\\s+"));
                }
                i++;
            }*/
        File dir = new File(getFilesDir().getAbsolutePath());
        fileList = Arrays.asList(dir.listFiles());
        for(File file: fileList){
            try {
                fistream = openFileInput(file.getName());
                StringBuffer sbuffer = new StringBuffer();
                int i;
                while ((i = fistream.read()) != -1) {
                    sbuffer.append((char) i);
                }

                testRecords.add(sbuffer.toString().split("\\s+"));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {

                    if (fistream != null) {
                        if (fistream!=null){
                            fistream.close();
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        if(testRecords.size()>0){
            for (String[] arr : testRecords) {
                String test = arr[0].replaceAll("/", " ");
                String sname = arr[6].replaceAll("/", " ");

                testStrings.add(test + " - " + sname);
            }
            open.setEnabled(true);
            delete.setEnabled(true);
        }
        else {

            open.setEnabled(false);
            delete.setEnabled(false);

        }
        arrayAdapter = new ArrayAdapter(Test.this, android.R.layout.simple_list_item_activated_1, testStrings);
        listview.setAdapter(arrayAdapter);
    }
}
