package com.voiceconversion;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;
import java.io.IOException;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

public class AudioRecorder extends ReactContextBaseJavaModule {
    MediaRecorder recorder;
    ReactApplicationContext context;
    File outputDir;


    AudioRecorder(ReactApplicationContext context) {
        super(context);
        this.context = context;
        this.outputDir = new File(context.getFilesDir(), "temp");
        if(!this.outputDir.exists()){
            this.outputDir.mkdir();
        }
    }

    @Override
    public String getName() {
        return "AudioRecorder";
    }

    @ReactMethod
    public void start(Callback cb) throws IllegalStateException, IOException{
        if (ActivityCompat.checkSelfPermission(getCurrentActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getCurrentActivity(), new String[] { Manifest.permission.RECORD_AUDIO },
                    10);
            cb.invoke(false);
        } else {
            this.recorder = new MediaRecorder();
            this.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            this.recorder.setOutputFile(this.outputDir + "/recording_temp.mp4");
            this.recorder.prepare();
            this.recorder.start();
            cb.invoke(true);
        }
    }

    @ReactMethod
    public void stop() {
        this.recorder.stop();
    }
}