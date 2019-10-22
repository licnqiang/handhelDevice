package cn.piesat.sanitation.networkdriver.common;


import java.util.List;

/**
 * 网络返回基类 支持泛型
 * Created by Tamic on 2016-06-06.
 */
public class BaseReseponseInfo<T> {
    public int code;
    public String msg;
    public T data;   //业务bean

    //分页特殊格式 特定字段
    public int totle;
    public Object rows;

}
