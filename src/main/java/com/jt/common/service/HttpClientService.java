package com.jt.common.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HttpClientService {
	@Autowired(required = false) // 非强制注入, 调用时注入
	private CloseableHttpClient httpClient; // 操作http请求
	@Autowired(required = false)
	private RequestConfig requestConfig;

	String result = null;

	public String doGet(String url, Map<String, String> params) {
		return doGet(url, params, null);
	}

	public String doGet(String url) {
		return doGet(url, null, null);
	}

	/**
	 * 
	 * 
	 * @param url
	 * @param param
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String doPost(String url, Map<String, String> params, String charset) {
		
		String result=null;
		
		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		// 1.定义连接时长
		System.out.println("url--"+url);
		HttpPost post = new HttpPost(url);
		post.setConfig(requestConfig);

		// 2.参数封装
		try {
			if (params != null) {
				List<BasicNameValuePair> parameters = new ArrayList<>();

				for (Map.Entry<String, String> entry : params.entrySet()) {
					parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, charset);
				post.setEntity(entity);
			}
			
		
		
		//3.发起url请求
		CloseableHttpResponse httpResponse = httpClient.execute(post);
		int code =httpResponse.getStatusLine().getStatusCode();
		System.out.println("code:"+code);
		if (code ==200) {
			result = EntityUtils.toString(httpResponse.getEntity());
		}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	public String doPost(String url, Map<String, String> params) {
		return doPost(url,  params,  null);
	}
	
	public String doPost(String url) {
		return doPost(url,  null,  null);
	}
	

	// 编辑请求发送的方式, 获取返回值结果给调用者
	public String doGet(String url, Map<String, String> params, String charset) {
		// 1.判断字符集编码是否为null
		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}

		// 2.封装用户提交的参数,原理是拼接字符串:手动拼串
		// 例如:http://www.jt.com?id=1&name=tom
//		if (params!=null) {
//			//拼接url
//			url=url+"?";
//			
//			for (Map.Entry<String, String> entry : params.entrySet()) {
//				String key = entry.getKey();
//				String value = entry.getValue();
//				url = url + key + "=" + value + "&";
//			}
//			url = url.substring(0, url.length()-1);
//		}
		// 使用工具API:URIBuild生成url
		if (params != null) {

			try {
				URIBuilder builder = new URIBuilder(url);

				for (Map.Entry<String, String> entry : params.entrySet()) {
					builder.addParameter(entry.getKey(), entry.getValue());
				}
				url = builder.build().toString();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 3.封装请求参数类型
		HttpGet httpGet = new HttpGet(url);

		// 4.发起请求.获取响应结果

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			// 5.判断返回值是否正确
			if (response.getStatusLine().getStatusCode() == 200) {
				// 6.解析返回值数据
				result = EntityUtils.toString(response.getEntity());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

//    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);
//
//    @Autowired(required=false)
//    private CloseableHttpClient httpClient;
//
//    @Autowired(required=false)
//    private RequestConfig requestConfig;
//
//    /**
//     * 执行get请求
//     * 
//     * @param url
//     * @return
//     * @throws Exception
//     */
//    public String doGet(String url,Map<String, String> params,String encode) throws Exception {
//        LOGGER.info("执行GET请求，URL = {}", url);
//        if(null != params){
//            URIBuilder builder = new URIBuilder(url);
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                builder.setParameter(entry.getKey(), entry.getValue());
//            }
//            url = builder.build().toString();
//        }
//        // 创建http GET请求
//        HttpGet httpGet = new HttpGet(url);
//        httpGet.setConfig(requestConfig);
//        CloseableHttpResponse response = null;
//        try {
//            // 执行请求
//            response = httpClient.execute(httpGet);
//            // 判断返回状态是否为200
//            if (response.getStatusLine().getStatusCode() == 200) {
//                if(encode == null){
//                    encode = "UTF-8";
//                }
//                return EntityUtils.toString(response.getEntity(), encode);
//            }
//        } finally {
//            if (response != null) {
//                response.close();
//            }
//            // 此处不能关闭httpClient，如果关闭httpClient，连接池也会销毁
//        }
//        return null;
//    }
//    
//    public String doGet(String url, String encode) throws Exception{
//        return this.doGet(url, null, encode);
//    }
//    
//    public String doGet(String url) throws Exception{
//        return this.doGet(url, null, null);
//    }
//
//    /**
//     * 带参数的get请求
//     * 
//     * @param url
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    public String doGet(String url, Map<String, String> params) throws Exception {
//        return this.doGet(url, params, null);
//    }
//
//    /**
//     * 执行POST请求
//     * 
//     * @param url
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    public String doPost(String url, Map<String, String> params,String encode) throws Exception {
//        // 创建http POST请求
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setConfig(requestConfig);
//
//        if (null != params) {
//            // 设置2个post参数，一个是scope、一个是q
//            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//
//            // 构造一个form表单式的实体
//            UrlEncodedFormEntity formEntity = null;
//            if(encode!=null){
//                formEntity = new UrlEncodedFormEntity(parameters,encode);
//            }else{
//                formEntity = new UrlEncodedFormEntity(parameters);
//            }
//            // 将请求实体设置到httpPost对象中
//            httpPost.setEntity(formEntity);
//        }
//
//        CloseableHttpResponse response = null;
//        try {
//            // 执行请求
//            response = httpClient.execute(httpPost);
//            // 判断返回状态是否为200
//            if (response.getStatusLine().getStatusCode() == 200) {
//                return EntityUtils.toString(response.getEntity(), "UTF-8");
//            }
//        } finally {
//            if (response != null) {
//                response.close();
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * 执行POST请求
//     * 
//     * @param url
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    public String doPost(String url, Map<String, String> params) throws Exception {
//        // 创建http POST请求
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setConfig(requestConfig);
//
//        if (null != params) {
//            // 设置2个post参数，一个是scope、一个是q
//            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//
//            // 构造一个form表单式的实体
//            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
//            // 将请求实体设置到httpPost对象中
//            httpPost.setEntity(formEntity);
//        }
//
//        CloseableHttpResponse response = null;
//        try {
//            // 执行请求
//            response = httpClient.execute(httpPost);
//            // 判断返回状态是否为200
//            if (response.getStatusLine().getStatusCode() == 200) {
//                return EntityUtils.toString(response.getEntity(), "UTF-8");
//            }
//        } finally {
//            if (response != null) {
//                response.close();
//            }
//        }
//        return null;
//    }
//
//    public String doPostJson(String url, String json) throws Exception {
//        // 创建http POST请求
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setConfig(requestConfig);
//        
//        if(null != json){
//            //设置请求体为 字符串
//            StringEntity stringEntity = new StringEntity(json,"UTF-8");
//            httpPost.setEntity(stringEntity);
//        }
//
//        CloseableHttpResponse response = null;
//        try {
//            // 执行请求
//            response = httpClient.execute(httpPost);
//            // 判断返回状态是否为200
//            if (response.getStatusLine().getStatusCode() == 200) {
//                return EntityUtils.toString(response.getEntity(), "UTF-8");
//            }
//        } finally {
//            if (response != null) {
//                response.close();
//            }
//        }
//        return null;
//    }

}
