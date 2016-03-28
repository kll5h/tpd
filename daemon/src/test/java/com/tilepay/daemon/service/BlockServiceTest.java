package com.tilepay.daemon.service;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;
import static com.tilepay.domain.entity.BalanceBuilder.aBalance;
import static com.tilepay.domain.entity.BlockBuilder.aBlock;
import static com.tilepay.domain.entity.IssuanceBuilder.anIssuance;
import static com.tilepay.domain.entity.SendBuilder.aSend;
import static com.tilepay.domain.entity.TransactionBuilder.aTransaction;
import static org.bitcoinj.testing.FakeTxBuilder.createFakeTx;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Set;

import javax.inject.Inject;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.util.ReflectionTestUtils;

import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.Block;
import com.tilepay.domain.entity.Issuance;
import com.tilepay.domain.entity.IssuanceStatus;
import com.tilepay.domain.entity.Send;
import com.tilepay.protocol.config.NetworkParametersConfig;
import com.tilepay.protocol.service.ProtocolTransactionService;

public class BlockServiceTest extends AbstractPersistenceTest {

    @Inject
    private BlockService blockService;

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    private ProtocolTransactionService protocolTransactionService = Mockito.mock(ProtocolTransactionService.class);

    @Before
    public void before() {
        balanceRepository.deleteAllInBatch();
        debitRepository.deleteAllInBatch();
        creditRepository.deleteAllInBatch();
        transactionRepository.deleteAllInBatch();
        messageRepository.deleteAllInBatch();
        assetRepository.deleteAllInBatch();
        blockRepository.deleteAllInBatch();

        MockitoAnnotations.initMocks(this);
        BlockService bs = (BlockService) unwrapProxy(blockService);
        ReflectionTestUtils.setField(bs, "protocolTransactionService", protocolTransactionService);
    }

    @Test
    public void issuance() throws AddressFormatException {
        Asset asset = anAsset().setTilecoinProtocol().setName("QQQQ").setBooked(true).setDivisible(true).build();
        Issuance issuance = anIssuance().setAsset(asset).setQuantity(123456).build();
        com.tilepay.domain.entity.Transaction tx1 = aTransaction().setSource("source").setDestination("destination").setMessage(issuance).build();

        Transaction btcTx1 = createFakeTx(networkParametersConfig.networkParameters(), Coin.CENT, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));

        Mockito.when(protocolTransactionService.parse(1,btcTx1)).thenReturn(tx1);

        com.tilepay.domain.entity.Block tilepayBlock = aBlock().setIndex(1).setHash("1").setTime(1L).build();
        blockService.parseBtcTransactions(tilepayBlock, Arrays.asList(btcTx1));

        Block block = blockRepository.findOne(tilepayBlock.getId());
        assertEquals(1, block.getTransactions().size());

        com.tilepay.domain.entity.Transaction transaction = block.getTransactions().iterator().next();
        assertEquals(0, transaction.getLedgerEntries().size());

        IssuanceAsserter.assertIssuance((Issuance) transaction.getMessage(), IssuanceStatus.FEE_IS_NOT_CHARGED);

