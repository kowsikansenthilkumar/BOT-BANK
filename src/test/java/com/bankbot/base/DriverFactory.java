package com.bankbot.base;

import com.bankbot.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initDriver() {
        if (DRIVER.get() != null) {
            return;
        }

        String browser = System.getProperty("browser", ConfigReader.get("browser", "chrome")).trim().toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", ConfigReader.get("headless", "true")));

        if (!"chrome".equals(browser)) {
            throw new IllegalArgumentException("Unsupported browser: " + browser + ". Only chrome is configured.");
        }

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1440,900");

        WebDriver driver = new ChromeDriver(options);
        long implicitWait = ConfigReader.getLong("implicit.wait.seconds", 0);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().window().maximize();
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call DriverFactory.initDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
