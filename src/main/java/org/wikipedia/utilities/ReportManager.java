package org.wikipedia.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String filePath = "test-output/ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filePath);
            sparkReporter.config().setReportName("Automation Test Report");
            sparkReporter.config().setDocumentTitle("Lambda Test Results");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Environment", "LambdaTest");
            extent.setSystemInfo("Tester", "Varun Raj");
        }
        return extent;
    }
}
