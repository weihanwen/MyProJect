package com.tianer.util.rsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tianer.controller.base.BaseController;
import com.tianer.util.MD5;
import com.tianer.util.Util;
import com.tianer.util.XMLParser;


/**
 * RSA相关测试
 * Base64用了Apache的工具包，即Apache Commons Codec
 * 为：commons-codec-1.10.jar
 *
 */
public class RSACoderTest {
	
   
	public static String publicKey;   
	public static String privateKey;   
	public static Map<String, Object> keyMap = new HashMap<String, Object>();
    
    //初始化
    public RSACoderTest() throws Exception{
    	 keyMap = RSACoder.initKey();   
         privateKey = RSACoder.getPrivateKey(keyMap);  
         publicKey = RSACoder.getPublicKey(keyMap);   
 	}
    


     
    
    public void test() throws Exception { 
         System.out.println("---------------------------私钥加密——公钥解密-----------------------------"); 
        String timestamp = getTimestampStr();//获取时间戳
		String waitSignStr = "deno100macidadminorderid20170329170000phone15925609210time" + timestamp;//待加密的字符串
		System.out.println("要加密的字符串:\r" + waitSignStr); 
        byte[] data = waitSignStr.getBytes("UTF-8");   

//        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);//私钥加密
//        String outputEncodedDataStr = Base64.encodeBase64String(encodedData); 
//        System.out.println("私钥加密后base64的字符串:\r" + outputEncodedDataStr); 
//
//		byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);//公钥加密
//        String outputStr = new String(decodedData);   
//        System.out.println("公钥解密后的字符串:\r" + outputStr); 
          
        System.out.println("---------------------------私钥签名——公钥验证签名---------------------------");   
        String sign = RSACoder.sign(data, privateKey); //签名
        System.out.println("私钥签名后的字符串:\r" + sign);   
//        
//        boolean status = RSACoder.verify(data, publicKey, sign);   // 验证签名   
//        System.out.println("验证签名状态(true为验证签名通过, false为验证签名失败):\r" + status);   
        
        //测试地址，直接复制到浏览器中运行
		String requestUrl = "http://shop.test.bolext.cn:81/shop/buyunit/orderpayforflow.do?arsid=4400&deno=100&macid=admin&orderid=20170329170000&phone=15925609210&sign="+URLEncoder.encode(sign,"UTF-8")+"&time=" + timestamp;
        System.out.println("请求地址:\r" + requestUrl);   
    }   
    
    
    
    /**
     * 获取油卡充值的调用接口
     * @param deno 金额
     * @param orderid 订单号
     * @param phone 电话号
     * @param oilcard_number 油卡号
     * @param timestamp 时间戳
     * @param arsid 油卡类型：中石化-4400 中石油-4500
     * @return 
     * @throws Exception
     */
    public String  getUrl(String deno,String orderid,String phone,String oilcard_number,String timestamp,String arsid) throws Exception {//
    	System.out.println("---------------------------生成签名，访问地址---------------------------");  
    	String requestUrl="";
        try {
        	StringBuffer sbReturn = new StringBuffer();
    		// 返回的数据进行组合
        	sbReturn.append("arsid").append(arsid).append("deno").append(deno)
    			    .append("macid").append(RSAConstants.macid)
    			    .append("orderid").append(orderid)
    			    .append("phone").append(oilcard_number)
     			    .append("time").append(timestamp);
       		String waitSignStr = sbReturn.toString();//待加密的字符串
            byte[] data = waitSignStr.getBytes("UTF-8");   
            String sign = RSACoder.sign(data, privateKey); //签名
            System.out.println("私钥签名后的字符串: " + sign);   
      		requestUrl = "http://shop.test.bolext.cn:81/shop/buyunit/orderpayforjyk.do?deno="+deno+"&macid="+RSAConstants.macid+"&orderid="+orderid+"&phone="+oilcard_number+"&arsid="+arsid+"&sign="+URLEncoder.encode(sign,"UTF-8")+"&time=" + timestamp;
            System.out.println("请求地址: " + requestUrl); 
 		} catch (Exception e) {
			// TODO: handle exception
 			(new BaseController()).logger.error(e.toString(), e);
		}
         return requestUrl;
    }  
    
    
    /**
     * 验签名
     * @param sign 回调的签名
     * @param deno 金额
     * @param orderid 订单ID
     * @param phone 电话
     * @param oilcard_number 油卡
     * @param timestamp 时间戳
      * @return
     */
    public boolean yanqian(String sign,String deno,String orderid,String phone,String oilcard_number,String timestamp,String arsid){//加密前的字符串
     	 boolean flag_status=true;
     	 try {
	     		StringBuffer sbReturn = new StringBuffer();
	    		// 返回的数据进行组合
	        	sbReturn.append("arsid").append(arsid)
	        			.append("deno").append(deno)
	    			    .append("macid").append(RSAConstants.macid)
	    			    .append("orderid").append(orderid)
	    			    .append("phone").append(oilcard_number)
	    			    .append("time").append(timestamp);
	       		String waitSignStr = sbReturn.toString();//待加密的字符串
	            byte[] data = waitSignStr.getBytes("UTF-8");   
       	        flag_status = RSACoder.verify(data, publicKey, sign);   // 验证签名   
//     	        System.out.println("验证签名状态(true为验证签名通过, false为验证签名失败):\r" + flag_status);   
      	        System.out.println("请求地址:\r" + flag_status); 
  		} catch (Exception e) {
 			// TODO: handle exception
  			flag_status=false;
 		}
     	return flag_status;
    }
    
    
    
