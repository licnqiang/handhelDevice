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
import cn.piesat.sanitation.data.BurnStationInfo;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.DriverInfo;
import cn.piesat.sanitation.model.contract.MaintainReportContract;
import cn.piesat.sanitation.model.presenter.MaintainReportPresenter;
import cn.piesat.sanitation.model.presenter.UpkeepReportPresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.ui.view.CommentItemInputModul;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长 新增维修上报
 * Created by sen.luo on 2020/1/2.
 */
public class AddMaintainReportActivity extends BaseActivity implements MaintainReportContract.getMaintainReportAddIView {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.car_num)
    CommentItemModul carNum;
    @BindView(R.id.deriver_person)
    CommentItemModul deriverPerson;
    @BindView(R.id.weixiudanwei)
    CommentItemInputModul weixiudanwei;
    @BindView(R.id.weixiujiage)
    CommentItemInputModul weixiujiage;
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
    @BindView(R.id.iv_paizhao_weixiu)
    ImageView ivPaizhaoWeixiu;
    @BindView(R.id.iv_paizhao_order)
    ImageView ivPaizhaoOrder;

    private CarInfo.RowsBean carRowsBean; //车辆
    private DriverInfo.RowsBean driverRowsBean;//司机
    private MaintainReportPresenter maintainReportPresenter;

    //现场照片
    public final int GET_IMAGE_BY_CAMERA = 6001;
    public final int GET_IMAGE_FROM_PHONE = 6002;

    //订单图片
    public final int Order_IMAGE_BY_CAMERA = 6003;
    public final int Order_IMAGE_FROM_PHONE = 6004;

    //维修图片
    public final int Maintain_IMAGE_BY_CAMERA = 6005;
    public final int Maintain_IMAGE_FROM_PHONE = 6006;

    private String picPath_xianchang; //现场图片url
    private String picPath_order; //订单图片url
    private String picPath_maintain; //维修图片url

    @Override
    protected int getLayoutId() {
        return R.layout.activity_maintain_report_add;
    }

    @Override
    protected void initView() {
        tv_title.setText("维修上报");

    }

    @Override
    protected void initData() {
        reportPerson.setText(BaseApplication.getUserInfo().name);
        area.setText(BaseApplication.getUserInfo().areaCount);
        stationName.setText(BaseApplication.getUserInfo().deptNameCount);
        maintainReportPresenter = new MaintainReportPresenter(this);

    }

    @OnClick({R.id.img_back, R.id.car_num, R.id.deriver_person, R.id.iv_paizhao_xianchang, R.id.iv_paizhao_weixiu, R.id.iv_paizhao_order, R.id.btn_submit_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            //车牌
            case R.id.car_num:
                Intent intentcar = new Intent();
                intentcar.setClass(this, ItemSelectActivity.class);
                Bundle bundlecar = new Bundle();
                bundlecar.putString(SysContant.QueryType.query_type, SysContant.QueryType.car_key);
                intentcar.putExtras(bundlecar);
                startActivityForResult(intentcar, 0);
                break;
            //驾驶员
            case R.id.deriver_person:
                Intent intentDriver = new Intent();
                intentDriver.setClass(this, ItemSelectActivity.class);
                Bundle bundleDriver = new Bundle();
                bundleDriver.putString(SysContant.QueryType.query_type, SysContant.QueryType.driver_key);
                intentDriver.putExtras(bundleDriver);
                startActivityForResult(intentDriver, 0);
                break;
            case R.id.iv_paizhao_xianchang:
                showDialogAction(1);
                break;
            case R.id.iv_paizhao_weixiu:
                showDialogAction(2);
                break;
            case R.id.iv_paizhao_order:
                showDialogAction(3);
                break;
            case R.id.btn_submit_order:
                getApprovalId();
                break;
        }
    }


    /**
     * 获取审批流程
     */
    private void getApprovalId() {
        String station = stationName.getText().toString().trim();
        String s_carNumber = carNum.getText().toString().trim();
        String s_reportPerson = reportPerson.getText().toString().trim();
        String s_weixiudanwei = weixiudanwei.getText().toString().trim();
        String s_deriverPerson = deriverPerson.getText().toString().trim();
        String s_weixiujiage = weixiujiage.getText().toString().trim();
        String s_area = area.getText().toString().trim();
        String s_orderBz = orderBz.getText().toString().trim();

        if (TextUtils.isEmpty(station) || TextUtils.isEmpty(s_carNumber) || TextUtils.isEmpty(s_reportPerson) ||
                TextUtils.isEmpty(s_weixiudanwei) || TextUtils.isEmpty(s_deriverPerson)|| TextUtils.isEmpty(s_weixiujiage) || TextUtils.isEmpty(s_area) ||
                TextUtils.isEmpty(s_orderBz) || TextUtils.isEmpty(picPath_xianchang) || TextUtils.isEmpty(picPath_order)|| TextUtils.isEmpty(picPath_maintain)) {
            ToastUtil.show(AddMaintainReportActivity.this, "请补全所有信息，再次提交");
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("userName", BaseApplication.getUserInfo().name);//行政区划
        map.put("userType", BaseApplication.getUserInfo().userType + "");//站点名称
        map.put("userId", BaseApplication.getUserInfo().id);//用户id
        map.put("roleName", "站长");//车牌号
        map.put("flowCode", "1001");//站长上报审批 固定1001
        map.put("money", s_weixiujiage);//审批金额
        maintainReportPresenter.getApporveIdReport(map);
    }


    /**
     * 1为现场照片
     * 2为维修照片
     * 3为订单照片
     */
    public void showDialogAction(int type) {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        if(1==type){
                            PhotoTool.openLocalImage(AddMaintainReportActivity.this,GET_IMAGE_FROM_PHONE);
                        }else if(2==type){
                            PhotoTool.openLocalImage(AddMaintainReportActivity.this,Maintain_IMAGE_FROM_PHONE);
                        }else {
                            PhotoTool.openLocalImage(AddMaintainReportActivity.this,Order_IMAGE_FROM_PHONE);
                        }
                    }
                }).addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        if(1==type){
                            PhotoTool.openLocalImage(AddMaintainReportActivity.this,GET_IMAGE_BY_CAMERA);
                        }else if(2==type){
                            PhotoTool.openLocalImage(AddMaintainReportActivity.this,Maintain_IMAGE_BY_CAMERA);
                        }else {
                            PhotoTool.openLocalImage(AddMaintainReportActivity.this,Order_IMAGE_BY_CAMERA);
                        }
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
            case Maintain_IMAGE_FROM_PHONE:
                if (resultCode == RESULT_OK) {
                    String path = PhotoTool.getImageAbsolutePath(this, data.getData());
                    showLoadingDialog("上传图片", false);
                    uploadFile(path, 3);
                }

                break;
            //选择照相机之后的处理
            case Maintain_IMAGE_BY_CAMERA:
                if (resultCode == RESULT_OK) {
                    String path = PhotoTool.getImageAbsolutePath(this, PhotoTool.imageUriFromCamera);
                    showLoadingDialog("上传图片", false);
                    uploadFile(path, 3);
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
            case SysContant.QueryType.driver_code:  //选择司机
                Bundle bundledriver = data.getExtras();
                driverRowsBean = (DriverInfo.RowsBean) bundledriver.getSerializable(SysContant.QueryType.query_type);
                deriverPerson.setText(driverRowsBean.name);
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
                if (1 == type) {
                    picPath_xianchang = str + ""; //现场图片url
                    Glide.with(AddMaintainReportActivity.this)
                            .load(IPConfig.getOutSourceURLPreFix() + str)
                            .into(ivPaizhaoXianchang);
                } else if(2==type){
                    picPath_order = str + ""; //订单图片url
                    Glide.with(AddMaintainReportActivity.this)
                            .load(IPConfig.getOutSourceURLPreFix() + str)
                            .into(ivPaizhaoOrder);
                }else {
                    picPath_maintain= str + ""; //维修图片url
                    Glide.with(AddMaintainReportActivity.this)
                            .load(IPConfig.getOutSourceURLPreFix() + str)
                            .into(ivPaizhaoWeixiu);
                }

            }

            @Override
            public void faild() {
                ToastUtil.show(AddMaintainReportActivity.this, "图片上传失败，请重新上传");
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
        String s_weixiudanwei = weixiudanwei.getText().toString().trim();
        String s_deriverPerson = deriverPerson.getText().toString().trim();
        String s_weixiujiage = weixiujiage.getText().toString().trim();
        String s_area = area.getText().toString().trim();
        String s_orderBz = orderBz.getText().toString().trim();

        if (TextUtils.isEmpty(station) || TextUtils.isEmpty(s_carNumber) || TextUtils.isEmpty(s_reportPerson) ||
                TextUtils.isEmpty(s_weixiudanwei) || TextUtils.isEmpty(s_deriverPerson)|| TextUtils.isEmpty(s_weixiujiage) || TextUtils.isEmpty(s_area) ||
                TextUtils.isEmpty(s_orderBz) || TextUtils.isEmpty(picPath_xianchang) || TextUtils.isEmpty(picPath_order)|| TextUtils.isEmpty(picPath_maintain)) {
            ToastUtil.show(AddMaintainReportActivity.this, "请补全所有信息，再次提交");
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
        addMap.put("maintenanceUnit", s_weixiudanwei);//维修单位
        addMap.put("maintenancePrice", s_weixiujiage);//维修价格
        addMap.put("driver", s_deriverPerson);//车辆司机
        addMap.put("userId", BaseApplication.getIns().getUserId());//加油升数
        addMap.put("roleId", SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID));//加油升数
        if (!s_orderBz.isEmpty()) {
            addMap.put("maintenanceReason", s_orderBz);
            addMap.put("remark", s_orderBz);
        }

        addMap.put("scenePhoto", IPConfig.getOutSourceURLPreFix() +picPath_xianchang);//现场照片
        addMap.put("maintainPhoto", IPConfig.getOutSourceURLPreFix() +picPath_maintain);//维修照片
        addMap.put("maintainBillPhoto", picPath_order);//维修单照片
        maintainReportPresenter.getMaintainReportAdd(addMap);
    }
}
