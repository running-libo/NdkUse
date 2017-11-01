package com.example.apple.ndkconfig;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by apple on 17/10/30.
 */

public class JavaFilter {
    public static final float brightness = 0.2f;  //亮度
    public static final float contrainst = 0.2f;  //对比度，颜色加深

    public static Bitmap getImageBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap result = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
        int a,r,g,b;
        int bab = (int) (255*brightness);
        float ca = 1.0f + contrainst;
        //改变所有像素点的rgb的值
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int color = bitmap.getPixel(x,y);  //获取color值
                a = Color.alpha(color);
                r = Color.red(color);  // (color >> 16) & 0xFF 问老王为什么
                g = Color.green(color);
                b = Color.blue(color);

                //美白
                int ri = r + bab;
                int gi = g + bab;
                int bi = b + bab;

                //边界检测
                r = ri > 255 ? 255 : (ri<0?0:ri);
                g = gi > 255 ? 255 : (gi<0?0:gi);
                b = bi > 255 ? 255 : (bi<0?0:bi);

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
                r = ri > 255 ? 255 : (ri<0?0:ri);
                g = gi > 255 ? 255 : (gi<0?0:gi);
                b = bi > 255 ? 255 : (bi<0?0:bi);

                //用创建指定宽高的新位图，将每个像素color设置进入
                result.setPixel(x,y,Color.argb(a,r,g,b));
            }
        }

        return result;
    }

}
