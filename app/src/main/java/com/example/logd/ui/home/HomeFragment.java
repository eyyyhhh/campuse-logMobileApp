package com.example.logd.ui.home;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.logd.R;
import com.example.logd.databinding.FragmentHomeBinding;
import com.example.logd.userID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    ImageView profile, edit;
    TextView profilename, school,contactNumber, address, usernum, age, birthdate;
    userID userID;
    String dbpassword, dbaddress, dbcon, dbname, dbage, dbbirthdate;
     Dialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

         dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.edit_profile);

        profile = root.findViewById(R.id.profile);
        profilename = root.findViewById(R.id.profile_name);
        school = root.findViewById(R.id.school);
        contactNumber = root.findViewById(R.id.contactNumber);
        address = root.findViewById(R.id.adddress);
        usernum = root.findViewById(R.id.usernum);
        age = root.findViewById(R.id.age);
        birthdate = root.findViewById(R.id.birthdate);
        edit = root.findViewById(R.id.edit);
        userID = new userID();
        loadData();
        editDialogBox();

        return root;
    }
    public void loadData(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://172.20.10.3/phpcon/read_profile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i= 0; i< jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                usernum.setText(jsonObject.getString("usernum"));
                                profilename.setText(jsonObject.getString("username"));
                                school.setText(jsonObject.getString("usertype") + " | " + jsonObject.getString("schoolName"));
                                address.setText("Address: " +jsonObject.getString("address"));
                                age.setText("Age: " +jsonObject.getString("age") + (" years old"));
                                birthdate.setText("Birthdate: " +jsonObject.getString("birthdate"));
                                contactNumber.setText("Mobile Phone: " + jsonObject.getString("phone"));
                                imageProfile(profile, jsonObject.getString("gender"));

                                dbpassword = jsonObject.getString("password");
                                dbaddress = jsonObject.getString("address");
                                dbcon = jsonObject.getString("phone");
                                dbname = jsonObject.getString("username");
                                dbage = jsonObject.getString("age");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Not working", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                int id = userID.getDataUserId();
                paramV.put("id", String.valueOf(id));
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
    public void editDialogBox(){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText usernames, password, phoneNumber, daddress, dage;
                Button btnUpdate;

                usernames = dialog.findViewById(R.id.username);
                password =  dialog.findViewById(R.id.password);
                phoneNumber = dialog.findViewById(R.id.phoneNumber);
                daddress = dialog.findViewById(R.id.address);
                dage = dialog.findViewById(R.id.age);
                btnUpdate = dialog.findViewById(R.id.btnUpdate);

                usernames.setText(dbname);
                password.setText(dbpassword);
                phoneNumber.setText(dbcon);
                daddress.setText(dbaddress);
                dage.setText(dbage);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ppname = usernames.getText().toString();
                        String ppassword = password.getText().toString();
                        String paddress = daddress.getText().toString();
                        String pphone = phoneNumber.getText().toString();
                        String page = dage.getText().toString();

                        update(ppname,ppassword, paddress, pphone, page);
                    }
                });

                dialog.show();
            }
        });
    }
    public void update( String ppname,String ppassword,String paddress,String pphone, String page){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://172.20.10.3/phpcon/update_profile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success")){
                            Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Not working", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                int id = userID.getDataUserId();
                paramV.put("id", String.valueOf(id));
                paramV.put("username", ppname);
                paramV.put("phone", pphone);
                paramV.put("password", ppassword);
                paramV.put("address",paddress);
                paramV.put("age",page);
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
    public void imgDP( String images){
        Decoder decoder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            decoder = Base64.getDecoder();
        }
        byte[] imageBytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            imageBytes = decoder.decode(images);
        }

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        profile.setImageBitmap(bitmap);
    }
    public void imageProfile(ImageView img, String gender){
        if (gender.equals("male")){
            img.setImageResource(R.drawable.avatarmale);
        }
        else if (gender.equals(("female"))) {
            img.setImageResource(R.drawable.avatarfemale);
        }
        else {
            img.setImageResource(R.drawable.vectors);
        }
    }
}


















