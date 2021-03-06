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

    private WorkCompressFragment workCompressFragment = new WorkCompressFragment(); //???????????????????????????
    private WorkStationHeaderFragment workStationHeaderFragment = new WorkStationHeaderFragment(); //????????????????????????
    private WorkDriverFragment workDriverFragment = new WorkDriverFragment(); //????????????????????????
    private WorkDustmanFragment workDustmanFragment = new WorkDustmanFragment(); //????????????????????????
    private WorkGroupFragment workGroupFragment = new WorkGroupFragment(); //????????????????????????

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
        //??????userId???????????????   ????????????????????????????????????????????????userId ????????????
        BaseApplication.initDB(userId);
        loginPresenter=new loginPresenter(this);
    }

    /**
     * //4?????? 5????????? 6???????????? 7??????
     *
     * @return
     */
    private BaseFragment switcheWork() {
        int type = BaseApplication.getUserInfo().userType;
        SpHelper.setStringValue(SysContant.userInfo.USER_ROLE_ID, type+""); //????????????id
        if (type == 4) {                       //4??????
            return workStationHeaderFragment;
        } else if (type == 5) {                //5?????????
            return workCompressFragment;
        } else if (type == 6) {                //6????????????
            return workDustmanFragment;
        } else if (type == 7) {                //7??????
            return workDriverFragment;
        } else if(type == 3){ //????????????
           /* SpHelper.setStringValue(SysContant.userInfo.USER_ROLE_ID, BaseApplication.getUserRoleInfo().identity);//????????????id ???????????????userType???
            SpHelper.setStringValue(SysContant.userInfo.USER_ROLE_NAME, BaseApplication.getUserRoleInfo().roleName);////???????????????*/
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
                        "??????",
                        R.mipmap.main_ziyuan,
                        R.mipmap.main_ziyuan_sel)
                .addItem(checkingFragment,
                        "??????",
                        R.mipmap.home_tab_msg_n,
                        R.mipmap.home_tab_msg_p)
                .addItem(switcheWork(),
                        "??????",
                        R.mipmap.main_work,
                        R.mipmap.main_work_sel)
                .addItem(meFragment,
                        "??????",
                        R.mipmap.main_wode,
                        R.mipmap.main_wode_sel)
                .build();

        //??????????????????
        if (loginPresenter!=null){
            loginPresenter.checkUpdate();
        }

        //??????????????????
        startService(new Intent(this, LocationService.class));

    }





    @Override
    public void onAttachFragment(Fragment fragment) {
        //???????????????Fragment??????????????????????????????fragment????????????onAttach???????????????Fragment??????
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
     * ????????????
     */

    private long lastTime = -1;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime > 2000) {
            ToastUtil.show(this, "????????????????????????");
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
            ToastUtil.show(MainActivity.this,"????????????????????????");

        }
    }

    @Override
    public void checkError(String msg) {
        ToastUtil.show(MainActivity.this,msg);
    }
    private ProgressDialog downProgressDialog;
    /**
     * ??????APK
     */
    private void downloadApk(String message,String apkUrl) {

        DialogUtils.generalDialog(MainActivity.this, "????????????",message, new DialogInterface.OnClickListener() {
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
                ToastUtil.show(MainActivity.this,"?????????????????????");

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

                    //8.0??????
                    if (Build.VERSION.SDK_INT>26) {
                        boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                        if (!hasInstallPermission) {
                            //???????????????????????????????????????
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
                    ToastUtil.show(MainActivity.this,"?????????????????????");
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
                        downProgressDialog.setMessage("?????????" + "\t" + msg.arg1 / 1024 + "Kb / " + msg.arg2 / 1024 + "Kb");
                    }
                    break;
                case FileDownLoader.UPDATE_DOWNLOAD_PROGRESS:
                    LogUtil.e("msg.arg1", msg.arg1 + "/msg.arg2:" + msg.arg2);
                    if (null != downProgressDialog) {
                        if (msg.arg2 != 0) {
                            downProgressDialog.setProgress(msg.arg1 );

                        }
                        downProgressDialog.setMessage("?????????" + "\t" + msg.arg1 / 1024 + "Kb / " + msg.arg2 / 1024 + "Kb");
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
                downProgressDialog.setMessage("???????????????....");
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
