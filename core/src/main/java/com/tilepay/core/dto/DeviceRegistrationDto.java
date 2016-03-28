package com.tilepay.core.dto;


public class DeviceRegistrationDto {

    private String name;

    private String address;
    
    private String BTCaddress;
    
    private String IPAddress;
    
    private String registrationToken;
    
    private String password;
    
    private String TxHash;
    
    private Long walletId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBTCaddress() {
        return BTCaddress;
    }

    public void setBTCaddress(String bTCaddress) {
        BTCaddress = bTCaddress;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String iPAddress) {
        IPAddress = iPAddress;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTxHash() {
        return TxHash;
    }

    public void setTxHash(String txHash) {
        TxHash = txHash;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

}
