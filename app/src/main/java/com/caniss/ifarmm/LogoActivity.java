package com.caniss.ifarmm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        Thread splash = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                    Intent next = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(next);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        splash.start();
    }
}
