package com.tilepay.core.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptChunk;

public class ResearchBitcoinjService {

    static BitcoinService bitcoinService = new BitcoinService();

    // private static NetworkParameters params = TestNet3Params.get();
    private static NetworkParameters params = MainNetParams.get();

    public static byte[] toByteArray(List<Byte> in) {
        final int n = in.size();
        byte ret[] = new byte[n];
        for (int i = 0; i < n; i++) {
            ret[i] = in.get(i);
        }
        return ret;
    }

    public static void send(String source, String destination) throws Exception {
        WalletAppKit kit = getKit();
        Wallet wallet = kit.wallet();
        Coin balance = wallet.getBalance();
        String currentReceiveAddress = wallet.currentReceiveAddress().toString();

        Address sourceAddress = getAddress(source);
        //DeterministicKey deterministicKey = bitcoinService.getDeterministicKey(wallet, sourceAddress);
        DeterministicKey deterministicKey = null;
        //byte[] pubKey = deterministicKey.getPubKey();

        Transaction tx = new Transaction(params);

        tx.addOutput(Transaction.MIN_NONDUST_OUTPUT, new Address(params, destination));

        //Script scriptToSendAsset = bitcoinService.createScriptToSendAsset(null, pubKey, kit);
        //tx.addOutput(Transaction.MIN_NONDUST_OUTPUT, scriptToSendAsset);
        // addOutput(tx, pubKey);
        // tx.addOutput(Coin.parseCoin("0.04"), new Address(params,
        // destination));

        sendCoins(kit, tx, source);
    }

    private static Address getAddress(String address) throws AddressFormatException {
        return new Address(params, address);
    }

    /*
     * private static Address getSourceAddress() throws AddressFormatException {
     * return new Address(params, "mfv3oz44o2fLaXbXeJzLst59NDRqTzQbNM"); }
     */

