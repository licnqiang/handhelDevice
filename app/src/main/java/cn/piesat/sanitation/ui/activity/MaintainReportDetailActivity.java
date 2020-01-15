package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hb.dialog.myDialog.MyAlertInputDialog;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.ApprovalStateBean;
import cn.piesat.sanitation.data.MaintainList;
import cn.piesat.sanitation.model.contract.ApprovalContract;
import cn.piesat.sanitation.model.presenter.ApprovalPresenter;
import cn.piesat.sanitation.ui.view.ApprovalDialog;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长 新增维修上报
 * Created by sen.luo on 2020/1/2.
 */
public class MaintainReportDetailActivity extends BaseActivity implements ApprovalContract.ApprovalView{

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

    @BindView(R.id.approval_state)
    LinearLayout approvalState;

    ApprovalPresenter approvalPresenter;

    ApprovalDialog approvalDialog;

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
        approvalPresenter = new ApprovalPresenter(this);
        if(null==approvalDialog){
            approvalDialog=new ApprovalDialog(this);
        }

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
        String roleTyep = SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID); //保存角色id

        /**
         * 判断信息是否审核中，
         * 若订单结束不显示审批按钮
         */
        if (null != rowsBean.appFlowInst) {
            if (rowsBean.approvalstatus.equals("01")) {             //01 -审核中
                approvalState.setVisibility(roleTyep.equals(rowsBean.appFlowInst.roleId) ? View.VISIBLE : View.GONE);   //判断若需要当前用户审批时，显示审批按钮
            }else {
                approvalState.setVisibility(View.GONE);
            }
        }
        carNum.setText(rowsBean.carNumber);
        deriverPerson.setText(rowsBean.driver);
        weixiudanwei.setText(rowsBean.maintenanceUnit);
        weixiujiage.setText(rowsBean.maintenancePrice + "");
        reportPerson.setText(rowsBean.applicant);
        area.setText(rowsBean.administrativeArea);
        stationName.setText(rowsBean.siteName);
        orderBz.setText(rowsBean.maintenanceReason);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loading)
                .fallback(R.mipmap.loading);

        Glide.with(MaintainReportDetailActivity.this)
//                .load(IPConfig.getOutSourceURLPreFix() + rowsBean.scenePhoto)
                .load(rowsBean.scenePhoto)
                .apply(requestOptions)
                .into(ivPaizhaoXianchang);

        Glide.with(MaintainReportDetailActivity.this)
//                .load(IPConfig.getOutSourceURLPreFix() + rowsBean.maintainPhoto)
                .load(rowsBean.maintainPhoto)
                .apply(requestOptions)
                .into(ivPaizhaoWeixiu);
        Glide.with(MaintainReportDetailActivity.this)
//                .load(IPConfig.getOutSourceURLPreFix() + rowsBean.maintainBillPhoto)
                .load(rowsBean.maintainBillPhoto)
                .apply(requestOptions)
                .into(ivPaizhaoOrder);
    }


    @OnClick({R.id.img_back, R.id.iv_paizhao_xianchang, R.id.iv_paizhao_weixiu, R.id.iv_paizhao_order, R.id.btn_pass, R.id.btn_del, R.id.approval_state_show})
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
            //审批通过
            case R.id.btn_pass:
                approvalPresenter.approvalHandlePass(rowsBean.approval);
                break;
            //审批驳回
            case R.id.btn_del:
                showDialog();
                break;
            //当前审批情况
            case R.id.approval_state_show:
                approvalPresenter.approvalStateById(rowsBean.approval);
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

    private void showDialog() {
        final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(this).builder()
                .setTitle("请输入")
                .setEditText("");
        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertInputDialog.dismiss();
                approvalPresenter.approvalHandleTurn(rowsBean.approval, myAlertInputDialog.getResult());
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
        approvalPresenter.maintainUpDate(object.toString(),rowsBean.id);
    }

    @Override
    public void ApprovalStateError(String msg) {
        dismiss();
        ToastUtil.show(this, "流程获取成功");
    }

    @Override
    public void ApprovalStateSuccess(List<ApprovalStateBean> approvalStates) {
        dismiss();
        if(null!=rowsBean){
            approvalDialog.showTaskDialog(approvalStates,rowsBean.approvalstatus,null!=rowsBean.appFlowInst?rowsBean.appFlowInst.appContent:"");
        }
    }

    @Override
    public void UpdateSuccess(Object object) {
        approvalState.setVisibility(View.GONE);
        dismiss();
        ToastUtil.show(this, "审批成功");
    }
}
