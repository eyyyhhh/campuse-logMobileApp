package com.example.logd;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.transform.Result;

public class questionnaires extends AppCompatActivity {

    Button btnNext;
    CheckBox c1, c2, c3,c4, c5;
    int result;
    String raw_health;
    PassResult rests;
    userID ids;
    String q1,q2,q3,q4,q5,q6,q7,q8,q9;
    EditText temp;
    String dbuserID, dbschoolID, dbhealthID, dbdate, dbtime, dbremarks, temps;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaires);
        statusBar();

        btnNext = findViewById(R.id.btnNext);
        c1 = findViewById(R.id.checkBox1);
        c2 = findViewById(R.id.checkBox2);
        c3 = findViewById(R.id.checkBox3);
        c4 = findViewById(R.id.checkBox4);
        c5 = findViewById(R.id.checkBox5);

        temp = findViewById(R.id.temp);

         rests = new PassResult();

        ids = new userID();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c1.isChecked()){
                    IntentMethod( questionnaires2.class);
                }
                else{
                    IntentMethodSave();
                }
            }
        });

    }
    public  void statusBar(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.blue));
            window.getDecorView().setSystemUiVisibility(this.getResources().getColor(R.color.white));
        }
    }
    public void IntentMethod( Class classs){

                checkBoxValidation(c1,2,0);
                checkBoxValidation(c2,2,0);
                checkBoxValidation(c3,3,0);
                checkBoxValidation(c4,0,3);
                checkBoxValidation(c5,20,0);

                rests.setQ1(checkBoxQ(c1));
                rests.setQ2(checkBoxQ(c2));
                rests.setQ3(checkBoxQ(c3));
                rests.setQ4(checkBoxQ(c4));
                rests.setQ5(checkBoxQ(c5));

                rests.setData(result);
                rests.setHealth(raw_health);
                String raw_temp = temp.getText().toString();
                rests.setTemp(raw_temp);

                result =0;
                Intent intent = new Intent(questionnaires.this, questionnaires2.class);
                startActivity(intent);
                finish();
    }

    public void IntentMethodSave(){


                String raw_temp = temp.getText().toString();
                if (!(raw_temp.equals(""))) {
                    double temperature = Integer.parseInt(raw_temp);
                    if (temperature >= 38) {
                        result += 20;
                    }
                    temps = Double.toString(temperature);

                    int questionnaire_result = rests.getData();
                    String health = rests.getHealth();
                    rests.setData(questionnaire_result + result);
                    rests.setHealth(health + raw_health);
                    int final_res = rests.getData();

                    dbuserID = Integer.toString(ids.getDataUserId());
                    dbschoolID = Integer.toString(ids.getDataSchoolId());
                    dbhealthID = rests.getHealth();

                    q1= checkBoxQ(c1);
                    q2= checkBoxQ(c1);
                    q3= checkBoxQ(c1);
                    q4= checkBoxQ(c1);
                    q5= checkBoxQ(c1);

                    timeDate();
                    dataRemarks(final_res);

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://172.20.10.3/phpcon/inserting_logs.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.equals("Success")) {

                                        if (final_res >= 0 && final_res <= 5) {
                                            intents(valid.class);
                                        } else if (final_res >= 6 && final_res <= 10) {
                                            intents(validmask.class);
                                        } else if (final_res >= 11) {
                                            intents(invalid.class);
                                        }
                                        result = 0;
                                    } else {
                                        Toast.makeText(questionnaires.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(questionnaires.this, "Not working", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("userid", dbuserID);
                            paramV.put("schoolid", dbschoolID);
                            paramV.put("date", dbdate);
                            paramV.put("time", dbtime);
                            paramV.put("remarks", dbremarks);
                            paramV.put("temperature", temps);
                            paramV.put("q1", q1);
                            paramV.put("q2", q2);
                            paramV.put("q3", q3);
                            paramV.put("q4", q4);
                            paramV.put("q5", q5);
                            paramV.put("q6", "0");
                            paramV.put("q7", "0");
                            paramV.put("q8", "0");
                            paramV.put("q9", "0");

                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
                else{
                    Toast.makeText(questionnaires.this, "Put a valid temperature.", Toast.LENGTH_SHORT).show();
                }
    }
    public void intents(Class validation){
        Intent intent = new Intent(questionnaires.this, validation);
        startActivity(intent);
        finish();
    }
    public void dataRemarks(int final_res){
        if(final_res >= 0 && final_res <= 5){
            dbremarks = "0";
        } else if (final_res >= 6 && final_res <= 10) {
            dbremarks = "1";
        }else if (final_res >= 11) {
            dbremarks = "2";
        }
    }
    public String checkBoxQ(CheckBox checkBox){
        String res = "0";
        if (checkBox.isChecked()){
            res = "1";

        } else if (!(checkBox.isChecked())) {
            res = "0";

        }
        return res;
    }
    public void timeDate(){
        // Get the current date
        Date currentDate = new Date();

        // Define the desired date format
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        // Format the date as a string
        dbdate = sdf.format(currentDate);

        Date currentTime = new Date();

        // Define the desired time format
        SimpleDateFormat sdt = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // Format the time as a string
        dbtime = sdt.format(currentTime);

    }
    public void checkBoxValidation(CheckBox checkBox, int checked, int notchecked){

        if (checkBox.isChecked()){
            result += checked;
            raw_health = "1" + raw_health;
        } else if (!(checkBox.isChecked())) {
            result += notchecked;
            raw_health = "0" + raw_health;
        }

    }
}