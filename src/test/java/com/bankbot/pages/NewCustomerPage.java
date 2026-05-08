package com.bankbot.pages;

import com.bankbot.base.BasePage;
import com.bankbot.testdata.CustomerData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewCustomerPage extends BasePage {
    private final By nameInput = By.id("customer-name");
    private final By genderSelect = By.id("customer-gender");
    private final By dobInput = By.id("customer-dob");
    private final By addressInput = By.id("customer-address");
    private final By cityInput = By.id("customer-city");
    private final By stateInput = By.id("customer-state");
    private final By pinInput = By.id("customer-pin");
    private final By phoneInput = By.id("customer-phone");
    private final By emailInput = By.id("customer-email");
    private final By passwordInput = By.id("customer-password");
    private final By submitButton = By.id("customer-submit");
    private final By resetButton = By.id("customer-reset");
    private final By successMessage = By.id("customer-success");
    private final By errorMessage = By.id("customer-error");
    private final By generatedCustomerId = By.id("generated-customer-id");

    public NewCustomerPage(WebDriver driver) {
        super(driver);
    }

    public void fillCustomer(CustomerData customer) {
        type(customer.name(), nameInput, By.name("name"));
        selectByVisibleText(customer.gender(), genderSelect, By.name("rad1"));
        type(customer.dateOfBirth(), dobInput, By.name("dob"));
        type(customer.address(), addressInput, By.name("addr"));
        type(customer.city(), cityInput, By.name("city"));
        type(customer.state(), stateInput, By.name("state"));
        type(customer.pin(), pinInput, By.name("pinno"));
        type(customer.phone(), phoneInput, By.name("telephoneno"));
        type(customer.email(), emailInput, By.name("emailid"));
        type(customer.password(), passwordInput, By.name("password"));
    }

    public void submit() {
        click(submitButton, By.name("sub"));
        acceptAlertIfPresent();
    }

    public void createCustomer(CustomerData customer) {
        fillCustomer(customer);
        submit();
    }

    public void reset() {
        click(resetButton, By.name("res"));
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage, By.xpath("//*[contains(normalize-space(), 'Customer Registered Successfully')]"));
    }

    public String getSuccessMessage() {
        return text(successMessage, By.xpath("//*[contains(normalize-space(), 'Customer Registered Successfully')]"));
    }

    public String getGeneratedCustomerId() {
        return text(generatedCustomerId, By.xpath("//td[normalize-space()='Customer ID']/following-sibling::td"));
    }

    public boolean isDuplicateEmailErrorDisplayed() {
        return isDisplayed(errorMessage, By.id("customer-email-error"), By.xpath("//*[contains(normalize-space(), 'Email already exists')]"));
    }

    public boolean hasValidationError() {
        return isDisplayed(
                By.id("customer-name-error"),
                By.id("customer-gender-error"),
                By.id("customer-dob-error"),
                By.id("customer-address-error"),
                By.id("customer-city-error"),
                By.id("customer-state-error"),
                By.id("customer-pin-error"),
                By.id("customer-phone-error"),
                By.id("customer-email-error"),
                By.id("customer-password-error"),
                By.xpath("//*[contains(@class, 'field-error') and string-length(normalize-space()) > 0]")
        );
    }

    public String getNameError() {
        return text(By.id("customer-name-error"), By.id("message"));
    }

    public String getDobError() {
        return text(By.id("customer-dob-error"));
    }

    public String getPinError() {
        return text(By.id("customer-pin-error"));
    }

    public String getPhoneError() {
        return text(By.id("customer-phone-error"));
    }

    public String getEmailError() {
        return text(By.id("customer-email-error"));
    }

    public String getAddressError() {
        return text(By.id("customer-address-error"));
    }

    public String getCityError() {
        return text(By.id("customer-city-error"));
    }

    public String getStateError() {
        return text(By.id("customer-state-error"));
    }

    public String getNameValue() {
        return value(nameInput);
    }

    public String getAddressValue() {
        return value(addressInput);
    }

    public String getCityValue() {
        return value(cityInput);
    }

    public String getStateValue() {
        return value(stateInput);
    }

    public boolean isCustomerListed(String customerId) {
        return isDisplayed(By.cssSelector("[data-customer-id='" + customerId + "']"));
    }
}
