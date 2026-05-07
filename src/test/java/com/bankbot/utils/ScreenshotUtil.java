package com.bankbot.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtil() {
    }

    public static String capture(WebDriver driver, String testName) {
        String safeTestName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path screenshotsDir = Path.of(ConfigReader.get("screenshots.dir", "screenshots"));
        try {
            Files.createDirectories(screenshotsDir);
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destination = screenshotsDir.resolve(safeTestName + "_" + LocalDateTime.now().format(FORMATTER) + ".png");
            Files.copy(screenshot.toPath(), destination);
            return destination.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to capture screenshot for " + testName, e);
        }
    }
}
