package com.example.qrsheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);



       // SharedPreferences sharedPref = getSharedPreferences("com.example.qrsheet.RECORDS", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPref.edit();
        //Map<String,?> keys = sharedPref.getAll();
        //boolean dup = false;
        testRecords = new ArrayList<String[]>();
        testStrings = new ArrayList<String>();
        listview = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(
                Test.this,
                android.R.layout.simple_list_item_1,
                testStrings);
        listview.setAdapter(arrayAdapter);
        /*int i = 0;
        while (sharedPref.contains("RECORDS" + i)) {
            String sf = sharedPref.getString("RECORDS" + i, null);
            if (sf != null) {
                testRecords.add(sf.split("\\s+"));
            }
            i++;
        }*/
        int j = 0;
        while (true) {
            try {
                fistream = openFileInput(Integer.toString(j));
                StringBuffer sbuffer = new StringBuffer();
                int i;
                while ((i = fistream.read()) != -1) {
                    sbuffer.append((char) i);
                }
                fistream.close();
                testRecords.add(sbuffer.toString().split("\\s+"));
                j++;
            } catch (FileNotFoundException e) {
                break;
            } catch (IOException e) {
                break;
            }
        }



        for (String[] arr : testRecords) {
            String test = arr[0].replaceAll("/", " ");
            String sname = arr[6].replaceAll("/", " ");
            //Log.d("MyArr", test + " - " + sname);
            testStrings.add(test + " - " + sname);
        }
        //Collections.sort(testStrings);







        Button scan = findViewById(R.id.scan_button);
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
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String[] testData = testRecords.get(position);
                String test_name = testData[0].replaceAll("/", " ");
                String test_date = testData[1].replaceAll("/", " ");
                String student_answer = testData[2];
                String right_answer = testData[3];
                String correct_answer = testData[4];
                String test_items = testData[5];
                String student_name = testData[6].replaceAll("/", " ");;
                String student_course = testData[8];
                String student_code = testData[7];
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
                /*intent.putExtra("last_name_key", results[1]);
                intent.putExtra("first_name_key", results[2]);
                intent.putExtra("course_key", results[3]);*/
                startActivity(intent);
            }
        });
        arrayAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            testRecords.clear();
            testStrings.clear();
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
            int j = 0;
            while (true) {
                try {
                    fistream = openFileInput(Integer.toString(j));
                    StringBuffer sbuffer = new StringBuffer();
                    int i;
                    while ((i = fistream.read()) != -1) {
                        sbuffer.append((char) i);
                    }
                    fistream.close();
                    testRecords.add(sbuffer.toString().split("\\s+"));
                    j++;
                } catch (FileNotFoundException e) {
                    break;
                } catch (IOException e) {
                    break;
                }
            }

            for (String[] arr : testRecords) {
                String test = arr[0].replaceAll("/", " ");
                String sname = arr[6].replaceAll("/", " ");
                testStrings.add(test + " - " + sname);
            }
            arrayAdapter.notifyDataSetChanged();
            listview.invalidateViews();
            listview.refreshDrawableState();

        }
    }

}
