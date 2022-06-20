package com.halodoc.cmstests.products;

import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.ErrorCodesAndMessages;
import com.halodoc.cmstests.ResponseValidations;
import com.jayway.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.halodoc.cmstests.Constants.*;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by nageshkumar
 * Updated by praveen.kumar: 18 Nov 2020
 * since  30/05/17.
 */
@Slf4j
public class ProductTest extends BaseCMSTest {
    private int product_id;
    private int product_id_images;
    private String product_ext_id_images;

    private String product_ext_id;
    private ResponseValidations responseValidations;

    public ProductTest() {
        responseValidations = new ResponseValidations();
    }

    @Test (groups = {"sanity", "regression"})
    public void createProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").log().all().
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "create_product.json")).
                log().all().
                expect().
                log().all().
                statusCode(201).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                body(containsString("\"images\":[]")).
                when().
                post(base_cms_url + product_endpoint);
        product_id = response.path("id");
        product_ext_id = response.path("external_id");
        log.info("createProduct response : \n" + response.prettyPrint());
    }



    @Test (priority = 1, groups = {"sanity", "regression"})
    public void getProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body("id", equalTo(product_id)).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                get(base_cms_url + product_endpoint + "/" + product_id);
        log.info("getProduct response : \n" + response.prettyPrint());
    }

    @Test (priority = 2, groups = {"sanity", "regression"})
    public void updateProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "update_product.json")).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                put(base_cms_url + product_endpoint + "/" + product_id);
        log.info("updateProduct response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void searchAllProducts() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("id")).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                when().
                get(base_cms_url + product_endpoint + search);
        log.info("searchAllProducts response : \n" + response.prettyPrint());
    }

    @Test (priority = 1, groups = {"sanity", "regression"})
    public void getCategoriesForAProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("external_id")).
                body(containsString("code")).
                body(containsString("name")).
                body(containsString("sub_categories")).
                body(containsString("image_url")).
                body(containsString("channels")).
                when().
                get(base_cms_url + product_categories.replace("$product_id", existing_product_id));
        log.info("getProduct response : \n" + response.prettyPrint());
    }

    @Test (priority = 1, groups = {"sanity", "regression"})
    public void getProductAttributes() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                urlEncodingEnabled(false).
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                get( base_cms_url + product_attributes.replace("$product_external_id", product_ext_id));
        log.info("getProductAttributes response : \n" + response.prettyPrint());
    }

    @Test (priority = 1, groups = {"sanity", "regression"})
    public void getProductDetails() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                urlEncodingEnabled(false).
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("product_attributes")).
                body(containsString("manufacturer")).
                when().
                get( base_cms_url + product_details.replace("$product_external_id", product_ext_id));
        log.info("getProductDetails response : \n" + response.prettyPrint());
    }

    //-------------------------------------Multiple images support tests------------------------------------------

    @Test (groups = {"sanity", "regression"})
    public void createProductForMultipleImagesSupport() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").log().all().
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "create_product_multiple_images_support.json")).
                log().all().
                expect().
                log().all().
                statusCode(201).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                post(base_cms_url + product_endpoint);
        product_id_images = response.path("id");
        product_ext_id_images = response.path("external_id");
        responseValidations.verifyMulipleImages(response.getBody());
    }

    @Test (groups = {"regression"})
    public void createProductForMultipleImagesSupportEmpty() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").log().all().
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "create_product_multiple_images_support_Empty.json")).
                log().all().
                expect().
                log().all().
                statusCode(201).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                body(containsString("\"images\":[]")).
                when().
                post(base_cms_url + product_endpoint);
    }

    @Test (groups = {"regression"})
    public void createProductForMultipleImagesSupportNull() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").log().all().
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "create_product_multiple_images_support_null.json")).
                log().all().
                expect().
                log().all().
                statusCode(422).
                when().
                post(base_cms_url + product_endpoint);
        Assert.assertEquals(response.getBody().asString(), ERRORS_IMAGES);
    }

    @Test (groups = {"regression"})
    public void createProductForMultipleImagesSupportInvalidString() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").log().all().
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "create_product_multiple_images_support_invalid.json")).
                log().all().
                expect().
                log().all().
                statusCode(400).
                when().
                post(base_cms_url + product_endpoint);
        responseValidations.validateExceptionCodeAndMessage(response.getBody().path("code").toString(), response.getBody().path("message").toString(), ErrorCodesAndMessages.BAD_REQUEST_GENERIC_EXCEPTION);
    }

    @Test (priority = 1, groups = {"regression"})
    public void getProductForMultipleImagesSupport() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body("id", equalTo(product_id_images)).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                get(base_cms_url + product_endpoint + "/" + product_id_images);
        log.info("getProduct response : \n" + response.prettyPrint());
        responseValidations.verifyMulipleImages(response.getBody());
    }

    @Test (groups = {"sanity", "regression"}, dependsOnMethods = {"createProductForMultipleImagesSupport"})
    public void updateProductForMultipleImagesSupport() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "update_product_multiple_images_support.json")).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                put(base_cms_url + product_endpoint + "/" + product_id_images);
        log.info("updateProduct response : \n" + response.prettyPrint());
        Assert.assertEquals(response.getBody().path("images.size()").toString(), IMAGES_UPDATED_COUNT, "Failed at validating updated images count");
        responseValidations.verifyMulipleImages(response.getBody());
    }

    @Test (groups = {"regression"})
    public void updateProductForMultipleImagesSupportEmpty() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").log().all().
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "update_product_multiple_images_support_Empty.json")).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                put(base_cms_url + product_endpoint + "/" + product_id_images);
        //Note: Here updating empty images is not considered for updation which is the expected behavior
        Assert.assertEquals(response.getBody().path("images.size()").toString(), IMAGES_UPDATED_COUNT, "Failed at validating updated images count");
        responseValidations.verifyMulipleImages(response.getBody());
    }

    @Test (groups = {"regression"})
    public void updateProductForMultipleImagesSupportNull() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").log().all().
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "update_product_multiple_images_support_Null.json")).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                put(base_cms_url + product_endpoint + "/" + product_id_images);
        //Note: Here updating null images is not considered for updation which is the expected behavior
        Assert.assertEquals(response.getBody().path("images.size()").toString(), IMAGES_UPDATED_COUNT, "Failed at validating updated images count");
        responseValidations.verifyMulipleImages(response.getBody());
    }

    @Test (groups = {"regression"})
    public void updateProductForMultipleImagesSupportInvalidString() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").log().all().
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "update_product_multiple_images_support_Invalid.json")).
                log().all().
                expect().
                log().all().
                statusCode(400).
                when().
                put(base_cms_url + product_endpoint + "/" + product_id_images);
        responseValidations.validateExceptionCodeAndMessage(response.getBody().path("code").toString(), response.getBody().path("message").toString(), ErrorCodesAndMessages.BAD_REQUEST_GENERIC_EXCEPTION);
    }

    @Test (groups = {"sanity", "regression"})
    public void searchAllProductsForMultipleImages() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("id")).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                when().
                get(base_cms_url + product_endpoint + search);
        log.info("searchAllProducts response : \n" + response.prettyPrint());
        for(int i=0; i<5; i++)
            Assert.assertTrue(response.path("products["+i+"]").toString().contains(IMAGES));
    }

    @Test (groups = {"sanity", "regression"}, dependsOnMethods = {"updateProductForMultipleImagesSupport"})
    public void getProductForMultipleImagesSupportAfterUpdate() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body("id", equalTo(product_id_images)).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                get(base_cms_url + product_endpoint + "/" + product_id_images);
        //After updation of 6 images
        Assert.assertEquals(response.getBody().path("images.size()").toString(), IMAGES_UPDATED_COUNT, "Failed at validating updated images count");
        responseValidations.verifyMulipleImages(response.getBody());
    }

    @Test (groups = {"regression"}, dependsOnMethods = {"updateProductForMultipleImagesSupport"})
    public void getProductAttributesForMultipleImagesSupportAfterUpdate() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                urlEncodingEnabled(false).
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                get( base_cms_url + product_attributes.replace("$product_external_id", product_ext_id_images));
        log.info("getProductAttributes response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void getProductAttributesForMultipleImagesSupport() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                urlEncodingEnabled(false).
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("external_id")).
                body(containsString("image_url")).
                body(containsString("name")).
                body(containsString("thumbnail_url")).
                body(containsString("meta_description")).
                body(containsString("meta_keywords")).
                body(containsString("manufacturer_id")).
                body(containsString("base_price")).
                when().
                get( base_cms_url + product_attributes.replace("$product_external_id", product_ext_id_images));
        log.info("getProductAttributes response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void getProductDetailsForMultipleImagesSupport() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                urlEncodingEnabled(false).
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                log().all().
                expect().
                log().all().
                statusCode(200).
                body(containsString("product_attributes")).
                body(containsString("manufacturer")).
                when().
                get( base_cms_url + product_details.replace("$product_external_id", product_ext_id_images));
        log.info("getProductDetails response : \n" + response.prettyPrint());
    }
}