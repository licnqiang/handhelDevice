package cn.piesat.sanitation.ui.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.model.contract.LoginContract;
import cn.piesat.sanitation.model.presenter.loginPresenter;
import cn.piesat.sanitation.ui.fragment.CheckingFragment;
import cn.piesat.sanitation.ui.fragment.WorkFragment;
import cn.piesat.sanitation.ui.fragment.HomeFragment;
import cn.piesat.sanitation.ui.fragment.MeFragment;
import cn.piesat.sanitation.ui.fragment.WorkStationHeaderFragment;
import cn.piesat.sanitation.ui.view.BottomBar;


public class MainActivity extends BaseActivity {
    private HomeFragment mapFragment = new HomeFragment();
    private CheckingFragment checkingFragment = new CheckingFragment();
    private WorkFragment workFragment = new WorkFragment();
    private WorkStationHeaderFragment workStationHeaderFragment = new WorkStationHeaderFragment();
    private MeFragment meFragment = new MeFragment();
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;
    private loginPresenter loginPresenterImp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView() {
        bottomBar.setContainer(R.id.fl_container)
                .setTitleSize(12)
                .setTitleBeforeAndAfterColor("#999999", "#1587FD")
                .addItem(mapFragment,
                        "首页",
                        R.mipmap.main_ziyuan,
                        R.mipmap.main_ziyuan_sel)
                .addItem(checkingFragment,
                        "考勤",
                        R.mipmap.home_tab_msg_n,
                        R.mipmap.home_tab_msg_p)
                .addItem(workStationHeaderFragment,
                        "工作",
                        R.mipmap.home_tab_msg_n,
                        R.mipmap.home_tab_msg_p)
                .addItem(meFragment,
                        "我的",
                        R.mipmap.main_wode,
                        R.mipmap.main_wode_sel)
                .build();
    }


    @Override
    protected void initData() {

    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        //重新让新的Fragment指向了原本未被销毁的fragment，它就是onAttach方法对应的Fragment对象
        if (mapFragment == null && fragment instanceof HomeFragment) {
            mapFragment = (HomeFragment) fragment;
        }
        if (checkingFragment == null && fragment instanceof WorkFragment) {
            checkingFragment = (CheckingFragment) fragment;
        }
        if (meFragment == null && fragment instanceof MeFragment) {
            meFragment = (MeFragment) fragment;
        }
        if (workStationHeaderFragment == null && fragment instanceof WorkFragment) {
            workStationHeaderFragment = (WorkStationHeaderFragment) fragment;
        }
        super.onAttachFragment(fragment);
    }

}
