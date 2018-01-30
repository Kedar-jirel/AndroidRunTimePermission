package com.example.pc.androidruntimepermission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.View;

/**
 * Created by PC on 11/17/2017.
 */

public abstract class AppRuntimePermission extends Activity {
    private SparseIntArray  mErrorString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString = new SparseIntArray();
    }


    public  abstract  void onPermissionGrantedd(int requestCode);

    public  void  requestAppPermisiion(final String[] requestApppermisslion,final  int stringId, final  int requestCode){
        mErrorString.put(requestCode,stringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermission =false;
        for(String permission:requestApppermisslion){
            permissionCheck = permissionCheck+ ContextCompat.checkSelfPermission(this,permission);
            showRequestPermission = showRequestPermission|| ActivityCompat.shouldShowRequestPermissionRationale(this,permission);
        }
        if(permissionCheck!=PackageManager.PERMISSION_GRANTED){
            if(showRequestPermission){
                Snackbar.make(findViewById(android.R.id.content),stringId,Snackbar.LENGTH_INDEFINITE).setAction("" +
                        "GRANT", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(AppRuntimePermission.this,requestApppermisslion,requestCode);
                    }
                }).show();
            }else {
                ActivityCompat.requestPermissions(AppRuntimePermission.this,requestApppermisslion,requestCode);

            }
        }else {
            onPermissionGrantedd(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for(int permission:grantResults){
            permissionCheck = permissionCheck+permission;
        }

        if(grantResults.length>0 && PackageManager.PERMISSION_GRANTED==permissionCheck){
            onPermissionGrantedd(requestCode);
        }else {
            Snackbar.make(findViewById(android.R.id.content),mErrorString.get(requestCode),Snackbar.LENGTH_INDEFINITE).setAction("" +
                    "Enable", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity( new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:"+getPackageName())).addCategory(Intent.CATEGORY_DEFAULT).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            .addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS));
                }
            }).show();
        }
    }
}

