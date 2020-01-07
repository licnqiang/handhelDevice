package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hb.dialog.myDialog.ActionSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.DriverInfo;
import cn.piesat.sanitation.data.GasonLines;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.model.contract.GasonLineReportContract;
import cn.piesat.sanitation.model.presenter.GasonLineReportPresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.ui.view.CommentItemInputModul;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长 新增违章上报
 * Created by sen.luo on 2020/1/2.
 */
public class GasoLineReportDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.station_name)
    CommentItemModul stationName;
    @BindView(R.id.car_num)
    CommentItemModul carNum;
    @BindView(R.id.report_person)
    CommentItemModul reportPerson;
    @BindView(R.id.gaso_line_person)
    CommentItemModul gasoLinePerson;
    @BindView(R.id.goso_line_station)
    CommentItemModul gosoLineStation;
    @BindView(R.id.before_mileage)
    CommentItemModul beforeMileage;
    @BindView(R.id.goso_line_money)
    CommentItemModul gosoLineMoney;
    @BindView(R.id.goso_line_litre)
    CommentItemModul gosoLineLitre;
    @BindView(R.id.area)
    CommentItemModul area;
    @BindView(R.id.order_bz)
    TextView orderBz;
    @BindView(R.id.iv_paizhao)
    ImageView ivPaizhao;
    private GasonLines.RecordsBean rowsBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_gasoline_report_detail;
    }

    @Override
    protected void initView() {
        tv_title.setText("详情");
    }

    @Override
    protected void initData() {
        GetIntentValue();
    }


    private void GetIntentValue() {
        Intent intent = getIntent();
        rowsBean = (GasonLines.RecordsBean) intent.getSerializableExtra(SysContant.CommentTag.comment_key);
        if (null == rowsBean) {
            return;
        }
        showBaseInfo(rowsBean);
    }

    private void showBaseInfo(GasonLines.RecordsBean rowsBean) {
        stationName.setText(rowsBean.siteName);
        carNum.setText(rowsBean.carNumber);
        reportPerson.setText(rowsBean.reportperson);
        gasoLinePerson.setText(rowsBean.refuelingPerson);
        gosoLineStation.setText(rowsBean.gasStation);
        beforeMileage.setText(rowsBean.oldMileage);
        gosoLineMoney.setText(rowsBean.oilPrice);
        gosoLineLitre.setText(rowsBean.oilLitre);
        area.setText(rowsBean.administrativeArea);
        orderBz.setText(rowsBean.remark);

        //显示图片
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.paizhao)
                .error(R.mipmap.paizhao)
                .fallback(R.mipmap.paizhao);
        Glide.with(GasoLineReportDetailActivity.this)
                .load(IPConfig.getOutSourceURLPreFix() + rowsBean.oilPhoto)
                .apply(requestOptions)
                .into(ivPaizhao);
    }


    @OnClick({R.id.img_back, R.id.iv_paizhao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 返回
            case R.id.img_back:
                finish();
                break;
            //照片
            case R.id.iv_paizhao:
                lookImage();
                break;
        }
    }


    private void lookImage() {
        if (null != rowsBean.oilPhoto && !TextUtils.isEmpty(rowsBean.oilPhoto)) {
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("images", IPConfig.getOutSourceURLPreFix() + rowsBean.oilPhoto);//非必须
            int[] location = new int[2];
            ivPaizhao.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);//必须
            intent.putExtra("locationY", location[1]);//必须
            intent.putExtra("width", ivPaizhao.getWidth());//必须
            intent.putExtra("height", ivPaizhao.getHeight());//必须
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

}
