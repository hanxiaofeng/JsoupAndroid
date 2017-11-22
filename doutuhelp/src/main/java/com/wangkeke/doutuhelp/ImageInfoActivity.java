package com.wangkeke.doutuhelp;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wangkeke.doutuhelp.bean.FoldData;

import java.io.IOException;

/**
 * 图片详情，设置为壁纸
 */
public class ImageInfoActivity extends AppCompatActivity {
    private View mContentView;
    private FoldData foldData;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_info);

        mContentView = findViewById(R.id.fullscreen_content);

        foldData = (FoldData) getIntent().getSerializableExtra("imagedata");

        Glide.with(ImageInfoActivity.this).load(foldData.getImgUrl()).asBitmap().into(new BitmapImageViewTarget(((ImageView) mContentView)) {

            @Override
            public void onLoadStarted(Drawable placeholder) {
//                ((ImageView) mContentView).setImageDrawable(ImageInfoActivity.this.getResources().getDrawable(R.mipmap.timeline_image_failure));
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                ((ImageView) mContentView).setImageDrawable(ImageInfoActivity.this.getResources().getDrawable(R.mipmap.timeline_image_failure));
            }

            @Override
            protected void setResource(Bitmap resource) {
                ((ImageView) mContentView).setImageBitmap(resource);
            }
        });

        findViewById(R.id.dummy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(ImageInfoActivity.this).load(foldData.getImgUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        setDeskImage(resource);
                    }
                }); //方法中设置asBitmap可以设置回调类型
            }
        });
    }


    private void setDeskImage(Bitmap bitmap) {
        try {
            WallpaperManager instance = WallpaperManager.getInstance(ImageInfoActivity.this);
            int desiredMinimumWidth = getWindowManager().getDefaultDisplay().getWidth();
            int desiredMinimumHeight = getWindowManager().getDefaultDisplay().getHeight();
            instance.suggestDesiredDimensions(desiredMinimumWidth, desiredMinimumHeight);
            instance.setBitmap(bitmap);
            Toast.makeText(ImageInfoActivity.this, "壁纸设置成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(ImageInfoActivity.this, "壁纸设置失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
