package com.tilepay.web;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tilepay.core.service.AppDataDirectoryService;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Inject
    private MessageSource messageSource;

    @Inject
    private AppDataDirectoryService appDataDirectoryService;

    @ExceptionHandler(value = { Exception.class })
    public String defaultErrorHandler(HttpServletRequest req, Exception e) {
        req.setAttribute("technicalError", messageSource.getMessage("8", new Object[] { appDataDirectoryService.getDataDirectory() + "tilepay.log" }, Locale.ENGLISH));
        log.error("", e);
        return "wallet";
    }

}
