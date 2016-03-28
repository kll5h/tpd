package com.tilepay.daemon.persistence;

import static com.tilepay.domain.entity.BlockBuilder.aBlock;
import static com.tilepay.domain.entity.TransactionBuilder.aTransaction;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.tilepay.domain.entity.Block;
import com.tilepay.domain.entity.Debit;
import com.tilepay.domain.entity.Transaction;

public class BlockRepositoryTest extends AbstractRepositoryTest {

    @Inject
    private BlockRepository blockRepository;

    @Inject
    private TransactionRepository transactionRepository;

    @Inject
    private DebitRepository debitRepository;

    @Before
    public void before() {
        debitRepository.deleteAllInBatch();

        transactionRepository.deleteAllInBatch();
        blockRepository.deleteAllInBatch();
    }

    @Test
    public void findAll() {
        saveBlock(1, "000000000000000019d056840b1ce766417adceb22256b18c183aa023fed0329");

        List<Block> all = blockRepository.findAll();

        assertEquals(1, all.size());
        assertEquals(new Integer(1), all.get(0).getIndex());
    }

    @Test
    public void findTopByOrderByIndexDesc() {
        saveBlock(1, "000000000000000019d056840b1ce766417adceb22256b18c183aa023fed0329");
        saveBlock(2, "000000000000000019d056840b1ce766417adceb22256b18c183aa023fed0330");

        Block lastBlock = blockRepository.findTopByOrderByIndexDesc();
        assertEquals(2L, lastBlock.getIndex().longValue());
    }

    @Test
    public void findByHash() {
        saveBlock(1, "000000000000000019d056840b1ce766417adceb22256b18c183aa023fed0329");
        Block block = blockRepository.findByHash("000000000000000019d056840b1ce766417adceb22256b18c183aa023fed0329");
        assertEquals(1L, block.getIndex().longValue());

        block = blockRepository.findByHash("12345");
        assertNull(block);
    }

    @Test
    public void findByIndexGreaterThanEqualOrderByIndexAsc() {
        saveBlock(0, "000000000000000019d056840b1ce766417adceb22256b18c183aa023fed0328");
        saveBlock(1, "000000000000000019d056840b1ce766417adceb22256b18c183aa023fed0329");
        saveBlock(2, "000000000000000019d056840b1ce766417adceb22256b18c183aa023fed0330");
        saveBlock(3, "000000000000000019d056840b1ce766417adceb22256b18c183aa023fed0331");

        List<Block> blocks = blockRepository.findByIndexGreaterThanEqualOrderByIndexAsc(1);
        assertEquals(3, blocks.size());
    }

    @Test
    public void addBlockWithTransactions() {
        Block block = aBlock().setIndex(1).setHash("12345").build();

        Transaction transaction = aTransaction().setHash("54321").setData(new byte[] { 1 }).build();

        Debit debit = new Debit();
        transaction.addLedgerEntry(debit);

        block.addTransaction(transaction);

        blockRepository.save(block);

        List<Block> blocks = blockRepository.findAll();
        assertEquals(1, blocks.size());

        Transaction actualTransaction = blocks.get(0).getTransactions().iterator().next();
        assertEquals("54321", actualTransaction.getHash());
        assertArrayEquals(new byte[] { 1 }, actualTransaction.getData());
        assertEquals(1, actualTransaction.getLedgerEntries().size());
    }

    private void saveBlock(Integer index, String hash) {
        Block block = aBlock().setIndex(index).setHash(hash).setTime(123456789L).build();
        blockRepository.save(block);
    }

}