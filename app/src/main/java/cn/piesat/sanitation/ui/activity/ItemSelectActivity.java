package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
import cn.piesat.sanitation.model.contract.QueryContract;
import cn.piesat.sanitation.model.presenter.QueryPresenter;
import cn.piesat.sanitation.ui.adapter.BurnStationAdapter;
import cn.piesat.sanitation.ui.adapter.CarAdapter;
import cn.piesat.sanitation.ui.adapter.CompressStationAdapter;
import cn.piesat.sanitation.ui.adapter.DriverAdapter;
import cn.piesat.sanitation.util.ToastUtil;


public class ItemSelectActivity extends BaseActivity implements QueryContract.QueryView, CompressStationAdapter.OnRecyclerViewItemClickListener
        , CarAdapter.OnRecyclerViewItemClickListener
        , DriverAdapter.OnRecyclerViewItemClickListener
        , BurnStationAdapter.OnRecyclerViewItemClickListener {

    QueryPresenter queryPresenter;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    private String queryType; //查询类型
    private CompressStationAdapter compressStationAdapter;
    private CarAdapter carAdapter;
    private DriverAdapter driverAdapter;
    private BurnStationAdapter burnStationAdapter;

    List<CompressStations.RowsBean> rowsBeanList;
    List<CarInfo.RowsBean> carInfos;
    List<DriverInfo.RowsBean> driverInfos;
    List<BurnStationInfo.RowsBean> burnInfos;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_item_select;
    }

    @Override
    protected void initView() {
    }


    @Override
    protected void initData() {
        queryPresenter = new QueryPresenter(this);
        showLoadingDialog();
        GetIntentValue();
    }

    private void GetIntentValue() {
        Intent intent = getIntent();
        queryType = intent.getStringExtra(SysContant.QueryType.query_type);
        if (null != queryType && !TextUtils.isEmpty(queryType)) {
            switch (queryType) {
                case SysContant.QueryType.compress_station_key:  //压缩站
                    tvTitle.setText("选择压缩站");
                    setCompressStationAdapter();
                    queryPresenter.QueryCompress(1, -1);
                    break;
                case SysContant.QueryType.car_key:  //车辆
                    tvTitle.setText("选择车辆");
                    setCarAdapter();
                    queryPresenter.QueryCar(1, -1, 0);
                    break;
                case SysContant.QueryType.driver_key:  //司机
                    tvTitle.setText("选择驾驶员");
                    setDriverAdapter();
                    queryPresenter.QueryDriver(1, -1, 7, BaseApplication.getUserInfo().idSysdept);
                    break;
                case SysContant.QueryType.burn_key:  //焚烧厂
                    tvTitle.setText("选择焚烧厂");
                    setBurnAdapter();
                    queryPresenter.QueryBurnStation(1, -1);
                    break;
            }
        }
    }

    /**
     * 设置压缩站列表
     */
    private void setCompressStationAdapter() {
        rowsBeanList = new ArrayList<>();
        compressStationAdapter = new CompressStationAdapter(this, rowsBeanList);
        compressStationAdapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(compressStationAdapter);
    }

    /**
     * 设置车辆列表
     */
    private void setCarAdapter() {
        carInfos = new ArrayList<>();
        carAdapter = new CarAdapter(this, carInfos);
        carAdapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(carAdapter);
    }

    /**
     * 设置司机列表
     */
    private void setDriverAdapter() {
        driverInfos = new ArrayList<>();
        driverAdapter = new DriverAdapter(this, driverInfos);
        driverAdapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(driverAdapter);
    }

    /**
     * 设置焚烧厂列表
     */
    private void setBurnAdapter() {
        burnInfos = new ArrayList<>();
        burnStationAdapter = new BurnStationAdapter(this, burnInfos);
        burnStationAdapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(burnStationAdapter);
    }


    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(this, errorMsg);
    }


    //查询压缩厂
    @Override
    public void SuccessFinshByCompress(CompressStations compressStations) {
        dismiss();
        compressStationAdapter.refreshData(compressStations.rows);
    }

    //查询车辆
    @Override
    public void SuccessFinshByCar(CarInfo carInfo) {
        dismiss();
        carAdapter.refreshData(carInfo.rows);
    }

    //查询焚烧厂
    @Override
    public void SuccessFinshByBurnStation(BurnStationInfo burnStationInfo) {
        dismiss();
        burnStationAdapter.refreshData(burnStationInfo.rows);
    }

    //查询司机
    @Override
    public void SuccessFinshByDriver(DriverInfo driverInfo) {
        dismiss();
        driverAdapter.refreshData(driverInfo.rows);
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    //压缩站列表点击
    @Override
    public void onItemClick(View view, int position) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SysContant.QueryType.query_type, rowsBeanList.get(position));
        resultIntent.putExtras(bundle);
        setResult(SysContant.QueryType.compress_station_code, resultIntent);
        finish();
    }

    //车辆列表点击
    @Override
    public void onCarItemClick(View view, int position) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SysContant.QueryType.query_type, carInfos.get(position));
        resultIntent.putExtras(bundle);
        setResult(SysContant.QueryType.car_code, resultIntent);
        finish();
    }

    //司机列表点击
    @Override
    public void onDriverItemClick(View view, int position) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SysContant.QueryType.query_type, driverInfos.get(position));
        resultIntent.putExtras(bundle);
        setResult(SysContant.QueryType.driver_code, resultIntent);
        finish();
    }

    //焚烧厂列表点击
    @Override
    public void onBrunItemClick(View view, int position) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SysContant.QueryType.query_type, burnInfos.get(position));
        resultIntent.putExtras(bundle);
        setResult(SysContant.QueryType.burn_code, resultIntent);
        finish();
    }
}
