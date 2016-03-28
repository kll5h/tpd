package com.tilepay.upholdclient;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import com.tilepay.commonclient.util.RandomUtil;
import com.tilepay.commonclient.util.RestClient;
import com.tilepay.upholdclient.model.authorization.UpholdAccessToken;
import com.tilepay.upholdclient.model.card.Card;
import com.tilepay.upholdclient.model.contact.Contact;
import com.tilepay.upholdclient.model.currencypair.CurrencyPair;
import com.tilepay.upholdclient.model.transaction.Destination;
import com.tilepay.upholdclient.model.transaction.Origin;
import com.tilepay.upholdclient.model.transaction.Transaction;
import com.tilepay.upholdclient.model.user.User;

public class UpholdClient {

    private static final String AUTHORIZE = "authorize";
    private static final String TOKEN = "oauth2/token";

    private static String USER = "v0/me";

    private static final String CARDS = "v0/me/cards";
    private static final String CARD = "v0/me/cards/{cardId}";

    private static final String CREATE_TRANSACTION = "v0/me/cards/{cardId}/transactions";
    private static final String COMMIT_TRANSACTION = "v0/me/cards/{cardId}/transactions/{transactionId}/commit";
    private static final String USER_TRANSACTIONS = "v0/me/transactions";
    private static final String CARD_TRANSACTIONS = "v0/me/cards/{cardId}/transactions";
    private static final String CANCEL_TRANSACTION = "v0/me/cards/{cardId}/transactions/{transactionId}/cancel";
    private static final String RESEND_TRANSACTION = "v0/me/cards/{cardId}/transactions/{transactionId}/resend";

    private static String CONTACTS = "v0/me/contacts";
    private static String CONTACT = "v0/me/contacts/{contactId}";

    private static final String TICKER = "v0/ticker";

    private String serverUrl;
    private String apiUrl;
    private UpholdAccessToken upholdAccessToken;
    private String clientId;
    private String clientSecret;
    private String state;
    private String scope;

    public UpholdClient(String serverUrl, String apiUrl, String clientId, String clientSecret, String scope) {
        super();
        this.serverUrl = serverUrl;
        this.apiUrl = apiUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.state = RandomUtil.genRandomNum(10);
        this.scope = scope;
    }

    public UpholdAccessToken getUpholdAccessToken() {
        return upholdAccessToken;
    }

    public String getAuthorizeUrl() {
        return serverUrl + AUTHORIZE + "/" + clientId + "?state=" + state + "&scope=" + scope;
    }

    private boolean validate(String state) {
        return !this.state.equals(state);
    }

    public void requestAccessToken(String state, String code) throws RestClientException {
        if (validate(state)) {
            throw new RestClientException("The state does not match");
        }

        String url = apiUrl + TOKEN;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("code", code);
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(body, headers);
        ResponseEntity<UpholdAccessToken> responseEntity = RestClient.getClient().exchange(url, HttpMethod.POST, requestEntity, UpholdAccessToken.class);
        upholdAccessToken = responseEntity.getBody();
    }

    public User getUser() throws RestClientException {
        User user = null;

        String url = apiUrl + USER;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<User> responseEntity = RestClient.getClient().exchange(url, HttpMethod.GET, requestEntity, User.class);
        user = responseEntity.getBody();

        return user;
    }

    public List<Card> listCards() throws RestClientException {
        Card[] cards = null;

        String url = apiUrl + CARDS;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        ResponseEntity<Card[]> responseEntity = RestClient.getClient().exchange(url, HttpMethod.GET, requestEntity, Card[].class);
        cards = responseEntity.getBody();

        return Arrays.asList(cards);
    }

    public Card getCardDetails(String cardId) throws RestClientException {
        Card card = null;

        String url = apiUrl + CARD;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        ResponseEntity<Card> responseEntity = RestClient.getClient().exchange(url, HttpMethod.GET, requestEntity, Card.class, cardId);
        card = responseEntity.getBody();

        return card;
    }

    public Card createCard(String label, String currency) throws RestClientException {
        Card card = null;

        String url = apiUrl + CARDS;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        headers.add("Content-Type", "application/json");
        String body = "{\"label\":\"" + label + "\",\"currency\":\"" + currency + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<Card> responseEntity = RestClient.getClient().exchange(url, HttpMethod.POST, requestEntity, Card.class);
        card = responseEntity.getBody();

        return card;
    }

    public Card updateCard(String cardId, String label, Integer position, Boolean starred) throws RestClientException {
        Card card = null;

        String url = apiUrl + CARD;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        headers.add("Content-Type", "application/json");
        String body = "{\"label\":\"" + label + "\",\"settings\": {\"position\": \"" + position.toString() + "\", \"starred\": " + starred.toString() + "}}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<Card> responseEntity = RestClient.getClient().exchange(url, HttpMethod.PATCH, requestEntity, Card.class, cardId);
        card = responseEntity.getBody();

        return card;
    }

    public Transaction createTransaction(String cardId, String currency, String amount, String destination) throws RestClientException {
        Transaction transaction = null;

        String url = apiUrl + CREATE_TRANSACTION;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("denomination[currency]", currency);
        body.add("denomination[amount]", amount);
        body.add("destination", destination);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(body, headers);
        ResponseEntity<Transaction> responseEntity = RestClient.getClient().exchange(url, HttpMethod.POST, requestEntity, Transaction.class, cardId);
        transaction = responseEntity.getBody();

        return transaction;
    }

