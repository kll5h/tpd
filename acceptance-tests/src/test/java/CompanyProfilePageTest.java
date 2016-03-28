
import static com.tilepay.acceptancetest.util.RandomStringUtilsUtil.randomLowercaseString;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class CompanyProfilePageTest extends AbstractPageTest {

    private CompanyProfilePage companyProfilePage;

    @Before
    public void before() {
        logInCompany();
        this.companyProfilePage = new CompanyProfilePage(driver);
    }

    @Test
    public void enterPage() {
        enterPage("profile/company");
        assertEquals("save", companyProfilePage.saveButton.getAttribute("id"));
    }

    @Test
    public void editProfile() {
        enterPage("profile/company");

        companyProfilePage.contactTab.click();
        companyProfilePage.firstNameInput.clear();
        companyProfilePage.firstNameInput.sendKeys("testFirstName");
        companyProfilePage.lastNameInput.clear();
        companyProfilePage.lastNameInput.sendKeys("testLastName");

        companyProfilePage.saveButton.click();

        waitUntil(presenceOfElementLocated(By.className("alert-success")));


        assertEquals("testFirstName", companyProfilePage.firstNameInput.getAttribute("value"));
        assertEquals("testLastName", companyProfilePage.lastNameInput.getAttribute("value"));
    }

    @Test
    public void errorMessagesAreShownWhenInputsAreInvalid() {
        enterPage("profile/company");
        
        companyProfilePage.companyNameInput.sendKeys(randomLowercaseString(256));
        companyProfilePage.addressInput.sendKeys(randomLowercaseString(256));
        companyProfilePage.cityInput.sendKeys(randomLowercaseString(61));
        companyProfilePage.stateInput.sendKeys(randomLowercaseString(61));
        companyProfilePage.zipInput.sendKeys(randomLowercaseString(31));
        companyProfilePage.captionInput.sendKeys(randomLowercaseString(141));
        
        companyProfilePage.savePublicButton.click();
        
        //ASSERTS
        waitUntil(visibilityOf(companyProfilePage.errorCompanyName));
        waitUntil(visibilityOf(companyProfilePage.errorAddress));
        waitUntil(visibilityOf(companyProfilePage.errorCity));
        waitUntil(visibilityOf(companyProfilePage.errorState));
        waitUntil(visibilityOf(companyProfilePage.errorZip));
        waitUntil(visibilityOf(companyProfilePage.errorCaption));
        
        
        //TESTING PRIVATE TAB
        
        companyProfilePage.privateTab.click();
        
        String companyType = randomLowercaseString(256);
        companyProfilePage.companyTypeInput.sendKeys(companyType);
        String taxId = randomLowercaseString(31);
        companyProfilePage.taxIdInput.sendKeys(taxId);
        
        companyProfilePage.savePrivateButton.click();
        
        //ASSERTS
        waitUntil(visibilityOf(companyProfilePage.errorCompanyType));
        waitUntil(visibilityOf(companyProfilePage.errorTaxId));
        
        //TESTING CONTACT TAB
        
        companyProfilePage.contactTab.click();
        companyProfilePage.firstNameInput.clear();
        companyProfilePage.lastNameInput.clear();
        companyProfilePage.emailInput.sendKeys("error mail@mail.ee");
        companyProfilePage.phoneInput.sendKeys("T0123456789");
        companyProfilePage.saveButton.click();

        //ASSERTS
        waitUntil(visibilityOf(companyProfilePage.errorFirstName));
        waitUntil(visibilityOf(companyProfilePage.errorLastName));
        waitUntil(visibilityOf(companyProfilePage.errorEmail));
        waitUntil(visibilityOf(companyProfilePage.errorPhone));

    }
}
