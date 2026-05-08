package com.bankbot.pages;

import com.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewAccountPage extends BasePage {
    private final By customerIdInput = By.id("account-customer-id");
    private final By accountTypeSelect = By.id("account-type");
    private final By initialDepositInput = By.id("account-initial-deposit");
    private final By submitButton = By.id("account-submit");
    private final By successMessage = By.id("account-success");
    private final By errorMessage = By.id("account-error");
    private final By generatedAccountNumber = By.id("generated-account-number");

    public NewAccountPage(WebDriver driver) {
        super(driver);
    }

    public void createAccount(String customerId, String accountType, String initialDeposit) {
        type(customerId, customerIdInput, By.name("cusid"));
        selectByVisibleText(accountType, accountTypeSelect, By.name("selaccount"));
        type(initialDeposit, initialDepositInput, By.name("inideposit"));
        click(submitButton, By.name("button2"));
        acceptAlertIfPresent();
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage, By.xpath("//*[contains(normalize-space(), 'Account Generated Successfully')]"));
    }

    public String getGeneratedAccountNumber() {
        return text(generatedAccountNumber, By.xpath("//td[normalize-space()='Account ID']/following-sibling::td"));
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage, By.id("account-customer-id-error"), By.id("account-initial-deposit-error"));
    }

    public String getCustomerIdError() {
        return text(By.id("account-customer-id-error"));
    }

    public String getInitialDepositError() {
        return text(By.id("account-initial-deposit-error"));
    }

    public boolean isAccountListed(String accountNumber) {
        return isDisplayed(By.cssSelector("[data-account-number='" + accountNumber + "']"));
    }

    public String getListedAccountType(String accountNumber) {
        return text(By.cssSelector("[data-account-number='" + accountNumber + "'] .account-type"));
    }

    public double getListedBalance(String accountNumber) {
        return Double.parseDouble(text(By.cssSelector("[data-account-number='" + accountNumber + "'] .account-balance")));
    }
}