    private static void sendCoins(WalletAppKit kit, Transaction tx, String changeAddress) throws Exception {
        Wallet.SendRequest sendRequest = Wallet.SendRequest.forTx(tx);
        sendRequest.ensureMinRequiredFee = false;
        // sendRequest.fee = Coin.parseCoin("-0.00009");
        sendRequest.shuffleOutputs = false;
        sendRequest.changeAddress = getAddress(changeAddress);
        Wallet wallet = kit.wallet();
        Wallet.SendResult sendResult = wallet.sendCoins(sendRequest);

        /*
         * byte[] data =
         * sendResult.tx.getInput(0).getScriptSig().getChunks().get(1).data;
         * byte[] bytes = Utils.sha256hash160(data); Address address = new
         * Address(params, bytes);
         */

        Executor executor = Executors.newSingleThreadExecutor();

        sendResult.broadcastComplete.addListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("Sent coins onwards! Transaction hash is " + sendResult.tx.getHashAsString());
            }
        }, executor);
    }

    public static void main(String[] args) throws Exception {
        // send("1KBWcYtGt8R9u1Vhr3MDHCGd3yFhRDPtNi",
        // "1QEJvxk9nFdJaMcsF27sVorncUcANmnCR9");
        //ResearchBitcoinjService service = new ResearchBitcoinjService();
        //WalletAppKit kit = getKit();
        // String s = bitcoinService.getDeterministicKey(kit.wallet(),
        // "1EEMwLXU4nh2Dm84V4RYpetLjWRSctjF66").getPrivateKeyEncoded(params).toString();
        // System.out.println(s);
        // service.get_sends("1FDpmh9WgLmNqvEHpc3Cuxkd2BbhmxUT1k");
        // service.get_balances("1QEJvxk9nFdJaMcsF27sVorncUcANmnCR9");
        // service.create_send("mfv3oz44o2fLaXbXeJzLst59NDRqTzQbNM",
        // "n3aKEHYsPQiRuU5f56GPuyXvPh55bnHpJc");
        // service.get_balances("1AUbmUmVZ62jxA7Fwju8jexpKH2cLwJTfN");
        // service.get_balances("1KBWcYtGt8R9u1Vhr3MDHCGd3yFhRDPtNi");
        // service.get_balances("n3aKEHYsPQiRuU5f56GPuyXvPh55bnHpJc");
        // Transaction transaction =
        // service.getTransaction("8da682ca4c106fc73f27e202e41bada3fa779f172f741d742f082952630934d0");

        // parseTransaction();

        /*
         * Transaction tx = new Transaction(params, Utils.HEX.decode(
         * "0100000001160742b7bfe4e50d008c8044139696c299a4e7b0950742b49e6c3a50ce1ed749000000001976a914045e2bf3528900dac6b7a31709759db8ff20dddd88acffffffff03781e0000000000001976a914f1f57da6302ad32eecada4b144d532122dea59dd88ac781e000000000000695121030c84eaac2a28aa8a72a099fd4161f4a55cdf5ce0ecbef9f1e05b13b3d08b17af218648502269bf2163f6e35583f65a31c4344314a5f90e186393c2d1083596ae970021ccb09e9306ea0da93094dc491d43c8d16c557131eb4e34c3b286a824d0252ffc2b53ae007df505000000001976a914045e2bf3528900dac6b7a31709759db8ff20dddd88ac00000000"
         * )); System.out.println(tx); sendCoins(getKit(), tx);
         */

        ECKey pubkey = new ECKey();
        byte[] pubKey = new byte[31];

        for (int i = 1; i < pubkey.getPubKey().length - 1; i++) {
            pubKey[i - 1] = pubkey.getPubKey()[i];
        }

        ECKey cpubkey = new ECKey();
        byte[] random_bytes = cpubkey.getPubKeyHash();
        //TODO: 03.12.2014 Andrei Sljusar: Return the digest value as a string of binary data.
        int sign = (random_bytes[0] & 0b1) + 2;
        byte nonce = random_bytes[1];
        nonce++;
        //byte initial_nonce = random_bytes[1];

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(sign);
        outputStream.write(pubKey);
        outputStream.write(nonce % 256);
        byte[] possibly_fully_valid_pubkey = outputStream.toByteArray();
        //cpubkey = new ECKey(null, possibly_fully_valid_pubkey);
    }

    public static List<Byte> scriptToDataArrayList(Script script) {

        List<Byte> dataArrayList = new ArrayList<>();

        List<ScriptChunk> chunks = script.getChunks();
        if (chunks.size() == 2 && chunks.get(0).equalsOpCode(106)) { // OP_RETURN
            for (byte b : chunks.get(1).data) {
                dataArrayList.add(b);
            }
        } else if (chunks.size() >= 5 && chunks.get(0).equalsOpCode(81) && chunks.get(3).equalsOpCode(82) && chunks.get(4).equalsOpCode(174)) { // MULTISIG
            for (int i = 1; i < chunks.get(2).data[0] + 1; i++) {
                dataArrayList.add(chunks.get(2).data[i]);
            }
        }

        return dataArrayList;
    }

    public static String byteListToString(List<Byte> l, Charset charset) {
        if (l == null) {
            return "";
        }
        byte[] array = new byte[l.size()];
        int i = 0;
        for (Byte current : l) {
            array[i] = current;
            i++;
        }
        return new String(array, charset);
    }

    public static long byteListToLongArray(List<Byte> byteList) {
        long value = 0;
        for (int i = 0; i < byteList.size(); i++) {
            value = (value << 8) + (byteList.get(i) & 0xff);
        }
        return value;
    }

    public static WalletAppKit getKit() {
        // WalletAppKit kit = new WalletAppKit(params, new File("."),
        // "TestNet3");
        WalletAppKit kit = new WalletAppKit(params, new File("."), "mainnet");
        kit.startAsync().awaitRunning();
        return kit;
    }

    public void create_send(String source, String destination) throws Exception {
        Wallet wallet = getKit().wallet();
        Address sourceAddress = new Address(params, source);
        DeterministicKey keyFromPubHash = (DeterministicKey) wallet.findKeyFromPubHash(sourceAddress.getHash160());
        byte[] bytes = keyFromPubHash.getPubKey();
        String pubKeyInHex = Utils.HEX.encode(bytes);

        String payload = "{\n" + "   \"method\":\"create_send\",\n" + "   \"params\":{\n" + "      \"source\":\"" + source + "\",\n"
                + "      \"destination\":\"" + destination + "\",\n" + "      \"asset\":\"INTEST\",\n" + "      \"pubkey\":\"" + pubKeyInHex + "\",\n"
                + "      \"quantity\":100000000\n" + "   },\n" + "   \"jsonrpc\":\"2.0\",\n" + "   \"id\":0\n" + "}";

        String response = call(payload);
        System.out.println(response);

    }

    public Transaction getTransaction(String hexString) {
        Wallet wallet = getKit().wallet();
        Transaction transaction = wallet.getTransaction(Sha256Hash.wrap(hexString));
        System.out.println(transaction);

        ScriptChunk scriptChunk = transaction.getOutput(1).getScriptPubKey().getChunks().get(2);

        /*
         * pubkeyhashes = [bitcoin.pubkey_to_pubkeyhash(pubkey) for pubkey in
         * pubkeys] destination, data = '_'.join([str(signatures_required)] +
         * sorted(pubkeyhashes) + [str(len(pubkeyhashes))]), None
         */

        /*
         * byte[] data =
         * sendResult.tx.getInput(0).getScriptSig().getChunks().get(1).data;
         * byte[] bytes = Utils.sha256hash160(data); Address address = new
         * Address(params, bytes);
         */

        //Address address = new Address(params, Utils.sha256hash160(scriptChunk.data));

        return transaction;
    }

    public void get_balances(String address) {
        String payload = "{\n" + "  \"method\": \"get_balances\",\n" + "  \"params\": {\"filters\": {\"field\": \"address\", \"op\": \"==\", \"value\": \""
                + address + "\"}},\n" + "  \"jsonrpc\": \"2.0\",\n" + "  \"id\": 0\n" + "}";
        String response = call(payload);
        System.out.println(response);
    }

    public void get_sends(String address) {
        String payload = "{\n" + "  \"method\": \"get_sends\",\n" + "  \"params\": {\"filters\": {\"field\": \"source\", \"op\": \"==\", \"value\": \""
                + address + "\"}},\n" + "  \"jsonrpc\": \"2.0\",\n" + "  \"id\": 0\n" + "}";
        String response = call(payload);
        System.out.println(response);
    }

    public String call(String payload) {

        final String rpcuser = "rpc";
        // final String rpcpassword = "xcppw1234";
        final String rpcpassword = "1234";

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(rpcuser, rpcpassword.toCharArray());
            }
        });

        String line;
        StringBuffer jsonString = new StringBuffer();
        try {
            // URL url = new URL("http://178.62.240.234:14000/api/");
            URL url = new URL("http://198.50.252.93:4000/api/");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return jsonString.toString();
    }

    public String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
