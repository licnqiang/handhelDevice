package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hb.dialog.myDialog.MyAlertInputDialog;

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
import cn.piesat.sanitation.data.ApprovalStateBean;
import cn.piesat.sanitation.data.InsuranceBean;
import cn.piesat.sanitation.model.contract.ApprovalContract;
import cn.piesat.sanitation.model.presenter.ApprovalPresenter;
import cn.piesat.sanitation.ui.view.ApprovalDialog;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.RoleIdSortUtil;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 保险年检详情
 */
public class InsuranceDetailActivity extends BaseActivity implements ApprovalContract.ApprovalView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.car_num)
    CommentItemModul carNum;
    @BindView(R.id.insurance_type)
    CommentItemModul insuranceType;
    @BindView(R.id.etInsStartDate)
    CommentItemModul etInsStartDate;
    @BindView(R.id.etInsEndDate)
    CommentItemModul etInsEndDate;
    @BindView(R.id.etInsBuyDate)
    CommentItemModul etInsBuyDate;
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

    private InsuranceBean.InsuranceListBean rowsBean;

    @BindView(R.id.approval_state)
    LinearLayout approvalState;

    ApprovalPresenter approvalPresenter;

    ApprovalDialog approvalDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_insurance_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("年检详情");
    }

    @Override
    protected void initData() {
        GetIntentValue();
        approvalPresenter = new ApprovalPresenter(this);
        if (null == approvalDialog) {
            approvalDialog = new ApprovalDialog(this);
        }
    }

    private void GetIntentValue() {
        Intent intent = getIntent();
        rowsBean = (InsuranceBean.InsuranceListBean) intent.getSerializableExtra(SysContant.CommentTag.comment_key);
        if (null == rowsBean) {
            return;
        }
        showBaseInfo(rowsBean);
    }


    private void showBaseInfo(InsuranceBean.InsuranceListBean rowsBean) {
//        String roleTyep = SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID); //保存角色id

        /**
         * 判断信息是否审核中，或者是否司机角色
         * 若订单结束不显示审批按钮
         */
        if (null != rowsBean.appFlowInst) {

            if (rowsBean.approvalstatus.equals("01")&&BaseApplication.getUserInfo().userType!=4) {
                //01 -审核中
//                approvalState.setVisibility(roleTyep.equals(rowsBean.appFlowInst.roleId) ? View.VISIBLE : View.GONE);   //判断若需要当前用户审批时，显示审批按钮
                //如果角色数组里包含当前角色id，显示审批按钮
                approvalState.setVisibility(BaseApplication.getIns().getUserRoleIdList().contains(rowsBean.check_role) ? View.VISIBLE : View.GONE);
            }else {
                approvalState.setVisibility(View.GONE);
            }
        }

        carNum.setText(rowsBean.carNumber);

        insuranceType.setText(rowsBean.coverage);

        etInsStartDate.setText(rowsBean.oldInsuranceStarttime);

        etInsEndDate.setText(rowsBean.oldInsuranceEndtime);

        etInsBuyDate.setText(rowsBean.purchaseEndtime);

        reportPerson.setText(rowsBean.applicant);

        area.setText(rowsBean.administrativeArea);

        stationName.setText(rowsBean.siteName);

        orderBz.setText(rowsBean.remark);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loading)
                .fallback(R.mipmap.loading);

        Glide.with(InsuranceDetailActivity.this)
//                .load(IPConfig.getOutSourceURLPreFix() + rowsBean.maintainBillPhoto)
                .load(rowsBean.insuranceSign)
                .apply(requestOptions)
                .into(ivPaizhaoXianchang);
    }


    @Override
    public void Error(String msg) {
        dismiss();
        ToastUtil.show(this, msg);
    }

    @Override
    public void SuccessOnReport(Object object) {
        approvalPresenter.insuranceUpDate(object.toString(),rowsBean.id);
    }

    @Override
    public void ApprovalStateError(String msg) {
        dismiss();
        ToastUtil.show(this, "流程获取失败");
    }

    @Override
    public void ApprovalStateSuccess(List<ApprovalStateBean> approvalStates) {
        dismiss();
        approvalDialog.showTaskDialog(approvalStates,rowsBean.approvalstatus,null!=rowsBean.appFlowInst?rowsBean.appFlowInst.appContent:"");
    }

    @Override
    public void UpdateSuccess(Object object) {
        approvalState.setVisibility(View.GONE);
        dismiss();
        ToastUtil.show(this, "审批成功");
    }


    @OnClick({R.id.img_back, R.id.iv_paizhao_xianchang, R.id.btn_pass, R.id.btn_del, R.id.approval_state_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.iv_paizhao_xianchang:
                lookImageOrder();
                break;
            case R.id.btn_pass: //通过
                Map<String, String> map = new HashMap<>();
                map.put("userName", BaseApplication.getUserInfo().name);
                map.put("userType", rowsBean.check_role); //角色id
                map.put("roleName", rowsBean.check_name); //角色名
                map.put("userId", SpHelper.getStringValue(SysContant.userInfo.USER_ID)); //用户id
                map.put("apprContent", ""); //内容
                map.put("apprResult", "T"); //T 通过 F 未通过 R 驳回
                map.put("appFlowInstId", rowsBean.approval); //审批id
                showLoadingDialog();
                approvalPresenter.approvalHandlePass(map);
                break;
            case R.id.btn_del://驳回
                showDialog();
                break;
            //当前审批情况
            case R.id.approval_state_show:
                approvalPresenter.approvalStateById(rowsBean.approval);
                break;
        }
    }

    /**
     * 查看订单图片
     */
    private void lookImageOrder() {
        if (null != rowsBean.insuranceSign && !TextUtils.isEmpty(rowsBean.insuranceSign)) {
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("images", rowsBean.insuranceSign);//非必须
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


    private void showDialog() {
        final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(this).builder()
                .setTitle("请输入")
                .setEditText("");
        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertInputDialog.dismiss();


                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("userName", BaseApplication.getUserInfo().name);
                hashMap.put("userType", SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID)); //角色id
                hashMap.put("roleName", SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_NAME)); //角色名
                hashMap.put("userId", SpHelper.getStringValue(SysContant.userInfo.USER_ID)); //用户id
                hashMap.put("apprContent", myAlertInputDialog.getResult()); //内容
                hashMap.put("apprResult", "R"); //T 通过 F 未通过 R 驳回
                hashMap.put("appFlowInstId", rowsBean.approval); //审批id
                showLoadingDialog();
                approvalPresenter.approvalHandleTurn(hashMap);
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertInputDialog.dismiss();
            }
        });
        myAlertInputDialog.show();
    }
}
