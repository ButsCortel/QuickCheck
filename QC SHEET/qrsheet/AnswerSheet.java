package com.example.qrsheet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
    ArrayList<RadioGroup> list=null;
    private String blockCharacterSet = "abcdefghijklmnopqrstuvwxyz1234567890.,/!@#$%^&*()FGHIJKLMNOPQRSTUVWXYZÑñ";
    private RadioButton rbA, rbB, rbC, rbD, rbE;
    private RadioGroup radioGroup;

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



        list=new ArrayList<RadioGroup>();

        DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
        String dateS = df.format(Calendar.getInstance().getTime());
        DateFormat tf = new SimpleDateFormat("h:mm a");
        String timeS = tf.format(Calendar.getInstance().getTime());
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        time_start = format.format(Calendar.getInstance().getTime());

        testcode = (TextView)findViewById(R.id.test_name);
        time = (TextView)findViewById(R.id.student_name);
        date = (TextView)findViewById(R.id.student_course);
        testcode.setText("Test Code: " + test_code);

        time.setText("Time: " + timeS);
        date.setText("Date: " + dateS);

        TableLayout tb = (TableLayout) findViewById(R.id.tableLayout);
        tb.setStretchAllColumns(true);
        int items = Integer.parseInt(test_items);
        for (int rowNum = 0; rowNum< items; rowNum++) {
            rbA = new RadioButton(this);
            rbB = new RadioButton(this);
            rbC = new RadioButton(this);
            rbD = new RadioButton(this);
            rbE = new RadioButton(this);
            rbA.setText("A");
            rbB.setText("B");
            rbC.setText("C");
            rbD.setText("D");
            rbE.setText("E");
            rbA.setTextColor(Color.BLACK);
            rbB.setTextColor(Color.BLACK);
            rbC.setTextColor(Color.BLACK);
            rbD.setTextColor(Color.BLACK);
            rbE.setTextColor(Color.BLACK);

            //TableRow.LayoutParams params = new TableRow.LayoutParams(20, TableRow.LayoutParams.WRAP_CONTENT);

            rbA.setTextSize(20);
            rbB.setTextSize(20);
            rbC.setTextSize(20);
            rbD.setTextSize(20);
            rbE.setTextSize(20);


            String no = (rowNum + 1) + ".";
            TextView item_num = new TextView(this);
            item_num.setText(no);
            item_num.setTextColor(Color.BLACK);
            item_num.setTextSize(20);


            radioGroup = new RadioGroup(this);
            radioGroup.addView(rbA);
            radioGroup.addView(rbB);
            radioGroup.addView(rbC);
            radioGroup.addView(rbD);
            radioGroup.addView(rbE);
            radioGroup.setOrientation(LinearLayout.HORIZONTAL);

            list.add(radioGroup);
            //item_num.setMaxWidth(10);

            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin=20;
            int topMargin=2;
            int rightMargin=10;
            int bottomMargin=2;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);


            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER);
            row.addView(item_num);
            row.addView(radioGroup);
            row.setLayoutParams(tableRowParams);
            //row.addView(parent);
            row.setWeightSum(7);
            //TableLayout.LayoutParams params = new TableLayout.LayoutParams().LayoutParams(width, TableLayout.LayoutParams.WRAP_CONTENT);
            //rl.setLayoutParams(params);
            //row.setLayoutParams(params);
            tb.addView(row);
            TableRow.LayoutParams rbParam = new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    6.0f
            );
            TableRow.LayoutParams numParam = new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            // item_num.setMaxWidth(30);
            item_num.setLayoutParams(numParam);
            //item_num.setGravity(Gravity.RIGHT);
            radioGroup.setLayoutParams(rbParam);

            RadioGroup.LayoutParams param = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    1.0f
            );

            rbA.setLayoutParams(param);

            rbB.setLayoutParams(param);
            rbC.setLayoutParams(param);
            rbD.setLayoutParams(param);
            rbE.setLayoutParams(param);




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
    private void QRString() {
       // int index = Integer.parseInt(itemString);
        for (RadioGroup rg: list) {
            int selected = rg.getCheckedRadioButtonId();
            if (selected != -1) {
                RadioButton radioButton = (RadioButton) findViewById(selected);
                string_to_intent = string_to_intent + radioButton.getText() + "/";
            }
            else {
                string_to_intent = string_to_intent + "*/";
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
                    QRString();
                    Intent intent = new Intent(AnswerSheet.this, QRActivity.class);
                    intent.putExtra("id_number_key", id_number);
                    intent.putExtra("test_code_key", test_code);
                    intent.putExtra("answer_key", string_to_intent);
                    intent.putExtra("time_start_key", time_start);
                    intent.putExtra("no_of_items", test_items);
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
