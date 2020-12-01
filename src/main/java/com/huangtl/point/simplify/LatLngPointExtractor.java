package com.huangtl.point.simplify;

import com.goebl.simplify.PointExtractor;

/**
 * @Description TODO
 * @Author huangtl
 * @Date 2020/11/30 13:53
 **/
public class LatLngPointExtractor implements PointExtractor<LatLng> {
    @Override
    public double getX(LatLng point) {
        return point.getLat() * 1000000;
    }

    @Override
    public double getY(LatLng point) {
        return point.getLng() * 1000000;
    }
}
