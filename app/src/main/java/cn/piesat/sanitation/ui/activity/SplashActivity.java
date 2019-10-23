package cn.piesat.sanitation.ui.activity;


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
import android.text.TextUtils;
import android.view.View;

import com.hb.dialog.dialog.ConfirmDialog;
import com.hb.dialog.myDialog.MyAlertDialog;

import cn.jpush.android.cache.Sp;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.util.SpHelper;

public class SplashActivity extends BaseActivity {
    String[] mPerms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
    private int mRequestCode = 0x1;
    /*界面跳转时间*/
    private long DELAY_TIME = 300;

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
//                    starMain();
                    j++;
                    if (j == 4) {
                        starMain();
                    }
                }else {
                    showPermiDialog();
                }
            }

          /*  if(j!=4){
                showPermiDialog();
            }*/
        }
    }

    /**
     * 提示手动开启权限
     */
    private void showPermiDialog() {
        MyAlertDialog myAlertDialog = new MyAlertDialog(this).builder()
                .setCancelable(false)
                .setTitle("提示")
                .setMsg("需要获取相应权限，否则您的应用将无法正常使用! 请手动开启权限")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", SplashActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        SplashActivity.this.startActivityForResult(intent, mRequestCode);
                    }
                });
        myAlertDialog.show();
    }

    /**
     * 两秒跳转
     */
    private void starMain() {
        if (!TextUtils.isEmpty(SpHelper.getStringValue("token"))){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 启动登录窗口
                    toActivity(MainActivity.class);
                    // 关闭启动画面
                    finish();
                }
            }, DELAY_TIME);
        }else {
            toActivity(LoginActivity.class);
            finish();

        }



    }

}
