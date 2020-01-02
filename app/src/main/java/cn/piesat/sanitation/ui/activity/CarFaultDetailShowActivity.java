package cn.piesat.sanitation.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.hb.dialog.myDialog.ActionSheetDialog;
import com.hb.dialog.myDialog.MyAlertInputDialog;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.CarBreakDown;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.model.contract.CarMaintenanceContract;
import cn.piesat.sanitation.model.contract.CarStateContract;
import cn.piesat.sanitation.model.presenter.CarMaintenancePresenter;
import cn.piesat.sanitation.model.presenter.CarStatePresenter;
import cn.piesat.sanitation.ui.view.CommentItemInputModul;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 车辆故障详情显示
 */
public class CarFaultDetailShowActivity extends BaseActivity implements CarMaintenanceContract.AuditView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.fault_car)
    CommentItemModul faultCar;
    @BindView(R.id.fault_type)
    CommentItemModul faultType;
    @BindView(R.id.fault_address)
    CommentItemModul faultAddress;
    @BindView(R.id.et_bz)
    TextView etBz;
    @BindView(R.id.fault_time_start)
    CommentItemModul faultTimeStart;
    @BindView(R.id.fault_time_end)
    CommentItemModul faultTimeEnd;
    @BindView(R.id.submit_show)
    LinearLayout submitShow;

    private CarInfo.RowsBean carRowsBean;
    CarMaintenancePresenter carMaintenancePresenter;
    private CarBreakDown.RowsBean rowsBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_fault_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("故障详情");
        carMaintenancePresenter = new CarMaintenancePresenter(this);
    }

    @Override
    protected void initData() {
        rowsBean = (CarBreakDown.RowsBean) getIntent().getSerializableExtra(SysContant.CommentTag.comment_key);
        faultCar.setText(null == rowsBean.licensePlate ? "" : rowsBean.licensePlate);  //   车牌号
        faultType.setText(null == rowsBean.carfaultType ? "" : rowsBean.carfaultType); //   故障类型
        faultAddress.setText(null == rowsBean.carfaultAddress ? "" : rowsBean.carfaultAddress);  //地点
        etBz.setText(null == rowsBean.bzCarfault ? "" : rowsBean.bzCarfault);               //备注
        faultTimeStart.setText(null == rowsBean.sbsjCarfault ? "" : rowsBean.sbsjCarfault);   //开始时间
        faultTimeEnd.setText(null == rowsBean.yjwcsjCarfault ? "" : rowsBean.yjwcsjCarfault);   //结束时间
        submitShow.setVisibility(rowsBean.lay1Sysdictionary==BaseApplication.getUserInfo().userType ? View.VISIBLE : View.GONE); //当该值为4时  可以审批的用户类型
    }


    @OnClick({R.id.img_back, R.id.btn_submit, R.id.btn_submit_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                showLoadingDialog();
                carMaintenancePresenter.AuditCarBreakDown(rowsBean.idBizcaifault, "1", "");
                break;
            case R.id.btn_submit_no:
                showEditDialog();
                break;
        }
    }


    private void showEditDialog() {
        final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(this).builder()
                .setTitle("请输入原因")
                .setEditText("");
        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertInputDialog.dismiss();
                showLoadingDialog();
                carMaintenancePresenter.AuditCarBreakDown(rowsBean.idBizcaifault, "0", myAlertInputDialog.getResult());
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertInputDialog.dismiss();
            }
        });
        myAlertInputDialog.show();
    }


    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(CarFaultDetailShowActivity.this, errorMsg);
    }

    @Override
    public void SuccessFinsh() {
        submitShow.setVisibility(View.GONE);
        dismiss();
        ToastUtil.show(CarFaultDetailShowActivity.this, "审核通过");
    }

}
