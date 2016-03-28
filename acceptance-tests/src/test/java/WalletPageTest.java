import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.support.ui.Select;

public class WalletPageTest extends AbstractPageTest {

    private WalletPage walletPage;
    private String wrongAddress = "mhojSpv28ww5S3PuxKtnMJFWDEMMCb3MR2";
    
    @Before
    public void before() {
    	logInIndividual();
        this.walletPage = new WalletPage(driver);
    }

    @Test
    public void enterPage() {
        enterPage("wallet");
        assertEquals("balanceTabId", walletPage.balanceTab.getAttribute("id"));
    }
    
    @Test
    public void sendTransaction(){
    	enterPage("wallet");
    	
    	walletPage.balanceTab.click();
    	walletPage.requestTxTab.click();
    	walletPage.txHistoryTab.click();
    	walletPage.sendTxTab.click();
    	
    	walletPage.addressFromInput.sendKeys("myAddress");
    	walletPage.addressToInput.sendKeys("destinationAddress");
    	walletPage.amountInput.sendKeys("123456");
    	new Select(walletPage.currencySelect).selectByVisibleText("BTC");
    	
    	walletPage.sendButton.click();
    }
    
    @Ignore("TODO: Check why returned test user passphrase is different and remove ignore when wallet is not locked")
    @Test
    public void getPrivateKey(){
    	enterPage("wallet");
    	
    	walletPage.clickFirstPrivateKeyButton();
    	walletPage.enterPassphrase("cute forget down grand out dull observe above said");
    	walletPage.modalConfirmButton.click();
    	
    	waitUntil(visibilityOf(walletPage.privateKeyField));
    	
    	assertEquals("74553550322049543760814247980174181152560610984436228784984981921039459812154", walletPage.privateKeyField.getText());
    }
    
    @Test
    public void validateTransactionAddresses() {
        enterPage("wallet");
        
        walletPage.addressFromInput.sendKeys(wrongAddress);
        walletPage.addressToInput.sendKeys(wrongAddress);
        walletPage.sendButton.click();
        waitUntil(visibilityOf(walletPage.errorAddressFrom));
        waitUntil(visibilityOf(walletPage.errorAddressTo));
    }
    
    @Test
    public void validateTransactionCurrencyAndFee() {
        enterPage("wallet");
        
        walletPage.sendButton.click();
        waitUntil(visibilityOf(walletPage.errorCurrency));
        waitUntil(visibilityOf(walletPage.errorMinersFee));
        
        walletPage.getCurrencySelect().selectByVisibleText("XTC");
        walletPage.minersFeeInput.clear();
        walletPage.minersFeeInput.sendKeys("0.000001");
        walletPage.sendButton.click();
        waitUntil(visibilityOf(walletPage.errorMinersFee));
    }
    
    @Test
    public void wrongPrivateKey(){
    	enterPage("wallet");
    	
    	walletPage.clickFirstPrivateKeyButton();
    	walletPage.enterPassphrase("wrong passphrase");
    	walletPage.modalConfirmButton.click();
    	waitUntil(visibilityOf(walletPage.passwordError));
    	
    	assertEquals(true, walletPage.passwordError.isDisplayed());
    }
}
