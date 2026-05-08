package com.bankbot.tests;

import com.bankbot.pages.HomePage;
import com.bankbot.pages.LoginPage;
import com.bankbot.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BankBotTestBase {

    @Test
    public void TC_LOGIN_001_loginWithValidManagerCredentials() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.login(ConfigReader.get("manager.username"), ConfigReader.get("manager.password"));

        Assert.assertTrue(loginPage.isLoginSuccessful(), "User should login successfully.");
        Assert.assertEquals(loginPage.getSuccessMessage(), "Login successful");
    }

    @Test
    public void TC_LOGIN_002_loginWithInvalidUsername() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.login("invalid_manager", ConfigReader.get("manager.password"));

        Assert.assertTrue(loginPage.isLoginErrorDisplayed(), "Error message should display for invalid username.");
    }

    @Test
    public void TC_LOGIN_003_loginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.login(ConfigReader.get("manager.username"), "invalid_password");

        Assert.assertTrue(loginPage.isLoginErrorDisplayed(), "Error message should display for invalid password.");
    }

    @Test
    public void TC_LOGIN_004_loginWithBlankUsername() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.login("", ConfigReader.get("manager.password"));

        Assert.assertTrue(loginPage.isUsernameValidationDisplayed(), "Username validation message should appear.");
    }

    @Test
    public void TC_LOGIN_005_loginWithBlankPassword() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.login(ConfigReader.get("manager.username"), "");

        Assert.assertTrue(loginPage.isPasswordValidationDisplayed(), "Password validation message should appear.");
    }

    @Test
    public void TC_LOGIN_006_loginWithBlankUsernameAndPassword() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.login("", "");

        Assert.assertTrue(loginPage.isUsernameValidationDisplayed(), "Username required validation should appear.");
        Assert.assertTrue(loginPage.isPasswordValidationDisplayed(), "Password required validation should appear.");
    }

    @Test
    public void TC_LOGIN_007_verifyLogoutFunctionality() {
        HomePage homePage = loginAsManager();

        LoginPage loginPage = homePage.logout();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "User should redirect to login page after logout.");
    }

    @Test
    public void TC_LOGIN_008_verifySessionExpiresAfterLogout() {
        HomePage homePage = loginAsManager();

        homePage.logout();
        getDriver().navigate().back();

        Assert.assertFalse(homePage.isDashboardDisplayedNow(), "Dashboard should remain inaccessible after logout.");
    }
}
