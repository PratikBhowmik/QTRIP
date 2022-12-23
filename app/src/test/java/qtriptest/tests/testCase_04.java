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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_04 {

    RemoteWebDriver driver;
    ExtentReports report;
    ExtentTest test;
    public static String lastgeneratedUsername;
    public static String cityName;
    public static String adventureName;
    public static String guestName;
    public static String date;
    public static String personCount;

    // 1. Navigate to QTrip
    // 2.Create a new User
    // 3. Search for an adventure
    // 4. Enter Name and Date and Reserve the adventure
    // 5. Verify that the adventure booking was successful
    // 6. Repeat steps 3-5 to make two more bookings
    // 7. Click on the History Page
    // 8. Check if all the bookings are displayed on the history page
    @BeforeTest
    public void setup() throws MalformedURLException {
        DriverSingleton driverObj = DriverSingleton.getInstance();
        driver = driverObj.getDriver();
        ReportSingleton reportObj = ReportSingleton.getInstance();
        report = reportObj.getReport();
        test = report.startTest("Test case 4 ");
        report.loadConfig(new File(System.getProperty("user.dir")
                + "/src/test/java/qtriptest/extentReportConfig.xml"));

    }

    @Test(dataProvider = "my data provider", dataProviderClass = DP.class, priority = 4,
            groups = {"Reliability Flow"})
    public void TestCase04(String username, String password, String dataset1, String dataset2,
            String dataset3) throws InterruptedException, IOException {

        // 1. Navigate to QTrip
        // 2. Create a new User
        // 3. Search for an adventure
        // 4. Enter Name and Date and Reserve the adventure
        // 5. Verify that the adventure booking was successful
        // 6. Repeat steps 3-5 to make two more bookings
        // 7. Click on the History Page
        // 8. Check if all the bookings are displayed on the history page

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
        AdventurePage adv = new AdventurePage(driver);
        AdventureDetailsPage advDetails = new AdventureDetailsPage(driver);
        String[] dataset1Array = dataset1.split(";");
        Assert.assertTrue(reserveAdventure(home, adv, advDetails, dataset1Array));
        String[] dataset2Array = dataset2.split(";");
        home.navigateToHomePage();
        Thread.sleep(3000);
        Assert.assertTrue(reserveAdventure(home, adv, advDetails, dataset2Array));
        String[] dataset3Array = dataset3.split(";");
        home.navigateToHomePage();
        Thread.sleep(3000);
        Assert.assertTrue(reserveAdventure(home, adv, advDetails, dataset3Array));
        Thread.sleep(5000);
        HistoryPage history = new HistoryPage(driver);
        history.clickOnReservation();
        Thread.sleep(3000);
        Assert.assertTrue(history.areAlltheBookingsPresent());
        Thread.sleep(3000);
        home.clickOnLogout();

        boolean status = history.areAlltheBookingsPresent();
        if (status) {
            test.log(LogStatus.PASS, "Test for booking is successfull");

        } else {
            test.log(LogStatus.FAIL, "Test failed for booking is successfull");
            test.log(LogStatus.FAIL, test.addScreenCapture(capture(driver)) + "Test failed");
        }

    }

    public boolean reserveAdventure(HomePage home, AdventurePage adv,
            AdventureDetailsPage advDetails, String[] data) throws InterruptedException {
        cityName = data[0];
        adventureName = data[1];
        guestName = data[2];
        date = data[3];
        personCount = data[4];
        home.searchCity(cityName);
        Thread.sleep(3000);
        home.selectCity(cityName);
        Thread.sleep(3000);
        adv.selectAdventure(adventureName);
        Thread.sleep(3000);
        advDetails.bookAdventure(guestName, date, personCount);
        Thread.sleep(3000);
        return advDetails.isBookingSuccessful();
    }

    public String capture(WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "/src/test/java/QtripFailedSS/"
                + System.currentTimeMillis() + ".png");
        String errflpath = dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, dest);
        return errflpath;
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
        report.endTest(test);
        report.flush();
    }
}
