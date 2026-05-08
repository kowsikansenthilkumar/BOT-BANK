package com.bankbot.tests;

import com.bankbot.pages.DeleteCustomerPage;
import com.bankbot.pages.EditCustomerPage;
import com.bankbot.pages.HomePage;
import com.bankbot.pages.NewCustomerPage;
import com.bankbot.testdata.CustomerData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class CustomerTest extends BankBotTestBase {

    @Test
    public void TC_CUST_001_createNewCustomerWithValidDetails() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.createCustomer(validCustomerData());

        Assert.assertFalse(customerPage.getGeneratedCustomerId().isBlank(), "Customer ID should generate.");
    }

    @Test
    public void TC_CUST_002_verifySuccessMessageAfterCustomerCreation() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.createCustomer(validCustomerData());

        Assert.assertTrue(customerPage.isSuccessMessageDisplayed(), "Success message should display.");
    }

    @Test
    public void TC_CUST_003_createCustomerWithExistingEmail() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();
        CustomerData customerData = validCustomerData();
        customerPage.createCustomer(customerData);

        customerPage.createCustomer(validCustomerData().withEmail(customerData.email()));

        Assert.assertTrue(customerPage.isDuplicateEmailErrorDisplayed(), "Duplicate email error should display.");
    }

    @Test
    public void TC_CUST_004_createCustomerWithBlankMandatoryFields() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.submit();

        Assert.assertTrue(customerPage.hasValidationError(), "Validation errors should display.");
    }

    @Test
    public void TC_CUST_005_enterNumericValuesInCustomerName() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.createCustomer(validCustomerData().withName("Aarav123"));

        Assert.assertTrue(customerPage.getNameError().contains("Numbers"), "Validation should reject numeric name input.");
    }

    @Test
    public void TC_CUST_006_enterSpecialCharactersInCustomerName() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.createCustomer(validCustomerData().withName("Aarav@Sharma"));

        Assert.assertTrue(customerPage.getNameError().contains("Special"), "Validation should reject special characters.");
    }

    @Test
    public void TC_CUST_007_verifyFutureDobIsNotAccepted() {
        HomePage homePage = loginAsManager();
        NewCustomerPage customerPage = homePage.openNewCustomerPage();

        customerPage.createCustomer(validCustomerData().withDateOfBirth(LocalDate.now().plusDays(1).toString()));

        Assert.assertTrue(customerPage.getDobError().contains("future"), "Future DOB should not be accepted.");
    }

    @Test
    public void TC_CUST_008_editExistingCustomerAddress() {
        HomePage homePage = loginAsManager();
        String customerId = createCustomer(homePage);
        EditCustomerPage editCustomerPage = homePage.openEditCustomerPage();

        editCustomerPage.loadCustomer(customerId);
        editCustomerPage.updateAddress("221 Brigade Road");

        Assert.assertTrue(editCustomerPage.isSuccessMessageDisplayed(), "Updated address should save.");
        Assert.assertEquals(editCustomerPage.getAddressValue(), "221 Brigade Road");
    }

    @Test
    public void TC_CUST_009_editCustomerCityAndState() {
        HomePage homePage = loginAsManager();
        String customerId = createCustomer(homePage);
        EditCustomerPage editCustomerPage = homePage.openEditCustomerPage();

        editCustomerPage.loadCustomer(customerId);
        editCustomerPage.updateCityAndState("Mysuru", "Karnataka");

        Assert.assertTrue(editCustomerPage.isSuccessMessageDisplayed(), "Updated city and state should persist.");
        Assert.assertEquals(editCustomerPage.getCityValue(), "Mysuru");
        Assert.assertEquals(editCustomerPage.getStateValue(), "Karnataka");
    }

    @Test
    public void TC_CUST_010_deleteExistingCustomer() {
        HomePage homePage = loginAsManager();
        String customerId = createCustomer(homePage);
        DeleteCustomerPage deleteCustomerPage = homePage.openDeleteCustomerPage();

        deleteCustomerPage.deleteCustomer(customerId);

        Assert.assertTrue(deleteCustomerPage.isSuccessMessageDisplayed(), "Customer record should remove successfully.");
        Assert.assertTrue(deleteCustomerPage.isCustomerAbsent(customerId), "Deleted customer should not remain in list.");
    }
}
