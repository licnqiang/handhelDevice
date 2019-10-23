package cn.piesat.sanitation.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.hb.dialog.dialog.ConfirmDialog;
import com.hb.dialog.myDialog.MyAlertDialog;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.data.StationCheckSet;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.model.contract.CheckingContract;
import cn.piesat.sanitation.model.presenter.CheckingPresenter;
import cn.piesat.sanitation.ui.activity.FaceEnterActivity;
import cn.piesat.sanitation.util.Log;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 考勤
 */
public class CheckingFragment extends BaseFragment implements CheckingContract.CheckingView {
    @BindView(R.id.rl_checking)
    RelativeLayout rlChecking;
    CheckingContract.CheckingPresenter checkingPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_checking;
    }

    @Override
    protected void initView() {
        checkingPresenter = new CheckingPresenter(this);
        //当天打卡情况
        checkingPresenter.QueryCheckingState(TimeUtils.getCurrentTime());
        //获取打卡的基本规则
        checkingPresenter.WorKTimeSet();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.rl_checking)
    public void onViewClicked() {
        UserInfo_Tab userInfo_tab = new Select().from(UserInfo_Tab.class).querySingle();

        //判断用户是否录入头像
        if (null == userInfo_tab.lay1Sysuser || TextUtils.isEmpty(userInfo_tab.lay1Sysuser)) {
            tipDialog();
        } else {

        }

    }

    private void tipDialog() {
        MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity()).builder()
                .setTitle("提示")
                .setMsg("请先录入人脸在打卡")
                .setPositiveButton("开始录入", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), FaceEnterActivity.class));
                    }
                });
        myAlertDialog.show();
    }


    @Override
    public void Error(String errorMsg) {
        ToastUtil.show(getActivity(), errorMsg);
    }

    //获取考勤记录
    @Override
    public void SuccessFinshByCheckRecord(CheckRecord checkRecord) {
        Log.e("--------checkRecords------", "-----checkRecords--------" + new Gson().toJson(checkRecord));
    }

    //考勤基本设定
    @Override
    public void SuccessFinshByWorKTimeSet(StationCheckSet stationCheckSet) {
        Log.e("--------StationCheckSet------", "-----StationCheckSet--------" + new Gson().toJson(stationCheckSet));
    }
}
