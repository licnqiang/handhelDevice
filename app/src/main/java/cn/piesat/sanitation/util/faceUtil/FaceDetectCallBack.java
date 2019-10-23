/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package cn.piesat.sanitation.util.faceUtil;


import cn.piesat.sanitation.util.faceUtil.LivenessModel;

/**
 * 人脸检测回调接口。
 *
 * @Time: 2019/1/25
 * @Author: v_chaixiaogang
 */
public interface FaceDetectCallBack {
    public void onFaceDetectCallback(LivenessModel livenessModel);

    public void onTip(int code, String msg);

    void onFaceDetectDarwCallback(LivenessModel livenessModel);
}