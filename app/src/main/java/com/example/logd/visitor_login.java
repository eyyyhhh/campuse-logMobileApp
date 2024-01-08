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
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.util.HashMap;
import java.util.Map;

public class visitor_login extends AppCompatActivity {
    Button btnLogin;
    EditText name, phone, address, age;
    RadioButton male, female;
    String dbname, dbaddress, dbgender, dbage;
    int status, schoolid, dbphone;
    userID userID;
    String Id;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_login);
        statusBar();

        btnLogin = findViewById(R.id.btnLogin);
        IntentMethod(btnLogin, scanner.class);
        userID = new userID();
        name = findViewById(R.id.fullname);
        phone = findViewById(R.id.contactNumber);
        age = findViewById(R.id.ages);
        address = findViewById(R.id.Address);
        male = findViewById(R.id.maleRadioButton);
        female = findViewById(R.id.femaleRadioButton);
        male.isChecked();


        String usertype = "Visitor";
        status = 1;
        schoolid =0;

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
    public void IntentMethod(Button btn, Class classs){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (female.isChecked()){
                    dbgender = "female";
                }
                else {
                    dbgender = "male";
                }
                dbname = name.getText().toString();
                String phones = phone.getText().toString();
                dbaddress = address.getText().toString();
                dbage = age.getText().toString();


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://172.20.10.3/phpcon/visitor_create.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i= 0; i< jsonArray.length(); i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        Id = jsonObject.getString("id");
                                        int id = Integer.parseInt(Id);
                                        if (id >=0){
                                            userID.setDataUserId(id);
                                            Intent intent =  new Intent(visitor_login.this, scanner.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(visitor_login.this, "Not working", Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("name", dbname);
                        paramV.put("phone", phones);
                        paramV.put("gender", dbgender);
                        paramV.put("address", dbaddress);
                        paramV.put("age", dbage);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}