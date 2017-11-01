#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_example_apple_ndkconfig_NdkFilter_getNdkBitmap(JNIEnv *env, jobject /* this */,
                                                           jintArray buffer_, jint width,
                                                           jint height) {
    float brightness = 0.2f;  //亮度
    float contrainst = 0.2f;  //对比度，颜色加深

    //数组要用指针指向首地址
    jint* source = env->GetIntArrayElements(buffer_,NULL);  //GetIntArrayElements  怎么理解意思

    int a,r,g,b;
    int bab = (int) (255 * brightness);
    float ca = 1.0f + contrainst;
    //改变所有像素点的rgb的值
    int x,y;
    for (x = 0; x < width; x++) {
        for (y = 0; y < height; y++) {
            int color = source[width*y + x];  //获取color值
            a = color >> 24;
            r = color >> 16 & 0xFF;  // (color >> 16) & 0xFF 问老王为什么
            g = color >> 8 & 0xFF;
            b = color & 0xFF;

            //美白
            int ri = r + bab;
            int gi = g + bab;
            int bi = b + bab;

            //边界检测
            r = ri > 255 ? 255 : (ri < 0 ? 0 : ri);
            g = gi > 255 ? 255 : (gi < 0 ? 0 : gi);
            b = bi > 255 ? 255 : (bi < 0 ? 0 : bi);

            //扩大对比度，需要白的更白，黑的更黑
            //128以上加强，128以下再减
            ri = r - 128;
            gi = g - 128;
            bi = b - 128;

            ri = (int) (ri * ca);
            gi = (int) (gi * ca);
            bi = (int) (bi * ca);

            ri += 128;
            gi += 128;
            bi += 128;

            //边界检测
            r = ri > 255 ? 255 : (ri < 0 ? 0 : ri);
            g = gi > 255 ? 255 : (gi < 0 ? 0 : gi);
            b = bi > 255 ? 255 : (bi < 0 ? 0 : bi);

            //result.setPixel(x, y, Color.argb(a, r, g, b));
            //jni的argb合成
            source[width * y + x] = a << 24| r << 16 | g << 8 | b;
        }
    }
    //处理原理同java，但是获取数据，对象方式不同
    int newSize = width * height;
    //根据数组长度,将source保存到jintArray，资源释放
    jintArray result = env->NewIntArray(newSize);
    env->SetIntArrayRegion(result,0,newSize,source);   //???

    env->ReleaseIntArrayElements(buffer_, source, 0);

    return result;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_apple_ndkconfig_NdkFilter_stringFromJNI(JNIEnv* env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
