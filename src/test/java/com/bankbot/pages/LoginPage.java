package com.bankbot.pages;

import com.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By successMessage = By.id("success-message");
    private final By errorMessage = By.id("error-message");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    public boolean isLoginSuccessful() {
        return isDisplayed(successMessage);
    }

    public boolean isLoginErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getSuccessMessage() {
        return text(successMessage);
    }

    public String getErrorMessage() {
        return text(errorMessage);
    }
}
