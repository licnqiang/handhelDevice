package cn.piesat.sanitation.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

import butterknife.BindView;
import cn.piesat.sanitation.BuildConfig;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.constant.FileConstant;
import cn.piesat.sanitation.constant.FileDownLoader;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.CheckUpdateBean;
import cn.piesat.sanitation.model.contract.LoginContract;
import cn.piesat.sanitation.model.presenter.loginPresenter;
import cn.piesat.sanitation.ui.fragment.CheckingFragment;
import cn.piesat.sanitation.ui.fragment.WorkCompressFragment;
import cn.piesat.sanitation.ui.fragment.HomeFragment;
import cn.piesat.sanitation.ui.fragment.MeFragment;
import cn.piesat.sanitation.ui.fragment.WorkDriverFragment;
import cn.piesat.sanitation.ui.fragment.WorkDustmanFragment;
import cn.piesat.sanitation.ui.fragment.WorkGroupFragment;
import cn.piesat.sanitation.ui.fragment.WorkStationHeaderFragment;
import cn.piesat.sanitation.ui.view.BottomBar;
import cn.piesat.sanitation.util.DialogUtils;
import cn.piesat.sanitation.util.LogUtil;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.ToastUtil;
import cn.piesat.sanitation.util.location.LocationService;


public class MainActivity extends BaseActivity implements LoginContract.CheckVersionUpdate{
    private HomeFragment mapFragment = new HomeFragment();
    private CheckingFragment checkingFragment = new CheckingFragment();
    private MeFragment meFragment = new MeFragment();

