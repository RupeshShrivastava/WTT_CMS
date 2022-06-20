package com.halodoc.cmstests.productMetaAttributes;

import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static com.halodoc.cmstests.Constants.base_cms_url;
import static com.halodoc.cmstests.Constants.product_meta_attributes;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import com.halodoc.cmstests.BaseCMSTest;
import com.jayway.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * Created by nageshkumar
 * since  08/06/17.
 */
@Slf4j
public class ProductMetaAttributesTest extends BaseCMSTest{

    @Test (groups = {"sanity", "regression"})
    public void getAllProductMetaAttributes() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           statusCode(200).
                                           body(containsString("id")).
                                           body(containsString("type")).
                                           body(containsString("name")).
                                           body(containsString("value_type")).
                                           when().
                                           get(base_cms_url + product_meta_attributes);

        log.info("getAllProductMetaAttributes response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void getProductMetaAttributeById() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           statusCode(200).
                                           body(containsString("id")).
                                           body(containsString("type")).
                                           body(containsString("name")).
                                           body(containsString("value_type")).
                                           when().
                                           get(base_cms_url + product_meta_attributes+"/"+1);

        log.info("getProductMetaAttributeById response : \n" + response.prettyPrint());
    }
}
