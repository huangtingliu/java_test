package com.huangtl.point.simplify;

import com.goebl.simplify.Point;
import com.goebl.simplify.Simplify;
import com.huangtl.kml.*;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description simplify-java测试，简化经纬度轨迹点
 * http://hgoebl.github.io/simplify-java/
 * @Author huangtl
 * @Date 2020/11/30 11:45
 **/
public class Test {

    public static void main2(String[] args) {
        // create an instance of the simplifier (empty array needed by List.toArray)
        Simplify<Point> simplify = new Simplify<Point>(new Point[0]);

        // here we have an array with hundreds of points
        Point[] allPoints = {};
        double tolerance = 20;
        boolean highQuality = true; // Douglas-Peucker, false for Radial-Distance

        // run simplification process
        Point[] lessPoints = simplify.simplify(allPoints, tolerance, highQuality);
    }

    public static void main(String[] args) {
        getPoints(1f);
//        getSimplifiedPoints(20f);
    }

    public static LatLng[] getSimplifiedPoints(double tolerance){
        LatLng[] coords = getPoints(); // the array of your "original" points

        System.out.println("优化前轨迹点数量："+coords.length);

        Simplify<LatLng> simplify = new Simplify<LatLng>(new LatLng[0], new LatLngPointExtractor());

        LatLng[] simplified = simplify.simplify(coords, tolerance, false);
        System.out.println("优化前轨迹点数量："+simplified.length);

        return simplified;
    }

    public static LatLng[] getPoints(){
        KmlProperty kmlProperty = Main.getKmlData();

        List<Coordinate> allPoints = new ArrayList();
        if (kmlProperty.getKmlPoints().size() > 0) {
            for (KmlPoint k : kmlProperty.getKmlPoints()) {
                allPoints.addAll(k.getPoints());
            }
        }
        if (kmlProperty.getKmlLines().size() > 0) {
            for (KmlLine k : kmlProperty.getKmlLines()) {
                allPoints.addAll(k.getPoints());
            }
        }
        if (kmlProperty.getKmlPolygons().size() > 0) {
            for (KmlPolygon k : kmlProperty.getKmlPolygons()) {
                allPoints.addAll(k.getPoints());
            }
        }

        return listToArray(allPoints);
    }

    public static KmlProperty getPoints(double tolerance){
        KmlProperty kmlProperty = Main.getKmlData();

        long start = System.currentTimeMillis();
        List<Coordinate> allPoints = new ArrayList();
        if (kmlProperty.getKmlPoints().size() > 0) {
            for (KmlPoint k : kmlProperty.getKmlPoints()) {
                allPoints.addAll(k.getPoints());
                k.setPoints(simplified(tolerance,k.getPoints()));
            }
        }
        if (kmlProperty.getKmlLines().size() > 0) {
            for (KmlLine k : kmlProperty.getKmlLines()) {
                allPoints.addAll(k.getPoints());
                k.setPoints(simplified(tolerance,k.getPoints()));
            }
        }
        if (kmlProperty.getKmlPolygons().size() > 0) {
            for (KmlPolygon k : kmlProperty.getKmlPolygons()) {
                allPoints.addAll(k.getPoints());
                k.setPoints(simplified(tolerance,k.getPoints()));
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("总数量："+allPoints.size());
        System.out.println("耗时："+(end-start)+"ms");
        return kmlProperty;
    }

    /**
     * 获取简化后的数组
     * @param tolerance
     * @param points
     * @return
     */
    private static List<Coordinate> simplified(double tolerance,List<Coordinate> points){
        Simplify<LatLng> simplify = new Simplify<LatLng>(new LatLng[0], new LatLngPointExtractor());
        LatLng[] simplified = simplify.simplify(listToArray(points), tolerance, false);

        return arrayToList(simplified);
    }

    /**
     * 经纬度点列表转数组
     * @param points
     * @return
     */
    private static LatLng[] listToArray(List<Coordinate> points){
        LatLng[] coords = new LatLng[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Coordinate p = points.get(i);
            coords[i] = new LatLng(p.getLatitude(),p.getLongitude());
        }

        return coords;
    }

    private static List<Coordinate> arrayToList(LatLng[] coords){
        List<Coordinate> points = new ArrayList<>(coords.length);
        for (int i = 0; i < coords.length; i++) {
            LatLng p = coords[i];
            if(null!=p) {
                points.add(new Coordinate(p.getLng(), p.getLat()));
            }
        }

        return points;
    }
}
