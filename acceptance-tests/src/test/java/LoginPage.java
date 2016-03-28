import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    @FindBy(id = "login")
    WebElement loginButton;
    
    @FindBy(id="username")
    WebElement passwordField;

    public LoginPage(WebDriver driver) {
        super(driver);
    }
}
