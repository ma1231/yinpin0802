package com.example.yinpin;

import android.os.Environment;
import android.util.Log;

import java.io.File;

import static com.example.yinpin.MainActivity.ispause;

public class mythread extends Thread{

    private static final String SAMPLE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/music.mp3";
    private audiodecoder decoder;
    @Override
    public void run() {
        decoder=new audiodecoder();
        Log.d("许诺", "123");
            decoder.excute(SAMPLE);
    }
}

//creatByCodecName方法


