package org.company.common;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;

public class CaptureScreenshots {

    WebDriver driver;

    public CaptureScreenshots(WebDriver driver) {
        this.driver = driver;
    }


}