package com.tilepay.web.controller;

import com.tilepay.core.dto.ResponseDTO;
import com.tilepay.web.service.PrivateKeyWebService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.IOException;

@RestController
@RequestMapping("/wallet")
public class PrivateKeyController {
	
	@Inject
	PrivateKeyWebService privateKeyWebService;
	
    @RequestMapping(value = "/confirm/{password}/{address}", method = RequestMethod.GET)
    public ResponseDTO confirmPassword(@PathVariable String password, @PathVariable String address) throws IOException {
    	return privateKeyWebService.confirmPassword(password, address);
    }
}
