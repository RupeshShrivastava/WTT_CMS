package com.halodoc.cmstests.categories;

import com.github.javafaker.Faker;
import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.ErrorCodesAndMessages;
import com.halodoc.cmstests.ResponseValidations;
import com.jayway.restassured.response.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.halodoc.cmstests.Constants.*;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by nageshkumar
 * Updated by praveen.kumar: 18 Nov 2020
 * since  30/05/17.
 */
@Slf4j
@Getter
public class CategoryTest extends BaseCMSTest {
    private String cat_external_id;
    private int category_id ;
    private String code;
    private ResponseValidations responseValidations;

    public CategoryTest() {
        responseValidations = new ResponseValidations();
    }

    @Test (priority = 1, groups = {"sanity", "regression"})
    public void getCategory() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                body("id", equalTo(category_id)).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body("channels", hasItems("GO_MED", "HALODOC")).
                statusCode(200).
                when().
                get(base_cms_url + category_endpoint + "/" + category_id);
        log.info("getCategory response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void searchAllCategories() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                body(containsString("id")).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                statusCode(200).
                when().
                get(base_cms_url + category_endpoint + search);
        log.info("searchAllCategories response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void createCategory() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Faker faker = new Faker();
        String epochTime = Long.toString(getEpochTime());

        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "create_category.json").replace("$code", faker.name().firstName() + epochTime).replace("Pak",faker.name().firstName()+epochTime)).
                expect().
                log().all().
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body("channels", hasItems("GO_MED", "HALODOC")).
                when().
                post(base_cms_url + category_endpoint);

