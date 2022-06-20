package com.halodoc.cmstests.medisend;

import static com.halodoc.cmstests.Constants.*;
import java.util.HashMap;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.apiDefinitions.MedisendAPI;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvoiceTests extends BaseCMSTest {
    private MedisendAPI medisendAPI = new MedisendAPI();
    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE DISTRIBUTOR WITH PAYMENT INSTRUCTIONS")
    @Test (groups = { "regression" }, priority = 1)
    public void verifyCreateDistributorWithPaymentInstruction() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String distributorName = CREATE_DISTRIBUTOR_NAME + getEpochTime();
        Response response = medisendAPI.createDistributorsWithPaymentInstruction(headers, distributorName, STATUS_ACTIVE, CREATE_DISTRIBUTOR_NAME,
                DISTRIBUTOR_PHONE_NUMBER, PAYMENT_INSTRUCTIONS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, distributorName));
        Assert.assertTrue(validateResponseValue(response, DESCRIPTION_PATH, CREATE_DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, DISTRIBUTOR_PHONE_NUMBER_PATH, DISTRIBUTOR_PHONE_NUMBER));
        Assert.assertTrue(validateResponseValue(response, PAYMENT_INSTRUCTIONS_PATH, PAYMENT_INSTRUCTIONS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE DISTRIBUTOR WITH PAYMENT INSTRUCTION MORE THAN 5000 WORDS")
    @Test (groups = { "regression" }, priority = 2)
    public void verifyCreateDistributorWithPaymentInstructionMoreThan5000Words() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String paymentInstructions = PAYMENT_INSTRUCTIONS + getEpochTime();
        Response response = medisendAPI.updateDistributorsWithPaymentInstruction(headers, DISTRIBUTOR_INTERNAL_ID, DISTRIBUTOR_NAME, STATUS_ACTIVE,
                CREATE_DISTRIBUTOR_NAME, DISTRIBUTOR_PHONE_NUMBER, paymentInstructions);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.getDistributors(headers, DISTRIBUTOR_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, DESCRIPTION_PATH, CREATE_DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, DISTRIBUTOR_PHONE_NUMBER_PATH, DISTRIBUTOR_PHONE_NUMBER));
        Assert.assertTrue(validateResponseValue(response, PAYMENT_INSTRUCTIONS_PATH, paymentInstructions));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}