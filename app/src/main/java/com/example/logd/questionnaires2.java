package com.example.logd;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class questionnaires2 extends AppCompatActivity {

    Button btnScan, btnback;
    CheckBox Q1,Q2,Q3,Q4,Q5;
    int result;
    PassResult rests;
    userID ids;
    String dbuserID, dbschoolID, dbhealthID, dbdate, dbtime, dbremarks, temps, raw_health;


    String q1,q2,q3,q4,q5,q6,q7,q8,q9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaires2);
        statusBar();

        btnScan = findViewById(R.id.btnScan);
        btnback = findViewById(R.id.back);
        Q2 = findViewById(R.id.checkBox2);
        Q3 = findViewById(R.id.checkBox3);
        Q4 = findViewById(R.id.checkBox4);
        Q5 = findViewById(R.id.checkBox5);

        IntentMethod(btnScan);
        rests = new PassResult();
        ids = new userID();

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(questionnaires2.this, questionnaires.class);
                startActivity(intent);
                finish();
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
    public void IntentMethod(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String raw_temp = rests.getTemp();
                if (!(raw_temp.equals(""))) {
                    double temperature = Integer.parseInt(raw_temp);
                    if (temperature >= 38) {
                        result += 20;
                    }
                    temps = Double.toString(temperature);
                    checkBoxValidation(Q2, 3, 0);
                    checkBoxValidation(Q3, 3, 0);
                    checkBoxValidation(Q4, 3, 0);
                    checkBoxValidation(Q5, 2, 0);

                    int questionnaire_result = rests.getData();
                    String health = rests.getHealth();
                    rests.setData(questionnaire_result + result);
                    rests.setHealth(health + raw_health);
                    int final_res = rests.getData();

                    dbuserID = Integer.toString(ids.getDataUserId());
                    dbschoolID = Integer.toString(ids.getDataSchoolId());
                    dbhealthID = rests.getHealth();

                    q1= rests.getQ1();
                    q2= rests.getQ2();
                    q3= rests.getQ3();
                    q4= rests.getQ4();
                    q5= rests.getQ5();
                    q6 = checkBoxQ(Q2);
                    q7 = checkBoxQ(Q3);
                    q8 = checkBoxQ(Q4);
                    q9 = checkBoxQ(Q5);

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
                                        Toast.makeText(questionnaires2.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(questionnaires2.this, "Not working", Toast.LENGTH_SHORT).show();
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
                            paramV.put("q6", q6);
                            paramV.put("q7", q7);
                            paramV.put("q8", q8);
                            paramV.put("q9", q9);

                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
                else{
                    Toast.makeText(questionnaires2.this, "Put a valid temperature.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void intents(Class validation){
        Intent intent = new Intent(questionnaires2.this, validation);
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