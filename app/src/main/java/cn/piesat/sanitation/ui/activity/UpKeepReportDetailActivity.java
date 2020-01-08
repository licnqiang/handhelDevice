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
import cn.piesat.sanitation.data.GasonLines;
import cn.piesat.sanitation.data.UpKeepList;
import cn.piesat.sanitation.model.contract.UpKeepReportContract;
import cn.piesat.sanitation.model.presenter.UpkeepReportPresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.ui.view.CommentItemInputModul;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长 新增违章上报
 * Created by sen.luo on 2020/1/2.
 */
public class UpKeepReportDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.car_num)
    CommentItemModul carNum;
    @BindView(R.id.maintain_company)
    CommentItemModul maintainCompany;
    @BindView(R.id.maintain_price)
    CommentItemModul maintainPrice;
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
    @BindView(R.id.iv_paizhao_order)
    ImageView ivPaizhaoOrder;
    private UpKeepList.RecordsBean rowsBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upkeep_report_add;
    }

    @Override
    protected void initView() {
        tv_title.setText("保养上报");
    }

    @Override
    protected void initData() {
        GetIntentValue();
    }


    private void GetIntentValue() {
        Intent intent = getIntent();
        rowsBean = (UpKeepList.RecordsBean) intent.getSerializableExtra(SysContant.CommentTag.comment_key);
        if (null == rowsBean) {
            return;
        }
        showBaseInfo(rowsBean);
    }

    private void showBaseInfo(UpKeepList.RecordsBean rowsBean) {

         carNum.setText(rowsBean.carNumber);
         maintainCompany.setText(rowsBean.maintainUnit);
         maintainPrice.setText(rowsBean.maintainPrice);
         reportPerson.setText(rowsBean.appFlowInst.sendUser);
         area.setText(rowsBean.administrativeArea);
         stationName.setText(rowsBean.siteName);
         orderBz.setText(rowsBean.maintainDescribe);
//        ImageView ivPaizhaoXianchang;
//        ImageView ivPaizhaoOrder;
//
//
//        Glide.with(UpKeepReportDetailActivity.this)
//                .load(IPConfig.getOutSourceURLPreFix() + rowsBean.oilPhoto)
//                .apply(requestOptions)
//                .into(ivPaizhao);
    }


    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }




    private void uploadFile(String path, int type) {
        List<String> paths = new ArrayList<>();
        paths.add(path);
        UpLoadFileControl.uploadFile(false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.upload, paths, null, new UpLoadFileControl.ResultCallBack() {
            @Override
            public void succeed(Object str) {

                dismiss();
                //显示图片
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.mipmap.paizhao)
                        .error(R.mipmap.paizhao)
                        .fallback(R.mipmap.paizhao);
                Glide.with(UpKeepReportDetailActivity.this)
                        .load(IPConfig.getOutSourceURLPreFix() + str)
                        .apply(requestOptions)
                        .into(type == 1 ? ivPaizhaoXianchang : ivPaizhaoOrder);
            }

            @Override
            public void faild() {
                ToastUtil.show(UpKeepReportDetailActivity.this, "图片上传失败，请重新上传");
                dismiss();
            }
        });

    }


    /**
     * 提交审批信息
     */
    private void getReport(String approval) {
        String station = stationName.getText().toString().trim();
        String s_carNumber = carNum.getText().toString().trim();
        String s_reportPerson = reportPerson.getText().toString().trim();
        String s_maintainCompany = maintainCompany.getText().toString().trim();
        String s_maintainPrice = maintainPrice.getText().toString().trim();
        String s_area = area.getText().toString().trim();
        String s_orderBz = orderBz.getText().toString().trim();

        showLoadingDialog();
        Map<String, String> addMap = new HashMap<>();
        addMap.put("approval", approval);//审批流程id
        addMap.put("roleId", BaseApplication.getUserInfo().userType + "");//站点名称
        addMap.put("userId", BaseApplication.getUserInfo().id);//站点名称
        addMap.put("administrativeArea", s_area);//行政区划
        addMap.put("siteName", station);//站点名称
        addMap.put("carNumber", s_carNumber);//车牌号
        addMap.put("reportperson", s_reportPerson);//上报人
        addMap.put("maintainUnit", s_maintainCompany);//保养单位
        addMap.put("maintainPrice", s_maintainPrice);//保养价格
        addMap.put("userId", BaseApplication.getIns().getUserId());//加油升数
        addMap.put("roleId", SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID));//加油升数
        if (!s_orderBz.isEmpty()) {
            addMap.put("maintainDescribe", s_orderBz);
            addMap.put("remark", s_orderBz);
        }
    }



}
