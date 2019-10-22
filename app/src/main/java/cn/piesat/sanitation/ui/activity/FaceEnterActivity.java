package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.ui.view.FaceRoundView;
import cn.piesat.sanitation.util.BitmapUtils;
import cn.piesat.sanitation.util.ToastUtil;
import cn.piesat.sanitation.util.carmera.AutoTexturePreviewView;
import cn.piesat.sanitation.util.carmera.CameraDataCallback;
import cn.piesat.sanitation.util.carmera.CameraPreviewManager;
import cn.piesat.sanitation.util.carmera.GlobalSet;

public class FaceEnterActivity extends BaseActivity {
    @BindView(R.id.auto_camera_preview_view)
    AutoTexturePreviewView mPreviewView;

    // RGB摄像头图像宽和高
    private static final int mWidth = 640;
    private static final int mHeight = 480;
    private byte[] picData;

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
        Intent intent = getIntent();
        String face_key_type = intent.getStringExtra(SysContant.CommentTag.comment_key);

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
                picData = data;

            }
        });
    }


    /**
     * 显示检测的图片。用于调试，如果人脸sdk检测的人脸需要朝上，可以通过该图片判断。实际应用中可注释掉
     *
     * @param rgb
     */
    private void showDetectImage(byte[] rgb) {
        showLoadingDialog("头像正在录入", false);
        if (rgb == null) {
            return;
        }
        String path = BitmapUtils.saveTakePictureImage(this, rgb);
        List<String> paths = new ArrayList<>();
        paths.add(path);
        UpLoadFileControl.uploadFile(false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.upload, paths, null, new UpLoadFileControl.ResultCallBack() {
            @Override
            public void succeed(Object str) {
                ToastUtil.show(FaceEnterActivity.this, "录入成功");
                dismiss();
            }

            @Override
            public void faild() {
                ToastUtil.show(FaceEnterActivity.this, "录入失败");
                dismiss();
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


    @OnClick({R.id.tv_back, R.id.rl_checking})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.rl_checking:
                showDetectImage(picData);
                break;
        }
    }
}
