package org.company.tests;

import org.company.pages.*;
import org.company.testComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class StandaloneTest extends BaseTest {

    @Test(dataProvider = "getData")
    public void submitOrder(HashMap<String, String> input) {

        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.loginApplication(input.get("email"), input.get("password"));

        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
        productCatalogue.addProductToCart(input.get("productName"));
        productCatalogue.goToCartPage();

        CartPage cartPage = new CartPage(driver);
        Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
        Assert.assertTrue(match);
        cartPage.goToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.selectCountry("india");
        checkoutPage.submitOrder();

        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods = "submitOrder", dataProvider = "getData")
    public void OrderHistoryTest(HashMap<String, String> input) {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.loginApplication(input.get("email"), input.get("password"));

        landingPage.goToOrderPage();

        OrderPage orderPage = new OrderPage(driver);
        Assert.assertTrue(orderPage.verifyOrderDisplay(input.get("productName")));
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> list = getJsonDataToMap("src/test/resources/files/StandaloneTest.json");
        return new Object[][]{{list.get(0)}, {list.get(1)}};
    }
}
