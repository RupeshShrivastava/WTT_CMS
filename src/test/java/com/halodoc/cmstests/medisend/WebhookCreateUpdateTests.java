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
public class WebhookCreateUpdateTests extends BaseCMSTest {
    private MedisendAPI medisendAPI = new MedisendAPI();
    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE PRODUCT")
    @Test (groups = { "regression" }, priority = 1)
    public void verifyCreateProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Long epochTime = getEpochTime();
        String productName = CREATE_MASTER_PRODUCT_NAME + epochTime;
        String skuId = CREATE_MAI_SKU + epochTime;

        Response response = medisendAPI.createUpdateProductByWebhook(MODE_CREATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, productName, COST_PRICE,
                STATUS_ACTIVE, skuId, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, ENTITY_TYPE_PATH, ENTITY_TYPE_DISTRIBUTOR));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ENTITY_ID_PATH, SEARCH_PRODUCT_DISTRIBUTOR_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, productName));
        Assert.assertTrue(validateResponseValue(response, PRICE_PATH, COST_PRICE));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, skuId));
        Assert.assertTrue(validateResponseValue(response, SEGMENTATION_PATH, SEGMENTATION_MAI_RED));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE PRODUCT WITH NO DISCOUNT")
    @Test (groups = { "sanity", "regression" }, priority = 2)
    public void verifyUpdateProductWithNoDiscount() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createUpdateProductByWebhook(MODE_UPDATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, NO_DISCOUNT_PRODUCT_NAME,
                UPDATED_PRICE, STATUS_INACTIVE, NO_DISCOUNT_PRODUCT_SKU, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, ENTITY_TYPE_PATH, ENTITY_TYPE_DISTRIBUTOR));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ENTITY_ID_PATH, SEARCH_PRODUCT_DISTRIBUTOR_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, NO_DISCOUNT_PRODUCT_NAME));
        Assert.assertTrue(validateResponseValue(response, PRICE_PATH, UPDATED_PRICE));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_INACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, NO_DISCOUNT_PRODUCT_SKU));
        Assert.assertTrue(validateResponseValue(response, SEGMENTATION_PATH, SEGMENTATION_MAI_GREEN));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, NO_DISCOUNT_PRODUCT_NAME, STATUS_INACTIVE,
                TRUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, NO_DISCOUNT_PRODUCT_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, NO_DISCOUNT_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, UPDATED_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, UPDATED_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_INACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, NO_DISCOUNT_PRODUCT_SKU, MODE_EQUALS));

        medisendAPI.createUpdateProductByWebhook(MODE_UPDATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, NO_DISCOUNT_PRODUCT_NAME,
                COST_PRICE, STATUS_ACTIVE, NO_DISCOUNT_PRODUCT_SKU, SEGMENTATION_MAI_RED);

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE PRODUCT WITH DISABLED DISCOUNT")
    @Test (groups = { "regression" }, priority = 3)
    public void verifyUpdateProductWithDisabledDiscount() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createUpdateProductByWebhook(MODE_UPDATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, MATCH_PRODUCT_NAME,
                UPDATED_PRICE, STATUS_INACTIVE, MATCH_SKU_ID, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, ENTITY_TYPE_PATH, ENTITY_TYPE_DISTRIBUTOR));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ENTITY_ID_PATH, SEARCH_PRODUCT_DISTRIBUTOR_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, MATCH_PRODUCT_NAME));
        Assert.assertTrue(validateResponseValue(response, PRICE_PATH, UPDATED_PRICE));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_INACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, MATCH_SKU_ID));
        Assert.assertTrue(validateResponseValue(response, SEGMENTATION_PATH, SEGMENTATION_MAI_GREEN));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, MATCH_PRODUCT_NAME, STATUS_INACTIVE,
                TRUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, MATCH_SKU_PRODUCT_EXTERNAL_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, MATCH_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, UPDATED_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, UPDATED_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_INACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, MATCH_SKU_ID, MODE_EQUALS));

        medisendAPI.createUpdateProductByWebhook(MODE_UPDATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, MATCH_PRODUCT_NAME, COST_PRICE,
                STATUS_ACTIVE, MATCH_SKU_ID, SEGMENTATION_MAI_RED);

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE PRODUCT WITH ENABLED DISCOUNT")
    @Test (groups = { "regression" }, priority = 4)
    public void verifyUpdateProductWithEnabledDiscount() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createUpdateProductByWebhook(MODE_UPDATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, DISCOUNT_PRODUCT_NAME,
                UPDATED_PRICE, STATUS_INACTIVE, DISCOUNT_PRODUCT_SKU, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, ENTITY_TYPE_PATH, ENTITY_TYPE_DISTRIBUTOR));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ENTITY_ID_PATH, SEARCH_PRODUCT_DISTRIBUTOR_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, DISCOUNT_PRODUCT_NAME));
        Assert.assertTrue(validateResponseValue(response, PRICE_PATH, UPDATED_PRICE));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_INACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, DISCOUNT_PRODUCT_SKU));
        Assert.assertTrue(validateResponseValue(response, SEGMENTATION_PATH, SEGMENTATION_MAI_GREEN));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, DISCOUNT_PRODUCT_NAME, STATUS_INACTIVE,
                TRUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, DISCOUNT_PRODUCT_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, DISCOUNT_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, UPDATED_PRICE_DISCOUNT_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, UPDATED_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_INACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, DISCOUNT_PRODUCT_SKU, MODE_EQUALS));

        medisendAPI.createUpdateProductByWebhook(MODE_UPDATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, DISCOUNT_PRODUCT_NAME, COST_PRICE,
                STATUS_ACTIVE, DISCOUNT_PRODUCT_SKU, SEGMENTATION_MAI_PPO);

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE UNMAPPED PRODUCT")
    @Test (groups = { "regression" }, priority = 5)
    public void verifyUpdateUnmappedProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createUpdateProductByWebhook(MODE_UPDATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, UNMAPPED_PRODUCT_NAME,
                UPDATED_PRICE, STATUS_INACTIVE, UNMAPPED_PRODUCT_SKU, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, ENTITY_TYPE_PATH, ENTITY_TYPE_DISTRIBUTOR));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ENTITY_ID_PATH, SEARCH_PRODUCT_DISTRIBUTOR_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, UNMAPPED_PRODUCT_NAME));
        Assert.assertTrue(validateResponseValue(response, PRICE_PATH, UPDATED_PRICE));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_INACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, UNMAPPED_PRODUCT_SKU));
        Assert.assertTrue(validateResponseValue(response, SEGMENTATION_PATH, SEGMENTATION_MAI_GREEN));

        medisendAPI.createUpdateProductByWebhook(MODE_UPDATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, UNMAPPED_PRODUCT_NAME, COST_PRICE,
                STATUS_ACTIVE, UNMAPPED_PRODUCT_SKU, SEGMENTATION_MAI_RED);

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE PRODUCT WITH DIFFERENT NAME")
    @Test (groups = { "regression" }, priority = 6)
    public void verifyUpdateProductWithDifferentName() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createUpdateProductByWebhook(MODE_UPDATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, DISCOUNT_PRODUCT_NAME,
                COST_PRICE, STATUS_ACTIVE, NO_DISCOUNT_PRODUCT_SKU, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, ENTITY_TYPE_PATH, ENTITY_TYPE_DISTRIBUTOR));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ENTITY_ID_PATH, SEARCH_PRODUCT_DISTRIBUTOR_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, DISCOUNT_PRODUCT_NAME));
        Assert.assertTrue(validateResponseValue(response, PRICE_PATH, COST_PRICE));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, NO_DISCOUNT_PRODUCT_SKU));
        Assert.assertTrue(validateResponseValue(response, SEGMENTATION_PATH, SEGMENTATION_MAI_GREEN));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, NO_DISCOUNT_PRODUCT_NAME, STATUS_ACTIVE,
                TRUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, NO_DISCOUNT_PRODUCT_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, NO_DISCOUNT_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, NO_DISCOUNT_PRODUCT_SKU, MODE_EQUALS));

        medisendAPI.createUpdateProductByWebhook(MODE_UPDATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, NO_DISCOUNT_PRODUCT_NAME,
                COST_PRICE, STATUS_ACTIVE, NO_DISCOUNT_PRODUCT_SKU, SEGMENTATION_MAI_RED);

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}