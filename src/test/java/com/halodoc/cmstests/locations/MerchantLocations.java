package com.halodoc.cmstests.locations;

import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static com.halodoc.cmstests.Constants.base_cms_url;
import static com.halodoc.cmstests.Constants.merchant;
import static com.halodoc.cmstests.Constants.merchant_locations;
import static com.halodoc.cmstests.Constants.merchant_locations_business_hours;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import com.github.javafaker.Faker;
import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.CMSHelper;
import com.jayway.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * Created by nageshkumar
 * since  09/06/17.
 */
@Slf4j
public class MerchantLocations extends BaseCMSTest {
    private int merchant_id;

    private String merchant_ext_id;

    private int merchant_location_id;

    @Test (groups = {"sanity", "regression"})
    public void createMerchantLocation() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response merchant_response = given().

                                                    contentType("application/json").
                                                    header("X-APP-TOKEN", X_APP_TOKEN).
                                                    body(getRequestFixture("merchants", "create_merchant.json")).
                                                    expect().
                                                    statusCode(201).
                                                    when().
                                                    post(base_cms_url + merchant);
        merchant_id = merchant_response.path("id");
        merchant_ext_id = merchant_response.path("external_id");
        log.info("createMerchant response : \n" + merchant_response.prettyPrint());
        Response response = given().

                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("locations", "create_merchant_location.json")).
                                           expect().
                                           statusCode(201).
                                           body(containsString("id")).
                                           body(containsString("name")).
                                           body(containsString("address_line")).
                                           body(containsString("business_hours")).
                                           when().
                                           post(base_cms_url + merchant_locations.replace("$id", String.valueOf(merchant_id)));
        merchant_location_id = response.path("id");
        log.info("createMerchantLocation response : \n" + response.prettyPrint());
    }

    @Test(priority = 1, groups = {"sanity", "regression"})
    public void getMerchantLocationsForMerchantId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           statusCode(200).
                                           body(containsString("id")).
                                           body(containsString("name")).
                                           body(containsString("address_line")).
                                           body(containsString("business_hours")).
                                           when().
                                           get(base_cms_url + merchant_locations.replace("$id", String.valueOf(merchant_id)) + "/search");

        log.info("getMerchantLocationsForMerchantId response : \n" + response.prettyPrint());

    }

    @Test(priority = 1, groups = {"sanity", "regression"})
    public void getMerchantLocationsForMerchantExtId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           statusCode(200).
                                           body(containsString("id")).
                                           body(containsString("name")).
                                           body(containsString("address_line")).
                                           body(containsString("business_hours")).
                                           when().
                                           get(base_cms_url + merchant_locations
                                                   .replace("$id", String.valueOf(merchant_ext_id)) + "/external/search");

        log.info("getMerchantLocationsForMerchantExtId response : \n" + response.prettyPrint());
    }

    @Test(priority = 1, groups = {"regression"})
    public void createMerchantLocBusinessHr() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("locations", "create_mer_loc_businesshr.json")).
                                           expect().
                                           statusCode(204).
                                           when().
                                           post(base_cms_url + merchant_locations_business_hours.replace("$id", String.valueOf(merchant_id))
                                                                                                .replace("$loc_id",
                                                                                                        String.valueOf(merchant_location_id)));
        log.info("createMerchantLocBusinessHr response : \n" + response.prettyPrint());
    }

    @Test(priority = 1, groups = {"regression"})
    public void getMerchantLocBusinessHr() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           statusCode(200).
                                           body(containsString("day_of_week")).
                                           body(containsString("day_of_week")).
                                           when().
                                           get(base_cms_url + merchant_locations_business_hours.replace("$id", String.valueOf(merchant_id))
                                                                                               .replace("$loc_id",
                                                                                                       String.valueOf(merchant_location_id)));
        log.info("getMerchantLocBusinessHr response : \n" + response.prettyPrint());
    }

}
