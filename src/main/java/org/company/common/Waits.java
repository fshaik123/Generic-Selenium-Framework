package org.company.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Waits extends CaptureScreenshots {
    WebDriver driver;

    public Waits(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void waitForPageToBeReady(int timeOutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds)).until(ExpectedConditions
                .jsReturnsValue("return document.readyState == 'complete';"));
    }

    public void waitUntilLoadingSpinnerDisappears(By findBy) {
        try {
            List<WebElement> loads = driver.findElements(findBy);
            if (!loads.isEmpty()) {
                new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.invisibilityOfAllElements(loads));
            } else {
                System.out.println("Loading or Spinner not present");
            }
        } catch (Exception e) {
            System.out.println("Loading or Spinner not present");
        }
    }

    public void waitForElementVisibility(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForElementInvisibility(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
    }
}
