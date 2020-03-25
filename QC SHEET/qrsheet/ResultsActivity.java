package com.example.qrsheet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
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

public class ResultsActivity extends AppCompatActivity {
    //ArrayList<RadioGroup> list;
    private AppCompatRadioButton rbA, rbB, rbC, rbD, rbE;
    //private RadioGroup radioGroup;
    String t_name;
    String t_date;
    String s_answer;
    String r_answer;
    String c_answer;
    String t_items;
    String s_name;
    String s_course;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        t_name = getIntent().getStringExtra("test_name");
        t_date = getIntent().getStringExtra("test_date");
        s_answer = getIntent().getStringExtra("student_answer");
        r_answer = getIntent().getStringExtra("right_answer");
        c_answer = getIntent().getStringExtra("correct_answer");
        t_items = getIntent().getStringExtra("test_items");
        s_name = getIntent().getStringExtra("student_name");
        s_course = getIntent().getStringExtra("student_course");



        //list=new ArrayList<RadioGroup>();


        TextView test_name = findViewById(R.id.test_name);
        TextView student_name = findViewById(R.id.student_name);
        TextView student_course = findViewById(R.id.student_course);
        TextView test_date = findViewById(R.id.test_date);
        TextView test_score = findViewById(R.id.test_score);
        test_name.setText(t_name);
        student_name.setText(s_name);
        student_course.setText(s_course);
        test_date.setText(t_date);
        test_score.setText(c_answer + "/" + t_items);

        //testcode = (TextView)findViewById(R.id.test_name);

        String[] stAnswer = s_answer.split("/");
        String[] riAnswer = r_answer.split("/");

        TableLayout tb = (TableLayout) findViewById(R.id.tableLayout);
        tb.setStretchAllColumns(true);
        int items = Integer.parseInt(t_items);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{

                        Color.GRAY
                        , Color.GREEN,
                }
        );
        ColorStateList colorStateList2 = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{

                        Color.GRAY
                        , Color.RED,
                }
        );
        for (int rowNum = 0; rowNum< items; rowNum++) {
            rbA = new AppCompatRadioButton(this);
            rbB = new AppCompatRadioButton(this);
            rbC = new AppCompatRadioButton(this);
            rbD = new AppCompatRadioButton(this);
            rbE = new AppCompatRadioButton(this);
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
            switch(stAnswer[rowNum]) {
                case "A":
                    rbA.setSupportButtonTintList(colorStateList2);
                    rbA.setChecked(true);
                    break;
                case "B":
                    rbB.setSupportButtonTintList(colorStateList2);
                    rbB.setChecked(true);
                    break;
                case "C":
                    rbC.setSupportButtonTintList(colorStateList2);
                    rbC.setChecked(true);
                    break;
                case "D":
                    rbD.setSupportButtonTintList(colorStateList2);
                    rbD.setChecked(true);
                    break;
                case "E":
                    rbE.setSupportButtonTintList(colorStateList2);
                    rbE.setChecked(true);
                    break;
            }
            switch(riAnswer[rowNum]) {
                case "A":
                    rbA.setSupportButtonTintList(colorStateList);
                    rbA.setSelected(true);

                    break;
                case "B":
                    rbB.setSupportButtonTintList(colorStateList);
                    rbB.setChecked(true);

                    break;
                case "C":
                    rbC.setSupportButtonTintList(colorStateList);
                    rbC.setChecked(true);
                    break;
                case "D":
                    rbD.setSupportButtonTintList(colorStateList);
                    rbD.setChecked(true);
                    break;
                case "E":
                    rbE.setSupportButtonTintList(colorStateList);
                    rbE.setChecked(true);
                    break;
            }
            rbA.setEnabled(false);
            rbB.setEnabled(false);
            rbC.setEnabled(false);
            rbD.setEnabled(false);
            rbE.setEnabled(false);



            String no = (rowNum + 1) + ".";
            TextView item_num = new TextView(this);
            item_num.setText(no);
            item_num.setTextColor(Color.BLACK);
            item_num.setTextSize(20);


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
            row.addView(rbA);
            row.addView(rbB);
            row.addView(rbC);
            row.addView(rbD);
            row.addView(rbE);
            row.setLayoutParams(tableRowParams);
            //row.addView(parent);
            row.setWeightSum(7);
            //TableLayout.LayoutParams params = new TableLayout.LayoutParams().LayoutParams(width, TableLayout.LayoutParams.WRAP_CONTENT);
            //rl.setLayoutParams(params);
            //row.setLayoutParams(params);
            tb.addView(row);
            TableRow.LayoutParams numParam = new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            TableRow.LayoutParams selParam = new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.2f
            );
            // item_num.setMaxWidth(30);
            item_num.setLayoutParams(numParam);
            //item_num.setGravity(Gravity.RIGHT);
            //radioGroup.setLayoutParams(rbParam);

            item_num.setLayoutParams(numParam);
            rbA.setLayoutParams(selParam);

            rbB.setLayoutParams(selParam);
            rbC.setLayoutParams(selParam);
            rbD.setLayoutParams(selParam);
            rbE.setLayoutParams(selParam);




        }


    }



    /* @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ResultsActivity.this);
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
                string_to_intent = string_to_intent + "";
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
                Intent intent = new Intent(ResultsActivity.this, QRActivity.class);
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
*/
}