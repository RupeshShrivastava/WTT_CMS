package com.halodoc.cmstests;

import static com.halodoc.cmstests.Constants.*;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Created by nageshkumar
 * since  30/05/17.
 */
@Slf4j
public class BaseCMSTest {
    public static final String file_location = "fixtures";

    @BeforeSuite (alwaysRun = true)
    public void beforeSuite() throws IOException, InterruptedException {
        log.info("Setting up cms-test api suite..");

        Awaitility.setDefaultPollInterval(1, TimeUnit.SECONDS);
        Awaitility.setDefaultPollDelay(1, TimeUnit.SECONDS);
        Awaitility.setDefaultTimeout(10, TimeUnit.SECONDS);
    }

    public String getRequestFixture(String folderName,String fileName) {
        return fixture(file_location + "/"+folderName+"/"+fileName);
    }

    public HashMap<String, String> getAppTokenHeaders(String appToken) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("x-app-token", appToken);
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public boolean validateStatusCode(Response response, int statusCode) {
        boolean result = response.statusCode() == statusCode;

        return result;
    }

    public boolean validateArraySize(Response response, String pathToValidate, int expectedValue) {
        int size = response.jsonPath().getList(pathToValidate).size();
        boolean result = size == expectedValue;

        return result;
    }

    public boolean validateResponseValue(Response response, String pathToValidate, Object expectedValue) {
        Object actualValue = response.path(pathToValidate);
        boolean result = actualValue.toString().equals(expectedValue.toString());

        return result;
    }

    public boolean validateResponseContains(Response response, String pathToValidate, Object expectedValue) {
        Object actualValue = response.path(pathToValidate);
        boolean result = actualValue.toString().contains(expectedValue.toString());

        return result;
    }


    public boolean validateResponseValueArray(Response response, String pathArray, String pathToValidate, Object expectedValue, String mode) {
        int size = response.jsonPath().getList(pathArray).size();
        boolean result = false;

        for(int i = 0; i < size; i++) {
            Object actualValue = response.path(pathArray + "[" + i +"]." + pathToValidate);

            if(mode.equals(MODE_EQUALS)) {
                result = actualValue.toString().equals(expectedValue.toString());
            } else if(mode.equals(MODE_CONTAINS)) {
                result = actualValue.toString().replace(" ", "").toLowerCase()
                                    .contains(expectedValue.toString().replace(" ", "").toLowerCase());
            } else if(mode.equals(MODE_NOT_EQUALS)) {
                result = !actualValue.toString().equals(expectedValue.toString());
            }

            if(!result)
                break;
        }

        return result;
    }

    public long getEpochTime() {
        Instant instant =Instant.now();
        long result = instant.toEpochMilli();

        return result;
    }

    @AfterSuite (alwaysRun = true)
    public void afterSuite() {
        log.info("Teardown after cms-test api suite.");
    }
}