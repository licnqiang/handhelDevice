package cn.piesat.sanitation.ui.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hb.dialog.myDialog.ActionSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.model.contract.ChangeOrderStateContract;
import cn.piesat.sanitation.model.presenter.ChangeOrderStatePresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 订单详情
 */
public class DriverOrderDetailActivity extends BaseActivity implements ChangeOrderStateContract.changeOrderStateView {

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
    EditText tvMaozhong;
    @BindView(R.id.tv_pizhong)
    EditText tvPizhong;
    @BindView(R.id.tv_jingzhong)
    EditText tvJingzhong;
    @BindView(R.id.tv_yundanhao)
    TextView tvYundanhao;
    @BindView(R.id.order_bz)
    TextView orderBz;
    @BindView(R.id.bangdan_info)
    LinearLayout bangdanInfo;
    @BindView(R.id.btn_get)
    Button btnGet;
    @BindView(R.id.iv_paizhao)
    ImageView ivPaizhao;
    @BindView(R.id.ll_pic)
    LinearLayout ll_pic;
    @BindView(R.id.ll_bz)
    LinearLayout ll_bz;
    private OrderList.RowsBean rowsBean;

    private ChangeOrderStatePresenter changeOrderStatePresenter;
    private String picPath; //图片url

    @Override
    protected int getLayoutId() {
        return R.layout.activity_driver_order_detail;
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
        if(TextUtils.isEmpty(rowsBean.pdsmBiztyd)){
            ll_bz.setVisibility(View.GONE);
        }else {
            ll_bz.setVisibility(View.VISIBLE);

        }

        if (null != rowsBean) {
            switch (rowsBean.status) {
                case 0:  //取消
                    bangdanInfo.setVisibility(View.GONE);
                    rlOrderState.setVisibility(View.GONE);
                    btnGet.setVisibility(View.GONE);
                    break;
                case 1:  //未接单
                    bangdanInfo.setVisibility(View.GONE);
                    rlOrderState.setVisibility(View.GONE);
                    btnGet.setVisibility(View.VISIBLE);
                    btnGet.setText("接单");
                    break;
                case 2:  //已接单未起运
                    OrderState_2();
                    break;
                case 3:  //已起运未过磅
                    isEdit();
                    OrderState_3();
                    break;
                case 4:  //已过磅未确认
                    showBDInfo();
                    OrderState_4();
                    break;
                case 5:  //已完成
                    showBDInfo();
                    notEdit();
                    rlOrderState.setVisibility(View.GONE);
                    btnGet.setVisibility(View.GONE);
                    break;
            }
        }
    }

