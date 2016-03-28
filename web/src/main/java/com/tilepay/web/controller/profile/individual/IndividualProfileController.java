package com.tilepay.web.controller.profile.individual;

import static com.tilepay.core.model.AccountType.INDIVIDUAL;
import static com.tilepay.web.controller.profile.individual.IndividualProfileController.ActiveTab.PRIVATE;
import static com.tilepay.web.controller.profile.individual.IndividualProfileController.ActiveTab.PUBLIC;
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
import com.tilepay.core.model.PrivateDetails;
import com.tilepay.core.service.CountryService;
import com.tilepay.web.service.profile.IndividualContactDetailsService;

@Controller
@RequestMapping("/profile/individual")
public class IndividualProfileController {

    private static final String ACTICVE_TAB = getShortNameAsProperty(ActiveTab.class);

    private static final String VIEW_NAME = "individualProfile";

    private static final String CONTROLLER_URL = "/profile/individual/";

    @Inject
    private CountryService countryService;

    @Inject
    private IndividualContactDetailsService individualContactDetailsService;

    @RequestMapping(method = GET)
    public String index(Model model) {
        if (!model.containsAttribute(ACTICVE_TAB)) {
            model.addAttribute(ACTICVE_TAB, PUBLIC.toString());
        }

        return VIEW_NAME;
    }

    @RequestMapping(method = POST, value = "/public")
    public String updatePublicDetails(@Valid @ModelAttribute PublicDetails publicDetails, BindingResult result, RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute(ACTICVE_TAB, PUBLIC.toString());
            return VIEW_NAME;
        }

        individualContactDetailsService.savePublicDetails(publicDetails);
        redirectAttributes.addFlashAttribute(ACTICVE_TAB, PUBLIC.toString());
        redirectAttributes.addFlashAttribute("publicDetailsSaveSuccess", true);

        return "redirect:" + CONTROLLER_URL;
    }

    @RequestMapping(method = POST, value = "/private")
    public String updatePrivateDetails(@Valid @ModelAttribute PrivateDetails privateDetails, BindingResult result, RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute(ACTICVE_TAB, PRIVATE.toString());
            return VIEW_NAME;
        }

        individualContactDetailsService.savePrivateDetails(privateDetails);
        redirectAttributes.addFlashAttribute(ACTICVE_TAB, PRIVATE.toString());
        redirectAttributes.addFlashAttribute("privateDetailsSaveSuccess", true);

        return "redirect:" + CONTROLLER_URL;
    }

    @ModelAttribute
    private void getModel(Model model) {
        model.addAttribute(countryService.findAll());
        model.addAttribute(individualContactDetailsService.getCurrentPublicDetails());
        model.addAttribute(individualContactDetailsService.getCurrentPrivateDetails());
        model.addAttribute(getShortNameAsProperty(AccountType.class), INDIVIDUAL.toString().toLowerCase());
    }

    enum ActiveTab {
        PUBLIC, PRIVATE
    }
}
