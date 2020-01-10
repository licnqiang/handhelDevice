package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hb.dialog.myDialog.ActionSheetDialog;
import com.hb.dialog.myDialog.MyAlertInputDialog;

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
import cn.piesat.sanitation.model.contract.ApprovalContract;
import cn.piesat.sanitation.model.contract.UpKeepReportContract;
import cn.piesat.sanitation.model.presenter.ApprovalPresenter;
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
public class UpKeepReportDetailActivity extends BaseActivity implements ApprovalContract.ApprovalView{

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
    @BindView(R.id.approval_state)
    LinearLayout approvalState;

    private UpKeepList.RecordsBean rowsBean;

    ApprovalPresenter approvalPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upkeep_report_detail;
    }

    @Override
    protected void initView() {
        tv_title.setText("保养上报");
    }

    @Override
    protected void initData() {
        GetIntentValue();
        approvalPresenter=new ApprovalPresenter(this);
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

       String roleTyep= SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID); //保存角色id

        if (roleTyep == "3") {                             //判断若需要当前用户审批时，显示审批按钮
            approvalState.setVisibility(View.VISIBLE);
        }

         carNum.setText(rowsBean.carNumber);
         maintainCompany.setText(rowsBean.maintainUnit);
         maintainPrice.setText(rowsBean.maintainPrice);
//         reportPerson.setText(rowsBean.appFlowInst.sendUser);
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


    @OnClick({R.id.img_back,R.id.btn_pass,R.id.btn_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            //审批通过
            case R.id.btn_pass:
                showLoadingDialog();
                approvalPresenter.approvalHandlePass(rowsBean.approval);
                break;
            //审批驳回
            case R.id.btn_del:
                showDialog();
                break;
        }
    }


    private void showDialog() {
        final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(this).builder()
                .setTitle("请输入")
                .setEditText("");
        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertInputDialog.dismiss();
                showLoadingDialog();
                approvalPresenter.approvalHandleTurn(rowsBean.approval,myAlertInputDialog.getResult());
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertInputDialog.dismiss();
            }
        });
        myAlertInputDialog.show();
    }

    @Override
    public void Error(String msg) {
        dismiss();
        ToastUtil.show(this, msg);
    }

    @Override
    public void SuccessOnReport(Object object) {
        approvalState.setVisibility(View.GONE);
        dismiss();
        ToastUtil.show(this, "审批成功");
    }

}
