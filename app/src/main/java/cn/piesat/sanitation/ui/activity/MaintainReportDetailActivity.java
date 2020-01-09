package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.MaintainList;
import cn.piesat.sanitation.ui.view.CommentItemModul;

/**
 * 站长 新增维修上报
 * Created by sen.luo on 2020/1/2.
 */
public class MaintainReportDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.car_num)
    CommentItemModul carNum;
    @BindView(R.id.deriver_person)
    CommentItemModul deriverPerson;
    @BindView(R.id.weixiudanwei)
    CommentItemModul weixiudanwei;
    @BindView(R.id.weixiujiage)
    CommentItemModul weixiujiage;
    @BindView(R.id.report_person)
    CommentItemModul reportPerson;
    @BindView(R.id.area)
    CommentItemModul area;
    @BindView(R.id.station_name)
    CommentItemModul stationName;
    @BindView(R.id.order_bz)
    TextView orderBz;
    @BindView(R.id.iv_paizhao_xianchang)
    ImageView ivPaizhaoXianchang;
    @BindView(R.id.iv_paizhao_weixiu)
    ImageView ivPaizhaoWeixiu;
    @BindView(R.id.iv_paizhao_order)
    ImageView ivPaizhaoOrder;
    private MaintainList.RecordsBean rowsBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_maintain_report_detail;
    }

    @Override
    protected void initView() {
        tv_title.setText("维修详情");

    }

    @Override
    protected void initData() {
        GetIntentValue();
    }

    private void GetIntentValue() {
        Intent intent = getIntent();
        rowsBean = (MaintainList.RecordsBean) intent.getSerializableExtra(SysContant.CommentTag.comment_key);
        if (null == rowsBean) {
            return;
        }
        showBaseInfo(rowsBean);
    }

    private void showBaseInfo(MaintainList.RecordsBean rowsBean) {

        carNum.setText(rowsBean.carNumber);
        deriverPerson.setText(rowsBean.driver);
        weixiudanwei.setText(rowsBean.maintenanceUnit);
        weixiujiage.setText(rowsBean.maintenancePrice + "");
        reportPerson.setText(null!=rowsBean.appFlowInst?rowsBean.appFlowInst.sendUser:"");
        area.setText(rowsBean.administrativeArea);
        stationName.setText(rowsBean.siteName);
        orderBz.setText(rowsBean.maintenanceReason);
        Glide.with(MaintainReportDetailActivity.this)
                .load(IPConfig.getOutSourceURLPreFix() + rowsBean.scenePhoto)
                .into(ivPaizhaoXianchang);

        Glide.with(MaintainReportDetailActivity.this)
                .load(IPConfig.getOutSourceURLPreFix() + rowsBean.maintainPhoto)
                .into(ivPaizhaoWeixiu);
        Glide.with(MaintainReportDetailActivity.this)
                .load(IPConfig.getOutSourceURLPreFix() + rowsBean.maintainBillPhoto)
                .into(ivPaizhaoOrder);
    }


    @OnClick({R.id.img_back, R.id.iv_paizhao_xianchang, R.id.iv_paizhao_weixiu, R.id.iv_paizhao_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.iv_paizhao_xianchang:
                lookImageXianchang();
                break;
            case R.id.iv_paizhao_weixiu:
                lookImageWeixiu();
                break;
            case R.id.iv_paizhao_order:
                lookImageOrder();
                break;
        }
    }

    /**
     * 查看维修图片
     */
    private void lookImageWeixiu() {
        if (null != rowsBean.maintainPhoto && !TextUtils.isEmpty(rowsBean.maintainPhoto)) {
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("images", IPConfig.getOutSourceURLPreFix() + rowsBean.maintainPhoto);//非必须
            int[] location = new int[2];
            ivPaizhaoWeixiu.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);//必须
            intent.putExtra("locationY", location[1]);//必须
            intent.putExtra("width", ivPaizhaoWeixiu.getWidth());//必须
            intent.putExtra("height", ivPaizhaoWeixiu.getHeight());//必须
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    /**
     * 查看现场图片
     */
    private void lookImageXianchang() {
        if (null != rowsBean.scenePhoto && !TextUtils.isEmpty(rowsBean.scenePhoto)) {
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("images", IPConfig.getOutSourceURLPreFix() + rowsBean.scenePhoto);//非必须
            int[] location = new int[2];
            ivPaizhaoXianchang.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);//必须
            intent.putExtra("locationY", location[1]);//必须
            intent.putExtra("width", ivPaizhaoXianchang.getWidth());//必须
            intent.putExtra("height", ivPaizhaoXianchang.getHeight());//必须
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    /**
     * 查看订单图片
     */
    private void lookImageOrder() {
        if (null != rowsBean.maintainBillPhoto && !TextUtils.isEmpty(rowsBean.maintainBillPhoto)) {
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("images", IPConfig.getOutSourceURLPreFix() + rowsBean.maintainBillPhoto);//非必须
            int[] location = new int[2];
            ivPaizhaoOrder.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);//必须
            intent.putExtra("locationY", location[1]);//必须
            intent.putExtra("width", ivPaizhaoOrder.getWidth());//必须
            intent.putExtra("height", ivPaizhaoOrder.getHeight());//必须
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }
}
