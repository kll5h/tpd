package com.tilepay.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.tilepay.domain.entity.Asset;

public class AssetFormater implements Formatter<Asset> {

    @Override
    public Asset parse(String id, Locale locale) throws ParseException {
        Asset currency = new Asset();
        currency.setName(id);
        if (id.equalsIgnoreCase("BTC")) {
            currency.setMinersFee(Asset.BTC_MINERS_FEE);
        } else {
            currency.setMinersFee(Asset.CUSTOM_CURRENCIES_MINERS_FEE);
        }
        return currency;
    }

    @Override
    public String print(Asset object, Locale locale) {
        String name = object.getName();
        return name == null ? "" : name;
    }
}
