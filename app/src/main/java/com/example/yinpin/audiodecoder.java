package com.example.yinpin;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class audiodecoder{
    int mSampleRate;
    int channel;
    int samplerate=44110;//采样率
    int changelConfig=2;//信道数

    private MediaCodec mediaDecode;
    private MediaFormat format;
    private MediaExtractor Extractor;
    private MediaCodec.Callback mCallback;
    private AudioTrack mPlayer;
    public void excute(String path){
        {
            try {
                Extractor = new MediaExtractor();
                Extractor.setDataSource(path);

                for (int i = 0; i < Extractor.getTrackCount(); i++) {
                    format = Extractor.getTrackFormat(i);//获取指定（index）的通道格式
                    String mime = format.getString(MediaFormat.KEY_MIME);//媒体数据格式

                    if (mime.startsWith("audio/")) {

                        Extractor.selectTrack(i);//选择此音频轨道
                        mSampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);//获取当前音频的采样率
                        Log.d("debug999", String.valueOf(mSampleRate));
                        channel = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT); //获取当前帧的通道数

                        int minBufferSize = AudioTrack.getMinBufferSize(samplerate, changelConfig, AudioFormat.ENCODING_PCM_16BIT);

                        mPlayer = new AudioTrack(AudioManager.STREAM_MUSIC, samplerate, changelConfig, AudioFormat.ENCODING_PCM_16BIT, minBufferSize, AudioTrack.MODE_STREAM);
                        mediaDecode = MediaCodec.createDecoderByType(mime);//创建Decode解码器 mediaDecode = MediaCodec.createDecoderByType(mime);
                        Log.d("debug123456789","aaa");
                        mediaDecode.setCallback(new MediaCodec.Callback() {

                            @Override
                            public void onInputBufferAvailable(MediaCodec mediaCodec, int id) {
                                Log.d("xn123","aaa");
                                // int inputIndex = mediaDecode.dequeueInputBuffer(-1);//获取可用的inputBuffer -1代表一直等待，0表示不等待 建议-1,避免丢帧
                                ByteBuffer inputBuffer = mediaDecode.getInputBuffer(id);

                                //inputBuffer.clear();
                                // fill inputBuffer with valid data
                                //mediaDecode.queueInputBuffer(i, …);
                                int readresult = Extractor.readSampleData(inputBuffer, 0);//从MediaExtractor中读取一帧待解的数据

                                //小于0 代表所有数据已读取完成
                                if (readresult < 0) {
                                    mediaDecode.queueInputBuffer(id, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                                } else {
                                    mediaDecode.queueInputBuffer(id, 0, readresult, Extractor.getSampleTime(), 0);//插入一帧待解码的数据

                                    Extractor.advance();//MediaExtractor移动到下一取样处
                                }

                            }

                            @Override
                            public void onOutputBufferAvailable(MediaCodec mediaCodec, int id, MediaCodec.BufferInfo bufferInfo) {
                                ByteBuffer outputBuffer = mediaDecode.getOutputBuffer(id);
                                MediaFormat outputFormat = mediaDecode.getOutputFormat(id);

                                if (format == outputFormat && outputBuffer != null && bufferInfo.size > 0) {

                                    byte[] mbuffer = new byte[bufferInfo.size];//BufferInfo内定义了此数据块的大小
                                    outputBuffer.get(mbuffer);//将Buffer内的数据取出到字节数组中
                                    mPlayer.play();
                                    mPlayer.write(mbuffer,0, bufferInfo.size);

                                }
                                mediaDecode.releaseOutputBuffer(id, false);

                            }

                            @Override
                            public void onError(MediaCodec mediaCodec, MediaCodec.CodecException e) {

                            }

                            @Override
                            public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {

                            }
                        });
                        mediaDecode.configure(format, null, null, 0);

                    }
                }
                //mediaDecode.configure(format, null, null, 0);
                mediaDecode.start();
                Log.d("xn","aaa");
                release();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void release() {
        if (mediaDecode != null) {
            mediaDecode.stop();
            mediaDecode.release();
            mediaDecode = null;
        }

        if (Extractor != null) {
            Extractor.release();
            Extractor = null;
        }

        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }





}
