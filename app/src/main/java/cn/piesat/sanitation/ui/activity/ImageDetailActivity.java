package cn.piesat.sanitation.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.ui.view.SmoothImageView;

public class ImageDetailActivity extends AppCompatActivity {

    private String mDatas;
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    private SmoothImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatas = getIntent().getStringExtra("images");
        mLocationX = getIntent().getIntExtra("locationX", 0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);

        imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(imageView);

        //显示图片
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.paizhao)
                .error(R.mipmap.paizhao)
                .fallback(R.mipmap.paizhao);
        Glide.with(ImageDetailActivity.this)
                .load(mDatas)
                .apply(requestOptions)
                .into(imageView);
    }

}
