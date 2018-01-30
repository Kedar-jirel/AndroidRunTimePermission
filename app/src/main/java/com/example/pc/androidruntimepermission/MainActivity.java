package com.example.pc.androidruntimepermission;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppRuntimePermission {
    public  final  static  int REQUESTPERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermisiion(new String[]{Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA},R.string.app_name,REQUESTPERMISSION);
    }

    @Override
    public void onPermissionGrantedd(int requestCode) {
        Toast.makeText(getApplicationContext(),"permission granted",Toast.LENGTH_SHORT).show();
    }
}
