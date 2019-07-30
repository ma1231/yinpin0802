package com.example.yinpin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private audiodecoder decoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        decoder=new audiodecoder();
        Button play=(Button)findViewById(R.id.play);//此处未完善
        play.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        decoder.start();
                    }
                }).start();
            }
        });
    }
}
boolean ispause=true;
public void onClick(View view){
    switch(view.getId()){
        case R.id.play:
            if(ispause){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        decoder.start();
                    }
                }).start();

            }
            ispause=false;
            break;
        case R.id.pause:
            if(ispause){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        decoder.start();
                    }
                }).suspend();

            }
            break;
        case R.id.stop:
            if(ispause){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        decoder.start();
                    }
                }).stop();

            }
            break;


    }
}