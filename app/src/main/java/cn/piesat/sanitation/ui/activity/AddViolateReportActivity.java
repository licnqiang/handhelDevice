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
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.DriverInfo;
import cn.piesat.sanitation.data.ViolateReportBean;
import cn.piesat.sanitation.model.contract.ReportContract;
import cn.piesat.sanitation.model.presenter.ReportPresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.util.DialogUtils;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长 新增违章上报\违章详情页面
 * Created by sen.luo on 2020/1/2.
 */
public class AddViolateReportActivity extends BaseActivity implements ReportContract.getReportAddIView {

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
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.btReport)
    Button btReport;

    private String picPath; //图片url
    private ReportPresenter reportPresenter;


    private CompressStations.RowsBean rowsBean; //站点
    private CarInfo.RowsBean carRowsBean; //车辆
    private DriverInfo.RowsBean driverRowsBean; //司机

    private boolean isEdit=false;//是否可编辑 用于详情页展示
    private String  reportId="";
    private String detailPhoto="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_violate_report_add;
    }

    @Override
    protected void initView() {
        isEdit=getIntent().getBooleanExtra("isEdit",false);
        reportId=getIntent().getStringExtra("report_id");
        tv_title.setText(isEdit?"添加违章上报":"违章详情");
        btReport.setVisibility(isEdit? View.VISIBLE:View.GONE);

        findViewById(R.id.img_back).setOnClickListener(v -> finish());


        reportPresenter=new ReportPresenter(this);

        if (BaseApplication.getUserInfo()!=null){
            etReportPerson.setText(BaseApplication.getUserInfo().name);
        }



    }

    @Override
    protected void initData() {
        if (!isEdit){
            etViolateDetail.setFocusable(false);
            etViolateFine.setFocusable(false);
            etRemark.setFocusable(false);
            Map<String,String>map =new HashMap<>();
            map.put("id",reportId);
            showLoadingDialog();
            reportPresenter.getViolateReportDetail(map);
        }
    }

    @OnClick({R.id.imgPhoto,R.id.btReport,R.id.etStation,R.id.etViolatePerson,R.id.etCarNumber,R.id.etViolateDistrict,R.id.etViolateDate})
    public void onViewClick(View view){
            if (!isEdit){

                return;
            }
        switch (view.getId()){
            // 选择站点
            case R.id.etStation:
                Intent intent = new Intent();
                intent.setClass(this, ItemSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(SysContant.QueryType.query_type, SysContant.QueryType.compress_station_key);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
                break;
                //选择车牌
            case R.id.etCarNumber:
                Intent intentcar = new Intent();
                intentcar.setClass(this, ItemSelectActivity.class);
                Bundle bundlecar = new Bundle();
                bundlecar.putString(SysContant.QueryType.query_type, SysContant.QueryType.car_key);
                intentcar.putExtras(bundlecar);
                startActivityForResult(intentcar, 0);
                break;
                //选择违章人\司机
            case R.id.etViolatePerson:
                Intent intentDriver = new Intent();
                intentDriver.setClass(this, ItemSelectActivity.class);
                Bundle bundleDriver = new Bundle();
                bundleDriver.putString(SysContant.QueryType.query_type, SysContant.QueryType.driver_key);
                intentDriver.putExtras(bundleDriver);
                startActivityForResult(intentDriver, 0);
                break;

                //违章区域
            case R.id.etViolateDistrict:

                DialogUtils.listDiaLog(this, "请选择区域：",SysContant.CommentTag.district, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etViolateDistrict.setText(SysContant.CommentTag.district[which]);
                    }
                });

                break;
                //违章时间
            case R.id.etViolateDate:
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
            ToastUtil.show(this,"违章人不得为空");
            return;
        }
        if (violateDate.isEmpty()){
            ToastUtil.show(this,"违章时间不得为空");
            return;
        }
        if (violateDetail.isEmpty()){
            ToastUtil.show(this,"违章项目不得为空");
            return;
        }
        if (violateDistrict.isEmpty()){
            ToastUtil.show(this,"违章区域不得为空");
            return;
        }
        if (violateFine.isEmpty()){
            ToastUtil.show(this,"违章罚款不得为空");
            return;
        }

      /*  if (picPath.isEmpty()){
            ToastUtil.show(this,"违章照片为空");
            return;
        }*/
        showLoadingDialog();
        Map<String,String> map =new HashMap<>();
        map.put("administrativeArea",violateDistrict);//行政区划
        map.put("siteName",station);//站点名称
        map.put("carNumber",carNumber);//车牌号
        map.put("reportperson",etReportPerson.getText().toString());//上报人
        map.put("illegalPeople",violatePerson);//违章人
        map.put("illegalTime",violateDate);//违章时间（年月日）
        map.put("illegalProject",violateDetail);//违章项目
        map.put("illegalMoney",violateFine);
        if (!etRemark.getText().toString().isEmpty()){
            map.put("remark",etRemark.getText().toString());
        }

        reportPresenter.getViolateReportAdd(map);
    }

    @Override
    public void Error(String msg) {
        ToastUtil.show(this,msg);
        dismiss();


    }
    @Override
    public void SuccessOnReportAdd(String msg) {
         ToastUtil.show(AddViolateReportActivity.this,msg);
        dismiss();

    }

    @Override
    public void SuccessOnViolateReportDetail(ViolateReportBean.ViolateListBean violateListBean) {
        dismiss();
        if (violateListBean!=null){
            etStation.setText(violateListBean.siteName==null?"空":violateListBean.siteName);
            etCarNumber.setText(violateListBean.carNumber==null?"空":violateListBean.carNumber);
            etViolatePerson.setText(violateListBean.illegalPeople==null?"空":violateListBean.illegalPeople);
            etViolateDate.setText(violateListBean.illegalTime==null?"空":violateListBean.illegalTime);
            etViolateDetail.setText(violateListBean.illegalProject==null?"空":violateListBean.illegalProject);
            etViolateDistrict.setText(violateListBean.administrativeArea==null?"空":violateListBean.administrativeArea);
            etViolateFine.setText(violateListBean.illegalMoney==null?"空":violateListBean.illegalMoney);
            etRemark.setText(violateListBean.remark==null?"空":violateListBean.remark);

            if (violateListBean.illegalPhoto!=null){
                detailPhoto=violateListBean.illegalPhoto;
                Glide.with(AddViolateReportActivity.this)
                        .load(violateListBean.illegalPhoto)
                        .into(imgPhoto);
            }


        }
    }


    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openLocalImage(AddViolateReportActivity.this);
                    }
                }).addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openCameraImage(AddViolateReportActivity.this);
                    }
                });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //选择照相机之后的处理
        if (requestCode==PhotoTool.GET_IMAGE_BY_CAMERA){
            if (resultCode == RESULT_OK) {
                String path = PhotoTool.getImageAbsolutePath(this, PhotoTool.imageUriFromCamera);
                showLoadingDialog("上传图片", false);
                uploadFile(path);
            }

        }


        switch (resultCode) {

            case SysContant.QueryType.compress_station_code:  //选择压缩站
                Bundle bundle = data.getExtras();
                rowsBean = (CompressStations.RowsBean) bundle.getSerializable(SysContant.QueryType.query_type);
                etStation.setText(rowsBean.nameYsz);
                break;

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

            //选择相册之后的处理
            case PhotoTool.GET_IMAGE_FROM_PHONE:
                if (resultCode == RESULT_OK) {
                    String path = PhotoTool.getImageAbsolutePath(this, data.getData());
                    showLoadingDialog("上传图片", false);
                    uploadFile(path);
                }

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
                Glide.with(AddViolateReportActivity.this)
                        .load(IPConfig.getOutSourceURLPreFix() + picPath)
                        .apply(requestOptions)
                        .into(imgPhoto);
            }

            @Override
            public void faild() {
                ToastUtil.show(AddViolateReportActivity.this, "图片上传失败，请重新上传");
                dismiss();
            }
        });

    }
    private void seleTimePicker(EditText textView) {
        TimePickerView pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//      Calendar calendar = Calendar.getInstance();
//      pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setTitle("选择起运时间");
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
