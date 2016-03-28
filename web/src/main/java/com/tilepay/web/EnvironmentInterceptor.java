package com.tilepay.web;

import java.util.Locale;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.tilepay.core.config.NetworkParams;

public class EnvironmentInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private NetworkParams networkParams;

    @Inject
    private MessageSource messageSource;

    @Resource
    private Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Locale locale = RequestContextUtils.getLocale(request);
        String environment = messageSource.getMessage("running.environment", new String[] { networkParams.getNetworkName() }, locale);
        request.setAttribute("runningEnvironment", environment);
        String version = messageSource.getMessage("running.version", new String[] { env.getRequiredProperty("tilepayVersion") }, locale);
        request.setAttribute("runningVersion", version);
        return super.preHandle(request, response, handler);
    }

}
