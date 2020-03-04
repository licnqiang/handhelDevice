package cn.piesat.sanitation.util.location;

import java.util.List;

/**
 * Created by sen.luo on 2020-02-19.
 */
public class LocationHelper {

    /**
     * 判断当前位置是否在指定的多边形内
     * @param locationBean
     * @param locationList
     * @return
     */
    public static boolean PtInPolygon(LocationBean locationBean, List<LocationBean>locationList){
        int nCross = 0;
        for (int i = 0; i < locationList.size(); i++)   {
            LocationBean p1 = locationList.get(i);
            LocationBean p2 = locationList.get((i + 1) % locationList.size());
            // 求解 y=p.y 与 p1p2 的交点
            if ( p1.longitude == p2.longitude)      // p1p2 与 y=p0.y平行
                continue;
            if ( locationBean.longitude <  Math.min(p1.longitude, p2.longitude))   // 交点在p1p2延长线上
                continue;
            if ( locationBean.longitude >= Math.max(p1.longitude, p2.longitude))   // 交点在p1p2延长线上
                continue;
            // 求交点的 X 坐标 --------------------------------------------------------------
            double x = (double)(locationBean.longitude - p1.longitude) * (double)(p2.latitude - p1.latitude) / (double)(p2.longitude - p1.longitude) + p1.latitude;
            if ( x > locationBean.latitude )
                nCross++; // 只统计单边交点
        }
        // 单边交点为偶数，点在多边形之外 ---
        return (nCross % 2 == 1);

    }
}
