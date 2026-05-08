package com.bankbot.tests;

import com.bankbot.pages.FundTransferPage;
import com.bankbot.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FundTransferTest extends BankBotTestBase {

    @Test
    public void TC_FUND_001_transferFundsBetweenValidAccounts() {
        HomePage homePage = loginAsManager();
        TransferAccounts accounts = createTransferAccounts(homePage);
        FundTransferPage transferPage = homePage.openFundTransferPage();

        transferPage.transfer(accounts.senderAccount(), accounts.receiverAccount(), "500", "Rent");

        Assert.assertTrue(transferPage.isSuccessMessageDisplayed(), "Transfer should complete successfully.");
    }

    @Test
    public void TC_FUND_002_verifySuccessMessageAfterTransfer() {
        HomePage homePage = loginAsManager();
        TransferAccounts accounts = createTransferAccounts(homePage);
        FundTransferPage transferPage = homePage.openFundTransferPage();

        transferPage.transfer(accounts.senderAccount(), accounts.receiverAccount(), "500", "Rent");

        Assert.assertTrue(transferPage.getSuccessMessage().contains("Fund Transfer Details"), "Confirmation message should display.");
    }

    @Test
    public void TC_FUND_003_verifySenderBalanceUpdates() {
        HomePage homePage = loginAsManager();
        TransferAccounts accounts = createTransferAccounts(homePage);
        FundTransferPage transferPage = homePage.openFundTransferPage();
        double openingBalance = transferPage.getAccountBalance(accounts.senderAccount());

        transferPage.transfer(accounts.senderAccount(), accounts.receiverAccount(), "700", "Loan payment");

        Assert.assertEquals(transferPage.getAccountBalance(accounts.senderAccount()), openingBalance - 700, 0.01,
                "Deducted balance should reflect.");
    }

    @Test
    public void TC_FUND_004_verifyReceiverBalanceUpdates() {
        HomePage homePage = loginAsManager();
        TransferAccounts accounts = createTransferAccounts(homePage);
        FundTransferPage transferPage = homePage.openFundTransferPage();
        double openingBalance = transferPage.getAccountBalance(accounts.receiverAccount());

        transferPage.transfer(accounts.senderAccount(), accounts.receiverAccount(), "700", "Loan payment");

        Assert.assertEquals(transferPage.getAccountBalance(accounts.receiverAccount()), openingBalance + 700, 0.01,
                "Added balance should reflect.");
    }

    @Test
    public void TC_FUND_005_transferAmountGreaterThanBalance() {
        HomePage homePage = loginAsManager();
        TransferAccounts accounts = createTransferAccounts(homePage);
        FundTransferPage transferPage = homePage.openFundTransferPage();

        transferPage.transfer(accounts.senderAccount(), accounts.receiverAccount(), "999999", "Large transfer");

        Assert.assertTrue(transferPage.isErrorDisplayed(), "Insufficient balance error should display.");
        Assert.assertTrue(transferPage.getAmountError().contains("Insufficient"));
    }

    @Test
    public void TC_FUND_006_transferUsingInvalidPayeeAccount() {
        HomePage homePage = loginAsManager();
        TransferAccounts accounts = createTransferAccounts(homePage);
        FundTransferPage transferPage = homePage.openFundTransferPage();

        transferPage.transfer(accounts.senderAccount(), "999999", "250", "Invalid payee");

        Assert.assertTrue(transferPage.isErrorDisplayed(), "Error message should display for invalid payee account.");
        Assert.assertTrue(transferPage.getPayeeError().contains("does not exist"));
    }

    @Test
    public void TC_FUND_007_transferNegativeAmount() {
        HomePage homePage = loginAsManager();
        TransferAccounts accounts = createTransferAccounts(homePage);
        FundTransferPage transferPage = homePage.openFundTransferPage();

        transferPage.transfer(accounts.senderAccount(), accounts.receiverAccount(), "-50", "Negative transfer");

        Assert.assertTrue(transferPage.getAmountError().contains("greater than zero"), "Validation should reject negative amount.");
    }

    @Test
    public void TC_FUND_008_transferWithBlankFields() {
        HomePage homePage = loginAsManager();
        FundTransferPage transferPage = homePage.openFundTransferPage();

        transferPage.submitBlank();

        Assert.assertTrue(transferPage.hasRequiredValidation(), "Required validations should appear.");
    }
}
