package com.tilepay.bitreserveclient.service.transaction;

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
import com.tilepay.bitreserveclient.model.card.Card;
import com.tilepay.bitreserveclient.model.transaction.Destination;
import com.tilepay.bitreserveclient.model.transaction.Origin;
import com.tilepay.bitreserveclient.model.transaction.Transaction;
import com.tilepay.bitreserveclient.service.AbstractBitreserveService;
import com.tilepay.bitreserveclient.util.HttpClientUtil;

@Service("bitreserveTransactionService")
public class TransactionService extends AbstractBitreserveService {

    private static final String USER_TRANSACTIONS = "v0/me/transactions";
    private static final String CARD_TRANSACTIONS = "v0/me/cards/:card/transactions";
    private static final String CREATE_TRANSACTION = "v0/me/cards/:card/transactions";
    private static final String COMMIT_TRANSACTION = "v0/me/cards/:card/transactions/:id/commit";
    private static final String CANCEL_TRANSACTION = "v0/me/cards/:card/transactions/:id/cancel";
    private static final String RESEND_TRANSACTION = "v0/me/cards/:card/transactions/:id/resend";

    /**
     * List User Transactions
     * 
     * @param token
     * @return
     * @throws BitreserveErrorException
     */
    public List<Transaction> listUserTransactions(String token) throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + USER_TRANSACTIONS;

        Header authorization = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        String json = HttpClientUtil.doGetWithOneHeader(url, authorization);
        List<Transaction> transactions = gson.fromJson(json, new TypeToken<List<Transaction>>() {
        }.getType());

        return transactions;
    }

    /**
     * List Card Transactions
     * 
     * @param token
     * @param cardId
     * @return
     * @throws BitreserveErrorException
     */
    public List<Transaction> listCardTransactions(String token, String cardId) throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + CARD_TRANSACTIONS.replace(":card", cardId);

        Header authorization = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        String json = HttpClientUtil.doGetWithOneHeader(url, authorization);
        List<Transaction> transactions = gson.fromJson(json, new TypeToken<List<Transaction>>() {
        }.getType());

        return transactions;
    }

    /**
     * Create a Transaction
     * 
     * @param token
     * @param currency
     * @param amount
     * @param destination
     * @return
     * @throws BitreserveErrorException
     */
    public Transaction createTransaction(String token, String cardId, String currency, String amount, String destination) throws BitreserveErrorException {
        String URL = bitreserveConfig.getApiUrl() + CREATE_TRANSACTION.replace(":card", cardId);

        Header httpHeader = CreateHeader(token);
        List<NameValuePair> nvList = new ArrayList<NameValuePair>();
        nvList.add(new BasicNameValuePair("denomination[currency]", currency));
        nvList.add(new BasicNameValuePair("denomination[amount]", amount));
        nvList.add(new BasicNameValuePair("destination", destination));

        String json = HttpClientUtil.doPostWithOneHeader(URL, httpHeader, nvList);
        Transaction result = gson.fromJson(json, new TypeToken<Transaction>() {
        }.getType());

        return result;
    }

    /**
     * Commit a Transaction
     * 
     * @param token
     * @param cardId
     * @param transactionId
     * @param message
     * @return
     * @throws BitreserveErrorException
     */
    public Transaction commitTransaction(String token, String cardId, String transactionId, String message) throws BitreserveErrorException {
        String URL = bitreserveConfig.getApiUrl() + COMMIT_TRANSACTION.replace(":card", cardId).replace(":id", transactionId);

        Header httpHeader = CreateHeader(token);
        List<NameValuePair> nvList = new ArrayList<NameValuePair>();
        if (message != null) {
            nvList.add(new BasicNameValuePair("message", message));
        }

        String json = HttpClientUtil.doPostWithOneHeader(URL, httpHeader, nvList);
        Transaction result = gson.fromJson(json, new TypeToken<Transaction>() {
        }.getType());

        return result;
    }

    /**
     * Cancel a Transaction
     * 
     * @param token
     * @param cardId
     * @param transactionId
     * @return
     * @throws BitreserveErrorException
     */
    public Transaction cancelTransaction(String token, String cardId, String transactionId) throws BitreserveErrorException {
        String URL = bitreserveConfig.getApiUrl() + CANCEL_TRANSACTION.replace(":card", cardId).replace(":id", transactionId);

        Header httpHeader = CreateHeader(token);

        String json = HttpClientUtil.doPostWithOneHeader(URL, httpHeader);
        Transaction result = gson.fromJson(json, new TypeToken<Transaction>() {
        }.getType());

        return result;
    }

    /**
     * Resend a Transaction
     * 
     * @param token
     * @param cardId
     * @param transactionId
     * @return
     * @throws BitreserveErrorException
     */
    public Transaction resendTransaction(String token, String cardId, String transactionId) throws BitreserveErrorException {
        String URL = bitreserveConfig.getApiUrl() + RESEND_TRANSACTION.replace(":card", cardId).replace(":id", transactionId);

        Header httpHeader = CreateHeader(token);

        String json = HttpClientUtil.doPostWithOneHeader(URL, httpHeader);
        Transaction result = gson.fromJson(json, new TypeToken<Transaction>() {
        }.getType());

        return result;
    }
    
    /**
     * @param cards
     * @param bitreserveUserTransactions
     */
    public void setCardsIntoTransactions(List<Card> cards, List<Transaction> bitreserveUserTransactions) {
        if (bitreserveUserTransactions != null && !bitreserveUserTransactions.isEmpty() && cards != null && !cards.isEmpty()) {
            for (Card card : cards) {
                for (Transaction transaction : bitreserveUserTransactions) {
                    Origin origin = transaction.getOrigin();
                    Destination destination = transaction.getDestination();
                    if (origin.getCardId() != null && origin.getCardId().equals(card.getId())) {
                        origin.setCard(card);
                    }
                    if (destination.getCardId() != null && destination.getCardId().equals(card.getId())) {
                        destination.setCard(card);
                    }
                }
            }
        }
    }
    
    /**
     * @param cards
     * @param transaction
     */
    public void setCardIntoTransactions(List<Card> cards, Transaction transaction) {
        if (cards != null && !cards.isEmpty()) {
            for (Card card : cards) {
                Origin origin = transaction.getOrigin();
                if (origin.getCardId() != null && origin.getCardId().equals(card.getId())) {
                    origin.setCard(card);
                    break;
                }
            }
            for (Card card : cards) {
                Destination destination = transaction.getDestination();
                if (destination.getCardId() != null && destination.getCardId().equals(card.getId())) {
                    destination.setCard(card);
                    break;
                }
            }
        }
    }

}