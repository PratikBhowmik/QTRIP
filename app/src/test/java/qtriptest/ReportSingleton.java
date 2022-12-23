package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import com.relevantcodes.extentreports.ExtentReports;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ReportSingleton {

    private ExtentReports report = null;

    private static ReportSingleton instanceOfReportSingletonClass = null;

    private ReportSingleton() throws MalformedURLException {
        report = new ExtentReports(System.getProperty("user.dir") + "/src/test/java/qtriptest/ExtentReport.html", true);
    }

    public static ReportSingleton getInstance() throws MalformedURLException {

        if (instanceOfReportSingletonClass == null) {
            instanceOfReportSingletonClass = new ReportSingleton();

        }
        return instanceOfReportSingletonClass;
    }

    public ExtentReports getReport() {
        return report;
    }
}
