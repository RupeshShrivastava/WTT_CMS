package com.halodoc.cmstests.merchantProducts;

import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static com.halodoc.cmstests.Constants.base_cms_url;
import static com.halodoc.cmstests.Constants.merchant;
import static com.halodoc.cmstests.Constants.merchant_locations;
import static com.halodoc.cmstests.Constants.merchant_products;
import static com.halodoc.cmstests.Constants.product_endpoint;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.CMSHelper;
import com.jayway.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * Created by nageshkumar
 * since  12/06/17.
 */
@Slf4j
public class MerchantProductsTest extends BaseCMSTest {
    private int merchant_id;

    private String merchant_ext_id;

    private String merchant_location_ext_id;

    private int product_id;

    private String product_ext_id;

    private String merchant_product_id;

    private CMSHelper cmsHelper = new CMSHelper();

    @Test (groups = {"sanity", "regression"})
    public void createMerchantProduct() {
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
        Response mer_loc_response = given().

                                                   contentType("application/json").
                                                   header("X-APP-TOKEN", X_APP_TOKEN).
                                                   body(getRequestFixture("locations", "create_merchant_location.json")).
                                                   expect().
                                                   statusCode(201).
                                                   when().
                                                   post(base_cms_url + merchant_locations.replace("$id", String.valueOf(merchant_id)));
        merchant_location_ext_id = mer_loc_response.path("external_id");
        Response prod_response = given().
                                                contentType("application/json").
                                                header("X-APP-TOKEN", X_APP_TOKEN).
                                                body(getRequestFixture("products", "create_product.json")).
                                                expect().
                                                statusCode(201).
                                                when().
                                                post(base_cms_url + product_endpoint);
        product_id = prod_response.path("id");
        product_ext_id = prod_response.path("external_id");
        Response response = given().

                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("merchantProducts", "create_merchant_product.json")
                                                   .replace("$sku_id", cmsHelper.randomString()).replace("$product_id", product_ext_id).replace("$cost_price","500").replace("$sale_price","500")).
                                           expect().
                                           log().all().
                                           statusCode(201).
                                           body("product_id", is(product_ext_id)).
                                           when().
                                           post(base_cms_url + merchant_products.replace("$merc_ext_id", merchant_ext_id)
                                                                                .replace("$merc_loc_id", merchant_location_ext_id));
        merchant_product_id = response.path("external_id");
        log.info("createMerchantProduct response : \n" + response.prettyPrint());
    }

    @Test (priority = 1, groups = {"sanity", "regression"})
    public void getMerchantProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           log().all().
                                           statusCode(200).
                                           body("product_id", is(product_ext_id)).
                                           body("external_id", is(merchant_product_id)).
                                           when().
                                           get(base_cms_url + merchant_products.replace("$merc_ext_id", merchant_ext_id).replace("$merc_loc_id",
                                                   merchant_location_ext_id) + "/" + merchant_product_id);
        log.info("getMerchantProduct response : \n" + response.prettyPrint());
    }
}
