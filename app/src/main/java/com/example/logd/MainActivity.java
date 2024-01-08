package com.example.logd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnLoginVisitor, btnLogin;
    EditText name, pass;
    TextView about;
    String username,password, Id;
    userID userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusBar();

        btnLoginVisitor = findViewById(R.id.btnloginVisitor);
        btnLogin = findViewById(R.id.btnLogin);
        name = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        userID = new userID();
        about = findViewById(R.id.about);

        IntentMethod(btnLoginVisitor, visitor_login.class);

            login();
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,terms.class);
                startActivity(intent);
            }
        });

    }
    public void login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               username = name.getText().toString();
               password = pass.getText().toString();

                if((username.equals("")) || (password.equals(""))){
                    Toast.makeText(getApplicationContext(), "Put a valid input.", Toast.LENGTH_LONG).show();
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="http://172.20.10.3/phpcon/user_login.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                       try {
                                           JSONArray jsonArray = new JSONArray(response);
                                           for (int i= 0; i< jsonArray.length(); i++) {
                                               JSONObject jsonObject = jsonArray.getJSONObject(i);

                                               Id = jsonObject.getString("id");
                                               int id = Integer.parseInt(Id);
                                               if (id >= 0) {
                                                   userID.setDataUserId(id);
                                                   Intent intent = new Intent(MainActivity.this, home.class);
                                                   startActivity(intent);
                                               }
                                           }
                                       } catch (JSONException e) {
                                           throw new RuntimeException(e);
                                       } catch (Exception r){

                                       } finally {

                                       }

                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    } finally {

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Not working", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("username", username);
                            paramV.put("password", password);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
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
    public void IntentMethod(Button btn, Class classs){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,classs);
                startActivity(intent);
            }
        });
    }
}