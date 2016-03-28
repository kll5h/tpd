package com.tilepay.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.tilepay.core.config.MainNetParamsConfig;
import com.tilepay.core.config.NetworkParams;
import com.tilepay.core.dto.AssetIssuanceDto;
import com.tilepay.core.dto.BTCTransactionDto;
import com.tilepay.core.dto.DeviceRegistrationDto;
import com.tilepay.core.dto.ResponseDTO;
import com.tilepay.core.dto.TransactionDto;
import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.model.Account;
import com.tilepay.core.model.Device;
import com.tilepay.core.service.AssetCreationResult;
import com.tilepay.core.service.BitcoinService;
import com.tilepay.core.service.DeviceRestClient;
import com.tilepay.core.service.DeviceService;
import com.tilepay.core.service.WalletService;
import com.tilepay.core.service.balance.BalanceService;
import com.tilepay.core.service.balance.TilecoinRestClient;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.AssetBuilder;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.Transaction;
import com.tilepay.protocol.config.NetworkParametersConfig;
import com.tilepay.web.service.AssetIssuanceValidator;
import com.tilepay.web.service.DeviceRegistrationValidator;
import com.tilepay.web.service.PasswordValidator;
import com.tilepay.web.service.SessionService;
import com.tilepay.web.service.WalletWebService;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.validation.Valid;
import org.bitcoinj.core.Wallet.SendResult;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    @Inject
    private SessionService sessionService;

    @Inject
    private BitcoinService bitcoinService;

    @Inject
    private WalletWebService walletWebService;

    @Inject
    private BalanceService balanceService;

    @Inject
    private MessageSource messageSource;

    @Inject
    private AssetIssuanceValidator assetIssuanceValidator;

    @Inject
    private PasswordValidator passwordValidator;

    @Inject
    private DeviceRegistrationValidator deviceRegistrationValidator;

    @Inject
    private NetworkParams networkParams;

    @Inject
    private DeviceService deviceService;

    @Inject
    private WalletService walletService;

    @Inject
    private DeviceRestClient deviceRestClient;

    @Inject
    private TilecoinRestClient tilecoinRestClient;
    
    @Inject
    private NetworkParametersConfig networkParametersConfig;

    @RequestMapping(method = GET)
    public String index(Model model) {
        prepareModel(model);
        return "wallet";
    }

    @RequestMapping(value = "/payFee", method = POST)
    public String prefilledFeeTransaction(Model model, @ModelAttribute("transactionForm") TransactionDto form) {
        prepareModel(model);
        TransactionDto prefilledForm= newTransactionForm();
        prefilledForm.setAddressFrom(getAccount(model).getWallet().getAddress().getAddress());
        prefilledForm.setAddressTo(networkParametersConfig.getFeeAddress());
        prefilledForm.setAmount("50");
        Asset asset = AssetBuilder.anAsset().setCntrprtyProtocol().setName("TILECOINXTC").setDivisible(false).build();
        prefilledForm.setAsset(asset);
        prefilledForm.setMinersFee("0.00001");
        model.addAttribute("transactionForm", prefilledForm);
        return "wallet";
    }
    
    @RequestMapping(method = POST)
    public String prefilledTransaction(Model model, @ModelAttribute("transactionForm") TransactionDto form) {
        prepareModel(model);
        return "wallet";
    }

    @RequestMapping(value = "/confirm", method = POST)
    public String confirmTransaction(@Valid @ModelAttribute("transactionForm") TransactionDto form, BindingResult result, Model model) {
        prepareModel(model);

        WalletDTO wallet = getWalletDTO(model);
        walletWebService.confirm(wallet, form, result);

        if (result.hasErrors()) {
            return "wallet";
        }

        model.addAttribute("confirming", true);
        return "wallet";
    }

    @RequestMapping(value = "/send", method = POST)
    @ResponseBody
    public DeferredResult<ResponseDTO> sendTransaction(@Valid @ModelAttribute("transactionForm") TransactionDto form, Model model, Locale locale) {
        prepareModel(model);
        Account account = getAccount(model);

        DeferredResult<ResponseDTO> deferredResult = new DeferredResult<>();
        ResponseDTO response = new ResponseDTO();
        deferredResult.setResult(response);

        if (form.getPassword().isEmpty()) {
            response.setMessage(messageSource.getMessage("empty.password", null, locale));
            response.setSuccessful(false);
            return deferredResult;
        }

        if (passwordValidator.isPasswordCorrect(account.getWallet().getId(), form.getPassword())) {
            walletWebService.send(form, response);
        } else {
            response.setMessage(messageSource.getMessage("incorrect.password", null, locale));
            response.setSuccessful(false);
        }

        return deferredResult;
    }

    @RequestMapping(value = "/assetCreate", method = POST)
    public String createAsset(@Valid @ModelAttribute("assetIssuanceForm") AssetIssuanceDto form, BindingResult result, Model model, Locale locale) {

        // TODO: 06.01.2015 Andrei Sljusar: move to Interceptor. // NotImplementedInMainnetException?
        if (networkParams instanceof MainNetParamsConfig) {
            throw new RuntimeException("TODO: Not implemented in mainnet");
        }

        prepareModel(model);

        WalletDTO wallet = getWalletDTO(model);
        assetIssuanceValidator.validate(form, wallet, result);

        if (result.hasErrors()) {
            return "wallet";
        }

        AssetCreationResult assetCreationResult = walletWebService.createAssetWithBalance(form);

        if (assetCreationResult.feeTxSendResult == null) {
            model.addAttribute("feeTxFailureAlert", messageSource.getMessage("4", new Object[] { form.getAssetName() }, locale));
        } else {
            model.addAttribute("feeTxSuccessAlert", messageSource.getMessage("5", new Object[] { assetCreationResult.feeTxSendResult.tx.getHashAsString() }, locale));
        }

        model.addAttribute("createAssetTxSuccessAlert",
                messageSource.getMessage("6", new Object[] { form.getAssetName(), assetCreationResult.assetCreationTxSendResult.tx.getHashAsString() }, locale));
        model.addAttribute("assetCreationSummary", true);
        return "wallet";
    }

    @RequestMapping(value = "/registerDevice", method = POST)
    public String registerDevice(@Valid @ModelAttribute("deviceRegistrationForm") DeviceRegistrationDto form, BindingResult result, Model model) {

        prepareModel(model);

        WalletDTO wallet = getWalletDTO(model);
        deviceRegistrationValidator.validate(form, wallet, result);

        if (result.hasErrors()) {
            return "wallet";
        }

        //request token
        Account account = getAccount(model);
        form.setBTCaddress(walletService.addAddressToWallet(account.getId()));

        String regToken = deviceRestClient.getRegistrationToken(form.getIPAddress());
        if (regToken != null) {
            form.setRegistrationToken(String.format("%040x", new BigInteger(1, regToken.getBytes())));
        }
        form.setIPAddress(form.getIPAddress());
        form.setWalletId(account.getWallet().getId());

        //if device pwd is correct
        if (regToken != null) {
            ResponseDTO response = new ResponseDTO();
            SendResult sendResult = walletWebService.sendOPReturnTx(form, regToken, response);

            form.setTxHash(sendResult.tx.getHash().toString());

            deviceService.save(form);
        }

        model.addAttribute("deviceRegistration", true);
        return "wallet";
    }

    private void prepareModel(Model model) {

        Account account = sessionService.getAccount();
        model.addAttribute("account", account);

        WalletDTO wallet = walletService.loadWallet(account.getId(), null);
        model.addAttribute("wallet", wallet);

        List<Balance> balances = balanceService.getBalances(account.getWallet().getAddress().getAddress());
        model.addAttribute("balances", balances);

        List<Asset> assets = balanceService.getAssets(balances, wallet.getBalanceAvailable());
        model.addAttribute("assets", assets);
        
        List<Device> devices = deviceService.getDeviceList(account.getWallet().getId());
        model.addAttribute("devices", devices);

        List<Transaction> issuanceTransactions = tilecoinRestClient.getIssuanceTransactions(account.getWallet().getAddress().getAddress());
        model.addAttribute("issuanceTransactions", issuanceTransactions);
        
        List<BTCTransactionDto> transactions = bitcoinService.convertTransactionsToBTCTransactionsDTO(account.getWallet().getId());//TA3-BUG-MAJOR Login-Download of Blockchain 
        model.addAttribute("transactions", transactions);
        
        model.addAttribute("network", networkParams.getNetworkName());
        model.addAttribute("blockExplorerUrl", networkParams.getBlockExplorerUrl());
        
    }

    @ModelAttribute("transactionForm")
    public TransactionDto newTransactionForm() {
        return new TransactionDto();
    }

    @ModelAttribute("assetIssuanceForm")
    public AssetIssuanceDto newIssuanceForm() {
        return new AssetIssuanceDto();
    }

    @ModelAttribute("deviceRegistrationForm")
    public DeviceRegistrationDto newDeviceRegistrationForm() {
        return new DeviceRegistrationDto();
    }

    private WalletDTO getWalletDTO(Model model) {
        return (WalletDTO) model.asMap().get("wallet");
    }

    private Account getAccount(Model model) {
        return (Account) model.asMap().get("account");
    }

}
