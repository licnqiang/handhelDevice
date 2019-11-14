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
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.model.contract.CarStateContract;
import cn.piesat.sanitation.model.presenter.CarStatePresenter;
import cn.piesat.sanitation.ui.view.CommentItemInputModul;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 车辆故障上报
 */
public class CarFaultActivity extends BaseActivity implements CarStateContract.CarStateView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.fault_car)
    CommentItemModul faultCar;
    @BindView(R.id.fault_type)
    CommentItemModul faultType;
    @BindView(R.id.fault_address)
    CommentItemInputModul faultAddress;
    @BindView(R.id.et_bz)
    EditText etBz;

    @BindView(R.id.fault_time_start)
    CommentItemModul faultTimeStart;
    @BindView(R.id.fault_time_end)
    CommentItemModul faultTimeEnd;

    private CarInfo.RowsBean carRowsBean;
    CarStatePresenter carStatePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_fault;
    }

    @Override
    protected void initView() {
        tvTitle.setText("车辆故障");
        carStatePresenter = new CarStatePresenter(this);
    }

    @Override
    protected void initData() {
        faultTimeStart.setText(TimeUtils.getCurrentTimeMMSS());
    }

    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("严重故障", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        faultType.setText("严重故障");
                    }
                }).addSheetItem("轻微故障", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        faultType.setText("轻微故障");
                    }
                });
        dialog.show();
    }


    @OnClick({R.id.img_back, R.id.btn_submit, R.id.fault_car, R.id.fault_time_end,R.id.fault_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fault_type:
                showDialog();
                break;
            case R.id.fault_time_end:
                seleTimePicker ();
                break;
            case R.id.fault_car:
                Intent intentcar = new Intent();
                intentcar.setClass(this, ItemSelectActivity.class);
                Bundle bundlecar = new Bundle();
                bundlecar.putString(SysContant.QueryType.query_type, SysContant.QueryType.car_key);
                intentcar.putExtras(bundlecar);
                startActivityForResult(intentcar, 0);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:

                String sfaultType = faultType.getText().toString();
                String sfaultTimeStart = faultTimeStart.getText().toString();
                String sfaultAddress = faultAddress.getText().toString();
                String sfaultTimeEnd = faultTimeEnd.getText().toString();
                String setBz = etBz.getText().toString();

                if (TextUtils.isEmpty(sfaultType) || TextUtils.isEmpty(sfaultTimeStart) || TextUtils.isEmpty(sfaultAddress) || TextUtils.isEmpty(sfaultTimeEnd) || TextUtils.isEmpty(setBz)) {
                    ToastUtil.show(this, "请填写所有信息");
                } else {

                    /**  车辆故障上报
                     * @param idBizcar        车辆ID;（选择车辆的ID）
                     * @param carfaultType    故障类型;（填写）
                     * @param carfaultAddress 故障地点;（填写）
                     * @param sbrCarfault     上报人姓名;（填写）
                     * @param sbsjCarfault    上报时间;（填写yyyy-mm-dd 24hh:mi:ss）
                     * @param bzCarfault      备注;（填写）
                     * @param yjwcsjRepair    完成时间
                     */
                    showLoadingDialog("故障上报中", false);
                    carStatePresenter.CarFaultSubmit(carRowsBean.vid+"", sfaultType, sfaultAddress, BaseApplication.getUserInfo().name, sfaultTimeStart, setBz, sfaultTimeEnd);
                }

                break;
        }
    }


    private void seleTimePicker (){
        TimePickerView pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        //控制时间范围
//      Calendar calendar = Calendar.getInstance();
//      pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setTitle("选择起运时间");
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                faultTimeEnd.setText(TimeUtils.getStringByFormat(date, "yyyy-MM-dd HH:mm:ss"));
            }
        });
        pvTime.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0) {
            return;
        }
        switch (resultCode) {
            case SysContant.QueryType.car_code:  //选择车辆
                Bundle bundlecar = data.getExtras();
                carRowsBean = (CarInfo.RowsBean) bundlecar.getSerializable(SysContant.QueryType.query_type);
                faultCar.setText(carRowsBean.licensePlate);
                break;

        }
    }


    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(CarFaultActivity.this, errorMsg);
    }

    @Override
    public void SuccessFinshByCarFaultSubmit() {
        dismiss();
        ToastUtil.show(CarFaultActivity.this, "故障上报成功");
        finish();
    }


}
