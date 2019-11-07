package com.crazy.portal.util;

import com.crazy.portal.bean.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by weiying on 2019/7/29.
 */
@Slf4j
public class HttpClientUtils {

    public static final int CONNECT_TIMEOUT = 360000;
    public static final int READ_TIMEOUT = 360000;
    public static final String CHARSET = "UTF-8";

    private static HttpClient client;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
        client = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String get(String url, String authSecret) throws IOException {
        return get(url, authSecret, null, null);
    }

    public static String post(String url, String body, String mimeType, Map<String,String> header) throws IOException{
        return post(url, body, header, mimeType, CHARSET, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    public static String patch(String url, String body, String mimeType, Map<String,String> header) throws IOException{
        return patch(url, body, header, mimeType, CHARSET, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 发送一个 Post 请求, 使用指定的字符集编码.
     *
     * @param url
     * @param body RequestBody
     * @param mimeType 例如 application/xml "application/x-www-form-urlencoded" a=1&b=2&c=3
     * @param charset 编码
     * @param connTimeout 建立链接超时时间,毫秒.
     * @param readTimeout 响应超时时间,毫秒.
     * @return ResponseBody, 使用指定的字符集编码.
     * @throws ConnectTimeoutException 建立链接超时异常
     * @throws SocketTimeoutException  响应超时
     * @throws Exception
     */
    private static String post(String url, String body,Map<String,String> header, String mimeType,String charset, Integer connTimeout, Integer readTimeout) throws IOException {
        HttpClient client = null;
        HttpPost post = new HttpPost(url);
        String result = "";
        boolean isHttps = url.startsWith("https");
        try {
            if (StringUtils.isNotBlank(body)) {
                HttpEntity entity = new StringEntity(body, ContentType.create(mimeType, charset));
                post.setEntity(entity);
            }
            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            post.setConfig(customReqConf.build());

            if(header != null) {
                header.forEach((k,v)->post.setHeader(k,v));
            }
            client = isHttps ? createSSLInsecureClient() : HttpClientUtils.client;
            HttpResponse res = client.execute(post);

            result = IOUtils.toString(res.getEntity().getContent(), charset);

        } catch (GeneralSecurityException | IOException e) {
            log.error("",e);
        } finally {
            post.releaseConnection();

            if (isHttps &&
                    client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
        return result;
    }

    private static String patch(String url, String body,Map<String,String> header, String mimeType,String charset, Integer connTimeout, Integer readTimeout) throws IOException {
        HttpClient client = null;
        HttpPatch patch = new HttpPatch(url);
        String result = "";
        boolean isHttps = url.startsWith("https");
        try {
            if (StringUtils.isNotBlank(body)) {
                HttpEntity entity = new StringEntity(body, ContentType.create(mimeType, charset));
                patch.setEntity(entity);
            }
            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            patch.setConfig(customReqConf.build());

            if(header != null) {
                header.forEach((k,v)-> patch.setHeader(k,v));
            }
            client = isHttps ? createSSLInsecureClient() : HttpClientUtils.client;
            HttpResponse res = client.execute(patch);

            result = IOUtils.toString(res.getEntity().getContent(), charset);

        } catch (GeneralSecurityException | IOException e) {
            log.error("",e);
        } finally {
            close(client, patch, isHttps);
        }
        return result;
    }

    private static void close(HttpClient client, HttpPatch patch, boolean isHttps) throws IOException {
        patch.releaseConnection();

        if (isHttps &&
                client != null && client instanceof CloseableHttpClient) {
            ((CloseableHttpClient) client).close();
        }
    }

    /**
     * 发送一个 GET 请求
     *
     * @param url
     * @param connTimeout  建立链接超时时间,毫秒.
     * @param readTimeout  响应超时时间,毫秒.
     * @return
     * @throws ConnectTimeoutException   建立链接超时
     * @throws SocketTimeoutException   响应超时
     * @throws Exception
     */
    private static String get(String url,String header, Integer connTimeout,Integer readTimeout) throws IOException {

        String result = "";
        HttpClient client = null;
        HttpGet httpGet = new HttpGet(url);
        boolean isHttps = url.startsWith("https");
        try {
            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }

            httpGet.setConfig(customReqConf.build());

            if(StringUtils.isNotEmpty(header)){
                httpGet.setHeader(Constant.Authorization,header);
            }

            client = isHttps ? createSSLInsecureClient() : HttpClientUtils.client;
            HttpResponse res = client.execute(httpGet);

            if (null != res) {
                InputStream content = res.getEntity().getContent();
                result = new BufferedReader(new InputStreamReader(content))
                        .lines().collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (IOException | GeneralSecurityException e) {
            log.error("", e);
        }finally {
            httpGet.releaseConnection();

            if (isHttps && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
        return result;
    }

    /**
     * 创建 SSL连接
     * @return
     * @throws GeneralSecurityException
     */
    private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLContext sslContext = SSLContextBuilder.create()
                    .setProtocol(SSLConnectionSocketFactory.SSL)
                    .loadTrustMaterial((x, y) -> true).build();

            RequestConfig config = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(20000).build();

            CloseableHttpClient httpClient = HttpClientBuilder.create()
                    .setDefaultRequestConfig(config)
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier((x, y) -> true).build();

            return httpClient;
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }
}
