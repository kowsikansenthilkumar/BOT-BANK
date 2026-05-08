package com.bankbot.tests;

import com.bankbot.pages.EditAccountPage;
import com.bankbot.pages.HomePage;
import com.bankbot.pages.NewAccountPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountTest extends BankBotTestBase {

    @Test
    public void TC_ACC_001_createNewAccountWithValidCustomerId() {
        HomePage homePage = loginAsManager();
        String customerId = createCustomer(homePage);
        NewAccountPage accountPage = homePage.openNewAccountPage();

        accountPage.createAccount(customerId, "Savings", "2500");

        Assert.assertFalse(accountPage.getGeneratedAccountNumber().isBlank(), "Account number should generate.");
    }

    @Test
    public void TC_ACC_002_verifyAccountAppearsInAccountList() {
        HomePage homePage = loginAsManager();
        String customerId = createCustomer(homePage);
        NewAccountPage accountPage = homePage.openNewAccountPage();

        accountPage.createAccount(customerId, "Savings", "2500");
        String accountNumber = accountPage.getGeneratedAccountNumber();

        Assert.assertTrue(accountPage.isAccountListed(accountNumber), "Account should display in account list.");
    }

    @Test
    public void TC_ACC_003_createAccountWithInvalidCustomerId() {
        HomePage homePage = loginAsManager();
        NewAccountPage accountPage = homePage.openNewAccountPage();

        accountPage.createAccount("999999", "Savings", "2500");

        Assert.assertTrue(accountPage.isErrorDisplayed(), "Error message should display for invalid customer ID.");
    }

    @Test
    public void TC_ACC_004_createAccountWithoutInitialDeposit() {
        HomePage homePage = loginAsManager();
        String customerId = createCustomer(homePage);
        NewAccountPage accountPage = homePage.openNewAccountPage();

        accountPage.createAccount(customerId, "Savings", "");

        Assert.assertTrue(accountPage.getInitialDepositError().contains("blank"), "Validation should trigger.");
    }

    @Test
    public void TC_ACC_005_editAccountType() {
        HomePage homePage = loginAsManager();
        String customerId = createCustomer(homePage);
        String accountNumber = createAccount(homePage, customerId, "Savings", "2500");
        EditAccountPage editAccountPage = homePage.openEditAccountPage();

        editAccountPage.loadAccount(accountNumber);
        editAccountPage.updateAccountType("Current");

        Assert.assertTrue(editAccountPage.isSuccessMessageDisplayed(), "Account type should update.");
        Assert.assertEquals(editAccountPage.getAccountTypeValue(), "Current");
    }

    @Test
    public void TC_ACC_006_verifyAccountDetailsAfterUpdate() {
        HomePage homePage = loginAsManager();
        String customerId = createCustomer(homePage);
        String accountNumber = createAccount(homePage, customerId, "Savings", "2500");
        EditAccountPage editAccountPage = homePage.openEditAccountPage();

        editAccountPage.loadAccount(accountNumber);
        editAccountPage.updateAccountType("Current");
        NewAccountPage accountPage = homePage.openNewAccountPage();

        Assert.assertEquals(accountPage.getListedAccountType(accountNumber), "Current", "Updated values should display.");
    }

    @Test
    public void TC_ACC_007_createMultipleAccountsForSameCustomer() {
        HomePage homePage = loginAsManager();
        String customerId = createCustomer(homePage);
        NewAccountPage accountPage = homePage.openNewAccountPage();

        accountPage.createAccount(customerId, "Savings", "2500");
        String firstAccount = accountPage.getGeneratedAccountNumber();
        accountPage.createAccount(customerId, "Current", "3500");
        String secondAccount = accountPage.getGeneratedAccountNumber();

        Assert.assertTrue(accountPage.isAccountListed(firstAccount), "First account should display.");
        Assert.assertTrue(accountPage.isAccountListed(secondAccount), "Second account should display.");
    }

    @Test
    public void TC_ACC_008_verifyAccountNumberUniqueness() {
        HomePage homePage = loginAsManager();
        String customerId = createCustomer(homePage);
        NewAccountPage accountPage = homePage.openNewAccountPage();

        accountPage.createAccount(customerId, "Savings", "2500");
        String firstAccount = accountPage.getGeneratedAccountNumber();
        accountPage.createAccount(customerId, "Current", "3500");
        String secondAccount = accountPage.getGeneratedAccountNumber();

        Assert.assertNotEquals(firstAccount, secondAccount, "Duplicate account numbers should not occur.");
    }
}
