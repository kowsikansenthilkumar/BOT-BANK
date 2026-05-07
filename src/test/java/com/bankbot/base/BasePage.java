package com.bankbot.base;

import com.bankbot.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WaitUtils waitUtils;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    protected WebElement visible(By locator) {
        return waitUtils.waitForVisibility(locator);
    }

    protected void click(By locator) {
        waitUtils.waitForClickable(locator).click();
    }

    protected void type(By locator, String value) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected String text(By locator) {
        return visible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        return waitUtils.isVisible(locator);
    }
}
