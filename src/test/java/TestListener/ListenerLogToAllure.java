package TestListener;

import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ListenerLogToAllure implements ITestListener {

    static final   ByteArrayOutputStream request = new ByteArrayOutputStream();
    static final  ByteArrayOutputStream response = new ByteArrayOutputStream();
    static final PrintStream requestVar = new PrintStream(request, true);
    static final PrintStream responseVar = new PrintStream(response, true);

    public void onStart(final ITestContext context) {
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL, responseVar),
                new RequestLoggingFilter(LogDetail.ALL, requestVar));
    }

    public void onTestSuccess(final ITestResult result) {
        logRequest(request);
        logResponse(response);
    }

    public void onTestFailure(ITestResult result) {
        logRequest(request);
        logResponse(response);
    }

    @Attachment(value = "request")
    public byte[] logRequest(final ByteArrayOutputStream stream) {
        return attach(stream);
    }

    @Attachment(value = "response")
    public byte[] logResponse(final ByteArrayOutputStream stream) {
        return attach(stream);

    }

    public byte[] attach(final ByteArrayOutputStream log) {
        byte[] array = log.toByteArray();
        log.reset();
        return array;
    }

}
