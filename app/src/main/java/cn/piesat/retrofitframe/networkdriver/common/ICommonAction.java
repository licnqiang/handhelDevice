package cn.piesat.retrofitframe.networkdriver.common;

import java.util.Map;

/**
 * Created by lq on 2015/12/21.
 */
public interface ICommonAction {

    void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap);


}
