package com.example.logd.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.logd.R;
import com.example.logd.databinding.FragmentDashboardBinding;
import com.example.logd.questionnaires;
import com.example.logd.scanner;
import com.example.logd.userID;
import com.example.logd.valid;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class DashboardFragment extends Fragment {
    Button btnScan;
    private FragmentDashboardBinding binding;
    int resInt;
    userID ids;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ids = new userID();
        btnScan = root.findViewById(R.id.btnScan);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanCode();
            }
        });
        return root;
    }
    private void ScanCode() {

        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan QR Code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureActivity.class);
        barLauncher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {

        try {
             resInt = Integer.parseInt(result.getContents().toString().trim());
        } catch (Exception e){

        }


        if (result.getContents()!=null && resInt > 0){
            Intent intent = new Intent(getActivity(), questionnaires.class);
            startActivity(intent);

            ids.setDataSchoolId(resInt);
            String exp = Integer.toString(ids.getDataSchoolId());
            Toast.makeText(getContext(),exp, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(),"Invalid QR Code", Toast.LENGTH_LONG).show();
        }
    });

}