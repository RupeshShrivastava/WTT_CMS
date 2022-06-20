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
public class MerchantLocationMappingTests extends BaseCMSTest {

    MedisendAPI medisendAPI = new MedisendAPI();
    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);
    private HashMap<String, String> headersOmega = getAppTokenHeaders(X_APP_TOKEN_OMEGA);
    private String DISTRIBUTOR_PHARMACY_ENTITY = "TESTDISTRIBUTORID" + getEpochTime();
    private String PHARMACY_LOCATION_ID = "TESTPHARLOCID" + getEpochTime();

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE DISTRIBUTOR PHARMACY ENTITY")
    @Test (groups = { "regression" }, priority = 1)
    public void verifyCreateDistributorPharmacyEntity() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createDistributorPharmacyEntity(headers, DISTRIBUTOR_PHARMACY_ENTITY, PHARMACY_LOCATION_ID, CREATE_DISTRIBUTOR_ENTITY_NAME, STATUS_ACTIVE, ADDRESS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, DISTRIBUTOR_REFERENCE_ID_PATH, DISTRIBUTOR_PHARMACY_ENTITY));
        Assert.assertTrue(validateResponseValue(response, PHARMACY_LOCATION_ID_PATH, PHARMACY_LOCATION_ID));
        Assert.assertTrue(validateResponseValue(response, ATTRIBUTES_KEY_PATH, ADDRESS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE DISTRIBUTOR PHARMACY ENTITY WITH EMPTY DISTRIBUTOR PHARMACY ENTITY")
    @Test (groups = { "sanity", "regression" }, priority = 2)
    public void verifyCreateDistributorPharmacyEntityEmptyDistributorPharmacyEntity() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createDistributorPharmacyEntity(headers, EMPTY_STRING, PHARMACY_LOCATION_ID, CREATE_DISTRIBUTOR_ENTITY_NAME, STATUS_ACTIVE, ADDRESS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_UNPROCESSABLE_ENTITY));
        Assert.assertTrue(validateResponseValue(response, INVALID_VALUE_ERROR_MESSAGE_PATH, ERROR_MESSAGE_EMPTY_DISTRIBUTOR_ID));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE DISTRIBUTOR PHARMACY ENTITY WITH EMPTY PHARMACY LOCATION ID")
    @Test (groups = { "regression" }, priority = 3)
    public void verifyCreateDistributorPharmacyEntityEmptyPharmacyLocationId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createDistributorPharmacyEntity(headers, DISTRIBUTOR_PHARMACY_ENTITY, EMPTY_STRING, CREATE_DISTRIBUTOR_ENTITY_NAME, STATUS_ACTIVE, ADDRESS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_UNPROCESSABLE_ENTITY));
        Assert.assertTrue(validateResponseValue(response, INVALID_VALUE_ERROR_MESSAGE_PATH, ERROR_MESSAGE_EMPTY_PHARMACY_LOCATION_ID));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE DISTRIBUTOR PHARMACY ENTITY WITH EMTPY NAME")
    @Test (groups = { "regression" }, priority = 4)
    public void verifyCreateDistributorPharmacyEntityEmptyName() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createDistributorPharmacyEntity(headers, DISTRIBUTOR_PHARMACY_ENTITY, PHARMACY_LOCATION_ID, EMPTY_STRING, STATUS_ACTIVE, ADDRESS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_UNPROCESSABLE_ENTITY));
        Assert.assertTrue(validateResponseValue(response, INVALID_VALUE_ERROR_MESSAGE_PATH, ERROR_MESSAGE_EMPTY_NAME));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE DISTRIBUTOR PHARMACY ENTITY WITH EMPTY STATUS")
    @Test (groups = { "regression" }, priority = 5)
    public void verifyCreateDistributorPharmacyEntityEmptyStatus() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createDistributorPharmacyEntity(headers, DISTRIBUTOR_PHARMACY_ENTITY, PHARMACY_LOCATION_ID, CREATE_DISTRIBUTOR_ENTITY_NAME, EMPTY_STRING, ADDRESS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE DISTRIBUTOR PHARMACY ENTITY FOR EXISTING ENTRY")
    @Test (groups = { "regression" }, priority = 6)
    public void verifyCreateDistributorPharmacyEntityForExistingEntry() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createDistributorPharmacyEntity(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID, EXISITING_PHARMACY_LOCATION_ID, EXISTING_DISTRIBUTOR_NAME, STATUS_ACTIVE, ADDRESS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CONFLICT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("GET DISTRIBUTOR PHARMACY ENTITY USING EXTERNAL ID")
    @Test (groups = { "regression" }, priority = 7, dependsOnMethods = "verifyCreateDistributorPharmacyEntityForExistingEntry")
    public void verifyGetDistributorPharmacyEntityUsingExternalId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorPharmacyEntity(headers, EXISTING_DISTRIBUTOR_EXTERNAL_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, DISTRIBUTOR_REFERENCE_ID_PATH, EXISTING_DISTRIBUTOR_REFERENCE_ID));
        Assert.assertTrue(validateResponseValue(response, PHARMACY_LOCATION_ID_PATH, EXISITING_PHARMACY_LOCATION_ID));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE DISTRIBUTOR PHARMACY ENTITY")
    @Test (groups = { "regression" }, priority = 8, dependsOnMethods = "verifyGetDistributorPharmacyEntityUsingExternalId")
    public void verifyUpdateDistributorPharmacyEntity() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorPharmacyEntity(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID, EXISITING_PHARMACY_LOCATION_ID, CREATE_BRANCH_NAME, STATUS_INACTIVE, ADDRESS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));
        Response response1 = medisendAPI.getDistributorPharmacyEntity(headers, EXISTING_DISTRIBUTOR_EXTERNAL_ID);
        Assert.assertTrue(validateStatusCode(response1, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response1, NAME_PATH, CREATE_BRANCH_NAME));
        Assert.assertTrue(validateResponseValue(response1, STATUS_PATH, STATUS_INACTIVE));
        Assert.assertTrue(validateResponseValue(response1, ADDRESS_PATH, ADDRESS));
        medisendAPI.updateDistributorPharmacyEntity(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID, EXISITING_PHARMACY_LOCATION_ID, EXISTING_DISTRIBUTOR_NAME, STATUS_ACTIVE, EXISTING_DISTRIBUTOR_ADDRESS);

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("UPDATE DISTRIBUTOR PHARMACY ENTITY NON EXISTING")
    @Test (groups = { "regression" }, priority = 9, dependsOnMethods = "verifyUpdateDistributorPharmacyEntity")
    public void verifyUpdateDistributorPharmacyEntityNonExisting() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorPharmacyEntity(headers, EXISITING_PHARMACY_LOCATION_ID, EXISTING_DISTRIBUTOR_NAME, CREATE_BRANCH_NAME, STATUS_INACTIVE, ADDRESS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("GET DISTRIBUTOR USING DISTRIBUTOR AND LOCATION ID")
    @Test (groups = { "regression" }, priority = 10)
    public void verifyGetDistributorUsingDistributorAndLocationId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorPharmacyEntityUsingDistributorAndLocationId(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID, EXISITING_PHARMACY_LOCATION_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, DISTRIBUTOR_REFERENCE_ID_PATH, EXISTING_DISTRIBUTOR_REFERENCE_ID));
        Assert.assertTrue(validateResponseValue(response, PHARMACY_LOCATION_ID_PATH, EXISITING_PHARMACY_LOCATION_ID));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("GET DISTRIBUTOR USING DISTRIBUTOR AND NOT LOCATION ID")
    @Test (groups = { "regression" }, priority = 11, dependsOnMethods = "verifyGetDistributorUsingDistributorAndLocationId")
    public void verifyGetDistributorUsingDistributorAndWithoutLocationId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorPharmacyEntityUsingDistributorAndLocationId(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID, EMPTY_STRING);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE DISTRIBUTOR PHARMACY ENTITY USING EXTERNAL ID")
    @Test (groups = { "regression" }, priority = 12, dependsOnMethods = "verifyGetDistributorPharmacyEntityUsingExternalId")
    public void verifyUpdateDistributorPharmacyEntityUsingExternalId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorPharmacyEntityUsingExternalId(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID, EXISITING_PHARMACY_LOCATION_ID, CREATE_BRANCH_NAME, STATUS_INACTIVE, ADDRESS, EXISTING_DISTRIBUTOR_EXTERNAL_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));
        Response response1 = medisendAPI.getDistributorPharmacyEntity(headers, EXISTING_DISTRIBUTOR_EXTERNAL_ID);
        Assert.assertTrue(validateStatusCode(response1, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response1, NAME_PATH, CREATE_BRANCH_NAME));
        Assert.assertTrue(validateResponseValue(response1, STATUS_PATH, STATUS_INACTIVE));
        Assert.assertTrue(validateResponseValue(response1, ADDRESS_PATH, ADDRESS));
        medisendAPI.updateDistributorPharmacyEntity(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID, EXISITING_PHARMACY_LOCATION_ID, EXISTING_DISTRIBUTOR_NAME, STATUS_ACTIVE, EXISTING_DISTRIBUTOR_ADDRESS);

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("UPDATED DISTRIBUTOR PHARMACY ENTITY USING INVALID EXTERNAL ID")
    @Test (groups = { "regression" }, priority = 13, dependsOnMethods = "verifyUpdateDistributorPharmacyEntityUsingExternalId")
    public void verifyUpdateDistributorPharmacyEntityUsingInvalidExternalId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorPharmacyEntityUsingExternalId(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID, EXISITING_PHARMACY_LOCATION_ID, CREATE_BRANCH_NAME, STATUS_INACTIVE, ADDRESS, INVALID_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH USING DISTRIBUTOR REFERENCE ID")
    @Test (groups = { "regression" }, priority = 14)
    public void verifySearchUsingDistributorReferenceId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchDistributorPharmacyEntity(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID_1);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateArraySize(response, RESULT_PATH, 4));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH DISTRIBUTOR PHARMACY ENTITY USING TEXT")
    @Test (groups = { "regression" }, priority = 15, dependsOnMethods = "verifySearchUsingDistributorReferenceId")
    public void verifySearchUsingText() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchDistributorPharmacyEntityUsingText(headers, DISTRIBUTOR_SEARCH_TEXT);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateArraySize(response, RESULT_PATH, COUNT_USING_SEARCH_TEXT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("BRANCH MAPPING COUNT")
    @Test (groups = { "regression" }, priority = 16)
    public void verifyBranchMappingCount() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorEntityMappingCount(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID_1, EXISTING_BRANCH_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, TOTAL_PATH, TOTAL_COUNT_OF_DISTRIBUTOR_BRANCH));
        Assert.assertTrue(validateResponseValue(response, MAPPED_PATH, PAGE_LIMIT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE DISTRIBUTOR ENTITY MERCHANT LOCATION MAPPING")
    @Test (groups = { "regression" }, priority = 17, dependsOnMethods = "verifyBranchMappingCount")
    public void verifyUpdateDistributorEntityMerchantLocationMapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorPharmacyEntityMapping(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID_1, EXISTING_BRANCH_ID, EXISTING_MERCHANT_LOCATION_ID, DISTRIBUTOR_MAPPING_REFERENCE_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));
        Response response1 = medisendAPI.getDistributorEntityMappingCount(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID_1, EXISTING_BRANCH_ID);
        Assert.assertTrue(validateStatusCode(response1, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response1, MAPPED_PATH, NEW_COUNT_OF_MAPPED_BRANCH));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE DISTRIBUTOR ENTITY MERCHANT LOCATION UNMAPPING")
    @Test (groups = { "regression" }, priority = 18, dependsOnMethods = "verifyUpdateDistributorEntityMerchantLocationMapping")
    public void verifyUpdateDistributorEntityMerchantLocationUnmapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorPharmacyEntityMapping(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID_1, EXISTING_BRANCH_ID, EXISTING_MERCHANT_LOCATION_ID, "null");
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));
        Response response1 = medisendAPI.getDistributorEntityMappingCount(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID_1, EXISTING_BRANCH_ID);
        Assert.assertTrue(validateStatusCode(response1, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response1, MAPPED_PATH, PAGE_LIMIT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("UPDATE DISTRIBUTOR PHARMACY ENTITY WITH ALREADY MAPPED LOCATION")
    @Test (groups = { "regression" }, priority = 19)
    public void verifyAlreadyMappedMerchantLocation() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorPharmacyEntityMapping(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID_1, EXISTING_BRANCH_ID, EXISTING_MERCHANT_LOCATION_ID, DISTRIBUTOR_ALREADY_MAPPED_REFERENCE_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CONFLICT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("UPDATE MAPPING WITH INVALID LOCATION ID")
    @Test (groups = { "regression" }, priority = 20)
    public void verifyMappingWithInvalidLocationId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorPharmacyEntityMapping(headers, EXISTING_DISTRIBUTOR_REFERENCE_ID_1, EXISTING_BRANCH_ID, INVALID_ID, DISTRIBUTOR_MAPPING_REFERENCE_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("DOWNLOAD CSV FOR MERCHANT LOCATION MAPPING")
    @Test (groups = { "regression" }, priority = 21)
    public void verifyDownloadCSV() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.requestCSV(headersOmega, EXISTING_DISTRIBUTOR_REFERENCE_ID_1, EXISTING_BRANCH_ID, REQUEST_CSV_TYPE_MERCHANT_MAPPING);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, TYPE_PATH, REQUEST_CSV_TYPE_MERCHANT_MAPPING));
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