    public Transaction commitTransaction(String cardId, String transactionId, String message) throws RestClientException {
        Transaction transaction = null;

        String url = apiUrl + COMMIT_TRANSACTION;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("message", message);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(body, headers);
        ResponseEntity<Transaction> responseEntity = RestClient.getClient().exchange(url, HttpMethod.POST, requestEntity, Transaction.class, cardId,
                transactionId);
        transaction = responseEntity.getBody();

        return transaction;
    }

    public Transaction cancelTransaction(String cardId, String transactionId) throws RestClientException {
        Transaction transaction = null;

        String url = apiUrl + CANCEL_TRANSACTION;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Transaction> responseEntity = RestClient.getClient().exchange(url, HttpMethod.POST, requestEntity, Transaction.class, cardId,
                transactionId);
        transaction = responseEntity.getBody();

        return transaction;
    }

    public Transaction resendTransaction(String cardId, String transactionId) throws RestClientException {
        Transaction transaction = null;

        String url = apiUrl + RESEND_TRANSACTION;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Transaction> responseEntity = RestClient.getClient().exchange(url, HttpMethod.POST, requestEntity, Transaction.class, cardId,
                transactionId);
        transaction = responseEntity.getBody();

        return transaction;
    }

    public List<Transaction> listUserTransactions() throws RestClientException {
        Transaction[] transactions = null;

        String url = apiUrl + USER_TRANSACTIONS;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Transaction[]> responseEntity = RestClient.getClient().exchange(url, HttpMethod.GET, requestEntity, Transaction[].class);
        transactions = responseEntity.getBody();

        return Arrays.asList(transactions);
    }

    public List<Transaction> listCardTransactions(String cardId) throws RestClientException {
        Transaction[] transactions = null;

        String url = apiUrl + CARD_TRANSACTIONS;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Transaction[]> responseEntity = RestClient.getClient().exchange(url, HttpMethod.GET, requestEntity, Transaction[].class, cardId);
        transactions = responseEntity.getBody();

        return Arrays.asList(transactions);
    }

    public List<Contact> listContacts() throws RestClientException {
        Contact[] contacts = null;

        String url = apiUrl + CONTACTS;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Contact[]> responseEntity = RestClient.getClient().exchange(url, HttpMethod.GET, requestEntity, Contact[].class);
        contacts = responseEntity.getBody();

        return Arrays.asList(contacts);
    }

    public Contact getContact(String contactId) throws RestClientException {
        Contact contact = null;

        String url = apiUrl + CONTACT;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Contact> responseEntity = RestClient.getClient().exchange(url, HttpMethod.GET, requestEntity, Contact.class, contactId);
        contact = responseEntity.getBody();

        return contact;
    }

    public Contact createContact(String firstName, String lastName, String company, List<String> emails, List<String> addresses) throws RestClientException {
        Contact contact = null;

        String url = apiUrl + CONTACTS;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", this.upholdAccessToken.getAccessToken()));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("firstName", firstName);
        body.add("lastName", lastName);
        body.add("company", company);
        for (String email : emails) {
            body.add("emails[]", email);
        }
        for (String address : addresses) {
            body.add("addresses[]", address);
        }
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(body, headers);
        ResponseEntity<Contact> responseEntity = RestClient.getClient().exchange(url, HttpMethod.POST, requestEntity, Contact.class);
        contact = responseEntity.getBody();

        return contact;
    }

    public List<CurrencyPair> getAllTickers() throws RestClientException {
        CurrencyPair[] currencyPairs = null;

        String url = apiUrl + TICKER;
        ResponseEntity<CurrencyPair[]> responseEntity = RestClient.getClient().getForEntity(url, CurrencyPair[].class);
        currencyPairs = responseEntity.getBody();

        return Arrays.asList(currencyPairs);
    }

    public List<CurrencyPair> getTickersForCurrency(String currencyType) throws RestClientException {
        CurrencyPair[] currencyPairs = null;

        String url = apiUrl + TICKER + "/{currencyType}";
        ResponseEntity<CurrencyPair[]> responseEntity = RestClient.getClient().getForEntity(url, CurrencyPair[].class, currencyType);
        currencyPairs = responseEntity.getBody();

        return Arrays.asList(currencyPairs);
    }

    private void setCardIntoOrigin(Card card, Origin origin) {
        origin.setCard(card);
    }

    private void setCardIntoDestination(Card card, Destination destination) {
        destination.setCard(card);
    }

    private boolean matchCard(String cardId, Card card) {
        if (cardId == null || card == null)
            return false;
        return cardId.equals(card.getId());
    }

    public void setCardIntoTransaction(List<Card> cards, Transaction transaction) {
        Origin origin = transaction.getOrigin();
        Destination destination = transaction.getDestination();
        for (Card card : cards) {
            if (matchCard(origin.getCardId(), card)) {
                setCardIntoOrigin(card, origin);
            }
            if (matchCard(destination.getCardId(), card)) {
                setCardIntoDestination(card, destination);
            }
        }
    }

    public void setCardIntoTransactions(List<Card> cards, List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            setCardIntoTransaction(cards, transaction);
        }
    }

}
