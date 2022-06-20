package com.halodoc.cmstests.locations;

import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.CMSHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static com.halodoc.cmstests.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@Slf4j
public class MerchantLocationProducts extends BaseCMSTest {
    final int max_locations=90000;
    final int noOfPages=220;
    final String  perPage="25";
    private CMSHelper cmsHelper = new CMSHelper();

    private String product_ext_id;
    int successCount=0;
    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = CMS_URL_V2;
    }
    @Test ()
    public void createProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        com.jayway.restassured.response.Response response = com.jayway.restassured.RestAssured.given().
                contentType("application/json").log().all().
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("products", "create_product.json")).
                expect().
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

        product_ext_id = response.path("external_id");
        log.info("createProduct response : \n" + response.prettyPrint());
    }
    @Test(dependsOnMethods = "createProduct")
    public void getAllMerchantLocations() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        for(int index=1;index<=noOfPages;index++) {
            Response response = given().

                    contentType("application/json").
                    header("X-APP-TOKEN", X_APP_TOKEN)
                    .param("per_page", perPage).
                            param("page_no",String.valueOf(index)).
                            expect().
                            statusCode(200).
                            body(containsString("id")).
                            body(containsString("name")).
                            body(containsString("address_line")).
                            body(containsString("business_hours")).
                            when().
                            get(MERCHANT_LOCATIONS_V2);
            List<String> merchant_external_id_count = new ArrayList<>();
            merchant_external_id_count = response.path("result.external_id");


            for (int index1 = 0; index1 <= merchant_external_id_count.size(); index1++) {
                try {
                    addProducts(response.path("result.merchant_id[" + index1 + "]"), response.path("result.external_id[" + index1 + "]"));
                    //    merchant_details.put(response.path("result.merchant_id[" + index + "]"), response.path("result.external_id[" + index + "]"));
                } catch (Exception exception) {
                    log.error("Error in adding ...");
                }
            }
        }
        log.info("Successfully inserted product with external ID " + product_ext_id + " for " + successCount + "  Merchant locations ");

    }

//    @Test(dependsOnMethods = "getAllMerchantLocations")
    public void addProducts(String merchant_id,String merchant_location_id ) {
//
//        for (Map.Entry<String, String> entry : merchant_details.entrySet()) {

        if (!(merchant_id == null || merchant_location_id.isEmpty())) {
//            continue;
//            }

            log.info("Merchant Ex id " + merchant_id + "Merchant location ID " + merchant_location_id);

            com.jayway.restassured.response.Response response = com.jayway.restassured.RestAssured.given().

                    contentType("application/json").
                    log().all().
                    header("X-APP-TOKEN", X_APP_TOKEN).
                    body(getRequestFixture("merchantProducts", "create_merchant_product.json")
                            .replace("$sku_id", cmsHelper.randomString()).replace("$product_id", product_ext_id).replace("$cost_price", getRandomNumberUsingInts(150, 2999)).replace("$sale_price", getRandomNumberUsingInts(150, 2999)))
                    .expect().
                            when().
                            post(base_cms_url + merchant_products.replace("$merc_ext_id", merchant_id)
                                    .replace("$merc_loc_id", merchant_location_id));
            successCount++;

        }

    }
    public String getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return String.valueOf(random.ints(min, max)
                .findFirst()
                .getAsInt());
    }
}
