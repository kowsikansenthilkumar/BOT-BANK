package com.bankbot.tests;

import com.bankbot.pages.HomePage;
import com.bankbot.pages.NewAccountPage;
import com.bankbot.pages.NewCustomerPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class ValidationTest extends BankBotTestBase {

    @Test
    public void TC_VAL_001_submitNewCustomerFormWithEmptyFields() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.submit();

        Assert.assertTrue(customerPage.hasValidationError(), "Field validations should display.");
    }

    @Test
    public void TC_VAL_002_enterAlphabetsInNumericFields() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.createCustomer(validCustomerData().withPin("ABCDEF").withPhone("PHONE"));

        Assert.assertTrue(customerPage.getPinError().contains("numbers"), "PIN input should reject alphabets.");
        Assert.assertTrue(customerPage.getPhoneError().contains("numbers"), "Phone input should reject alphabets.");
    }

    @Test
    public void TC_VAL_003_enterSpecialCharactersInPhoneNumber() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.createCustomer(validCustomerData().withPhone("98765@4321"));

        Assert.assertTrue(customerPage.getPhoneError().contains("numbers"), "Validation error should display.");
    }

    @Test
    public void TC_VAL_004_enterInvalidEmailFormat() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.createCustomer(validCustomerData().withEmail("invalid-email"));

        Assert.assertTrue(customerPage.getEmailError().contains("valid format"), "Email validation should trigger.");
    }

    @Test
    public void TC_VAL_005_enterFutureDob() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.createCustomer(validCustomerData().withDateOfBirth(LocalDate.now().plusDays(1).toString()));

        Assert.assertTrue(customerPage.getDobError().contains("future"), "Date validation should trigger.");
    }

    @Test
    public void TC_VAL_006_submitAccountCreationWithoutCustomerId() {
        HomePage homePage = loginAsManager();
        NewAccountPage accountPage = homePage.openNewAccountPage();

        accountPage.createAccount("", "Savings", "1000");

        Assert.assertTrue(accountPage.getCustomerIdError().contains("blank"), "Validation should display.");
    }

    @Test
    public void TC_VAL_007_enterSpacesOnlyInMandatoryFields() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.createCustomer(validCustomerData().withMandatoryText(" "));

        Assert.assertTrue(customerPage.getNameError().contains("blank"), "Name spaces should be rejected.");
        Assert.assertTrue(customerPage.getAddressError().contains("blank"), "Address spaces should be rejected.");
        Assert.assertTrue(customerPage.getCityError().contains("blank"), "City spaces should be rejected.");
        Assert.assertTrue(customerPage.getStateError().contains("blank"), "State spaces should be rejected.");
    }

    @Test
    public void TC_VAL_008_verifyResetButtonClearsForm() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.fillCustomer(validCustomerData());
        customerPage.reset();

        Assert.assertEquals(customerPage.getNameValue(), "", "Name should clear.");
        Assert.assertEquals(customerPage.getAddressValue(), "", "Address should clear.");
        Assert.assertEquals(customerPage.getCityValue(), "", "City should clear.");
        Assert.assertEquals(customerPage.getStateValue(), "", "State should clear.");
    }
}
