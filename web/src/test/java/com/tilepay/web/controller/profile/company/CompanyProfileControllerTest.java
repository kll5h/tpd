package com.tilepay.web.controller.profile.company;

import static com.tilepay.web.util.StringUtils.stringOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tilepay.core.model.CompanyCategory;
import com.tilepay.core.model.Country;
import com.tilepay.core.model.PresenceType;
import com.tilepay.core.model.TaxIdType;
import com.tilepay.core.service.CountryService;
import com.tilepay.web.service.profile.CompanyContactDetailsFormService;

@RunWith(MockitoJUnitRunner.class)
public class CompanyProfileControllerTest {
    
    @Mock
    private CountryService countryService;
    @Mock
    private CompanyContactDetailsFormService companyContactDetailsService;
    
    @InjectMocks
    private CompanyProfileController companyProfileController;
    
    private MockMvc mockMvc;
    
    private List<Country> countryList;
    private CompanyPublicDetails companyPublicDetails;
    private CompanyPrivateDetails companyPrivateDetails;
    private CompanyContact companyContact;
    
    @Before
    public void setUp() {
        setUpCountriesList();
        setUpPublicDetails();
        setUpPrivateDetails();
        setUpContactDetails();
        
        mockMvc = standaloneSetup(companyProfileController).build();
    }

    @Test
    public void getRequestContainsModelAndReturnsCorrectViewName() throws Exception {
        ResultActions response = mockMvc.perform(get("/profile/company"));
        
        verifyModelThatShouldBePresentInEveryRequest(response);
        response.andExpect(model().attribute("activeTab", "PUBLIC"));
        response.andExpect(view().name("companyProfile"));
    }

    @Test
    public void updatesPublicDetailsCorrectlyAndRedirectsBackToPublicForm() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/company/public");
        request.param("companyName", "WhateverCompanyName");
        request.param("address", "WhateverAddress");
        
        ResultActions response = mockMvc.perform(request);

        ArgumentCaptor<CompanyPublicDetails> pdCaptor = forClass(CompanyPublicDetails.class);
        verify(companyContactDetailsService).savePublicDetails(pdCaptor.capture());

        CompanyPublicDetails cpd = pdCaptor.getValue();
        assertEquals("WhateverCompanyName", cpd.getCompanyName());
        assertEquals("WhateverAddress", cpd.getAddress());
        
        response.andExpect(redirectedUrl("/profile/company"));
        response.andExpect(flash().attribute("activeTab", "PUBLIC"));
        response.andExpect(flash().attribute("publicDetailsSaveSuccess", true));
    }
    
    @Test
    public void showsErrorInFormIfPublicDetailsInputDataInvalid() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/company/public");
        request.param("companyName", stringOf("A", 256));
        
        ResultActions response = mockMvc.perform(request);
        
        response.andExpect(model().attributeHasFieldErrors("companyPublicDetails", "companyName"));
        verifyModelThatShouldBePresentInEveryRequest(response);
    }
    
    @Test
    public void updatesPrivateDetailsCorrectlyAndRedirectsBackToPrivateForm() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/company/private");
        request.param("taxId", "SomeTaxId");
        
        ResultActions response = mockMvc.perform(request);
        
        ArgumentCaptor<CompanyPrivateDetails> pdCaptor = forClass(CompanyPrivateDetails.class);
        verify(companyContactDetailsService).savePrivateDetails(pdCaptor.capture());
        
        CompanyPrivateDetails cpd = pdCaptor.getValue();
        assertEquals("SomeTaxId", cpd.getTaxId());
        
        response.andExpect(redirectedUrl("/profile/company"));
        response.andExpect(flash().attribute("activeTab", "PRIVATE"));
        response.andExpect(flash().attribute("privateDetailsSaveSuccess", true));
    }

    @Test
    public void showsErrorInFormIfPrivateDetailsInputDataInvalid() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/company/private");
        request.param("taxId", stringOf("A", 256));
        
        ResultActions response = mockMvc.perform(request);
        
        response.andExpect(model().attributeHasFieldErrors("companyPrivateDetails", "taxId"));
        verifyModelThatShouldBePresentInEveryRequest(response);
    }
    
    @Test
    public void updatesContactCorrectlyAndRedirectsBackToPublicForm() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/company/contact");
        request.param("firstName", "SomeFirstName");
        
        ResultActions response = mockMvc.perform(request);
        
        ArgumentCaptor<CompanyContact> pdCaptor = forClass(CompanyContact.class);
        verify(companyContactDetailsService).saveCompanyContact(pdCaptor.capture());
        
        CompanyContact cc = pdCaptor.getValue();
        assertEquals("SomeFirstName", cc.getFirstName());

        response.andExpect(redirectedUrl("/profile/company"));
        response.andExpect(flash().attribute("activeTab", "CONTACT"));
        response.andExpect(flash().attribute("contactDetailsSaveSuccess", true));
    }
    
    @Test
    public void showsErrorInFormIfContactInputDataInvalid() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/company/contact");
        request.param("firstName", stringOf("A", 256));
        
        ResultActions response = mockMvc.perform(request);
        
        response.andExpect(model().attributeHasFieldErrors("companyContact", "firstName"));
        verifyModelThatShouldBePresentInEveryRequest(response);
    }
    
    private void verifyModelThatShouldBePresentInEveryRequest(ResultActions response) throws Exception {
        response.andExpect(model().attribute("countryList", countryList));
        response.andExpect(model().attribute("companyPublicDetails", companyPublicDetails));
        response.andExpect(model().attribute("companyPrivateDetails", companyPrivateDetails));
        response.andExpect(model().attribute("companyContact", companyContact));
        response.andExpect(model().attribute("taxIdTypeList", TaxIdType.values()));
        response.andExpect(model().attribute("companyCategoryList", CompanyCategory.values()));
        response.andExpect(model().attribute("presenceTypeList", PresenceType.values()));
    }
    
    private void setUpPublicDetails() {
        companyPublicDetails = new CompanyPublicDetails();
        when(companyContactDetailsService.getPublicDetails()).thenReturn(companyPublicDetails);
    }

    private void setUpPrivateDetails() {
        companyPrivateDetails = new CompanyPrivateDetails();
        when(companyContactDetailsService.getPrivateDetails()).thenReturn(companyPrivateDetails);
    }
    
    private void setUpContactDetails() {
        companyContact = new CompanyContact();
        when(companyContactDetailsService.getCompanyContact()).thenReturn(companyContact);
    }

    private void setUpCountriesList() {
        countryList = new ArrayList<>();
        countryList.add(new Country(1L, "A"));
        countryList.add(new Country(2L, "B"));
        when(countryService.findAll()).thenReturn(countryList);
    }

}
