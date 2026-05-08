package com.bankbot.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        long waitSeconds = ConfigReader.getLong("explicit.wait.seconds", 10);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
    }

    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForAnyVisibility(By... locators) {
        return wait.until(driver -> {
            for (By locator : locators) {
                for (WebElement element : driver.findElements(locator)) {
                    if (element.isDisplayed()) {
                        return element;
                    }
                }
            }
            return null;
        });
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForAnyClickable(By... locators) {
        return wait.until(driver -> {
            for (By locator : locators) {
                for (WebElement element : driver.findElements(locator)) {
                    if (element.isDisplayed() && element.isEnabled()) {
                        return element;
                    }
                }
            }
            return null;
        });
    }

    public boolean isVisible(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isAnyVisible(By... locators) {
        try {
            return waitForAnyVisibility(locators).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
