package com.example.yinpin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private audiodecoder decoder;
    //private mythread decodeandplaythread;//7.31
    static boolean ispause = true;
     private static final String SAMPLE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/music.mp3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //decodeandplaythread = new mythread();//7.31
        Button play = (Button) findViewById(R.id.play);//此处未完善
        Button pause = (Button) findViewById(R.id.pause);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
//
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "拒绝权限将无法应用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }


    //
    @Override
    public void onClick(View view) {
        decoder = new audiodecoder();
        switch (view.getId()) {
            case R.id.play:
                if (ispause) {

                   // decodeandplaythread.start();
                    decoder.excute(SAMPLE);

                    ispause = false;
                }
                break;
            case R.id.pause:
                if (!ispause) {

                    Log.d("ispause", "123");
                    ispause = true;
                }
                break;
            default:
                break;

        }
        //   decodeandplaythread.start();
    }
}