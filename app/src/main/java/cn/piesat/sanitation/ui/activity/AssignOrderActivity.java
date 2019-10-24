package cn.piesat.sanitation.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.BurnStationInfo;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.DriverInfo;
import cn.piesat.sanitation.model.contract.OrderContract;
import cn.piesat.sanitation.model.presenter.OrderPresenter;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长派单
 */
public class AssignOrderActivity extends BaseActivity implements OrderContract.AssignOrderView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.station_name)
    CommentItemModul stationName;
    @BindView(R.id.car_num)
    CommentItemModul carNum;
    @BindView(R.id.car_people)
    CommentItemModul carPeople;
    @BindView(R.id.car_start_time)
    CommentItemModul carStartTime;
    @BindView(R.id.car_send_address)
    CommentItemModul carSendAddress;
    @BindView(R.id.car_send_address_time)
    CommentItemModul carSendAddressTime;
    @BindView(R.id.car_header)
    CommentItemModul carHeader;
    @BindView(R.id.order_bz)
    EditText orderBz;

    OrderPresenter orderPresenter;
    private CompressStations.RowsBean rowsBean;
    private CarInfo.RowsBean carRowsBean;
    private DriverInfo.RowsBean driverRowsBean;
    private BurnStationInfo.RowsBean burnRowsBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_assign_order;
    }

    @Override
    protected void initView() {
        tvTitle.setText("派单");
    }

    @Override
    protected void initData() {
        orderPresenter = new OrderPresenter(this);
        carHeader.setText(BaseApplication.getUserInfo().name);  //显示派单人
    }


    @OnClick({R.id.btn_assign_order, R.id.img_back, R.id.station_name, R.id.car_num, R.id.car_people, R.id.car_start_time, R.id.car_send_address, R.id.car_send_address_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //派单
            case R.id.btn_assign_order:
                String name = stationName.getText();
                String num = carNum.getText();
                String people = carPeople.getText();
                String startTime = carStartTime.getText();
                String sendAddress = carSendAddress.getText();
                String sendAddressTime = carSendAddressTime.getText();
                String header = carHeader.getText();

                if (null==rowsBean||null==carRowsBean||null==driverRowsBean||null==burnRowsBean||TextUtils.isEmpty(name)||TextUtils.isEmpty(num)||TextUtils.isEmpty(people)||TextUtils.isEmpty(startTime)||TextUtils.isEmpty(sendAddress)||TextUtils.isEmpty(sendAddressTime)||TextUtils.isEmpty(header)) {
                    ToastUtil.show(this,"亲，信息不全不能派单哦！");
                }else {
                    showLoadingDialog("正在派单中",false);
                    orderPresenter.AssignOrder(burnRowsBean.idFsc, burnRowsBean.nameFsc, carRowsBean.vid, sendAddressTime, startTime,
                            orderBz.getText()+"", driverRowsBean.id, rowsBean.idYsz, rowsBean.nameYsz);
                }


                break;
            //返回
            case R.id.img_back:
                finish();
                break;
            //压缩站
            case R.id.station_name:
                Intent intent = new Intent();
                intent.setClass(this, ItemSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(SysContant.QueryType.query_type, SysContant.QueryType.compress_station_key);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
                break;
            //车牌
            case R.id.car_num:
                Intent intentcar = new Intent();
                intentcar.setClass(this, ItemSelectActivity.class);
                Bundle bundlecar = new Bundle();
                bundlecar.putString(SysContant.QueryType.query_type, SysContant.QueryType.car_key);
                intentcar.putExtras(bundlecar);
                startActivityForResult(intentcar, 0);
                break;
            //驾驶员
            case R.id.car_people:
                Intent intentDriver = new Intent();
                intentDriver.setClass(this, ItemSelectActivity.class);
                Bundle bundleDriver = new Bundle();
                bundleDriver.putString(SysContant.QueryType.query_type, SysContant.QueryType.driver_key);
                intentDriver.putExtras(bundleDriver);
                startActivityForResult(intentDriver, 0);
                break;
            //起运时间
            case R.id.car_start_time:
                seleTimePicker(carStartTime);
                break;
            //焚烧厂
            case R.id.car_send_address:
                Intent intentBurn = new Intent();
                intentBurn.setClass(this, ItemSelectActivity.class);
                Bundle bundleBurn = new Bundle();
                bundleBurn.putString(SysContant.QueryType.query_type, SysContant.QueryType.burn_key);
                intentBurn.putExtras(bundleBurn);
                startActivityForResult(intentBurn, 0);
                break;
            //抵达时间
            case R.id.car_send_address_time:
                seleTimePicker(carSendAddressTime);
                break;
        }
    }

    private void seleTimePicker(CommentItemModul textView) {
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
                textView.setText(TimeUtils.getStringByFormat(date, "yyyy-MM-dd HH:mm:ss"));
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
            case SysContant.QueryType.compress_station_code:  //选择压缩站
                Bundle bundle = data.getExtras();
                rowsBean = (CompressStations.RowsBean) bundle.getSerializable(SysContant.QueryType.query_type);
                stationName.setText(rowsBean.nameYsz);
                break;
            case SysContant.QueryType.car_code:  //选择车辆
                Bundle bundlecar = data.getExtras();
                carRowsBean = (CarInfo.RowsBean) bundlecar.getSerializable(SysContant.QueryType.query_type);
                carNum.setText(carRowsBean.licensePlate);
                break;
            case SysContant.QueryType.driver_code:  //选择司机
                Bundle bundledriver = data.getExtras();
                driverRowsBean = (DriverInfo.RowsBean) bundledriver.getSerializable(SysContant.QueryType.query_type);
                carPeople.setText(driverRowsBean.name);
                break;
            case SysContant.QueryType.burn_code:  //选择焚烧厂
                Bundle bundlebrun = data.getExtras();
                burnRowsBean = (BurnStationInfo.RowsBean) bundlebrun.getSerializable(SysContant.QueryType.query_type);
                carSendAddress.setText(burnRowsBean.nameFsc);
                break;
        }
    }

    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(this, errorMsg);
    }

    @Override
    public void SuccessFinshByAssign() {
        dismiss();
        ToastUtil.show(this, "派单成功");
        finish();
    }
}
