package com.tilepay.web.controller.profile.individual;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
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

import com.tilepay.core.model.Country;
import com.tilepay.core.model.PrivateDetails;
import com.tilepay.core.service.CountryService;
import com.tilepay.web.service.SessionService;
import com.tilepay.web.service.profile.IndividualContactDetailsService;

@RunWith(MockitoJUnitRunner.class)
public class IndividualProfileControllerTest {

    @Mock
    private SessionService sessionService;
    @Mock
    private CountryService countryService;
    @Mock
    private IndividualContactDetailsService individualService;

    @InjectMocks
    private IndividualProfileController individualProfileController;

    private MockMvc mockMvc;

    private PrivateDetails privateDetails = new PrivateDetails();
    private PublicDetails publicDetails = new PublicDetails();
    private List<Country> countryList = Arrays.asList(new Country(1L, "A"), new Country(2L, "B"));

    @Before
    public void setUp() {
        when(individualService.getCurrentPublicDetails()).thenReturn(publicDetails);
        when(individualService.getCurrentPrivateDetails()).thenReturn(privateDetails);
        when(countryService.findAll()).thenReturn(countryList);
        mockMvc = standaloneSetup(individualProfileController).build();
    }

    @Test
    public void viewNameIsIndividualProfileOnGetRequest() throws Exception {
        ResultActions response = sendGetRequest();
        response.andExpect(view().name("individualProfile"));
    }

    @Test
    public void publicTabIsSelectedOnGetRequest() throws Exception {
        ResultActions response = sendGetRequest();
        response.andExpect(model().attribute("activeTab", "PUBLIC"));
    }

    @Test
    public void countriesListIsPresentOnGetRequest() throws Exception {
        ResultActions response = sendGetRequest();
        response.andExpect(model().attribute("countryList", countryList));
    }

    @Test
    public void publicDetailsArePresentOnGetRequest() throws Exception {
        ResultActions response = sendGetRequest();
        response.andExpect(model().attribute("publicDetails", publicDetails));
    }

    @Test
    public void privateDetailsArePresentOnGetRequest() throws Exception {
        ResultActions response = sendGetRequest();
        response.andExpect(model().attribute("privateDetails", privateDetails));
    }

    private ResultActions sendGetRequest() throws Exception {
        MockHttpServletRequestBuilder request = get("/profile/individual");
        return mockMvc.perform(request);
    }

    @Test
    public void publicDetailsMappingIsCorrectAreSavedIfValid() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/individual/public");
        request.param("userName", "UserName");
        request.param("caption", "I like beer");

        mockMvc.perform(request);

        ArgumentCaptor<PublicDetails> pdCaptor = forClass(PublicDetails.class);
        verify(individualService).savePublicDetails(pdCaptor.capture());

        PublicDetails pd = pdCaptor.getValue();
        assertEquals("UserName", pd.getUserName());
        assertEquals("I like beer", pd.getCaption());
    }

    @Test
    public void privateDetailsMappingIsCorrectAreSavedIfValid() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/individual/private");
        request.param("address", "Downing Street");
        request.param("dateOfBirth", "1988-04-07");

        mockMvc.perform(request);

        ArgumentCaptor<PrivateDetails> pdCaptor = forClass(PrivateDetails.class);
        verify(individualService).savePrivateDetails(pdCaptor.capture());

        PrivateDetails pd = pdCaptor.getValue();
        assertEquals("Downing Street", pd.getAddress());
        assertEquals(new LocalDate(1988, 4, 7).toDate(), pd.getDateOfBirth());
    }

    @Test
    public void privateDetailsAreNotSavedIfDataIsInvalidAndFormIsShownAgain() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/individual/private");
        request.param("dateOfBirth", "AAAA-BB-CC");

        ResultActions response = mockMvc.perform(request);
        response.andExpect(model().attributeHasFieldErrors("privateDetails", "dateOfBirth"));

        response.andExpect(view().name("individualProfile"));
        response.andExpect(model().attributeExists("publicDetails", "countryList"));
        response.andExpect(model().attribute("activeTab", "PRIVATE"));
    }

    @Test
    public void privateDetailsTabIsActiveAfterSavingPrivateDetails() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/individual/private");
        ResultActions response = mockMvc.perform(request);

        response.andExpect(flash().attribute("activeTab", "PRIVATE"));
    }

    @Test
    public void publicDetailsTabIsActiveAfterSavingPublicDetails() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/individual/public");
        ResultActions response = mockMvc.perform(request);

        response.andExpect(flash().attribute("activeTab", "PUBLIC"));
    }

    @Test
    public void successMessageIsShownIfPublicDetailsSavesSuccessfully() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/individual/public");
        ResultActions response = mockMvc.perform(request);

        response.andExpect(flash().attribute("publicDetailsSaveSuccess", true));
    }

    @Test
    public void successMessageIsShownIfPrivateDetailsSavesSuccessfully() throws Exception {
        MockHttpServletRequestBuilder request = post("/profile/individual/private");
        ResultActions response = mockMvc.perform(request);

        response.andExpect(flash().attribute("privateDetailsSaveSuccess", true));
    }

}
