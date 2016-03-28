package com.tilepay.core.service;

import com.tilepay.core.model.ContactDetails;
import com.tilepay.core.repository.ContactDetailsRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ContactDetailsService {

	@Inject
	private ContactDetailsRepository contactDetailsRepository;
	
	public void save(ContactDetails contactDetails) {
	    contactDetailsRepository.save(contactDetails);
	  }
	
	public ContactDetails findOne(final Long id){
		return contactDetailsRepository.findOne(id);
	}
	
}
