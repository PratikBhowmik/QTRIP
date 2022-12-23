package qtriptest.pages;

import qtriptest.WrapperClass;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    @FindBy(name = "email")
    WebElement usernameLogin;
    @FindBy(name = "password")
    WebElement passwordLogin;
    @FindBy(xpath = "//button[text() = 'Login to QTrip']")
    WebElement loginBtn;

    WrapperClass wrap = new WrapperClass();

    RemoteWebDriver driver;

    public LoginPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void loginToQtrip(String username, String password){
        // usernameLogin.sendKeys(username);
        wrap.sendKeys(usernameLogin, username);
        // passwordLogin.sendKeys(password);
        wrap.sendKeys(passwordLogin, password);
    }

    public void clickLogin() {
        // WebDriverWait wait = new WebDriverWait(driver, 30);
        // wait.until(ExpectedConditions.visibilityOf(loginBtn));
        // loginBtn.click();
        wrap.click(loginBtn, driver);
    }

    public boolean verifyusernavigatedtologinPage(){
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getCurrentUrl().endsWith("/login"));
        return driver.getCurrentUrl().endsWith("/login");
    }
}
