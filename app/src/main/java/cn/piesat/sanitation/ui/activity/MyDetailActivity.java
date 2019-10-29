package cn.piesat.sanitation.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hb.dialog.myDialog.ActionSheetDialog;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.model.contract.UserInfoContract;
import cn.piesat.sanitation.model.presenter.UserInfoPresenter;
import cn.piesat.sanitation.networkdriver.upLoadFile.UpLoadFileControl;
import cn.piesat.sanitation.ui.view.CircleImageView;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.PhotoTool;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

public class MyDetailActivity extends BaseActivity implements UserInfoContract.MyDetailUserInfoView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sex)
    TextView etSex;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_brthy)
    TextView etBrthy;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.user_header)
    CircleImageView userHeader;
    @BindView(R.id.btn_save)
    Button btnSave;
    private String picPath;

    UserInfoPresenter userInfoPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_detail;
    }

    @Override
    protected void initView() {
        userInfoPresenter = new UserInfoPresenter(this);
        tvTitle.setText("个人信息");
        //显示右侧修改按钮
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("修改");
    }

    @Override
    protected void initData() {
        try {
            showInfo();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show(this, "信息显示有误");
        }
    }

    private void showInfo() {
        //不可编辑
        ETFocusa(false);
        UserInfo_Tab userInfo_tab = new Select().from(UserInfo_Tab.class).querySingle();
        picPath = userInfo_tab.lay1Sysuser;
        //显示图片
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_head_default)
                .error(R.mipmap.icon_head_default)
                .fallback(R.mipmap.icon_head_default);
        Glide.with(MyDetailActivity.this)
                .load(IPConfig.getOutSourceURLPreFix() + userInfo_tab.lay1Sysuser)
                .apply(requestOptions)
                .into(userHeader);

        etName.setText(userInfo_tab.name);
        etSex.setText(userInfo_tab.sex.equals("1") ? "男" : "女");
        etPhone.setText(userInfo_tab.phone);
        etBrthy.setText(userInfo_tab.birthday.replace(" 00:00:00", ""));
        etAddress.setText(userInfo_tab.address);
    }


    private void ETFocusa(boolean isFocusa) {

        etName.setFocusable(isFocusa);
        etName.setFocusableInTouchMode(isFocusa);

        etPhone.setFocusable(isFocusa);
        etPhone.setFocusableInTouchMode(isFocusa);

        etAddress.setFocusable(isFocusa);
        etAddress.setFocusableInTouchMode(isFocusa);

        etSex.setEnabled(isFocusa);
        userHeader.setEnabled(isFocusa);
        etBrthy.setEnabled(isFocusa);
        btnSave.setVisibility(isFocusa ? View.VISIBLE : View.GONE);
    }


    @OnClick({R.id.user_header, R.id.img_back, R.id.tv_right, R.id.et_sex, R.id.et_brthy, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_header:
                showDialog();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_right:
                tvRight.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                ETFocusa(true);  //信息可编辑
                break;
            case R.id.et_sex:
                showSex();
                break;
            case R.id.et_brthy:
                seleTimePicker();
                break;
            case R.id.btn_save:
                String name = etName.getText().toString();
                String sex = etSex.getText().toString();
                String phone = etPhone.getText().toString();
                String brthy = etBrthy.getText().toString() + " 00:00:00";  //后台需要固定时间格式 年月日时分秒
                String address = etAddress.getText().toString();
                showLoadingDialog("个人信息保存中", true);
                userInfoPresenter.ModeyUserInfo(picPath, name, sex.equals("女") ? "0" : "1", phone, brthy, address);
                break;
        }
    }

    /**
     * 选择生日
     */
    private void seleTimePicker() {
        TimePickerView pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//      Calendar calendar = Calendar.getInstance();
//      pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setTitle("选择生日");
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                etBrthy.setText(TimeUtils.getStringByFormat(date, "yyyy-MM-dd"));
            }
        });
        pvTime.show();
    }

    /**
     * 选择性别
     */
    private void showSex() {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择性别")
                .addSheetItem("男", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        etSex.setText("男");
                    }
                }).addSheetItem("女", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        etSex.setText("女");
                    }
                });
        dialog.show();
    }

    /**
     * 选择图片
     */
    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择")
                .addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openLocalImage(MyDetailActivity.this);
                    }
                }).addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PhotoTool.openCameraImage(MyDetailActivity.this);
                    }
                });
        dialog.show();
    }

    /**
     * 选择图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    /**
     * 上传头像
     *
     * @param path
     */
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
                Glide.with(MyDetailActivity.this)
                        .load(IPConfig.getOutSourceURLPreFix() + picPath)
                        .apply(requestOptions)
                        .into(userHeader);
            }

            @Override
            public void faild() {
                ToastUtil.show(MyDetailActivity.this, "图片上传失败，请重新上传");
                dismiss();
            }
        });

    }

    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(this, errorMsg);
    }

    @Override
    public void SuccessFinsh() {
        tvRight.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
        ETFocusa(false);  //信息可编辑
        dismiss();
        ToastUtil.show(this, "用户信息修改成功");
    }
}
