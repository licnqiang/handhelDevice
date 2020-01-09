package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hb.dialog.myDialog.ActionSheetDialog;

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
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.DriverInfo;
import cn.piesat.sanitation.model.contract.InsuranceContract;
import cn.piesat.sanitation.model.contract.UpKeepReportContract;
import cn.piesat.sanitation.model.presenter.InsurancePresenter;
import cn.piesat.sanitation.model.presenter.UpkeepReportPresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.util.LogUtil;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 新增保险年检
 */
public class AddInsuranceActivity extends BaseActivity implements  InsuranceContract.GetInsuranceAddIView {

    @BindView(R.id.etStation)
    EditText etStation;
    @BindView(R.id.etCarNumber)
    EditText etCarNumber;
    @BindView(R.id.etReportPerson)
    EditText etReportPerson;
    @BindView(R.id.etInsurance)
    EditText etInsurance;
    @BindView(R.id.etInsStartDate)
    EditText etInsStartDate;
    @BindView(R.id.etInsEndDate)
    EditText etInsEndDate;
    @BindView(R.id.etInsBuyDate)
    EditText etInsBuyDate;

    @BindView(R.id.etViolateDistrict)
    EditText etDistrict;
    @BindView(R.id.etRemark)
    EditText etRemark;
    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btReport)
    Button btReport;



    private CarInfo.RowsBean carRowsBean; //车辆
    private InsurancePresenter insurancePresenter;
    private String  reportId="";//请求详情Id
    private boolean isEdit=false;//是否可编辑 用于详情页展示
    private String picPath; //图片url
    private String detailPhoto="";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_insurance;
    }

    @Override
    protected void initView() {
        isEdit=getIntent().getBooleanExtra("isEdit",false);
        reportId=getIntent().getStringExtra("report_id");
        tv_title.setText(isEdit?"新增保险年检申请":"保险年奖详情");
        btReport.setVisibility(isEdit? View.VISIBLE:View.GONE);

        insurancePresenter =new InsurancePresenter(this);

        if (BaseApplication.getUserInfo()!=null){
            etReportPerson.setText(BaseApplication.getUserInfo().name);
            etStation.setText(BaseApplication.getUserInfo().deptNameCount);//站点名称
            etDistrict.setText(BaseApplication.getUserInfo().areaCount);
        }

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.img_back, R.id.imgPhoto,R.id.btReport,R.id.etStation,R.id.etCarNumber,R.id.etInsStartDate,R.id.etInsEndDate,R.id.etInsBuyDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.imgPhoto:
                if (!isEdit){
                    lookImage(detailPhoto);
                }else {
                    showDialog();
                }
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

            case R.id.btReport:
                getApprovalId();
                break;

            //选择车牌
            case R.id.etCarNumber:
                if (!isEdit){
                    return;
                }
                Intent intentcar = new Intent();
                intentcar.setClass(this, ItemSelectActivity.class);
                Bundle bundlecar = new Bundle();
                bundlecar.putString(SysContant.QueryType.query_type, SysContant.QueryType.car_key);
                intentcar.putExtras(bundlecar);
                startActivityForResult(intentcar, 0);
                break;
        }
    }

    /**
     * 获取审批流程
     */
    private void getApprovalId() {
        Map<String, String> map = new HashMap<>();
        map.put("userId",BaseApplication.getIns().getUserId());
        map.put("userName", BaseApplication.getUserInfo().name);//行政区划
        map.put("userType", BaseApplication.getUserInfo().userType + "");//站点名称
        map.put("roleName", "站长");//车牌号
        map.put("flowCode", "1001");//站长上报审批 固定1001
        showLoadingDialog();
        insurancePresenter.getReportProcess(map);

    }

    @Override
    public void insuranceError(String msg) {
        ToastUtil.show(this,msg);
        dismiss();
    }

    @Override
    public void getInsuranceDetailSuccess(Object o) {
        dismiss();
    }

    @Override
    public void getAddInsuranceSuccess(String msg) {
        ToastUtil.show(this,msg);
        dismiss();
        setResult(0);
        finish();
    }

    /**
     * 获取流程id后再发送申请
     * @param o
     */
    @Override
    public void getReportProcessId(Object o) {
        if (o!=null){
            ToastUtil.show(this,"流程创建成功，正在上报");
            getReport(o.toString());
        }else {
            ToastUtil.show(this,"获取流程出错");
        }
    }

    /**
     * 信息上报
     * @param processId 流程id
     */
    private void getReport(String processId) {
        String station =etStation.getText().toString().trim();
        String carNumber =etCarNumber.getText().toString().trim();
        String reportPerson =etReportPerson.getText().toString().trim();
        String insurance =etInsurance.getText().toString().trim();
        String insStartDate=etInsStartDate.getText().toString().trim();
        String insEndDate =etInsEndDate.getText().toString().trim();
        String insBuyDate =etInsBuyDate.getText().toString().trim();
        String violateDistrict =etDistrict.getText().toString().trim();

        if (station.isEmpty()){
            ToastUtil.show(this,"站名不得为空");

            return;
        }
        if (carNumber.isEmpty()){
            ToastUtil.show(this,"车牌号不得为空");
            return;
        }
        if (reportPerson.isEmpty()){
            ToastUtil.show(this,"申请人不得为空");
            return;
        }
        if (insurance.isEmpty()){
            ToastUtil.show(this,"险种不得为空");
            return;
        }
        if (insStartDate.isEmpty()){
            ToastUtil.show(this,"旧保险起始时间不得为空");
            return;
        }
        if (insEndDate.isEmpty()){
            ToastUtil.show(this,"旧保险结束时间不得为空");
            return;
        }

        if (insBuyDate.isEmpty()){
            ToastUtil.show(this,"采购结束时间不得为空");
            return;
        }



        showLoadingDialog();
        Map<String,String> map =new HashMap<>();
        map.put("userId",BaseApplication.getIns().getUserId());
        map.put("roleId", String.valueOf(BaseApplication.getUserInfo().userType));
        map.put("administrativeArea",violateDistrict);//行政区划
        map.put("applicant",reportPerson);//申请人
        map.put("coverage",insurance);//险种
        map.put("carNumber",carNumber);//车牌号
        map.put("oldInsuranceStarttime",insStartDate);//旧保险起始时间
        map.put("oldInsuranceEndtime",insEndDate);//旧保险结束时间
        map.put("purchaseEndtime",insBuyDate);//采购结束时间
        map.put("approval",processId);//流程id
        if (!etRemark.getText().toString().isEmpty()){
            map.put("remark",etRemark.getText().toString());
        }
        if (!picPath.isEmpty()){
            map.put("insuranceSign",picPath);
        }
        insurancePresenter.getInsuranceAdd(map);

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
                etCarNumber.setText(carRowsBean.licensePlate);
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
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.mipmap.paizhao)
                        .error(R.mipmap.paizhao)
                        .fallback(R.mipmap.paizhao);
                Glide.with(AddInsuranceActivity.this)
                        .load(IPConfig.getOutSourceURLPreFix() + picPath)
                        .apply(requestOptions)
                        .into(imgPhoto);
            }

            @Override
            public void faild() {
                ToastUtil.show(AddInsuranceActivity.this, "图片上传失败，请重新上传");
                dismiss();
            }
        });

    }

    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openLocalImage(AddInsuranceActivity.this);
                    }
                }).addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openCameraImage(AddInsuranceActivity.this);
                    }
                });
        dialog.show();
    }


    private void seleTimePicker(EditText textView) {
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



    /**
     * 查看大图
     */
    private void lookImage(String picUrl){
        LogUtil.e("查看大图url",picUrl);
        if ( !TextUtils.isEmpty(picUrl)) {
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("images", IPConfig.getOutSourceURLPreFix() + picUrl);//非必须
            int[] location = new int[2];
            imgPhoto.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);//必须
            intent.putExtra("locationY", location[1]);//必须
            intent.putExtra("width", imgPhoto.getWidth());//必须
            intent.putExtra("height", imgPhoto.getHeight());//必须
            startActivity(intent);
            overridePendingTransition(0, 0);
        }else {
            ToastUtil.show(this,"无法查看图片");
        }
    }
}
