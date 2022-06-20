package com.halodoc.cmstests.priceConversions;

import static com.halodoc.cmstests.Constants.ACTIVATE_CONVERSION;
import static com.halodoc.cmstests.Constants.CONVERSION_ID;
import static com.halodoc.cmstests.Constants.DEACTIVATE_CONVERSION;
import static com.halodoc.cmstests.Constants.GET_CONVERSION;
import static com.halodoc.cmstests.Constants.ITEM_ID;
import static com.halodoc.cmstests.Constants.SEARCH_CONVERSION;
import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static com.halodoc.cmstests.Constants.base_cms_url;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * Created by nageshkumar
 * since  05/09/18.
 */
@Slf4j
public class ConversionTest {
    @Test (priority = 1, groups = {"sanity", "regression"})
    public void deactivateConversion() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       log().all().
                       expect().
                       log().all().
                       statusCode(204).
                       when().
                       put(base_cms_url + DEACTIVATE_CONVERSION, CONVERSION_ID);
    }

    @Test (priority = 2, groups = {"sanity", "regression"})
    public void activateConversion() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       log().all().
                       expect().
                       log().all().
                       statusCode(204).
                       when().
                       put(base_cms_url + ACTIVATE_CONVERSION, CONVERSION_ID);
    }

    @Test (priority = 3, groups = {"sanity", "regression"})
    public void getConversionById() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       log().all().
                       expect().
                       log().all().
                       statusCode(200).
                       body("id", is(CONVERSION_ID)).
                       body(containsString("item_id")).
                       body(containsString("conversion_type")).
                       body(containsString("conversion_level")).
                       when().
                       get(base_cms_url + GET_CONVERSION, CONVERSION_ID);
    }

    @Test (groups = {"sanity", "regression"})
    public void searchAllConversions() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       log().all().
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("item_id")).
                       body(containsString("conversion_type")).
                       body(containsString("conversion_level")).
                       when().
                       get(base_cms_url + SEARCH_CONVERSION);
    }

    @Test (groups = {"regression"})
    public void searchConversionByFilter() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("conversion_level", "merchant_product").
                       queryParam("item_id", ITEM_ID).
                       log().all().
                       expect().
                       log().all().
                       statusCode(200).
                       body("item_id",hasItem(ITEM_ID)).
                       body("conversion_level",hasItem("merchant_product")).
                       body(containsString("conversion_type")).
                       when().
                       get(base_cms_url + SEARCH_CONVERSION);
    }

    @Test (groups = {"regression"})
    public void searchConversionByInvalidConversionLevel() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("conversion_level", ITEM_ID).
                       queryParam("item_id", ITEM_ID).
                       log().all().
                       expect().
                       log().all().
                       statusCode(400).
                       when().
                       get(base_cms_url + SEARCH_CONVERSION);
    }

    @Test (groups = {"regression"})
    public void searchConversionByInvalidItemId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("conversion_level", "merchant_location").
                       queryParam("item_id", ITEM_ID).
                       log().all().
                       expect().
                       log().all().
                       statusCode(404).
                       body("message",is("Conversion Attributes not found")).
                       when().
                       get(base_cms_url + SEARCH_CONVERSION);
    }
}
