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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        
        driver = new ChromeDriver(options);
        driver.get("https://herokuapp.com");
        loginPage = new LoginPage(driver);
    }

    // Keep Method 1: For your standard Login.feature
    @When("The user enters an invalid username and password")
    public void the_user_enters_an_invalid_username_and_password() {
        loginPage.enterUsername("invalidUser");
        loginPage.enterPassword("wrongPassword123");
    }

    // Append Method 2: For your data-driven LoginDataDriven.feature
    @When("The user enters an username {string} and password {string}")
    public void the_user_enters_an_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
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
                "[CUCUMBER ERROR] The validation banner did not match the expected string.");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
