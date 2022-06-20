package com.halodoc.cmstests.popUpStores;

import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.ErrorCodesAndMessages;
import com.halodoc.cmstests.ResponseValidations;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.halodoc.cmstests.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

/**
 * Updated by praveen.kumar: 18 Nov 2020
 */
@Slf4j
public class popUpStoreE2ETest extends BaseCMSTest {
    private String store_id;

    private String store_location_id;
    private ResponseValidations responseValidations;

    public popUpStoreE2ETest() {
        responseValidations = new ResponseValidations();
    }

    @BeforeClass (alwaysRun = true)
    public void setUp() {
        RestAssured.baseURI = base_cms_url;
    }

    @Test (priority = 1, groups = {"sanity", "regression"})
    public void createStore() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response store_response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "create_store.json")).
                expect().
                log().all().
                statusCode(201).
                body("image_url", is("image_url")).
                when().
                post(STORES);
        store_id = store_response.path("external_id");
        log.info("Store id : " + store_id);

        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                body("image_url", is("image_url")).
                when().
                get(STORES + "/" + store_id);

    }

    @Test (priority = 2, groups = {"sanity", "regression"})
    public void updateStore() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "update_store.json")).
                expect().
                log().all().
                statusCode(204).
                when().
                patch(STORES + "/" + store_id);

        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                body("image_url", is("new_image_url")).
                body("status", is("inactive")).
                when().
                get(STORES + "/" + store_id);

    }

    @Test (priority = 3, groups = {"sanity", "regression"})
    public void addStoreAttributes() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "add_attributes.json")).
                log().all().
                expect().
                log().all().
                statusCode(201).
                body("[0].attribute_key", is("name")).
                body("[0].attribute_value", is("att_value_na")).
                when().
                post(STORE_ATTRIBUTES, store_id);

        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                when().
                get(STORE_ATTRIBUTES, store_id);

    }

    @Test (priority = 4, groups = {"sanity", "regression"})
    public void updateStoreAttributes() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "update_attributes.json")).
                expect().
                log().all().
                statusCode(204).
                when().
                patch(STORE_ATTRIBUTES, store_id);

        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                body("[0].attribute_key", is("name")).
                body("[2].attribute_value", is("att_valuee")).
                when().
                get(STORE_ATTRIBUTES, store_id);

    }

    @Test (priority = 5, groups = {"sanity", "regression"})
    public void addStoreLocations() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response location_response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "add_location.json")).
                expect().
                log().all().
                statusCode(201).
                when().
                post(STORE_LOCATIONS, store_id);
        store_location_id = location_response.path("[0].external_id");

        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                when().
                get(STORE_LOCATIONS, store_id);

    }

    @Test (priority = 6, groups = {"sanity", "regression"})
    public void updateStoreLocations() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "update_location.json")).
                expect().
                log().all().
                statusCode(204).
                when().
                patch(STORE_LOCATIONS + "/" + store_location_id, store_id);

        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                body("active", is(false)).
                when().
                get(STORE_LOCATIONS + "/" + store_location_id, store_id);
    }

    @Test (priority = 7, groups = {"sanity", "regression"})
    public void createAndGetStoreForMultipleImagesSupport() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response store_response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "create_store_multiple_images_support.json")).
                log().all().
                expect().
                log().all().
                statusCode(201).
                body("image_url", is("image_url")).
                when().
                post(STORES);
        store_id = store_response.path("external_id");
        log.info("Store id : " + store_id);

        Response getResponse = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                body("image_url", is("image_url")).
                when().
                get(STORES + "/" + store_id);
        responseValidations.verifyMulipleImages(getResponse.getBody());
    }

    @Test (priority = 7,groups = {"regression"})
    public void createAndGetStoreForMultipleImagesSupportEmpty() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "create_store_multiple_images_support_Empty.json")).
                log().all().
                expect().
                log().all().
                statusCode(201).
                body("image_url", is("image_url")).
                body(containsString("\"images\":[]")).
                when().
                post(STORES);
    }

    @Test (priority = 7,groups = {"regression"})
    public void createAndGetStoreForMultipleImagesSupportNull() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "create_store_multiple_images_support_Null.json")).
                log().all().
                expect().
                log().all().
                statusCode(422).
                when().
                post(STORES);
        Assert.assertEquals(response.getBody().asString(), ERRORS_IMAGES);
    }

    @Test (priority = 7, groups = {"regression"})
    public void createAndGetStoreForMultipleImagesSupportInvalid() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "create_store_multiple_images_support_Invalid.json")).
                log().all().
                expect().
                log().all().
                statusCode(400).
                when().
                post(STORES);
        responseValidations.validateExceptionCodeAndMessage(response.getBody().path("code").toString(), response.getBody().path("message").toString(), ErrorCodesAndMessages.BAD_REQUEST_GENERIC_EXCEPTION);
    }

    @Test (groups = {"sanity", "regression"}, dependsOnMethods = {"createAndGetStoreForMultipleImagesSupport"})
    public void updateStoreForMultipleImagesSupport() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "update_store_multiple_images_support.json")).
                expect().
                log().all().
                statusCode(204).
                when().
                patch(STORES + "/" + store_id);

        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                body("image_url", is("image_url")).
                body("status", is("active")).
                when().
                get(STORES + "/" + store_id);
        //Note: After updating the 6 images
        Assert.assertEquals(response.getBody().path("images.size()").toString(), IMAGES_UPDATED_COUNT, "Failed at validating updated images count");
        responseValidations.verifyMulipleImages(response.getBody());
    }

    @Test (groups = {"regression"}, dependsOnMethods = {"createAndGetStoreForMultipleImagesSupport"})
    public void updateStoreForMultipleImagesSupportEmpty() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "update_store_multiple_images_support_Empty.json")).
                expect().
                log().all().
                statusCode(204).
                when().
                patch(STORES + "/" + store_id);

        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                body("image_url", is("image_url")).
                body("status", is("active")).
                when().
                get(STORES + "/" + store_id);
        //Note: After updating the 6 images
        Assert.assertEquals(response.getBody().path("images.size()").toString(), IMAGES_UPDATED_COUNT, "Failed at validating updated images count");
        responseValidations.verifyMulipleImages(response.getBody());
    }

    @Test (groups = {"regression"}, dependsOnMethods = {"createAndGetStoreForMultipleImagesSupport"})
    public void updateStoreForMultipleImagesSupportNull() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "update_store_multiple_images_support_Null.json")).
                expect().
                log().all().
                statusCode(204).
                when().
                patch(STORES + "/" + store_id);

        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                body("image_url", is("image_url")).
                body("status", is("active")).
                when().
                get(STORES + "/" + store_id);
        //Note: After updating the 6 images
        Assert.assertEquals(response.getBody().path("images.size()").toString(), IMAGES_UPDATED_COUNT, "Failed at validating updated images count");
        responseValidations.verifyMulipleImages(response.getBody());
    }

    @Test (groups = {"regression"}, dependsOnMethods = {"createAndGetStoreForMultipleImagesSupport"})
    public void updateStoreForMultipleImagesSupportInvalid() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("popUpStores", "update_store_multiple_images_support_Invalid.json")).
                expect().
                log().all().
                statusCode(400).
                when().
                patch(STORES + "/" + store_id);
        responseValidations.validateExceptionCodeAndMessage(response.getBody().path("code").toString(), response.getBody().path("message").toString(), ErrorCodesAndMessages.BAD_REQUEST_GENERIC_EXCEPTION);
    }

    @Test (groups = {"sanity", "regression"})
    public void getAllStores() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                log().all().
                statusCode(200).
                body(containsString("\"images\":")).
                when().
                get(STORES);
    }
}
