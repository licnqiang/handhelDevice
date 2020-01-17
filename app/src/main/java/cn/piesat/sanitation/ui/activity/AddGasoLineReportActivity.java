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
import cn.piesat.sanitation.model.contract.GasonLineReportContract;
import cn.piesat.sanitation.model.contract.ReportContract;
import cn.piesat.sanitation.model.presenter.GasonLineReportPresenter;
import cn.piesat.sanitation.model.presenter.ReportPresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.ui.view.CommentItemInputModul;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长 新增违章上报
 * Created by sen.luo on 2020/1/2.
 */
public class AddGasoLineReportActivity extends BaseActivity implements GasonLineReportContract.getGasonLineReportAddIView {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.station_name)
    CommentItemModul stationName;
    @BindView(R.id.car_num)
    CommentItemModul carNum;
    @BindView(R.id.report_person)
    CommentItemModul reportPerson;
    @BindView(R.id.gaso_line_person)
    CommentItemModul gasoLinePerson;
    @BindView(R.id.goso_line_station)
    CommentItemInputModul gosoLineStation;
    @BindView(R.id.before_mileage)
    CommentItemInputModul beforeMileage;
    @BindView(R.id.goso_line_money)
    CommentItemInputModul gosoLineMoney;
    @BindView(R.id.goso_line_litre)
    CommentItemInputModul gosoLineLitre;
    @BindView(R.id.area)
    CommentItemModul area;
    @BindView(R.id.order_bz)
    EditText orderBz;
    @BindView(R.id.iv_paizhao)
    ImageView ivPaizhao;

    private CompressStations.RowsBean rowsBean; //站点
    private CarInfo.RowsBean carRowsBean; //车辆
    private DriverInfo.RowsBean driverRowsBean; //司机
    private GasonLineReportPresenter reportPresenter;
    private String picPath; //图片url

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gasoline_report_add;
    }

    @Override
    protected void initView() {
        tv_title.setText("加油上报");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());

        reportPresenter = new GasonLineReportPresenter(this);

        reportPerson.setText(BaseApplication.getUserInfo().name);
        area.setText(BaseApplication.getUserInfo().areaCount);
        stationName.setText(BaseApplication.getUserInfo().deptNameCount);
    }

    @Override
    protected void initData() {


    }

    @OnClick({R.id.station_name, R.id.car_num, R.id.gaso_line_person, R.id.btn_submit_order, R.id.iv_paizhao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 选择站点
            case R.id.station_name:
                Intent intent = new Intent();
                intent.setClass(this, ItemSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(SysContant.QueryType.query_type, SysContant.QueryType.compress_station_key);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
                break;
            //选择车牌
            case R.id.car_num:
                Intent intentcar = new Intent();
                intentcar.setClass(this, ItemSelectActivity.class);
                Bundle bundlecar = new Bundle();
                bundlecar.putString(SysContant.QueryType.query_type, SysContant.QueryType.car_key);
                intentcar.putExtras(bundlecar);
                startActivityForResult(intentcar, 0);
                break;
            //选择加油人，司机
            case R.id.gaso_line_person:
                Intent intentDriver = new Intent();
                intentDriver.setClass(this, ItemSelectActivity.class);
                Bundle bundleDriver = new Bundle();
                bundleDriver.putString(SysContant.QueryType.query_type, SysContant.QueryType.driver_key);
                intentDriver.putExtras(bundleDriver);
                startActivityForResult(intentDriver, 0);
                break;
            case R.id.iv_paizhao:
                showDialog();
                break;
            case R.id.btn_submit_order:
                getReport();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
            case SysContant.QueryType.compress_station_code:  //选择压缩站
                Bundle bundle = data.getExtras();
                rowsBean = (CompressStations.RowsBean) bundle.getSerializable(SysContant.QueryType.query_type);
                stationName.setText(rowsBean.nameYsz);
                break;

            case SysContant.QueryType.car_code:  //选择车辆
                Bundle bundlecar = data.getExtras();
                carRowsBean = (CarInfo.RowsBean) bundlecar.getSerializable(SysContant.QueryType.query_type);
                carNum.setText(carRowsBean.licensePlate);
                break;
            case SysContant.QueryType.driver_code:  //选择司机
                Bundle bundledriver = data.getExtras();
                driverRowsBean = (DriverInfo.RowsBean) bundledriver.getSerializable(SysContant.QueryType.query_type);
                gasoLinePerson.setText(driverRowsBean.name);
                break;
        }

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
                Glide.with(AddGasoLineReportActivity.this)
                        .load(IPConfig.getOutSourceURLPreFix() + picPath)
                        .apply(requestOptions)
                        .into(ivPaizhao);
            }

            @Override
            public void faild() {
                ToastUtil.show(AddGasoLineReportActivity.this, "图片上传失败，请重新上传");
                dismiss();
            }
        });

    }


    private void getReport() {
        String station = stationName.getText().toString().trim();
        String s_carNumber = carNum.getText().toString().trim();
        String s_reportPerson = reportPerson.getText().toString().trim();
        String s_gasoLinePerson = gasoLinePerson.getText().toString().trim();
        String s_gosoLineStation = gosoLineStation.getText().toString().trim();
        String s_beforeMileage = beforeMileage.getText().toString().trim();
        String s_gosoLineMoney = gosoLineMoney.getText().toString().trim();
        String s_gosoLineLitre = gosoLineLitre.getText().toString().trim();
        String s_area = area.getText().toString().trim();
        String s_orderBz = orderBz.getText().toString().trim();

        if (TextUtils.isEmpty(station) || TextUtils.isEmpty(s_carNumber) || TextUtils.isEmpty(s_reportPerson) ||
                TextUtils.isEmpty(s_gasoLinePerson) || TextUtils.isEmpty(s_gosoLineStation) || TextUtils.isEmpty(s_beforeMileage) ||
                TextUtils.isEmpty(s_gosoLineMoney) || TextUtils.isEmpty(s_gosoLineLitre) || TextUtils.isEmpty(s_area) ||
                TextUtils.isEmpty(picPath)) {
            ToastUtil.show(AddGasoLineReportActivity.this, "请补全所有信息，再次提交");
            return;
        }
        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("administrativeArea", s_area);//行政区划
        map.put("siteName", station);//站点名称
        map.put("carNumber", s_carNumber);//车牌号
        map.put("reportperson", s_reportPerson);//上报人
        map.put("refuelingPerson", s_gasoLinePerson);//加油人
        map.put("gasStation", s_gosoLineStation);//加油站点
        map.put("oldMileage", s_beforeMileage);//加油前里程数
        map.put("oilPrice", s_gosoLineMoney);//加油金额
        map.put("oilLitre", s_gosoLineLitre);//加油升数
        map.put("oilPhoto", IPConfig.getOutSourceURLPreFix()+picPath);//加油升数
        map.put("userId", BaseApplication.getIns().getUserId());//加油升数
        map.put("roleId", SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID));//加油升数
        if (!s_orderBz.isEmpty()) {
            map.put("remark", s_orderBz);
        }
        reportPresenter.getGosolineReportAdd(map);
    }

    @Override
    public void Error(String msg) {
        ToastUtil.show(this, msg);
        dismiss();


    }

    @Override
    public void SuccessOnReportAdd(Object o) {
        dismiss();
        ToastUtil.show(this, "提交成功");
        finish();
    }

    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openLocalImage(AddGasoLineReportActivity.this);
                    }
                }).addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openCameraImage(AddGasoLineReportActivity.this);
                    }
                });
        dialog.show();
    }


}
