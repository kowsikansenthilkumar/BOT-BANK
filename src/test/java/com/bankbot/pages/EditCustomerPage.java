package com.bankbot.pages;

import com.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditCustomerPage extends BasePage {
    private final By customerIdInput = By.id("edit-customer-id");
    private final By loadButton = By.id("edit-customer-load");
    private final By addressInput = By.id("edit-customer-address");
    private final By cityInput = By.id("edit-customer-city");
    private final By stateInput = By.id("edit-customer-state");
    private final By saveButton = By.id("edit-customer-save");
    private final By successMessage = By.id("edit-customer-success");

    public EditCustomerPage(WebDriver driver) {
        super(driver);
    }

    public void loadCustomer(String customerId) {
        type(customerId, customerIdInput, By.name("cusid"));
        click(loadButton, By.name("AccSubmit"));
        acceptAlertIfPresent();
    }

    public void updateAddress(String address) {
        type(address, addressInput, By.name("addr"));
        click(saveButton, By.name("sub"));
        acceptAlertIfPresent();
    }

    public void updateCityAndState(String city, String state) {
        type(city, cityInput, By.name("city"));
        type(state, stateInput, By.name("state"));
        click(saveButton, By.name("sub"));
        acceptAlertIfPresent();
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage, By.xpath("//*[contains(normalize-space(), 'updated successfully')]"));
    }

    public String getAddressValue() {
        return value(addressInput, By.name("addr"));
    }

    public String getCityValue() {
        return value(cityInput, By.name("city"));
    }

    public String getStateValue() {
        return value(stateInput, By.name("state"));
    }
}
