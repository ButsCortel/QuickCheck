package com.example.qrsheet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    public String id = "";
    public String mode = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        id = getIntent().getStringExtra("id_number_key");
        Button recordsButton = findViewById(R.id.records_button);
        recordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(Home.this,"Scan QR code from your proctor", Toast.LENGTH_LONG).show();
                mode = "result";
                Intent intent = new Intent(Home.this, MainActivity.class);
                intent.putExtra("mode", mode);
                startActivity(intent);*/
                Intent intent = new Intent(Home.this, Test.class);
                intent.putExtra("id_number_key", id);
                startActivity(intent);
            }
        });
        Button testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, InfoSheet.class);
                intent.putExtra("id_number_key", id);
                startActivity(intent);
            }
        });
        Button changeButton = findViewById(R.id.change_user);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home.this,"Scan your QR Code", Toast.LENGTH_LONG).show();
                mode = "id";
                Intent intent = new Intent(Home.this, MainActivity.class);
                intent.putExtra("mode", mode);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setMessage("Are you sure you want to Exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
