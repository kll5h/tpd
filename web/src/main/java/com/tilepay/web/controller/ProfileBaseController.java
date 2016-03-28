package com.tilepay.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tilepay.core.model.Account;
import com.tilepay.core.model.CompanyAccount;
import com.tilepay.core.model.IndividualAccount;
import com.tilepay.web.service.SessionService;

@Controller
@RequestMapping("/profile")
public class ProfileBaseController {

    @Inject
    private SessionService sessionService;

    @RequestMapping(method = GET)
    public String index() {
        Account account = sessionService.getAccount();

        if (account instanceof CompanyAccount) {
            return "redirect:/profile/company/";
        }
        if (account instanceof IndividualAccount) {
            return "redirect:/profile/individual/";
        }
        return "redirect:/";
    }

}
