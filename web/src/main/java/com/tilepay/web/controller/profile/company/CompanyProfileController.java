package com.tilepay.web.controller.profile.company;

import static com.tilepay.core.model.AccountType.COMPANY;
import static com.tilepay.web.controller.profile.company.CompanyProfileController.ActiveTab.CONTACT;
import static com.tilepay.web.controller.profile.company.CompanyProfileController.ActiveTab.PRIVATE;
import static com.tilepay.web.controller.profile.company.CompanyProfileController.ActiveTab.PUBLIC;
import static org.springframework.util.ClassUtils.getShortNameAsProperty;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tilepay.core.model.AccountType;
import com.tilepay.core.model.CompanyCategory;
import com.tilepay.core.model.PresenceType;
import com.tilepay.core.model.TaxIdType;
import com.tilepay.core.service.CountryService;
import com.tilepay.web.service.profile.CompanyContactDetailsFormService;

@Controller
@RequestMapping("/profile/company")
public class CompanyProfileController {

    private static final String ACTICVE_TAB = getShortNameAsProperty(ActiveTab.class);

    private static final String VIEW_NAME = "companyProfile";

    private static final String CONTROLLER_URL = "/profile/company";

    @Inject
    private CountryService countryService;
    @Inject
    private CompanyContactDetailsFormService companyContactDetailsService;

    @RequestMapping(method = GET)
    public String index(Model model) {
        if (!model.containsAttribute(ACTICVE_TAB)) {
            model.addAttribute(ACTICVE_TAB, PUBLIC.toString());
        }

        return VIEW_NAME;
    }

    @RequestMapping(method = POST, value = "/public")
    public String updatePublicDetails(@Valid @ModelAttribute CompanyPublicDetails publicDetails, BindingResult result, RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute(ACTICVE_TAB, PUBLIC.toString());
            return VIEW_NAME;
        }

        companyContactDetailsService.savePublicDetails(publicDetails);
        redirectAttributes.addFlashAttribute(ACTICVE_TAB, PUBLIC.toString());
        redirectAttributes.addFlashAttribute("publicDetailsSaveSuccess", true);
        return "redirect:" + CONTROLLER_URL;
    }

    @RequestMapping(method = POST, value = "/private")
    public String updatePrivateDetails(@Valid @ModelAttribute CompanyPrivateDetails privateDetails, BindingResult result,
            RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            model.addAttribute(ACTICVE_TAB, PRIVATE.toString());
            return VIEW_NAME;
        }

        companyContactDetailsService.savePrivateDetails(privateDetails);
        redirectAttributes.addFlashAttribute(ACTICVE_TAB, PRIVATE.toString());
        redirectAttributes.addFlashAttribute("privateDetailsSaveSuccess", true);

        return "redirect:" + CONTROLLER_URL;
    }

    @RequestMapping(method = POST, value = "/contact")
    public String updateContact(@Valid @ModelAttribute CompanyContact companyContact, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            model.addAttribute(ACTICVE_TAB, CONTACT.toString());
            return VIEW_NAME;
        }

        companyContactDetailsService.saveCompanyContact(companyContact);
        redirectAttributes.addFlashAttribute(ACTICVE_TAB, CONTACT.toString());
        redirectAttributes.addFlashAttribute("contactDetailsSaveSuccess", true);
        return "redirect:" + CONTROLLER_URL;
    }

    @ModelAttribute
    private void getModel(Model model) {
        model.addAttribute(countryService.findAll());
        model.addAttribute(companyContactDetailsService.getPublicDetails());
        model.addAttribute(companyContactDetailsService.getPrivateDetails());
        model.addAttribute(companyContactDetailsService.getCompanyContact());
        model.addAttribute(TaxIdType.values());
        model.addAttribute(CompanyCategory.values());
        model.addAttribute(PresenceType.values());

        // TODO: Vladimir & Gabriel: Used in url, maybe better way?
        model.addAttribute(getShortNameAsProperty(AccountType.class), COMPANY.toString().toLowerCase());
    }

    enum ActiveTab {
        PUBLIC, PRIVATE, CONTACT, PERMISSIONS
    }
}
