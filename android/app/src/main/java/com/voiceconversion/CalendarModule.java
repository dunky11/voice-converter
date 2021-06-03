package com.voiceconversion;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import android.content.res.AssetManager;
import android.content.Context;
import android.util.Log;
import java.nio.FloatBuffer;

import org.pytorch.Tensor;
import org.pytorch.Module;
import org.pytorch.IValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public static String assetFilePath(Context context, String assetName) {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        } catch (IOException e) {
            System.out.println("Error process asset " + assetName + " to file path");
        }
        return null;
    }

    @ReactMethod
    public void createCalendarEvent(Callback cb) throws IOException {
        AssetManager assetManager = this.context.getAssets();
        String[] fileNames = assetManager.list("models");
        for(String name:fileNames){
            System.out.println("Folder path " + name);
        }
        Module model = Module.load(assetFilePath(this.context, "model.pt"));
        float[] input  = new float[10 * 80 * 88];
        for(int i = 0; i < input.length; i++) {
            input[i] = 1.0f;
        }
        Tensor data =
                Tensor.fromBlob(
                        input,
                        new long[] {10, 80, 88}
                );
        IValue result = model.forward(IValue.from(data));
        Tensor output = result.toTensor();
        cb.invoke(assetManager+"/books/IndividualVillas/images-bali/1.jpg");
    }
}