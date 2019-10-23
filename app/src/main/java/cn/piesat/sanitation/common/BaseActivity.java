package cn.piesat.sanitation.common;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hb.dialog.dialog.LoadingDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.piesat.sanitation.common.netchange.event.NetworkChangeEvent;
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


}
