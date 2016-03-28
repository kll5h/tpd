package com.tilepay.web.service.profile;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tilepay.core.model.ContactDetails;
import com.tilepay.core.model.Country;
import com.tilepay.core.model.IndividualAccount;
import com.tilepay.core.service.AccountService;
import com.tilepay.core.service.ContactDetailsService;
import com.tilepay.core.model.PrivateDetails;
import com.tilepay.web.controller.profile.individual.PublicDetails;
import com.tilepay.web.service.SessionService;

@RunWith(MockitoJUnitRunner.class)
public class IndividualContactDetailsServiceTest {

	@Mock
	private ContactDetailsService contactDetailsService;
	@Mock
	private SessionService sessionService;
	@Mock
	private AccountService accountService;
		
	@InjectMocks
	private IndividualContactDetailsService detailsService;
	
	private ContactDetails contactDetails = mock(ContactDetails.class);
	
	@Before
	public void setUp() {
		IndividualAccount mockAccount = mock(IndividualAccount.class);
		when(sessionService.getCurrentIndividualAccount()).thenReturn(mockAccount);
		when(mockAccount.getContactDetails()).thenReturn(contactDetails);
	}
	
	@Test
	public void savesPublicDetailsSuccessfully() {
		PublicDetails publicDetails = mock(PublicDetails.class);
		detailsService.savePublicDetails(publicDetails);
		
		verify(contactDetails).setUserName(anyString());
		verify(contactDetails).setCaption(anyString());
		
		verify(contactDetailsService).save(contactDetails);
	}
	
	@Test
	public void savesPrivateDetailsSuccessfully() {
		PrivateDetails privateDetails = mock(PrivateDetails.class);
		detailsService.savePrivateDetails(privateDetails);
		
		verify(contactDetails).setEmail(anyString());
		verify(contactDetails).setFirstName(anyString());
		verify(contactDetails).setLastName(anyString());
		verify(contactDetails).setAddress(anyString());
		verify(contactDetails).setCountry(any(Country.class));
		verify(contactDetails).setCity(anyString());
		verify(contactDetails).setZip(anyString());
		verify(contactDetails).setDateOfBirth(any(Date.class));
		verify(contactDetails).setPhone(anyString());
		
		verify(contactDetailsService).save(contactDetails);
	}
	
	@Test
	public void extractsPublicDetails() {
		when(contactDetails.getCaption()).thenReturn("Caption");
		when(contactDetails.getUserName()).thenReturn("UserName");
		
		PublicDetails publicDetails = detailsService.getCurrentPublicDetails();
		
		assertEquals("Caption", publicDetails.getCaption());
		assertEquals("UserName", publicDetails.getUserName());
	}
	
	@Test
	public void extractsPrivateDetails() {
		when(contactDetails.getEmail()).thenReturn("Email");
		when(contactDetails.getFirstName()).thenReturn("FirstName");
		when(contactDetails.getLastName()).thenReturn("LastName");
		when(contactDetails.getAddress()).thenReturn("Address");
		when(contactDetails.getCity()).thenReturn("City");
		when(contactDetails.getState()).thenReturn("State");
		when(contactDetails.getZip()).thenReturn("Zip");
		Country country = new Country(1L, "Country");
		when(contactDetails.getCountry()).thenReturn(country);
		Date date = new LocalDate(1989, 04, 22).toDate();
		when(contactDetails.getDateOfBirth()).thenReturn(date);
		when(contactDetails.getPhone()).thenReturn("Phone");

		PrivateDetails privateDetails = detailsService.getCurrentPrivateDetails();

		assertEquals("Email", privateDetails.getEmail());
		assertEquals("FirstName", privateDetails.getFirstName());
		assertEquals("LastName", privateDetails.getLastName());
		assertEquals("Address", privateDetails.getAddress());
		assertEquals("City", privateDetails.getCity());
		assertEquals("State", privateDetails.getState());
		assertEquals("Zip", privateDetails.getZip());
		assertEquals(country, privateDetails.getCountry());
		assertEquals(date, privateDetails.getDateOfBirth());
		assertEquals("Phone", privateDetails.getPhone());
	}
}
