package com.tilepay.core.repository;

import com.tilepay.core.model.ContactDetails;
import com.tilepay.core.service.CountryService;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

public class ContactDetailsRepositoryTest extends AbstractRepositoryTest {

    @Inject
    private ContactDetailsRepository contactDetailsRepository;

    @Inject
    private CountryService countryService;
    
    @Test
    public void findAll() {
        contactDetailsRepository.findAll();
    }

    @Test
    public void save() {
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setAddress("Street");
        contactDetails.setCaption("A caption");
        contactDetails.setCity("city");
        contactDetails.setCountry(countryService.findOne(1L));
        contactDetails.setFirstName("John");
        contactDetails.setLastName("Woo");
        contactDetails.setUserName("johnwoo");
        contactDetails.setPhone("1234567");
        contactDetails.setState("state");
        contactDetails.setZip("55124");
        contactDetails.setEmail("john@w.oo");
     
        contactDetailsRepository.save(contactDetails);
        
        ContactDetails actualContactDetails = contactDetailsRepository.findOne(contactDetails.getId());
        assertEquals(contactDetails.getId(), actualContactDetails.getId());
        assertEquals("johnwoo", actualContactDetails.getUserName());
        
        contactDetailsRepository.delete(contactDetails);
        
        assertEquals(null, contactDetailsRepository.findOne(contactDetails.getId()));
    }


}