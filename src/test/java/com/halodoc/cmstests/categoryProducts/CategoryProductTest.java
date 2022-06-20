package com.halodoc.cmstests.categoryProducts;

import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static com.halodoc.cmstests.Constants.base_cms_url;
import static com.halodoc.cmstests.Constants.category_endpoint;
import static com.halodoc.cmstests.Constants.category_products_endpoint;
import static com.halodoc.cmstests.Constants.product_endpoint;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import com.github.javafaker.Faker;
import com.halodoc.cmstests.BaseCMSTest;
import com.jayway.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * Created by nageshkumar
 * since  30/05/17.
 */
@Slf4j
public class CategoryProductTest extends BaseCMSTest {
    private int product_id;

    private int category_id;

    private int cat_product_id;

    Faker faker = new Faker();

    @Test (groups = {"sanity", "regression"})
    public void createCategoryProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response product_response = given().
                                                   contentType("application/json").
                                                   header("X-APP-TOKEN", X_APP_TOKEN).
                                                   body(getRequestFixture("products", "create_product.json")).
                                                   expect().
                                                   statusCode(201).
                                                   when().
                                                   post(base_cms_url + product_endpoint);
        product_id = product_response.path("id");
        String epochTime = Long.toString(getEpochTime());
        log.info("getCategoryProduct response : \n" + product_response.prettyPrint());
        Response cat_response = given().
                                               contentType("application/json").
                                               header("X-APP-TOKEN", X_APP_TOKEN).
                                               body(getRequestFixture("categories", "create_category.json")
                                                       .replace("$code", faker.name().firstName() + epochTime).replace("Pak",faker.name().firstName() + epochTime)).
                                               expect().
                                               statusCode(201).
                                               when().
                                               post(base_cms_url + category_endpoint);

        category_id = cat_response.path("id");
        log.info("getCategoryProduct response : \n" + cat_response.prettyPrint());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("categoryProducts", "create_category_product.json")
                                                   .replace("$productId", String.valueOf(product_id)).
                                                           replace("$catId", String.valueOf(category_id))).
                                           expect().
                                           body("category_id", is(category_id)).
                                           body("product_id", is(product_id)).
                                           statusCode(201).
                                           when().
                                           post(base_cms_url + category_products_endpoint);
        cat_product_id = response.path("id");
        log.info("getCategoryProduct response : \n" + response.prettyPrint());
    }

    @Test (priority = 1, groups = {"sanity", "regression"})
    public void getCategoryProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           body("id", equalTo(cat_product_id)).
                                           body(containsString("category_id")).
                                           body(containsString("product_id")).
                                           statusCode(200).
                                           when().
                                           get(base_cms_url + category_products_endpoint + "/" + cat_product_id);
        log.info("getCategoryProduct response : \n" + response.prettyPrint());
    }

    @Test (priority = 2, groups = {"sanity", "regression"})
    public void updateCategoryProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("categoryProducts", "update_category_product.json")
                                                   .replace("$productId", String.valueOf(product_id)).
                                                           replace("$catId", String.valueOf(category_id))).
                                           expect().
                                           statusCode(200).
                                           body("category_id", is(category_id)).
                                           body("product_id", is(product_id)).
                                           body("status", is("inactive")).
                                           when().
                                           put(base_cms_url + category_products_endpoint + "/" + cat_product_id);
        log.info("updateCategoryProduct response : \n" + response.prettyPrint());
    }

}