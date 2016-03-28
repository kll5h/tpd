package com.tilepay.core.dto;

public class WalletDTOBuilder {

	private String passphrase;
	private String privateKey;

	public static WalletDTOBuilder aWallet() {
		return new WalletDTOBuilder();
	}

	public WalletDTOBuilder withPassphrase(String passphrase) {
		this.passphrase = passphrase;
		return this;
	}

	public WalletDTOBuilder withPrivateKey(String privateKey) {
		this.privateKey = privateKey;
		return this;
	}

	public WalletDTO build() {
		WalletDTO wallet = new WalletDTO();
		wallet.setPassPhrase(passphrase);
		wallet.setPrivateKey(privateKey);
		return wallet;
	}

}