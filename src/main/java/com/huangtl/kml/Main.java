package com.huangtl.kml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        KmlProperty kmlProperty = getKmlData();
        if (kmlProperty.getKmlPoints().size() > 0) {
            System.out.println("点====");
            for (KmlPoint k : kmlProperty.getKmlPoints()) {
                System.out.println("点【"+k.getName()+"】        ====》       "+k.getPoints());
            }
        }
        if (kmlProperty.getKmlLines().size() > 0) {
            System.out.println("线====");
            for (KmlLine k : kmlProperty.getKmlLines()) {
                System.out.println("线【"+k.getName()+"】        ====》       "+k.getPoints());
            }
        }
        if (kmlProperty.getKmlPolygons().size() > 0) {
            System.out.println("面====");
            for (KmlPolygon k : kmlProperty.getKmlPolygons()) {
                System.out.println("面【"+k.getName()+"】        ====》       "+k.getPoints());
            }
        }
    }

    public static KmlProperty getKmlData(){
        KmlProperty kmlProperty = null;
        ParsingKmlUtil parsingKmlUtil = new ParsingKmlUtil();
        String path = Main.class.getClassLoader().getResource("").getPath();

//        String path1 = this.getClass().getResource("").getPath();
        System.out.println(path);
        File file = new File(path+"/test.kml");
        try {
            kmlProperty = parsingKmlUtil.parseKmlByInputstream(new FileInputStream(file));
            assert kmlProperty != null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        kmlProperty = parsingKmlUtil.parseKmlForJAK(file);

        return kmlProperty;
    }
}
