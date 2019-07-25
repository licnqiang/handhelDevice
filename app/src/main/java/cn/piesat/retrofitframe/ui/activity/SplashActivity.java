package cn.piesat.retrofitframe.ui.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import cn.piesat.retrofitframe.R;
import cn.piesat.retrofitframe.common.BaseActivity;

public class SplashActivity extends BaseActivity {
    String[] mPerms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
    private int mRequestCode = 0x1;
    /*界面跳转时间*/
    private long DELAY_TIME = 3000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        requestPer();
    }

    @Override
    protected void initData() {
    }

    /**
     * 请求权限
     */
    private void requestPer() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            starMain();
        } else {
            ActivityCompat.requestPermissions(this, mPerms, 1);
        }
    }

    int j = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    j++;
                    if (j == 4) {
                        starMain();
                    }
                }
            }

            if(j!=4){
                showPermiDialog();
            }
        }
    }

    /**
     * 提示手动开启权限
     */
    private void showPermiDialog() {
        new AlertDialog.Builder(SplashActivity.this)
                .setTitle("提示")
                .setCancelable(false)
                .setMessage("需要获取相应权限，否则您的应用将无法正常使用! 请手动开启权限")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", SplashActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        // Start for result
                        SplashActivity.this.startActivityForResult(intent, mRequestCode);
                    }
                })
                .create().show();
    }

    /**
     * 两秒跳转
     */
    private void starMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 启动登录窗口
                toActivity(MainActivity.class);
//                toActivity(MainActivity.class);
                // 关闭启动画面
                finish();
            }
        }, DELAY_TIME);
    }

}