    /**
     * 显示过磅信息
     */
    private void showBDInfo(){
        //磅单信息
        tvMaozhong.setText(rowsBean.mzBizgbd);          //毛重
        tvPizhong.setText(rowsBean.pzBizgbd);           //皮重
        tvJingzhong.setText(rowsBean.jzBizgbd);         //净重
        //显示磅单图片
        if (TextUtils.isEmpty(rowsBean.bdtp)) {
            ll_pic.setVisibility(View.GONE);
        } else {
            ll_pic.setVisibility(View.VISIBLE);
            //显示图片
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.background_pass)
                    .error(R.mipmap.background_pass)
                    .fallback(R.mipmap.background_pass);
            Glide.with(DriverOrderDetailActivity.this)
                    .load(IPConfig.getOutSourceURLPreFix() +rowsBean.bdtp)
                    .apply(requestOptions)
                    .into(ivPaizhao);
        }
    }

    /**
     * 订单状态为 已接单未起运 的显示状态
     */
    private void OrderState_2(){
        bangdanInfo.setVisibility(View.GONE);
        rlOrderState.setVisibility(View.GONE);
        rlOrderState.setVisibility(View.VISIBLE);
        ivOrderState.setImageResource(R.mipmap.order_state_1);
        btnGet.setVisibility(View.VISIBLE);
        btnGet.setText("起运");
    }

    /**
     * 订单状态为 已起运未过磅 的显示状态
     */
    private void OrderState_3(){
        bangdanInfo.setVisibility(View.VISIBLE);
        rlOrderState.setVisibility(View.VISIBLE);
        ivOrderState.setImageResource(R.mipmap.order_state_2);
        btnGet.setText("确认到达");
    }

    /**
     * 订单状态为 已过磅未确认 的显示状态
     */
    private void OrderState_4(){
        notEdit();
        bangdanInfo.setVisibility(View.VISIBLE);
        rlOrderState.setVisibility(View.VISIBLE);
        ivOrderState.setImageResource(R.mipmap.order_state_3);
        btnGet.setVisibility(View.GONE);
    }


    /**
     * 界面可编辑
     */
    private void isEdit(){
        tvMaozhong.setFocusable(true);
        tvPizhong.setFocusable(true);
        tvJingzhong.setFocusable(true);
        ivPaizhao.setFocusable(true);
    }

    /**
     * 界面不可编辑，显示过磅单相关信息
     */
    private void notEdit() {
        tvMaozhong.setFocusable(false);
        tvPizhong.setFocusable(false);
        tvJingzhong.setFocusable(false);
        ivPaizhao.setFocusable(false);
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
                            showLoadingDialog("接单中", false);
                            changeOrderStatePresenter.GetOrder(rowsBean.idBiztydsjgbd, TimeUtils.getCurrentTimeMMSS());
                            break;
                        case 2:  //已接单未起运
                            showLoadingDialog("起运中", false);
                            changeOrderStatePresenter.StartTransport(rowsBean.idBiztydsjgbd, TimeUtils.getCurrentTimeMMSS());
                            break;
                        case 3:  //已起运未过磅

                            String stvMaozhong = tvMaozhong.getText().toString();
                            String stvPizhong = tvPizhong.getText().toString();
                            String stvJingzhong = tvJingzhong.getText().toString();

                            if (TextUtils.isEmpty(stvMaozhong) || TextUtils.isEmpty(stvPizhong) || TextUtils.isEmpty(stvJingzhong)) {
                                ToastUtil.show(DriverOrderDetailActivity.this, "请输入磅单信息，再次提交");
                            } else {
                                showLoadingDialog("订单确认中", false);
                                changeOrderStatePresenter.CountWeight(picPath, rowsBean.bdId, rowsBean.idBiztydsjgbd, "100", "100", "100");
                            }

                            break;
                        case 4:  //已过磅未确认

                            break;
                        case 5:  //已完成

                            break;
                    }
                }

                break;
        }
    }

    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openLocalImage(DriverOrderDetailActivity.this);
                    }
                }).addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openCameraImage(DriverOrderDetailActivity.this);
                    }
                });
        dialog.show();
    }


    private void uploadFile(String path) {
        List<String> paths = new ArrayList<>();
        paths.add(path);
        UpLoadFileControl.uploadFile(false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.upload, paths, null, new UpLoadFileControl.ResultCallBack() {
            @Override
            public void succeed(Object str) {
                picPath = str + "";
                dismiss();
                //显示图片
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.mipmap.paizhao)
                        .error(R.mipmap.paizhao)
                        .fallback(R.mipmap.paizhao);
                Glide.with(DriverOrderDetailActivity.this)
                        .load(IPConfig.getOutSourceURLPreFix() + picPath)
                        .apply(requestOptions)
                        .into(ivPaizhao);
            }

            @Override
            public void faild() {
                ToastUtil.show(DriverOrderDetailActivity.this, "图片上传失败，请重新上传");
                dismiss();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //选择相册之后的处理
            case PhotoTool.GET_IMAGE_FROM_PHONE:
                if (resultCode == RESULT_OK) {
                    String path = PhotoTool.getImageAbsolutePath(this, data.getData());
                    showLoadingDialog("上传图片", false);
                    uploadFile(path);
                }

                break;
            //选择照相机之后的处理
            case PhotoTool.GET_IMAGE_BY_CAMERA:
                if (resultCode == RESULT_OK) {
                    String path = PhotoTool.getImageAbsolutePath(this, PhotoTool.imageUriFromCamera);
                    showLoadingDialog("上传图片", false);
                    uploadFile(path);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(this, errorMsg);
    }

    @Override
    public void SuccessFinshByGetOrder() {
        dismiss();
        rowsBean.status = 2;  //修改订单状态为未起运
        OrderState_2();
    }

    @Override
    public void SuccessFinshByStartTransport() {
        dismiss();
        rowsBean.status = 3;  //修改订单状态为未起运
        OrderState_3();
    }

    @Override
    public void SuccessFinshByCountWeight() {
        dismiss();
        rowsBean.status = 4;  //修改订单状态为未起运
        OrderState_4();
    }

}
