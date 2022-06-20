package com.halodoc.cmstests.medisend;
import static com.halodoc.cmstests.Constants.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.apiDefinitions.MedisendAPI;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class  MedisendTests extends BaseCMSTest {
    private MedisendAPI medisendAPI = new MedisendAPI();
    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);
    String search_text;
    String invalid_search_text;
    String page_no;
    String per_page;
    String status;
    final String testDataResourcePath="distributorInfo.properties";
    Properties prop=new Properties();

    @BeforeClass
    public void setPrerequisite() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream resourceStream = loader.getResourceAsStream(testDataResourcePath)) {
            prop.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        search_text=prop.getProperty("search_text");
        page_no=prop.getProperty("page_no");
        per_page=prop.getProperty("per_page");
        status=prop.getProperty("status");
        invalid_search_text=prop.getProperty("invalid_search_text");
    }

    @Severity (SeverityLevel.BLOCKER)
    @Description ("CREATE DISTRIBUTOR")
    @Test (groups = { "regression" }, priority = 1)
    public void verifyCreateDistributor() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String distributorName = CREATE_DISTRIBUTOR_NAME + getEpochTime();
        Response response = medisendAPI.createDistributors(headers, distributorName, STATUS_ACTIVE, CREATE_DISTRIBUTOR_NAME,
                DISTRIBUTOR_PHONE_NUMBER);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, distributorName));
        Assert.assertTrue(validateResponseValue(response, DESCRIPTION_PATH, CREATE_DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, DISTRIBUTOR_PHONE_NUMBER_PATH, DISTRIBUTOR_PHONE_NUMBER));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET DISTRIBUTOR")
    @Test (groups = { "sanity", "regression" }, priority = 2)
    public void verifyGetDistributor() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributors(headers, DISTRIBUTOR_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, DESCRIPTION_PATH, CREATE_DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, DISTRIBUTOR_PHONE_NUMBER_PATH, DISTRIBUTOR_PHONE_NUMBER));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("GET DISTRIBUTOR WITH INVALID DISTRIBUTOR ID")
    @Test (groups = { "regression" }, priority = 3)
    public void verifyGetDistributorWithInvalidDistributorId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributors(headers, INVALID_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE DISTRIBUTOR WITH STATUS INACTIVE")
    @Test (groups = { "regression" }, priority = 4)
    public void verifyUpdateDistributorWithStatusInactive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributors(headers, DISTRIBUTOR_INTERNAL_ID, DISTRIBUTOR_NAME, STATUS_INACTIVE, CREATE_DISTRIBUTOR_NAME,
                DISTRIBUTOR_PHONE_NUMBER);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.getDistributors(headers, DISTRIBUTOR_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_INACTIVE));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, DESCRIPTION_PATH, CREATE_DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, DISTRIBUTOR_PHONE_NUMBER_PATH, DISTRIBUTOR_PHONE_NUMBER));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE DISTRIBUTOR WITH STATUS ACTIVE")
    @Test (groups = { "regression" }, priority = 5)
    public void verifyUpdateDistributorWithStatusActive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributors(headers, DISTRIBUTOR_INTERNAL_ID, DISTRIBUTOR_NAME, STATUS_ACTIVE, CREATE_DISTRIBUTOR_NAME,
                DISTRIBUTOR_PHONE_NUMBER);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.getDistributors(headers, DISTRIBUTOR_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, DESCRIPTION_PATH, CREATE_DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, DISTRIBUTOR_PHONE_NUMBER_PATH, DISTRIBUTOR_PHONE_NUMBER));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("SEARCH DISTRIBUTOR BY NAME, STATUS, PAGE_LIMIT")
    @Test (groups = { "regression" }, priority = 6)
    public void verifySearchDistributorByNameStatusPageLimit() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchDistributors(headers, CREATE_DISTRIBUTOR_NAME, STATUS_ACTIVE, PAGE_LIMIT);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateArraySize(response, RESULT_PATH, Integer.valueOf(PAGE_LIMIT)));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, CREATE_DISTRIBUTOR_NAME, MODE_CONTAINS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET DISTRIBUTORS PRODUCTS")
    @Test (groups = { "regression" }, priority = 7)
    public void verifyGetDistributorsProducts() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorsProducts(headers, DISTRIBUTOR_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("CREATE DISTRIBUTORS BRANCH")
    @Test (groups = { "regression" }, priority = 8)
    public void verifyCreateDistributorsBranch() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String branchName = CREATE_BRANCH_NAME + getEpochTime();
        Response response = medisendAPI.createDistributorsBranch(headers, DISTRIBUTOR_ID, branchName, STATUS_ACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("SEARCH DISTRIBUTORS BRANCH")
    @Test (groups = { "regression" }, priority = 9)
    public void verifySearchDistributorsBranch() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchDistributorsBranch(headers, DISTRIBUTOR_ID, CREATE_BRANCH_NAME, STATUS_ACTIVE, PAGE_LIMIT);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateArraySize(response, RESULT_PATH, Integer.valueOf(PAGE_LIMIT)));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, CREATE_BRANCH_NAME, MODE_CONTAINS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("SEARCH BRANCH")
    @Test (groups = { "regression" }, priority = 10)
    public void verifySearchBranches() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchBranches(headers, CREATE_BRANCH_NAME, PAGE_LIMIT);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateArraySize(response, RESULT_PATH, Integer.valueOf(PAGE_LIMIT)));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, CREATE_BRANCH_NAME, MODE_CONTAINS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET DISTRIBUTORS BRANCH BY ID")
    @Test (groups = { "regression" }, priority = 11)
    public void verifyGetDistributorsBranchById() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorsBranchById(headers, DISTRIBUTOR_ID, BRANCH_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, BRANCH_NAME));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE DISTRIBUTOR BRANCH WITH STATUS INACTIVE")
    @Test (groups = { "regression" }, priority = 12)
    public void verifyUpdateDistributorsBranchWithStatusInactive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorsBranch(headers, DISTRIBUTOR_ID, BRANCH_ID, BRANCH_NAME, STATUS_INACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE DISTRIBUTOR BRANCH WITH STATUS ACTIVE")
    @Test (groups = { "regression" }, priority = 13)
    public void verifyUpdateDistributorsBranchWithStatusActive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorsBranch(headers, DISTRIBUTOR_ID, BRANCH_ID, BRANCH_NAME, STATUS_ACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("CREATE DISTRIBUTOR WAREHOUSE")
    @Test (groups = { "regression" }, priority = 14)
    public void verifyCreateDistributorsWarehouse() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String warehouseName = CREATE_WAREHOUSE_NAME + getEpochTime();
        Response response = medisendAPI.createDistributorsWarehouse(headers, DISTRIBUTOR_ID, BRANCH_ID, warehouseName, STATUS_ACTIVE, ADDRESS, LANDMARK,
                LATITUDE, LONGITUDE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, ZONE_ID_PATH, ZONE_ID));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET DISTRIBUTOR WAREHOUSE")
    @Test (groups = { "regression" }, priority = 15)
    public void verifyGetDistributorsWarehouse() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorsWarehouse(headers, DISTRIBUTOR_ID, BRANCH_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET DISTRIBUTOR WAREHOUSE BY ID")
    @Test (groups = { "regression" }, priority = 16)
    public void verifyGetDistributorsWarehouseById() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorsWarehouseById(headers, DISTRIBUTOR_ID, BRANCH_ID, WAREHOUSE_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, WAREHOUSE_NAME));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, ADDRESS_PATH, ADDRESS));
        Assert.assertTrue(validateResponseValue(response, LANDMARK_PATH, LANDMARK));
        Assert.assertTrue(validateResponseValue(response, LATITUDE_PATH, LATITUDE));
        Assert.assertTrue(validateResponseValue(response, LONGITUDE_PATH, LONGITUDE));
        Assert.assertTrue(validateResponseValue(response, ZONE_ID_PATH, ZONE_ID));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE DISTRIBUTOR WAREHOUSE WITH STATUS INACTIVE")
    @Test (groups = { "regression" }, priority = 17)
    public void verifyUpdateDistributorsWarehouseWithStatusInactive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorsWarehouse(headers, DISTRIBUTOR_ID, BRANCH_ID, WAREHOUSE_ID, WAREHOUSE_NAME, STATUS_INACTIVE,
                ADDRESS, LANDMARK, LATITUDE, LONGITUDE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.getDistributorsWarehouseById(headers, DISTRIBUTOR_ID, BRANCH_ID, WAREHOUSE_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, WAREHOUSE_NAME));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_INACTIVE));
        Assert.assertTrue(validateResponseValue(response, ADDRESS_PATH, ADDRESS));
        Assert.assertTrue(validateResponseValue(response, LANDMARK_PATH, LANDMARK));
        Assert.assertTrue(validateResponseValue(response, LATITUDE_PATH, LATITUDE));
        Assert.assertTrue(validateResponseValue(response, LONGITUDE_PATH, LONGITUDE));
        Assert.assertTrue(validateResponseValue(response, ZONE_ID_PATH, ZONE_ID));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE DISTRIBUTOR WAREHOUSE WITH STATUS ACTIVE")
    @Test (groups = { "regression" }, priority = 18)
    public void verifyUpdateDistributorsWarehouseWithStatusActive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateDistributorsWarehouse(headers, DISTRIBUTOR_ID, BRANCH_ID, WAREHOUSE_ID, WAREHOUSE_NAME, STATUS_ACTIVE,
                ADDRESS, LANDMARK, LATITUDE, LONGITUDE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.getDistributorsWarehouseById(headers, DISTRIBUTOR_ID, BRANCH_ID, WAREHOUSE_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, WAREHOUSE_NAME));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, ADDRESS_PATH, ADDRESS));
        Assert.assertTrue(validateResponseValue(response, LANDMARK_PATH, LANDMARK));
        Assert.assertTrue(validateResponseValue(response, LATITUDE_PATH, LATITUDE));
        Assert.assertTrue(validateResponseValue(response, LONGITUDE_PATH, LONGITUDE));
        Assert.assertTrue(validateResponseValue(response, ZONE_ID_PATH, ZONE_ID));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("CREATE PRODUCT")
    @Test (groups = { "regression" }, priority = 19)
    public void verifyCreateProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String branchName = CREATE_BRANCH_NAME + getEpochTime();
        Response response = medisendAPI.createDistributorsBranch(headers, DISTRIBUTOR_ID, branchName, STATUS_ACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String branchId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createProduct(headers, DISTRIBUTOR_ID, branchId, B2B_MAPPED_PRODUCT_EXTERNAL_ID, STATUS_ACTIVE, COST_PRICE, SKU_ID,
                AVAILABLE_QUANTITY, FALSE, null);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ID_PATH, B2B_MAPPED_PRODUCT_EXTERNAL_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, B2B_MAPPED_PRODUCT_NAME));
        Assert.assertTrue(validateResponseValue(response, COST_PRICE_PATH, COST_PRICE));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, SKU_ID));
        Assert.assertTrue(validateResponseValue(response, AVAILABLE_QUANTITY_PATH, Integer.valueOf(AVAILABLE_QUANTITY)));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE PRODUCT WITH STATUS INACTIVE")
    @Test (groups = { "regression" }, priority = 20)
    public void verifyUpdateProductWithStatusInactive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateProduct(headers, DISTRIBUTOR_ID, PRODUCT_BRANCH_ID, PRODUCT_MAPPING_ID, PRODUCT_ID, STATUS_INACTIVE,
                COST_PRICE, SKU_ID, AVAILABLE_QUANTITY, FALSE, null);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE PRODUCT WITH STATUS ACTIVE")
    @Test (groups = { "regression" }, priority = 21)
    public void verifyUpdateProductWithStatusActive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateProduct(headers, DISTRIBUTOR_ID, PRODUCT_BRANCH_ID, PRODUCT_MAPPING_ID, PRODUCT_ID, STATUS_ACTIVE,
                COST_PRICE, SKU_ID, AVAILABLE_QUANTITY, FALSE, null);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET PRODUCT")
    @Test (groups = { "regression" }, priority = 22)
    public void verifyGetProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getProduct(headers, DISTRIBUTOR_ID, PRODUCT_BRANCH_ID, PRODUCT_MAPPING_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, EXTERNAL_ID_PATH, PRODUCT_MAPPING_ID));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ID_PATH, PRODUCT_ID));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, PRODUCT_NAME));
        Assert.assertTrue(validateResponseValue(response, COST_PRICE_PATH, Float.valueOf(COST_PRICE)));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));
        Assert.assertTrue(validateResponseValue(response, SKU_ID_PATH, SKU_ID));
        Assert.assertTrue(validateResponseValue(response, AVAILABLE_QUANTITY_PATH, Integer.valueOf(AVAILABLE_QUANTITY)));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("SEARCH PRODUCT")
    @Test (groups = { "regression" }, priority = 23)
    public void verifySearchProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchProduct(headers, SEARCH_PRODUCT_DISTRIBUTOR_ID, SEARCH_PRODUCT_BRANCH_ID, PRODUCT_NAME, STATUS_ACTIVE,
                TRUE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateArraySize(response, RESULT_PATH, 1));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET BRANCHES BY PRODUCT ID")
    @Test (groups = { "regression" }, priority = 24)
    public void verifyGetBranchesByProductId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getBranchesByProductId(headers, PRODUCT_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        log.info(response.body().prettyPrint());

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET DISTRIBUTORS BY PRODUCT ID")
    @Test (groups = { "regression" }, priority = 25)
    public void verifyGetDistributorsByProductId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorsByProductId(headers, PRODUCT_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        log.info(response.body().prettyPrint());

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET BRANCH PRODUCT LIST")
    @Test (groups = { "regression" }, priority = 26)
    public void verifyGetBranchProductList() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getBranchProductList(headers, DISTRIBUTOR_ID, PRODUCT_BRANCH_ID, PRODUCT_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, EXTERNAL_ID_PATH, PRODUCT_MAPPING_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, PRODUCT_ID_PATH, PRODUCT_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, NAME_PATH, PRODUCT_NAME, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, COST_PRICE_PATH, Float.valueOf(COST_PRICE), MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, SKU_ID_PATH, SKU_ID, MODE_EQUALS));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, AVAILABLE_QUANTITY_PATH, Integer.valueOf(AVAILABLE_QUANTITY), MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("CREATE MERCHANT MAPPING")
    @Test (groups = { "regression" }, priority = 27)
    public void verifyCreateMerchantMapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String branchName = CREATE_BRANCH_NAME + getEpochTime();
        Response response = medisendAPI.createDistributorsBranch(headers, DISTRIBUTOR_ID, branchName, STATUS_ACTIVE, MIN_BASKET_SIZE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        String branchId = response.path(EXTERNAL_ID_PATH);

        response = medisendAPI.createMerchantMapping(headers, DISTRIBUTOR_ID, branchId, MERCHANT_LOCATION_ID, STATUS_ACTIVE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_CREATED));
        Assert.assertTrue(validateResponseValue(response, MERCHANT_LOCATION_ID_PATH, MERCHANT_LOCATION_ID));
        Assert.assertTrue(validateResponseValue(response, STATUS_PATH, STATUS_ACTIVE));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE MERCHANT MAPPING WITH STATUS INACTIVE")
    @Test (groups = { "regression" }, priority = 28)
    public void verifyUpdateMerchantMappingWithStatusInactive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateMerchantMapping(headers, DISTRIBUTOR_ID, BRANCH_ID_FOR_MERCHANT_MAPPING, MERCHANT_MAPPING_ID,
                MERCHANT_LOCATION_ID, STATUS_INACTIVE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE MERCHANT MAPPING WITH STATUS ACTIVE")
    @Test (groups = { "regression" }, priority = 29)
    public void verifyUpdateMerchantMappingWithStatusActive() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateMerchantMapping(headers, DISTRIBUTOR_ID, BRANCH_ID_FOR_MERCHANT_MAPPING, MERCHANT_MAPPING_ID,
                MERCHANT_LOCATION_ID, STATUS_ACTIVE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET BRANCHES MERCHANT MAPPING")
    @Test (groups = { "regression" }, priority = 30)
    public void verifyGetBranchesMerchantMapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getBranchesMerchantMapping(headers, MERCHANT_ID, MERCHANT_LOCATION_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET DISTRIBUTORS MERCHANT MAPPING")
    @Test (groups = { "regression" }, priority = 31)
    public void verifyGetDistributorsMerchantMapping() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorsMerchantMapping(headers, MERCHANT_ID, MERCHANT_LOCATION_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValueArray(response, EMPTY_STRING, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("BOOK INVENTORY")
    @Test (groups = { "regression" }, priority = 32)
    public void verifyBookInventory() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateProduct(headers, DISTRIBUTOR_ID, PRODUCT_BRANCH_ID, PRODUCT_MAPPING_ID, PRODUCT_ID, STATUS_ACTIVE,
                COST_PRICE, SKU_ID, AVAILABLE_QUANTITY, FALSE, null);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.bookInventory(headers, DISTRIBUTOR_ID, PRODUCT_BRANCH_ID, TYPE_BOOK, PRODUCT_ID, ORDER_QUANTITY);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ID, ORDER_QUANTITY));

        int finalQuantity = Integer.valueOf(AVAILABLE_QUANTITY) - Integer.valueOf(ORDER_QUANTITY);
        response = medisendAPI.getProduct(headers, DISTRIBUTOR_ID, PRODUCT_BRANCH_ID, PRODUCT_MAPPING_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, AVAILABLE_QUANTITY_PATH, finalQuantity));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("CANCEL INVENTORY")
    @Test (groups = { "regression" }, priority = 33)
    public void verifyCancelInventory() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.updateProduct(headers, DISTRIBUTOR_ID, PRODUCT_BRANCH_ID, PRODUCT_MAPPING_ID, PRODUCT_ID, STATUS_ACTIVE,
                COST_PRICE, SKU_ID, AVAILABLE_QUANTITY, FALSE, null);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));

        response = medisendAPI.bookInventory(headers, DISTRIBUTOR_ID, PRODUCT_BRANCH_ID, TYPE_CANCEL, PRODUCT_ID, ORDER_QUANTITY);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, PRODUCT_ID, ORDER_QUANTITY));

        int finalQuantity = Integer.valueOf(AVAILABLE_QUANTITY) + Integer.valueOf(ORDER_QUANTITY);
        response = medisendAPI.getProduct(headers, DISTRIBUTOR_ID, PRODUCT_BRANCH_ID, PRODUCT_MAPPING_ID);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, AVAILABLE_QUANTITY_PATH, finalQuantity));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("SEARCH PHARMACY")
    @Test (groups = { "regression" }, priority = 34)
    public void verifySearchPharmacy() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchPharmacy(headers, CREATE_BRANCH_NAME, STATUS_ACTIVE, PAGE_LIMIT);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateArraySize(response, RESULT_PATH, Integer.valueOf(PAGE_LIMIT)));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, NAME_PATH, CREATE_BRANCH_NAME, MODE_CONTAINS));
        Assert.assertTrue(validateResponseValueArray(response, RESULT_PATH, STATUS_PATH, STATUS_ACTIVE, MODE_EQUALS));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("SEARCH MERCHANT LOCATION AND ACTIVE STATUS")
    @Test(groups = {"sanity", "regression"}, priority = 35)
    public void verifyMerchantLocationSearchAndActiveStatus(){
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchMerchantLocation(headers, MERCHANT_LOCATION_SEARCH_TEXT, STATUS_ACTIVE, MIN_BASKET_SIZE, STATUS_MAI_ACTIVE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(medisendAPI.validateSearchMerchantLocation(response, MERCHANT_LOCATION_SEARCH_TEXT, ACTIVE));
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("SEARCH MERCHANT LOCATION AND STATUS INACTIVE")
    @Test(groups = {"sanity", "regression"}, priority = 36)
    public void verifyMerchantLocationSearchAndInactiveStatus() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchMerchantLocation(headers, MERCHANT_LOCATION_SEARCH_TEXT, STATUS_INACTIVE, MIN_BASKET_SIZE, STATUS_MAI_ACTIVE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(medisendAPI.validateSearchMerchantLocation(response, MERCHANT_LOCATION_SEARCH_TEXT, INACTIVE));
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("MERCHANT LOCATION SEARCH WTIH INVALID PAGE_NO")
    @Test(groups = {"regression"}, priority = 37)
    public void verifyMerchantLocationSearchInvalidPageNo() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchMerchantLocation(headers, MERCHANT_LOCATION_SEARCH_TEXT, STATUS_ACTIVE, MIN_BASKET_SIZE, DISCOUNT_MIN_QUANTITY_ZERO);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("MERCHANT LOCATION SEARCH WITH INVALID PER_PAGE")
    @Test(groups = {"regression"}, priority = 38)
    public void verifyMerchantLocationSearchInvalidPerPage() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.searchMerchantLocation(headers, MERCHANT_LOCATION_SEARCH_TEXT, STATUS_ACTIVE, NEGETIVE_DISCOUNT_VALUE, STATUS_MAI_ACTIVE);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("GET DISTRIBUTOR SLUG")
    @Test(groups = {"sanity","regression"}, priority = 39)
    public void verifyGetDistributorSlug() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorSlug(headers, EXPECTED_SLUG_NAME);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, NAME_PATH, EXPECTED_DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseValue(response, SLUG_VALUE_PATH, EXPECTED_SLUG_NAME));
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("UPDATE DISTRIBUTOR SLUG BY DISTRIBUTOR NAME")
    @Test(groups = {"regression"}, priority = 40)
    public void verifyUpdateDistributorSlugByDistributorName() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorSlug(headers, EXPECTED_UPDATE_SLUG_NAME);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseContains(response, NAME_PATH, EXPECTED_UPDATE_DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseContains(response, SLUG_VALUE_PATH, EXPECTED_UPDATE_SLUG_NAME));

        String updateDistributorName = EXPECTED_UPDATE_DISTRIBUTOR_NAME + getEpochTime();
        response = medisendAPI.updateDistributors(headers, DISTRIBUTOR_INTERNAL_ID_SLUG, updateDistributorName, STATUS_ACTIVE, EMPTY_STRING, EMPTY_STRING);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NO_CONTENT));
        response = medisendAPI.getDistributorSlug(headers, EXPECTED_UPDATE_SLUG_NAME);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseContains(response, NAME_PATH, EXPECTED_UPDATE_DISTRIBUTOR_NAME));
        Assert.assertTrue(validateResponseContains(response, SLUG_VALUE_PATH, EXPECTED_UPDATE_SLUG_NAME));
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("GET INVALID DISTRIBUTOR SLUG")
    @Test(groups = {"regression"}, priority = 41)
    public void verifyGetInvalidDistributorSlug() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = medisendAPI.getDistributorSlug(headers, DISCOUNT_MIN_QUANTITY);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

    }
    @Test(description = "Search distributor branch name")
    public void searchForDistributorBranchName(){
        Response response=MedisendAPI.searchDistributorBranchByName(search_text,per_page,page_no,status);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK,response.prettyPrint());
        JsonPath obj=response.jsonPath();
        int count=obj.get("total_count");

        Assert.assertNotNull(count);

    }
    @Test(description = "Search invalid distributor branch name")
    public void searchForInvalidDistributorBranchName(){
        Response response=MedisendAPI.searchDistributorBranchByName(invalid_search_text,per_page,page_no,status);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK,response.prettyPrint());

    }

    @Test(description = "Search for distributor name")
    public void searchForDistributorName(){
        Response response=MedisendAPI.searchDistributorByName(search_text,per_page,page_no,status);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK,response.prettyPrint());
        JsonPath obj=response.jsonPath();
        int count=obj.get("total_count");
        Assert.assertNotNull(count);
    }

    @Test(description = "Search invalid distributor branch")
    public void searchForInvalidDistributorName(){
        Response response=MedisendAPI.searchDistributorByName(invalid_search_text,per_page,page_no,status);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK,response.prettyPrint());
    }
}