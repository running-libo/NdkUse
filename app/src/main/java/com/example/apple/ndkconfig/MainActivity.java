package com.example.apple.ndkconfig;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView ivShow;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        ivShow = (ImageView) findViewById(R.id.iv);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.filter_thumb_calm);

        findViewById(R.id.btn_java).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = JavaFilter.getImageBitmap(mBitmap);
                ivShow.setImageBitmap(bitmap);
            }
        });

        findViewById(R.id.btn_jni).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int width = mBitmap.getWidth();
                int height = mBitmap.getHeight();
                int[] buffer = new int[width * height];
                mBitmap.getPixels(buffer,0,width,0,0,width-1,height-1);  //???
                int[] ndkImage = NdkFilter.getNdkBitmap(buffer,width,height);
                Bitmap bitmap = Bitmap.createBitmap(ndkImage,width,height, Bitmap.Config.RGB_565);
                ivShow.setImageBitmap(bitmap);
            }
        });
    }
}
