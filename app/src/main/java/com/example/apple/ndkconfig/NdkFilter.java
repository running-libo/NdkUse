package com.example.apple.ndkconfig;


/**
 * Created by apple on 17/10/30.
 */

public class NdkFilter {

    static {
        System.loadLibrary("native-lib");
    }

    public static native int[] getNdkBitmap(int[] buffer,int width,int height);

    public static native String stringFromJNI();
}
