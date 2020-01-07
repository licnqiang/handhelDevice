package cn.piesat.sanitation.ui.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.model.presenter.loginPresenter;
import cn.piesat.sanitation.ui.fragment.CheckingFragment;
import cn.piesat.sanitation.ui.fragment.WorkCompressFragment;
import cn.piesat.sanitation.ui.fragment.HomeFragment;
import cn.piesat.sanitation.ui.fragment.MeFragment;
import cn.piesat.sanitation.ui.fragment.WorkDriverFragment;
import cn.piesat.sanitation.ui.fragment.WorkDustmanFragment;
import cn.piesat.sanitation.ui.fragment.WorkStationHeaderFragment;
import cn.piesat.sanitation.ui.view.BottomBar;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.ToastUtil;


public class MainActivity extends BaseActivity {
    private HomeFragment mapFragment = new HomeFragment();
    private CheckingFragment checkingFragment = new CheckingFragment();
    private MeFragment meFragment = new MeFragment();

    private WorkCompressFragment workCompressFragment = new WorkCompressFragment(); //操作工
    private WorkStationHeaderFragment workStationHeaderFragment = new WorkStationHeaderFragment(); //站长
    private WorkDriverFragment workDriverFragment = new WorkDriverFragment(); //司机
    private WorkDustmanFragment workDustmanFragment = new WorkDustmanFragment(); //扫保

    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView() {
        String userId = SpHelper.getStringValue("userId");
        //通过userId开启数据库   在这里开启数据库是因为只有在这里userId 才能确定
        BaseApplication.initDB(userId);
    }

    /**
     * //4站长 5操作工 6扫保人员 7司机
     *
     * @return
     */
    private BaseFragment switcheWork() {
        int type = BaseApplication.getUserInfo().userType;
        if (type == 4) {                       //4站长
            return workStationHeaderFragment;
        } else if (type == 5) {                //5操作工
            return workCompressFragment;
        } else if (type == 6) {                //6扫保人员
            return workDustmanFragment;
        } else if (type == 7) {                //7司机
            return workDriverFragment;
        } else {
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
            finish();
            super.onBackPressed();
            System.exit(0);
        }
    }

}
