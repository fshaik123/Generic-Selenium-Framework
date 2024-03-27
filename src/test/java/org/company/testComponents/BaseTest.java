package org.company.testComponents;

import org.apache.commons.io.FileUtils;
import org.company.resources.PropertiesUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest extends PropertiesUtils {
    public WebDriver driver;

    public WebDriver initializeDriver() throws IOException {
        Properties prop = readPropertiesFile("config.properties");

        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");

        switch (browserName) {
            case "Chrome":
                driver = new ChromeDriver();
                break;
            case "ChromeHeadless":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
                driver.manage().window().setSize(new Dimension(1440, 900));
                break;
            case "Firefox":
                driver = new FirefoxDriver();
                break;
            case "Edge":
                driver = new EdgeDriver();
                break;
            case "IE":
                driver = new InternetExplorerDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    @BeforeMethod
    public void launchApplication() throws IOException {
        initializeDriver();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public String takeScreenshot(String screenshotName, WebDriver driver) throws IOException {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")
                + "\\reports\\screenshots\\" + screenshotName + System.currentTimeMillis() + ".png";
        FileUtils.copyFile(file, new File(path));
        return path;
    }
}
