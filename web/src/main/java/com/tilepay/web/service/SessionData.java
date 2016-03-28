package com.tilepay.web.service;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.tilepay.core.model.Account;
import com.tilepay.core.service.AccountService;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData implements Serializable {

    private static final long serialVersionUID = -7235825252976212942L;
    
    @Inject
    private AccountService accountService;

    public Long getAccountId() {
        Authentication authentication = getContext().getAuthentication();
        if (authentication != null) {
            Account account = accountService.findOneByPassword(authentication.getName());
            if (account != null) {
                return account.getId();
            }
        }
        return null;
    }

}
