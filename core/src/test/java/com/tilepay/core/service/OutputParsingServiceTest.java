package com.tilepay.core.service;

import static org.junit.Assert.assertEquals;

import com.tilepay.core.config.CoreAppConfig;
import com.tilepay.core.config.NetworkParams;
import com.tilepay.core.dto.BTCTransactionDto;
import com.tilepay.core.dto.OutputDTOBuilder;
import com.tilepay.core.dto.OutputDto;
import com.tilepay.core.service.balance.TilecoinRestClient;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.AssetBuilder;
import com.tilepay.protocol.config.NetworkParametersConfig;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = CoreAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public class OutputParsingServiceTest {

    @Inject
    private NetworkParams networkParams;

    private TilecoinRestClient tilecoinRestClient = Mockito.mock(TilecoinRestClient.class);

    private AssetConverter assetConverter = Mockito.mock(AssetConverter.class);

    @InjectMocks
    @Inject
    private OutputParsingService outputParsingService;

    @Inject
    private NetworkParametersConfig networkParametersConfig;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void parseOutput() throws AddressFormatException {
        TransactionOutput output = new TransactionOutput(networkParams.get(), null, Coin.parseCoin("12"), new Address(networkParams.get(),
                "mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM"));

        OutputDto outputDto = outputParsingService.parseOutput(output);

        assertEquals(outputDto.getAddress(), "mjbkKLzKMdLq4FZoRCLTagtgR6sjgPs6ZM");
        assertEquals(outputDto.getAmount(), "12.00 BTC");
    }

    @Test
    public void parseCounterPartyOutput() {
        byte[] data = new byte[] { 28, 67, 78, 84, 82, 80, 82, 84, 89, 0, 0, 0, 0, 0, 0, 3, -85, -111, -127, 124, 25, 0, 0, 0, 0, 0, 0, 7, -48, 0, 0, 0, 0 };
        OutputDto output = new OutputDto();

        Mockito.when(assetConverter.getAsset("TILECOINXTC")).thenReturn(AssetBuilder.anAsset().setName("TILECOINXTC").setDivisible(true).build());

        outputParsingService.parseAssetOutput(data, output);

        assertEquals("TILECOINXTC", output.getAsset().getName());
        assertEquals("0.00002", output.getAssetAmount());
    }

    @Test
    public void parseTilecoinOutput() {
        byte[] data = new byte[] { 84, 73, 76, 69, 67, 79, 73, 78, 0, 0, 0, 0, 0, 0, 3, -92, -94, 21, -28, 33, 0, 0, 0, 0, 0, 0, 0, 0, 100, 0, 0, 0, 0 };
        OutputDto output = new OutputDto();

        Asset asset = AssetBuilder.anAsset().setName("TESTCOIMZ").setDivisible(true).build();
        Mockito.when(tilecoinRestClient.getAssetByName("TESTCOIMZ")).thenReturn(asset);

        outputParsingService.parseAssetOutput(data, output);

        assertEquals("TESTCOIMZ", output.getAsset().getName());
        assertEquals("0.000001", output.getAssetAmount());
    }

    @Test
    public void transactionTypeSent() {
        Transaction tx = new Transaction(networkParams.get());
        OutputDto output1 = OutputDTOBuilder.anOutput().withAssetName("XTC").withAssetAmount("50").build();
        OutputDto output2 = OutputDTOBuilder.anOutput().withAddress("mysSDZywsPMu6UwHF5uf2Hg8zNJrYt6RAv").withAmount("0.00000786").build();
        OutputDto output3 = OutputDTOBuilder.anOutput().withAddress("yysSDZywsPMu6UwHF5uf2Hg8zNJrYt6RAA").withAmount("0.001").build();
        List<OutputDto> outputs = Arrays.asList(output1, output2, output3);

        BTCTransactionDto transaction = new BTCTransactionDto();
        transaction.setOutputs(outputs);
        outputParsingService.setTxType(tx, transaction);

        assertEquals("Sent ", transaction.getMessage());
    }

    @Test
    public void transactionTypeIssued() {
        Transaction tx = new Transaction(networkParams.get());
        OutputDto output1 = OutputDTOBuilder.anOutput().withAssetName("XTC").withAssetAmount("50").isIssuance(true).build();
        OutputDto output2 = OutputDTOBuilder.anOutput().withAddress("mysSDZywsPMu6UwHF5uf2Hg8zNJrYt6RAk").withAmount("0.00000786").build();
        OutputDto output3 = OutputDTOBuilder.anOutput().withAddress("yysSDZywsPMu6UwHF5uf2Hg8zNJrYt6RAA").withAmount("0.001").build();
        List<OutputDto> outputs = Arrays.asList(output1, output2, output3);

        BTCTransactionDto transaction = new BTCTransactionDto();
        transaction.setOutputs(outputs);
        outputParsingService.setTxType(tx, transaction);

        assertEquals("Issued ", transaction.getMessage());
    }

    @Test
    public void transactionTypeFee() {
        Transaction tx = new Transaction(networkParams.get());
        OutputDto output1 = OutputDTOBuilder.anOutput().withAssetName("XTC").withAssetAmount("50").build();
        OutputDto output2 = OutputDTOBuilder.anOutput().withAddress(networkParametersConfig.getFeeAddress()).withAmount("0.00000786").build();
        OutputDto output3 = OutputDTOBuilder.anOutput().withAddress("yysSDZywsPMu6UwHF5uf2Hg8zNJrYt6RAA").withAmount("0.001").build();
        List<OutputDto> outputs = Arrays.asList(output1, output2, output3);

        BTCTransactionDto transaction = new BTCTransactionDto();
        transaction.setOutputs(outputs);
        outputParsingService.setTxType(tx, transaction);

        assertEquals("Fee of ", transaction.getMessage());
    }

    @Test
    public void transactionTypeReceived() {
        Transaction tx = new Transaction(networkParams.get());
        TransactionOutput input = Mockito.mock(TransactionOutput.class);
        tx.addInput(input);

        OutputDto output1 = OutputDTOBuilder.anOutput().withAssetName("XTC").withAssetAmount("50").build();
        OutputDto output2 = OutputDTOBuilder.anOutput().withAddress("mysSDZywsPMu6UwHF5uf2Hg8zNJrYt6RAv").withAmount("0.00000786").build();
        OutputDto output3 = OutputDTOBuilder.anOutput().withAddress("yysSDZywsPMu6UwHF5uf2Hg8zNJrYt6RAA").withAmount("0.001").build();
        List<OutputDto> outputs = Arrays.asList(output1, output2, output3);

        BTCTransactionDto transaction = new BTCTransactionDto();
        transaction.setOutputs(outputs);
        outputParsingService.setTxType(tx, transaction);

        assertEquals("Received ", transaction.getMessage());
    }
}
