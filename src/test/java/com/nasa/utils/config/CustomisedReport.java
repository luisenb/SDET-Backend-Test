package com.nasa.utils.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class CustomisedReport implements IReporter {

    private static final Logger logger = LogManager.getLogger(CustomisedReport.class.getName());

    private static final String ROW_TEMPLATE =
            "<tr class=\"%s\" data-toggle=\"collapse\" id=\"%s\" data-target=\".%s\">"
                    + "<td>%s</td>" // Test
                    + "<td>%s</td>" // Method
                    + "<td>%s</td>" // Status
                    + "<td>"
                            + "<button class=\"btn btn-default btn-sm\"> View More </button>"
                    + "</td>"
                    + "</tr>"
                    + "<tr class=\"collapse %s\">"
                    + "<td> Request </td>"
                    + "<td> Response </td>"
                    + "</tr>"
                    + "<tr class=\"collapse %s\">"
                    + "<td>%s</td>" //Request
                    + "<td>%s</td>" //Response
                    + "</tr>";

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory){
        String reportTemplate = initReportTemplate();

        final String body = suites
                .stream()
                .flatMap(suiteToResults())
                .collect(Collectors.joining());

        saveReportTemplate(outputDirectory, reportTemplate.replaceFirst("</tbody>", String.format("%s</tbody>", body)));
    }

    private Function<ISuite, Stream<? extends String>> suiteToResults() {
        return suite -> suite.getResults().entrySet()
                .stream()
                .flatMap(resultsToRows(suite));
    }

    private Function<Map.Entry<String, ISuiteResult>, Stream<? extends String>> resultsToRows(ISuite suite) {
        return e -> {
            ITestContext testContext = e.getValue().getTestContext();

            Set<ITestResult> failedTests = testContext
                    .getFailedTests()
                    .getAllResults();
            Set<ITestResult> passedTests = testContext
                    .getPassedTests()
                    .getAllResults();
            Set<ITestResult> skippedTests = testContext
                    .getSkippedTests()
                    .getAllResults();

            return Stream
                    .of(failedTests, passedTests, skippedTests)
                    .flatMap(results -> generateReportRows(e.getKey(), results).stream());
        };
    }

    private List<String> generateReportRows( String suiteName, Set<ITestResult> allTestResults) {
        return allTestResults.stream()
                .map(testResultToResultRow(suiteName))
                .collect(toList());
    }

    private Function<ITestResult, String> testResultToResultRow( String suiteName) {

        return testResult -> {

            Object requestObject = testResult.getAttribute("Request");
            Object responseObject = testResult.getAttribute("Response");
            String request =
                    requestObject != null?
                            requestObject.toString()
                            : "Request has not been set.";
            String response =
                    responseObject != null?
                            responseObject.toString()
                            : "Response has not been set.";
            String testName = testResult
                    .getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(Test.class).
                    testName();
            String description = testResult
                    .getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(Test.class).
                    description();
            String testNameTrim = testName.replace(" ", "");


            switch (testResult.getStatus()) {
                case ITestResult.FAILURE:
                    return String.format(ROW_TEMPLATE, "danger", testNameTrim, testNameTrim, testName, description,
                            "FAILED", testNameTrim, testNameTrim, "NA", "NA");

                case ITestResult.SUCCESS:
                    return String.format(ROW_TEMPLATE, "success", testNameTrim, testNameTrim, testName, description,
                            "PASSED", testNameTrim, testNameTrim, request, response);

                case ITestResult.SKIP:
                    return String.format(ROW_TEMPLATE, "warning", testNameTrim, testNameTrim, testName, description,
                            "SKIPPED", testNameTrim, testNameTrim, "NA", "NA");

                default:
                    return "";
            }
        };
    }

    private String initReportTemplate() {
        String template = null;
        byte[] reportTemplate;
        try {
            reportTemplate = Files.readAllBytes(Paths.get("src/test/resources/reportTemplate.html"));
            template = new String(reportTemplate, "UTF-8");
        } catch (IOException e) {
            logger.error("Problem initializing template", e);
        }
        return template;
    }

    private void saveReportTemplate(String outputDirectory, String reportTemplate) {
        new File(outputDirectory).mkdirs();
        try {
            PrintWriter reportWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDirectory, "my-report.html"))));
            reportWriter.println(reportTemplate);
            reportWriter.flush();
            reportWriter.close();
        } catch (IOException e) {
            logger.error("Problem saving template", e);
        }
    }


}
