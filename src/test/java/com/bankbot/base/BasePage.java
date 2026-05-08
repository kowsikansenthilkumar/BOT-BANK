package com.bankbot.base;

import com.bankbot.utils.WaitUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WaitUtils waitUtils;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    protected WebElement visible(By... locators) {
        return waitUtils.waitForAnyVisibility(locators);
    }

    protected void click(By... locators) {
        waitUtils.waitForAnyClickable(locators).click();
    }

    protected void type(By locator, String value) {
        WebElement element = visible(locator);
        element.clear();
        setElementValue(element, value);
    }

    protected void type(String value, By... locators) {
        WebElement element = visible(locators);
        element.clear();
        setElementValue(element, value);
    }

    private void setElementValue(WebElement element, String value) {
        if ("date".equalsIgnoreCase(element.getAttribute("type"))) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                    element,
                    value
            );
            return;
        }
        element.sendKeys(value);
    }

    protected void selectByVisibleText(String option, By... locators) {
        new Select(visible(locators)).selectByVisibleText(option);
    }

    protected String text(By... locators) {
        return visible(locators).getText();
    }

    protected String value(By... locators) {
        return visible(locators).getAttribute("value");
    }

    protected boolean isDisplayed(By... locators) {
        return waitUtils.isAnyVisible(locators);
    }

    protected boolean isDisplayedNow(By locator) {
        return driver.findElements(locator).stream().anyMatch(WebElement::isDisplayed);
    }

    protected boolean isTextPresent(String expectedText) {
        return driver.getPageSource().contains(expectedText);
    }

    protected String acceptAlertIfPresent() {
        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            alert.accept();
            return alertText;
        } catch (TimeoutException e) {
            return "";
        }
    }
}
