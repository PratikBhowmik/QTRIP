package qtriptest.pages;

import qtriptest.WrapperClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdventureDetailsPage {
    @FindBy(name = "name")
    WebElement entername;

    @FindBy(name = "date")
    WebElement enterdate;

    @FindBy(xpath = "//input[@type = 'number']")
    WebElement enterNumberOfPersons;

    @FindBy(xpath = "//button[text() = 'Reserve']")
    WebElement reserveBtn;

    @FindBy(id = "reserved-banner")
    WebElement validationElement;

    RemoteWebDriver driver;

    WrapperClass wrap = new WrapperClass();

    public AdventureDetailsPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void bookAdventure(String name, String date, String count) {
        // entername.sendKeys(name);
        // enterdate.sendKeys(date);
        // enterNumberOfPersons.clear();
        // enterNumberOfPersons.sendKeys(count);
        // reserveBtn.click();
        wrap.sendKeys(entername, name);
        wrap.sendKeys(enterdate, date);
        wrap.sendKeys(enterNumberOfPersons, count);
        wrap.click(reserveBtn, driver);
        
    }

    public boolean isBookingSuccessful() throws InterruptedException {
        return validationElement.isDisplayed();
    }
}
