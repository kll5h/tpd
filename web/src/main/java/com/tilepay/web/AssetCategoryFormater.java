package com.tilepay.web;

import java.text.ParseException;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.format.Formatter;

import com.tilepay.core.model.AssetCategory;
import com.tilepay.core.service.AssetCategoryService;

public class AssetCategoryFormater implements Formatter<AssetCategory> {

    @Inject
    private AssetCategoryService assetCategoryService;

    @Override
    public AssetCategory parse(String id, Locale locale) throws ParseException {
        try {
            Long.parseLong(id);

            if ("0".equals(id)) {
                AssetCategory assetCategory = new AssetCategory();
                assetCategory.setId(0);
                assetCategory.setName("Custom");
                return assetCategory;
            }

            AssetCategory category = assetCategoryService.findOne(Long.parseLong(id));
            return category;

        } catch (NumberFormatException e) {
            AssetCategory assetCategory = new AssetCategory();
            assetCategory.setName(id);
            return assetCategory;
        }
    }

    @Override
    public String print(AssetCategory object, Locale locale) {
        return object.getName().toString();
    }
}
