package com.tilepay.web.service.profile;

import static com.tilepay.core.model.CompanyCategory.ARTS;
import static com.tilepay.core.model.PresenceType.ONLINE;
import static com.tilepay.core.model.TaxIdType.EIN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tilepay.core.model.CompanyAccount;
import com.tilepay.core.model.CompanyContactDetails;
import com.tilepay.core.model.Country;
import com.tilepay.core.service.CompanyContactDetailsService;
import com.tilepay.web.controller.profile.company.CompanyContact;
import com.tilepay.web.controller.profile.company.CompanyPrivateDetails;
import com.tilepay.web.controller.profile.company.CompanyPublicDetails;
import com.tilepay.web.service.SessionService;

@RunWith(MockitoJUnitRunner.class)
public class CompanyContactDetailsFormServiceTest {

    @Mock
    private CompanyContactDetailsService detailsService;
    @Mock
    private SessionService sessionService;

    @InjectMocks
    private CompanyContactDetailsFormService formService;

    private CompanyContactDetails contactDetails = mock(CompanyContactDetails.class);

    @Before
    public void setUp() {
        setUpCompanyContactDetails();
    }

    @Test
    public void savesPublicDetailsSuccessfully() {
        CompanyPublicDetails publicDetails = mock(CompanyPublicDetails.class);

        when(publicDetails.getCompanyName()).thenReturn("CompanyName");
        when(publicDetails.getAddress()).thenReturn("Address");
        when(publicDetails.getCity()).thenReturn("City");
        when(publicDetails.getState()).thenReturn("State");
        when(publicDetails.getZip()).thenReturn("Zip");
        Country country = new Country(1L, "Country");
        when(publicDetails.getCountry()).thenReturn(country);
        when(publicDetails.getPresenceType()).thenReturn(ONLINE);
        when(publicDetails.getCaption()).thenReturn("Caption");

        formService.savePublicDetails(publicDetails);

        verify(contactDetails).setCompanyName(eq("CompanyName"));
        verify(contactDetails).setAddress(eq("Address"));
        verify(contactDetails).setCity(eq("City"));
        verify(contactDetails).setState(eq("State"));
        verify(contactDetails).setZip(eq("Zip"));
        verify(contactDetails).setCountry(eq(country));
        verify(contactDetails).setPresence(eq(ONLINE));
        verify(contactDetails).setCaption(eq("Caption"));

        verify(detailsService).save(contactDetails);
    }

    @Test
    public void extractsPublicDetailsCorrectly() {
        when(contactDetails.getCompanyName()).thenReturn("CompanyName");
        when(contactDetails.getAddress()).thenReturn("Address");
        when(contactDetails.getCity()).thenReturn("City");
        when(contactDetails.getState()).thenReturn("State");
        when(contactDetails.getZip()).thenReturn("Zip");
        Country country = new Country(1L, "Country");
        when(contactDetails.getCountry()).thenReturn(country);
        when(contactDetails.getPresence()).thenReturn(ONLINE);
        when(contactDetails.getCaption()).thenReturn("Caption");

        CompanyPublicDetails publicDetails = formService.getPublicDetails();

        assertEquals("CompanyName", publicDetails.getCompanyName());
        assertEquals("Address", publicDetails.getAddress());
        assertEquals("City", publicDetails.getCity());
        assertEquals("State", publicDetails.getState());
        assertEquals("Zip", publicDetails.getZip());
        assertEquals(country, publicDetails.getCountry());
        assertEquals(ONLINE, publicDetails.getPresenceType());
        assertEquals("Caption", publicDetails.getCaption());
    }

    @Test
    public void savesContactSuccessfully() {
        CompanyContact companyContact = mock(CompanyContact.class);

        when(companyContact.getFirstName()).thenReturn("FirstName");
        when(companyContact.getLastName()).thenReturn("LastName");
        when(companyContact.getPhone()).thenReturn("Phone");
        when(companyContact.getEmail()).thenReturn("Email");

        formService.saveCompanyContact(companyContact);

        verify(contactDetails).setFirstName(eq("FirstName"));
        verify(contactDetails).setLastName(eq("LastName"));
        verify(contactDetails).setPhone(eq("Phone"));
        verify(contactDetails).setEmail(eq("Email"));

        verify(detailsService).save(contactDetails);
    }

    @Test
    public void extractsContactCorrectly() {
        when(contactDetails.getFirstName()).thenReturn("FirstName");
        when(contactDetails.getLastName()).thenReturn("LastName");
        when(contactDetails.getPhone()).thenReturn("Phone");
        when(contactDetails.getEmail()).thenReturn("Email");

        CompanyContact companyContact = formService.getCompanyContact();

        assertEquals("FirstName", companyContact.getFirstName());
        assertEquals("LastName", companyContact.getLastName());
        assertEquals("Phone", companyContact.getPhone());
        assertEquals("Email", companyContact.getEmail());
    }

    @Test
    public void savesPrivateDetailsCorrectly() {
        CompanyPrivateDetails privateDetails = mock(CompanyPrivateDetails.class);

        when(privateDetails.getCompanyType()).thenReturn("CompanyType");
        when(privateDetails.getCompanyCategory()).thenReturn(ARTS);
        when(privateDetails.getTaxIdType()).thenReturn(EIN);
        when(privateDetails.getTaxId()).thenReturn("TaxId");

        formService.savePrivateDetails(privateDetails);

        verify(contactDetails).setCompanyType("CompanyType");
        verify(contactDetails).setCompanyCategory(ARTS);
        verify(contactDetails).setTaxIdType(EIN);
        verify(contactDetails).setTaxId("TaxId");

        verify(detailsService).save(contactDetails);
    }

    @Test
    public void extractsPrivateDetails() {
        when(contactDetails.getCompanyType()).thenReturn("CompanyType");
        when(contactDetails.getCompanyCategory()).thenReturn(ARTS);
        when(contactDetails.getTaxIdType()).thenReturn(EIN);
        when(contactDetails.getTaxId()).thenReturn("TaxId");

        CompanyPrivateDetails privateDetails = formService.getPrivateDetails();

        assertEquals("CompanyType", privateDetails.getCompanyType());
        assertEquals(ARTS, privateDetails.getCompanyCategory());
        assertEquals(EIN, privateDetails.getTaxIdType());
        assertEquals("TaxId", privateDetails.getTaxId());
    }

    private void setUpCompanyContactDetails() {
        CompanyAccount mockAccount = mock(CompanyAccount.class);
        when(sessionService.getCurrentCompanyAccount()).thenReturn(mockAccount);
        when(mockAccount.getCompanyContactDetails()).thenReturn(contactDetails);
    }
}
