package com.tilepay.bitreserveclient.service.authorization;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.tilepay.bitreserveclient.exception.BitreserveErrorException;
import com.tilepay.bitreserveclient.model.authorization.AuthorizationDetail;
import com.tilepay.bitreserveclient.model.authorization.Token;
import com.tilepay.bitreserveclient.service.AbstractBitreserveService;
import com.tilepay.bitreserveclient.util.HttpClientUtil;

@Service("bitreserveAuthorizationService")
public class AuthorizationService extends AbstractBitreserveService {

	private static final String AUTHORIZE = "authorize";
	private static final String TOKEN = "oauth2/token";
	private static final String REVOKING_PAT = "v0/me/tokens";

	/**
	 * @param authorizationDetail
	 * @return
	 */
	public String getAuthorizeUrl(AuthorizationDetail authorizationDetail) {
		return bitreserveConfig.getServerUrl() + AUTHORIZE + "/" + authorizationDetail.getClientId() + "?state="
				+ authorizationDetail.getState() + "&scope=" + authorizationDetail.getScope();
	}

	/**
	 * @param AuthorizationDetail
	 * @return Token
	 * @throws BitreserveErrorException
	 */
    public Token getAccessToken(AuthorizationDetail authorizationDetail) throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + TOKEN;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("client_id", authorizationDetail.getClientId()));
        params.add(new BasicNameValuePair("client_secret", authorizationDetail.getClientSecret()));
        params.add(new BasicNameValuePair("code", authorizationDetail.getCode()));
        params.add(new BasicNameValuePair("state", "true"));
        params.add(new BasicNameValuePair("redirect_uri", ""));

        Token token = null;
        String json = HttpClientUtil.doPost(url, params);
        token = gson.fromJson(json, new TypeToken<Token>() {
        }.getType());

        return token;
    }

	/**
	 * @param token
	 * @throws BitreserveErrorException
	 */
    public void revokingPAT(String token) throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + REVOKING_PAT + "/" + token;
        Header authorization = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpClientUtil.doDeleteWithOneHeader(url, authorization);
    }

}