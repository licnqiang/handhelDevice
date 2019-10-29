package cn.piesat.sanitation.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.dialog.myDialog.MyAlertDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.data.StationCheckSet;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.model.contract.CheckingContract;
import cn.piesat.sanitation.model.presenter.CheckingPresenter;
import cn.piesat.sanitation.ui.activity.FaceEnterActivity;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;
import cn.piesat.sanitation.util.UiUtils;

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
    @BindView(R.id.user_start_check_time)
    TextView userStartCheckTime;
    @BindView(R.id.user_end_check_time)
    TextView userEndCheckTime;
    @BindView(R.id.tv_clock_in_time)
    TextView tvClockInTime;
    @BindView(R.id.tv_clock_in)
    TextView tvClockIn;
    @BindView(R.id.user_name_tag)
    TextView userNameTag;

    private UserInfo_Tab userInfo_tab;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_checking;
    }

    @Override
    protected void initView() {
        checkingPresenter = new CheckingPresenter(this);
        //获取打卡的基本规则
        checkingPresenter.WorKTimeSet();
    }

    @Override
    protected void initData() {
        userStartCheckTime.setVisibility(View.GONE);
        userEndCheckTime.setVisibility(View.GONE);
        setUserInfo();
    }

    //设置用户基本信息
    private void setUserInfo() {

        userInfo_tab = BaseApplication.getUserInfo();
        userName.setText(userInfo_tab.name);
        userNameTag.setText(UiUtils.getPinYinHeadChar(userInfo_tab.name.substring(0,1)));
        userAddress.setText(userInfo_tab.address);
        tvYear.setText(TimeUtils.getCurrentYear());
        tvMathDay.setText(TimeUtils.getCurrentMathDay());
    }

    @Override
    public void onResume() {
        super.onResume();

        boolean startTime = TimeUtils.isCurrentInTimeScope(8, 0, 12, 0);
        boolean endTime = TimeUtils.isCurrentInTimeScope(12, 0, 20, 0);

        if(startTime){
            tvClockIn.setText("上班打卡");
        }else if(endTime){
            tvClockIn.setText("下班打卡");
        }

        //显示当前时分秒
        tvClockInTime.setText(TimeUtils.getCurrentTimeHHMMSS());
        //当天打卡情况
        checkingPresenter.QueryCheckingState(TimeUtils.getCurrentTime());
    }

    @OnClick(R.id.rl_checking)
    public void onViewClicked() {
        //    判断用户是否录入头像
        if (null == userInfo_tab.lay1Sysuser || TextUtils.isEmpty(userInfo_tab.lay1Sysuser)) {
            tipDialog();
        } else {

            boolean startTime = TimeUtils.isCurrentInTimeScope(8, 0, 12, 0);
            boolean endTime = TimeUtils.isCurrentInTimeScope(12, 0, 20, 0);

            if (startTime) {  //上班
                if (TextUtils.isEmpty(userStartCheckTime.getText().toString())) {
                    startActivity(new Intent(getActivity(), FaceEnterActivity.class)
                            .putExtra(SysContant.CommentTag.comment_key, SysContant.CommentTag.face_tag_verify)
                            .putExtra("type", "1"));
                } else {
                    ToastUtil.show(getActivity(), "暂不支持迟到/早退卡或重复打卡");
                }
            } else if (endTime) {   //下班
                if (TextUtils.isEmpty(userEndCheckTime.getText().toString())) {
                    startActivity(new Intent(getActivity(), FaceEnterActivity.class)
                            .putExtra(SysContant.CommentTag.comment_key, SysContant.CommentTag.face_tag_verify)
                            .putExtra("type", "2"));
                } else {
                    ToastUtil.show(getActivity(), "暂不支持迟到/早退卡或重复打卡");
                }
            }else {
                ToastUtil.show(getActivity(), "暂不支持迟到/早退卡或重复打卡");
            }
        }

    }

    private void tipDialog() {
        MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity()).builder()
                .setTitle("提示")
                .setMsg("请先录入人脸在打卡")
                .setPositiveButton("开始录入", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), FaceEnterActivity.class).putExtra(SysContant.CommentTag.comment_key, SysContant.CommentTag.face_tag_entering));
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
    public void SuccessFinshByCheckRecord(List<CheckRecord> checkRecords) {
        if (null != checkRecords && checkRecords.size() > 0) {
            for (int i = 0; i < checkRecords.size(); i++) {
                if (checkRecords.get(i).type == 1) {
                    userStartCheckTime.setVisibility(View.VISIBLE);
                    userStartCheckTime.setText("打卡时间" + checkRecords.get(i).time);

                } else if (checkRecords.get(i).type == 2) {
                    userEndCheckTime.setVisibility(View.VISIBLE);
                    userEndCheckTime.setText("打卡时间" + checkRecords.get(i).time);
                }
            }
        }
    }

    //考勤基本设定
    @Override
    public void SuccessFinshByWorKTimeSet(StationCheckSet stationCheckSet) {
        if (null == stationCheckSet) {
            return;
        }
        startTime.setText("上班时间" + stationCheckSet.startTime);
        endTime.setText("上班时间" + stationCheckSet.endTime);
    }

}
