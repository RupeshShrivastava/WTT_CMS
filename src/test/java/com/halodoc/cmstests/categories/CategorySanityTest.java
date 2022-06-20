package com.halodoc.cmstests.categories;

import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static com.halodoc.cmstests.Constants.base_cms_url;
import static com.halodoc.cmstests.Constants.category_endpoint;
import static com.halodoc.cmstests.Constants.category_product_latlong;
import static com.halodoc.cmstests.Constants.existing_cat_id;
import static com.halodoc.cmstests.Constants.latitude;
import static com.halodoc.cmstests.Constants.longitude;
import static com.halodoc.cmstests.Constants.search;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import com.halodoc.cmstests.BaseCMSTest;
import com.jayway.restassured.response.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter

public class CategorySanityTest extends BaseCMSTest {

    private String cat_external_id;
    private int category_id = 2455;
    private String code;

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

    @Test (priority = 2, groups = {"sanity", "regression"})
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

    @Test (priority = 3, groups = {"sanity", "regression"})
    public void createCategory() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Faker faker = new Faker();
        String epochTime = Long.toString(getEpochTime());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("categories", "create_category.json").replace("$code", faker.name().firstName()  + epochTime).replace("Pak",faker.name().firstName() + epochTime)).
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

    @Test (priority = 4, groups = {"sanity", "regression"})
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

    @Test (priority = 5, groups = {"sanity", "regression"})
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

    @Test (priority = 6, groups = {"regression"})
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
}
