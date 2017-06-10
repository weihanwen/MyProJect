package com.tianer.util.rsa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

import com.tianer.util.StringUtil;

/**
* RSA安全编码组件 
*   
*/ 
public class RSACoder{   
    

    /**
     * 用私钥对信息生成数字签名 
     *   
     * @param data  加密数据 
     * @param privateKey  私钥 
     * @return 
     * @throws Exception 
     */ 
    public static String sign(byte[] data, String privateKey) throws Exception {   
        // 解密由base64编码的私钥   
        byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes()); 

        // 构造PKCS8EncodedKeySpec对象   
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   

        // RSAConstants.KEY_ALGORITHM 指定的加密算法   
        KeyFactory keyFactory = KeyFactory.getInstance(RSAConstants.KEY_ALGORITHM);   

        // 取私钥匙对象   
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);   

        // 用私钥对信息生成数字签名   
        Signature signature = Signature.getInstance(RSAConstants.SIGNATURE_ALGORITHM);   
        signature.initSign(priKey);   
        signature.update(data);   
        return Base64.encodeBase64String(signature.sign());   
    }   

    /** *//** 
     * 校验数字签名 
     *   
     * @param data  加密数据 
     * @param publicKey  公钥 
     * @param sign  数字签名 
     * @return 校验成功返回true 失败返回false 
     * @throws Exception 
     *   
     */ 
    public static boolean verify(byte[] data, String publicKey, String sign)   
            throws Exception {   

        // 解密由base64编码的公钥   
        byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());   

        // 构造X509EncodedKeySpec对象   
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);   

        // RSAConstants.KEY_ALGORITHM 指定的加密算法   
        KeyFactory keyFactory = KeyFactory.getInstance(RSAConstants.KEY_ALGORITHM);   

        // 取公钥匙对象   
        PublicKey pubKey = keyFactory.generatePublic(keySpec);   

        Signature signature = Signature.getInstance(RSAConstants.SIGNATURE_ALGORITHM);   
        signature.initVerify(pubKey);   
        signature.update(data);   

        // 验证签名是否正常   
        return signature.verify(Base64.decodeBase64(sign));
    }   

    /**
     * 解密<br> 
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */ 
    public static byte[] decryptByPrivateKey(byte[] data, String key)   
            throws Exception {   
        // 对密钥解密   
        byte[] keyBytes = Base64.decodeBase64(key.getBytes());   

        // 取得私钥   
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(RSAConstants.KEY_ALGORITHM);   
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);   

        // 对数据解密   
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());   
        cipher.init(Cipher.DECRYPT_MODE, privateKey);   

        return cipher.doFinal(data);   
    }   

    /**
     * 解密<br> 
     * 用公钥解密 
     *   
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */ 
    public static byte[] decryptByPublicKey(byte[] data, String key)   
            throws Exception {   
        // 对密钥解密   
        byte[] keyBytes = Base64.decodeBase64(key.getBytes());   

        // 取得公钥   
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(RSAConstants.KEY_ALGORITHM);   
        Key publicKey = keyFactory.generatePublic(x509KeySpec);   

        // 对数据解密   
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());   
        cipher.init(Cipher.DECRYPT_MODE, publicKey);   

        return cipher.doFinal(data);   
    }   
    

    /**
     * 加密<br> 
     * 用公钥加密 
     *   
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */ 
    public static byte[] encryptByPublicKey(byte[] data, String key)   
            throws Exception {   
        // 对公钥解密   
        byte[] keyBytes = Base64.decodeBase64(key.getBytes());   

        // 取得公钥   
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(RSAConstants.KEY_ALGORITHM);   
        Key publicKey = keyFactory.generatePublic(x509KeySpec);   

        // 对数据加密   
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());   
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);   

        return cipher.doFinal(data);   
    }   

    /**
     * 加密<br> 
     * 用私钥加密 
     *   
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */ 
    public static byte[] encryptByPrivateKey(byte[] data, String key)   throws Exception {   
        // 对密钥解密   
        byte[] keyBytes = Base64.decodeBase64(key.getBytes());   

        // 取得私钥   
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(RSAConstants.KEY_ALGORITHM);   
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);   

        // 对数据加密   
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());   
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);   

        return cipher.doFinal(data);   
    }   

    /**
     * 取得私钥 
     *   
     * @param keyMap 
     * @return 
     * @throws Exception 
     */ 
    public static String getPrivateKey(Map<String, Object> keyMap)   
            throws Exception {   
        Key key = (Key) keyMap.get(RSAConstants.PRIVATE_KEY); 
        return Base64.encodeBase64String(key.getEncoded());   
    }   

    /** 
     * 取得公钥 
     *   
     * @param keyMap 
     * @return 
     * @throws Exception 
     */ 
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {   
        Key key = (Key) keyMap.get(RSAConstants.PUBLIC_KEY);   
         return Base64.encodeBase64String(key.getEncoded());   
    }   

    /**
     * 初始化密钥 
     *   
     * @return 
     * @throws Exception 
     */ 
    public static Map<String, Object> initKey() throws Exception {   
//     	BufferedReader privateKey = new BufferedReader(new FileReader("E://hzjy//weihanwen//myproject//WebRoot//WEB-INF//classes//shiyou//privkey_pkcs8.pem"));
     	BufferedReader privateKey = new BufferedReader(new FileReader(RSAConstants.PRIVATE_KEY_FILE));
		BufferedReader publicKey = new BufferedReader(new FileReader(RSAConstants.PUBLIC_KEY_FILE));	
		String strPrivateKey = "";
		String strPublicKey = "";
		String line = "";
		while((line = privateKey.readLine()) != null){
			strPrivateKey += line;
		}
		while((line = publicKey.readLine()) != null){
			strPublicKey += line;
		}
		privateKey.close();
		publicKey.close();
 		// 私钥需要使用pkcs8格式的，公钥使用x509格式的
		String strPrivKey = strPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");				
		String strPubKey = strPublicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");

		byte [] privKeyByte = Base64.decodeBase64(strPrivKey.getBytes());
		byte [] pubKeyByte = Base64.decodeBase64(strPubKey.getBytes());
		PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privKeyByte);
		X509EncodedKeySpec 	pubKeySpec = new X509EncodedKeySpec(pubKeyByte);

		KeyFactory kf = KeyFactory.getInstance("RSA");

		PrivateKey privKey = kf.generatePrivate(privKeySpec);
		PublicKey pubKey = kf.generatePublic(pubKeySpec);
        
        Map<String, Object> keyMap = new HashMap<String, Object>(2);   

        keyMap.put(RSAConstants.PRIVATE_KEY, privKey);   
        keyMap.put(RSAConstants.PUBLIC_KEY, pubKey);   
       
        return keyMap;   
    }   
   
    
    
    public static void main(String[] args) {
    	try {
 			initKey();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
} 