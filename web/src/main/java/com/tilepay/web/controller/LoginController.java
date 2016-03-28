package com.tilepay.web.controller;

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tilepay.core.service.WalletAppKitHolder;

@Controller
public class LoginController {

    @Inject
    private WalletAppKitHolder walletAppKitHolder;

    @Inject
    private MessageSource messageSource;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Locale locale, Model model, @RequestParam(required = false) String newAccount) {
        // TODO: 19.10.2014 Andrei Sljusar: account?
        // model.addAttribute("account", new Account());

        if (walletAppKitHolder.isStarting()) {
            model.addAttribute("downloading_msg", messageSource.getMessage("login.blockchain.downloading", null, locale));
            return "login";
        }

        // TODO: 19.10.2014 Andrei Sljusar: what does test mean?
        model.addAttribute("test", null);

        if (newAccount != null) {
            model.addAttribute("success_msg", messageSource.getMessage("login.accountCreated", null, locale));
            model.addAttribute("test", "value");
        }
        return "login";
    }
}
