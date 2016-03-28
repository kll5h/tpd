package com.tilepay.web;

import com.tilepay.core.service.AppDataDirectoryService;

import ch.qos.logback.core.PropertyDefinerBase;

public class LogFileDirPropertyDefiner extends PropertyDefinerBase {

    private AppDataDirectoryService appDataDirectoryService = new AppDataDirectoryService();

    @Override
    public String getPropertyValue() {
        return appDataDirectoryService.getDataDirectory();
    }
}
