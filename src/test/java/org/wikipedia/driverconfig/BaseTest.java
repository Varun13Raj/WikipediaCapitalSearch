package org.wikipedia.driverconfig;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.wikipedia.driverfactory.WebDriverManager;
import org.wikipedia.pages.SearchPage;

import java.net.MalformedURLException;

public class BaseTest {
    public SearchPage searchPage;
    public WebDriver driver;

    @Parameters({"browserName", "browserVersion", "osVersion", "platformName"})
    @BeforeClass
    public void setUp(
            @Optional("Chrome") String browserName,
            @Optional("latest") String browserVersion,
            @Optional("Windows 10") String osVersion,
            @Optional("LambdaTest") String platformName,
            ITestContext context
    ) throws MalformedURLException {
        browserName = System.getProperty("browserName", browserName);
        browserVersion = System.getProperty("browserVersion", browserVersion);
        osVersion = System.getProperty("osVersion", osVersion);
        platformName = System.getProperty("platformName", platformName);

        driver = WebDriverManager.getDriver(browserName, browserVersion, osVersion, platformName);
        driver.manage().window().maximize();
        searchPage = new SearchPage(driver);
        context.setAttribute("driver", driver);
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        System.out.println("launching driver " );
    }

//    @BeforeMe
//    public void getUrl() {
//        if (driver == null) {
//            throw new IllegalStateException("Driver is not initialized. Check @BeforeTest setup.");
//        }
//
//    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
