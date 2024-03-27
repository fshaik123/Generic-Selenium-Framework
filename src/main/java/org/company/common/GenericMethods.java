package org.company.common;

import org.apache.commons.io.FileUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GenericMethods extends Waits {

    WebDriver driver;

    public GenericMethods(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public String takeElementScreenshot(WebElement element, String screenshotName) throws IOException {
        File file = element.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")
                + "\\reports\\screenshots\\" + screenshotName + System.currentTimeMillis() + ".png";
        FileUtils.copyFile(file, new File(path));
        return path;
    }

    public void selectValueFromDropdownByVisibleText(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    public void selectValueFromDropdownByValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void selectValueFromDropdownByIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    public String getDropdownValueByIndex(WebElement element, int index) {
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
        return options.get(index).getText();
    }

    public String getDefaultDropdownValue(WebElement element) {
        try {
            Select select = new Select(element);
            String txt = select.getFirstSelectedOption().getText();
            System.out.println("Text is: " + txt);
            return txt;
        } catch (Exception e) {
            return "Select";
        }
    }

    public String getFutureDateBasedOnCurrentDate(String dateFormat, int NoOfDaysToAddToCurrentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        //Getting current date
        Calendar cal = Calendar.getInstance();
        //Number of Days to add
        cal.add(Calendar.DAY_OF_MONTH, NoOfDaysToAddToCurrentDate);
        //Date after adding the days to the current date
        return sdf.format(cal.getTime());
    }

    public LocalDate parseDate(String DateFormat, String date) {
        DateTimeFormatter objSDF = DateTimeFormatter.ofPattern(DateFormat);
        //convert String to LocalDate
        return LocalDate.parse(date, objSDF);
    }

    public String convertStringDateFromOneFormatToAnother(String InitialFormat, String RequiredFormat, String date) throws ParseException {
        SimpleDateFormat sdfmt1 = new SimpleDateFormat(InitialFormat);
        SimpleDateFormat sdfmt2 = new SimpleDateFormat(RequiredFormat);
        Date dDate = sdfmt1.parse(date);
        return sdfmt2.format(dDate);
    }

    public String getCurrentDateInTheGivenFormat(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        //Getting current date
        Calendar cal = Calendar.getInstance();
        //Date after adding the days to the current date
        return sdf.format(cal.getTime());
    }

    public String getCurrentDateToUseAsVariable() {
        String value = getCurrentDateInTheGivenFormat("MMddyyyyHHmmss");
        System.out.println(value);
        return value;
    }

    public void enterText_CharByChar(WebElement WebElement, String text) {
        // Send keys is not able to enter all values if angular version is older
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String s = String.valueOf(c);
            WebElement.sendKeys(s);
        }
    }

    public void clickUsingJavaScriptExecutor(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0]. click();", element);
    }

    public void enterTextUsingJavaScriptExecutor(WebElement element, String text) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1]", element, text);
    }

    public void click_pseudoWebElement_using_Javascript(String pseudoValue, String element) {
        ((JavascriptExecutor) driver).executeScript("document.querySelector(arguments[0],':" + pseudoValue + "').click();", element);
    }

    public String generateRandomAlphanumeric(int noOfCharacters) {
        RandomStringGenerator randomStringGenerator =
                new RandomStringGenerator.Builder()
                        .withinRange('0', 'z')
                        .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                        .build();
        return randomStringGenerator.generate(noOfCharacters);
    }

    public void appendToPath(String dir) {
        try {
            final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        String path = System.getProperty("java.library.path");
        path = dir + ";" + path;
        System.setProperty("java.library.path", path);
    }

    public void deleteBrowserCookies(WebDriver driver) throws InterruptedException {
        driver.navigate().to("chrome://settings/clearBrowserData");
        Thread.sleep(3000);
        String clearBtnCssScript = "return document.querySelector('settings-ui').shadowRoot.querySelector('settings-main').shadowRoot.querySelector('settings-basic-page').shadowRoot.querySelector('settings-section > settings-privacy-page').shadowRoot.querySelector('settings-clear-browsing-data-dialog').shadowRoot.querySelector('#clearBrowsingDataDialog').querySelector('#clearBrowsingDataConfirm')";
        WebElement clearButton = (WebElement) ((JavascriptExecutor) driver).executeScript(clearBtnCssScript);
        clearButton.click();
        Thread.sleep(3000);
    }

    public void verify_SortingOrder_Dates_List(List<WebElement> elementsList, String ascendingOrDescending, String DateFormat) {
        List<Date> listDates = new ArrayList<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DateFormat);

        // Storing options in list
        for (WebElement ele : elementsList) {
            try {
                listDates.add(dateFormatter.parse(ele.getText().trim()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // Creating a temp list to sort
        List<Date> tempList = new ArrayList<>(listDates);
        if (ascendingOrDescending.equalsIgnoreCase("ascending")) {
            // Sort list ascending
            Collections.sort(tempList);
            // Default order of option in drop down
            System.out.println("Default List : " + listDates);
            // tempList after sorting
            System.out.println("Sorted List : " + tempList);
            Assert.assertEquals(tempList, listDates, "Values are not sorted in Ascending Order");
        } else if (ascendingOrDescending.equalsIgnoreCase("descending")) {
            // Sort list descending
            tempList.sort(Collections.reverseOrder());
            // Default order of option in drop down
            System.out.println("Default List : " + listDates);
            // tempList after sorting
            System.out.println("Sorted List : " + tempList);
            Assert.assertEquals(tempList, listDates, "Values are not sorted in Descending Order");
        }
    }

    public void verify_SortingOrder_ListString(List<String> aList, String ascendingOrDescending) {
        // Creating a temp list to sort
        List<String> tempList = new ArrayList<>(aList);
        if (ascendingOrDescending.equalsIgnoreCase("ascending")) {
            // Default order of option in drop down
            System.out.println("Default List : " + aList);
            // Sort list ascending
            Collections.sort(tempList);
            // tempList after sorting
            System.out.println("Sorted List : " + tempList);
            Assert.assertEquals(tempList, aList, "Values are not sorted in Ascending Order");
        } else if (ascendingOrDescending.equalsIgnoreCase("descending")) {
            // Default order of option in drop down
            System.out.println("Default List : " + aList);
            // Sort list descending
            tempList.sort(Collections.reverseOrder());
            // tempList after sorting
            System.out.println("Sorted List : " + tempList);
            Assert.assertEquals(tempList, aList, "Values are not sorted in Descending Order");
        }
    }

    public void verify_ColumnHeader_isDisplayed(List<WebElement> elementsList, String ColumnName) {
        for (WebElement e : elementsList) {
            if (e.getText().trim().contains(ColumnName)) {
                Assert.assertTrue(e.isDisplayed());
                break;
            }
        }
    }

    public void verify_GivenColumn_isBetween_FirstColumn_and_LastColumn(List<WebElement> elementsList, String givenColumn, String firstColumn, String lastColumn) {
        for (int i = 0; i < elementsList.size(); i++) {
            if (elementsList.get(i).getText().trim().contains(firstColumn)) {
                Assert.assertTrue(elementsList.get(i).getText().trim().contains(firstColumn));
                Assert.assertTrue(elementsList.get(i + 1).getText().trim().contains(givenColumn));
                Assert.assertTrue(elementsList.get(i + 2).getText().trim().contains(lastColumn));
                break;
            }
        }
    }

    public void click_ColumnHeader_inTable(List<WebElement> elementsList, String ColumnName) {
        for (WebElement e : elementsList) {
            if (e.getText().trim().equalsIgnoreCase(ColumnName)) {
                e.click();
                break;
            }
        }
    }
}
