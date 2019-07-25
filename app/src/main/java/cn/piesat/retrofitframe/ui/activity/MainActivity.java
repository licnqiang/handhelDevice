package cn.piesat.retrofitframe.ui.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import butterknife.BindView;
import cn.piesat.retrofitframe.R;
import cn.piesat.retrofitframe.common.BaseActivity;
import cn.piesat.retrofitframe.contract.LoginContract;
import cn.piesat.retrofitframe.presenter.loginPresenter;
import cn.piesat.retrofitframe.ui.fragment.GroupFragment;
import cn.piesat.retrofitframe.ui.fragment.HomeFragment;
import cn.piesat.retrofitframe.ui.fragment.MeFragment;
import cn.piesat.retrofitframe.ui.view.BottomBar;


public class MainActivity extends BaseActivity implements LoginContract.LoginView{
    private HomeFragment mapFragment = new HomeFragment();
    private MeFragment meFragment = new MeFragment();
    private GroupFragment groupFragment = new GroupFragment();
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
                .addItem(groupFragment,
                        "群组",
                        R.mipmap.home_tab_msg_p,
                        R.mipmap.home_tab_msg_p)
                .addItem(meFragment,
                        "我的",
                        R.mipmap.main_wode,
                        R.mipmap.main_wode_sel)
                .build();
    }


    @Override
    protected void initData() {
        loginPresenterImp = new loginPresenter(this);
        loginPresenterImp.login("admin", "123456");
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        //重新让新的Fragment指向了原本未被销毁的fragment，它就是onAttach方法对应的Fragment对象
        if (mapFragment == null && fragment instanceof HomeFragment) {
            mapFragment = (HomeFragment) fragment;
        }
        if (meFragment == null && fragment instanceof MeFragment) {
            meFragment = (MeFragment) fragment;
        }
        super.onAttachFragment(fragment);
    }

    @Override
    public void loginError(String errorMsg) {

    }

    @Override
    public void jumpToMain() {

    }
}
