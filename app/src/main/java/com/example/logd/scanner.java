package com.example.logd;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class scanner extends AppCompatActivity {

    Button btnScan;
    TextView qrResult;
    int resInt;
    userID ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        statusBar();

        btnScan = findViewById(R.id.btnScan);
        ids = new userID();

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              ScanCode();
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
            Intent intent = new Intent(scanner.this, questionnaires.class);
            startActivity(intent);
            finish();

            ids.setDataSchoolId(resInt);
            String exp = Integer.toString(ids.getDataSchoolId());

        }
        else{
            Toast.makeText(getApplicationContext(),"Invalid QR Code", Toast.LENGTH_LONG).show();
        }
    });

}















