package com.tilepay.web;


import com.tilepay.core.model.Country;
import com.tilepay.core.service.CountryService;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.Locale;

public class CountryFormatter implements Formatter<Country> {

    @Inject
    private CountryService countryService;

    @Inject
    private MessageSource messageSource;

    @Override
    public Country parse(String id, Locale locale) throws ParseException {
        if ("-1".equals(id)) {
            Country country = new Country();
            country.setId(-1L);
            country.setName(messageSource.getMessage("companyProfile.countrySelect", null, locale));
            return country;
        }
        return countryService.findOne(Long.parseLong(id));
    }

    @Override
    public String print(Country object, Locale locale) {
        return object.getId().toString();
    }
}
