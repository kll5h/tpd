import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


public class WalletPage extends AbstractPage  {

	@FindBy(id = "balanceTabId")
    WebElement balanceTab;
    
    @FindBy(id="billsTabId")
    WebElement billsTab;
    
    @FindBy(id="exchangeTabId")
    WebElement exchangeTab;
    
    @FindBy(id="lendTabId")
    WebElement lendTab;
    
    @FindBy(id="clubTabId")
    WebElement clubTab;
    
    @FindBy(id="gatewayTabId")
    WebElement gatewayTab;
    
    @FindBy(id="sendTxTabId")
    WebElement sendTxTab;
    
    @FindBy(id="requestTxTabId")
    WebElement requestTxTab;
    
    @FindBy(id="txHistoryTabId")
    WebElement txHistoryTab;
    
    @FindBy(id="addressFrom")
    WebElement addressFromInput;
    
    @FindBy(id="errorAddressFrom")
    WebElement errorAddressFrom;
    
    @FindBy(id="addressTo")
    WebElement addressToInput;

    @FindBy(id="errorAddressTo")
    WebElement errorAddressTo;
    
    @FindBy(id="amount")
    WebElement amountInput;
    
    @FindBy(id="currency")
    WebElement currencySelect;
    
    @FindBy(id="errorCurrency")
    WebElement errorCurrency;
    
    @FindBy(id="minersFee")
    WebElement minersFeeInput;
    
    @FindBy(id="errorMinersFee")
    WebElement errorMinersFee;
    
    
    @FindBy(id="save")
    WebElement sendButton;
    
    @FindBy(id="modal-password")
    WebElement passwordInput;

    @FindBy(id="modal-password-error")
    WebElement passwordError;
    
    @FindBy(id="modal-confirm")
    WebElement modalConfirmButton;
    
    @FindBy(id="private-key")
    WebElement privateKeyField;
    
	public WalletPage(WebDriver driver) {
        super(driver);
    }
	
	public void clickFirstPrivateKeyButton() {
		driver.findElements(By.className("private-key-btn")).get(0).click();
	}

	public void enterPassphrase(String passphrase) {
		passwordInput.sendKeys(passphrase);
	}
	
	public Select getCurrencySelect() {
	    return new Select(currencySelect);
	}
}
