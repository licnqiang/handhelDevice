package cn.piesat.retrofitframe.netWork.common;


/**
 * 网络返回基类 支持泛型
 * Created by Tamic on 2016-06-06.
 */
public class BaseReseponseInfo<T>{

    public int status;
//    private String msg;
    public T data;   //业务bean


}