    /**
     * 获取自1970年1月1号以来的毫秒数
     * @return
     * @throws Exception
     */
    public  String getTimestampStr() throws Exception {
    	  java.text.SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	  java.util.Date  date =   formater.parse("1970-01-01 00:00:00");  
    	  long time = System.currentTimeMillis() - date.getTime();
    	  return String.valueOf(time).substring(0,10);
    }
    
    
    //对字符加密
    public String jiami(String notjiamiStr){
    	 String jiamiStr="";
     	 try {
      		  String waitSignStr = notjiamiStr;// + getTimestampStr();//待加密的字符串
      		  System.out.println("加密字符串:\r" + waitSignStr); 
              byte[] data = waitSignStr.getBytes("UTF-8");   
              byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);//私钥加密
              jiamiStr = Base64.encodeBase64String(encodedData); 
//              System.out.println("私钥加密后base64的字符串:\r" + jiamiStr); 
 		} catch (Exception e) {
			// TODO: handle exception
		}
     	return jiamiStr;
    }
    //对字符解密
    public String jiemi(String jiamiStr){
   	 String jiemiStr="";
    	 try {
    		 byte[] keyBytes = Base64.decodeBase64(jiamiStr.getBytes()); 
     		 byte[] decodedData = RSACoder.decryptByPublicKey(keyBytes, publicKey);//公钥加密
    		 jiemiStr= new String(decodedData);   
//           System.out.println("公钥解密后的字符串:\r" + jiemiStr); 
 		} catch (Exception e) {
			// TODO: handle exception
 			System.out.println(e.toString());
		}
    	return jiemiStr;
   }
    
  
    
 
    

	public  static void main(String[] args) {
  		try {
  			RSACoderTest tt=new RSACoderTest();
  			String timestamp=tt.getTimestampStr();
			String sign=getSign("100", "201703300968", "15925609210", "1000113200005437747", timestamp, "4400");
			System.out.println("签名："+sign);
			String requestUrl = "http://shop.test.bolext.cn:81/shop/buyunit/orderpayforjyk.do?encryptType=MD5&deno=100&macid="+RSAConstants.md5macid+"&orderid=201703300968&phone=1000113200005437747&arsid=4400&sign="+URLEncoder.encode(sign,"UTF-8")+"&time=" + timestamp;
       		System.out.println("请求地址：" + requestUrl); 
 			String xml=httpGet(requestUrl);
 			System.out.println("回调回来的数据："+xml);
  			Map<String, Object> map = XMLParser.getMapFromXML(xml);
			System.out.println("map数据："+map);
			sign = URLDecoder.decode(map.get("sign").toString(),"UTF-8");
			System.out.println("使用utf8:"+sign);
			// 返回的数据进行组合
			StringBuffer sbReturn = new StringBuffer();
						 sbReturn.append("deno").append(map.get("deno").toString())
							    .append("errcode").append(map.get("errcode").toString())
							    .append("errinfo").append(map.get("errinfo").toString())
							    .append("id").append(map.get("id").toString())
							    .append("orderid").append(map.get("orderid").toString())
							    .append("successdeno").append(map.get("successdeno").toString()).append(RSAConstants.md5key);
			System.out.println("返回字符串组合："+sbReturn.toString());
			String now_sign=MD5.md5(sbReturn.toString());
			System.out.println("回调后生成的sign："+now_sign);
  		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * 获取请求的sign
	 * 使用MD5
	 * @return
	 */
	 public static String  getSign(String deno,String orderid,String phone,String oilcard_number,String timestamp,String arsid) throws Exception {//
	    	System.out.println("---------------------------生成签名，访问地址---------------------------");  
	    	String sign="";
	        try {
	        	StringBuffer sbReturn = new StringBuffer();
	    		// 返回的数据进行组合
	        	sbReturn.append("arsid").append(arsid)
	        			.append("deno").append(deno)
	    			    .append("macid").append(RSAConstants.md5macid)
	    			    .append("orderid").append(orderid)
	    			    .append("phone").append(oilcard_number)
 	    			    .append("time").append(timestamp).append(RSAConstants.md5key);
	       		String waitSignStr = sbReturn.toString();//待加密的字符串
 	            sign = MD5.md5(waitSignStr); //签名
	            System.out.println("私钥签名后的字符串: " + sign);   
 	 		} catch (Exception e) {
				// TODO: handle exception
	 			(new BaseController()).logger.error(e.toString(), e);
			}
	         return sign;
	    }  
    
	 /*返回格式
      * <result>
      * 		<id>20170116134631</id>
      * 		<orderid>20170116134631</orderid>
      * 		<deno>100</deno>
      * 		<successdeno>100</successdeno>
      * 		<errcode>OrderSended</errcode>
      * 		<errinfo>处理中</errinfo>
      * 		<sign>snJSsuKQLUVAUSCElMIJcjM4Er13T18JMt48j1omHDTSk67KLjOdU6jpnpIZwGa8sx+mMJKs3Oc/MvByO4GRUFoRqhhStAFbndMG5V1rzf5X0SHklacWMXO57gApfrCGYk5wHFiS+Kc4Bx6KEAPGT8kNpedycJ87HXc3fD3mChQ=</sign>
      *</result>
      */
	 
	 
 	 /**
	  * 解析xml格式
	  * @param xmlString
	  * @return
	  * @throws ParserConfigurationException
	  * @throws IOException
	  * @throws SAXException
	  */
	 public static Map<String,Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {
         //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is =  Util.getStringStream(xmlString);
        Document document = builder.parse(is);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;
     }
	 
	 
	 /**
	     * Http Get 请求
	     * @param urlString
	     * @return
	     */
	    public static String httpGet(String urlString) {
	         String result = "";
	        try {
	            URL url = new URL(urlString);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = rd.readLine()) != null) {
	                result += line;
	            }
	             rd.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	    
	    
	    //=======================================以下为自定义接口================================================
	    
	    
	    /**
	     * 充值油卡并返回数据，是否充值成功
	     * money 金额
	     * orderno 订单号
	     * phone 电话
	     * oilcard 油卡号码
	     * timestamp 时间戳
	     * arsid 油卡类型
		 * 1----获取请求的sign
		 * 2----拼接请求地址requestUrl
		 * 3----开始请求返回xml数据
		 * 4----xml转map
		 * 使用MD5
		 * @return
		 */
		 public static Map<String, Object>  toPayOil(String money,String orderno,String phone,String oilcard_number,String timestamp,String arsid) throws Exception {//
			 	Map<String, Object> map=null;
 		        try {
		        	//1.
		        	StringBuffer sbReturn = new StringBuffer();
		    		// 返回的数据进行组合
		        	sbReturn.append("arsid").append(arsid)
		        			.append("deno").append(money)
		    			    .append("macid").append(RSAConstants.md5macid)
		    			    .append("orderid").append(orderno)
		    			    .append("phone").append(oilcard_number)
	 	    			    .append("time").append(timestamp).append(RSAConstants.md5key);
		       		String waitSignStr = sbReturn.toString();//待加密的字符串
		       		String sign  = MD5.md5(waitSignStr); //签名
		            System.out.println("私钥签名后的字符串: " + sign); 
		            //2.
		            String url="http://shop.test.bolext.cn:81/shop/buyunit/orderpayforjyk.do?";
		            String requestUrl = url+"encryptType=MD5&deno="+money
			            					+"&macid="+RSAConstants.md5macid
			            					+"&orderid="+orderno
			            					+"&phone="+oilcard_number
			            					+"&arsid="+arsid
			            					+"&sign="+URLEncoder.encode(sign,"UTF-8")
			            					+"&time=" + timestamp;
		            //3.
		            String xml=httpGet(requestUrl);
		            //4.
		            map = getMapFromXML(xml);
	 	 		} catch (Exception e) {
					// TODO: handle exception
		 			(new BaseController()).logger.error(e.toString(), e);
				}
		         return map;
		    }  
    

}
