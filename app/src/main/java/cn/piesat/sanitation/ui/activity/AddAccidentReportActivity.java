package cn.piesat.sanitation.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import cn.piesat.sanitation.data.AccidentReportBean;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.DriverInfo;
import cn.piesat.sanitation.model.contract.AccidentReportContract;
import cn.piesat.sanitation.model.presenter.AccidentReportPresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.util.DialogUtils;
import cn.piesat.sanitation.util.LogUtil;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 事故 详情、新增
 */
public class AddAccidentReportActivity extends BaseActivity implements AccidentReportContract.GetAccidentAddIView {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.etStation)
    EditText etStation;
    @BindView(R.id.etCarNumber)
    EditText etCarNumber;
    @BindView(R.id.etReportPerson)
    EditText etReportPerson;
    @BindView(R.id.etViolatePerson)
    EditText etViolatePerson;
    @BindView(R.id.etViolateDetail)
    EditText etViolateDetail;

    @BindView(R.id.etViolateDistrict)
    EditText etViolateDistrict;
    @BindView(R.id.etRemark)
    EditText etRemark;
    @BindView(R.id.etViolateDate)
    EditText etViolateDate;
    @BindView(R.id.etViolateFine)
    EditText etViolateFine;
    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;

    @BindView(R.id.btReport)
    Button btReport;
    @BindView(R.id.etDescription)
    EditText etDescription;

    private boolean isEdit=false;//是否可编辑 用于详情页展示
    private String  reportId="";
    private String picPath; //图片url
    private String detailPhoto="";

    private CarInfo.RowsBean carRowsBean; //车辆
    private DriverInfo.RowsBean driverRowsBean; //司机

    private AccidentReportPresenter accidentReportPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_accident_report_add;
    }

    @Override
    protected void initView() {
        isEdit=getIntent().getBooleanExtra("isEdit",false);
        reportId=getIntent().getStringExtra("report_id");
        tv_title.setText(isEdit?"新增事故上报":"事故详情");
        btReport.setVisibility(isEdit? View.VISIBLE:View.GONE);

        findViewById(R.id.img_back).setOnClickListener(v -> finish());

        if (BaseApplication.getUserInfo()!=null){
            etReportPerson.setText(BaseApplication.getUserInfo().name);
            etStation.setText(BaseApplication.getUserInfo().deptNameCount);//站点名称
        }

        accidentReportPresenter=new AccidentReportPresenter(this);
    }

    @Override
    protected void initData() {
        if (!isEdit){
            etViolateDetail.setFocusable(false);
            etViolateFine.setFocusable(false);
            etRemark.setFocusable(false);
            etStation.setFocusable(false);
            etReportPerson.setFocusable(false);
            etDescription.setFocusable(false);
            Map<String,String> map =new HashMap<>();
            map.put("id",reportId);
            showLoadingDialog();
            accidentReportPresenter.getAccidentReportDetail(map);
        }
    }


    @OnClick({R.id.imgPhoto,R.id.btReport,R.id.etViolatePerson,R.id.etCarNumber,R.id.etViolateDistrict,R.id.etViolateDate})
    public void onViewClick(View view){

        switch (view.getId()){
          /*  // 选择站点
            case R.id.etStation:
                Intent intent = new Intent();
                intent.setClass(this, ItemSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(SysContant.QueryType.query_type, SysContant.QueryType.compress_station_key);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
                break;*/
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
            //选择事故人\司机
            case R.id.etViolatePerson:
                if (!isEdit){
                    return;
                }
                Intent intentDriver = new Intent();
                intentDriver.setClass(this, ItemSelectActivity.class);
                Bundle bundleDriver = new Bundle();
                bundleDriver.putString(SysContant.QueryType.query_type, SysContant.QueryType.driver_key);
                intentDriver.putExtras(bundleDriver);
                startActivityForResult(intentDriver, 0);
                break;

            //事故区域
            case R.id.etViolateDistrict:
                if (!isEdit){
                    return;
                }

                DialogUtils.listDiaLog(this, "请选择区域：",SysContant.CommentTag.district, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etViolateDistrict.setText(SysContant.CommentTag.district[which]);
                    }
                });

                break;
            //事故时间
            case R.id.etViolateDate:
                if (!isEdit){
                    return;
                }
                seleTimePicker(etViolateDate);
                break;

            case R.id.imgPhoto:
                if (!isEdit){
                    lookImage(detailPhoto);
                }else {
                    showDialog();
                }
                break;
            case R.id.btReport:
                getReport();

                break;
        }
    }


    private void getReport() {
        String station=etStation.getText().toString().trim();
        String carNumber=etCarNumber.getText().toString().trim();
        String violatePerson=etViolatePerson.getText().toString().trim();
        String violateDate=etViolateDate.getText().toString().trim();
        String violateDetail=etViolateDetail.getText().toString().trim();
        String violateDistrict=etViolateDistrict.getText().toString().trim();
        String violateFine=etViolateFine.getText().toString().trim();
        if (station.isEmpty()){
            ToastUtil.show(this,"站名不得为空");
            return;
        }
        if (carNumber.isEmpty()){
            ToastUtil.show(this,"车牌号不得为空");
            return;
        }
        if (violatePerson.isEmpty()){
            ToastUtil.show(this,"事故人不得为空");
            return;
        }
        if (violateDate.isEmpty()){
            ToastUtil.show(this,"事故时间不得为空");
            return;
        }
        if (violateDetail.isEmpty()){
            ToastUtil.show(this,"定则划分不得为空");
            return;
        }
        if (violateDistrict.isEmpty()){
            ToastUtil.show(this,"事故区域不得为空");
            return;
        }
        if (violateFine.isEmpty()){
            ToastUtil.show(this,"定损额不得为空");
            return;
        }

      /*  if (picPath.isEmpty()){
            ToastUtil.show(this,"违章照片为空");
            return;
        }*/
        showLoadingDialog();
        Map<String,String> map =new HashMap<>();
        map.put("userId",BaseApplication.getIns().getUserId());
        map.put("roleId", String.valueOf(BaseApplication.getUserInfo().userType));
        map.put("administrativeArea",violateDistrict);//行政区划
        map.put("siteName",station);//站点名称
        map.put("carNumber",carNumber);//车牌号
        map.put("reportperson",etReportPerson.getText().toString());//上报人
        map.put("accidentPeople",violatePerson);//事故人
        map.put("accidentTime",violateDate);//事故时间（年月日）
        map.put("proportionalAmount",violateDetail);//定则划分
        map.put("fee",violateFine);//定损额
        if (!etRemark.getText().toString().isEmpty()){
            map.put("remark",etRemark.getText().toString());
        }
        if (!etDescription.getText().toString().isEmpty()){
            map.put("accidentDescription",etDescription.getText().toString());
        }

        if (!picPath.isEmpty()){
          map.put("scenePhotos",IPConfig.getOutSourceURLPreFix()+picPath);
        }
        accidentReportPresenter.getAccidentReportAdd(map);
    }

    @Override
    public void error(String msg) {
        ToastUtil.show(this,msg);
        dismiss();
    }

    @Override
    public void successOnAccidentAdd(String msg) {
        ToastUtil.show(AddAccidentReportActivity.this,msg);
        dismiss();
        setResult(0);
        finish();


    }

    @Override
    public void successOnAccidentReportDetail(AccidentReportBean.AccidentListBean accidentListBean) {
        dismiss();
        if (accidentListBean!=null){
            etStation.setText(accidentListBean.siteName==null?"空":accidentListBean.siteName);
            etCarNumber.setText(accidentListBean.carNumber==null?"空":accidentListBean.carNumber);
            etViolatePerson.setText(accidentListBean.accidentPeople==null?"空":accidentListBean.accidentPeople);
            etViolateDate.setText(accidentListBean.accidentTime==null?"空":accidentListBean.accidentTime);
            etViolateDetail.setText(accidentListBean.proportionalAmount==null?"空":accidentListBean.proportionalAmount);
            etViolateDistrict.setText(accidentListBean.administrativeArea==null?"空":accidentListBean.administrativeArea);
            etViolateFine.setText(accidentListBean.fee==null?"空":accidentListBean.fee);
            etRemark.setText(accidentListBean.remark==null?"空":accidentListBean.remark);
            etDescription.setText(accidentListBean.accidentDescription==null? "空":accidentListBean.accidentDescription);

            if (accidentListBean.scenePhotos!=null){
                detailPhoto=accidentListBean.scenePhotos;
                Glide.with(AddAccidentReportActivity.this)
                        .load(IPConfig.getOutSourceURLPreFix()+accidentListBean.scenePhotos)
                        .into(imgPhoto);
            }


        }

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

         /*   case SysContant.QueryType.compress_station_code:  //选择压缩站
                Bundle bundle = data.getExtras();
                rowsBean = (CompressStations.RowsBean) bundle.getSerializable(SysContant.QueryType.query_type);
                etStation.setText(rowsBean.nameYsz);
                break;*/

            case SysContant.QueryType.car_code:  //选择车辆
                Bundle bundlecar = data.getExtras();
                carRowsBean = (CarInfo.RowsBean) bundlecar.getSerializable(SysContant.QueryType.query_type);
                etCarNumber.setText(carRowsBean.licensePlate);
                break;

            case SysContant.QueryType.driver_code:  //选择司机
                Bundle bundledriver = data.getExtras();
                driverRowsBean = (DriverInfo.RowsBean) bundledriver.getSerializable(SysContant.QueryType.query_type);
                etViolatePerson.setText(driverRowsBean.name);
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
                Glide.with(AddAccidentReportActivity.this)
                        .load(IPConfig.getOutSourceURLPreFix() + picPath)
                        .apply(requestOptions)
                        .into(imgPhoto);
            }

            @Override
            public void faild() {
                ToastUtil.show(AddAccidentReportActivity.this, "图片上传失败，请重新上传");
                dismiss();
            }
        });

    }


    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openLocalImage(AddAccidentReportActivity.this);
                    }
                }).addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openCameraImage(AddAccidentReportActivity.this);
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
