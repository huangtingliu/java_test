package com.huangtl.kml;

import de.micromata.opengis.kml.v_2_2_0.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: KML文件解析
 **/
public class ParsingKmlUtil {
    //以下三行都是自定义的KML类，用于获取名称name、所有点points、样式颜色color
//    private List<KmlPoint> kmlPointList = new ArrayList<>();
//    private List<KmlLine> kmlLineList = new ArrayList<>();
//    private List<KmlPolygon> kmlPolygonList = new ArrayList<>();
//    private KmlProperty kmlProperty = new KmlProperty();

    /**
     * 保存kml数据到临时表
     *
     * @param file 上传的文件实体
     * @return 自定义的KML文件实体
     */
    public KmlProperty parseKmlByFile(File file) {
        Kml kml = Kml.unmarshal(file);
        return getByKml(kml);
    }

    public KmlProperty parseKmlByInputstream(InputStream inputstream) {
        Kml kml = Kml.unmarshal(inputstream);
        return getByKml(kml);
    }

    private KmlProperty getByKml(Kml kml){

        KmlProperty kmlProperty = new KmlProperty();
        kmlProperty.setKmlPoints(new ArrayList<>());
        kmlProperty.setKmlLines(new ArrayList<>());
        kmlProperty.setKmlPolygons(new ArrayList<>());

        Feature feature = kml.getFeature();
        parseFeature(feature,kmlProperty);

        return kmlProperty;
    }

    private void parseFeature(Feature feature, KmlProperty kmlProperty) {
        if (feature != null) {
            if (feature instanceof Document) {
                List<Feature> featureList = ((Document) feature).getFeature();
                featureList.forEach(documentFeature -> {
                            if (documentFeature instanceof Placemark) {
                                getPlaceMark((Placemark) documentFeature, kmlProperty);
                            } else {
                                parseFeature(documentFeature,kmlProperty);
                            }
                        }
                );
            } else if (feature instanceof Folder) {
                List<Feature> featureList = ((Folder) feature).getFeature();
                featureList.forEach(documentFeature -> {
                            if (documentFeature instanceof Placemark) {
                                getPlaceMark((Placemark) documentFeature, kmlProperty);
                            }else{
                                parseFeature(documentFeature,kmlProperty);
                            }
                        }
                );
            }
        }
    }

    private void getPlaceMark(Placemark placemark, KmlProperty kmlProperty) {
        Geometry geometry = placemark.getGeometry();
        String name = placemark.getName();
        if(name==null){
            name=placemark.getDescription();
        }
        parseGeometry(name, geometry,kmlProperty);
    }

    private void parseGeometry(String name, Geometry geometry, KmlProperty kmlProperty) {
        if (geometry != null) {
            if (geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
                if (outerBoundaryIs != null) {
                    LinearRing linearRing = outerBoundaryIs.getLinearRing();
                    if (linearRing != null) {
                        List<Coordinate> coordinates = linearRing.getCoordinates();
                        if (coordinates != null) {
                            outerBoundaryIs = ((Polygon) geometry).getOuterBoundaryIs();
                            addPolygonToList(kmlProperty.getKmlPolygons(), name, outerBoundaryIs);
                        }
                    }
                }
            } else if (geometry instanceof LineString) {
                LineString lineString = (LineString) geometry;
                List<Coordinate> coordinates = lineString.getCoordinates();
                if (coordinates != null) {
                    int width = 0;
                    coordinates = ((LineString) geometry).getCoordinates();
                    addLineStringToList(width, kmlProperty.getKmlLines(), coordinates, name);
                }
            } else if (geometry instanceof Point) {
                Point point = (Point) geometry;
                List<Coordinate> coordinates = point.getCoordinates();
                if (coordinates != null) {
                    coordinates = ((Point) geometry).getCoordinates();
                    addPointToList(kmlProperty.getKmlPoints(), coordinates, name);
                }
            } else if (geometry instanceof MultiGeometry) {
                List<Geometry> geometries = ((MultiGeometry) geometry).getGeometry();
                for (Geometry geometryToMult : geometries) {
                    Boundary outerBoundaryIs;
                    List<Coordinate> coordinates;
                    if (geometryToMult instanceof Point) {
                        coordinates = ((Point) geometryToMult).getCoordinates();
                        addPointToList(kmlProperty.getKmlPoints(), coordinates, name);
                    } else if (geometryToMult instanceof LineString) {
                        int width = 0;
                        coordinates = ((LineString) geometryToMult).getCoordinates();
                        addLineStringToList(width, kmlProperty.getKmlLines(), coordinates, name);
                    } else if (geometryToMult instanceof Polygon) {
                        outerBoundaryIs = ((Polygon) geometryToMult).getOuterBoundaryIs();
                        addPolygonToList(kmlProperty.getKmlPolygons(), name, outerBoundaryIs);
                    }
                }
            }
        }
    }

    private void addPolygonToList(List<KmlPolygon> kmlPolygonList, String name, Boundary outerBoundaryIs) {
        LinearRing linearRing;
        List<Coordinate> coordinates;
        linearRing = outerBoundaryIs.getLinearRing();//面
        coordinates = linearRing.getCoordinates();
        ShowCoordinates(coordinates, "面状障碍:"+name);
        KmlPolygon kmlPolygon = new KmlPolygon();
        kmlPolygon.setPoints(coordinates);
        kmlPolygon.setName(name);
        kmlPolygonList.add(kmlPolygon);
    }

    private void addLineStringToList(int width, List<KmlLine> kmlLineList, List<Coordinate> coordinates, String name) {
        ShowCoordinates(coordinates, "线状障碍:"+name);
        KmlLine kmlLine = new KmlLine();
        kmlLine.setPoints(coordinates);
        kmlLine.setWidth(width);
        kmlLine.setName(name);
        kmlLineList.add(kmlLine);
    }

    private void addPointToList(List<KmlPoint> kmlPointList, List<Coordinate> coordinates, String name) {
        ShowCoordinates(coordinates, "点状障碍:"+name);
        KmlPoint kmlPoint = new KmlPoint();
        kmlPoint.setName(name);
        kmlPoint.setPoints(coordinates);
        kmlPointList.add(kmlPoint);
    }

    private void ShowCoordinates(List<Coordinate> coordinates, String name) {
        /*System.out.println(name+":");
        for (Coordinate coordinate : coordinates) {
            System.out.println(coordinate);
        }*/
    }
}
