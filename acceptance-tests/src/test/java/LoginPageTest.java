import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginPageTest extends AbstractPageTest {

    private LoginPage loginPage;

    @Before
    public void before() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void login() {
        driver.get(contextPath);
        assertEquals("Login", loginPage.loginButton.getAttribute("value"));
    }

}
