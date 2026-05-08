package com.bankbot.pages;

import com.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FundTransferPage extends BasePage {
    private final By payerAccountInput = By.id("transfer-payer-account");
    private final By payeeAccountInput = By.id("transfer-payee-account");
    private final By amountInput = By.id("transfer-amount");
    private final By descriptionInput = By.id("transfer-description");
    private final By submitButton = By.id("transfer-submit");
    private final By successMessage = By.id("transfer-success");
    private final By errorMessage = By.id("transfer-error");

    public FundTransferPage(WebDriver driver) {
        super(driver);
    }

    public void transfer(String payerAccount, String payeeAccount, String amount, String description) {
        type(payerAccount, payerAccountInput, By.name("payersaccount"));
        type(payeeAccount, payeeAccountInput, By.name("payeeaccount"));
        type(amount, amountInput, By.name("ammout"));
        type(description, descriptionInput, By.name("desc"));
        click(submitButton, By.name("accSubmit"));
        acceptAlertIfPresent();
    }

    public void submitBlank() {
        click(submitButton, By.name("accSubmit"));
        acceptAlertIfPresent();
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage, By.xpath("//*[contains(normalize-space(), 'Fund Transfer Details')]"));
    }

    public String getSuccessMessage() {
        return text(successMessage, By.xpath("//*[contains(normalize-space(), 'Fund Transfer Details')]"));
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage, By.id("transfer-payer-account-error"), By.id("transfer-payee-account-error"), By.id("transfer-amount-error"));
    }

    public String getAmountError() {
        return text(By.id("transfer-amount-error"));
    }

    public String getPayeeError() {
        return text(By.id("transfer-payee-account-error"));
    }

    public boolean hasRequiredValidation() {
        return isDisplayed(
                By.id("transfer-payer-account-error"),
                By.id("transfer-payee-account-error"),
                By.id("transfer-amount-error"),
                By.id("transfer-description-error")
        );
    }

    public double getAccountBalance(String accountNumber) {
        String balance = driver.findElement(By.cssSelector("[data-account-number='" + accountNumber + "'] .account-balance"))
                .getAttribute("textContent")
                .trim();
        return Double.parseDouble(balance);
    }
}
