package com.tilepay.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tilepay.counterpartyclient.model.CounterpartyStatus;
import com.tilepay.counterpartyclient.service.CounterpartyService;

public class CounterpartyStatusInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private CounterpartyService counterpartyService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("/wallet".equals(request.getServletPath())) {
            CounterpartyStatus counterpartyStatus = counterpartyService.getStatus();
            request.setAttribute("counterpartyStatus", counterpartyStatus);
        }
        return super.preHandle(request, response, handler);
    }

}