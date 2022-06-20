package com.halodoc.cmstests.banners;

import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static com.halodoc.cmstests.Constants.banner_endpoint;
import static com.halodoc.cmstests.Constants.base_cms_url;
import static com.halodoc.cmstests.Constants.latitude;
import static com.halodoc.cmstests.Constants.longitude;
import static com.halodoc.cmstests.Constants.search;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import com.halodoc.cmstests.BaseCMSTest;
import com.jayway.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * Created by nageshkumar
 * since  30/05/17.
 */
@Slf4j
public class BannerTest extends BaseCMSTest {
    int banner_id;

    @Test (priority = 1, groups = {"sanity", "regression"})
    public void getBanner() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           body("id", equalTo(banner_id)).
                                           body(containsString("external_id")).
                                           body(containsString("image_url")).
                                           statusCode(200).
                                           when().
                                           get(base_cms_url + banner_endpoint + "/" + banner_id);
        log.info("getBanner response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void searchAllBanners() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           parameters("latitude", latitude).
                                           parameters("longitude", longitude).
                                           expect().
                                           body(containsString("id")).
                                           body(containsString("external_id")).
                                           body(containsString("image_url")).
                                           statusCode(200).
                                           when().
                                           get(base_cms_url + banner_endpoint + search);
        log.info("searchAllBanners response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void createBanner() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("banners", "create_banner.json")).
                                           expect().
                                           statusCode(201).
                                           body("type", equalTo("category")).
                                           body(containsString("external_id")).
                                           body(containsString("image_url")).
                                           when().
                                           post(base_cms_url + banner_endpoint);
        banner_id = response.path("id");
        log.info("createBanner response : \n" + response.prettyPrint());
    }

    @Test (priority = 2, groups = {"sanity", "regression"})
    public void updateBanner() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("banners", "update_banner.json")).
                                           expect().
                                           statusCode(200).
                                           body("type", equalTo("product")).
                                           body("channels", hasItems("GO_MED", "HALODOC")).
                                           body(containsString("image_url")).
                                           when().
                                           put(base_cms_url + banner_endpoint + "/" + banner_id);
        log.info("updateBanner response : \n" + response.prettyPrint());
    }
}
