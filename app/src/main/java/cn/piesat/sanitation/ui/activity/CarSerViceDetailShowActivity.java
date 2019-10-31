package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.hb.dialog.myDialog.ActionSheetDialog;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.CarBreakDown;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CarMaintenance;
import cn.piesat.sanitation.model.contract.CarMaintenanceContract;
import cn.piesat.sanitation.model.contract.CarStateContract;
import cn.piesat.sanitation.model.presenter.CarMaintenancePresenter;
import cn.piesat.sanitation.model.presenter.CarStatePresenter;
import cn.piesat.sanitation.ui.view.CommentItemInputModul;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 汽车维保详情显示
 */
public class CarSerViceDetailShowActivity extends BaseActivity implements CarMaintenanceContract.AuditView{


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_car)
    CommentItemModul tvCar;
    @BindView(R.id.tv_type)
    CommentItemModul tvType;
    @BindView(R.id.tv_address)
    CommentItemModul tvAddress;
    @BindView(R.id.tv_car_par)
    CommentItemModul tvCarPar;
    @BindView(R.id.tv_time)
    CommentItemModul tvTime;
    @BindView(R.id.tv_money)
    CommentItemModul tvMoney;
    @BindView(R.id.end_time)
    CommentItemModul endTime;
    @BindView(R.id.et_bz)
    TextView etBz;
    private CarMaintenance.RowsBean rowsBean;

    CarMaintenancePresenter carMaintenancePresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_ser_vice_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("维保详情");
        carMaintenancePresenter=new CarMaintenancePresenter(this);
    }

    @Override
    protected void initData() {
         rowsBean=(CarMaintenance.RowsBean)getIntent().getSerializableExtra(SysContant.CommentTag.comment_key);
         tvCar.setText(null==rowsBean.licensePlate?"":rowsBean.licensePlate);          //车牌号
         tvType.setText(null==rowsBean.typeRepair?"":rowsBean.typeRepair);           //维保类型(1维修;2保养)
         tvAddress.setText(null==rowsBean.sgdwRepair?"":rowsBean.sgdwRepair);        //车辆保养/维修施工单位
         tvCarPar.setText(null==rowsBean.xmRepair?"":rowsBean.xmRepair);           //保养/维修项目
         tvTime.setText(null==rowsBean.createtimeBizrepair?"":rowsBean.createtimeBizrepair);  //上报时间
         tvMoney.setText(null==rowsBean.jeRepair?"":rowsBean.jeRepair);            // 保养/维修项目金额
         endTime.setText(null==rowsBean.yjwcsjRepair?"":rowsBean.yjwcsjRepair);        //预计完成时间
         etBz.setText(null==rowsBean.bzRepair?"":rowsBean.bzRepair);               //备注
    }


    @OnClick({R.id.img_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                carMaintenancePresenter.AuditCarMaintenance(rowsBean.idBizrepair,"","");
                break;
        }
    }


    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(CarSerViceDetailShowActivity.this, errorMsg);
    }

    @Override
    public void SuccessFinsh() {
        dismiss();
        ToastUtil.show(CarSerViceDetailShowActivity.this, "审核通过");
    }

}
