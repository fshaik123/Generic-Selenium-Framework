package org.company.tests;

import org.company.pages.*;
import org.company.testComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidationsTest extends BaseTest {

    @Test
    public void loginErrorTest() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.loginApplication("anshika@gmail.com", "Iamking");
        String errorMessage = landingPage.getErrorMessage();
        System.out.println(errorMessage);
        Assert.assertEquals(errorMessage, "Incorrect email or password.", "Error Message not displayed");
    }

    @Test(retryAnalyzer = org.company.testComponents.RetryListeners.class)
    public void loginErrorTest_Fail() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.loginApplication("anshika@gmail.com", "Iamking");
        String errorMessage = landingPage.getErrorMessage();
        System.out.println(errorMessage);
        Assert.assertEquals(errorMessage, "Incorrect email", "Error Message not displayed");
    }
}
