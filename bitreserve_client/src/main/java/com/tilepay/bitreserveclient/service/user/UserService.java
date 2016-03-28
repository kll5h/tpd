package com.tilepay.bitreserveclient.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.tilepay.bitreserveclient.exception.BitreserveErrorException;
import com.tilepay.bitreserveclient.model.user.Phone;
import com.tilepay.bitreserveclient.model.user.User;
import com.tilepay.bitreserveclient.service.AbstractBitreserveService;

@Service("bitreserveUserService")
public class UserService extends AbstractBitreserveService {

    private static String USER = "v0/me";
    private static String PHONES = "v0/me/phones";

    /**
     * Get User
     * 
     * @param token
     * @return
     * @throws BitreserveErrorException
     */
    public User getUser(String token) throws BitreserveErrorException {
        String URL = bitreserveConfig.getApiUrl() + USER;
        String result = sendGetRequest(URL, token);
        User user = gson.fromJson(result, new TypeToken<User>() {
        }.getType());

        return user;
    }

    /**
     * Get User Phone Numbers
     * 
     * @param token
     * @return
     * @throws BitreserveErrorException
     */
    public List<Phone> getUserPhoneNumbers(String token) throws BitreserveErrorException {
        String URL = bitreserveConfig.getApiUrl() + PHONES;
        String result = sendGetRequest(URL, token);
        List<Phone> userPhoneNumbers = gson.fromJson(result, new TypeToken<Phone>() {
        }.getType());

        return userPhoneNumbers;
    }

}