        assertEquals(0, balanceRepository.findAll().size());
    }

    @Test
    public void issueBookedAsset() throws AddressFormatException {
        Asset asset = anAsset().setTilecoinProtocol().setName("QQQQ").setBooked(true).setDivisible(true).build();
        assetRepository.save(asset);

        asset = anAsset().setTilecoinProtocol().setName("QQQQ").setDivisible(true).build();
        Issuance issuance = anIssuance().setAsset(asset).setQuantity(123456).build();
        com.tilepay.domain.entity.Transaction tx1 = aTransaction().setSource("source").setDestination("destination").setMessage(issuance).build();

        Transaction btcTx1 = createFakeTx(networkParametersConfig.networkParameters(), Coin.CENT, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));
        Mockito.when(protocolTransactionService.parse(1,btcTx1)).thenReturn(tx1);


        com.tilepay.domain.entity.Block tilepayBlock = aBlock().setIndex(1).setHash("1").setTime(1L).build();
        blockService.parseBtcTransactions(tilepayBlock, Arrays.asList(btcTx1));

        Block block = blockRepository.findOne(tilepayBlock.getId());
        assertEquals(1, block.getTransactions().size());

        com.tilepay.domain.entity.Transaction transaction = block.getTransactions().iterator().next();
        assertEquals(0, transaction.getLedgerEntries().size());
        IssuanceAsserter.assertIssuance((Issuance) transaction.getMessage(), IssuanceStatus.FEE_IS_NOT_CHARGED);
    }

    @Test
    public void issuanceSameAsset() throws AddressFormatException {
        Issuance issuance = anIssuance().setAsset(anAsset().setTilecoinProtocol().setName("QQQQ").setBooked(true).setDivisible(true).build()).setQuantity(123456).build();
        com.tilepay.domain.entity.Transaction tx1 = aTransaction().setSource("source").setDestination("destination").setMessage(issuance).build();

        issuance = anIssuance().setAsset(anAsset().setTilecoinProtocol().setName("QQQQ").setBooked(true).setDivisible(true).build()).setQuantity(123456).build();
        com.tilepay.domain.entity.Transaction tx2 = aTransaction().setSource("source").setDestination("destination").setMessage(issuance).build();

        Transaction btcTx1 = createFakeTx(networkParametersConfig.networkParameters(), Coin.CENT, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));
        Transaction btcTx2 = createFakeTx(networkParametersConfig.networkParameters(), Coin.CENT, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));

        Mockito.when(protocolTransactionService.parse(1,btcTx1)).thenReturn(tx1);
        Mockito.when(protocolTransactionService.parse(1,btcTx2)).thenReturn(tx2);

        com.tilepay.domain.entity.Block tilepayBlock = aBlock().setIndex(1).setHash("1").setTime(1L).build();
        blockService.parseBtcTransactions(tilepayBlock, Arrays.asList(btcTx1, btcTx2));

        Block block = blockRepository.findOne(tilepayBlock.getId());
        assertEquals(1, block.getTransactions().size());

        com.tilepay.domain.entity.Transaction transaction = block.getTransactions().iterator().next();
        assertEquals(0, transaction.getLedgerEntries().size());

        IssuanceAsserter.assertIssuance((Issuance) transaction.getMessage(), IssuanceStatus.FEE_IS_NOT_CHARGED);

        assertEquals(0, balanceRepository.findAll().size());
    }

    @Test
    public void twoIssuances() throws AddressFormatException {
        Issuance issuance = anIssuance().setAsset(anAsset().setTilecoinProtocol().setName("AAAA").setBooked(true).setDivisible(true).build()).setQuantity(100).build();
        com.tilepay.domain.entity.Transaction tx1 = aTransaction().setSource("source").setDestination("destination").setMessage(issuance).build();

        issuance = anIssuance().setAsset(anAsset().setTilecoinProtocol().setName("BBBB").setBooked(true).setDivisible(true).build()).setQuantity(100).build();
        com.tilepay.domain.entity.Transaction tx2 = aTransaction().setSource("source").setDestination("destination").setMessage(issuance).build();

        Transaction btcTx1 = createFakeTx(networkParametersConfig.networkParameters(), Coin.CENT, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));
        Transaction btcTx2 = createFakeTx(networkParametersConfig.networkParameters(), Coin.CENT, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));

        Mockito.when(protocolTransactionService.parse(1,btcTx1)).thenReturn(tx1);
        Mockito.when(protocolTransactionService.parse(1,btcTx2)).thenReturn(tx2);

        com.tilepay.domain.entity.Block tilepayBlock = aBlock().setIndex(1).setHash("1").setTime(1L).build();
        blockService.parseBtcTransactions(tilepayBlock, Arrays.asList(btcTx1, btcTx2));

        Block block = blockRepository.findOne(tilepayBlock.getId());
        assertEquals(2, block.getTransactions().size());

        Set<com.tilepay.domain.entity.Transaction> transactions = block.getTransactions();
        for (com.tilepay.domain.entity.Transaction t : transactions) {
            IssuanceAsserter.assertIssuance((Issuance) t.getMessage(), IssuanceStatus.FEE_IS_NOT_CHARGED);
        }

        //com.tilepay.domain.entity.Transaction transaction = block.getTransactions().iterator().next();
        //assertEquals(1, transaction.getLedgerEntries().size());
    }

    @Test
    public void send() {
        Asset asset = anAsset().setTilecoinProtocol().setName("QQQQ").setBooked(true).setDivisible(true).build();
        assetRepository.save(asset);

        Balance balance = aBalance().setAsset(asset).setAddress("source").setQuantity(100).build();
        balanceRepository.save(balance);

        Send send = aSend().setAsset(asset).setQuantity(50).build();
        com.tilepay.domain.entity.Transaction tx = aTransaction().setSource("source").setDestination("destination").setMessage(send).build();
        com.tilepay.domain.entity.Block tilepayBlock = aBlock().setIndex(1).setHash("1").setTime(1L).build();

        Transaction btcTransaction = new Transaction(null);

        Mockito.when(protocolTransactionService.parse(1,btcTransaction)).thenReturn(tx);

        blockService.parseBtcTransactions(tilepayBlock, Arrays.asList(btcTransaction));

        Block block = blockRepository.findOne(tilepayBlock.getId());
        assertEquals(1, block.getTransactions().size());

        com.tilepay.domain.entity.Transaction transaction = block.getTransactions().iterator().next();
        assertEquals(2, transaction.getLedgerEntries().size());
    }

    @Test
    public void twoSends() throws AddressFormatException {
        Asset asset = anAsset().setTilecoinProtocol().setName("AAAA").setBooked(true).setDivisible(true).build();
        assetRepository.save(asset);

        Balance balance = aBalance().setAsset(asset).setAddress("source").setQuantity(100).build();
        balanceRepository.save(balance);

        asset.setId(null);
        Send send = aSend().setAsset(asset).setQuantity(50).build();
        com.tilepay.domain.entity.Transaction tx1 = aTransaction().setSource("source").setDestination("destination").setMessage(send).build();

        send = aSend().setAsset(asset).setQuantity(50).build();
        com.tilepay.domain.entity.Transaction tx2 = aTransaction().setSource("source").setDestination("destination").setMessage(send).build();

        Transaction btcTx1 = createFakeTx(networkParametersConfig.networkParameters(), Coin.CENT, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));
        Transaction btcTx2 = createFakeTx(networkParametersConfig.networkParameters(), Coin.CENT, new Address(networkParametersConfig.networkParameters(), "mv9AHx4vdn9Mdxxpe5g8B3AxfAk77VMYx9"));

        Mockito.when(protocolTransactionService.parse(1,btcTx1)).thenReturn(tx1);
        Mockito.when(protocolTransactionService.parse(1,btcTx2)).thenReturn(tx2);

        com.tilepay.domain.entity.Block tilepayBlock = aBlock().setIndex(1).setHash("1").setTime(1L).build();
        blockService.parseBtcTransactions(tilepayBlock, Arrays.asList(btcTx1, btcTx2));

        Block block = blockRepository.findOne(tilepayBlock.getId());
        assertEquals(2, block.getTransactions().size());

        com.tilepay.domain.entity.Transaction transaction = block.getTransactions().iterator().next();
        assertEquals(2, transaction.getLedgerEntries().size());
    }

    public static Object unwrapProxy(Object bean) {

        if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {
            Advised advised = (Advised) bean;
            try {
                bean = advised.getTargetSource().getTarget();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return bean;
    }

}