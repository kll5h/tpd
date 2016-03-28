package com.tilepay.web.uphold.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.async.DeferredResult;

import com.tilepay.core.service.BitcoinService;
import com.tilepay.upholdclient.UpholdClient;
import com.tilepay.upholdclient.UpholdClientFactory;
import com.tilepay.upholdclient.model.DataTableFieldError;
import com.tilepay.upholdclient.model.UpholdJsonModel;
import com.tilepay.upholdclient.model.card.Card;
import com.tilepay.upholdclient.model.contact.Contact;
import com.tilepay.upholdclient.model.currencypair.CurrencyPair;
import com.tilepay.upholdclient.model.transaction.Transaction;
import com.tilepay.upholdclient.model.user.User;
import com.tilepay.web.UpholdSettings;
import com.tilepay.web.uphold.form.UpholdCardForm;
import com.tilepay.web.uphold.form.UpholdContactForm;
import com.tilepay.web.uphold.form.UpholdTransactionForm;

@Controller
@RequestMapping("/uphold")
public class UpholdController {

    private static String UPHOLD_CLIENT = "upholdClient";

    @Inject
    private MessageSource messageSource;

    @Inject
    private BitcoinService bitcoinService;

    @Inject
    private UpholdSettings upholdSettings;

    @Inject
    private UpholdClientFactory upholdClientFactory;

    @RequestMapping(value = "/authorize", method = GET)
    public String authorize(HttpSession session) {
        UpholdClient upholdClient = upholdClientFactory.getUpholdClient(upholdSettings.getClientId(), upholdSettings.getClientSecret(),
                upholdSettings.getScope());
        session.setAttribute(UPHOLD_CLIENT, upholdClient);
        return "redirect:" + upholdClient.getAuthorizeUrl();
    }

    @RequestMapping(value = "/oauth2/callback", method = GET)
    public String callback(HttpServletRequest request, HttpSession session) throws RestClientException {
        String state = request.getParameter("state");
        String code = request.getParameter("code");
        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        upholdClient.requestAccessToken(state, code);

        return "redirect:/wallet";
    }

    @RequestMapping(value = "/getUser", method = GET)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<User>> getUser(HttpSession session) {
        DeferredResult<UpholdJsonModel<User>> deferredResult = new DeferredResult<UpholdJsonModel<User>>();
        UpholdJsonModel<User> upholdJsonModel = new UpholdJsonModel<User>();

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            User remoteUser = upholdClient.getUser();
            upholdJsonModel.setSuccessful(Boolean.TRUE);
            upholdJsonModel.setData(remoteUser);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/listCards", method = GET)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<List<Card>>> listCards(HttpSession session) {
        DeferredResult<UpholdJsonModel<List<Card>>> deferredResult = new DeferredResult<UpholdJsonModel<List<Card>>>();
        UpholdJsonModel<List<Card>> upholdJsonModel = new UpholdJsonModel<List<Card>>();

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            List<Card> remoteCards = upholdClient.listCards();
            upholdJsonModel.setSuccessful(Boolean.TRUE);
            upholdJsonModel.setData(remoteCards);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/createCard", method = POST)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<Card>> createCard(HttpSession session, @Valid Card card, BindingResult bindingResult) {
        DeferredResult<UpholdJsonModel<Card>> deferredResult = new DeferredResult<UpholdJsonModel<Card>>();
        UpholdJsonModel<Card> upholdJsonModel = new UpholdJsonModel<Card>();

        if (bindingResult.hasErrors()) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            List<DataTableFieldError> fieldErrors = new ArrayList<DataTableFieldError>();
            FieldError labelError = bindingResult.getFieldError("label");
            if (labelError != null) {
                fieldErrors.add(new DataTableFieldError(labelError));
            }
            FieldError currencyError = bindingResult.getFieldError("currency");
            if (currencyError != null) {
                fieldErrors.add(new DataTableFieldError(currencyError));
            }
            upholdJsonModel.setFieldErrors(fieldErrors);
            deferredResult.setResult(upholdJsonModel);
            return deferredResult;
        }

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            Card newCard = upholdClient.createCard(card.getLabel(), card.getCurrency());
            upholdJsonModel.setData(newCard);
            upholdJsonModel.setSuccessful(Boolean.TRUE);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/updateCardLabel", method = POST)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<Card>> updateCardLabel(HttpSession session, @Valid UpholdCardForm card, BindingResult bindingResult) {
        DeferredResult<UpholdJsonModel<Card>> deferredResult = new DeferredResult<UpholdJsonModel<Card>>();
        UpholdJsonModel<Card> upholdJsonModel = new UpholdJsonModel<Card>();

