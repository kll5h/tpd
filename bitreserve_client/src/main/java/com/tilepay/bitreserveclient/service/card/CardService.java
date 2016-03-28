package com.tilepay.bitreserveclient.service.card;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tilepay.bitreserveclient.exception.BitreserveErrorException;
import com.tilepay.bitreserveclient.model.card.Card;
import com.tilepay.bitreserveclient.model.card.Settings;
import com.tilepay.bitreserveclient.service.AbstractBitreserveService;
import com.tilepay.bitreserveclient.util.HttpClientUtil;

@Service("bitreserveCardService")
public class CardService extends AbstractBitreserveService {

    private static final String CARDS = "v0/me/cards";
    private static final String CARD = "v0/me/cards/";

    /**
     * List Cards
     * 
     * @param token
     * @return
     * @throws BitreserveErrorException
     */
    public List<Card> listCards(String token) throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + CARDS;

        Header authorization = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        String json = HttpClientUtil.doGetWithOneHeader(url, authorization);
        List<Card> cards = gson.fromJson(json, new TypeToken<List<Card>>() {
        }.getType());

        return cards;
    }

    /**
     * Get Card Details
     * 
     * @param token
     * @param cardId
     * @return
     * @throws BitreserveErrorException
     */
    public Card getCardDetails(String token, String cardId) throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + CARD + cardId;

        Header authorization = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        String json = HttpClientUtil.doGetWithOneHeader(url, authorization);
        Card card = gson.fromJson(json, new TypeToken<Card>() {
        }.getType());

        return card;
    }

    /**
     * Create Card
     * 
     * @param token
     * @param label
     * @param currency
     * @return
     * @throws BitreserveErrorException
     */
    public Card createCard(String token, String label, String currency) throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + CARDS;

        List<Header> headers = new ArrayList<Header>();
        Header authorization = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.add(authorization);
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(contentType);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("label", label));
        params.add(new BasicNameValuePair("currency", currency));

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("label", label);
        jsonObject.addProperty("currency", currency);

        String json = HttpClientUtil.doPost(url, headers.toArray(new BasicHeader[0]), jsonObject);
        Card card = gson.fromJson(json, new TypeToken<Card>() {
        }.getType());

        return card;
    }

    /**
     * Update Card
     * 
     * @param token
     * @param cardId
     * @param label
     * @return Card
     * @throws BitreserveErrorException
     */
    public Card updateCard(String token, String cardId, String label) throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + CARD + cardId;

        List<Header> headers = new ArrayList<Header>();
        Header authorization = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.add(authorization);
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(contentType);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("label", label);

        String json = HttpClientUtil.doPatch(url, headers.toArray(new BasicHeader[0]), jsonObject);
        Card card = gson.fromJson(json, new TypeToken<Card>() {
        }.getType());

        return card;
    }

    /**
     * Update Card
     * 
     * @param token
     * @param cardId
     * @param settings
     * @return
     * @throws BitreserveErrorException
     */
    public Card updateCard(String token, String cardId, Settings settings) throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + CARD + cardId;

        List<Header> headers = new ArrayList<Header>();
        Header authorization = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.add(authorization);
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(contentType);

        String jsonParam = "{\"settings\": {\"position\": \"" + settings.getPosition().toString() + "\", \"starred\": " + settings.getStarred().toString() + "}}";

        String json = HttpClientUtil.doPatch(url, headers.toArray(new BasicHeader[0]), jsonParam);
        Card card = gson.fromJson(json, new TypeToken<Card>() {
        }.getType());

        return card;
    }

    /**
     * Get Location Card By ID
     * 
     * @param cards
     * @param cardId
     * @return
     */
    public static Card getLocationCardById(List<Card> cards, String cardId) {
        for (Card card : cards) {
            if (cardId.equals(card.getId())) {
                return card;
            }
        }
        
        return null;
    }
}