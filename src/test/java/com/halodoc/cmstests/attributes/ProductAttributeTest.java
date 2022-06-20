package com.halodoc.cmstests.attributes;

import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static com.halodoc.cmstests.Constants.attribute;
import static com.halodoc.cmstests.Constants.base_cms_url;
import static com.halodoc.cmstests.Constants.product_endpoint;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import com.halodoc.cmstests.BaseCMSTest;
import com.jayway.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * Created by nageshkumar
 * since  07/06/17.
 */
@Slf4j
public class ProductAttributeTest extends BaseCMSTest {
    private int product_id;

    @Test (groups = {"sanity", "regression"})
    public void createAttribute() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response prod_response = given().
                                                contentType("application/json").
                                                header("X-APP-TOKEN", X_APP_TOKEN).
                                                body(getRequestFixture("products", "create_product.json")).
                                                expect().
                                                statusCode(201).
                                                when().
                                                post(base_cms_url + product_endpoint);
        product_id = prod_response.path("id");
        log.info("CreateProduct response : "+prod_response.prettyPrint());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("attributes", "create_attribute.json")).
                                           expect().
                                           statusCode(201).
                                           body(containsString("prescription_required")).
                                           body(containsString("composition")).
                                           body(containsString("segmentation")).
                                           body(containsString("selling_unit")).
                                           body(containsString("dosage_form")).
                                           when().
                                           post(base_cms_url + attribute.replace("$product_id", String.valueOf(product_id)));

        log.info("createAttribute response : \n" + response.prettyPrint());
    }

    @Test (priority = 1, groups = {"sanity", "regression"})
    public void getAttribute() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           statusCode(200).
                                           body(containsString("prescription_required")).
                                           body(containsString("composition")).
                                           body(containsString("segmentation")).
                                           body(containsString("selling_unit")).
                                           body(containsString("dosage_form")).
                                           when().
                                           get(base_cms_url + attribute.replace("$product_id", String.valueOf(product_id)));

        log.info("getAttribute response : \n" + response.prettyPrint());
    }

    @Test (priority = 2, groups = {"sanity", "regression"})
    public void updateAttribute() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("attributes", "update_attribute.json")).
                                           expect().
                                           statusCode(204).
                                           when().
                                           put(base_cms_url + attribute.replace("$product_id", String.valueOf(product_id)));

        log.info("updateAttribute response : \n" + response.prettyPrint());
    }

}
