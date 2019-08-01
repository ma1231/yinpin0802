package com.example.yinpin;

import android.os.Environment;

import java.io.File;

public class mythread extends Thread{

    private static final String SAMPLE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/music.mp3";
    private audiodecoder decoder;
    @Override
    public void run() {
        decoder=new audiodecoder();
        decoder.excute(SAMPLE);
    }
}

//creatByCodecName方法


