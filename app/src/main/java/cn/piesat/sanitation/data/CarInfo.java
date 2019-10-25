package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @fileName CarInfo
 * @data on  2019/10/23 18:56
 * @describe TODO
 */
public class CarInfo implements Serializable{


    public int code;
    public int totle;
    public List<RowsBean> rows;

    public static class RowsBean implements Serializable{
        public long vid;
        public String licensePlate;
        public String licensePlateColor;
        public String vtId;
        public String vehicleType;
        public String vehicleNationality;
        public String maintMiles;
        public String yearExamine;
        public String insuranceDate;
        public String owner;
        public String ownertel;
        public String transIndustryType;
        public String roadTransportSn;
        public String remark;
        public String idSysdept;
        public String gpsOemType;
        public String terminalType;
        public String terminalId;
        public String avlid;
        public String simcardNo;
        public String manufacturerId;
        public String initmileage;
        public String accOffDbInterval;
        public int camNum;
        public String ledType;
        public String vehicleStatus;
        public String fixTime;
        public String fixlocation;
        public String fixer;
        public String relativetel;
        public String lastMaintMiles;
        public String vedioNum;
        public String businessmag;
        public String vehicleClass;
        public String transportMechanism;
        public String owersName;
        public String owersTel;
        public String owersId;
        public String deviceId;
        public String videoType;
        public String videoSource;
        public String m2mcardNo;
        public String photo;
        public String driverids;
        public String externalId;
        public String externalType;
        public String dueTime;
        public String managePassword;
        public String carState;
        public String deptNameCount;
        
    }
}
