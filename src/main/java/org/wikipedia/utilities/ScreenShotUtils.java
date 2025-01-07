package org.wikipedia.utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ScreenShotUtils {
    public static String captureScreenshotOnTestFail(WebDriver driver, ITestResult result) {
        String screenshotPath = "test-output/screenshots/" + result.getMethod().getMethodName() + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.createDirectories(new File("test-output/screenshots").toPath());
            Files.copy(screenshot.toPath(), new File(screenshotPath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
}
