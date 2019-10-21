package cn.piesat.sanitation.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.ui.activity.FaceEnterActivity;

/**
 * 考勤
 */
public class CheckingFragment extends BaseFragment {

    @BindView(R.id.rl_checking)
    RelativeLayout rlChecking;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_checking;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.rl_checking)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), FaceEnterActivity.class));
    }
}