        category_id = response.path("id");
        cat_external_id = response.path("external_id");
        code = response.path("code");
        log.info("createCategory response : \n" + response.prettyPrint());
    }

    @Test (priority = 1, groups = {"sanity", "regression"})
    public void getCategoryProductsAtLatlong() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                parameters("latitude", latitude).
                parameters("longitude", longitude).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                when().
                get((base_cms_url + (category_product_latlong.replace("$cat_ext_id", existing_cat_id))));
        log.info("getCategoryProductsAtLatlong response : \n" + response.prettyPrint());
    }

    @Test (priority = 2, groups = {"sanity", "regression"})
    public void updateCategory() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        log.info(base_cms_url + category_endpoint + "/" + category_id);
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "update_category.json").replace("$code", code)).
                expect().
                log().all().
                statusCode(204).
                when().
                put(base_cms_url + category_endpoint + "/" + category_id);
        log.info("updateCategory response : \n" + response.prettyPrint());
    }

    @Test (priority = 2, groups = {"regression"})
    public void updateCategorySameAsParent() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "update_category.json").replace("$code", code)
                        .replace("null", String.valueOf(category_id))).
                expect().
                statusCode(400).
                body("header", containsString("Looping parent category")).
                when().
                put(base_cms_url + category_endpoint + "/" + category_id);

        log.info("updateCategory response : \n" + response.prettyPrint());
    }

    //-------------------------------------Multiple images support tests------------------------------------------

    @Test (groups = {"sanity", "regression"})
    public void createCategoryForMultipleImagesSupport() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Faker faker = new Faker();
        String epochTime = Long.toString(getEpochTime());

        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "create_category_multiple_images_support.json").replace("$code", faker.name().firstName() + epochTime).replace("Pak",faker.name().firstName()+epochTime)).
                expect().
                log().all().
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body("channels", hasItems("GO_MED", "HALODOC")).
                when().
                post(base_cms_url + category_endpoint);

        category_id = response.path("id");
        cat_external_id = response.path("external_id");
        code = response.path("code");
        responseValidations.verifyMulipleImages(response.getBody());
    }


    @Test (groups = {"regression"})
    public void createCategoryForMultipleImagesSupportEmpty() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Faker faker = new Faker();
        String epochTime = Long.toString(getEpochTime());

        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "create_category_multiple_images_support_Empty.json").replace("$code", faker.name().firstName() + epochTime).replace("Pak",faker.name().firstName()+epochTime)).
                expect().
                log().all().
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body("channels", hasItems("GO_MED", "HALODOC")).
                body(containsString("\"images\":[]")).
                when().
                post(base_cms_url + category_endpoint);
    }

    @Test (groups = {"regression"})
    public void createCategoryForMultipleImagesSupportNull() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Faker faker = new Faker();
        String epochTime = Long.toString(getEpochTime());

        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "create_category_multiple_images_support_Null.json").replace("$code", faker.name().firstName() + epochTime).replace("Pak",faker.name().firstName()+epochTime)).
                expect().
                log().all().
                when().
                post(base_cms_url + category_endpoint);
        Assert.assertEquals(response.getBody().asString(), ERRORS_IMAGES);
    }

    @Test (groups = {"regression"})
    public void createCategoryForMultipleImagesSupportInvalidRequest() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Faker faker = new Faker();
        String epochTime = Long.toString(getEpochTime());

        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "create_category_multiple_images_support_Invalid.json").replace("$code", faker.name().firstName() + epochTime).replace("Pak",faker.name().firstName()+epochTime)).
                log().all().
                expect().
                log().all().
                when().
                post(base_cms_url + category_endpoint);
        responseValidations.validateExceptionCodeAndMessage(response.getBody().path("code").toString(), response.getBody().path("message").toString(), ErrorCodesAndMessages.BAD_REQUEST_GENERIC_EXCEPTION);
    }

    @Test (priority = 1, groups = {"sanity", "regression"}, dependsOnMethods ={"createCategoryForMultipleImagesSupport"})
    public void getCategoryForMultipleImagesSupport() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                body("id", equalTo(category_id)).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body("channels", hasItems("GO_MED", "HALODOC")).
                statusCode(200).
                when().
                get(base_cms_url + category_endpoint + "/" + category_id);
        log.info("getCategory response : \n" + response.prettyPrint());
        responseValidations.verifyMulipleImages(response.getBody());
    }

    @Test (groups = {"sanity", "regression"}, dependsOnMethods = {"createCategoryForMultipleImagesSupport"})
    public void updateCategoryForMultipleImagesSupport() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Faker faker = new Faker();
        String epochTime = Long.toString(getEpochTime());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "update_category_multiple_images_support.json").replace("$code", code).replace("Pak",faker.name().firstName()+epochTime)).
                expect().
                log().all().
                statusCode(204).
                when().
                put(base_cms_url + category_endpoint + "/" + category_id);
    }

    @Test (groups = {"regression"})
    public void updateCategoryForMultipleImagesSupportEmpty() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Faker faker = new Faker();
        String epochTime = Long.toString(getEpochTime());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "update_category_multiple_images_support_Empty.json").replace("$code", code).replace("Pak",faker.name().firstName()+epochTime)).
                log().all().
                expect().
                log().all().
                statusCode(204).
                when().
                put(base_cms_url + category_endpoint + "/" + category_id);
    }

    @Test (groups = {"regression"})
    public void updateCategoryForMultipleImagesSupportNull() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Faker faker = new Faker();
        String epochTime = Long.toString(getEpochTime());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "update_category_multiple_images_support_Null.json").replace("$code", code).replace("Pak",faker.name().firstName()+epochTime)).
                log().all().
                expect().
                log().all().
                statusCode(422).
                when().
                put(base_cms_url + category_endpoint + "/" + category_id);
        Assert.assertEquals(response.getBody().asString(), ERRORS_IMAGES);
    }

    @Test (groups = {"regression"})
    public void updateCategoryForMultipleImagesSupportEmptyInvalidRequest() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Faker faker = new Faker();
        String epochTime = Long.toString(getEpochTime());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("categories", "update_category_multiple_images_support_Invalid.json").replace("$code", code).replace("Pak",faker.name().firstName()+epochTime)).
                log().all().
                expect().
                log().all().
                statusCode(400).
                when().
                put(base_cms_url + category_endpoint + "/" + category_id);
        responseValidations.validateExceptionCodeAndMessage(response.getBody().path("code").toString(), response.getBody().path("message").toString(), ErrorCodesAndMessages.BAD_REQUEST_GENERIC_EXCEPTION);
    }

    @Test (priority = 10, groups = {"sanity", "regression"}, dependsOnMethods ={"updateCategoryForMultipleImagesSupport"})
    public void getCategoryForMultipleImagesSupportAfterUpdate() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                body("id", equalTo(category_id)).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body("channels", hasItems("GO_MED", "HALODOC")).
                statusCode(200).
                when().
                get(base_cms_url + category_endpoint + "/" + category_id);
        //Note: After updating the 6 images
        Assert.assertEquals(response.getBody().path("images.size()").toString(), IMAGES_UPDATED_COUNT, "Failed at validating updated images count");
        responseValidations.verifyMulipleImages(response.getBody());
    }
}