    private WorkCompressFragment workCompressFragment = new WorkCompressFragment(); //操作工人员工作模块
    private WorkStationHeaderFragment workStationHeaderFragment = new WorkStationHeaderFragment(); //站长人员工作模块
    private WorkDriverFragment workDriverFragment = new WorkDriverFragment(); //司机人员工作模块
    private WorkDustmanFragment workDustmanFragment = new WorkDustmanFragment(); //扫保人员工作模块
    private WorkGroupFragment workGroupFragment = new WorkGroupFragment(); //集团人员工作模块

    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    private loginPresenter loginPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        String userId = BaseApplication.getIns().getUserId();
        //通过userId开启数据库   在这里开启数据库是因为只有在这里userId 才能确定
        BaseApplication.initDB(userId);
        loginPresenter=new loginPresenter(this);
    }

    /**
     * //4站长 5操作工 6扫保人员 7司机
     *
     * @return
     */
    private BaseFragment switcheWork() {
        int type = BaseApplication.getUserInfo().userType;
        SpHelper.setStringValue(SysContant.userInfo.USER_ROLE_ID, type+""); //保存角色id
        if (type == 4) {                       //4站长
            return workStationHeaderFragment;
        } else if (type == 5) {                //5操作工
            return workCompressFragment;
        } else if (type == 6) {                //6扫保人员
            return workDustmanFragment;
        } else if (type == 7) {                //7司机
            return workDriverFragment;
        } else if(type == 3){ //集团人员
           /* SpHelper.setStringValue(SysContant.userInfo.USER_ROLE_ID, BaseApplication.getUserRoleInfo().identity);//保存角色id （保存的是userType）
            SpHelper.setStringValue(SysContant.userInfo.USER_ROLE_NAME, BaseApplication.getUserRoleInfo().roleName);////保存角色名*/
            return workGroupFragment;
        }else
            {
            return workDustmanFragment;
        }
    }

    @Override
    protected void initData() {
        bottomBar.setContainer(R.id.fl_container)
                .setTitleSize(14)
                .setTitleBeforeAndAfterColor("#999999", "#1587FD")
                .setIconHeight(20)
                .setIconWidth(20)
                .addItem(mapFragment,
                        "首页",
                        R.mipmap.main_ziyuan,
                        R.mipmap.main_ziyuan_sel)
                .addItem(checkingFragment,
                        "考勤",
                        R.mipmap.home_tab_msg_n,
                        R.mipmap.home_tab_msg_p)
                .addItem(switcheWork(),
                        "工作",
                        R.mipmap.main_work,
                        R.mipmap.main_work_sel)
                .addItem(meFragment,
                        "我的",
                        R.mipmap.main_wode,
                        R.mipmap.main_wode_sel)
                .build();

        //检查版本更新
        if (loginPresenter!=null){
            loginPresenter.checkUpdate();
        }

        //启动定位服务
        startService(new Intent(this, LocationService.class));

    }





    @Override
    public void onAttachFragment(Fragment fragment) {
        //重新让新的Fragment指向了原本未被销毁的fragment，它就是onAttach方法对应的Fragment对象
        if (mapFragment == null && fragment instanceof HomeFragment) {
            mapFragment = (HomeFragment) fragment;
        }
        if (checkingFragment == null && fragment instanceof WorkCompressFragment) {
            checkingFragment = (CheckingFragment) fragment;
        }
        if (meFragment == null && fragment instanceof MeFragment) {
            meFragment = (MeFragment) fragment;
        }
        if (workStationHeaderFragment == null && fragment instanceof WorkCompressFragment) {
            workStationHeaderFragment = (WorkStationHeaderFragment) fragment;
        }

        if (workCompressFragment == null && fragment instanceof WorkCompressFragment) {
            workCompressFragment = (WorkCompressFragment) fragment;
        }

        if (workDriverFragment == null && fragment instanceof WorkCompressFragment) {
            workDriverFragment = (WorkDriverFragment) fragment;
        }

        if (workDustmanFragment == null && fragment instanceof WorkCompressFragment) {
            workDustmanFragment = (WorkDustmanFragment) fragment;
        }

        if (workGroupFragment == null && fragment instanceof WorkGroupFragment) {
            workGroupFragment = (WorkGroupFragment) fragment;
        }
        super.onAttachFragment(fragment);
    }

    /**
     * 退出程序
     */

    private long lastTime = -1;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime > 2000) {
            ToastUtil.show(this, "再按一次退出程序");
            lastTime = System.currentTimeMillis();
        } else {
            stopService(new Intent(this,LocationService.class));
            finish();
            super.onBackPressed();
            System.exit(0);
        }
    }


    @Override
    public void checkSuccess(CheckUpdateBean checkBean) {
        if (checkBean!=null){
            if (Integer.parseInt(checkBean.version)> BuildConfig.VERSION_CODE){
                downloadApk(checkBean.versionDesc,checkBean.url);
            }

        }else {
            ToastUtil.show(MainActivity.this,"版本更新检查失败");

        }
    }

    @Override
    public void checkError(String msg) {
        ToastUtil.show(MainActivity.this,msg);
    }
    private ProgressDialog downProgressDialog;
    /**
     * 下载APK
     */
    private void downloadApk(String message,String apkUrl) {

        DialogUtils.generalDialog(MainActivity.this, "更新提示",message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DownloadApplicationTask().execute(new String[]{apkUrl});
                dialog.dismiss();
            }
        });

    }




    private class DownloadApplicationTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            showDownLoadProgress();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {
            return FileDownLoader.downloadFile(updateHandler, params[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);


            if (result==FileDownLoader.DOWN_FILE_ERROR){
                closeDownLoadProgress();
                ToastUtil.show(MainActivity.this,"更新包下载失败");

                return;
            }
            closeDownLoadProgress();
            File file = new File(FileConstant.getFileDownloadPath(), "xxhuanwei.apk");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (result == FileDownLoader.DOWN_FILE_SUCCESS) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "cn.piesat.sanitation.provider", file);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(photoURI, "application/vnd.android.package-archive");

                    //8.0适配
                    if (Build.VERSION.SDK_INT>26) {
                        boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                        if (!hasInstallPermission) {
                            //请求安装未知应用来源的权限
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 6666);

                        }

                    }

                } else {
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                }
            }
            startActivity(intent);
        }

    }

    private Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case FileDownLoader.DOWN_FILE_ERROR:
                    ToastUtil.show(MainActivity.this,"更新包下载失败");
                    closeDownLoadProgress();
                    break;

                case FileDownLoader.CLOSE_PROGRESSDIALOG:
                    closeDownLoadProgress();
                    break;
                case FileDownLoader.START_DOWNLOAD:
                    if (null != downProgressDialog) {
                        if (msg.arg2!=0){
                            downProgressDialog.setMax(msg.arg2);
                        }
                        downProgressDialog.setProgressNumberFormat("");
                        downProgressDialog.setProgress(0);
                        downProgressDialog.setMessage("下载中" + "\t" + msg.arg1 / 1024 + "Kb / " + msg.arg2 / 1024 + "Kb");
                    }
                    break;
                case FileDownLoader.UPDATE_DOWNLOAD_PROGRESS:
                    LogUtil.e("msg.arg1", msg.arg1 + "/msg.arg2:" + msg.arg2);
                    if (null != downProgressDialog) {
                        if (msg.arg2 != 0) {
                            downProgressDialog.setProgress(msg.arg1 );

                        }
                        downProgressDialog.setMessage("下载中" + "\t" + msg.arg1 / 1024 + "Kb / " + msg.arg2 / 1024 + "Kb");
                    }
                    break;

            }
        }

    };

    public void closeDownLoadProgress() {
        try {
            if (downProgressDialog != null) {
                downProgressDialog.dismiss();
                downProgressDialog = null;
            }
        } catch (Exception e) {
        }
    }

    private void showDownLoadProgress() {

        try {
            if (downProgressDialog == null) {
                downProgressDialog = new ProgressDialog(this);
            }
            if (!downProgressDialog.isShowing()) {
                downProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                downProgressDialog.setMessage("正在下载中....");
//                downProgressDialog.setIcon(R.mipmap.login_logo);
                downProgressDialog.setProgress(0);

//                downProgressDialog.setMax(fileLenght);
                downProgressDialog.setIndeterminate(false);
                downProgressDialog.setCancelable(false);
                downProgressDialog.show();
            }
        } catch (Exception e) {
        }
    }
}
