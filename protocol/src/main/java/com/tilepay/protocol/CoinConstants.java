package com.tilepay.protocol;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.bitcoinj.core.Coin;

public class CoinConstants {

    //TODO: 26.01.2015 Andrei Sljusar: depends on number of chunks with data
    public static String TX_DUST = "0.00000786";

    public static Coin TX_DUST_IN_COINS = Coin.parseCoin(TX_DUST);
    public static BigDecimal TX_DUST_AS_BIG_DECIMAL = new BigDecimal(TX_DUST);

    public static BigInteger MAX_ASSET_QUANTITY = new BigInteger("10000000000");

    public static String ASSET_CREATION_FEE_IN_BTC = "0.00001";
    public static String DEVICE_REGISTRATION_FEE_IN_BTC = "0.0001";
    public static Coin ASSET_CREATION_FEE_IN_COINS = Coin.parseCoin(ASSET_CREATION_FEE_IN_BTC);

    public static final BigDecimal ASSET_CREATION_FEE = new BigDecimal("5000000000");
}
