package cn.piesat.sanitation.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hb.dialog.myDialog.MyAlertDialog;
import com.raizlabs.android.dbflow.sql.language.Select;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.data.StationCheckSet;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.model.contract.CheckingContract;
import cn.piesat.sanitation.model.presenter.CheckingPresenter;
import cn.piesat.sanitation.ui.activity.FaceEnterActivity;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 考勤
 */
public class CheckingFragment extends BaseFragment implements CheckingContract.CheckingView {
    @BindView(R.id.rl_checking)
    RelativeLayout rlChecking;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_address)
    TextView userAddress;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_math_day)
    TextView tvMathDay;
    CheckingPresenter checkingPresenter;
    private UserInfo_Tab userInfo_tab;

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
        setUserInfo();
    }

    //设置用户基本信息
    private void setUserInfo() {
        userInfo_tab = BaseApplication.getUserInfo();
        userName.setText(userInfo_tab.name);
        userAddress.setText(userInfo_tab.address);
        tvYear.setText(TimeUtils.getCurrentYear());
        tvMathDay.setText(TimeUtils.getCurrentMathDay());
    }

    @OnClick(R.id.rl_checking)
    public void onViewClicked() {
        //判断用户是否录入头像
        if (null == userInfo_tab.lay1Sysuser || TextUtils.isEmpty(userInfo_tab.lay1Sysuser)) {
            tipDialog();
        } else {

        }

//        checkingPresenter.WorkChecking("1", TimeUtils.getCurrentTimeByHm());

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
     /*   startTime.setText("上班时间" + stationCheckSet.startTime);
        endTime.setText("上班时间" + stationCheckSet.endTime);*/
    }

    @Override
    public void SuccessFinshByWorkCheck() {
        ToastUtil.show(getActivity(), "打卡成功");
    }

}
