import static com.tilepay.acceptancetest.util.RandomStringUtilsUtil.randomLowercaseString;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.junit.Before;
import org.junit.Test;

public class ProfilePageTest extends AbstractPageTest {

    private ProfilePage profilePage;

    @Before
    public void before() {
        logInIndividual();
        this.profilePage = new ProfilePage(driver);
    }

    @Test
    public void privateTabDetailsAreSuccessfullyUpdated() {
        enterPage("profile/individual");
        
        profilePage.usernameInput.clear();
        profilePage.usernameInput.sendKeys("testusername");
        profilePage.privateTab.click();
        profilePage.firstNameInput.clear();
        profilePage.firstNameInput.sendKeys("testFirstName");
        profilePage.lastNameInput.clear();
        profilePage.lastNameInput.sendKeys("testLastName");

        profilePage.getCountry().selectByValue("1");

        profilePage.dateOfBirthInput.clear();
        profilePage.dateOfBirthInput.sendKeys("1970-08-08");

        profilePage.privateTabSaveButton.click();

        waitUntil(visibilityOf(profilePage.successMessage));
        
        // TODO: maybe we can use built-in selenium asserts
        assertEquals("testFirstName", profilePage.firstNameInput.getAttribute("value"));
        assertEquals("testLastName", profilePage.lastNameInput.getAttribute("value"));
        assertEquals("1970-08-08", profilePage.dateOfBirthInput.getAttribute("value"));
        assertEquals("United States", profilePage.getCountry().getFirstSelectedOption().getText());
    }
    
    @Test
    public void errorMessagesAreShownWhenInputsAreInvalid() {
        enterPage("profile/individual");
        profilePage.usernameInput.sendKeys(randomLowercaseString(21));
        profilePage.captionInput.sendKeys(randomLowercaseString(141));
        profilePage.publicTabSaveButton.click();
        
        waitUntil(visibilityOf(profilePage.errorUserName));
        waitUntil(visibilityOf(profilePage.errorCaption));
        
        profilePage.privateTab.click();
        
        //Init fields
        profilePage.firstNameInput.clear();
        profilePage.lastNameInput.clear();
        profilePage.emailInput.sendKeys("this is not a valid email@mail.ee");
        profilePage.addressInput.sendKeys(randomLowercaseString(256));
        profilePage.cityInput.sendKeys(randomLowercaseString(61));
        profilePage.stateInput.sendKeys(randomLowercaseString(61));
        profilePage.zipInput.sendKeys(randomLowercaseString(31));
        profilePage.phoneInput.sendKeys("T000123456789");
        profilePage.dateOfBirthInput.sendKeys("01/01/1950");
        
        profilePage.privateTabSaveButton.click();
        
        waitUntil(visibilityOf(profilePage.errorEmail));
        waitUntil(visibilityOf(profilePage.errorFirstName));
        waitUntil(visibilityOf(profilePage.errorLastName));
        waitUntil(visibilityOf(profilePage.errorAddress));
        waitUntil(visibilityOf(profilePage.errorCity));
        waitUntil(visibilityOf(profilePage.errorState));
        waitUntil(visibilityOf(profilePage.errorZip));
        waitUntil(visibilityOf(profilePage.errorDateOfBirth));
        waitUntil(visibilityOf(profilePage.errorPhone));
    }

}
