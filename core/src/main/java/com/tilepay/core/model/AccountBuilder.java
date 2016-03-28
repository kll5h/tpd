package com.tilepay.core.model;

public class AccountBuilder {

	private Wallet wallet;
	private AccountType accountType;
	private String passphrase;
	
	public static AccountBuilder anAccount() {
		return new AccountBuilder();
	}

	public AccountBuilder setWallet(Wallet wallet){
		this.wallet = wallet;
		return this;
	}
	
	public AccountBuilder setAccountType(AccountType accountType){
		this.accountType = accountType;
		return this;
	}
	
	public AccountBuilder setPassphrase(String passphrase){
		this.passphrase = passphrase;
		return this;
	}
	
	public Account build() {
		Account account = new Account();
		account.setWallet(wallet);
		account.setAccountType(accountType);
		account.setPassPhrase(passphrase);
		return account;
	}
}
