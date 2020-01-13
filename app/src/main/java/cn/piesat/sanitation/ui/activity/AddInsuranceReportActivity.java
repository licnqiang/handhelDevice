package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.hb.dialog.myDialog.ActionSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import cn.piesat.sanitation.data.InsuranceBean;
import cn.piesat.sanitation.model.contract.InsuranceContract;
import cn.piesat.sanitation.model.presenter.InsurancePresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.ui.view.CommentItemInputModul;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 新增保险年检
 */
public class AddInsuranceReportActivity extends BaseActivity implements InsuranceContract.GetInsuranceAddIView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.car_num)
    CommentItemModul carNum;
    @BindView(R.id.insurance_type)
    CommentItemInputModul insuranceType;
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
    EditText orderBz;
    @BindView(R.id.iv_paizhao_xianchang)
    ImageView ivPaizhaoXianchang;

    InsurancePresenter insurancePresenter;
    private CarInfo.RowsBean carRowsBean;

    private String picPath = ""; //图片url

    @Override
    protected int getLayoutId() {
        return R.layout.activity_insurance_add;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        reportPerson.setText(BaseApplication.getUserInfo().name);
        area.setText(BaseApplication.getUserInfo().areaCount);
        stationName.setText(BaseApplication.getUserInfo().deptNameCount);
        insurancePresenter = new InsurancePresenter(this);
    }


    @OnClick({R.id.img_back, R.id.car_num, R.id.etInsStartDate, R.id.etInsEndDate, R.id.etInsBuyDate, R.id.iv_paizhao_xianchang, R.id.btReport})
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
            case R.id.etInsStartDate:
                seleTimePicker(etInsStartDate);
                break;
            case R.id.etInsEndDate:
                seleTimePicker(etInsEndDate);
                break;
            case R.id.etInsBuyDate:
                seleTimePicker(etInsBuyDate);
                break;
            case R.id.iv_paizhao_xianchang:
                showDialog();
                break;
            case R.id.btReport:
                getApprovalId();
                break;
        }
    }

    /**
     * 获取审批流程
     */
    private void getApprovalId() {
        Map<String, String> map = new HashMap<>();
        map.put("userName", BaseApplication.getUserInfo().name);//行政区划
        map.put("userType", BaseApplication.getUserInfo().userType + "");//站点名称
        map.put("userId", BaseApplication.getUserInfo().id);//用户id
        map.put("roleName", "站长");//车牌号
        map.put("flowCode", "1001");//站长上报审批 固定1001
        map.put("money", "0");//审批金额
        showLoadingDialog();
        insurancePresenter.getReportProcess(map);

    }


    private void seleTimePicker(CommentItemModul textView) {
        TimePickerView pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//      Calendar calendar = Calendar.getInstance();
//      pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setTitle("选择时间");
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                textView.setText(TimeUtils.getStringByFormat(date, "yyyy-MM-dd"));
            }
        });
        pvTime.show();
    }


    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openLocalImage(AddInsuranceReportActivity.this);
                    }
                }).addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openCameraImage(AddInsuranceReportActivity.this);
                    }
                });
        dialog.show();
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

        switch (resultCode) {
            case SysContant.QueryType.car_code:  //选择车辆
                Bundle bundlecar = data.getExtras();
                carRowsBean = (CarInfo.RowsBean) bundlecar.getSerializable(SysContant.QueryType.query_type);
                carNum.setText(carRowsBean.licensePlate);
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
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

                Glide.with(AddInsuranceReportActivity.this).load(Uri.fromFile(new File(path))).into(ivPaizhaoXianchang);
            }

            @Override
            public void faild() {
                ToastUtil.show(AddInsuranceReportActivity.this, "图片上传失败，请重新上传");
                dismiss();
            }
        });

    }


    @Override
    public void insuranceError(String msg) {
        ToastUtil.show(this, msg);
        dismiss();
    }

    @Override
    public void getInsuranceDetailSuccess(InsuranceBean.InsuranceListBean bean) {
    }

    @Override
    public void getAddInsuranceSuccess(String msg) {
        ToastUtil.show(this, msg);
        dismiss();
        finish();
    }

    @Override
    public void getReportProcessId(Object o) {
        if (o != null) {
            ToastUtil.show(this, "流程创建成功，正在上报");
            getReport(o.toString());
        } else {
            ToastUtil.show(this, "获取流程出错");
        }
    }


    /**
     * 信息上报
     *
     * @param processId 流程id
     */
    private void getReport(String processId) {

        String station = stationName.getText().toString().trim();
        String carNumber = carNum.getText().toString().trim();
        String e_reportPerson = reportPerson.getText().toString().trim();
        String insurance = insuranceType.getText().toString().trim();
        String insStartDate = etInsStartDate.getText().toString().trim();
        String insEndDate = etInsEndDate.getText().toString().trim();
        String insBuyDate = etInsBuyDate.getText().toString().trim();
        String violateDistrict = orderBz.getText().toString().trim();
        String e_area = area.getText().toString().trim();

        if (station.isEmpty()) {
            ToastUtil.show(this, "站名不得为空");

            return;
        }
        if (carNumber.isEmpty()) {
            ToastUtil.show(this, "车牌号不得为空");
            return;
        }
        if (e_reportPerson.isEmpty()) {
            ToastUtil.show(this, "申请人不得为空");
            return;
        }
        if (insurance.isEmpty()) {
            ToastUtil.show(this, "险种不得为空");
            return;
        }
        if (insStartDate.isEmpty()) {
            ToastUtil.show(this, "旧保险起始时间不得为空");
            return;
        }
        if (insEndDate.isEmpty()) {
            ToastUtil.show(this, "旧保险结束时间不得为空");
            return;
        }

        if (insBuyDate.isEmpty()) {
            ToastUtil.show(this, "采购结束时间不得为空");
            return;
        }


        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("userId", BaseApplication.getIns().getUserId());
        map.put("roleId", String.valueOf(BaseApplication.getIns().getUserRoleId()));
        map.put("siteName", BaseApplication.getIns().getSiteName());
        map.put("administrativeArea", violateDistrict);//行政区划
        map.put("applicant", e_reportPerson);//申请人
        map.put("coverage", insurance);//险种
        map.put("carNumber", carNumber);//车牌号
        map.put("oldInsuranceStarttime", insStartDate);//旧保险起始时间
        map.put("oldInsuranceEndtime", insEndDate);//旧保险结束时间
        map.put("purchaseEndtime", insBuyDate);//采购结束时间
        map.put("approval", processId);//流程id
        map.put("remark", violateDistrict);
        if (!picPath.isEmpty()) {
            map.put("insuranceSign", IPConfig.getOutSourceURLPreFix()+picPath);
        }
        insurancePresenter.getInsuranceAdd(map);

    }

}
