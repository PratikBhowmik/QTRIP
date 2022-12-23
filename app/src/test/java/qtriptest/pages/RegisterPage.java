package qtriptest.pages;

import qtriptest.DriverSingleton;
import qtriptest.WrapperClass;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    // locate username, password and confirm password elements by the help of @FindBy annotation and
    // then create a constructor for initializing the page objects

    RemoteWebDriver driver;

    String registerpageUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    public String lastgeneratedUsername = "";

    @FindBy(name = "email")
    WebElement emailField;

    @FindBy(name = "password")
    WebElement passwordField;

    @FindBy(name = "confirmpassword")
    WebElement confirmpasswordField;

    @FindBy(xpath = "//button[@type = 'submit']")
    WebElement registerButton;

    WrapperClass wrap = new WrapperClass();

    public RegisterPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void registrationOfNewUser(String username, String password, String confirmPassword,
            boolean makeUsernameDynamic) {

        // Get time stamp for generating a unique username
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String test_data_username;
        if (makeUsernameDynamic)
            // Concatenate the timestamp to string to form unique timestamp
            test_data_username = username + String.valueOf(timestamp.getTime());
        else
            test_data_username = username;

        // emailField.sendKeys(test_data_username);
        wrap.sendKeys(emailField, test_data_username);
        // passwordField.sendKeys(password);
        wrap.sendKeys(passwordField, password);
        // confirmpasswordField.sendKeys(confirmPassword);
        wrap.sendKeys(confirmpasswordField, confirmPassword);
        this.lastgeneratedUsername = test_data_username;
    }

    public void clickregisternowButton() {
        // WebDriverWait wait = new WebDriverWait(driver, 30);
        // wait.until(ExpectedConditions.visibilityOf(registerButton));
        // registerButton.click();
        wrap.click(registerButton, driver);
    }

    public boolean isregisTrationpagedisplayed() {
        return driver.getCurrentUrl().equalsIgnoreCase(registerpageUrl);
    }
    // public boolean regsiterUser(String name, String password, String confirmPassword, String
    // generateRandomUsername) {
    // }
}
