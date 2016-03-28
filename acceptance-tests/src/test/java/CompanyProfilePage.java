import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CompanyProfilePage extends AbstractPage {

    @FindBy(id = "save")
    WebElement saveButton;
    
    @FindBy(id = "companyName")
    WebElement companyNameInput;
    
    @FindBy(id = "address")
    WebElement addressInput;

    @FindBy(id = "city")
    WebElement cityInput;
    
    @FindBy(id = "state")
    WebElement stateInput;

    @FindBy(id = "zip")
    WebElement zipInput;

    @FindBy(id = "caption")
    WebElement captionInput;
    
    @FindBy(id = "firstName")
    WebElement firstNameInput;
    
    @FindBy(id = "lastName")
    WebElement lastNameInput;
    
    @FindBy(id = "companyType")
    WebElement companyTypeInput;
    
    @FindBy(id = "taxId")
    WebElement taxIdInput;

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "phone")
    WebElement phoneInput;
    
    @FindBy(id="privateTab")
    WebElement privateTab;

    @FindBy(id="publicTab")
    WebElement publicTab;
    
    @FindBy(id="contactTab")
    WebElement contactTab;
    
    @FindBy(id="permissionsTab")
    WebElement permissionsTab;
    
    @FindBy(id = "savePublic")
    WebElement savePublicButton;

    @FindBy(id = "savePrivate")
    WebElement savePrivateButton;
    
    @FindBy(id="errorCompanyName")
    WebElement errorCompanyName;

    @FindBy(id="errorAddress")
    WebElement errorAddress;

    @FindBy(id="errorCity")
    WebElement errorCity;

    @FindBy(id="errorState")
    WebElement errorState;

    @FindBy(id="errorZip")
    WebElement errorZip;

    @FindBy(id="errorCaption")
    WebElement errorCaption;

    @FindBy(id="errorCompanyType")
    WebElement errorCompanyType;

    @FindBy(id="errorTaxId")
    WebElement errorTaxId;

    @FindBy(id="errorFirstName")
    WebElement errorFirstName;

    @FindBy(id="errorLastName")
    WebElement errorLastName;

    @FindBy(id="errorEmail")
    WebElement errorEmail;

    @FindBy(id="errorPhone")
    WebElement errorPhone;

    public CompanyProfilePage(WebDriver driver) {
        super(driver);
    }
}
