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
import butterknife.ButterKnife;
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
import cn.piesat.sanitation.model.contract.UpKeepReportContract;
import cn.piesat.sanitation.model.presenter.GasonLineReportPresenter;
import cn.piesat.sanitation.model.presenter.UpkeepReportPresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.ui.view.CommentItemInputModul;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 保养上报
 * Created by sen.luo on 2020/1/2.
 */
public class AddUpKeepReportActivity extends BaseActivity implements UpKeepReportContract.getUpkeepReportAddIView {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.car_num)
    CommentItemModul carNum;
    @BindView(R.id.maintain_company)
    CommentItemInputModul maintainCompany;
    @BindView(R.id.maintain_price)
    CommentItemInputModul maintainPrice;
    @BindView(R.id.report_person)
    CommentItemModul reportPerson;
    @BindView(R.id.area)
    CommentItemModul area;
    @BindView(R.id.station_name)
    CommentItemModul stationName;
    @BindView(R.id.order_bz)
    EditText orderBz;
    @BindView(R.id.iv_paizhao_xianchang)
    ImageView ivPaizhaoXianchang;
    @BindView(R.id.iv_paizhao_order)
    ImageView ivPaizhaoOrder;


    private CarInfo.RowsBean carRowsBean; //车辆
    private UpkeepReportPresenter upkeepReportPresenter;

    //现场照片
    public final int GET_IMAGE_BY_CAMERA = 6001;
    public final int GET_IMAGE_FROM_PHONE = 6002;

    //订单图片
    public final int Order_IMAGE_BY_CAMERA = 6003;
    public final int Order_IMAGE_FROM_PHONE = 6004;

    private String picPath_xianchang; //现场图片url
    private String picPath_order; //订单图片url


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
        reportPerson.setText(BaseApplication.getUserInfo().name);
        area.setText(BaseApplication.getUserInfo().areaCount);
        stationName.setText(BaseApplication.getUserInfo().deptNameCount);
        upkeepReportPresenter = new UpkeepReportPresenter(this);
    }


    @OnClick({R.id.img_back, R.id.car_num, R.id.iv_paizhao_xianchang, R.id.iv_paizhao_order, R.id.btn_submit_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.car_num:
                Intent intentcar = new Intent();
                intentcar.setClass(this, ItemSelectActivity.class);
                Bundle bundlecar = new Bundle();
                bundlecar.putString(SysContant.QueryType.query_type, SysContant.QueryType.car_key);
                intentcar.putExtras(bundlecar);
                startActivityForResult(intentcar, 0);
                break;
            case R.id.iv_paizhao_xianchang:
                showDialogAction(1);
                break;
            case R.id.iv_paizhao_order:
                showDialogAction(2);
                break;
            case R.id.btn_submit_order:
                getApprovalId();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //选择相册之后的处理
            case GET_IMAGE_FROM_PHONE:
                if (resultCode == RESULT_OK) {
                    String path = PhotoTool.getImageAbsolutePath(this, data.getData());
                    showLoadingDialog("上传图片", false);
                    uploadFile(path, 1);
                }

                break;
            //选择照相机之后的处理
            case GET_IMAGE_BY_CAMERA:
                if (resultCode == RESULT_OK) {
                    String path = PhotoTool.getImageAbsolutePath(this, PhotoTool.imageUriFromCamera);
                    showLoadingDialog("上传图片", false);
                    uploadFile(path, 1);
                }

                //选择相册之后的处理
            case Order_IMAGE_FROM_PHONE:
                if (resultCode == RESULT_OK) {
                    String path = PhotoTool.getImageAbsolutePath(this, data.getData());
                    showLoadingDialog("上传图片", false);
                    uploadFile(path, 2);
                }

                break;
            //选择照相机之后的处理
            case Order_IMAGE_BY_CAMERA:
                if (resultCode == RESULT_OK) {
                    String path = PhotoTool.getImageAbsolutePath(this, PhotoTool.imageUriFromCamera);
                    showLoadingDialog("上传图片", false);
                    uploadFile(path, 2);
                }
        }
        switch (resultCode) {
            case SysContant.QueryType.car_code:  //选择车辆
                Bundle bundlecar = data.getExtras();
                carRowsBean = (CarInfo.RowsBean) bundlecar.getSerializable(SysContant.QueryType.query_type);
                carNum.setText(carRowsBean.licensePlate);
                break;
        }

    }


    private void uploadFile(String path, int type) {
        List<String> paths = new ArrayList<>();
        paths.add(path);
        UpLoadFileControl.uploadFile(false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.upload, paths, null, new UpLoadFileControl.ResultCallBack() {
            @Override
            public void succeed(Object str) {
                if (1 == type) {
                    picPath_xianchang = str + ""; //现场图片url

                } else {
                    picPath_order = str + ""; //订单图片url
                }
                dismiss();
                //显示图片
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.mipmap.paizhao)
                        .error(R.mipmap.paizhao)
                        .fallback(R.mipmap.paizhao);
                Glide.with(AddUpKeepReportActivity.this)
                        .load(IPConfig.getOutSourceURLPreFix() + str)
                        .apply(requestOptions)
                        .into(type == 1 ? ivPaizhaoXianchang : ivPaizhaoOrder);
            }

            @Override
            public void faild() {
                ToastUtil.show(AddUpKeepReportActivity.this, "图片上传失败，请重新上传");
                dismiss();
            }
        });

    }

    /**
     * 获取审批流程
     */
    private void getApprovalId() {
        Map<String, String> map = new HashMap<>();
        map.put("userName", BaseApplication.getUserInfo().name);//行政区划
        map.put("userType", BaseApplication.getUserInfo().userType + "");//站点名称
        map.put("roleName", "站长");//车牌号
        map.put("flowCode", "1001");//站长上报审批 固定1001
        upkeepReportPresenter.getApporveIdReport(map);
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

        if (TextUtils.isEmpty(station) || TextUtils.isEmpty(s_carNumber) || TextUtils.isEmpty(s_reportPerson) ||
                TextUtils.isEmpty(s_maintainCompany) || TextUtils.isEmpty(s_maintainPrice) || TextUtils.isEmpty(s_area) ||
                TextUtils.isEmpty(s_orderBz) || TextUtils.isEmpty(picPath_xianchang) || TextUtils.isEmpty(picPath_order)) {
            ToastUtil.show(AddUpKeepReportActivity.this, "请补全所有信息，再次提交");
            return;
        }
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
        upkeepReportPresenter.getUpkeepReportAdd(addMap);
    }


    /**
     * 1为现场照片
     * 2为订单照片
     */
    public void showDialogAction(int type) {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openLocalImage(AddUpKeepReportActivity.this, type == 1 ? GET_IMAGE_FROM_PHONE : Order_IMAGE_FROM_PHONE);
                    }
                }).addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openCameraImage(AddUpKeepReportActivity.this, type == 1 ? GET_IMAGE_BY_CAMERA : Order_IMAGE_BY_CAMERA);
                    }
                });
        dialog.show();
    }


    @Override
    public void Error(String msg) {
        dismiss();
        ToastUtil.show(this, msg);
    }

    @Override
    public void SuccessOnReportAdd(Object o) {
        dismiss();
        ToastUtil.show(this, "提交成功");
        finish();
    }

    @Override
    public void ApproveError(String msg) {
        dismiss();
        ToastUtil.show(this, msg);
    }

    @Override
    public void ApproveSuccess(Object o) {
        getReport(o.toString());
    }
}
