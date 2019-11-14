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
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

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
    @BindView(R.id.springView)
    SpringView springView;

    private String queryType; //查询类型
    private CompressStationAdapter compressStationAdapter;
    private CarAdapter carAdapter;
    private DriverAdapter driverAdapter;
    private BurnStationAdapter burnStationAdapter;

    List<CompressStations.RowsBean> rowsBeanList;
    List<CarInfo.RowsBean> carInfos;
    List<DriverInfo.RowsBean> driverInfos;
    List<BurnStationInfo.RowsBean> burnInfos;

    private int pageNum=1;
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
                    queryPresenter.QueryCompress(pageNum, SysContant.CommentTag.pageSize);
                    break;
                case SysContant.QueryType.car_key:  //车辆
                    tvTitle.setText("选择车辆");
                    setCarAdapter();
                    queryPresenter.QueryCar(pageNum, SysContant.CommentTag.pageSize, 0);
                    break;
                case SysContant.QueryType.driver_key:  //司机
                    tvTitle.setText("选择驾驶员");
                    setDriverAdapter();
                    queryPresenter.QueryDriver(pageNum, SysContant.CommentTag.pageSize, 7, BaseApplication.getUserInfo().idSysdept);
                    break;
                case SysContant.QueryType.burn_key:  //焚烧厂
                    tvTitle.setText("选择焚烧厂");
                    setBurnAdapter();
                    queryPresenter.QueryBurnStation(pageNum, SysContant.CommentTag.pageSize);
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
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                queryPresenter.QueryCompress(pageNum, SysContant.CommentTag.pageSize);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                queryPresenter.QueryCompress(pageNum, SysContant.CommentTag.pageSize);
            }
        });
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
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                queryPresenter.QueryCar(pageNum, SysContant.CommentTag.pageSize, 0);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                queryPresenter.QueryCar(pageNum, SysContant.CommentTag.pageSize, 0);
            }
        });
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
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                queryPresenter.QueryDriver(pageNum, SysContant.CommentTag.pageSize, 7, BaseApplication.getUserInfo().idSysdept);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                queryPresenter.QueryDriver(pageNum, SysContant.CommentTag.pageSize, 7, BaseApplication.getUserInfo().idSysdept);
            }
        });
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
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                queryPresenter.QueryBurnStation(pageNum, SysContant.CommentTag.pageSize);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                queryPresenter.QueryBurnStation(pageNum, SysContant.CommentTag.pageSize);
            }
        });
    }


    @Override
    public void Error(String errorMsg) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        ToastUtil.show(this, errorMsg);
    }


    //查询压缩厂
    @Override
    public void SuccessFinshByCompress(CompressStations compressStations) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        if (null!=compressStations.rows&&compressStations.rows.size()>0){

            if (pageNum==1){
                compressStationAdapter.refreshData(compressStations.rows);
            }else {
                compressStationAdapter.addAll(compressStations.rows);
            }
        }else {
            ToastUtil.show(this,"没有更多数据咯!");
        }
    }

    //查询车辆
    @Override
    public void SuccessFinshByCar(CarInfo carInfo) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        if (null!=carInfo.rows&&carInfo.rows.size()>0){

            if (pageNum==1){
                carAdapter.refreshData(carInfo.rows);
            }else {
                carAdapter.addAll(carInfo.rows);
            }
        }else {
            ToastUtil.show(this,"没有更多数据咯!");
        }
    }

    //查询焚烧厂
    @Override
    public void SuccessFinshByBurnStation(BurnStationInfo burnStationInfo) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        if (null!=burnStationInfo.rows&&burnStationInfo.rows.size()>0){

            if (pageNum==1){
                burnStationAdapter.refreshData(burnStationInfo.rows);
            }else {
                burnStationAdapter.addAll(burnStationInfo.rows);
            }
        }else {
            ToastUtil.show(this,"没有更多数据咯!");
        }
    }

    //查询司机
    @Override
    public void SuccessFinshByDriver(DriverInfo driverInfo) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();

        if (null!=driverInfo.rows&&driverInfo.rows.size()>0){

            if (pageNum==1){
                driverAdapter.refreshData(driverInfo.rows);
            }else {
                driverAdapter.addAll(driverInfo.rows);
            }
        }else {
            ToastUtil.show(this,"没有更多数据咯!");
        }
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
