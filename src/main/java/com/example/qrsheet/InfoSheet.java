package com.example.qrsheet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InfoSheet extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static String test_items;
    private EditText testCodeString;
    private Button button;
    static String id_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sheet);
        id_number = getIntent().getStringExtra("id_number_key");



        Spinner spinner = findViewById(R.id.items_spinner);
        //Integer[] items = new Integer[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.items_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        button = (Button) findViewById(R.id.start_button);
        button.setEnabled(false);
        testCodeString = (EditText) findViewById(R.id.test_code_string);
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (testCodeString.getText().toString().startsWith(" "))
                {
                    testCodeString.setText("");
                }
                updateSignInButtonState();
            }
        };

        testCodeString.addTextChangedListener(tw);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        test_items = parent.getItemAtPosition(position).toString();
        updateSignInButtonState();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button again in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
    public void updateSignInButtonState() {
        button.setEnabled(testCodeString.getText().length() > 3 &&
                             !test_items.equals("Select no. of items"));
    }
    public void showAlertDialog(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Start Test");
        alert.setMessage("Are you sure you want to start the test?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String str = testCodeString.getText().toString().replaceAll("\\s+","");
                Intent intent = new Intent(InfoSheet.this, AnswerSheet.class);
                intent.putExtra("id_number_key", id_number);
                intent.putExtra("test_code_key", str);
                intent.putExtra("test_items_key", test_items);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.setCancelable(false);
        alert.create().show();
    }

}
