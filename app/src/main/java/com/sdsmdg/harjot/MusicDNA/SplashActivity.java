package com.sdsmdg.harjot.MusicDNA;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    boolean perm1 = false;
    boolean perm2 = false;
    boolean perm3 = false;
    boolean perm4 = false;
    boolean perm5 = false;
    boolean perm6 = false;
    boolean perm7 = false;

    static Typeface tf;
    static Typeface tf2;
    static Typeface tf3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ERROR", this.toString());
        setContentView(R.layout.activity_splash);

        try {
            tf3 = Typeface.createFromAsset(getAssets(), "fonts/Gidole-Regular.ttf");
            tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
            tf2 = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Medium.ttf");
        } catch (Exception e) {
        }

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions();
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    perm1 = true;
                    requestPermissions();
                } else {
                }
                break;
            }
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    perm2 = true;
                    requestPermissions();
                } else {
                }
                break;
            }
            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    perm3 = true;
                    requestPermissions();
                } else {
                }
                break;
            }
            case 3: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    perm4 = true;
                    requestPermissions();
                } else {
                }
                break;
            }
            case 4: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    perm5 = true;
                    requestPermissions();
                } else {
                }
                break;
            }
            case 5: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    perm6 = true;
                    requestPermissions();
                } else {
                }
                break;
            }
            case 6: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    perm7 = true;
                    requestPermissions();
                } else {
                }
                break;
            }
        }
    }

    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (!perm1) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0);
            }
        } else {
            perm1 = true;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (!perm2 && perm1) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        1);
            }
        } else {
            perm2 = true;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            if (!perm3 && perm2 && perm1) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS}, 2);
            }
        } else {
            perm3 = true;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!perm4 && perm3 && perm2 && perm1) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
            }
        } else {
            perm4 = true;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!perm5 && perm4 && perm3 && perm2 && perm1) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 4);
            }
        } else {
            perm5 = true;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (!perm6 && perm5 && perm4 && perm3 && perm2 && perm1) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 5);
            }
        } else {
            perm6 = true;
        }

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.MEDIA_CONTENT_CONTROL) != PackageManager.PERMISSION_GRANTED) {
            if (!perm7 && perm6 && perm5 && perm4 && perm3 && perm2 && perm1) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MEDIA_CONTENT_CONTROL}, 6);
            }
        } else {
            perm7 = true;
        }*/

        if (perm1 && perm2 && perm3 && perm4 && perm5 && perm6) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 1000);
        }
    }


}
