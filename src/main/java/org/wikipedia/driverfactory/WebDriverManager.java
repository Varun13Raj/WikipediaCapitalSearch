package org.wikipedia.driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

public class WebDriverManager {

    public static WebDriver getDriver(String browserName, String browserVersion, String osVersion, String platformName) throws MalformedURLException {
        WebDriver driver;
        String url;

        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }

        String username = System.getProperty("LT_USERNAME", properties.getProperty("LT_USERNAME"));
        String accessKey = System.getProperty("LT_ACCESS_KEY", properties.getProperty("LT_ACCESS_KEY"));

        if (username == null || accessKey == null) {
            throw new IllegalArgumentException("LambdaTest username and access key must be specified in the config.properties file.");
        }

        HashMap<String, Object> options = new HashMap<>();
        options.put("browserName", browserName);
        options.put("browserVersion", browserVersion);
        options.put("os", osVersion);

        if ("LambdaTest".equalsIgnoreCase(platformName)) {
            url = "https://hub.lambdatest.com/wd/hub";
            options.put("username", username);
            options.put("accessKey", accessKey);
            options.put("build", "WikipediaTestBuild");
            options.put("project", "WikipediaTestProject");

        } else if ("Local".equalsIgnoreCase(platformName)) {                    // If we want to run our test locally instead of LambdaTest.
            url = null;
        } else {
            throw new IllegalArgumentException("Unsupported platform: " + platformName);
        }

        switch (browserName.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPlatformName(osVersion);
                chromeOptions.setBrowserVersion(browserVersion);
                if (url != null) chromeOptions.setCapability("LT:Options", options);
                driver = url == null ? new ChromeDriver(chromeOptions) : new RemoteWebDriver(new URL(url), chromeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPlatformName(osVersion);
                firefoxOptions.setBrowserVersion(browserVersion);
                if (url != null) firefoxOptions.setCapability("LT:Options", options);
                driver = url == null ? new FirefoxDriver(firefoxOptions) : new RemoteWebDriver(new URL(url), firefoxOptions);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setPlatformName(osVersion);
                edgeOptions.setBrowserVersion(browserVersion);
                if (url != null) edgeOptions.setCapability("LT:Options", options);
                driver = url == null ? new EdgeDriver(edgeOptions) : new RemoteWebDriver(new URL(url), edgeOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        return driver;
    }
}

