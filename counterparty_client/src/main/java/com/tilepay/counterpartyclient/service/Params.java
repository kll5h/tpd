package com.tilepay.counterpartyclient.service;

import java.util.List;

import com.tilepay.counterpartyclient.model.Filter;

public class Params {
    private String method;
    private Params params;
    private List<String> addresses;
    private String address;
    private List<String> assets;
    private String asset;
    private List<Filter> filters;
    private String filterop;

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getAssets() {
        return assets;
    }

    public void setAssets(List<String> assets) {
        this.assets = assets;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public String getFilterop() {
        return filterop;
    }

    public void setFilterop(String filterop) {
        this.filterop = filterop;
    }
    
}
