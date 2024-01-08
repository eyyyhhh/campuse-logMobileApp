package com.example.logd.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.logd.Adapter;
import com.example.logd.ListData;
import com.example.logd.R;
import com.example.logd.ViewHolder;
import com.example.logd.databinding.FragmentNotificationsBinding;
import com.example.logd.userID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    userID userID;
    ArrayList arrayList;
    String remarks , schoolID;
    RecyclerView recyclerView;
    List<ListData> item;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        userID = new userID();
        recyclerView = root.findViewById(R.id.recyclerView);
        item =new ArrayList<ListData>();

        RecyclerView(recyclerView);
        return root;
    }

    public void RecyclerView (RecyclerView recyclerViews){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://172.20.10.3/phpcon/read_logs.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int i;
                            JSONArray jsonArray = new JSONArray(response);
                            for (i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String date = jsonObject.getString("date");
                                String time = jsonObject.getString("time");

                                if (jsonObject.getString("remarks").equals("0")){
                                    remarks = "Valid Pass";
                                } else if (jsonObject.getString("remarks").equals("1")) {
                                    remarks = "Mask Required";
                                }
                                else {
                                    remarks = "Invalid Pass";
                                }
                                schoolID = jsonObject.getString("schoolName");


                                item.add(new ListData(date,time, remarks, schoolID));
                            }

                           recyclerViews.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerViews.setAdapter(new Adapter(getContext(),item));
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
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                int id = userID.getDataUserId();
                paramV.put("id", String.valueOf(id));
                return paramV;
            }
        };
        queue.add(stringRequest);
    }

}