package com.tilepay.core.service;

import com.tilepay.core.model.Country;
import com.tilepay.core.repository.CountryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CountryService {

	@Inject
	private CountryRepository countryRepository;
	
	public void save(Country country) {
		countryRepository.save(country);
	  }
	
	public Country findOne(final Long id){
		return countryRepository.findOne(id);
	}
	
	public List<Country> findAll(){
		return countryRepository.findAll();
	}
	
}
