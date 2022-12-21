package qtriptest.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HistoryPage {

    RemoteWebDriver driver;

    @FindBy(xpath = "//a[text()='Reservations']")
    WebElement reservations;

    @FindBy(xpath = "(//table//button)[1]")
    WebElement cancelBtn;

    @FindBy(id = "no-reservation-banner")
    WebElement banner;

    @FindBy(tagName = "table")
    WebElement webtable;

    public HistoryPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void cancelReservations() {
        List<WebElement> cancelBtns = driver.findElements(By.xpath("//table//button"));

        for (int i = 0; i < cancelBtns.size(); i++) {
            cancelBtn.click();
        }
    }

    public void noteTransactionId() {
        List<WebElement> table = driver.findElements(By.tagName("//table"));
        List<WebElement> firstColumnTransactionIDs = new ArrayList<>();
        int i = 1;
        for (WebElement webElement : table) {
            firstColumnTransactionIDs.add(webElement.findElements(By.tagName("//tr")).get(i));
            i++;
        }
    }

    public void clickOnReservation() {
        reservations.click();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public boolean isTransactionIdRemoved() {
        return banner.isDisplayed();
    }

    public boolean areAlltheBookingsPresent(){
        return webtable.isDisplayed();
    }
}
