package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import pages.LoginPage;

public class LoginSteps {
    private WebDriver driver;
    private LoginPage loginPage;

    @Given("The user navigates to the login page")
    public void the_user_navigates_to_the_login_page() {
        // Driver initialization optimized for framework context
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        
        driver = new ChromeDriver(options);
        driver.get("https://herokuapp.com");
        
        // Initializing the Page Object model instance
        loginPage = new LoginPage(driver);
    }

    @When("The user enters an invalid username and password")
    public void the_user_enters_an_invalid_username_and_password() {
        // Utilizing your encapsulated page actions
        loginPage.enterUsername("invalidUser");
        loginPage.enterPassword("wrongPassword123");
    }

    @When("Clicks on the submit button")
    public void clicks_on_the_submit_button() {
        loginPage.clickLogin();
    }

    @Then("An error message confirming invalid credentials should be displayed")
    public void an_error_message_confirming_invalid_credentials_should_be_displayed() {
        try {
            String actualError = loginPage.getErrorMessageText();
            Assert.assertTrue(actualError.contains("Your username is invalid!"), 
                "[CUCUMBER ERROR] The validation banner did not match the expected authentication failure string.");
        } finally {
            // Clean hook termination to prevent hanging browser memory allocations
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
