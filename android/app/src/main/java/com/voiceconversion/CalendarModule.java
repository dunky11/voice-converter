package com.voiceconversion;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.voiceconversion.ml.MelToMel;
import com.voiceconversion.ml.MelToWav;

import android.content.Context;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.CompatibilityList;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.IOException;

public class CalendarModule extends ReactContextBaseJavaModule {
    Context context;

    CalendarModule(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    // add to CalendarModule.java
    @Override
    public String getName() {
        return "CalendarModule";
    }

    @ReactMethod
    public void createCalendarEvent(Callback cb) throws IOException {

        // Initialize interpreter with GPU delegate
        Interpreter.Options options = new Interpreter.Options();
        CompatibilityList compatList = new CompatibilityList();

        if(compatList.isDelegateSupportedOnThisDevice()){
            // if the device has a supported GPU, add the GPU delegate
            GpuDelegate.Options delegateOptions = compatList.getBestOptionsForThisDevice();
            GpuDelegate gpuDelegate = new GpuDelegate(delegateOptions);
            options.addDelegate(gpuDelegate);
        } else {
            // if the GPU is not supported, run on 4 threads
            options.setNumThreads(4);
        }
        MelToMel model = MelToMel.newInstance(this.context);
        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 80, 80}, DataType.FLOAT32);

        MelToWav model2 = MelToWav.newInstance(this.context);

        System.out.println("HERE");

        cb.invoke("/books/IndividualVillas/images-bali/1.jpg");
    }
}