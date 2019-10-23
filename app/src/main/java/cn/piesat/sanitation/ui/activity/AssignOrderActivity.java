package cn.piesat.sanitation.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.ui.view.CommentItemModul;

/**
 * 站长派单
 */
public class AssignOrderActivity extends BaseActivity {

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

    }


    @OnClick({R.id.img_back, R.id.station_name, R.id.car_num, R.id.car_people, R.id.car_start_time, R.id.car_send_address, R.id.car_send_address_time, R.id.car_header})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                break;
            //焚烧厂
            case R.id.car_send_address:
                Intent intentBurn = new Intent();
                intentBurn.setClass(this, ItemSelectActivity.class);
                Bundle bundleBurn = new Bundle();
                bundleBurn.putString(SysContant.QueryType.query_type, SysContant.QueryType.burn_key);
                intentBurn.putExtras(bundleBurn);
                startActivityForResult(intentBurn, 0);
            //抵达时间
            case R.id.car_send_address_time:
                break;
            //派单人
            case R.id.car_header:
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0) {
            return;
        }
        switch (resultCode) {
            case SysContant.QueryType.compress_station_code:  //选择压缩站
                Bundle bundle = data.getExtras();
                CompressStations.RowsBean rowsBean = (CompressStations.RowsBean) bundle.getSerializable(SysContant.QueryType.query_type);
                stationName.setText(rowsBean.nameYsz);
                break;
            case SysContant.QueryType.car_code:  //选择车辆
                Bundle bundlecar = data.getExtras();
                CarInfo.RowsBean carRowsBean = (CarInfo.RowsBean) bundlecar.getSerializable(SysContant.QueryType.query_type);
                carNum.setText(carRowsBean.licensePlate);
                break;
        }
    }

}
