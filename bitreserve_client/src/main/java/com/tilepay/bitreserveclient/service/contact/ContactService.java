package com.tilepay.bitreserveclient.service.contact;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.reflect.TypeToken;
import com.tilepay.bitreserveclient.exception.BitreserveErrorException;
import com.tilepay.bitreserveclient.model.contact.Contact;
import com.tilepay.bitreserveclient.service.AbstractBitreserveService;
import com.tilepay.bitreserveclient.util.HttpClientUtil;

@Service("bitreserveContactService")
public class ContactService extends AbstractBitreserveService {
    
    private static String CONTACTS_URI_SUFFIX = "v0/me/contacts";

    /**
     * get all contacts for the current user
     * 
     * @param token
     * @return
     * @throws BitreserveErrorException
     */
    public List<Contact> listContacts(String token) throws BitreserveErrorException {
        String URL = bitreserveConfig.getApiUrl() + CONTACTS_URI_SUFFIX;
        String result = sendGetRequest(URL, token);
        List<Contact> contacts = gson.fromJson(result, new TypeToken<List<Contact>>() {
        }.getType());

        return contacts;
    }

    /**
     * get a contact with the contact id
     * 
     * @param id
     * @param token
     * @return
     * @throws BitreserveErrorException
     */
    public Contact getContact(String id, String token) throws BitreserveErrorException {
        String URL = bitreserveConfig.getApiUrl() + CONTACTS_URI_SUFFIX + "/" + id;
        String result = sendGetRequest(URL, token);
        Contact contact = gson.fromJson(result, new TypeToken<Contact>() {
        }.getType());

        return contact;
    }

    /**
     * Create new contact for current user
     * 
     * @param token
     * @param contact
     * @return
     * @throws BitreserveErrorException
     */
    public Contact createContact(String token, Contact contact) throws BitreserveErrorException {
        String URL = bitreserveConfig.getApiUrl() + CONTACTS_URI_SUFFIX;
        Header httpHeader = CreateHeader(token);
        List<NameValuePair> nvList = new ArrayList<NameValuePair>();
        if (!StringUtils.isEmpty(contact.getFirstName())) {
            BasicNameValuePair pair = new BasicNameValuePair("firstName", contact.getFirstName());
            nvList.add(pair);
        }
        if (!StringUtils.isEmpty(contact.getLastName())) {
            BasicNameValuePair pair = new BasicNameValuePair("lastName", contact.getLastName());
            nvList.add(pair);
        }
        if (!StringUtils.isEmpty(contact.getCompany())) {
            BasicNameValuePair pair = new BasicNameValuePair("company", contact.getCompany());
            nvList.add(pair);
        }
        List<String> emails = contact.getEmails();
        if (emails != null && !emails.isEmpty()) {
            for (String email : emails) {
                BasicNameValuePair pair = new BasicNameValuePair("emails[]", email);
                nvList.add(pair);
            }
        }
        List<String> addresses = contact.getAddresses();
        if (addresses != null && !addresses.isEmpty()) {
            for (String address : addresses) {
                BasicNameValuePair pair = new BasicNameValuePair("addresses[]", address);
                nvList.add(pair);
            }
        }

        String json = HttpClientUtil.doPostWithOneHeader(URL, httpHeader, nvList);
        Contact result = gson.fromJson(json, new TypeToken<Contact>() {
        }.getType());
        
        return result;
    }

}