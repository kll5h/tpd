package com.tilepay.core.repository;

import com.tilepay.core.model.CompanyCategory;
import com.tilepay.core.model.CompanyContactDetails;
import com.tilepay.core.model.Country;
import com.tilepay.core.model.TaxIdType;
import com.tilepay.core.service.CountryService;
import org.junit.Test;

import javax.inject.Inject;

import static com.tilepay.core.model.PresenceType.ONLINE;
import static org.junit.Assert.assertEquals;

public class CompanyContactDetailsRepositoryTest extends AbstractRepositoryTest {

    @Inject
    private CompanyContactDetailsRepository companyContactDetailsRepository;
    
    @Inject
    private CountryService countryService;

    @Test
    public void findAll() {
    	companyContactDetailsRepository.findAll();
    }

    @Test
    public void save() {
    	CompanyContactDetails companyContactDetails = new CompanyContactDetails();
    	companyContactDetails.setAddress("address");
    	companyContactDetails.setCaption("caption");
    	companyContactDetails.setCity("city");
    	companyContactDetails.setCompanyCategory(CompanyCategory.ARTS);
    	companyContactDetails.setCompanyName("companyName");
    	companyContactDetails.setTaxId("taxID");
    	companyContactDetails.setTaxIdType(TaxIdType.DUNS);
    	companyContactDetails.setCompanyType("Company Type");
    	Country country = countryService.findOne(1L);
    	companyContactDetails.setCountry(country);
    	companyContactDetails.setEmail("e@ma.il");
    	companyContactDetails.setFirstName("firstname");
    	companyContactDetails.setLastName("lastname");
    	companyContactDetails.setMerchantId("merchantId");
    	companyContactDetails.setPhone("12345678");
    	companyContactDetails.setPresence(ONLINE);
    	companyContactDetails.setState("state");
    	companyContactDetails.setZip("zip");
    	
    	companyContactDetailsRepository.save(companyContactDetails);
        
    	CompanyContactDetails actualCompanyContactDetails = companyContactDetailsRepository.findOne(companyContactDetails.getId());
        assertEquals(companyContactDetails.getId(), actualCompanyContactDetails.getId());
        assertEquals("companyName", actualCompanyContactDetails.getCompanyName());
        
        companyContactDetailsRepository.delete(companyContactDetails);
        
        assertEquals(null, companyContactDetailsRepository.findOne(companyContactDetails.getId()));
    }


}