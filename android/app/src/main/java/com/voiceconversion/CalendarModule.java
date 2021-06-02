package com.voiceconversion;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import android.content.res.AssetManager;
import android.content.Context;
import org.pytorch.Tensor;
import org.pytorch.Module;
import org.pytorch.IValue;

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
    public void createCalendarEvent(Callback cb) {
        AssetManager assetManager = this.context.getAssets();
        Module module = Module.load(assetManager+"/models/voice_converter.pt");

        cb.invoke(assetManager+"/books/IndividualVillas/images-bali/1.jpg");
    }
}