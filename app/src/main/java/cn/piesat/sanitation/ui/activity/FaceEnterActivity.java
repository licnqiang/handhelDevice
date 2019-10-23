package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.baidu.idl.main.facesdk.FaceAuth;
import com.baidu.idl.main.facesdk.callback.Callback;
import com.baidu.idl.main.facesdk.model.BDFaceImageInstance;
import com.baidu.idl.main.facesdk.model.BDFaceSDKCommon;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.model.contract.UserInfoContract;
import cn.piesat.sanitation.model.presenter.UserInfoPresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.ui.view.FaceRoundView;
import cn.piesat.sanitation.util.BitmapUtils;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;
import cn.piesat.sanitation.util.carmera.AutoTexturePreviewView;
import cn.piesat.sanitation.util.carmera.CameraDataCallback;
import cn.piesat.sanitation.util.carmera.CameraPreviewManager;
import cn.piesat.sanitation.util.carmera.GlobalSet;
import cn.piesat.sanitation.util.carmera.SingleBaseConfig;

public class FaceEnterActivity extends BaseActivity implements UserInfoContract.UserInfoView {
    @BindView(R.id.auto_camera_preview_view)
    AutoTexturePreviewView mPreviewView;
    @BindView(R.id.iv_user_pic)
    ImageView imageView;

    // RGB摄像头图像宽和高
    private static final int mWidth = 640;
    private static final int mHeight = 480;
    private byte[] picData;

    UserInfoPresenter userInfoPresenter;
    private String type = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_enter;
    }

    @Override
    protected void initView() {
        setCarmera();
        userInfoPresenter = new UserInfoPresenter(this);
    }

    @Override
    protected void initData() {

    }


    private void setCarmera() {
        // RGB预览
        mPreviewView = findViewById(R.id.auto_camera_preview_view);
        // 遮罩
        FaceRoundView rectView = findViewById(R.id.rect_view);
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
        showLoadingDialog("头像采集中", false);

        BDFaceImageInstance rgbInstance = new BDFaceImageInstance(rgb, mHeight,
                mWidth, BDFaceSDKCommon.BDFaceImageType.BDFACE_IMAGE_TYPE_YUV_420,
                SingleBaseConfig.getBaseConfig().getDetectDirection(),
                SingleBaseConfig.getBaseConfig().getMirrorRGB());
        BDFaceImageInstance imageInstance = rgbInstance.getImage();
        final Bitmap bitmap = BitmapUtils.getInstaceBmp(imageInstance);
        String path = BitmapUtils.getFileFromBytes(this, bitmap);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
            }
        });

        List<String> paths = new ArrayList<>();
        paths.add(path);
        UpLoadFileControl.uploadFile(false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.upload, paths, null, new UpLoadFileControl.ResultCallBack() {
            @Override
            public void succeed(Object str) {
                Intent intent = getIntent();
                String face_key_type = intent.getStringExtra(SysContant.CommentTag.comment_key);
                type = intent.getStringExtra("type");
                if (face_key_type.equals(SysContant.CommentTag.face_tag_entering)) {
                    userInfoPresenter.ModeyUserPic(str + "");
                } else {
                    userInfoPresenter.UserPicVers(IPConfig.getOutSourceURLPreFix() + str);
                }


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

    @Override
    public void Error(String errorMsg) {
        imageView.setVisibility(View.GONE);
        dismiss();
        ToastUtil.show(FaceEnterActivity.this, errorMsg);
    }

    @Override
    public void SuccessFinsh() {
        dismiss();
        ToastUtil.show(FaceEnterActivity.this, "录入成功");
        finish();
    }

    @Override
    public void SuccessFinshPicVers() {
        userInfoPresenter.WorkChecking(type, TimeUtils.getCurrentTimeByHm());
    }

    @Override
    public void SuccessFinshByWorkCheck() {
        finish();
        dismiss();
        ToastUtil.show(FaceEnterActivity.this, "打卡成功");
    }
}
