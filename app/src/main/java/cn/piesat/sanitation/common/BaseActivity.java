package cn.piesat.sanitation.common;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hb.dialog.dialog.ConfirmDialog;
import com.hb.dialog.dialog.LoadingDialog;
import com.hb.dialog.myDialog.MyAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.netchange.event.NetworkChangeEvent;
import cn.piesat.sanitation.common.netchange.event.TokenLoseEvent;
import cn.piesat.sanitation.ui.activity.LoginActivity;
import cn.piesat.sanitation.util.NetUtil;
import cn.piesat.sanitation.util.ToastUtil;

public abstract class BaseActivity extends AppCompatActivity {
    Unbinder mBind;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全部禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }

        BaseApplication.putActivityInfoToMap(this);
        EventBus.getDefault().register(this);
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);
        initLoadingDialog();
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mBind) {
            mBind.unbind();
        }
        dismiss();
        EventBus.getDefault().unregister(this);
        BaseApplication.removeActivityInfoFromMap(this);
    }

    //init loading
    protected void initLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(this);
        }
    }

    //show loading
    public void showLoadingDialog(String message, boolean cancelable) {
        loadingDialog.setMessage(message);
        loadingDialog.setCancelable(cancelable);
        loadingDialog.show();
    }

    public void showLoadingDialog() {
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }


    //close loading
    public void dismiss() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


    /**
     * 跳转界面
     */
    public void toActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    /**
     * 网络监听回调
     *
     * @time 2018/8/14 14:17
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventNetworkChange(NetworkChangeEvent event) {

        if (NetUtil.isNetConnect(event.networkType)) {
            ToastUtil.show(this, "网络恢复");
        } else {
            ToastUtil.show(this, "网络异常");
        }
    }


    /**
     * token失效
     *
     * @time 2018/8/14 14:17
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventTokenLose(TokenLoseEvent tokenLoseEvent) {
        BaseApplication.getUserInfo().token = "";  //清空token
        if (!tokenLoseEvent.tokenIsLoase) {
            showDialog();
        }
    }

    private void showDialog() {
        MyAlertDialog myAlertDialog = new MyAlertDialog(this).builder()
                .setTitle("登陆失效")
                .setMsg("该用户在其他设备登陆")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseApplication.closeAllActivityByMap();
                        toActivity(LoginActivity.class);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseApplication.closeAllActivityByMap();
                    }
                });
        myAlertDialog.show();
    }


}
