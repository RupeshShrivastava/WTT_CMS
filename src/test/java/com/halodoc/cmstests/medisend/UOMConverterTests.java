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
public class UOMConverterTests extends BaseCMSTest {
    private MedisendAPI medisendAPI = new MedisendAPI();
    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE B2B PRODUCT")
    @Test (groups = { "regression" }, priority = 1)
    public void verifyCreateB2BProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, productName));
        Assert.assertTrue(validateResponseValue(response, SOURCE_PATH, SOURCE_PRODUCT_B2B));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE B2C PRODUCT")
    @Test (groups = { "regression" }, priority = 2)
    public void verifyCreateB2CProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2C);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, productName));
        Assert.assertTrue(validateResponseValue(response, SOURCE_PATH, SOURCE_PRODUCT_B2C));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE B2B PRODUCT")
    @Test (groups = { "regression" }, priority = 3)
    public void verifyUpdateB2BProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        int productId = response.path(ID_PATH);

        productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        response = medisendAPI.updateMasterProduct(headers, Integer.toString(productId), productName);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, productName));
        Assert.assertTrue(validateResponseValue(response, SOURCE_PATH, SOURCE_PRODUCT_B2B));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE B2C PRODUCT")
    @Test (groups = { "regression" }, priority = 4)
    public void verifyUpdateB2CProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2C);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        int productId = response.path(ID_PATH);

        productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        response = medisendAPI.updateMasterProduct(headers, Integer.toString(productId), productName);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, productName));
        Assert.assertTrue(validateResponseValue(response, SOURCE_PATH, SOURCE_PRODUCT_B2C));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH B2B PRODUCT PAGINATED")
    @Test (groups = { "sanity", "regression" }, priority = 5)
    public void verifySearchB2BProductPaginated() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchMasterProductPaginated(headers, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SOURCE_PATH, SOURCE_PRODUCT_B2B, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH B2C PRODUCT PAGINATED")
    @Test (groups = { "regression" }, priority = 6)
    public void verifySearchB2CProductPaginated() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchMasterProductPaginated(headers, SOURCE_PRODUCT_B2C);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SOURCE_PATH, SOURCE_PRODUCT_B2C, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("SEARCH PRODUCT PAGINATED WITHOUT SOURCE")
    @Test (groups = { "regression" }, priority = 7)
    public void verifySearchProductPaginatedWithoutSource() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchMasterProductPaginated(headers, EMPTY_STRING);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, SOURCE_PATH, SOURCE_PRODUCT_B2C, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH B2B PRODUCT")
    @Test (groups = { "regression" }, priority = 8)
    public void verifySearchB2BProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchMasterProduct(headers, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, PRODUCTS_PATH, SOURCE_PATH, SOURCE_PRODUCT_B2B, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH B2C PRODUCT")
    @Test (groups = { "regression" }, priority = 9)
    public void verifySearchB2CProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchMasterProduct(headers, SOURCE_PRODUCT_B2C);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, PRODUCTS_PATH, SOURCE_PATH, SOURCE_PRODUCT_B2C, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("SEARCH PRODUCT WITHOUT SOURCE")
    @Test (groups = { "regression" }, priority = 10)
    public void verifySearchProductWithoutSource() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchMasterProduct(headers, EMPTY_STRING);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, PRODUCTS_PATH, SOURCE_PATH, SOURCE_PRODUCT_B2C, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE UOM MAPPING")
    @Test (groups = { "regression" }, priority = 11)
    public void verifyCreateUOMMapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String productExternalId = response.path(EXTERNAL_ID_PATH);
        int productInternalId = response.path(ID_PATH);

        response = medisendAPI.createProductAttributes(headers, Integer.toString(productInternalId), UOM_BOX);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));

        response = medisendAPI.createUOMMapping(headers, productExternalId, PRODUCT_ID, UOM_VALUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, SELLING_PRODUCT_ID_PATH, PRODUCT_ID));
        Assert.assertTrue(validateResponseValue(response, SELLING_UOM_UNIT_PATH, UOM_TABLET));
        Assert.assertTrue(validateResponseValue(response, SELLING_UOM_VALUE_PATH, UOM_VALUE));
        Assert.assertTrue(validateResponseValue(response, BUYING_PRODUCT_ID_PATH, productExternalId));
        Assert.assertTrue(validateResponseValue(response, BUYING_UOM_UNIT_PATH, UOM_BOX));
        Assert.assertTrue(validateResponseValue(response, BUYING_UOM_VALUE_PATH, UOM_VALUE_DEFAULT));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE UOM MAPPING WITH MAPPED B2B PRODUCT")
    @Test (groups = { "regression" }, priority = 12)
    public void verifyCreateUOMMappingWithMappedB2BProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2C);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String productExternalId = response.path(EXTERNAL_ID_PATH);
        int productInternalId = response.path(ID_PATH);

        response = medisendAPI.createProductAttributes(headers, Integer.toString(productInternalId), UOM_TABLET);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));

        response = medisendAPI.createUOMMapping(headers, B2B_MAPPED_PRODUCT_EXTERNAL_ID, productExternalId, UOM_VALUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, SELLING_PRODUCT_ID_PATH, productExternalId));
        Assert.assertTrue(validateResponseValue(response, SELLING_UOM_UNIT_PATH, UOM_TABLET));
        Assert.assertTrue(validateResponseValue(response, SELLING_UOM_VALUE_PATH, UOM_VALUE));
        Assert.assertTrue(validateResponseValue(response, BUYING_PRODUCT_ID_PATH, B2B_MAPPED_PRODUCT_EXTERNAL_ID));
        Assert.assertTrue(validateResponseValue(response, BUYING_UOM_UNIT_PATH, UOM_BOX));
        Assert.assertTrue(validateResponseValue(response, BUYING_UOM_VALUE_PATH, UOM_VALUE_DEFAULT));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE DUPLICATE UOM MAPPING")
    @Test (groups = { "regression" }, priority = 13)
    public void verifyCreateDuplicateUOMMapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createUOMMapping(headers, B2B_MAPPED_PRODUCT_EXTERNAL_ID, PRODUCT_ID, UOM_VALUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE UOM MAPPING WITH B2C PRODUCT ID")
    @Test (groups = { "regression" }, priority = 14)
    public void verifyCreateUOMMappingWithB2CProductId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.createUOMMapping(headers, PRODUCT_ID, B2B_MAPPED_PRODUCT_EXTERNAL_ID, UOM_VALUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE UOM MAPPING WITH B2B PRODUCT ID AS SELLING")
    @Test (groups = { "regression" }, priority = 15)
    public void verifyCreateUOMMappingWithB2BProductIdAsSelling() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String productExternalId = response.path(EXTERNAL_ID_PATH);
        int productInternalId = response.path(ID_PATH);

        response = medisendAPI.createProductAttributes(headers, Integer.toString(productInternalId), UOM_BOX);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));

        response = medisendAPI.createUOMMapping(headers, productExternalId, B2B_MAPPED_PRODUCT_EXTERNAL_ID, UOM_VALUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE UOM MAPPING WITH DECIMAL VALUE")
    @Test (groups = { "regression" }, priority = 16)
    public void verifyCreateUOMMappingWithDecimalValue() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String productExternalId = response.path(EXTERNAL_ID_PATH);
        int productInternalId = response.path(ID_PATH);

        response = medisendAPI.createProductAttributes(headers, Integer.toString(productInternalId), UOM_BOX);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));

        response = medisendAPI.createUOMMapping(headers, productExternalId, PRODUCT_ID, UOM_VALUE_DECIMAL);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, SELLING_PRODUCT_ID_PATH, PRODUCT_ID));
        Assert.assertTrue(validateResponseValue(response, SELLING_UOM_UNIT_PATH, UOM_TABLET));
        Assert.assertTrue(validateResponseValue(response, SELLING_UOM_VALUE_PATH, UOM_VALUE_DECIMAL));
        Assert.assertTrue(validateResponseValue(response, BUYING_PRODUCT_ID_PATH, productExternalId));
        Assert.assertTrue(validateResponseValue(response, BUYING_UOM_UNIT_PATH, UOM_BOX));
        Assert.assertTrue(validateResponseValue(response, BUYING_UOM_VALUE_PATH, UOM_VALUE_DEFAULT));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("CREATE UOM MAPPING WITH INVALID VALUE")
    @Test (groups = { "regression" }, priority = 17)
    public void verifyCreateUOMMappingWithInvalidValue() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String productExternalId = response.path(EXTERNAL_ID_PATH);
        int productInternalId = response.path(ID_PATH);

        response = medisendAPI.createProductAttributes(headers, Integer.toString(productInternalId), UOM_BOX);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));

        response = medisendAPI.createUOMMapping(headers, productExternalId, PRODUCT_ID, productExternalId);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("GET UOM MAPPING")
    @Test (groups = { "regression" }, priority = 18)
    public void verifyGetUOMMapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getUOMMapping(headers, B2B_MAPPED_PRODUCT_WITH_INACTIVE_EXTERNAL_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("GET UOM MAPPING WITH B2C PRODUCT ID")
    @Test (groups = { "regression" }, priority = 19)
    public void verifyGetUOMMappingWithB2CProductId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getUOMMapping(headers, PRODUCT_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateArraySize(response, EMPTY_STRING, 0));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE UOM MAPPING")
    @Test (groups = { "regression" }, priority = 20)
    public void verifyUpdateUOMMapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateUOMMapping(headers, B2B_MAPPED_PRODUCT_EXTERNAL_ID, UOM_MAPPING_ID, UOM_BOX, UOM_VALUE_DECIMAL);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.getUOMMappingById(headers, B2B_MAPPED_PRODUCT_EXTERNAL_ID, UOM_MAPPING_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, EXTERNAL_ID_PATH, UOM_MAPPING_ID));
        Assert.assertTrue(validateResponseValue(response, SELLING_UOM_UNIT_PATH, UOM_TABLET));
        Assert.assertTrue(validateResponseValue(response, SELLING_UOM_VALUE_PATH, UOM_VALUE_DECIMAL));
        Assert.assertTrue(validateResponseValue(response, BUYING_PRODUCT_ID_PATH, B2B_MAPPED_PRODUCT_EXTERNAL_ID));
        Assert.assertTrue(validateResponseValue(response, BUYING_UOM_UNIT_PATH, UOM_BOX));
        Assert.assertTrue(validateResponseValue(response, BUYING_UOM_VALUE_PATH, UOM_VALUE_DEFAULT_LONG));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));

        response = medisendAPI.updateUOMMapping(headers, B2B_MAPPED_PRODUCT_EXTERNAL_ID, UOM_MAPPING_ID, UOM_TABLET, UOM_VALUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("UPDATE UOM MAPPING WITH INVALID VALUE")
    @Test (groups = { "regression" }, priority = 21)
    public void verifyUpdateUOMMappingWithInvalidValue() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateUOMMapping(headers, B2B_MAPPED_PRODUCT_EXTERNAL_ID, UOM_MAPPING_ID, UOM_BOX, B2B_MAPPED_PRODUCT_EXTERNAL_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("UPDATE UOM MAPPING WITH B2C PRODUCT ID")
    @Test (groups = { "regression" }, priority = 22)
    public void verifyUpdateUOMMappingWithB2CProductId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateUOMMapping(headers, PRODUCT_ID, UOM_MAPPING_ID, UOM_BOX, UOM_VALUE_DECIMAL);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("UPDATE UOM MAPPING WITH INVALID MAPPING ID")
    @Test (groups = { "regression" }, priority = 23)
    public void verifyUpdateUOMMappingWithInvalidMappingId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateUOMMapping(headers, B2B_MAPPED_PRODUCT_WITH_INACTIVE_EXTERNAL_ID, UOM_MAPPING_ID_INVALID, UOM_BOX,
                UOM_VALUE_DECIMAL);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("DELETE UOM MAPPING")
    @Test (groups = { "regression" }, priority = 24)
    public void verifyDeleteUOMMapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String productName = CREATE_MASTER_PRODUCT_NAME + getEpochTime();
        Response response = medisendAPI.createMasterProduct(headers, productName, SOURCE_PRODUCT_B2B);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String productExternalId = response.path(EXTERNAL_ID_PATH);
        int productInternalId = response.path(ID_PATH);

        response = medisendAPI.createProductAttributes(headers, Integer.toString(productInternalId), UOM_BOX);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));

        response = medisendAPI.createUOMMapping(headers, productExternalId, PRODUCT_ID, UOM_VALUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String mappingId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.deleteUOMMapping(headers, productExternalId, mappingId);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.getUOMMapping(headers, productExternalId);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateArraySize(response, EMPTY_STRING, 0));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("DELETE UOM MAPPING WITH B2C PRODUCT ID")
    @Test (groups = { "regression" }, priority = 25)
    public void verifyDeleteUOMMappingWithB2CProductId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.deleteUOMMapping(headers, PRODUCT_ID, UOM_MAPPING_ID_B2C);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity (SeverityLevel.NORMAL)
    @Description ("DELETE UOM MAPPING WITH INVALID MAPPING ID")
    @Test (groups = { "regression" }, priority = 26)
    public void verifyDeleteUOMMappingWithInvalidMappingId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.deleteUOMMapping(headers, B2B_MAPPED_PRODUCT_WITH_INACTIVE_EXTERNAL_ID, UOM_MAPPING_ID_INVALID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}