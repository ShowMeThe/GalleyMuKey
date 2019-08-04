package com.journeyapps.barcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.google.zxing.integration.android.IntentResult;

public class ScanCameraManager implements DefaultLifecycleObserver {

    private CaptureManager capture;
    private AppCompatActivity activity;
    private DecoratedBarcodeView barcodeScannerView;

    public ScanCameraManager init(AppCompatActivity activity, DecoratedBarcodeView barcodeScannerView) {
        this.activity = activity;
        this.activity.getLifecycle().addObserver(this);
        this.barcodeScannerView = barcodeScannerView;
        return  this;
    }


    public void start(Bundle savedInstanceState){
        capture = new CaptureManager(activity, barcodeScannerView);
        capture.initializeFromIntent(activity.getIntent(), savedInstanceState);
        capture.decodeContinuous();

        capture.setOnBarCodeResultCallBack(intent -> {
            if(onBarCodeCallBack!=null){
               return onBarCodeCallBack.onCallBack(intent);
            }else {
                return false;
            }
        });
    }


    private onBarCodeCallBack onBarCodeCallBack;

    public void setOnBarCodeCallBackListener(onBarCodeCallBack onBarCodeCallBackListener){
        this.onBarCodeCallBack = onBarCodeCallBackListener;
    }

    public interface onBarCodeCallBack{

        boolean  onCallBack(IntentResult intent);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        if(capture!=null){
            capture.onPause();
        }
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        if(capture!=null){
            capture.onResume();
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        if(capture!=null){
            capture.onDestroy();
            activity.getLifecycle().removeObserver(this);
            activity = null;
            capture = null;
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if(capture!=null){
            capture.onSaveInstanceState(outState);
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(capture!=null){
            capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || activity.onKeyDown(keyCode, event);
    }

}
