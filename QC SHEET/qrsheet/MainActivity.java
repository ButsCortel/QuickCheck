package com.example.qrsheet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.google.zxing.Result;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;



public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    //private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private String[] results;
    String mode = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode = getIntent().getStringExtra("mode");
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkPermission()) //checks if the permission is granted or not
            {
                Toast.makeText(MainActivity.this,"Scan your QR Code", Toast.LENGTH_LONG).show();
            }
            else {
                requestPermission();
            }
        }*/
    }
    /*private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(MainActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
        //true if permission is granted
    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int grantResults[])
    {
        switch(requestCode)
        {
            case REQUEST_CAMERA:
                if (grantResults.length > 0)
                {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted)
                    {
                        Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_LONG).show();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            if(shouldShowRequestPermissionRationale(CAMERA))
                            {
                                displayAlertMesssage("You need to allow access for both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }*/
    @Override
    public void onResume()
    {
        super.onResume();
        if(scannerView == null)
        {
            scannerView = new ZXingScannerView(this);
            setContentView(scannerView);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        scannerView.stopCamera();
    }
    /*public void displayAlertMesssage(String message, DialogInterface.OnClickListener listener)
    {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }*/
    @Override
    public void handleResult(final Result result) {
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result:");
        builder.setPositiveButton("Start!", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(MainActivity.this, InfoSheet.class);
                intent.putExtra("id_number_key", results[results.length-1]);
                /*intent.putExtra("last_name_key", results[1]);
                intent.putExtra("first_name_key", results[2]);
                intent.putExtra("course_key", results[3]);*/
                startActivity(intent);
                finish();



            }
        });
        builder.setNeutralButton("Rescan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(MainActivity.this);
            }
        });
        results = scanResult.split("\\s+");
        if (results.length > 3 && myIsDigitsOnly(results[results.length -1]) && results[results.length -1].length() > 3 && mode.equals("id"))
        {

            builder.setMessage("Student information acquired! Please select next step.");
            AlertDialog alert = builder.create();
            alert.setCancelable(false);
            alert.show();
        }
        else if (results.length > 7 && mode.equals("result")) {
            saveRecord(scanResult);
            setResult(2);
            finish();



        }
        else
        {

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Result:");
            alertDialog.setMessage("Invalid QR code Format! Please try again.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Rescan",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            scannerView.resumeCameraPreview(MainActivity.this);
                        }
                    });
            alertDialog.setCancelable(false);
            alertDialog.show();
        }

    }
    boolean myIsDigitsOnly(String str) {
        if(str.isEmpty()) {
            return false;
        } else {
            return TextUtils.isDigitsOnly(str);
        }
    }
    FileOutputStream fstream;
    FileInputStream fistream;
    void saveRecord(String answers) {
        int j = 0;
        while (true) {
            try {
                fistream = openFileInput(Integer.toString(j));
                fistream.close();
                j++;
            } catch (FileNotFoundException e) {
                break;
            } catch (IOException e) {
                break;
            }

        }
        try {
            fstream = openFileOutput(Integer.toString(j), Context.MODE_PRIVATE);
            fstream.write(answers.getBytes());

            fstream.close();
            Toast.makeText(getApplicationContext(), "Details Saved Successfully",Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG).show();
        }




//Do something





       /* SharedPreferences sharedPref = getSharedPreferences("com.example.qrsheet.RECORDS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Map<String,?> keys = sharedPref.getAll();
        boolean dup = false;
        for(Map.Entry<String,?> entry : keys.entrySet()){
            if (entry.getValue().toString().equals(answers)) {
                dup = true;
                break;
            }

        }
        if (!dup) {
            int i = 0;
            while (sharedPref.contains("RECORDS" + i)) {
                i++;
            }
            editor.putString("RECORDS" + i, answers);
            editor.apply();
        }
            */
    }

}