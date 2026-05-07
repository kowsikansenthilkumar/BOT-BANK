package com.bankbot.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentManager {
    private static ExtentReports extentReports;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            String reportsDir = ConfigReader.get("reports.dir", "reports");
            Path reportPath = Path.of(reportsDir, "ExtentReport.html");
            try {
                Files.createDirectories(reportPath.getParent());
                Files.createDirectories(Path.of(ConfigReader.get("screenshots.dir", "screenshots")));
            } catch (Exception e) {
                throw new IllegalStateException("Unable to create report or screenshot output directories.", e);
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath.toString());
            sparkReporter.config().setDocumentTitle("BOT-BANK Automation Report");
            sparkReporter.config().setReportName("BOT-BANK Test Execution");
            sparkReporter.config().setTheme(Theme.STANDARD);

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Framework", "Selenium Java TestNG POM");
            extentReports.setSystemInfo("Browser", ConfigReader.get("browser", "chrome"));
            extentReports.setSystemInfo("Headless", ConfigReader.get("headless", "true"));
        }
        return extentReports;
    }
}
