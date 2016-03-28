package com.tilepay.web.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.tilepay.web.AssetCategoryFormater;
import com.tilepay.web.AssetFormater;
import com.tilepay.web.CounterpartyStatusInterceptor;
import com.tilepay.web.CountryFormatter;
import com.tilepay.web.EnvironmentInterceptor;
import com.tilepay.web.HttpSessionStatusInterceptor;
import com.tilepay.web.ThymeleafLayoutInterceptor;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/css/**").addResourceLocations("/static/css/");//.setCachePeriod(31556926);
        registry.addResourceHandler("/static/images/**").addResourceLocations("/static/images/");//.setCachePeriod(31556926);
        registry.addResourceHandler("/static/js/**").addResourceLocations("/static/js/");//.setCachePeriod(31556926);
        registry.addResourceHandler("/static/components/**").addResourceLocations("/static/components/");//.setCachePeriod(31556926);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThymeleafLayoutInterceptor());
        registry.addInterceptor(localeChangeInterceptor());

        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds(0);
        webContentInterceptor.setUseExpiresHeader(true);
        webContentInterceptor.setUseCacheControlHeader(true);
        webContentInterceptor.setUseCacheControlNoStore(true);
        registry.addInterceptor(webContentInterceptor);
        
        registry.addInterceptor(environmentInterceptor());
        registry.addInterceptor(httpSessionStatusInterceptor());
        registry.addInterceptor(counterpartyStatusInterceptor());
        
    }

    @Bean
    public EnvironmentInterceptor environmentInterceptor() {
        return new EnvironmentInterceptor();
    }

    @Bean
    public CounterpartyStatusInterceptor counterpartyStatusInterceptor() {
        return new CounterpartyStatusInterceptor();
    }
    
    @Bean
    public HttpSessionStatusInterceptor httpSessionStatusInterceptor() {
        return new HttpSessionStatusInterceptor();
    }

    @Bean
    public CountryFormatter countryFormatter() {
        return new CountryFormatter();
    }

    @Bean
    public AssetFormater currencyFormatter() {
        return new AssetFormater();
    }

    @Bean
    public AssetCategoryFormater assetCategoryFormatter() {
        return new AssetCategoryFormater();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(countryFormatter());
        registry.addFormatter(currencyFormatter());
        registry.addFormatter(assetCategoryFormatter());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

}
