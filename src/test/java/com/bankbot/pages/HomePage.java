package com.bankbot.pages;

import com.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By dashboard = By.id("dashboard");
    private final By guru99ManagerHome = By.xpath("//*[contains(normalize-space(), 'Manger Id') or contains(normalize-space(), 'Manager')]");
    private final By loginForm = By.id("login-form");
    private final By logoutButton = By.id("logout-button");
    private final By guru99Logout = By.linkText("Log out");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardDisplayed() {
        return isDisplayed(dashboard, guru99ManagerHome);
    }

    public boolean isDashboardDisplayedNow() {
        return isDisplayedNow(dashboard) || isDisplayedNow(guru99ManagerHome);
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(loginForm, By.name("uid"));
    }

    public LoginPage logout() {
        click(logoutButton, guru99Logout, By.xpath("//a[normalize-space()='Log out']"));
        acceptAlertIfPresent();
        return new LoginPage(driver);
    }

    public NewCustomerPage openNewCustomerPage() {
        click(By.id("nav-new-customer"), By.linkText("New Customer"), By.xpath("//a[normalize-space()='New Customer']"));
        return new NewCustomerPage(driver);
    }

    public EditCustomerPage openEditCustomerPage() {
        click(By.id("nav-edit-customer"), By.linkText("Edit Customer"), By.xpath("//a[normalize-space()='Edit Customer']"));
        return new EditCustomerPage(driver);
    }

    public DeleteCustomerPage openDeleteCustomerPage() {
        click(By.id("nav-delete-customer"), By.linkText("Delete Customer"), By.xpath("//a[normalize-space()='Delete Customer']"));
        return new DeleteCustomerPage(driver);
    }

    public NewAccountPage openNewAccountPage() {
        click(By.id("nav-new-account"), By.linkText("New Account"), By.xpath("//a[normalize-space()='New Account']"));
        return new NewAccountPage(driver);
    }

    public EditAccountPage openEditAccountPage() {
        click(By.id("nav-edit-account"), By.linkText("Edit Account"), By.xpath("//a[normalize-space()='Edit Account']"));
        return new EditAccountPage(driver);
    }

    public FundTransferPage openFundTransferPage() {
        click(By.id("nav-fund-transfer"), By.linkText("Fund Transfer"), By.xpath("//a[normalize-space()='Fund Transfer']"));
        return new FundTransferPage(driver);
    }
}
