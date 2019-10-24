package cn.piesat.sanitation.ui.activity;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.dialog.myDialog.ActionSheetDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.model.contract.ChangeOrderStateContract;
import cn.piesat.sanitation.model.presenter.ChangeOrderStatePresenter;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 订单详情
 */
public class HeaderOrderDetailActivity extends BaseActivity implements ChangeOrderStateContract.HeaderchangeOrderStateView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_order_state)
    ImageView ivOrderState;
    @BindView(R.id.rl_order_state)
    RelativeLayout rlOrderState;
    @BindView(R.id.compress_station)
    TextView compressStation;
    @BindView(R.id.tv_car)
    TextView tvCar;
    @BindView(R.id.tv_driver)
    TextView tvDriver;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.burn_station)
    TextView burnStation;
    @BindView(R.id.send_time)
    TextView sendTime;
    @BindView(R.id.tv_maozhong)
    TextView tvMaozhong;
    @BindView(R.id.tv_pizhong)
    TextView tvPizhong;
    @BindView(R.id.tv_jingzhong)
    TextView tvJingzhong;
    @BindView(R.id.tv_yundanhao)
    TextView tvYundanhao;
    @BindView(R.id.bangdan_info)
    LinearLayout bangdanInfo;
    @BindView(R.id.btn_get)
    Button btnGet;
    @BindView(R.id.iv_paizhao)
    ImageView ivPaizhao;
    private OrderList.RowsBean rowsBean;

    private ChangeOrderStatePresenter changeOrderStatePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("订单详情");
        GetIntentValue();
        changeOrderStatePresenter = new ChangeOrderStatePresenter(this);
    }

    private void GetIntentValue() {
        Intent intent = getIntent();
        rowsBean = (OrderList.RowsBean) intent.getSerializableExtra(SysContant.CommentTag.comment_key);
        compressStation.setText(rowsBean.yszName);
        tvCar.setText(rowsBean.licensePlate);
        tvDriver.setText(rowsBean.name);
        tvStartTime.setText(rowsBean.jhqysjBiztyd);
        burnStation.setText(rowsBean.fscmc);
        sendTime.setText(rowsBean.jhddsjBiztyd);
        tvMaozhong.setText(rowsBean.mzBizgbd);
        tvPizhong.setText(rowsBean.pzBizgbd);
        tvJingzhong.setText(rowsBean.jzBizgbd);
        tvYundanhao.setText(rowsBean.ydhBiztyd);

        if (null != rowsBean) {
            switch (rowsBean.status) {
                case 0:  //取消
                    rlOrderState.setVisibility(View.GONE);
                    btnGet.setVisibility(View.GONE);
                    break;
                case 1:  //未接单
                    bangdanInfo.setVisibility(View.GONE);
                    rlOrderState.setVisibility(View.GONE);
                    btnGet.setText("取消订单");
                    break;
                case 2:  //已接单未起运
                    bangdanInfo.setVisibility(View.GONE);
                    rlOrderState.setVisibility(View.VISIBLE);
                    rlOrderState.setVisibility(View.GONE);
                    ivOrderState.setImageResource(R.mipmap.order_state_1);
                    btnGet.setText("取消订单");
                    break;
                case 3:  //已起运未过磅
                    btnGet.setVisibility(View.GONE);
                    break;
                case 4:  //已过磅未确认
                    bangdanInfo.setVisibility(View.VISIBLE);
                    rlOrderState.setVisibility(View.GONE);
                    btnGet.setVisibility(View.VISIBLE);
                    btnGet.setText("确认");
                    break;
                case 5:  //已完成
                    rlOrderState.setVisibility(View.GONE);
                    btnGet.setVisibility(View.GONE);
                    break;
            }
        }
    }


    @Override
    protected void initData() {

    }


    @OnClick({R.id.img_back, R.id.btn_get, R.id.iv_paizhao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_paizhao:
                showDialog();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_get:
                if (null != rowsBean) {
                    switch (rowsBean.status) {
                        case 0:  //取消
                            break;
                        case 1:  //未接单
                            showLoadingDialog("订单取消中", false);
                            changeOrderStatePresenter.CancelSendOrder(rowsBean.idBiztydsjgbd);
                            break;
                        case 2:  //已接单未起运
                            showLoadingDialog("订单取消中", false);
                            changeOrderStatePresenter.CancelSendOrder(rowsBean.idBiztydsjgbd);
                            btnGet.setText("取消订单");
                            break;
                        case 3:  //已起运未过磅
                        case 4:  //已过磅未确认
                            showLoadingDialog("订单确认中", false);
                            changeOrderStatePresenter.AffrimCountWeight(rowsBean.idBiztydsjgbd, TimeUtils.getCurrentTimeMMSS());
                            break;
                        case 5:  //已完成

                            break;
                    }
                }

                break;
        }
    }

    private void showDialog() {

    }

    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(this, errorMsg);
    }

    @Override
    public void SuccessFinshByAffrimCountWeight() {
        dismiss();
        btnGet.setVisibility(View.GONE);
    }

    @Override
    public void SuccessFinshByCancelSendOrder() {
        dismiss();
        btnGet.setVisibility(View.GONE);
        ToastUtil.show(this, "订单成功取消");
    }


}
