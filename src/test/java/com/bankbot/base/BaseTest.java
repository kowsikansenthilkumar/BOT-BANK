package com.bankbot.base;

import com.bankbot.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.URISyntaxException;
import java.net.URL;

public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initDriver();
        getDriver().get(resolveBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    private String resolveBaseUrl() {
        String configuredUrl = ConfigReader.get("base.url", "").trim();
        if (!configuredUrl.isEmpty()) {
            return configuredUrl;
        }

        URL loginPage = BaseTest.class.getClassLoader().getResource("login.html");
        if (loginPage == null) {
            throw new IllegalStateException("Default login.html test resource was not found.");
        }

        try {
            return loginPage.toURI().toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable to resolve login.html resource URL.", e);
        }
    }
}
