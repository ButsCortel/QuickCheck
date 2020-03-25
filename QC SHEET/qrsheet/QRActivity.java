package com.example.qrsheet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.luminance;

public class QRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        final String id_number = getIntent().getStringExtra("id_number_key").replaceAll("\\s+", "/");
        final String test_code = getIntent().getStringExtra("test_code_key");
        final String answers = getIntent().getStringExtra("answer_key");
        final String time_start = getIntent().getStringExtra("time_start_key");
        final String items = getIntent().getStringExtra("no_of_items");
        final SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        //final String time_end = format.format(Calendar.getInstance().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        String timeElapsed = "";
        try {
            timeElapsed = timeElapsed(time_start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SharedPreferences sharedPref = getSharedPreferences("com.example.qrsheet.TESTS_" + currentDate, Context.MODE_PRIVATE);

        String rec = test_code+id_number;
        int attempts = sharedPref.getInt(rec + "T", 1);

        //TextView qrstring = (TextView) findViewById(R.id.QR_string) ;
        String all_values = test_code + " " + id_number + " " + items+ " " + answers + " " + attempts + " " + timeElapsed;
        //+ " " + time_start + " End:/" + time_end
        //qrstring.setText(all_values);
        ImageView qrimage = (ImageView) findViewById(R.id.imageView) ;
        try {
            qrimage.setImageBitmap(generateQrCode(all_values));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        Button resultButton = findViewById(R.id.results);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRActivity.this, Test.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(QRActivity.this);
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
    public static Bitmap generateQrCode(String myCodeText) throws WriterException {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // H = 30% damage

        QRCodeWriter writer = new QRCodeWriter();

        int size = 512;

        BitMatrix bitMatrix = writer.encode(myCodeText, BarcodeFormat.QR_CODE, size, size);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
    String timeElapsed(String date) throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        final String time_end = format.format(Calendar.getInstance().getTime());
        Date d1 = format.parse(date);
        Date d2 = format.parse(time_end);
        long difference = d2.getTime() - d1.getTime();
        long diffMinutes = difference / (60 * 1000);



        return Long.toString(diffMinutes);
    }
}
