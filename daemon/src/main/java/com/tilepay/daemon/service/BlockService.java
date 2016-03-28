package com.tilepay.daemon.service;

import static com.tilepay.domain.entity.BlockBuilder.aBlock;

import java.util.List;

import javax.inject.Inject;

import org.bitcoinj.core.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tilepay.daemon.persistence.BlockRepository;
import com.tilepay.domain.entity.Block;
import com.tilepay.domain.entity.Message;
import com.tilepay.protocol.config.NetworkParametersConfig;
import com.tilepay.protocol.service.ProtocolTransactionService;

@Service
public class BlockService {

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    @Inject
    private ProtocolTransactionService protocolTransactionService;

    @Inject
    private TransactionHandlerFactory transactionHandlerFactory;

    @Inject
    private BlockRepository blockRepository;

    public Integer getLastBlock() {
        Block block = blockRepository.findTopByOrderByIndexDesc();
        return block == null ? networkParametersConfig.getFirstBlockIndex() - 1 : block.getIndex();
    }

    @Transactional
    public void parse(org.bitcoinj.core.Block btcBlock, int blockIndex) {
        com.tilepay.domain.entity.Block tilepayBlock = aBlock().setIndex(blockIndex).setHash(btcBlock.getHashAsString()).setTime(btcBlock.getTimeSeconds()).build();
        parseBtcTransactions(tilepayBlock, btcBlock.getTransactions());
    }

    @Transactional
    public void parseBtcTransactions(Block block, List<Transaction> btcTransactions) {

        blockRepository.save(block);

        for (Transaction btcTransaction : btcTransactions) {
            com.tilepay.domain.entity.Transaction tx = protocolTransactionService.parse(block.getIndex(),btcTransaction);

            if (tx != null) {
                Message message = tx.getMessage();
                transactionHandlerFactory.getTransactionHandler(message).handle(tx);
                if (tx.getMessage().getId() != null) {
                    block.addTransaction(tx);
                }

            }
        }

    }

}
