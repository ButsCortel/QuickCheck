package com.example.qrsheet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AnswerSheet extends AppCompatActivity {
    String string_to_intent = "";
    static String id_number;
    static String test_code;
    static String test_items;
    String time_start = null;
    ArrayList<EditText> list=null;
    private String blockCharacterSet = "abcdefghijklmnopqrstuvwxyz1234567890.,/!@#$%^&*()EFGHIJKLMNOPQRSTUVWXYZÑñ";

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_sheet2);
        id_number = getIntent().getStringExtra("id_number_key");
        test_code = getIntent().getStringExtra("test_code_key");
        test_items = getIntent().getStringExtra("test_items_key");
        TextView testcode;



        TextView time;
        TextView date;
        TableRow r1 = (TableRow)findViewById(R.id.row1);
        TableRow r2 = (TableRow)findViewById(R.id.row2);
        TableRow r3 = (TableRow)findViewById(R.id.row3);
        TableRow r4 = (TableRow)findViewById(R.id.row4);
        TableRow r5 = (TableRow)findViewById(R.id.row5);
        TableRow r6 = (TableRow)findViewById(R.id.row6);
        TableRow r7 = (TableRow)findViewById(R.id.row7);
        TableRow r8 = (TableRow)findViewById(R.id.row8);
        TableRow r9 = (TableRow)findViewById(R.id.row9);
        TableRow r10 = (TableRow)findViewById(R.id.row10);
        TableRow r11 = (TableRow)findViewById(R.id.row11);
        TableRow r12 = (TableRow)findViewById(R.id.row12);
        TableRow r13 = (TableRow)findViewById(R.id.row13);
        TableRow r14 = (TableRow)findViewById(R.id.row14);
        TableRow r15 = (TableRow)findViewById(R.id.row15);
        TableRow r16 = (TableRow)findViewById(R.id.row16);
        TableRow r17 = (TableRow)findViewById(R.id.row17);
        TableRow r18 = (TableRow)findViewById(R.id.row18);
        TableRow r19 = (TableRow)findViewById(R.id.row19);
        TableRow r20 = (TableRow)findViewById(R.id.row20);
        TableRow r21 = (TableRow)findViewById(R.id.row21);
        TableRow r22 = (TableRow)findViewById(R.id.row22);
        TableRow r23 = (TableRow)findViewById(R.id.row23);
        TableRow r24 = (TableRow)findViewById(R.id.row24);
        TableRow r25 = (TableRow)findViewById(R.id.row25);
        EditText a0 = (EditText) findViewById(R.id.anwer1);
        EditText a1 = (EditText) findViewById(R.id.anwer2);
        EditText a2 = (EditText) findViewById(R.id.anwer3);
        EditText a3 = (EditText) findViewById(R.id.anwer4);
        EditText a4 = (EditText) findViewById(R.id.anwer5);
        EditText a5 = (EditText) findViewById(R.id.anwer6);
        EditText a6 = (EditText) findViewById(R.id.anwer7);
        EditText a7 = (EditText) findViewById(R.id.anwer8);
        EditText a8 = (EditText) findViewById(R.id.anwer9);
        EditText a9 = (EditText) findViewById(R.id.anwer10);
        EditText a10 = (EditText) findViewById(R.id.anwer11);
        EditText a11 = (EditText) findViewById(R.id.anwer12);
        EditText a12 = (EditText) findViewById(R.id.anwer13);
        EditText a13 = (EditText) findViewById(R.id.anwer14);
        EditText a14 = (EditText) findViewById(R.id.anwer15);
        EditText a15 = (EditText) findViewById(R.id.anwer16);
        EditText a16 = (EditText) findViewById(R.id.anwer17);
        EditText a17 = (EditText) findViewById(R.id.anwer18);
        EditText a18 = (EditText) findViewById(R.id.anwer19);
        EditText a19 = (EditText) findViewById(R.id.anwer20);
        EditText a20 = (EditText) findViewById(R.id.anwer21);
        EditText a21 = (EditText) findViewById(R.id.anwer22);
        EditText a22 = (EditText) findViewById(R.id.anwer23);
        EditText a23 = (EditText) findViewById(R.id.anwer24);
        EditText a24 = (EditText) findViewById(R.id.anwer25);
        EditText a25 = (EditText) findViewById(R.id.anwer26);
        EditText a26 = (EditText) findViewById(R.id.anwer27);
        EditText a27 = (EditText) findViewById(R.id.anwer28);
        EditText a28 = (EditText) findViewById(R.id.anwer29);
        EditText a29 = (EditText) findViewById(R.id.anwer30);
        EditText a30 = (EditText) findViewById(R.id.anwer31);
        EditText a31 = (EditText) findViewById(R.id.anwer32);
        EditText a32 = (EditText) findViewById(R.id.anwer33);
        EditText a33 = (EditText) findViewById(R.id.anwer34);
        EditText a34 = (EditText) findViewById(R.id.anwer35);
        EditText a35 = (EditText) findViewById(R.id.anwer36);
        EditText a36 = (EditText) findViewById(R.id.anwer37);
        EditText a37 = (EditText) findViewById(R.id.anwer38);
        EditText a38 = (EditText) findViewById(R.id.anwer39);
        EditText a39 = (EditText) findViewById(R.id.anwer40);
        EditText a40 = (EditText) findViewById(R.id.anwer41);
        EditText a41 = (EditText) findViewById(R.id.anwer42);
        EditText a42 = (EditText) findViewById(R.id.anwer43);
        EditText a43 = (EditText) findViewById(R.id.anwer44);
        EditText a44 = (EditText) findViewById(R.id.anwer45);
        EditText a45 = (EditText) findViewById(R.id.anwer46);
        EditText a46 = (EditText) findViewById(R.id.anwer47);
        EditText a47 = (EditText) findViewById(R.id.anwer48);
        EditText a48 = (EditText) findViewById(R.id.anwer49);
        EditText a49 = (EditText) findViewById(R.id.anwer50);
        TextView l6 = (TextView) findViewById(R.id.label6);
        TextView l16 = (TextView) findViewById(R.id.label16);
        TextView l26 = (TextView) findViewById(R.id.label26);
        TextView l36 = (TextView) findViewById(R.id.label36);
        TextView l46 = (TextView) findViewById(R.id.label46);


        list=new ArrayList<EditText>();

        DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
        String dateS = df.format(Calendar.getInstance().getTime());
        DateFormat tf = new SimpleDateFormat("h:mm a");
        String timeS = tf.format(Calendar.getInstance().getTime());


        testcode = (TextView)findViewById(R.id.test_codel);
        time = (TextView)findViewById(R.id.time_startl);
        date = (TextView)findViewById(R.id.datel);
        testcode.setText("Test Code: " + test_code);
        time_start = ("Date: "+ dateS + " Start: " + timeS );
        time.setText("Time: " + timeS);
        date.setText("Date: " + dateS);

        switch(test_items) {
            case "5":
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                a5.setVisibility(View.INVISIBLE);
                l6.setVisibility(View.INVISIBLE);
                list.add(a0);
                list.add(a1);
                list.add(a2);
                list.add(a3);
                list.add(a4);
                list.add(a5);

                //Woohoo, it's weekend time
                break;
            case "10":
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                list.add(a0);
                list.add(a1);
                list.add(a2);
                list.add(a3);
                list.add(a4);
                list.add(a5);
                list.add(a6);
                list.add(a7);
                list.add(a8);
                list.add(a9);
                list.add(a10);
                //Woohoo, it's weekend time
                break;
            case "15":
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                l16.setVisibility(View.INVISIBLE);
                a15.setVisibility(View.INVISIBLE);
                list.add(a0);
                list.add(a1);
                list.add(a2);
                list.add(a3);
                list.add(a4);
                list.add(a5);
                list.add(a6);
                list.add(a7);
                list.add(a8);
                list.add(a9);
                list.add(a10);
                list.add(a11);
                list.add(a12);
                list.add(a13);
                list.add(a14);
                list.add(a15);

                //Woohoo, it's weekend time
                break;
            case "20":
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r10.setVisibility(View.VISIBLE);
                list.add(a0);
                list.add(a1);
                list.add(a2);
                list.add(a3);
                list.add(a4);
                list.add(a5);
                list.add(a6);
                list.add(a7);
                list.add(a8);
                list.add(a9);
                list.add(a10);
                list.add(a11);
                list.add(a12);
                list.add(a13);
                list.add(a14);
                list.add(a15);
                list.add(a16);
                list.add(a17);
                list.add(a18);
                list.add(a19);
                list.add(a20);


                //Woohoo, it's weekend time
                break;
            case "25":
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r10.setVisibility(View.VISIBLE);
                r11.setVisibility(View.VISIBLE);
                r12.setVisibility(View.VISIBLE);
                r13.setVisibility(View.VISIBLE);
                a25.setVisibility(View.INVISIBLE);
                l26.setVisibility(View.INVISIBLE);
                list.add(a0);
                list.add(a1);
                list.add(a2);
                list.add(a3);
                list.add(a4);
                list.add(a5);
                list.add(a6);
                list.add(a7);
                list.add(a8);
                list.add(a9);
                list.add(a10);
                list.add(a11);
                list.add(a12);
                list.add(a13);
                list.add(a14);
                list.add(a15);
                list.add(a16);
                list.add(a17);
                list.add(a18);
                list.add(a19);
                list.add(a20);
                list.add(a21);
                list.add(a22);
                list.add(a23);
                list.add(a24);
                list.add(a25);
                //Woohoo, it's weekend time
                break;
            case "30":
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r10.setVisibility(View.VISIBLE);
                r11.setVisibility(View.VISIBLE);
                r12.setVisibility(View.VISIBLE);
                r13.setVisibility(View.VISIBLE);
                r14.setVisibility(View.VISIBLE);
                r15.setVisibility(View.VISIBLE);
                list.add(a0);
                list.add(a1);
                list.add(a2);
                list.add(a3);
                list.add(a4);
                list.add(a5);
                list.add(a6);
                list.add(a7);
                list.add(a8);
                list.add(a9);
                list.add(a10);
                list.add(a11);
                list.add(a12);
                list.add(a13);
                list.add(a14);
                list.add(a15);
                list.add(a16);
                list.add(a17);
                list.add(a18);
                list.add(a19);
                list.add(a20);
                list.add(a21);
                list.add(a22);
                list.add(a23);
                list.add(a24);
                list.add(a25);
                list.add(a26);
                list.add(a27);
                list.add(a28);
                list.add(a29);
                list.add(a30);

                //Woohoo, it's weekend time
                break;
            case "35":
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r10.setVisibility(View.VISIBLE);
                r11.setVisibility(View.VISIBLE);
                r12.setVisibility(View.VISIBLE);
                r13.setVisibility(View.VISIBLE);
                r14.setVisibility(View.VISIBLE);
                r15.setVisibility(View.VISIBLE);
                r16.setVisibility(View.VISIBLE);
                r17.setVisibility(View.VISIBLE);
                r18.setVisibility(View.VISIBLE);
                a35.setVisibility(View.INVISIBLE);
                l36.setVisibility(View.INVISIBLE);
                list.add(a0);
                list.add(a1);
                list.add(a2);
                list.add(a3);
                list.add(a4);
                list.add(a5);
                list.add(a6);
                list.add(a7);
                list.add(a8);
                list.add(a9);
                list.add(a10);
                list.add(a11);
                list.add(a12);
                list.add(a13);
                list.add(a14);
                list.add(a15);
                list.add(a16);
                list.add(a17);
                list.add(a18);
                list.add(a19);
                list.add(a20);
                list.add(a21);
                list.add(a22);
                list.add(a23);
                list.add(a24);
                list.add(a25);
                list.add(a26);
                list.add(a27);
                list.add(a28);
                list.add(a29);
                list.add(a30);
                list.add(a31);
                list.add(a32);
                list.add(a33);
                list.add(a34);
                list.add(a35);
                //Woohoo, it's weekend time
                break;
            case "40":
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r10.setVisibility(View.VISIBLE);
                r11.setVisibility(View.VISIBLE);
                r12.setVisibility(View.VISIBLE);
                r13.setVisibility(View.VISIBLE);
                r14.setVisibility(View.VISIBLE);
                r15.setVisibility(View.VISIBLE);
                r16.setVisibility(View.VISIBLE);
                r17.setVisibility(View.VISIBLE);
                r18.setVisibility(View.VISIBLE);
                r19.setVisibility(View.VISIBLE);
                r20.setVisibility(View.VISIBLE);
                list.add(a0);
                list.add(a1);
                list.add(a2);
                list.add(a3);
                list.add(a4);
                list.add(a5);
                list.add(a6);
                list.add(a7);
                list.add(a8);
                list.add(a9);
                list.add(a10);
                list.add(a11);
                list.add(a12);
                list.add(a13);
                list.add(a14);
                list.add(a15);
                list.add(a16);
                list.add(a17);
                list.add(a18);
                list.add(a19);
                list.add(a20);
                list.add(a21);
                list.add(a22);
                list.add(a23);
                list.add(a24);
                list.add(a25);
                list.add(a26);
                list.add(a27);
                list.add(a28);
                list.add(a29);
                list.add(a30);
                list.add(a31);
                list.add(a32);
                list.add(a33);
                list.add(a34);
                list.add(a35);
                list.add(a36);
                list.add(a37);
                list.add(a38);
                list.add(a39);
                list.add(a40);
                //Woohoo, it's weekend time
                break;
            case "45":
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r10.setVisibility(View.VISIBLE);
                r11.setVisibility(View.VISIBLE);
                r12.setVisibility(View.VISIBLE);
                r13.setVisibility(View.VISIBLE);
                r14.setVisibility(View.VISIBLE);
                r15.setVisibility(View.VISIBLE);
                r16.setVisibility(View.VISIBLE);
                r17.setVisibility(View.VISIBLE);
                r18.setVisibility(View.VISIBLE);
                r19.setVisibility(View.VISIBLE);
                r20.setVisibility(View.VISIBLE);
                r21.setVisibility(View.VISIBLE);
                r22.setVisibility(View.VISIBLE);
                r23.setVisibility(View.VISIBLE);
                a45.setVisibility(View.INVISIBLE);
                l46.setVisibility(View.INVISIBLE);

                list.add(a0);
                list.add(a1);
                list.add(a2);
                list.add(a3);
                list.add(a4);
                list.add(a5);
                list.add(a6);
                list.add(a7);
                list.add(a8);
                list.add(a9);
                list.add(a10);
                list.add(a11);
                list.add(a12);
                list.add(a13);
                list.add(a14);
                list.add(a15);
                list.add(a16);
                list.add(a17);
                list.add(a18);
                list.add(a19);
                list.add(a20);
                list.add(a21);
                list.add(a22);
                list.add(a23);
                list.add(a24);
                list.add(a25);
                list.add(a26);
                list.add(a27);
                list.add(a28);
                list.add(a29);
                list.add(a30);
                list.add(a31);
                list.add(a32);
                list.add(a33);
                list.add(a34);
                list.add(a35);
                list.add(a36);
                list.add(a37);
                list.add(a38);
                list.add(a39);
                list.add(a40);
                list.add(a41);
                list.add(a42);
                list.add(a43);
                list.add(a44);
                list.add(a45);
                //Woohoo, it's weekend time
                break;
            case "50":
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r10.setVisibility(View.VISIBLE);
                r11.setVisibility(View.VISIBLE);
                r12.setVisibility(View.VISIBLE);
                r13.setVisibility(View.VISIBLE);
                r14.setVisibility(View.VISIBLE);
                r15.setVisibility(View.VISIBLE);
                r16.setVisibility(View.VISIBLE);
                r17.setVisibility(View.VISIBLE);
                r18.setVisibility(View.VISIBLE);
                r19.setVisibility(View.VISIBLE);
                r20.setVisibility(View.VISIBLE);
                r21.setVisibility(View.VISIBLE);
                r22.setVisibility(View.VISIBLE);
                r23.setVisibility(View.VISIBLE);
                r24.setVisibility(View.VISIBLE);
                r25.setVisibility(View.VISIBLE);
                list.add(a0);
                list.add(a1);
                list.add(a2);
                list.add(a3);
                list.add(a4);
                list.add(a5);
                list.add(a6);
                list.add(a7);
                list.add(a8);
                list.add(a9);
                list.add(a10);
                list.add(a11);
                list.add(a12);
                list.add(a13);
                list.add(a14);
                list.add(a15);
                list.add(a16);
                list.add(a17);
                list.add(a18);
                list.add(a19);
                list.add(a20);
                list.add(a21);
                list.add(a22);
                list.add(a23);
                list.add(a24);
                list.add(a25);
                list.add(a26);
                list.add(a27);
                list.add(a28);
                list.add(a29);
                list.add(a30);
                list.add(a31);
                list.add(a32);
                list.add(a33);
                list.add(a34);
                list.add(a35);
                list.add(a36);
                list.add(a37);
                list.add(a38);
                list.add(a39);
                list.add(a40);
                list.add(a41);
                list.add(a42);
                list.add(a43);
                list.add(a44);
                list.add(a45);
                list.add(a46);
                list.add(a47);
                list.add(a48);
                list.add(a49);
                //Woohoo, it's weekend time
                break;

        }


    }



    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AnswerSheet.this);
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
    private void QRString(String itemString) {
        int index = Integer.parseInt(itemString);
        for(int i = 0; i < index; i++) {

            if(list.get(i).getText().toString().equals("")) {
                string_to_intent = string_to_intent + "*/";
            }
            else {
                string_to_intent = string_to_intent + list.get(i).getText().toString() + "/";
            }
        }
    }
    public void showAlertDialog(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Finish Test");
        alert.setMessage("Are you sure you want to finish the Test?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    QRString(test_items);
                    Intent intent = new Intent(AnswerSheet.this, QRActivity.class);
                    intent.putExtra("id_number_key", id_number);
                    intent.putExtra("test_code_key", test_code);
                    intent.putExtra("answer_key", string_to_intent);
                    intent.putExtra("time_start_key", time_start);
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
