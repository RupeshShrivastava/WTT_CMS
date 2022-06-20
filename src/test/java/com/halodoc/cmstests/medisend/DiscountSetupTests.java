package com.halodoc.cmstests.medisend;

import static com.halodoc.cmstests.Constants.AVAILABLE_QUANTITY_DISCOUNT_SETUP;
import static com.halodoc.cmstests.Constants.COST_PRICE_FOR_DISCOUNT_ENABLED_PRODUCT;
import static com.halodoc.cmstests.Constants.DISCOUNT_MAX_QUANTITY;
import static com.halodoc.cmstests.Constants.DISCOUNT_MIN_QUANTITY;
import static com.halodoc.cmstests.Constants.DISCOUNT_MIN_QUANTITY_ZERO;
import static com.halodoc.cmstests.Constants.DISCOUNT_VALUE;
import static com.halodoc.cmstests.Constants.DISTRIBUTOR_BRANCH_FOR_DISCOUNT_ENABLED;
import static com.halodoc.cmstests.Constants.DISTRIBUTOR_ID_FOR_DISCOUNT_ENABLED;
import static com.halodoc.cmstests.Constants.EFFECTIVE_PRICE;
import static com.halodoc.cmstests.Constants.EFFECTIVE_PRICE_FOR_QUANTITY;
import static com.halodoc.cmstests.Constants.EFFECTIVE_PRICE_FOR_UPDATE;
import static com.halodoc.cmstests.Constants.ERROR_MESSAGE_FOR_INVALID_VALUE;
import static com.halodoc.cmstests.Constants.EXPECTED_DISCOUNT_VALUE;
import static com.halodoc.cmstests.Constants.EXPECTED_SELLING_PRICE;
import static com.halodoc.cmstests.Constants.EXTERNAL_ID_PATH;
import static com.halodoc.cmstests.Constants.FALSE;
import static com.halodoc.cmstests.Constants.GET_RATE_CARD_ENTITY_PATH;
import static com.halodoc.cmstests.Constants.INVALID_PRODUCT_ENTITY_ID;
import static com.halodoc.cmstests.Constants.INVALID_VALUE_ERROR_MESSAGE_PATH;
import static com.halodoc.cmstests.Constants.NEGETIVE_DISCOUNT_VALUE;
import static com.halodoc.cmstests.Constants.PRODUCT_ENTITY_ID;
import static com.halodoc.cmstests.Constants.PRODUCT_ENTITY_ID_2;
import static com.halodoc.cmstests.Constants.PRODUCT_ENTITY_ID_PATH;
import static com.halodoc.cmstests.Constants.PRODUCT_ID_FOR_DISCOUNT_ENABLED;
import static com.halodoc.cmstests.Constants.QUANTITY_NO_RATE_CARD;
import static com.halodoc.cmstests.Constants.QUANTITY_WITH_RATE_CARD;
import static com.halodoc.cmstests.Constants.RATE_CARD_EFFECTIVE_PRICE_PATH;
import static com.halodoc.cmstests.Constants.RATE_CARD_ID_PATH;
import static com.halodoc.cmstests.Constants.RATE_CARD_RULES_EXTERNAL_ID_PATH;
import static com.halodoc.cmstests.Constants.RATE_CARD_RULES_ID_PATH;
import static com.halodoc.cmstests.Constants.RATE_CARD_RULES_MAX_QUANTITY_PATH;
import static com.halodoc.cmstests.Constants.RATE_CARD_RULES_MIN_QUANTITY_PATH;
import static com.halodoc.cmstests.Constants.RATE_CARD_RULES_STATUS_PATH;
import static com.halodoc.cmstests.Constants.RATE_CARD_STATUS_PATH;
import static com.halodoc.cmstests.Constants.RATE_CARD_VALUE_PATH;
import static com.halodoc.cmstests.Constants.SELLING_PRICE_PATH;
import static com.halodoc.cmstests.Constants.SKU_ID_FOR_DISCOUNT_ENABLED;
import static com.halodoc.cmstests.Constants.STATUS_ACTIVE;
import static com.halodoc.cmstests.Constants.STATUS_INACTIVE;
import static com.halodoc.cmstests.Constants.TRUE;
import static com.halodoc.cmstests.Constants.X_APP_TOKEN_CMS;
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
public class DiscountSetupTests extends BaseCMSTest {

    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);
    MedisendAPI medisendAPI = new MedisendAPI();
    String RATE_CARD_EXTERNAL_ID = null;
    String RATE_CARD_RULES_EXTERNAL_ID = null;
    String RATE_CARD_EXTERNAL_ID_2 = null;
    String RATE_CARD_RULES_EXTERNAL_ID_2 = null;

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE RATE CARD")
    @Test (groups = { "regression" }, priority = 1)
    public void verifyCreateRateCard() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = medisendAPI.createRateCard(headers, DISCOUNT_VALUE, DISCOUNT_MIN_QUANTITY, DISCOUNT_MAX_QUANTITY, PRODUCT_ENTITY_ID);
        RATE_CARD_EXTERNAL_ID = response.path(EXTERNAL_ID_PATH);
        RATE_CARD_RULES_EXTERNAL_ID = response.path(RATE_CARD_RULES_EXTERNAL_ID_PATH);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, RATE_CARD_VALUE_PATH, DISCOUNT_VALUE));
        Assert.assertTrue(validateResponseValue(response,PRODUCT_ENTITY_ID_PATH , PRODUCT_ENTITY_ID));
        Assert.assertTrue(validateResponseValue(response, RATE_CARD_STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, RATE_CARD_RULES_STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, RATE_CARD_RULES_MIN_QUANTITY_PATH, DISCOUNT_MIN_QUANTITY));
        Assert.assertTrue(validateResponseValue(response, RATE_CARD_RULES_MAX_QUANTITY_PATH, DISCOUNT_MAX_QUANTITY));
        Assert.assertTrue(validateResponseValue(response, RATE_CARD_EFFECTIVE_PRICE_PATH, EFFECTIVE_PRICE_FOR_UPDATE));
        medisendAPI.updateRateCard(headers, PRODUCT_ENTITY_ID, RATE_CARD_EXTERNAL_ID, DISCOUNT_VALUE, STATUS_INACTIVE);
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE RATE CARD WITH INVALID ENTITY ID")
    @Test (groups = { "sanity" , "regression" }, priority = 2)
    public void verifyCreateRateCardInvalidEntityId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = medisendAPI.createRateCard(headers, DISCOUNT_VALUE, DISCOUNT_MIN_QUANTITY, DISCOUNT_MAX_QUANTITY, INVALID_PRODUCT_ENTITY_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE RATE CARD WITH INVALID PERCENT VALUE")
    @Test (groups = { "regression" }, priority = 3)
    public void verifyCreateRateCardInvalidPercentValue() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = medisendAPI.createRateCard(headers, NEGETIVE_DISCOUNT_VALUE, DISCOUNT_MIN_QUANTITY, DISCOUNT_MAX_QUANTITY, PRODUCT_ENTITY_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_UNPROCESSABLE_ENTITY));
        Assert.assertTrue(validateResponseValue(response, INVALID_VALUE_ERROR_MESSAGE_PATH, ERROR_MESSAGE_FOR_INVALID_VALUE));
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE RATE CARD WITH INVALID QUANTITY VALUE")
    @Test (groups = { "regression" }, priority = 4)
    public void verifyCreateRateCardInvalidQuantityValue() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = medisendAPI.createRateCard(headers, DISCOUNT_VALUE, DISCOUNT_MIN_QUANTITY_ZERO, DISCOUNT_MAX_QUANTITY, PRODUCT_ENTITY_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CONFLICT));
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE RATE CARD WITH QUANTITY INTERCHANGE VALUES")
    @Test (groups = { "regression" }, priority = 5)
    public void verifyCreateRateCardWithQuantityInterchangeValues() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = medisendAPI.createRateCard(headers, DISCOUNT_VALUE, DISCOUNT_MAX_QUANTITY, DISCOUNT_MIN_QUANTITY, PRODUCT_ENTITY_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CONFLICT));
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("GET RATE CARD")
    @Test (groups = { "regression" }, priority = 6)
    public void verifyGetRateCard() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = medisendAPI.getRateCard(headers, PRODUCT_ENTITY_ID_2);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        RATE_CARD_EXTERNAL_ID_2 = response.path(GET_RATE_CARD_ENTITY_PATH + EXTERNAL_ID_PATH);
        RATE_CARD_RULES_EXTERNAL_ID_2 = response.path(GET_RATE_CARD_ENTITY_PATH + RATE_CARD_RULES_EXTERNAL_ID_PATH);
        Assert.assertTrue(validateResponseValue(response, GET_RATE_CARD_ENTITY_PATH + PRODUCT_ENTITY_ID_PATH, PRODUCT_ENTITY_ID_2));
        Assert.assertTrue(validateResponseValue(response, GET_RATE_CARD_ENTITY_PATH + RATE_CARD_VALUE_PATH, EXPECTED_DISCOUNT_VALUE));
        Assert.assertTrue(validateResponseValue(response, GET_RATE_CARD_ENTITY_PATH + RATE_CARD_RULES_MIN_QUANTITY_PATH, DISCOUNT_MIN_QUANTITY));
        Assert.assertTrue(validateResponseValue(response, GET_RATE_CARD_ENTITY_PATH + RATE_CARD_RULES_MAX_QUANTITY_PATH, DISCOUNT_MAX_QUANTITY));
        Assert.assertTrue(validateResponseValue(response, GET_RATE_CARD_ENTITY_PATH + RATE_CARD_EFFECTIVE_PRICE_PATH, EFFECTIVE_PRICE));
        Assert.assertEquals(response.path(RATE_CARD_ID_PATH).toString(), response.path(GET_RATE_CARD_ENTITY_PATH + RATE_CARD_RULES_ID_PATH).toString());
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE RATE CARD VALUE")
    @Test (groups = { "regression" }, priority = 7)
    public void verifyUpdateValueRateCard() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = medisendAPI.updateRateCard(headers, PRODUCT_ENTITY_ID_2, "bc97af1f-0dc9-4ac9-a6cc-f47f399e4c80", DISCOUNT_VALUE, STATUS_ACTIVE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));
        Response response1 = medisendAPI.getRateCard(headers, PRODUCT_ENTITY_ID_2);
        Assert.assertTrue(validateResponseValue(response1, GET_RATE_CARD_ENTITY_PATH + RATE_CARD_VALUE_PATH, DISCOUNT_VALUE));
        Assert.assertTrue(validateResponseValue(response1, GET_RATE_CARD_ENTITY_PATH + RATE_CARD_EFFECTIVE_PRICE_PATH, EFFECTIVE_PRICE_FOR_UPDATE));
        medisendAPI.updateRateCard(headers, PRODUCT_ENTITY_ID_2, RATE_CARD_EXTERNAL_ID_2, DISCOUNT_MAX_QUANTITY, STATUS_ACTIVE);
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE RATE CARD STATUS")
    @Test (groups = { "regression" }, priority = 8)
    public void verifyUpdateStatusRateCard() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = medisendAPI.updateRateCard(headers, PRODUCT_ENTITY_ID_2, RATE_CARD_EXTERNAL_ID_2, DISCOUNT_MAX_QUANTITY, STATUS_INACTIVE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));
        Response response1 = medisendAPI.getRateCard(headers, PRODUCT_ENTITY_ID_2);
        Assert.assertTrue(validateResponseValue(response1, GET_RATE_CARD_ENTITY_PATH + RATE_CARD_STATUS_PATH, STATUS_INACTIVE));
        medisendAPI.updateRateCard(headers, PRODUCT_ENTITY_ID_2, RATE_CARD_EXTERNAL_ID_2, DISCOUNT_MAX_QUANTITY, STATUS_ACTIVE);
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("GET RATE FOR QUANTITY")
    @Test (groups = { "regression" }, priority = 9)
    public void verifyRateForQuantity() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = medisendAPI.getRateForProduct(headers, PRODUCT_ENTITY_ID_2, DISCOUNT_MAX_QUANTITY);
        Assert.assertTrue(validateResponseValue(response, RATE_CARD_EFFECTIVE_PRICE_PATH, EFFECTIVE_PRICE));
        Response response1 = medisendAPI.getRateForProduct(headers, PRODUCT_ENTITY_ID_2, QUANTITY_NO_RATE_CARD);
        Assert.assertTrue(validateStatusCode(response1, HttpStatus.SC_NO_CONTENT));
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("GET RATE OF QUANTITY FOR TWO RATE CARDS")
    @Test (groups = { "regression" }, priority = 10)
    public void verifyRateForQuantityForTwoRateCards() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = medisendAPI.getRateForProduct(headers, PRODUCT_ENTITY_ID_2, DISCOUNT_MAX_QUANTITY);
        Assert.assertTrue(validateResponseValue(response, RATE_CARD_EFFECTIVE_PRICE_PATH, EFFECTIVE_PRICE));
        Response response1 = medisendAPI.getRateForProduct(headers, PRODUCT_ENTITY_ID_2, QUANTITY_WITH_RATE_CARD);
        Assert.assertTrue(validateResponseValue(response1, RATE_CARD_EFFECTIVE_PRICE_PATH, EFFECTIVE_PRICE_FOR_QUANTITY));
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SELLING PRICE WHEN DISCOUNT TOGGLE IS ENABLED")
    @Test (groups = { "regression" }, priority = 11)
    public void verifySellingPriceWhenDiscountToggleEnabled() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        medisendAPI.updateProduct(headers, DISTRIBUTOR_ID_FOR_DISCOUNT_ENABLED, DISTRIBUTOR_BRANCH_FOR_DISCOUNT_ENABLED, PRODUCT_ENTITY_ID_2, PRODUCT_ID_FOR_DISCOUNT_ENABLED, STATUS_ACTIVE, COST_PRICE_FOR_DISCOUNT_ENABLED_PRODUCT, SKU_ID_FOR_DISCOUNT_ENABLED, AVAILABLE_QUANTITY_DISCOUNT_SETUP, FALSE, null);
        Response response1 = medisendAPI.getProduct(headers,DISTRIBUTOR_ID_FOR_DISCOUNT_ENABLED, DISTRIBUTOR_BRANCH_FOR_DISCOUNT_ENABLED, PRODUCT_ENTITY_ID_2);
        Assert.assertTrue(validateResponseValue(response1, SELLING_PRICE_PATH, EXPECTED_SELLING_PRICE));
        medisendAPI.updateProduct(headers, DISTRIBUTOR_ID_FOR_DISCOUNT_ENABLED, DISTRIBUTOR_BRANCH_FOR_DISCOUNT_ENABLED, PRODUCT_ENTITY_ID_2, PRODUCT_ID_FOR_DISCOUNT_ENABLED, STATUS_ACTIVE, COST_PRICE_FOR_DISCOUNT_ENABLED_PRODUCT, SKU_ID_FOR_DISCOUNT_ENABLED, AVAILABLE_QUANTITY_DISCOUNT_SETUP, TRUE, null);
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
