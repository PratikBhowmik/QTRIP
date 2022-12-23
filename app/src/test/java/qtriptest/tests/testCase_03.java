package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase_03 {

    RemoteWebDriver driver;
    ExtentReports report;
    ExtentTest test;
    public static String lastgeneratedUsername;

    @BeforeSuite
    public void setup() throws MalformedURLException {
        DriverSingleton ds = DriverSingleton.getInstance();
        driver = ds.getDriver();
        ReportSingleton reportObj = ReportSingleton.getInstance();
        report = reportObj.getReport();
        test = report.startTest("Test case 3 ");
        report.loadConfig(new File(System.getProperty("user.dir")
                + "/src/test/java/qtriptest/extentReportConfig.xml"));
    }

    @Test(dataProvider = "my data provider", dataProviderClass = DP.class, priority = 3,
            groups = {"Booking and Cancellation Flow"})
    public void TestCase03(String username, String password, String SearchCity,
            String AdventureName, String GuestName, String Date, String count)
            throws InterruptedException, IOException {
        // 1. Navigate to QTrip
        // 2. Create a new User
        // 3. Search for an adventure
        // 4. Enter Name and Date and Reserve the adventure
        // 5. Verify that the adventure booking was successful
        // 6. Click on the history page
        // 7. Note down the transaction ID
        // 8. Cancel the Reservation
        // 9. Refresh the page
        // 10. Check if the transaction ID is removed
        HomePage home = new HomePage(driver);
        home.navigateToHomePage();
        home.clickOnRegisterPage();
        RegisterPage reg = new RegisterPage(driver);
        reg.registrationOfNewUser(username, password, password, true);
        reg.clickregisternowButton();
        LoginPage login = new LoginPage(driver);
        Thread.sleep(3000);
        lastgeneratedUsername = reg.lastgeneratedUsername;
        login.loginToQtrip(lastgeneratedUsername, password);
        login.clickLogin();
        Thread.sleep(3000);
        home.searchCity(SearchCity);
        Thread.sleep(3000);
        home.selectCity(SearchCity);
        Thread.sleep(3000);
        AdventurePage ad = new AdventurePage(driver);
        ad.selectAdventure(AdventureName);
        Thread.sleep(3000);
        AdventureDetailsPage advdetails = new AdventureDetailsPage(driver);
        advdetails.bookAdventure(GuestName, Date, count);
        Thread.sleep(3000);
        Assert.assertTrue(advdetails.isBookingSuccessful());
        HistoryPage bookingHistory = new HistoryPage(driver);
        bookingHistory.clickOnReservation();
        Thread.sleep(3000);
        bookingHistory.noteTransactionId();
        Thread.sleep(3000);
        bookingHistory.cancelReservations();
        Thread.sleep(3000);
        bookingHistory.refreshPage();
        Thread.sleep(3000);
        Assert.assertTrue(bookingHistory.isTransactionIdRemoved());
        Thread.sleep(3000);
        home.clickOnLogout();

        boolean status = bookingHistory.isTransactionIdRemoved();

        if (status) {
            test.log(LogStatus.PASS, "Test for user booking and cancellation passed");
        } else {
            test.log(LogStatus.FAIL, "Test for user booking and cancellation failed");
            test.log(LogStatus.FAIL, test.addScreenCapture(capture(driver)) + "Test failed");
        }

    }

    public String capture(WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "/src/test/java/QtripFailedSS/"
                + System.currentTimeMillis() + ".png");
        String errflpath = dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, dest);
        return errflpath;
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
        report.endTest(test);
        report.flush();
    }
}
