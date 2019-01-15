package com.huangtl.weixin.utils;


//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.Constants;
import com.huangtl.weixin.bean.encryption.MiniUserInfoDecryptedData;
import com.huangtl.weixin.bean.encryption.MiniUserInfoEncryptedData;

import java.util.Base64;

/**
 * 小程序工具类
 */
public class MiniUtils{

    /**
     * 获取服务商小程序工具类
     * @return
     */
    public static WxUtils getServicerWxUtil(){
        WxUtils MpWxUtil =  new WxUtils(Constants.MINI_ACCESS_TOKEN_KEY,Constants.MINI_APPID,Constants.MINI_APPSECRET);
        return MpWxUtil;
    }

    /**
         加密数据解密算法
         接口如果涉及敏感数据（如wx.getUserInfo当中的 openId 和 unionId），接口的明文内容将不包含这些敏感数据。
         开发者如需要获取敏感数据，需要对接口返回的加密数据(encryptedData) 进行对称解密。 解密算法如下：
         1.对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。
         2.对称解密的目标密文为 Base64_Decode(encryptedData)。
         3.对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
         4.对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
     */
    public static MiniUserInfoDecryptedData decryptData(MiniUserInfoEncryptedData encryptedData, String sessionKey){
        MiniUserInfoDecryptedData decryptedData = new MiniUserInfoDecryptedData();
//        byte[] dataBytes = Base64.decode(encryptedData.getEncryptedData());
//        byte[] ivBytes = Base64.decode(encryptedData.getIv());
//        byte[] sessionkeyBytes = Base64.decode(sessionKey);

        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] result = AES.decrypt(decoder.decode(encryptedData.getEncryptedData()), decoder.decode(sessionKey),
                    AES.generateIV(decoder.decode(encryptedData.getIv())));
//            String s=StringUtils.toString(result,"UTF-8");
            String json = new String(result,"UTF-8");
            decryptedData = JsonUtils.jsonToPojo(json,MiniUserInfoDecryptedData.class);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return decryptedData;
    }

}
