import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ProfilePage extends AbstractPage {

    @FindBy(id = "privateTabSave")
    WebElement privateTabSaveButton;
    
    @FindBy(id = "publicTabSave")
    WebElement publicTabSaveButton;

    @FindBy(className = "alert-success")
    WebElement successMessage;
    
    @FindBy(id = "userName")
    WebElement usernameInput;

    @FindBy(id = "caption")
    WebElement captionInput;
    
    @FindBy(id = "firstName")
    WebElement firstNameInput;

    @FindBy(id = "lastName")
    WebElement lastNameInput;

    @FindBy(id = "country")
    private WebElement country;

    @FindBy(id = "dateOfBirth")
    WebElement dateOfBirthInput;

    @FindBy(id = "privateTab")
    WebElement privateTab;

    @FindBy(id = "email")
    WebElement emailInput;
    
    @FindBy(id = "address")
    WebElement addressInput;
    
    @FindBy(id = "city")
    WebElement cityInput;

    @FindBy(id = "state")
    WebElement stateInput;
    
    @FindBy(id = "zip")
    WebElement zipInput;
    
    @FindBy(id = "phone")
    WebElement phoneInput;
    
    @FindBy(id = "errorUserName")
    WebElement errorUserName;

    @FindBy(id = "errorCaption")
    WebElement errorCaption;

    @FindBy(id = "errorEmail")
    WebElement errorEmail;

    @FindBy(id = "errorFirstName")
    WebElement errorFirstName;

    @FindBy(id = "errorLastName")
    WebElement errorLastName;

    @FindBy(id = "errorAddress")
    WebElement errorAddress;

    @FindBy(id = "errorCity")
    WebElement errorCity;

    @FindBy(id = "errorState")
    WebElement errorState;
    
    @FindBy(id = "errorZip")
    WebElement errorZip;
    
    @FindBy(id = "errorDateOfBirth")
    WebElement errorDateOfBirth;

    @FindBy(id = "errorPhone")
    WebElement errorPhone;

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public Select getCountry() {
        return new Select(country);
    }
}
