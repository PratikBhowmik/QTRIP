package qtriptest.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventurePage {

    RemoteWebDriver driver;

    // @FindBys({@FindBy(className = "hero-input"), @FindBy(id = "autocomplete")})
    // WebElement searchbar;
    // @FindBy(id = "autocomplete")
    // WebElement searchbar;

    // @FindBy(id = "results")
    // WebElement noCityFound;
    Select sel;

    @FindBy(xpath = "//ul[@id = 'results']")
    WebElement citybar;

    @FindBy(xpath = "//select[@id = 'duration-select']")
    WebElement hours;

    @FindBy(xpath = "//select[@id = 'category-select']")
    WebElement categoryElement;

    @FindBy(xpath = "//div[text() = 'Cycling']")
    WebElement dataAfterApplyingFilter;

    @FindBy(xpath = "//div[@onclick = 'clearDuration(event)']")
    WebElement clearHours;

    @FindBy(className = "ms-3")
    List<WebElement> clearElements;

    @FindBy(xpath = "//div[@id = 'data']")
    WebElement displayOfAllElementsAfterClearingFilter;

    @FindBy(id = "category-list")
    List<WebElement> categoryFilters;

    @FindBy(xpath = "//div[@class = 'row' and @id = 'data']/div")
    List<WebElement> allCards;

    @FindBy(id = "search-adventures")
    WebElement searchAdventure;

    @FindBy(xpath = "//div[@class = 'activity-card']")
    WebElement selectTheAdventure;

    public AdventurePage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setFilterValue(String value) {
        if (hours.isDisplayed()) {
            sel = new Select(hours);
            sel.selectByVisibleText(value);
        }
    }

    public void setCategoryValue(String category) throws InterruptedException {
        // try {
        if (categoryElement.isDisplayed()) {
            sel = new Select(categoryElement);
            sel.selectByVisibleText(category);
        }
        // } catch (Exception e) {
        // System.out.println(e.getMessage());
        // }
    }

    public int getResultCount() {
        int count = 0;
        for (WebElement counts : categoryFilters) {
            count++;
        }
        return count;
    }

    public void clearCategory() {
        for (int i = 0; i < clearElements.size(); i++) {
            clearElements.get(i).click();
        }
    }

    public void selectAdventure(String adventure) {
        try {
            searchAdventure.sendKeys(adventure);
            Thread.sleep(3000);
            selectTheAdventure.click();
        } catch (Exception e) {
            System.out.println("exception while searching and selecting adventure");
        }
    }
}
