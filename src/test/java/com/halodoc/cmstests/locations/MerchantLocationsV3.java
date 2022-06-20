package com.halodoc.cmstests.locations;

import static com.halodoc.cmstests.Constants.*;
import java.util.HashMap;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.apiDefinitions.MerchantLocationsV3Api;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MerchantLocationsV3 extends BaseCMSTest {
    private MerchantLocationsV3Api merchantLocationsV3Api = new MerchantLocationsV3Api();
    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);

    @Test (groups = { "sanity", "regression" }, priority = 1)
    public void verifyGetMerchantLocationsWithNationwideTrueInsideGeozonePharmacyOpen() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(headers, LATITUDE_INSIDE_GEOZONE, LONGITUDE_INSIDE_GEOZONE,
                STORE_NATIONWIDE_PHARMACY_OPEN_ID, true, true);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, MERCHANT_LOCATIONS_PATH, MERCHANT_LOCATIONS_OPEN_ID));
        Assert.assertTrue(validateArraySize(response, EMPTY_STRING, NUM_OF_PHARMACY));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "regression" }, priority = 2)
    public void verifyGetMerchantLocationsWithNationwideTrueOutsideGeozonePharmacyOpen() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(headers, LATITUDE_OUTSIDE_GEOZONE, LONGITUDE_OUTSIDE_GEOZONE,
                STORE_NATIONWIDE_PHARMACY_OPEN_ID, true, true);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, MERCHANT_LOCATIONS_PATH, MERCHANT_LOCATIONS_OPEN_ID));
        Assert.assertTrue(validateArraySize(response, EMPTY_STRING, NUM_OF_PHARMACY));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "regression" }, priority = 3)
    public void verifyGetMerchantLocationsWithNationwideTrueInsideGeozonePharmacyClose() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(headers, LATITUDE_INSIDE_GEOZONE, LONGITUDE_INSIDE_GEOZONE,
                STORE_NATIONWIDE_PHARMACY_CLOSE_ID, true, true);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, MERCHANT_LOCATIONS_PATH, MERCHANT_LOCATIONS_CLOSE_ID));
        Assert.assertTrue(validateArraySize(response, EMPTY_STRING, NUM_OF_PHARMACY));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "regression" }, priority = 4)
    public void verifyGetMerchantLocationsWithNationwideFalseInsideGeozonePharmacyOpen() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(headers, LATITUDE_INSIDE_GEOZONE, LONGITUDE_INSIDE_GEOZONE,
                STORE_NON_NATIONWIDE_ID, false, false);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, MERCHANT_LOCATIONS_PATH, MERCHANT_LOCATIONS_OPEN_ID));
        Assert.assertTrue(validateArraySize(response, EMPTY_STRING, NUM_OF_PHARMACY));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "regression" }, priority = 5)
    public void verifyGetMerchantLocationsWithNationwideFalseOutsideGeozonePharmacyOpen() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(headers, LATITUDE_OUTSIDE_GEOZONE, LONGITUDE_OUTSIDE_GEOZONE,
                STORE_NON_NATIONWIDE_ID, false, false);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "regression" }, priority = 6)
    public void verifyGetMerchantLocationsWithNationwideFalseInsideGeozonePharmacyClose() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(headers, LATITUDE_INSIDE_GEOZONE, LONGITUDE_INSIDE_GEOZONE,
                STORE_NON_NATIONWIDE_PHARMACY_CLOSE_ID, false, false);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "regression" }, priority = 7)
    public void verifyGetMerchantLocationsWithInactiveStore() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(headers, LATITUDE_INSIDE_GEOZONE, LONGITUDE_INSIDE_GEOZONE,
                STORE_NATIONWIDE_INACTIVE_ID, true, true);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "sanity", "regression" }, priority = 8)
    public void verifyGetMerchantLocationsWithInvalidStoreId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(headers, LATITUDE_INSIDE_GEOZONE, LONGITUDE_INSIDE_GEOZONE,
                INVALID_ID, true, true);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_FOUND));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "sanity", "regression" }, priority = 9)
    public void verifyGetMerchantLocationsWithoutParam() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(headers, LATITUDE_INSIDE_GEOZONE, LONGITUDE_INSIDE_GEOZONE,
                EMPTY_STRING, true, true);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_BAD_REQUEST));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "sanity", "regression" }, priority = 10)
    public void verifyGetMerchantLocationsWithInvalidAppToken() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(getAppTokenHeaders(INVALID_ID), LATITUDE_INSIDE_GEOZONE, LONGITUDE_INSIDE_GEOZONE,
                STORE_NATIONWIDE_PHARMACY_OPEN_ID, true, true);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_UNAUTHORIZED));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "sanity", "regression" }, priority = 11)
    public void verifyGetMerchantLocationsWithoutAppToken() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response response = merchantLocationsV3Api.getMerchantLocations(getAppTokenHeaders(INVALID_ID), LATITUDE_INSIDE_GEOZONE, LONGITUDE_INSIDE_GEOZONE,
                STORE_NATIONWIDE_PHARMACY_OPEN_ID, true, true);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_UNAUTHORIZED));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}