package com.example.qrsheet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.text.TextUtils;


import com.google.zxing.Result;


import me.dm7.barcodescanner.zxing.ZXingScannerView;



public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    //private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private String[] results;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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



            }
        });
        builder.setNeutralButton("Rescan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(MainActivity.this);
            }
        });
        results = scanResult.split("\\s+");
        if (results.length > 3 && myIsDigitsOnly(results[results.length -1]) && results[results.length -1].length() == 4 )
        {
            builder.setMessage("Student information acquired! Please select next step.");
            AlertDialog alert = builder.create();
            alert.setCancelable(false);
            alert.show();
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
}
