package com.bankbot.pages;

import com.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usernameInput = By.id("username");
    private final By guru99UsernameInput = By.name("uid");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By guru99LoginButton = By.name("btnLogin");
    private final By successMessage = By.id("success-message");
    private final By dashboard = By.id("dashboard");
    private final By guru99ManagerHome = By.xpath("//*[contains(normalize-space(), 'Manger Id') or contains(normalize-space(), 'Manager')]");
    private final By errorMessage = By.id("error-message");
    private final By usernameError = By.id("username-error");
    private final By passwordError = By.id("password-error");
    private String lastAlertText = "";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        lastAlertText = "";
        type(username, usernameInput, guru99UsernameInput);
        type(password, passwordInput, By.name("password"));
        click(loginButton, guru99LoginButton);
        lastAlertText = acceptAlertIfPresent();
    }

    public boolean isLoginSuccessful() {
        return isDisplayed(dashboard, successMessage, guru99ManagerHome);
    }

    public boolean isLoginErrorDisplayed() {
        return !lastAlertText.isBlank() || isDisplayed(errorMessage);
    }

    public String getSuccessMessage() {
        if (isDisplayedNow(dashboard)) {
            return "Login successful";
        }
        return text(successMessage);
    }

    public String getErrorMessage() {
        if (!lastAlertText.isBlank()) {
            return lastAlertText;
        }
        return text(errorMessage);
    }

    public boolean isUsernameValidationDisplayed() {
        return !lastAlertText.isBlank() || isDisplayed(usernameError);
    }

    public boolean isPasswordValidationDisplayed() {
        return !lastAlertText.isBlank() || isDisplayed(passwordError);
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(usernameInput, guru99UsernameInput);
    }
}
