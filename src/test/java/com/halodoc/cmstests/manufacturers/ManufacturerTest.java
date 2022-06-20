package com.halodoc.cmstests.manufacturers;

import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static com.halodoc.cmstests.Constants.base_cms_url;
import static com.halodoc.cmstests.Constants.manufacturer_endpoint;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
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
public class ManufacturerTest extends BaseCMSTest {
    private int manufacturer_id;

    Faker faker = new Faker();
    String epochTime = Long.toString(getEpochTime());

    @Test (priority = 0, groups = {"regression", "sanity"})
    public void createManufacturer() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("manufacturers", "create_manufacturer.json")
                                                   .replace("$code", faker.name().lastName() + epochTime)).
                                           log().all().
                                           expect().
                                            log().all().
                                           statusCode(201).
                                           when().
                                           post(base_cms_url + manufacturer_endpoint);
        manufacturer_id = response.path("id");
        log.info("createManufacturer response : \n" + response.prettyPrint());
    }

    @Test (dependsOnMethods = "createManufacturer", priority = 0, groups = {"regression", "sanity"})
    public void getManufacturer() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().
                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                body("id", equalTo(manufacturer_id)).
                body(containsString("external_id")).
                body(containsString("phone")).
                body(containsString("name")).
                body(containsString("contact_person")).
                statusCode(200).
                when().
                get(base_cms_url + manufacturer_endpoint + "/" + manufacturer_id);
        log.info("getManufacturer response : \n" + response.prettyPrint());
    }

    @Test (dependsOnMethods = "createManufacturer", priority = 0, groups = {"regression", "sanity"})
    public void updateManufacturer() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        String epochTime = Long.toString(getEpochTime());
        Response response = given().
                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("manufacturers", "update_manufacturer.json")
                                                   .replace("$code", faker.name().lastName() + epochTime)).
                                           log().all().
                                           expect().
                                           statusCode(204).
                                           when().
                                           put(base_cms_url + manufacturer_endpoint + "/" + manufacturer_id);
        log.info("updateManufacturer response : \n" + response.prettyPrint());
    }

}