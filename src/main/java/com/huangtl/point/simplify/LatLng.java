package com.huangtl.point.simplify;

/**
 * @Description TODO
 * @Author huangtl
 * @Date 2020/11/30 13:52
 **/
public class LatLng {

    private final double lat;
    private final double lng;

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
