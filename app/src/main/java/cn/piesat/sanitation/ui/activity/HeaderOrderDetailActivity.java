package cn.piesat.sanitation.ui.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.model.contract.ChangeOrderStateContract;
import cn.piesat.sanitation.model.presenter.ChangeOrderStatePresenter;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长端
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
    @BindView(R.id.order_bz)
    TextView orderBz;
    @BindView(R.id.ll_pic)
    LinearLayout ll_pic;
    @BindView(R.id.ll_bz)
    LinearLayout ll_bz;
    private OrderList.RowsBean rowsBean;
    private ChangeOrderStatePresenter changeOrderStatePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_header_order_detail;
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
        if (null == rowsBean) {
            return;
        }
        showBaseInfo();
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
                    ivOrderState.setImageResource(R.mipmap.order_state_1);
                    btnGet.setText("取消订单");
                    break;
                case 3:  //已起运未过磅
                    bangdanInfo.setVisibility(View.GONE);
                    btnGet.setVisibility(View.GONE);
                    rlOrderState.setVisibility(View.VISIBLE);
                    ivOrderState.setImageResource(R.mipmap.order_state_2);
                    break;
                case 4:  //已过磅未确认
                    showBDInfo();
                    bangdanInfo.setVisibility(View.VISIBLE);
                    rlOrderState.setVisibility(View.VISIBLE);
                    ivOrderState.setImageResource(R.mipmap.order_state_3);
                    btnGet.setVisibility(View.VISIBLE);
                    btnGet.setText("确认");
                    break;
                case 5:  //已完成
                    showBDInfo();
                    rlOrderState.setVisibility(View.GONE);
                    btnGet.setVisibility(View.GONE);
                    break;
            }
        }
    }


    @Override
    protected void initData() {
    }

    private void showBaseInfo() {
        //显示基础数据
        compressStation.setText(rowsBean.yszName);      //压缩站
        tvCar.setText(rowsBean.licensePlate);           //车牌号
        tvDriver.setText(rowsBean.name);                //司机名
        tvStartTime.setText(rowsBean.jhqysjBiztyd);     //起运时间
        burnStation.setText(rowsBean.fscmc);            //焚烧厂
        sendTime.setText(rowsBean.jhddsjBiztyd);        //送达时间
        tvYundanhao.setText(rowsBean.ydhBiztyd);        //运单号
        orderBz.setText(rowsBean.pdsmBiztyd);           //备注
        //当备注为空时 不显示该项
        if (TextUtils.isEmpty(rowsBean.pdsmBiztyd)) {
            ll_bz.setVisibility(View.GONE);
        } else {
            ll_bz.setVisibility(View.VISIBLE);

        }
    }


    /**
     * 显示过磅信息
     */
    private void showBDInfo() {
        //磅单信息
        tvMaozhong.setText(rowsBean.mzBizgbd);          //毛重
        tvPizhong.setText(rowsBean.pzBizgbd);           //皮重
        tvJingzhong.setText(rowsBean.jzBizgbd);         //净重
        //显示磅单图片
        Log.e("+++++++++图片地址++++","------------"+IPConfig.getOutSourceURLPreFix() + rowsBean.bdtp);
        if (TextUtils.isEmpty(rowsBean.bdtp)) {
            ll_pic.setVisibility(View.GONE);
        } else {
            ll_pic.setVisibility(View.VISIBLE);
            //显示图片
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.background_pass)
                    .error(R.mipmap.background_pass)
                    .fallback(R.mipmap.background_pass);
            Glide.with(HeaderOrderDetailActivity.this)
                    .load(IPConfig.getOutSourceURLPreFix() + rowsBean.bdtp)
                    .apply(requestOptions)
                    .into(ivPaizhao);
        }
    }


    @OnClick({R.id.img_back, R.id.btn_get, R.id.iv_paizhao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_paizhao:  //站长查看图片
                if (null != rowsBean.bdtp && !TextUtils.isEmpty(rowsBean.bdtp)) {
                    Intent intent = new Intent(this, ImageDetailActivity.class);
                    intent.putExtra("images", IPConfig.getOutSourceURLPreFix() + rowsBean.bdtp);//非必须
                    int[] location = new int[2];
                    ivPaizhao.getLocationOnScreen(location);
                    intent.putExtra("locationX", location[0]);//必须
                    intent.putExtra("locationY", location[1]);//必须
                    intent.putExtra("width", ivPaizhao.getWidth());//必须
                    intent.putExtra("height", ivPaizhao.getHeight());//必须
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }

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
