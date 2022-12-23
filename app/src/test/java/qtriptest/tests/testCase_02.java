package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
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
import org.testng.asserts.SoftAssert;

public class testCase_02 {

    RemoteWebDriver driver;
    ExtentReports report;
    ExtentTest test;
    SoftAssert softassert = new SoftAssert();

    @BeforeSuite
    public void setup() throws MalformedURLException {

        DriverSingleton ds = DriverSingleton.getInstance();
        driver = ds.getDriver();
        ReportSingleton reportObj = ReportSingleton.getInstance();
        report = reportObj.getReport();
        test = report.startTest("Test case 2 ");
        report.loadConfig(new File(System.getProperty("user.dir")
                + "/src/test/java/qtriptest/extentReportConfig.xml"));

    }

    @Test(dataProvider = "my data provider", dataProviderClass = DP.class, priority = 2,
            groups = {"Search and Filter flow"})
    public void TestCase02(String city, String categoryFilter, String durationFilter,
            String expectedFilterResult, String expectedUnfilterResult)
            throws InterruptedException, IOException {

        // 1. Navigate to the Home page of QTrip
        // 2. Search for a city that's not present
        // 3. Verify if the no matches found message is displayed
        // 4. Search for a city that's present
        // 5. verify that the city is displayed on auto complete
        // 6. Click on the city
        // 7. Select Filters : hours
        // 8. Verify that appropriate data is displayed
        // 9. Select category
        // 10.Verify that appropriate data is displayed
        // 11.Clear Filters and category
        // 12.Verify that all the records are displayed

        HomePage home = new HomePage(driver);
        home.navigateToHomePage();
        Thread.sleep(3000);
        home.searchCity("Tripura");
        Assert.assertTrue(home.assertAutoCompleteText("No City found"));
        Thread.sleep(3000);
        home.searchCity(city);
        Thread.sleep(3000);
        home.selectCity(city);

        AdventurePage adventurePageTest = new AdventurePage(driver);
        adventurePageTest.setFilterValue(durationFilter);
        adventurePageTest.setCategoryValue(categoryFilter);
        Thread.sleep(3000);
        Assert.assertTrue(adventurePageTest.getResultCount() > 0);
        adventurePageTest.clearCategory();
        Thread.sleep(3000);
        Assert.assertTrue(adventurePageTest.getResultCount() > 0);

        boolean status = adventurePageTest.getResultCount() > 0;
        if (status) {
            test.log(LogStatus.PASS, "Test for searching and filters passed");
        } else {
            test.log(LogStatus.FAIL, "Test for searching and filters failed");
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
