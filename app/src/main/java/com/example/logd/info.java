package com.example.logd;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.logd.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class info extends Fragment {

    FragmentHomeBinding binding;
    RecyclerView recyclerView;
    TextView mission, vission;
    List<ListDataFAQ> item;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_info, container, false);

        mission = root.findViewById(R.id.mission);
        vission = root.findViewById(R.id.vission);

        recyclerView = root.findViewById(R.id.recyclerViewFAQ);
        item =new ArrayList<ListDataFAQ>();

        RecyclerView(recyclerView);
        loadData();
        return root;
    }

    public void RecyclerView (RecyclerView recyclerViews){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://172.20.10.3/phpcon/read_faq.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int i;
                            JSONArray jsonArray = new JSONArray(response);
                            for (i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String answer = jsonObject.getString("answer");
                                String question = jsonObject.getString("question");

                                item.add(new ListDataFAQ(question,answer));
                            }

                            recyclerViews.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerViews.setAdapter(new AdapterFAQ(getContext(),item));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Not working", Toast.LENGTH_SHORT).show();
            }
        }){

        };
        queue.add(stringRequest);
    }
    public void loadData(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://172.20.10.3/phpcon/read_school.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i= 0; i< jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                mission.setText(jsonObject.getString("mission"));

                                vission.setText(jsonObject.getString("vission"));
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
                paramV.put("id","1");
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}