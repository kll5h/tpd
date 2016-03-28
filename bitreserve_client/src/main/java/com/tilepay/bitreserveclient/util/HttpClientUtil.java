package com.tilepay.bitreserveclient.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.tilepay.bitreserveclient.exception.BitreserveErrorException;
import com.tilepay.bitreserveclient.model.error.BitreserveError;

public class HttpClientUtil {

    private static int TIME_OUT_PERIOD = 300000;
    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIME_OUT_PERIOD).setConnectTimeout(TIME_OUT_PERIOD).build();

    /**
     * GET
     * 
     * @param url
     * @param headers
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doGet(String url, Header[] headers) throws BitreserveErrorException {
        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        if (headers != null && headers.length > 0) {
            httpGet.setHeaders(headers);
        }
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse httpResp = null;
        try {
            httpResp = httpclient.execute(httpGet);
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            } else {
                throw new BitreserveErrorException(BitreserveError.getBitreserveError(statusCode));
            }
        } catch (ClientProtocolException e) {
            throw new BitreserveErrorException(e);
        } catch (IOException e) {
            throw new BitreserveErrorException(e);
        } finally {
            if (httpResp != null) {
                try {
                    httpResp.close();
                } catch (IOException e) {
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                }
            }
        }

        return result;
    }

    /**
     * POST
     * 
     * @param url
     * @param headers
     * @param params
     * @param jsonParam
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPost(String url, Header[] headers, List<NameValuePair> params, JsonObject jsonParam) throws BitreserveErrorException {
        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (headers != null && headers.length > 0) {
            httpPost.setHeaders(headers);
        }
        try {
            if (params != null && !params.isEmpty()) {
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            throw new BitreserveErrorException(e);
        }
        try {
            if (jsonParam != null) {
                StringEntity entity;
                entity = new StringEntity(jsonParam.toString(), "UTF-8");
                httpPost.setEntity(entity);
            }
        } catch (UnsupportedCharsetException e) {
            throw new BitreserveErrorException(e);
        }
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse httpResp = null;
        try {
            httpResp = httpclient.execute(httpPost);
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_ACCEPTED) {
                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            } else {
                throw new BitreserveErrorException(BitreserveError.getBitreserveError(statusCode));
            }
        } catch (ClientProtocolException e) {
            throw new BitreserveErrorException(e);
        } catch (IOException e) {
            throw new BitreserveErrorException(e);
        } finally {
            if (httpResp != null) {
                try {
                    httpResp.close();
                } catch (IOException e) {
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                }
            }
        }

        return result;
    }

    /**
     * POST
     * 
     * @param url
     * @param headers
     * @param params
     * @param jsonParam
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPost(String url, Header[] headers, String jsonParam) throws BitreserveErrorException {
        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (headers != null && headers.length > 0) {
            httpPost.setHeaders(headers);
        }
        try {
            if (jsonParam != null) {
                StringEntity entity;
                entity = new StringEntity(jsonParam.toString(), "UTF-8");
                httpPost.setEntity(entity);
            }
        } catch (UnsupportedCharsetException e) {
            throw new BitreserveErrorException(e);
        }
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse httpResp = null;
        try {
            httpResp = httpclient.execute(httpPost);
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_ACCEPTED) {
                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            } else {
                throw new BitreserveErrorException(BitreserveError.getBitreserveError(statusCode));
            }
        } catch (ClientProtocolException e) {
            throw new BitreserveErrorException(e);
        } catch (IOException e) {
            throw new BitreserveErrorException(e);
        } finally {
            if (httpResp != null) {
                try {
                    httpResp.close();
                } catch (IOException e) {
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                }
            }
        }

        return result;
    }

    /**
     * PATCH
     * 
     * @param url
     * @param headers
     * @param params
     * @param jsonParam
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPatch(String url, Header[] headers, List<NameValuePair> params, JsonObject jsonParam) throws BitreserveErrorException {
        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch(url);
        if (headers != null && headers.length > 0) {
            httpPatch.setHeaders(headers);
        }
        try {
            if (params != null && !params.isEmpty()) {
                httpPatch.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            throw new BitreserveErrorException(e);
        }
        try {
            if (jsonParam != null) {
                StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
                httpPatch.setEntity(entity);
            }
        } catch (UnsupportedCharsetException e) {
            throw new BitreserveErrorException(e);
        }
        httpPatch.setConfig(requestConfig);
        CloseableHttpResponse httpResp = null;
        try {
            httpResp = httpclient.execute(httpPatch);
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            } else {
                throw new BitreserveErrorException(BitreserveError.getBitreserveError(statusCode));
            }
        } catch (ClientProtocolException e) {
            throw new BitreserveErrorException(e);
        } catch (IOException e) {
            throw new BitreserveErrorException(e);
        } finally {
            if (httpResp != null) {
                try {
                    httpResp.close();
                } catch (IOException e) {
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                }
            }
        }

        return result;
    }

    /**
     * PATCH
     * 
     * @param url
     * @param headers
     * @param jsonParam
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPatch(String url, Header[] headers, String jsonParam) throws BitreserveErrorException {
        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch(url);
        if (headers != null && headers.length > 0) {
            httpPatch.setHeaders(headers);
        }
        try {
            if (jsonParam != null) {
                StringEntity entity = new StringEntity(jsonParam);
                httpPatch.setEntity(entity);
            }
        } catch (UnsupportedEncodingException e) {
            throw new BitreserveErrorException(e);
        }
        try {
            if (jsonParam != null) {
                StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
                httpPatch.setEntity(entity);
            }
        } catch (UnsupportedCharsetException e) {
            throw new BitreserveErrorException(e);
        }
        httpPatch.setConfig(requestConfig);
        CloseableHttpResponse httpResp = null;
        try {
            httpResp = httpclient.execute(httpPatch);
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            } else {
                throw new BitreserveErrorException(BitreserveError.getBitreserveError(statusCode));
            }
        } catch (ClientProtocolException e) {
            throw new BitreserveErrorException(e);
        } catch (IOException e) {
            throw new BitreserveErrorException(e);
        } finally {
            if (httpResp != null) {
                try {
                    httpResp.close();
                } catch (IOException e) {
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                }
            }
        }

        return result;
    }

    /**
     * DELETE
     * 
     * @param url
     * @param headers
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doDelete(String url, Header[] headers) throws BitreserveErrorException {
        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        if (headers != null && headers.length > 0) {
            httpDelete.setHeaders(headers);
        }
        httpDelete.setConfig(requestConfig);
        CloseableHttpResponse httpResp = null;
        try {
            httpResp = httpclient.execute(httpDelete);
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            } else {
                throw new BitreserveErrorException(BitreserveError.getBitreserveError(statusCode));
            }
        } catch (ClientProtocolException e) {
            throw new BitreserveErrorException(e);
        } catch (IOException e) {
            throw new BitreserveErrorException(e);
        } finally {
            if (httpResp != null) {
                try {
                    httpResp.close();
                } catch (IOException e) {
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                }
            }
        }

        return result;
    }

    /**
     * GET
     * 
     * @param url
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doGet(String url) throws BitreserveErrorException {
        return doGet(url, null);
    }

    /**
     * GET
     * 
     * @param url
     * @param head
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doGetWithOneHeader(String url, Header header) throws BitreserveErrorException {
        Header[] headerArray = new Header[] { header };
        return doGet(url, headerArray);
    }

    /**
     * POST
     * 
     * @param url
     * @param headers
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPost(String url, Header[] headers) throws BitreserveErrorException {
        return doPost(url, headers, null, null);
    }

    /**
     * POST
     * 
     * @param url
     * @param params
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPost(String url, List<NameValuePair> params) throws BitreserveErrorException {
        return doPost(url, null, params, null);
    }

    /**
     * PATCH
     * 
     * @param url
     * @param headers
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPatch(String url, Header[] headers) throws BitreserveErrorException {
        return doPatch(url, headers, null, null);
    }

    /**
     * PATCH
     * 
     * @param url
     * @param params
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPatch(String url, List<NameValuePair> params) throws BitreserveErrorException {
        return doPatch(url, null, params, null);
    }

    /**
     * PATCH
     * 
     * @param url
     * @param jsonParam
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPatch(String url, Header[] headers, JsonObject jsonParam) throws BitreserveErrorException {
        return doPatch(url, headers, null, jsonParam);
    }

    /**
     * POST
     * 
     * @param url
     * @param jsonParam
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPost(String url, Header[] headers, JsonObject jsonParam) throws BitreserveErrorException {
        return doPost(url, headers, null, jsonParam);
    }

    /**
     * POST
     * 
     * @param url
     * @param head
     * @param params
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPostWithOneHeader(String url, Header header, List<NameValuePair> params) throws BitreserveErrorException {
        Header[] headerArray = new Header[] { header };
        return doPost(url, headerArray, params, null);
    }

    /**
     * POST
     * 
     * @param url
     * @param head
     * @param params
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPostWithOneHeader(String url, Header header, String jsonParam) throws BitreserveErrorException {
        Header[] headerArray = new Header[] { header };
        return doPost(url, headerArray, jsonParam);
    }

    /**
     * POST
     * 
     * @param url
     * @param head
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doPostWithOneHeader(String url, Header header) throws BitreserveErrorException {
        Header[] headerArray = new Header[] { header };
        return doPost(url, headerArray, null, null);
    }

    /**
     * DELETE
     * 
     * @param url
     * @param headers
     * @return String
     * @throws BitreserveErrorException
     */
    public static String doDeleteWithOneHeader(String url, Header header) throws BitreserveErrorException {
        Header[] headerArray = new Header[] { header };
        return doDelete(url, headerArray);
    }
}
