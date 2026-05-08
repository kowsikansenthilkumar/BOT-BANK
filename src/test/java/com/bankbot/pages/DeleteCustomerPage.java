package com.bankbot.pages;

import com.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeleteCustomerPage extends BasePage {
    private final By customerIdInput = By.id("delete-customer-id");
    private final By deleteButton = By.id("delete-customer-submit");
    private final By successMessage = By.id("delete-customer-success");
    private final By errorMessage = By.id("delete-customer-error");

    public DeleteCustomerPage(WebDriver driver) {
        super(driver);
    }

    public void deleteCustomer(String customerId) {
        type(customerId, customerIdInput, By.name("cusid"));
        click(deleteButton, By.name("AccSubmit"));
        acceptAlertIfPresent();
        acceptAlertIfPresent();
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage, By.xpath("//*[contains(normalize-space(), 'deleted successfully')]"));
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public boolean isCustomerAbsent(String customerId) {
        return !isDisplayedNow(By.cssSelector("[data-customer-id='" + customerId + "']"));
    }
}
