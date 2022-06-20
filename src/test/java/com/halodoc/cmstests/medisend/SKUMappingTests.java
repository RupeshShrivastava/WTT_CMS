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
public class SKUMappingTests extends BaseCMSTest {
    private MedisendAPI medisendAPI = new MedisendAPI();
    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);
    private HashMap<String, String> headersOmega = getAppTokenHeaders(X_APP_TOKEN_OMEGA);

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH PRODUCT MAPPED")
    @Test (groups = { "sanity", "regression" }, priority = 1)
    public void verifySearchProductMapped() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, EMPTY_STRING, EMPTY_STRING,
                TRUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, EMPTY_STRING, MODE_NOT_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH PRODUCT UNMAPPED")
    @Test (groups = { "regression" }, priority = 2)
    public void verifySearchProductUnmapped() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, EMPTY_STRING, EMPTY_STRING,
                FALSE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, EMPTY_STRING, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("GET PRODUCT COUNT")
    @Test (groups = { "regression" }, priority = 3)
    public void verifyGetProductCount() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getProductCount(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH DUMP PRODUCT BY NAME")
    @Test (groups = { "regression" }, priority = 4)
    public void verifySearchDumpProductByName() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchDumpProduct(headers, SEARCH_DUMP_PRODUCT_NAME, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, SEARCH_DUMP_PRODUCT_NAME, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH DUMP PRODUCT BY STATUS")
    @Test (groups = { "regression" }, priority = 5)
    public void verifySearchDumpProductByStatus() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchDumpProduct(headers, EMPTY_STRING, STATUS_ACTIVE, EMPTY_STRING, EMPTY_STRING);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH DUMP PRODUCT WITH PAGE LIMIT")
    @Test (groups = { "regression" }, priority = 6)
    public void verifySearchDumpProductWithPageLimit() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchDumpProduct(headers, EMPTY_STRING, EMPTY_STRING, PAGE_LIMIT, EMPTY_STRING);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateArraySize(response, RESULT_PATH, Integer.valueOf(PAGE_LIMIT)));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH DUMP PRODUCT BY ENTITY ID")
    @Test (groups = { "regression" }, priority = 7)
    public void verifySearchDumpProductByEntityId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchDumpProduct(headers, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, SEARCH_PRODUCT_DISTRIBUTOR_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ENTITY_ID_PATH, SEARCH_PRODUCT_DISTRIBUTOR_ID, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH DUMP PRODUCT BY COMBINED PARAM")
    @Test (groups = { "regression" }, priority = 8)
    public void verifySearchDumpProductByCombinedParam() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchDumpProduct(headers, SEARCH_DUMP_PRODUCT_NAME, STATUS_ACTIVE, PAGE_LIMIT, SEARCH_PRODUCT_DISTRIBUTOR_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, SEARCH_DUMP_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ENTITY_ID_PATH, SEARCH_PRODUCT_DISTRIBUTOR_ID, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("MULTI GET MAPPING")
    @Test (groups = { "regression" }, priority = 9)
    public void verifyMultiGetMapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.multiGetMapped(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, skuIds);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, PRODUCT_ENTITY_ID_PATH, SEARCH_PRODUCT_DISTRIBUTOR_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, SKU_ID_PATH, SKU_ID_MAPPED, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("ADD BRANCH PRODUCT WITH BLANK SKU AND STATUS ACTIVE")
    @Test (groups = { "regression" }, priority = 10)
    public void verifyAddBranchProductWithBlankSKUandStatusActive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String externalId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, externalId, STATUS_ACTIVE,
                COST_PRICE, EMPTY_STRING, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ID_PATH, externalId));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, productName));
        Assert.assertTrue(validateResponseValue(response, COST_PRICE_PATH, COST_PRICE));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_INACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, EMPTY_STRING));
        Assert.assertTrue(validateResponseValue(response, AVAILABLE_QUANTITY_PATH, Integer.valueOf(AVAILABLE_QUANTITY)));
        Assert.assertTrue(validateResponseValue(response, SEGMENTATION_PATH, SEGMENTATION_MAI_RED));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("ADD PRODUCT WITH BLANK SKU AND SKU EXISTING IN NO OTHER BRANCH")
    @Test (groups = { "regression" }, priority = 11)
    public void verifyAddBranchProductWithBlankSKUandSKUexistinotherbranch() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID,
                MAPPED_PRODUCT_OTHER_BRANCH_EXTERNAL_ID, STATUS_ACTIVE, COST_PRICE, EMPTY_STRING, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("ADD BRANCH PRODUCT WITH UNMATCHED DISTRIBUTOR SKU")
    @Test (groups = { "regression" }, priority = 12)
    public void verifyAddBranchProductWithUnmatchDistributorSKU() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID,
                UNMATCH_PRODUCT_OTHER_BRANCH_EXTERNAL_ID, STATUS_ACTIVE, COST_PRICE, UNMATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("ADD BRANCH PRODUCT WITH MATCH DISTRIBUTOR SKU")
    @Test (groups = { "regression" }, priority = 13)
    public void verifyAddBranchProductWithMatchDistributorSKU() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String branchName = CREATE_BRANCH_NAME + getEpochTime();
        Response response = medisendAPI.createDistributorsBranch(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchName, STATUS_ACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String branchId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchId, MATCH_SKU_PRODUCT_EXTERNAL_ID, STATUS_ACTIVE,
                COST_PRICE, MATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ID_PATH, MATCH_SKU_PRODUCT_EXTERNAL_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, MATCH_PRODUCT_NAME));
        Assert.assertTrue(validateResponseValue(response, SELLING_PRICE_PATH, COST_PRICE_LONG));
        Assert.assertTrue(validateResponseValue(response, COST_PRICE_PATH, COST_PRICE_LONG));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, MATCH_SKU_ID));
        Assert.assertTrue(validateResponseValue(response, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY));
        Assert.assertTrue(validateResponseValue(response, SEGMENTATION_PATH, SEGMENTATION_MAI_RED));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("ADD BRANCH PRODUCT WITH MATCH DISTRIBUTOR SKU AND BLANK SKU EXIST IN NO OTHER BRANCH")
    @Test (groups = { "regression" }, priority = 14)
    public void verifyAddBranchProductWithMatchDistributorSKUandBlankSKUexistinotherbranch() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String branchName = CREATE_BRANCH_NAME + getEpochTime();
        Response response = medisendAPI.createDistributorsBranch(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchName, STATUS_ACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String branchId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchId, MATCH_SKU_BLANK_PRODUCT_EXTERNAL_ID, STATUS_ACTIVE,
                COST_PRICE, BLANK_MATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ID_PATH, MATCH_SKU_BLANK_PRODUCT_EXTERNAL_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, BLANK_MATCH_PRODUCT_NAME));
        Assert.assertTrue(validateResponseValue(response, SELLING_PRICE_PATH, COST_PRICE_LONG));
        Assert.assertTrue(validateResponseValue(response, COST_PRICE_PATH, COST_PRICE_LONG));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, BLANK_MATCH_SKU_ID));
        Assert.assertTrue(validateResponseValue(response, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY));
        Assert.assertTrue(validateResponseValue(response, SEGMENTATION_PATH, SEGMENTATION_MAI_RED));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, BLANK_MATCH_BRANCH_ID, BLANK_MATCH_PRODUCT_NAME, STATUS_INACTIVE,
                FALSE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, MATCH_SKU_BLANK_PRODUCT_EXTERNAL_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, BLANK_MATCH_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_INACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, EMPTY_STRING, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("ADD BRANCH PRODUCT WITH MATCH DISTRIBUTOR SKU AND DIFFERENT PRICE STATUS")
    @Test (groups = { "regression" }, priority = 15)
    public void verifyAddBranchProductWithMatchDistributorSKUandDifferentPriceStatus() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String branchName = CREATE_BRANCH_NAME + getEpochTime();
        Response response = medisendAPI.createDistributorsBranch(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchName, STATUS_ACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String branchId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchId, MATCH_SKU_PRODUCT_EXTERNAL_ID, STATUS_INACTIVE,
                UPDATED_PRICE, MATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ID_PATH, MATCH_SKU_PRODUCT_EXTERNAL_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, MATCH_PRODUCT_NAME));
        Assert.assertTrue(validateResponseValue(response, SELLING_PRICE_PATH, COST_PRICE_LONG));
        Assert.assertTrue(validateResponseValue(response, COST_PRICE_PATH, COST_PRICE_LONG));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, MATCH_SKU_ID));
        Assert.assertTrue(validateResponseValue(response, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY));
        Assert.assertTrue(validateResponseValue(response, SEGMENTATION_PATH, SEGMENTATION_MAI_RED));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("ADD BRANCH PRODUCT WITH MATCH DISTRIBUTOR SKU ANND DIFFERENT SKU WITH OTHER BRANCH")
    @Test (groups = { "regression" }, priority = 16)
    public void verifyAddBranchProductWithMatchDistributorSKUandDifferentSKUwithotherbranch() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String branchName = CREATE_BRANCH_NAME + getEpochTime();
        Response response = medisendAPI.createDistributorsBranch(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchName, STATUS_ACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String branchId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchId, MATCH_SKU_PRODUCT_EXTERNAL_ID, STATUS_ACTIVE,
                COST_PRICE, BLANK_MATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("ADD BRANCH PRODUCT WITH DUPLICATE MATCH")
    @Test (groups = { "regression" }, priority = 17)
    public void verifyAddBranchProductWithDuplicateMatch() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, MATCH_SKU_PRODUCT_EXTERNAL_ID,
                STATUS_ACTIVE, COST_PRICE, MATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CONFLICT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE BRANCH PRODUCT BLANK SKU WITH STATUS ACTIVE")
    @Test (groups = { "regression" }, priority = 18)
    public void verifyUpdateBranchProductBlankSKUWithStatusActive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, BLANK_MATCH_BRANCH_ID, BLANK_MATCH_PRODUCT_MAPPING_ID,
                MATCH_SKU_BLANK_PRODUCT_EXTERNAL_ID, STATUS_ACTIVE, COST_PRICE, EMPTY_STRING, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, BLANK_MATCH_BRANCH_ID, BLANK_MATCH_PRODUCT_NAME, STATUS_INACTIVE,
                FALSE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, MATCH_SKU_BLANK_PRODUCT_EXTERNAL_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, BLANK_MATCH_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_INACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, EMPTY_STRING, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE BRANCH PRODUCT BLANK SKU WITH STATUS INACTIVE")
    @Test (groups = { "regression" }, priority = 19)
    public void verifyUpdateBranchProductBlankSKUWithStatusInactive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, BLANK_MATCH_BRANCH_ID, BLANK_MATCH_PRODUCT_MAPPING_ID,
                MATCH_SKU_BLANK_PRODUCT_EXTERNAL_ID, STATUS_ACTIVE, COST_PRICE, EMPTY_STRING, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, BLANK_MATCH_BRANCH_ID, BLANK_MATCH_PRODUCT_NAME, STATUS_INACTIVE,
                FALSE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, MATCH_SKU_BLANK_PRODUCT_EXTERNAL_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, BLANK_MATCH_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_INACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, EMPTY_STRING, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE BRANCH PRODUCT WITH BLANK SKU AND SKU EXIST IN NO OTHER BRANCH")
    @Test (groups = { "regression" }, priority = 20)
    public void verifyUpdateBranchProductWithBlankSKUandSKUexistinotherbranch() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String branchName = CREATE_BRANCH_NAME + getEpochTime();
        Response response = medisendAPI.createDistributorsBranch(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchName, STATUS_ACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String branchId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchId, MATCH_SKU_PRODUCT_EXTERNAL_ID, STATUS_ACTIVE,
                COST_PRICE, MATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String mappingId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.updateProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchId, mappingId, MATCH_SKU_PRODUCT_EXTERNAL_ID, STATUS_ACTIVE,
                COST_PRICE, EMPTY_STRING, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchId, MATCH_PRODUCT_NAME, STATUS_INACTIVE, FALSE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, MATCH_SKU_PRODUCT_EXTERNAL_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, MATCH_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_INACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, EMPTY_STRING, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY, MODE_EQUALS));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, MATCH_PRODUCT_NAME, STATUS_ACTIVE, TRUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, MATCH_SKU_PRODUCT_EXTERNAL_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, MATCH_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, MATCH_SKU_ID, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("UPDATE BRANCH PRODUCT WITH UNMATCH SKU")
    @Test (groups = { "regression" }, priority = 21)
    public void verifyUpdateBranchProductWithUnmatchSKU() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, UNMATCH_UPDATE_PRODUCT_MAPPING_ID,
                UNMATCH_UPDATE_PRODUCT_ID, STATUS_ACTIVE, COST_PRICE, UNMATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE BRANCH PRODUCT WITH MATCH SKU")
    @Test (groups = { "regression" }, priority = 22)
    public void verifyUpdateBranchProductWithMatchSKU() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String epochTime = Long.toString(getEpochTime());
        String productName = CREATE_MASTER_PRODUCT_NAME + epochTime;
        String skuId = CREATE_MAI_SKU + epochTime;
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String externalId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, externalId, STATUS_INACTIVE, COST_PRICE,
                EMPTY_STRING, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String mappingId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createUpdateProductByWebhook(MODE_CREATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, productName, COST_PRICE, STATUS_ACTIVE,
                skuId, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));

        response = medisendAPI.updateProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, mappingId, externalId, STATUS_ACTIVE,
                COST_PRICE, skuId, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, productName, STATUS_ACTIVE, TRUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, externalId, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, productName, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, skuId, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE BRANCH PRODUCT WITH MATCH SKU AND BLANK SKU EXIST IN NO OTHER BRANCH")
    @Test (groups = { "regression" }, priority = 23)
    public void verifyUpdateBranchProductWithMatchSKUAndBlankSKUexistinotherbranch() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String epochTime = Long.toString(getEpochTime());
        String productName = CREATE_MASTER_PRODUCT_NAME + epochTime;
        String skuId = CREATE_MAI_SKU + epochTime;
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String externalId = response.path(EXTERNAL_ID_PATH);

        String branchName = CREATE_BRANCH_NAME + getEpochTime();
        response = medisendAPI.createDistributorsBranch(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchName, STATUS_ACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String branchId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, externalId, STATUS_INACTIVE, COST_PRICE,
                EMPTY_STRING, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String mappingId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchId, externalId, STATUS_INACTIVE, COST_PRICE, EMPTY_STRING,
                AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));

        response = medisendAPI.createUpdateProductByWebhook(MODE_CREATE, headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, productName, COST_PRICE, STATUS_ACTIVE,
                skuId, SEGMENTATION_MAI_RED);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));

        response = medisendAPI.updateProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, mappingId, externalId, STATUS_ACTIVE,
                COST_PRICE, skuId, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, productName, STATUS_ACTIVE, TRUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, externalId, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, productName, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, skuId, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY, MODE_EQUALS));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, branchId, productName, STATUS_INACTIVE, FALSE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, externalId, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, productName, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_INACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, EMPTY_STRING, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE BRANCH PRODUCT WITH DIFFERENT PRICE AND STATUS")
    @Test (groups = { "regression" }, priority = 24)
    public void verifyUpdateBranchProductWithDifferentPriceAndStatus() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, MATCH_PRODUCT_MAPPING_ID,
                MATCH_SKU_PRODUCT_EXTERNAL_ID, STATUS_INACTIVE, UPDATED_PRICE, MATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, MATCH_PRODUCT_NAME, STATUS_ACTIVE, TRUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, PRODUCT_ID_PATH, MATCH_SKU_PRODUCT_EXTERNAL_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, MATCH_PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SELLING_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, COST_PRICE_PATH, COST_PRICE_LONG, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SKU_ID_PATH, MATCH_SKU_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, AVAILABLE_QUANTITY_PATH, AVAILABLE_QUANTITY, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE BRANCH PRODUCT WITH DIFFERENT SKU")
    @Test (groups = { "regression" }, priority = 25)
    public void verifyUpdateBranchProductWithDifferentSKU() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, MATCH_PRODUCT_MAPPING_ID,
                MATCH_SKU_PRODUCT_EXTERNAL_ID, STATUS_ACTIVE, COST_PRICE, BLANK_MATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE BRANCH PRODUCT WITH DUPLICATE SKU")
    @Test (groups = { "regression" }, priority = 26)
    public void verifyUpdateBranchProductWithDuplicateSKU() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, BLANK_SKU_PRODUCT_MAPPING_ID,
                BLANK_SKU_PRODUCT_ID, STATUS_ACTIVE, COST_PRICE, MATCH_SKU_ID, AVAILABLE_QUANTITY, FALSE, SEGMENTATION_MAI_GREEN);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("DOWNLOAD CSV FOR SKU MAPPINGS")
    @Test (groups = { "regression" }, priority = 27)
    public void verifyDownloadCSV() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.requestCSV(headersOmega, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, REQUEST_CSV_TYPE_SKU_MAPPING);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, TYPE_PATH, REQUEST_CSV_TYPE_SKU_MAPPING));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_SUBMITTED));
        String requestId = response.path(IDENTIFIER_PATH);

        medisendAPI.checkStatusUntil(headersOmega, requestId, STATUS_COMPLETED);

        response = medisendAPI.getCSV(headersOmega, requestId);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, IDENTIFIER_PATH, requestId));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_COMPLETED));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}