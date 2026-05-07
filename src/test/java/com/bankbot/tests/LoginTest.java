package com.bankbot.tests;

import com.bankbot.base.BaseTest;
import com.bankbot.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData")
    public void verifyLogin(String username, String password, boolean validCredentials) {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.login(username, password);

        if (validCredentials) {
            Assert.assertTrue(loginPage.isLoginSuccessful(), "Expected login success message.");
            Assert.assertEquals(loginPage.getSuccessMessage(), "Login successful");
        } else {
            Assert.assertTrue(loginPage.isLoginErrorDisplayed(), "Expected login error message.");
            Assert.assertEquals(loginPage.getErrorMessage(), "Invalid username or password");
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"standard_user", "secret_sauce", true},
                {"standard_user", "secret_sauce", true},
                {"standard_user", "secret_sauce", true},
                {"standard_user", "secret_sauce", true},
                {"standard_user", "secret_sauce", true},
                {"standard_user", "secret_sauce", true},
                {"locked_user", "wrong_password", false},
                {"problem_user", "bad_password", false},
                {"", "secret_sauce", false},
                {"standard_user", "", false},
                {"", "", false},
                {"admin", "admin123", false}
        };
    }
}
