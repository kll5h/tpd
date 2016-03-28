package com.tilepay.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.model.Account;
import com.tilepay.core.model.AccountType;
import com.tilepay.core.model.Wallet;
import com.tilepay.core.service.AccountService;
import com.tilepay.core.service.AddressService;
import com.tilepay.core.service.WalletService;

@Controller
public class AccountController {

    // TODO: 22.09.2014 Andrei Sljusar: account web service
    @Inject
    private AccountService accountService;

    @Inject
    private AddressService addressService;

    @Inject
    private WalletService walletService;

    @RequestMapping(value = "/create", method = GET)
    public String create(Model model) {
        model.addAttribute("account", new Account());
        return "loginNewAccount";
    }

    @RequestMapping(value = "/saveNewAccount", method = POST)
    public String saveNewAccount(HttpServletRequest request, @Valid @ModelAttribute("saveNewAccount")Account account) throws IOException {
        Wallet wallet = walletService.createNewWalletForAccount();
        addressService.save(wallet.getAddress());
        if (account == null){
        	account = new Account();
        	account.setPassPhrase(request.getParameter("passPhrase"));
        	account.setPassword(request.getParameter("password"));
        	account.setAccountType(AccountType.valueOf(request.getParameter("accountType")));
        }
        account.setWallet(wallet);
        accountService.createAccount(account);
        walletService.encryptWallet(wallet.getId(), account.getPassword());
    	
        // TODO: 19.10.2014 Andrei Sljusar: loginAfterNewAccountHasBeenCreated
        return "redirect:/login?newAccount";
    }

    @RequestMapping(value = "/createNewAccount", method = GET)
    public String createWallet(Model model) {
        WalletDTO wallet = walletService.createWallet();
    	
        // TODO: 13.11.2014 Andrei Sljusar: ?
        if (wallet == null)
            return "login";
        else {
            Account account = new Account();
            account.setPassPhrase(wallet.getPassPhrase());
            model.addAttribute("account", account);
            return "createNewAccount";
        }

    }

}
