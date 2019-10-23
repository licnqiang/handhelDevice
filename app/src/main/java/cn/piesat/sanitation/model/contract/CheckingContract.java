package cn.piesat.sanitation.model.contract;

import java.util.List;

import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.StationCheckSet;

/**
 * @author lq
 * @fileName LoginContract
 * @data on  2019/7/25 15:44
 * @describe TODO
 */
public interface CheckingContract {

    interface CheckingView {
        void Error(String errorMsg);
        void SuccessFinshByCheckRecord(List<CheckRecord> checkRecord);
        void SuccessFinshByWorKTimeSet(StationCheckSet stationCheckSet);
    }

    interface CheckingPresenter {
        //查询考勤情况
        void QueryCheckingState(String date);
        //考勤时间段设置
        void WorKTimeSet();
    }
}
