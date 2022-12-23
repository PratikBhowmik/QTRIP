package qtriptest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class WrapperClass {

    // JavascriptExecutor js;
    RemoteWebDriver driver;

    // Wrapper method for click
    public void click(WebElement ele, WebDriver driver) {
        if (ele.isEnabled()) {
            // js = (JavascriptExecutor) driver;
            // js.executeScript("arguments[0].scrollIntoView(true)");
            ele.click();
        } else {
            System.out.println("Element isn't visible");
        }
    }

    // Wrapper method to sendKeys
    public void sendKeys(WebElement ele, String inputStr) {
        ele.clear();
        ele.sendKeys(inputStr);
    }

    // Wrapper method to get url
    public void get(WebDriver driver, String url) {
        if (driver.getCurrentUrl() != url) {
            driver.get(url);
        } else {
            System.out.println("url matched");
        }
    }

    // Wrapper method to findelement with retry
    public void findElementWithRetry(WebDriver driver, By by, int retryCount) {
        for (int i = 0; i <= retryCount; i++) {
            // driver.findElement(by);
            try {
                driver.findElement(by);
                continue;
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }

        }
    }
}

