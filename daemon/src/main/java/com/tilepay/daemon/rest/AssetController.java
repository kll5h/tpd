package com.tilepay.daemon.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tilepay.daemon.persistence.AssetRepository;
import com.tilepay.daemon.persistence.TransactionRepository;
import com.tilepay.daemon.service.DaemonAssetService;
import com.tilepay.daemon.service.DaemonBalanceService;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.Transaction;

@RestController
public class AssetController {

    @Inject
    private DaemonBalanceService daemonBalanceService;

    @Inject
    private AssetRepository assetRepository;

    @Inject
    private DaemonAssetService daemonAssetService;

    @Inject
    private TransactionRepository transactionRepository;

    @RequestMapping("/running-info")
    public String runningInfo() {
        return "TODO: running info";
    }

    @RequestMapping(value = "/saveAsset", method = RequestMethod.POST)
    public String saveAsset(@RequestBody Asset asset) {
        daemonAssetService.saveAsset(asset);
        return "TODO: running info";
    }
    
    @RequestMapping(value = "/saveAssetWithBalance", method = RequestMethod.POST)
    public String saveAssetWithBalance(@RequestBody Balance balance) {
        daemonBalanceService.saveBalance(balance);
        return "TODO: running info";
    }

    //TODO: 06.01.2015 Andrei Sljusar: assets exist and confirmed
    @RequestMapping(value = "/getBalances/{address}", method = RequestMethod.GET)
    public List<Balance> getBalances(@PathVariable String address) {
        return daemonBalanceService.getBalances(address);
    }

    @RequestMapping(value = "/getAsset/{assetName}", method = RequestMethod.GET)
    public Asset getAsset(@PathVariable String assetName) {
        Asset asset = assetRepository.findByName(assetName);
        if (asset != null) {
            asset.setTilepayProtocol();
        }
        return asset;
    }

    @RequestMapping(value = "/getIssuanceTransactions/{source}", method = RequestMethod.GET)
    public List<Transaction> getIssuanceTransactions(@PathVariable String source) {
        List<Transaction> transactions = transactionRepository.findIssuanceTransactionsWithoutFeeCharged(source);
        for (Transaction transaction : transactions) {
            transaction.getMessage().getAsset().setTilepayProtocol();
            transaction.getBlock().getTransactions().clear();
            transaction.getMessage().setTransaction(null);
        }
        return transactions;
    }
}
