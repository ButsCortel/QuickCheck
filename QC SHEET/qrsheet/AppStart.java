package com.example.qrsheet;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.CAMERA;


public class AppStart extends AppCompatActivity {

    public static final int REQUEST_CAMERA = 12383;
    public String mode = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);

        Button scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(checkPermission()) //checks if the permission is granted or not
                    {
                        Toast.makeText(AppStart.this,"Scan your QR Code", Toast.LENGTH_LONG).show();
                        mode = "id";
                        startMain();
                    }
                    else {
                        requestPermission();
                    }
                }
            }
        });
        Button resultButton = findViewById(R.id.result_button);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(checkPermission()) //checks if the permission is granted or not
                    {
                        Intent intent = new Intent(AppStart.this, Test.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                    else {
                        requestPermission();
                    }
                }
            }
        });
    }


    /*public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CAMERA) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startMain();
            }
            else {
                Toast.makeText(this, "Cannot start scanner without permission.", Toast.LENGTH_LONG).show();
            }
        }
    }*/
    private void startMain(){
        Intent intent = new Intent(AppStart.this, MainActivity.class);
        intent.putExtra("mode", mode);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(AppStart.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
        //true if permission is granted
    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int grantResults[])
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0) {
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    Toast.makeText(AppStart.this, "Permission Granted!", Toast.LENGTH_LONG).show();
                    startMain();
                } else {
                    Toast.makeText(AppStart.this, "Permission Denied!", Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            displayAlertMesssage("You need to allow access for both permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                        }
                                    });
                        }
                    }
                }
            }
        }
    }
    public void displayAlertMesssage(String message, DialogInterface.OnClickListener listener)
    {
        new AlertDialog.Builder(AppStart.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}