package cn.piesat.retrofitframe.ui.fragment;


import android.support.v4.app.Fragment;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import cn.piesat.retrofitframe.R;
import cn.piesat.retrofitframe.common.BaseFragment;
import cn.piesat.retrofitframe.ui.view.banner.AutoSwitchAdapter;
import cn.piesat.retrofitframe.ui.view.banner.AutoSwitchView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.loopswitch)
    AutoSwitchView loopswitch;
    private AutoSwitchAdapter mAdapter;
    private ArrayList<String> im;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        setSwitch();
    }

    private void setSwitch() {
        im = new ArrayList<String>();
        im.add("");
        im.add("");
        im.add("");
        im.add("");
        mAdapter = new AutoSwitchAdapter(getActivity(), im);
        loopswitch.setAdapter(mAdapter);

//        showLoadingDialog("我的",true);

    }

    @Override
    protected void initData() {
    }

}
