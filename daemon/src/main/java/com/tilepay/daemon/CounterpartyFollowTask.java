package com.tilepay.daemon;

import java.util.List;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tilepay.counterpartyclient.model.CounterpartySend;
import com.tilepay.counterpartyclient.service.CounterpartyService;
import com.tilepay.daemon.persistence.TransactionRepository;
import com.tilepay.daemon.service.DaemonTransactionService;
import com.tilepay.domain.entity.Transaction;
import com.tilepay.protocol.config.NetworkParametersConfig;

@ThreadSafe
@Component
@Profile(value = { "testnet", "mainnet" })
public class CounterpartyFollowTask {

    private static final Logger logger = LoggerFactory.getLogger(CounterpartyFollowTask.class);

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    @Inject
    private DaemonTransactionService daemonTransactionService;

    @Inject
    private CounterpartyService counterpartyService;

    @Inject
    private TransactionRepository transactionRepository;

    @Scheduled(fixedRate = 60000)
    public void follow() throws Exception {

        logger.info("Starting synch with Counterparty...");

        //TODO: 21.01.2015 Andrei Sljusar: move to service
        List<Transaction> transactions = transactionRepository.findTransactionsWithoutFeeCharged();

        logger.debug("Number of transactions without fee charged: " + transactions.size());

        for (Transaction transaction : transactions) {
            List<CounterpartySend> feeSends = counterpartyService.getSends(transaction.getSource(), networkParametersConfig.getFeeAddress());
            List<Transaction> feeTransactions = transactionRepository.findBySourceAndDestination(transaction.getSource(), networkParametersConfig.getFeeAddress());
            CounterpartySend firstFreeFeeSend = daemonTransactionService.getFirstFreeFeeSend(feeSends, feeTransactions);
            if (firstFreeFeeSend != null) {
                daemonTransactionService.saveFeeTransaction(firstFreeFeeSend, transaction);
            }
        }

        logger.info("Finished synch with Counterparty.");
    }

}
