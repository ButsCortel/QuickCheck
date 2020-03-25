package com.example.qrsheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.Map;

public class Test extends AppCompatActivity {
    //public static final String param = "com.example.qrsheet.";
    public String id = "";
    public String test = "";
    public ArrayList<String> testStrings = null;
    public ArrayList<String[]> testRecords = null;
    private ListView listview;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        SharedPreferences sharedPref = getSharedPreferences("com.example.qrsheet.RECORDS", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPref.edit();
        Map<String,?> keys = sharedPref.getAll();
        //boolean dup = false;
        testRecords = new ArrayList<String[]>();
        testStrings = new ArrayList<String>();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            testRecords.add(entry.getValue().toString().split("\\s+"));
        }
        for (String[] arr : testRecords) {
            String test = arr[0].replaceAll("/", " ");
            String sname = arr[6].replaceAll("/", " ");
            testStrings.add(test + " - " + sname);
        }




        listview = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                testStrings);
        listview.setAdapter(arrayAdapter);

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

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            SharedPreferences sharedPref = getSharedPreferences("com.example.qrsheet.RECORDS", Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor = sharedPref.edit();
            Map<String,?> keys = sharedPref.getAll();
            //boolean dup = false;
            testRecords.clear();
            testStrings.clear();

            for(Map.Entry<String,?> entry : keys.entrySet()){
                testRecords.add(entry.getValue().toString().split("\\s+"));
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
