package com.tilepay.web.service.profile;

import com.tilepay.core.model.ContactDetails;
import com.tilepay.core.model.PrivateDetails;
import com.tilepay.core.service.ContactDetailsService;
import com.tilepay.web.controller.profile.individual.PublicDetails;
import com.tilepay.web.service.SessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class IndividualContactDetailsService {

	@Inject
	private ContactDetailsService contactDetailsService;

	@Inject
	private SessionService sessionService;

	@Transactional
	public void savePublicDetails(PublicDetails publicDetails) {
		ContactDetails cd = getCurrentIndividualContactDetails();
		
		cd.setUserName(publicDetails.getUserName());
		cd.setCaption(publicDetails.getCaption());		

		contactDetailsService.save(cd);
	}
	
	@Transactional
	public void savePrivateDetails(PrivateDetails privateDetails) {
		ContactDetails cd = getCurrentIndividualContactDetails();

		cd.setEmail(privateDetails.getEmail());
		cd.setFirstName(privateDetails.getFirstName());
		cd.setLastName(privateDetails.getLastName());
		cd.setAddress(privateDetails.getAddress());
		cd.setCountry(privateDetails.getCountry());
		cd.setCity(privateDetails.getCity());
		cd.setZip(privateDetails.getZip());
		cd.setDateOfBirth(privateDetails.getDateOfBirth());
		cd.setPhone(privateDetails.getPhone());

		contactDetailsService.save(cd);
	}
	
	public PublicDetails getCurrentPublicDetails() {
		ContactDetails cd = getCurrentIndividualContactDetails();
		
		PublicDetails publicDetails = new PublicDetails();
		publicDetails.setCaption(cd.getCaption());
		publicDetails.setUserName(cd.getUserName());
		return publicDetails;
	}
	
	public PrivateDetails getCurrentPrivateDetails() {
		ContactDetails cd = getCurrentIndividualContactDetails();
		
		PrivateDetails privateDetails = new PrivateDetails();
		privateDetails.setEmail(cd.getEmail());
		privateDetails.setFirstName(cd.getFirstName());
		privateDetails.setLastName(cd.getLastName());
		privateDetails.setAddress(cd.getAddress());
		privateDetails.setCity(cd.getCity());
		privateDetails.setState(cd.getState());
		privateDetails.setZip(cd.getZip());
		privateDetails.setCountry(cd.getCountry());
		privateDetails.setDateOfBirth(cd.getDateOfBirth());
		privateDetails.setPhone(cd.getPhone());
		
		return privateDetails;
	}

	private ContactDetails getCurrentIndividualContactDetails() {
		return sessionService.getCurrentIndividualAccount().getContactDetails();
	}
}
