package com.halodoc.cmstests;

import static com.halodoc.cmstests.Constants.CONTENT_TYPE;
import static com.halodoc.cmstests.Constants.CREATE_PRODUCTS;
import static com.halodoc.cmstests.Constants.CREATE_PRODUCT_BASE_URL;
import static com.halodoc.cmstests.Constants.FILE_LOCATION;
import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

/**
 * Created by nagashree on 12/09/17.
 */
@Slf4j
public class CMSHelper {
    public String getRequestFixture(String folderName, String fileName) {
        return fixture(FILE_LOCATION + "/" + folderName + "/" + fileName);
    }


    public String createProduct() {
        Response create_order_response = given().
                                                        contentType(CONTENT_TYPE).
                                                        headers("X-APP-TOKEN", X_APP_TOKEN).
                                                        body(getRequestFixture("products","create_product.json")).
                                                        expect().
                                                      //  log().all().
                                                        statusCode(201).
                                                        when().
                                                        post(CREATE_PRODUCT_BASE_URL + CREATE_PRODUCTS);
        return  create_order_response.path("external_id");
    }

    public String randomString() {
        return UUID.randomUUID().toString();
    }
}
