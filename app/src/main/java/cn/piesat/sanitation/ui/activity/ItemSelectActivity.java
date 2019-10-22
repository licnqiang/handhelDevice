package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.model.contract.QueryContract;
import cn.piesat.sanitation.model.presenter.QueryPresenter;
import cn.piesat.sanitation.ui.adapter.CompressStationAdapter;
import cn.piesat.sanitation.ui.adapter.OrderAdapter;
import cn.piesat.sanitation.util.Log;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;


public class ItemSelectActivity extends BaseActivity implements QueryContract.QueryView, CompressStationAdapter.OnRecyclerViewItemClickListener {

    QueryPresenter queryPresenter;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    private String queryType; //查询类型
    private CompressStationAdapter compressStationAdapter;
    CompressStations compressStations;
    List<CompressStations.RowsBean> rowsBeanList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_item_select;
    }

    @Override
    protected void initView() {
        rowsBeanList = new ArrayList<>();
        compressStationAdapter = new CompressStationAdapter(this, rowsBeanList);
        compressStationAdapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(compressStationAdapter);
    }


    @Override
    protected void initData() {
        queryPresenter = new QueryPresenter(this);
        GetIntentValue();
    }

    private void GetIntentValue() {
        Intent intent = getIntent();
        queryType = intent.getStringExtra(SysContant.QueryType.query_type);
        if (null != queryType && !TextUtils.isEmpty(queryType)) {
            switch (queryType) {
                case SysContant.QueryType.compress_station_key:  //压缩站
                    tvTitle.setText("选择压缩站");
                    queryPresenter.QueryCompress(1, -1);
                    break;
            }
        }
    }

    @Override
    public void Error(String errorMsg) {
        ToastUtil.show(this, errorMsg);
    }

    @Override
    public void SuccessFinshByCompress(CompressStations compressStations) {
        compressStationAdapter.refreshData(compressStations.rows);
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
}
