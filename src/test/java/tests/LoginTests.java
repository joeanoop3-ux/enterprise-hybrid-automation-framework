package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "Verify application behavior on invalid login credentials")
    public void testInvalidLoginErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Execute the encapsulated user action flow
        loginPage.login("invalidUser", "invalidPassword123");
        
        // Validate system output against expected criteria
        String actualError = loginPage.getErrorMessageText();
        Assert.assertTrue(actualError.contains("Your username is invalid!"), 
            "[ERROR] The expected validation alert text did not display on screen.");
    }
}
