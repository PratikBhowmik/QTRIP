package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    String homepageurl = "https://qtripdynamic-qa-frontend.vercel.app/";
    // String registerPageUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
    String lastGeneratedUsername = "";

    RemoteWebDriver driver;

    @FindBy(xpath = "//a[text() = 'Register']")
    WebElement registerButton;

    @FindBy(xpath = "//div[@class = 'nav-link login register']")
    WebElement logoutBtn;

    @FindBy(id = "autocomplete")
    WebElement searchbar;

    @FindBy(id = "results")
    WebElement results;

    @FindBy(xpath = "//h5[text()='No City found']")
    WebElement noCityfound;


    // public void navigateToHomePage() {
    // // if (!driver.getCurrentUrl().equals(this.url)) {
    // // driver.get(this.url);
    // // }
    // driver.get(homepageurl);
    // }

    public HomePage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToHomePage() {
        if (!driver.getCurrentUrl().equals(this.homepageurl)) {
            driver.get(this.homepageurl);
        }
        // driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
    }

    public void clickOnRegisterPage() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(registerButton));
        registerButton.click();
    }

    public void clickOnLogout() {
        logoutBtn.click();
    }

    public boolean ishomepageDisplayed() {

        return driver.getCurrentUrl().equalsIgnoreCase(homepageurl);
    }

    public void searchCity(String city) throws InterruptedException {
        // try {
        // WebDriverWait wait = new WebDriverWait(driver, 30);
        // wait.until(ExpectedConditions.visibilityOf(searchbar));
        searchbar.clear();
        // wait.until(ExpectedConditions.visibilityOf(searchbar));
        searchbar.sendKeys(city);
        Thread.sleep(3000);
        // } catch (Exception e) {
        // System.out.println(e.getMessage());
        // }
    }

    public boolean assertAutoCompleteText(String autoCompleteText) {
        // try {
        if (noCityfound.getText().equals("No City found")) {
            return true;
        } else {
            return false;
        }
        // } catch (Exception e) {
        // System.out.println(e.getMessage());
        // }
        // return false;
    }

    public void selectCity(String city) {
        // try {
        // results.click();
        // } catch (Exception e) {
        // System.out.println(e.getMessage());
        // }
        // WebElement cityEle = null;
        try {
            // Thread.sleep(2000);
            By byCity = new By.ByXPath(String.format("//li[@id='%s']", city.toLowerCase()));
            driver.findElement(byCity).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
