package com.tilepay.daemon.service;

import static org.junit.Assert.assertEquals;

import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.IssuanceStatus;

public class IssuanceAsserter {

    public static void assertIssuance(Issuance issuance, IssuanceStatus issuanceStatus) {
        assertEquals(issuanceStatus, issuance.getStatus());
    }
}
