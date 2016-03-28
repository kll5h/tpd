package com.tilepay.core.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.core.model.CompanyContactDetails;
import com.tilepay.core.repository.CompanyContactDetailsRepository;

@Service
public class CompanyContactDetailsService {

    @Inject
    private CompanyContactDetailsRepository companyContactDetailsRepository;

    public void save(CompanyContactDetails contactDetails) {
        companyContactDetailsRepository.save(contactDetails);
    }

    public CompanyContactDetails findOne(final Long id) {
        return companyContactDetailsRepository.findOne(id);
    }
}
