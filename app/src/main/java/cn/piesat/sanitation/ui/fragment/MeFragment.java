package cn.piesat.sanitation.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseFragment;

/**
 * 个人中心
 */
public class MeFragment extends BaseFragment {

    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.btn_start, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                showLoadingDialog("加载中...",true);
                break;
            case R.id.btn_stop:
                dismiss();
                break;
        }
    }
}
