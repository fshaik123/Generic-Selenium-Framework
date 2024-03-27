package org.company.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExtentReportsMerger {

    public static void main(String[] args) {
        try {
            ExtentReports merged = new ExtentReports();

            //Path directoryPath = Paths.get(System.getProperty("user.dir") + "\\reports\\html");
            File directoryPath = new File(System.getProperty("user.dir") + "\\reports\\html");
            // Specify the paths of the reports to be merged
            //List<String> pathReports = new ArrayList<>();
            //File file :Objects.requireNonNull(directoryPath.toFile().listFiles()
            //pathReports.add(file.getAbsolutePath());
            /*ExtentSparkReporter[] reporters = new ExtentSparkReporter[pathReports.size()];
            for (int i = 0; i < pathReports.size(); i++) {
                reporters[i] = new ExtentSparkReporter(pathReports.get(i));
            }*/
            String[] pathReports = directoryPath.list();
            ExtentSparkReporter r1 = new ExtentSparkReporter(pathReports[0]);
            ExtentSparkReporter r2 = new ExtentSparkReporter(pathReports[1]);
            merged.attachReporter(r1, r2);
            ExtentTest mergedTest = merged.createTest("Merged Test");
            mergedTest.log(Status.INFO, "This report is a combination of all reports.");

            merged.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
