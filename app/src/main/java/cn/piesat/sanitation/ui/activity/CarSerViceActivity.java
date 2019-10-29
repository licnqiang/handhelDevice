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
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 汽车维保
 */
public class CarSerViceActivity extends BaseActivity implements CarStateContract.CarStateViewService{


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_car)
    CommentItemModul tvCar;
    @BindView(R.id.tv_type)
    CommentItemModul tvType;
    @BindView(R.id.tv_address)
    CommentItemInputModul tvAddress;
    @BindView(R.id.tv_car_par)
    CommentItemInputModul tvCarPar;
    @BindView(R.id.tv_time)
    CommentItemModul tvTime;
    @BindView(R.id.tv_money)
    CommentItemInputModul tvMoney;
    @BindView(R.id.end_time)
    CommentItemModul endTime;
    @BindView(R.id.et_bz)
    EditText etBz;

    private CarInfo.RowsBean carRowsBean;
    CarStatePresenter carStatePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_ser_vice;
    }

    @Override
    protected void initView() {
        tvTitle.setText("汽车维保");
        carStatePresenter = new CarStatePresenter(this);
    }

    @Override
    protected void initData() {
        tvTime.setText(TimeUtils.getCurrentTimeMMSS());
    }

    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("维修", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvType.setText("维修");
                    }
                }).addSheetItem("保养", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvType.setText("保养");
                    }
                });
        dialog.show();
    }

    @OnClick({R.id.img_back, R.id.btn_submit, R.id.tv_car, R.id.end_time,R.id.tv_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                showDialog();
                break;
            case R.id.tv_car:
                Intent intentcar = new Intent();
                intentcar.setClass(this, ItemSelectActivity.class);
                Bundle bundlecar = new Bundle();
                bundlecar.putString(SysContant.QueryType.query_type, SysContant.QueryType.car_key);
                intentcar.putExtras(bundlecar);
                startActivityForResult(intentcar, 0);
                break;
            case R.id.end_time:
                seleTimePicker ();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                String sfaultType="";
                String getfaultType = tvType.getText().toString();
                if(getfaultType.equals("维修")){
                    sfaultType="1";
                }else if(getfaultType.equals("保养")){
                    sfaultType="2";
                }else {
                    sfaultType="";
                }
                String sfaultTimeStart = tvTime.getText().toString();
                String sfaultAddress = tvAddress.getText().toString();
                String sfaultTimeEnd = endTime.getText().toString();
                String carPar = tvCarPar.getText().toString();
                String money = tvMoney.getText().toString();
                String setBz = etBz.getText().toString();

                if (TextUtils.isEmpty(sfaultType) || TextUtils.isEmpty(sfaultTimeStart) || TextUtils.isEmpty(sfaultAddress) || TextUtils.isEmpty(sfaultTimeEnd) || TextUtils.isEmpty(setBz)|| TextUtils.isEmpty(carPar)|| TextUtils.isEmpty(carPar)) {
                    ToastUtil.show(this, "请填写所有信息");
                } else {

                    /**
                     *  车辆维保上报
                     * @param idBizcar                车辆ID;（选择车辆的ID）
                     * @param typeRepair              维保类型(1维修;2保养);（选择，传1或者2）
                     * @param sgdwRepair              车辆保养/维修施工单位;（填写）
                     * @param sjRepair                保养/维修时间;（填写）
                     * @param xmRepair                保养/维修项目(可能有多个);（填写）
                     * @param jeRepair                保养/维修项目金额;（填写）
                     * @param sbrRpair                上报人姓名;（填写）
                     * @param bzRepair                备注;（填写）
                     * @param yjwcsjCarfault          完成时间
                     */
                    showLoadingDialog("维保上报中", false);
                    carStatePresenter.CarServiceSubmit(carRowsBean.vid+"", sfaultType, sfaultAddress,sfaultTimeStart,carPar,money, BaseApplication.getUserInfo().name, setBz, sfaultTimeEnd);
                }

                break;
        }
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
                tvCar.setText(carRowsBean.licensePlate);
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
                endTime.setText(TimeUtils.getStringByFormat(date, "yyyy-MM-dd HH:mm:ss"));
            }
        });
        pvTime.show();
    }



    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(CarSerViceActivity.this, errorMsg);
    }

    @Override
    public void SuccessFinshByCarServiceSubmit() {
        dismiss();
        ToastUtil.show(CarSerViceActivity.this, "故障上报成功");
        finish();
    }
}
