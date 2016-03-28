import com.tilepay.core.config.CoreAppConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

@ContextConfiguration(classes = CoreAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractPageTest {

    protected String contextPath = "http://localhost:8080";
    protected static WebDriver driver;
    protected static int TIMEOUT = 5;

    String BASE_URL = System.getenv("SELENIUM_BASE_URL") != null ? System.getenv("SELENIUM_BASE_URL") : "http://localhost:8080/";


    @BeforeClass
    public static void beforeClass() throws IOException {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void afterClass() {
        try {
            if (driver != null) {
                driver.quit();
                driver.close();
            }
        } catch (Exception e) {
            System.out.println((e.getMessage()));
        }
    }

    public WebElement findElementById(String id) {
        return driver.findElement(By.id(id));
    }

    public WebElement findElementClassName(String className) {
        return driver.findElement(By.className(className));
    }

    public void clickElementById(String id) {
        findElementById(id).click();
    }

    protected WalletPage logInUser(String password) {
        logout();
        findElementById("username").sendKeys(password);
        clickElementById("login");
        waitUntil(elementToBeClickable(By.id("logout")));
        return new WalletPage(driver);
    }

    public void logInIndividual() {
        logInUser("cute forget down grand out dull observe above said");
    }

    public void logInCompany() {
        logInUser("start art hill fog depression queen tough thousand darling such sadness hunt");
    }

    protected void logout() {
        enterPage("logout");
        waitUntil(elementToBeClickable(By.id("login")));
    }

    protected void enterPage(final String path) {
        driver.get(BASE_URL + path);
    }

    protected void waitUntil(ExpectedCondition<WebElement> expectedCondition) {
        new WebDriverWait(driver, TIMEOUT).until(expectedCondition);
    }

}
