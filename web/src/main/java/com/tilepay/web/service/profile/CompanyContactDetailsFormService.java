package com.tilepay.web.service.profile;

import com.tilepay.core.model.CompanyContactDetails;
import com.tilepay.core.service.CompanyContactDetailsService;
import com.tilepay.web.controller.profile.company.CompanyContact;
import com.tilepay.web.controller.profile.company.CompanyPrivateDetails;
import com.tilepay.web.controller.profile.company.CompanyPublicDetails;
import com.tilepay.web.service.SessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class CompanyContactDetailsFormService {

	@Inject
	private CompanyContactDetailsService detailsService;

	@Inject
	private SessionService sessionService;
	
	@Transactional
	public void savePublicDetails(CompanyPublicDetails publicDetails) {
		CompanyContactDetails cd = getCurrentCompanyContactDetails();
		
		cd.setCompanyName(publicDetails.getCompanyName());
		cd.setAddress(publicDetails.getAddress());
		cd.setCity(publicDetails.getCity());
		cd.setState(publicDetails.getState());
		cd.setZip(publicDetails.getZip());
		cd.setCountry(publicDetails.getCountry());
		cd.setPresence(publicDetails.getPresenceType());
		cd.setCaption(publicDetails.getCaption());

		detailsService.save(cd);
	}
	
	public CompanyPublicDetails getPublicDetails() {
		CompanyContactDetails contactDetails = getCurrentCompanyContactDetails();

		CompanyPublicDetails publicDetails = new CompanyPublicDetails();
		publicDetails.setCompanyName(contactDetails.getCompanyName());
		publicDetails.setAddress(contactDetails.getAddress());
		publicDetails.setCity(contactDetails.getCity());
		publicDetails.setState(contactDetails.getState());
		publicDetails.setZip(contactDetails.getZip());
		publicDetails.setCountry(contactDetails.getCountry());
		publicDetails.setPresenceType(contactDetails.getPresence());
		publicDetails.setCaption(contactDetails.getCaption());
		
		return publicDetails;
	}
	
	@Transactional
	public void saveCompanyContact(CompanyContact contact) {
		CompanyContactDetails cd = getCurrentCompanyContactDetails();
		
		cd.setFirstName(contact.getFirstName());
		cd.setLastName(contact.getLastName());
		cd.setPhone(contact.getPhone());
		cd.setEmail(contact.getEmail());
		
		detailsService.save(cd);
	}
	
	public CompanyContact getCompanyContact() {
		CompanyContactDetails contactDetails = getCurrentCompanyContactDetails();
		
		CompanyContact contact = new CompanyContact();
		contact.setFirstName(contactDetails.getFirstName());
		contact.setLastName(contactDetails.getLastName());
		contact.setPhone(contactDetails.getPhone());
		contact.setEmail(contactDetails.getEmail());
		
		return contact;
	}
	
	@Transactional
    public void savePrivateDetails(CompanyPrivateDetails privateDetails) {
        CompanyContactDetails cd = getCurrentCompanyContactDetails();

        cd.setCompanyType(privateDetails.getCompanyType());
        cd.setCompanyCategory(privateDetails.getCompanyCategory());
        cd.setTaxIdType(privateDetails.getTaxIdType());
        cd.setTaxId(privateDetails.getTaxId());
        
        detailsService.save(cd);
    }
	
	public CompanyPrivateDetails getPrivateDetails() {
	    CompanyContactDetails cd = getCurrentCompanyContactDetails();
	    
	    CompanyPrivateDetails companyPrivateDetails = new CompanyPrivateDetails();

	    companyPrivateDetails.setCompanyType(cd.getCompanyType());
	    companyPrivateDetails.setCompanyCategory(cd.getCompanyCategory());
	    companyPrivateDetails.setTaxIdType(cd.getTaxIdType());
	    companyPrivateDetails.setTaxId(cd.getTaxId());
	    
        return companyPrivateDetails;
	}
	
	private CompanyContactDetails getCurrentCompanyContactDetails() {
		return sessionService.getCurrentCompanyAccount().getCompanyContactDetails();
	}

}
