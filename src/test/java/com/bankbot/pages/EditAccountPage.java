package com.bankbot.pages;

import com.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditAccountPage extends BasePage {
    private final By accountNumberInput = By.id("edit-account-number");
    private final By loadButton = By.id("edit-account-load");
    private final By accountTypeSelect = By.id("edit-account-type");
    private final By saveButton = By.id("edit-account-save");
    private final By successMessage = By.id("edit-account-success");

    public EditAccountPage(WebDriver driver) {
        super(driver);
    }

    public void loadAccount(String accountNumber) {
        type(accountNumber, accountNumberInput, By.name("accountno"));
        click(loadButton, By.name("AccSubmit"));
        acceptAlertIfPresent();
    }

    public void updateAccountType(String accountType) {
        selectByVisibleText(accountType, accountTypeSelect, By.name("selaccount"));
        click(saveButton, By.name("sub"));
        acceptAlertIfPresent();
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage, By.xpath("//*[contains(normalize-space(), 'updated successfully')]"));
    }

    public String getAccountTypeValue() {
        return value(accountTypeSelect, By.name("selaccount"));
    }
}
