package com.huangtl.utils;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 * 中联车网自定义响应结构
 * @author Administrator
 *
 */
public class JsonUtils {
	// 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 对象或对象集合转Json时日期格式处理
     * @param array
     * @return
     * @throws RuntimeException
     */
    public static String Array2Json(Object array) throws RuntimeException {  
        String result = "";  
        ObjectMapper mapper = new ObjectMapper();  
        try {  
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));  
            result = mapper.writeValueAsString(array);  
        } catch (Exception e) {  
            //log.error("JacksonUtil Exception>>>>>>>:{}", e.toString());  
            throw new RuntimeException(e);  
        }  
        return result;  
    } 
    /**
     * Jackson对象节点创建及使用
     * 
     * 数组节点创建类似，用下面的语句
     * ArrayNode arr = mapper.createArrayNode();  
     * 
     * @param status
     * @param jsonSource
     * @return
     */
    public static String wrapSuccessString(String status, String jsonSource) {  
        String result = null;  
        ObjectMapper mapper = new ObjectMapper();  
        try {  
            ObjectNode node = mapper.createObjectNode();  
            node.put("status", status);  
            node.put("value", mapper.readTree(jsonSource));  
      
            result = mapper.writeValueAsString(node);  
        } catch (JsonProcessingException e) {  
            throw new RuntimeException(e);  
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }  
        return result;  
    }  
    
    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     * @throws IOException 
     */
    public static String objectToJson(Object data) throws IOException {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

    public static <T> T mapToBean(Map map, Class<T> clazz){
        try {
            String json = JsonUtils.objectToJson(map);
            T t = JsonUtils.jsonToPojo(json, clazz);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
   
}
