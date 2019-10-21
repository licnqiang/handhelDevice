package cn.piesat.sanitation.ui.activity;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.ui.view.FaceRoundView;
import cn.piesat.sanitation.util.carmera.AutoTexturePreviewView;
import cn.piesat.sanitation.util.carmera.CameraDataCallback;
import cn.piesat.sanitation.util.carmera.CameraPreviewManager;
import cn.piesat.sanitation.util.carmera.GlobalSet;
import cn.piesat.sanitation.util.carmera.SingleBaseConfig;

public class FaceEnterActivity extends BaseActivity {
    @BindView(R.id.auto_camera_preview_view)
    AutoTexturePreviewView mPreviewView;

    // RGB摄像头图像宽和高
    private static final int mWidth = 640;
    private static final int mHeight = 480;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_enter;
    }

    @Override
    protected void initView() {
        setCarmera();
    }

    @Override
    protected void initData() {

    }


    private void setCarmera() {
        // RGB预览
        mPreviewView = findViewById(R.id.auto_camera_preview_view);
        // 遮罩
        FaceRoundView rectView = findViewById(R.id.rect_view);
        rectView.setVisibility(View.VISIBLE);
        // 需要调整预览 大小
        DisplayMetrics dm = new DisplayMetrics();
        Display display = this.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        // 显示Size
        int mDisplayWidth = dm.widthPixels;
        int mDisplayHeight = dm.heightPixels;
        int w = mDisplayWidth;
        int h = mDisplayHeight;
        FrameLayout.LayoutParams cameraFL = new FrameLayout.LayoutParams(
                (int) (w * GlobalSet.SURFACE_RATIO), (int) (h * GlobalSet.SURFACE_RATIO),
                Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        mPreviewView.setLayoutParams(cameraFL);

    }


    /**
     * 摄像头图像预览
     */
    private void startCameraPreview() {

        CameraPreviewManager.getInstance().setCameraFacing(CameraPreviewManager.CAMERA_FACING_FRONT);

        CameraPreviewManager.getInstance().startPreview(this, mPreviewView, mWidth, mHeight, new CameraDataCallback() {
            @Override
            public void onGetCameraData(byte[] data, Camera camera, int width, int height) {


            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        // 摄像头图像预览
        startCameraPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraPreviewManager.getInstance().stopPreview();
    }


    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
