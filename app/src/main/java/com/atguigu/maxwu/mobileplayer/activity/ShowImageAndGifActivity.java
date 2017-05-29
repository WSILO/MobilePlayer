package com.atguigu.maxwu.mobileplayer.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.atguigu.maxwu.mobileplayer.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

public class ShowImageAndGifActivity extends AppCompatActivity {

    private PhotoView photoView;
    private PhotoViewAttacher attacher;
    private String url;
    private ImageOptions imageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_and_gif);
        photoView = (PhotoView) findViewById(R.id.photoView);
        attacher = new PhotoViewAttacher(photoView);
        url = getIntent().getStringExtra("url").trim();
        Log.e("TAG", "url--" + url);
        imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.drawable.video_default)
                .setRadius(DensityUtil.dip2px(5))
                .setIgnoreGif(false)
                .setLoadingDrawableId(R.drawable.video_default)
                .build();
        x.image().bind(photoView, url, imageOptions, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                attacher.update();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
