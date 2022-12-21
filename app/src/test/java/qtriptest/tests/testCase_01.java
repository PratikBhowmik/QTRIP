package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class testCase_01 {

    RemoteWebDriver driver;
    // HomePage home = new HomePage(driver);

    public static String lastgeneratedUsername;

    // SoftAssert soft = new SoftAssert();

    @BeforeSuite
    public void setup() throws MalformedURLException {

        DriverSingleton ds = DriverSingleton.getInstance();
        driver = ds.getDriver();
        // driver.get("https://qtripdynamic-qa-frontend.vercel.app/");

    }

    @Test(dataProvider = "my data provider", dataProviderClass = DP.class, priority = 1, groups = {"Login flow"})
    public void TestCase01(Object username, Object password) throws InterruptedException {

        // ExtentReports er = new ExtentReports("filePath", true);

        // ExtentTest test = er.startTest("TC");

        // er.loadConfig(new File(
        // "/home/crio-user/workspace/impratikbhowmik-ME_QTRIP_QA/app/src/test/java/qtriptest/tests/extentconfig.xml"));

        // Launch Qtrip and click on register
        HomePage home = new HomePage(driver);
        // driver.navigate().to("https://qtripdynamic-qa-frontend.vercel.app/");
        home.navigateToHomePage();
        home.clickOnRegisterPage();
        Thread.sleep(3000);
        // Verify that registration page is displayed
        RegisterPage register = new RegisterPage(driver);
        Assert.assertTrue(register.isregisTrationpagedisplayed());
        Thread.sleep(3000);
        // Enter email password and confirmpassword
        // RegisterPage register = new RegisterPage(driver);
        try {
            register.registrationOfNewUser((String) username, (String) password, (String) password,
                    true);
        } catch (Exception e) {
            System.out.println("Failed conversion trying typeCasting with Double--->" + e);
            register.registrationOfNewUser((String) username, Double.toString((Double) password),
                    Double.toString((Double) password), true);

        }
        // click on register now button
        register.clickregisternowButton();
        // Verify that user is navigated to login page
        LoginPage login = new LoginPage(driver);
        Thread.sleep(3000);
        Assert.assertTrue(login.verifyusernavigatedtologinPage());
        // Enter username password and click on login button
        lastgeneratedUsername = register.lastgeneratedUsername;
        try {
            login.loginToQtrip(lastgeneratedUsername, (String) password);
        } catch (Exception e) {
            login.loginToQtrip(lastgeneratedUsername, Double.toString((Double) password));
        } // LoginPage login = new LoginPage(driver);

        login.clickLogin();
        Thread.sleep(3000);
        // Verify that user is logged in
        Assert.assertTrue(home.ishomepageDisplayed());
        // login.loginToQtrip();
        // click logout
        home.clickOnLogout();
        // Verify user is logged out
        Assert.assertTrue(home.ishomepageDisplayed());
        // home.clickOnRegisterPage();
        // register.registrationOfNewUser();
        // login.loginToQtrip();

    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
        // driver = null;
    }
}
