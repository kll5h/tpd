package com.tilepay.core.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.core.dto.AssetIssuanceDto;
import com.tilepay.core.model.AssetCategory;
import com.tilepay.core.repository.AssetCategoryRepository;

@Service
public class AssetCategoryService {

    @Inject
    private AssetCategoryRepository assetCategoryRepository;

    public void save(AssetCategory assetCategory) {
        assetCategoryRepository.save(assetCategory);
    }

    public void update(AssetCategory assetCategory, Long parentID) {
        AssetCategory ac = assetCategoryRepository.findOne(assetCategory.getId());
        ac.setParent(assetCategoryRepository.findOne(parentID));
        assetCategoryRepository.save(ac);
    }

    public AssetCategory findOne(final Long id) {
        return assetCategoryRepository.findOne(id);
    }

    public List<AssetCategory> findAll() {
        return assetCategoryRepository.findAll();
    }
    
    public void handleAssetCategories(AssetIssuanceDto form){
    	if (form.getCustomAssetCategory() != null) {
            this.save(form.getCustomAssetCategory());
        }
        if (form.getCustomSubAssetCategory() != null) {
            this.save(form.getCustomSubAssetCategory());
        }

        if (form.getCustomSubAssetCategory() != null) {
            this.update(form.getCustomSubAssetCategory(), form.getCustomAssetCategory() == null ? form.getAssetCategory().getId() : form.getCustomAssetCategory().getId());
        }
    }

}
