package com.huangtl.webFetcher.demo.service;

import com.huangtl.webFetcher.demo.bean.FetcherObject;
import com.huangtl.webFetcher.demo.bean.ServiceOrg;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 养老机构爬虫服务
 */
public class ProcessYl  {

    //处理列表页
    public void processDoc() {
        try {
//            FetcherObject fo = new FetcherObject("http://www.yanglaocn.com/yanglaoyuan/yly/");
//            Elements list = fo.getElements(".querywhere2");
//            for (Element e : list) {
//                processListItem(e);
//            }
            processListUrl("http://www.yanglaocn.com/yanglaoyuan/yly/","");
           /* for (Node node : list) {
                processTree(node);
            }*/
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据Url处理列表页
    public void processListUrl(String url,String uri){
        String postUrl = url;
        if(StringUtils.isNotEmpty(uri)){
            postUrl+=uri;
        }
        FetcherObject fo = new FetcherObject(postUrl);
        Elements listPageElements = fo.getElements(".querywhere2");
        for (Element e : listPageElements) {
            processListItem(e);
        }
        //是否有下一页
        Elements pageA = fo.getElements(".page .webpage a");
        Element last = pageA.last();
        if(last.className().equals("pagebutton")){
            //代表有下一页
            processListUrl(url,last.attr("href"));
        }
        System.out.println();
    }

    //处理列表项
    public void processListItem(Element root) {
        List<ServiceOrg> orgList = new ArrayList<>();
        Elements list = root.select(".jiadiantucontext");//机构列表
        for (Element item : list) {
            ServiceOrg org = new ServiceOrg();
            org.setProvince("福建省");
            org.setCity("福州市");
//            System.out.println(item.html());
            Elements img = item.select("img");
            if(img.size()>0) {
                org.setMainPic(img.get(0).attr("src"));
            }
            Elements aItems = item.select("a");
            if(aItems.size()>0) {
                processItemDetail(aItems.get(0),org);//处理详情页
                orgList.add(org);
            }
        }

        System.out.println("总记录数"+orgList.size());
    }

    //处理详情页
    public void processItemDetail(Element a,ServiceOrg org) {
        String url = a.attr("href");
        FetcherObject detailObject = new FetcherObject(url);
        Elements BasicInformation = detailObject.getElements("#BasicInformation");//基础信息

        /*处理基础信息*/
        String orgName = BasicInformation.select(".leftcontext_left .leftcontexttitle label").text();//机构名称
        org.setName(orgName);
        Elements basicItems = BasicInformation.select(".leftcontext_left .leftcontexttitleT");//基础信息各项
        for (Element item : basicItems) {
            String establishDate = getItemValue(item, "成立时间：");
            String bedNum = getItemValue(item, "床位数量：");
            String areaSize = getItemValue(item, "占地面积：");
            String objectType = getItemValue(item, "收住对象：");
            String minPrice = getItemValue(item, "收费区间：");
            String maxPrice = minPrice;
            String staffNum = getItemValue(item, "员工人数：");
            String nature = getItemValue(item, "机构性质：");
            String typeOrg = getItemValue(item, "机构类型：");

            org.setEstablishDate(StringUtils.isNotEmpty(establishDate)?establishDate:org.getEstablishDate());
            org.setBedNum(StringUtils.isNotEmpty(bedNum)?bedNum:org.getBedNum());
            org.setAreaSize(StringUtils.isNotEmpty(areaSize)?areaSize:org.getAreaSize());
            org.setObjectType(StringUtils.isNotEmpty(objectType)?objectType:org.getObjectType());
            org.setMinPrice(StringUtils.isNotEmpty(minPrice)?minPrice:org.getMinPrice());
            org.setMaxPrice(StringUtils.isNotEmpty(maxPrice)?maxPrice:org.getMaxPrice());
            org.setStaffNum(StringUtils.isNotEmpty(staffNum)?staffNum:org.getStaffNum());
            org.setNature(StringUtils.isNotEmpty(nature)?nature:org.getNature());
            org.setTypeOrg(StringUtils.isNotEmpty(typeOrg)?typeOrg:org.getTypeOrg());
        }

        Elements OrganizationsOn = detailObject.getElements("#OrganizationsOn");//机构介绍
        org.setOrgContent(getContent(OrganizationsOn,".leftcontextmessage p"));

        Elements DietaryIntroduced = detailObject.getElements("#DietaryIntroduced");//膳食介绍
        org.setEatContent(getContent(DietaryIntroduced,".leftcontextmessage p"));

        Elements ServiceObject = detailObject.getElements("#ServiceObject");//服务对象
        org.setServiceContent(getContent(ServiceObject,".leftcontextmessage p"));

        Elements EnvironmentalFacilities = detailObject.getElements("#EnvironmentalFacilities");//环境设施
        org.setFacilities(getContent(EnvironmentalFacilities,".leftcontextmessage p"));

        Elements CheckinNotes = detailObject.getElements("#CheckinNotes");//入住须知
        org.setLiveContent(getContent(CheckinNotes,".leftcontextmessage p"));

        Elements FeeScale = detailObject.getElements("#FeeScale");//收费标准
        org.setPriceContent(getContent(FeeScale,".leftcontextmessage p"));

        Elements ContactUs = detailObject.getElements("#ContactUs");//联系我们
        Element contactUsContext = ContactUs.select(".leftcontext").get(0);
//        org.setContact(getItemValue(contactUsContext.select("#leftcontext_id .leftcontexttitle").get(0),"联系人："));
//        org.setContact(getItemValue(contactUsContext.select("#leftcontext_id .leftcontexttitle").get(1),"固定电话："));
//        org.setContact(getItemValue(contactUsContext.select("#leftcontext_id .leftcontexttitle").get(2),"手机号码："));
        Elements contactUsItems = contactUsContext.select(".leftcontexttitle");
        org.setEmail(getItemValue(contactUsItems.get(0),"电子邮箱："));
        org.setPostalCode(getItemValue(contactUsItems.get(1),"邮政编码："));
        org.setCounty(getItemValue(contactUsItems.get(2),"所在地区："));
        org.setAddress(getItemValue(contactUsItems.get(3),"联系地址："));


        try {
            Elements TrafficInformation = detailObject.getElements("#TrafficInformation");//交通信息
            Elements tItems = TrafficInformation.select(".leftcontext .leftcontextmessage p");
            org.setPhone(tItems.get(1).text().replace("●联系电话：", ""));
            org.setContact(tItems.get(2).text().replace("●负责人：", ""));
        }catch (Exception e){}

        List<String> imgUrls = new ArrayList<>();
        try {
            Elements PictureShows = detailObject.getElements("#PictureShows");//图集展示
            Elements imgs = PictureShows.select(".leftcontextmessageother img");
            for (Element img : imgs) {
                imgUrls.add(img.attr("src"));
            }

        }catch (Exception e){}
        org.setImageList(imgUrls);

        System.out.println(org);
    }

    /**
     * 根据标题获取基础信息栏各项内容
     * @param item
     * @param filterText
     * @return
     */
    private String getItemValue(Element item,String filterText){
        Elements span = item.select("span");
        String value = null;
        if(null!=span) {
            if (span.text().equals(filterText)) {
                Element parent = span.last().parent();
                Node textNode = parent.childNodes().get(parent.childNodes().size() - 1);
                value= Jsoup.parse(textNode.toString()).text();//可以将&nbsp;等转成空格
//                value= textNode.toString();
            }
        }
        return value;
    }

    /**
     * 获取详情页介绍段落文字
     * @param parent
     * @param selector
     * @return
     */
    private String getContent(Elements parent,String selector){
        Elements messagItems = parent.select(selector);
        StringBuilder content=new StringBuilder();
        for (Element p : messagItems) {
            content.append("    ");
            content.append(p.text());
            content.append(System.getProperty("line.separator"));
        }
        return content.toString();
    }


    public static void main(String[] args) {
        ProcessYl process = new ProcessYl();
        process.processDoc();
    }

}
