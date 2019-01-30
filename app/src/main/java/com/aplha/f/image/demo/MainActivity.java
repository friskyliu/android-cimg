package com.aplha.f.image.demo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.alpha.f.image.CImage;

import java.io.File;
import java.io.FilenameFilter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "C-IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) == 0;
        if (isGranted) {
            ImageView imageView = findViewById(R.id.test_image);

            final String folder = "/sdcard/test/";
            new File(folder).mkdirs();

            String src = getTestPath();
            String dst = folder + "test.jpg";


            Point size = getSize(src);
            Log.e(TAG, "image path:" + src + "     size:" + size.x + "x" + size.y);

            float scale = 720F/Math.max(size.x, size.y);
            int dstW = (int) (scale * size.x + 0.5f);
            int dstH = (int) (scale * size.y + 0.5f);

            try {
                boolean rst = CImage.cropCImg(src, dst, 0, 0, dstW, dstH, 0, scale,
                        Bitmap.CompressFormat.JPEG.ordinal(), 90, 0, 0);
                if (rst && new File(dst).exists()) {
                    size = getSize(dst);
                    Log.e(TAG, "crop path:" + dst + "     size:" + size.x + "x" + size.y);
                } else {
                    Log.e(TAG, "crop error");
                }

                imageView.setImageBitmap(BitmapFactory.decodeFile(dst));
            } catch (Exception e) {
                Log.e(TAG, "crop error", e);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 1001);
        }
    }

    private String getTestPath() {
        File folder = new File("/sdcard/DCIM/Camera/");
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".jpg") || s.endsWith(".JPG");
            }
        });
        return files[0].getAbsolutePath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static Point getSize(String jpg) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(jpg, options);
        if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
            return new Point(-1, -1);
        }

        return new Point(options.outWidth, options.outHeight);
    }
}
