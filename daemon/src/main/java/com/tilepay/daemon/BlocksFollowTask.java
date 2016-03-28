package com.tilepay.daemon;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;

import org.bitcoinj.core.Block;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.StoredBlock;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.ListenableFuture;
import com.tilepay.daemon.config.DaemonConfig;
import com.tilepay.daemon.service.BlockService;
import com.tilepay.protocol.config.NetworkParametersConfig;

@ThreadSafe
@Component
@Profile(value = { "testnet", "mainnet" })
public class BlocksFollowTask {

    private static final Logger logger = LoggerFactory.getLogger(BlocksFollowTask.class);

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    @Resource
    private Environment env;

    @Inject
    private BlockService blockService;

    private Wallet wallet;
    private PeerGroup peerGroup;
    private BlockStore blockStore;

    @Scheduled(fixedRate = 60000)
    public void follow() throws Exception {

        init();

        logger.debug("Watching new blocks to appear....");

        Integer blockHeight = getHeight();
        Integer lastBlock = blockService.getLastBlock();

        logger.debug("Last block: " + lastBlock + ", block height: " + blockHeight);

        if (blockHeight > lastBlock) {

            StoredBlock chainHead = blockStore.getChainHead();
            Block block = peerGroup.getDownloadPeer().getBlock(chainHead.getHeader().getHash()).get(30, TimeUnit.SECONDS);

            List<Sha256Hash> blockHashes = new ArrayList<>();

            while (getBlockHeight(block) > lastBlock) {
                blockHashes.add(block.getHash());
                StoredBlock storedBlock = blockStore.get(block.getPrevBlockHash());
                block = storedBlock.getHeader();
            }

            logger.debug("Number of new blocks: " + blockHashes.size());

            Collections.reverse(blockHashes);

            List<ListenableFuture<Block>> futures = new ArrayList<>();

            //traverse blocks in reverse order
            futures.addAll(blockHashes.stream().map(blockHash -> peerGroup.getDownloadPeer().getBlock(blockHash)).collect(Collectors.toList()));
            logger.debug("Number of blocks to be downloaded: " + futures.size());

            for (ListenableFuture<Block> future : futures) {
                Block nextBlock = future.get(30, TimeUnit.SECONDS);
                int blockIndex = getBlockHeight(nextBlock);
                logger.debug("Block: " + blockIndex + ", " + nextBlock.getTime());
                try {
                    blockService.parse(nextBlock, blockIndex);
                    //TODO: 02.02.2015 Andrei Sljusar: AssetDoesNotExistException
                //} catch (InsufficientFundsException e) {
                } catch (Exception e) {
                    logger.error("", e);
                }
            }

        }
    }

    private int getBlockHeight(Block block) throws BlockStoreException {
        return blockStore.get(block.getHash()).getHeight();
    }

    public Integer getHeight() {
        try {
            return blockStore.getChainHead().getHeight();
        } catch (BlockStoreException e) {
            logger.error("", e);
        }
        return 0;
    }

    private void init() {
        if (wallet == null) {
            String userHome = System.getProperty("user.home");
            WalletAppKit walletAppKit = new WalletAppKit(networkParametersConfig.networkParameters(), new File(userHome), env.getRequiredProperty("wallet.file.prefix"));
            walletAppKit.startAsync().awaitRunning();

            this.wallet = walletAppKit.wallet();
            this.blockStore = walletAppKit.store();
            this.peerGroup = walletAppKit.peerGroup();
        }
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaemonConfig.class)) {

        }
    }
}
