package com.tilepay.bitreserveclient.service.ticker;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.tilepay.bitreserveclient.exception.BitreserveErrorException;
import com.tilepay.bitreserveclient.model.currencypair.CurrencyPair;
import com.tilepay.bitreserveclient.service.AbstractBitreserveService;
import com.tilepay.bitreserveclient.util.HttpClientUtil;

@Service("bitreserveTickerService")
public class TickerService extends AbstractBitreserveService {

    private static final String TICKER = "v0/ticker";

    /**
     * Get Tickers for Currency
     * 
     * @return
     * @throws BitreserveErrorException
     */
    public List<CurrencyPair> getAllTickers() throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + TICKER;

        String json = HttpClientUtil.doGet(url);
        List<CurrencyPair> tickers = gson.fromJson(json, new TypeToken<List<CurrencyPair>>() {
        }.getType());

        return tickers;
    }

    /**
     * Get Tickers for Currency
     * 
     * @param currencyType
     * @return
     * @throws BitreserveErrorException
     */
    public List<CurrencyPair> getTickersForCurrency(String currencyType) throws BitreserveErrorException {
        String url = bitreserveConfig.getApiUrl() + TICKER + "/" + currencyType;

        String json = HttpClientUtil.doGet(url);
        List<CurrencyPair> tickers = gson.fromJson(json, new TypeToken<List<CurrencyPair>>() {
        }.getType());

        return tickers;
    }

}