package cn.lifengyong.java.little.things.http;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient 工具类，基于version4.4.1
 * 
 * @author lify
 *
 */
public class HttpClientUtils {
    
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    
    /**
     * httpclient读取内容时使用的字符集
     */
    private static final String CONTENT_CHARSET = "UTF-8";
    
    /**
     * json格式
     */
    private static final String CONTENT_TYPE_JSON = "application/json";
    
    /**
     * 获取httpclient
     */
    private static CloseableHttpClient httpclient = getHttpClient();
    
    /**
     * get调用
     * 
     * @param url
     * @return
     */
    public static String get(String url) {
        return get(url, null);
    }
    
    /**
     * 带参数get调用
     * 
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, Map<String, String> params) {
        return get(url, params, null);
    }
    
    /**
     * 带参数和传输字符集get调用
     * 
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String get(String url, Map<String, String> params, String charset){
        //返回结果
        String result = null;
        
        HttpGet httpGet = getHttpGet(url, params);
        
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertStatus(response);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consumeQuietly(entity);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
       
    /**
     * post调用
     * 
     * @param url 请求地址
     * @param params 请求参数
     * @return
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, params, CONTENT_CHARSET);
    }
    
    /**
     * 带字符集的post调用
     * 
     * @param url 请求地址
     * @param params 请求参数
     * @param charset 传输字符集
     * @return
     */
    public static String post(String url, Map<String, String> params, String charset) {
        return post(url, params, charset, null);
    }
    
    /**
     * 带字符集的post调用
     * 
     * @param url 请求地址
     * @param params 请求参数
     * @param charset 传输字符集
     * @return
     */
    public static String postJson(String url, String json) {
        
        //返回结果
        String result = null;
            
        HttpPost httpPost = getHttpPost(url, null);
        StringEntity stringEntity = new StringEntity(json, CONTENT_CHARSET);
        stringEntity.setContentEncoding(CONTENT_CHARSET);
        stringEntity.setContentType(CONTENT_TYPE_JSON);
        httpPost.setEntity(stringEntity);
        
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            assertStatus(response);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF_8);
            }
            EntityUtils.consumeQuietly(entity);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
        
    }
    
    /**
     * 带字符集和头信息的post调用
     * 
     * @param url 请求地址
     * @param params 请求参数
     * @param charset 传输字符集
     * @return
     */
    public static String post(String url, Map<String, String> params, String charset, Map<String, String> header) {
        //返回结果
        String result = null;
            
        HttpPost httpPost = getHttpPost(url, params);
        setHeader(httpPost, header);
        
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            assertStatus(response);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consumeQuietly(entity);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
        
    }
    
    /**
     * 单个文件上传
     * 
     * @param url 上传地址
     * @param filePath 文件绝对路径
     * @param fileName 上传文件名称
     * @throws IOException 
     */
    public static void uploadFile(String url, String filePath, String fileName) throws IOException {
        HttpPost httpPost = getHttpPost(url, null);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", new File(filePath), ContentType.APPLICATION_OCTET_STREAM, fileName);
        HttpEntity multipart = builder.build();
        
        httpPost.setEntity(multipart);
        
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            assertStatus(response);
            HttpEntity entity = response.getEntity();
            EntityUtils.consumeQuietly(entity);
        }
    }
    
    /**
     * 单个文件和参数同时上传
     * 
     * @param url 上传地址
     * @param params 上传参数
     * @param filePath 文件绝对路径
     * @param fileName 上传文件名称
     */
    public static void multipartRequest(String url, Map<String, String> params, 
            String[] filePath, String[] fileName) {
        HttpPost httpPost = getHttpPost(url, null);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setCharset(UTF_8);
        //普通参数
        if (params != null) {
            for (String key : params.keySet()) {
                builder.addTextBody(key, params.get(key), ContentType.APPLICATION_JSON);
            }
        }
        //文件
        if(filePath != null && fileName != null 
                && filePath.length == fileName.length) {
            for(int i = 0; i < filePath.length; i++) {
                builder.addBinaryBody("file"+i, new File(filePath[i]), ContentType.APPLICATION_OCTET_STREAM, fileName[i]);
            }
            
        }
        
        HttpEntity multipart = builder.build();
        
        httpPost.setEntity(multipart);
        
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            assertStatus(response);
            HttpEntity entity = response.getEntity();
            EntityUtils.consumeQuietly(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }
    
    /**
     * 创建HttpClient
     * 
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(10);
        
        return HttpClients.custom()
                .setConnectionManager(cm)
                .evictExpiredConnections()
                .evictIdleConnections(5L, TimeUnit.SECONDS)
                .build();
        
    }
    
    /**
     * 构建httpGet对象
     * 
     * @param url
     * @param params
     * @return
     */
    public static HttpGet getHttpGet(String url, Map<String, String> params) {
        return new HttpGet(appendGetUrl(url, params));
    }
    
    /**
     * 构建httpPost对象
     * 
     * @param url
     * @param params
     * @return
     */
    public static HttpPost getHttpPost(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        
        if (params != null) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                formparams.add(new BasicNameValuePair(key, params.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formparams, UTF_8));
        }
        
        return httpPost;
    }
    
    /**
     * get请求后添加参数
     * 
     * @param url
     * @param params
     * @return
     */
    private static String appendGetUrl(String url, Map<String, String> params) {
        StringBuffer uriStr = new StringBuffer(url);
        if (params != null) {
            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                ps.add(new BasicNameValuePair(key, params.get(key)));
            }
            uriStr.append("?");
            uriStr.append(URLEncodedUtils.format(ps, UTF_8));
        }
        return uriStr.toString();
    }
    
    /**
     * 设置httpRequestHeader
     * 
     * @param request 
     * @param header
     */
    private static void setHeader(HttpRequestBase request, Map<String, String> header) {
        if (header != null) {
            for (String key : header.keySet()) {
                request.addHeader(key, header.get(key));
            }
        }
    }
    
    /**
     * 服务器返回状态码200时才正常
     * 
     * @param res
     * @throws IOException
     */
    private static void assertStatus(HttpResponse res) throws IOException{
        int statusCode = res.getStatusLine().getStatusCode();
        switch (statusCode) {
            case HttpStatus.SC_OK:
                break;
            default:
                throw new IOException("服务器响应状态异常，状态码：" + statusCode);
        }
    }
    
}
