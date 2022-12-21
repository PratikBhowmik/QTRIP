package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {

    private RemoteWebDriver driver;

    private static DriverSingleton instanceOfDriverSingletonClass = null;

    private DriverSingleton() throws MalformedURLException {

        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        driver.manage().window().maximize();

    }

    public static DriverSingleton getInstance() throws MalformedURLException {

        if (instanceOfDriverSingletonClass == null) {
            instanceOfDriverSingletonClass = new DriverSingleton();

        }
        return instanceOfDriverSingletonClass;
    }

    public RemoteWebDriver getDriver() {
        return driver;
    }
}
