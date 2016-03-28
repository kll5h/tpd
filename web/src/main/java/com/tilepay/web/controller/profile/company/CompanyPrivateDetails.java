package com.tilepay.web.controller.profile.company;

import com.tilepay.core.model.CompanyCategory;
import com.tilepay.core.model.TaxIdType;

import javax.validation.constraints.Size;

import static com.tilepay.core.constant.FieldSize.MAX_STRING;
import static com.tilepay.core.constant.FieldSize.MAX_TAXID;

public class CompanyPrivateDetails {

    @Size(max = MAX_STRING)
    private String companyType;
    
    private CompanyCategory companyCategory;
    
    private TaxIdType taxIdType;
    
    @Size(max = MAX_TAXID)
    private String taxId;

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public CompanyCategory getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(CompanyCategory companyCantegory) {
        this.companyCategory = companyCantegory;
    }

    public TaxIdType getTaxIdType() {
        return taxIdType;
    }

    public void setTaxIdType(TaxIdType taxIdType) {
        this.taxIdType = taxIdType;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

}