        if (bindingResult.hasErrors()) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            List<DataTableFieldError> fieldErrors = new ArrayList<DataTableFieldError>();
            FieldError labelError = bindingResult.getFieldError("label");
            if (labelError != null) {
                fieldErrors.add(new DataTableFieldError(labelError));
                upholdJsonModel.setFieldErrors(fieldErrors);
                deferredResult.setResult(upholdJsonModel);
                return deferredResult;
            }
        }

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            Card remoteCard = upholdClient.getCardDetails(card.getId());
            remoteCard = upholdClient.updateCard(card.getId(), card.getLabel(), remoteCard.getSettings().getPosition(), remoteCard.getSettings().getStarred());
            upholdJsonModel.setData(remoteCard);
            upholdJsonModel.setSuccessful(Boolean.TRUE);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/updateCardStarred", method = POST)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<Card>> updateCardStarred(HttpSession session, @Valid UpholdCardForm card, BindingResult bindingResult) {
        DeferredResult<UpholdJsonModel<Card>> deferredResult = new DeferredResult<UpholdJsonModel<Card>>();
        UpholdJsonModel<Card> upholdJsonModel = new UpholdJsonModel<Card>();

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            Card remoteCard = upholdClient.getCardDetails(card.getId());
            remoteCard = upholdClient.updateCard(card.getId(), remoteCard.getLabel(), remoteCard.getSettings().getPosition(),
                    !remoteCard.getSettings().getStarred());
            upholdJsonModel.setSuccessful(Boolean.TRUE);
            upholdJsonModel.setData(remoteCard);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/listTickers/{currency}", method = GET)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<List<CurrencyPair>>> listTickers(HttpSession session, @PathVariable String currency) {
        DeferredResult<UpholdJsonModel<List<CurrencyPair>>> deferredResult = new DeferredResult<UpholdJsonModel<List<CurrencyPair>>>();
        UpholdJsonModel<List<CurrencyPair>> upholdJsonModel = new UpholdJsonModel<List<CurrencyPair>>();

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            List<CurrencyPair> remoteTickers = null;
            if ("ALL".equals(currency)) {
                remoteTickers = upholdClient.getAllTickers();
            } else {
                remoteTickers = upholdClient.getTickersForCurrency(currency);
            }
            upholdJsonModel.setSuccessful(Boolean.TRUE);
            upholdJsonModel.setData(remoteTickers);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/listContacts", method = GET)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<List<Contact>>> listContacts(HttpSession session) {
        DeferredResult<UpholdJsonModel<List<Contact>>> deferredResult = new DeferredResult<UpholdJsonModel<List<Contact>>>();
        UpholdJsonModel<List<Contact>> upholdJsonModel = new UpholdJsonModel<List<Contact>>();

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            List<Contact> remoteContacts = upholdClient.listContacts();
            upholdJsonModel.setSuccessful(Boolean.TRUE);
            upholdJsonModel.setData(remoteContacts);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/createContact", method = POST)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<Contact>> createContact(HttpSession session, @Valid UpholdContactForm contactForm, BindingResult bindingResult,
            Locale locale) {
        DeferredResult<UpholdJsonModel<Contact>> deferredResult = new DeferredResult<UpholdJsonModel<Contact>>();
        UpholdJsonModel<Contact> upholdJsonModel = new UpholdJsonModel<Contact>();

        if (StringUtils.isEmpty(contactForm.getFirstName()) && StringUtils.isEmpty(contactForm.getLastName())
                && StringUtils.isEmpty(contactForm.getCompany())) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(messageSource.getMessage("upholdContacts.firstNameAndLastNameAndCompany.atLeastOne", null, locale));
            deferredResult.setResult(upholdJsonModel);
            return deferredResult;
        }
        if (bindingResult.hasErrors()) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            List<DataTableFieldError> fieldErrors = new ArrayList<DataTableFieldError>();
            FieldError firstNameError = bindingResult.getFieldError("firstName");
            if (firstNameError != null) {
                fieldErrors.add(new DataTableFieldError(firstNameError));
            }
            FieldError lastNameError = bindingResult.getFieldError("lastName");
            if (lastNameError != null) {
                fieldErrors.add(new DataTableFieldError(lastNameError));
            }
            FieldError companyError = bindingResult.getFieldError("company");
            if (companyError != null) {
                fieldErrors.add(new DataTableFieldError(companyError));
            }
            FieldError email1Error = bindingResult.getFieldError("email1");
            if (email1Error != null) {
                fieldErrors.add(new DataTableFieldError(email1Error));
            }
            FieldError email2Error = bindingResult.getFieldError("email2");
            if (email2Error != null) {
                fieldErrors.add(new DataTableFieldError(email2Error));
            }
            FieldError email3Error = bindingResult.getFieldError("email3");
            if (email3Error != null) {
                fieldErrors.add(new DataTableFieldError(email3Error));
            }
            upholdJsonModel.setFieldErrors(fieldErrors);

            deferredResult.setResult(upholdJsonModel);
            return deferredResult;
        }

        contactForm.setAddress1(StringUtils.trimAllWhitespace(contactForm.getAddress1()));
        contactForm.setAddress2(StringUtils.trimAllWhitespace(contactForm.getAddress2()));
        contactForm.setAddress3(StringUtils.trimAllWhitespace(contactForm.getAddress3()));
        if (!StringUtils.isEmpty(contactForm.getAddress1()) || !StringUtils.isEmpty(contactForm.getAddress2())
                || !StringUtils.isEmpty(contactForm.getAddress3())) {
            List<DataTableFieldError> fieldErrors = new ArrayList<DataTableFieldError>();
            if (!StringUtils.isEmpty(contactForm.getAddress1())) {
                if (!bitcoinService.isValidAddress(contactForm.getAddress1())) {
                    FieldError address1Error = new FieldError(UpholdContactForm.class.getName(), "address1",
                            messageSource.getMessage("bitcoin.address.format.invalid", null, locale));
                    fieldErrors.add(new DataTableFieldError(address1Error));
                }
            }
            if (!StringUtils.isEmpty(contactForm.getAddress2())) {
                if (!bitcoinService.isValidAddress(contactForm.getAddress2())) {
                    FieldError address2Error = new FieldError(UpholdContactForm.class.getName(), "address2",
                            messageSource.getMessage("bitcoin.address.format.invalid", null, locale));
                    fieldErrors.add(new DataTableFieldError(address2Error));
                }
            }
            if (!StringUtils.isEmpty(contactForm.getAddress3())) {
                if (!bitcoinService.isValidAddress(contactForm.getAddress3())) {
                    FieldError address3Error = new FieldError(UpholdContactForm.class.getName(), "address3",
                            messageSource.getMessage("bitcoin.address.format.invalid", null, locale));
                    fieldErrors.add(new DataTableFieldError(address3Error));
                }
            }
            if (!fieldErrors.isEmpty()) {
                upholdJsonModel.setSuccessful(Boolean.FALSE);
                upholdJsonModel.setFieldErrors(fieldErrors);
                deferredResult.setResult(upholdJsonModel);
                return deferredResult;
            }
        }

        Contact newContact = new Contact();
        newContact.setFirstName(contactForm.getFirstName());
        newContact.setLastName(contactForm.getLastName());
        newContact.setCompany(contactForm.getCompany());
        List<String> emails = new ArrayList<String>();
        if (!StringUtils.isEmpty(contactForm.getEmail1())) {
            emails.add(contactForm.getEmail1());
        }
        if (!StringUtils.isEmpty(contactForm.getEmail2())) {
            emails.add(contactForm.getEmail2());
        }
        if (!StringUtils.isEmpty(contactForm.getEmail3())) {
            emails.add(contactForm.getEmail3());
        }
        if (!emails.isEmpty()) {
            newContact.setEmails(emails);
        }
        List<String> addresses = new ArrayList<String>();
        if (!StringUtils.isEmpty(contactForm.getAddress1())) {
            addresses.add(contactForm.getAddress1());
        }
        if (!StringUtils.isEmpty(contactForm.getAddress2())) {
            addresses.add(contactForm.getAddress2());
        }
        if (!StringUtils.isEmpty(contactForm.getAddress3())) {
            addresses.add(contactForm.getAddress3());
        }
        if (!addresses.isEmpty()) {
            newContact.setAddresses(addresses);
        }

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            Contact remoteContact = upholdClient.createContact(contactForm.getFirstName(), contactForm.getLastName(), contactForm.getCompany(), emails,
                    addresses);
            upholdJsonModel.setData(remoteContact);
            upholdJsonModel.setSuccessful(Boolean.TRUE);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/listUserTransactions", method = GET)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<List<Transaction>>> listUserTransactions(HttpSession session) {
        DeferredResult<UpholdJsonModel<List<Transaction>>> deferredResult = new DeferredResult<UpholdJsonModel<List<Transaction>>>();
        UpholdJsonModel<List<Transaction>> upholdJsonModel = new UpholdJsonModel<List<Transaction>>();

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            List<Transaction> remoteTransactions = upholdClient.listUserTransactions();
            List<Card> remoteCards = upholdClient.listCards();
            upholdClient.setCardIntoTransactions(remoteCards, remoteTransactions);
            upholdJsonModel.setSuccessful(Boolean.TRUE);
            upholdJsonModel.setData(remoteTransactions);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/listCardTransactions/{cardId}", method = GET)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<List<Transaction>>> listCardTransactions(HttpSession session, @PathVariable String cardId) {
        DeferredResult<UpholdJsonModel<List<Transaction>>> deferredResult = new DeferredResult<UpholdJsonModel<List<Transaction>>>();
        UpholdJsonModel<List<Transaction>> upholdJsonModel = new UpholdJsonModel<List<Transaction>>();

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            List<Transaction> remoteTransactions = upholdClient.listCardTransactions(cardId);
            List<Card> remoteCards = upholdClient.listCards();
            upholdClient.setCardIntoTransactions(remoteCards, remoteTransactions);
            upholdJsonModel.setSuccessful(Boolean.TRUE);
            upholdJsonModel.setData(remoteTransactions);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/createTransaction", method = POST)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<Transaction>> createTransaction(HttpSession session, @Valid UpholdTransactionForm transactionForm,
            BindingResult bindingResult) {
        DeferredResult<UpholdJsonModel<Transaction>> deferredResult = new DeferredResult<UpholdJsonModel<Transaction>>();
        UpholdJsonModel<Transaction> upholdJsonModel = new UpholdJsonModel<Transaction>();

        if (bindingResult.hasErrors()) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            List<DataTableFieldError> fieldErrors = new ArrayList<DataTableFieldError>();
            FieldError cardIdError = bindingResult.getFieldError("cardId");
            if (cardIdError != null) {
                fieldErrors.add(new DataTableFieldError(cardIdError));
            }

            FieldError destinationTypeError = bindingResult.getFieldError("destinationType");
            if (destinationTypeError != null) {
                fieldErrors.add(new DataTableFieldError(destinationTypeError));
            }

            if ("my cards".equals(transactionForm.getDestinationType())) {
                FieldError destination4CardError = bindingResult.getFieldError("destination4Card");
                if (destination4CardError != null) {
                    fieldErrors.add(new DataTableFieldError(destination4CardError));
                }
            } else if ("my contacts".equals(transactionForm.getDestinationType())) {
                FieldError destination4ContactError = bindingResult.getFieldError("destination4Contact");
                if (destination4ContactError != null) {
                    fieldErrors.add(new DataTableFieldError(destination4ContactError));
                }
            } else if ("address".equals(transactionForm.getDestinationType())) {
                FieldError destination4AddressError = bindingResult.getFieldError("destination4Address");
                if (destination4AddressError != null) {
                    fieldErrors.add(new DataTableFieldError(destination4AddressError));
                }
            }

            FieldError amountError = bindingResult.getFieldError("amount");
            if (amountError != null) {
                fieldErrors.add(new DataTableFieldError(amountError));
            }

            FieldError currencyError = bindingResult.getFieldError("currency");
            if (currencyError != null) {
                fieldErrors.add(new DataTableFieldError(currencyError));
            }

            if (!fieldErrors.isEmpty()) {
                upholdJsonModel.setFieldErrors(fieldErrors);
                deferredResult.setResult(upholdJsonModel);
                return deferredResult;
            }
        }

        String destination = null;
        if ("my cards".equals(transactionForm.getDestinationType())) {
            destination = transactionForm.getDestination4Card();
        } else if ("my contacts".equals(transactionForm.getDestinationType())) {
            destination = transactionForm.getDestination4Contact();
        } else if ("address".equals(transactionForm.getDestinationType())) {
            destination = transactionForm.getDestination4Address();
        }

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            Transaction remoteTransaction = upholdClient.createTransaction(transactionForm.getCardId(), transactionForm.getCurrency(),
                    transactionForm.getAmount(), destination);
            remoteTransaction.setMessage(transactionForm.getMessage());
            upholdJsonModel.setSuccessful(Boolean.TRUE);
            upholdJsonModel.setData(remoteTransaction);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/commitTransaction", method = POST)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<Transaction>> commitTransaction(HttpSession session, @Valid UpholdTransactionForm transactionForm,
            BindingResult bindingResult) {
        DeferredResult<UpholdJsonModel<Transaction>> deferredResult = new DeferredResult<UpholdJsonModel<Transaction>>();
        UpholdJsonModel<Transaction> upholdJsonModel = new UpholdJsonModel<Transaction>();

        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            Transaction remoteTransaction = upholdClient.commitTransaction(transactionForm.getCardId(), transactionForm.getId(), transactionForm.getMessage());
            List<Card> remoteCards = upholdClient.listCards();
            upholdClient.setCardIntoTransaction(remoteCards, remoteTransaction);
            upholdJsonModel.setSuccessful(Boolean.TRUE);
            upholdJsonModel.setData(remoteTransaction);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/resendTransaction", method = POST)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<Transaction>> resendTransaction(HttpSession session, HttpServletRequest request) {
        DeferredResult<UpholdJsonModel<Transaction>> deferredResult = new DeferredResult<UpholdJsonModel<Transaction>>();
        UpholdJsonModel<Transaction> upholdJsonModel = new UpholdJsonModel<Transaction>();

        String transactionId = request.getParameter("data[id]");
        String cardId = request.getParameter("data[origin][card][id]");
        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            Transaction remoteTransaction = upholdClient.resendTransaction(cardId, transactionId);
            upholdJsonModel.setData(remoteTransaction);
            upholdJsonModel.setSuccessful(Boolean.TRUE);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }

    @RequestMapping(value = "/cancelTransaction", method = POST)
    @ResponseBody
    public DeferredResult<UpholdJsonModel<Transaction>> cancelTransaction(HttpSession session, HttpServletRequest request) {
        DeferredResult<UpholdJsonModel<Transaction>> deferredResult = new DeferredResult<UpholdJsonModel<Transaction>>();
        UpholdJsonModel<Transaction> upholdJsonModel = new UpholdJsonModel<Transaction>();

        String transactionId = request.getParameter("data[id]");
        String cardId = request.getParameter("data[origin][card][id]");
        UpholdClient upholdClient = (UpholdClient) session.getAttribute(UPHOLD_CLIENT);
        try {
            Transaction remoteTransaction = upholdClient.cancelTransaction(cardId, transactionId);
            upholdJsonModel.setSuccessful(Boolean.TRUE);
            upholdJsonModel.setData(remoteTransaction);
        } catch (RestClientException e) {
            upholdJsonModel.setSuccessful(Boolean.FALSE);
            upholdJsonModel.setError(e.getMessage());
        }
        deferredResult.setResult(upholdJsonModel);

        return deferredResult;
    }
}
