package com.bankbot.tests;

import com.bankbot.base.BaseTest;
import com.bankbot.pages.HomePage;
import com.bankbot.pages.LoginPage;
import com.bankbot.pages.NewAccountPage;
import com.bankbot.pages.NewCustomerPage;
import com.bankbot.testdata.CustomerData;
import com.bankbot.utils.ConfigReader;
import org.testng.Assert;

import java.util.concurrent.atomic.AtomicLong;

public abstract class BankBotTestBase extends BaseTest {
    private static final AtomicLong UNIQUE = new AtomicLong(System.currentTimeMillis());

    protected HomePage loginAsManager() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(
                ConfigReader.get("manager.username", "mngr123"),
                ConfigReader.get("manager.password", "demo123")
        );
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Manager login should succeed before workflow setup.");
        return new HomePage(getDriver());
    }

    protected CustomerData validCustomerData() {
        long suffix = UNIQUE.incrementAndGet();
        return new CustomerData(
                "Aarav Sharma",
                "male",
                "1990-01-15",
                "15 MG Road",
                "Bengaluru",
                "Karnataka",
                "560001",
                "98765" + String.valueOf(suffix).substring(String.valueOf(suffix).length() - 5),
                "customer" + suffix + "@bankbot.test",
                "Pass1234"
        );
    }

    protected String createCustomer(HomePage homePage) {
        return createCustomer(homePage, validCustomerData());
    }

    protected String createCustomer(HomePage homePage, CustomerData customerData) {
        NewCustomerPage customerPage = homePage.openNewCustomerPage();
        customerPage.createCustomer(customerData);
        Assert.assertTrue(customerPage.isSuccessMessageDisplayed(), "Customer creation should show success.");
        return customerPage.getGeneratedCustomerId();
    }

    protected String createAccount(HomePage homePage, String customerId, String accountType, String initialDeposit) {
        NewAccountPage accountPage = homePage.openNewAccountPage();
        accountPage.createAccount(customerId, accountType, initialDeposit);
        Assert.assertTrue(accountPage.isSuccessMessageDisplayed(), "Account creation should show success.");
        return accountPage.getGeneratedAccountNumber();
    }

    protected TransferAccounts createTransferAccounts(HomePage homePage) {
        String customerId = createCustomer(homePage);
        String senderAccount = createAccount(homePage, customerId, "Savings", "5000");
        String receiverAccount = createAccount(homePage, customerId, "Current", "1000");
        return new TransferAccounts(senderAccount, receiverAccount, 5000, 1000);
    }

    protected record TransferAccounts(String senderAccount, String receiverAccount, double senderOpeningBalance,
                                      double receiverOpeningBalance) {
    }
}
