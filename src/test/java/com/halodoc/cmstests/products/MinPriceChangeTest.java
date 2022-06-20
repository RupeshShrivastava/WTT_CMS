//package com.halodoc.cmstests.products;
//
//import static com.halodoc.cmstests.Constants.ACTIVATE_MERCHANT_PRODUCT;
//import static com.halodoc.cmstests.Constants.CREATE_MERCHANT_PRODUCT;
//import static com.halodoc.cmstests.Constants.GET_EXTERNAL_DETAILS;
//import static com.halodoc.cmstests.Constants.MARKUP_CONVERSION;
//import static com.halodoc.cmstests.Constants.USER_AGENT_MERCHANT;
//import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//import java.io.IOException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigBuilder;
//import com.halodoc.cms.MerchantProductResponse;
//import com.halodoc.cmstests.CMSHelper;
//import com.halodoc.cmstests.reports.CustomisedListener;
//import io.restassured.config.RestAssuredConfig;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Listeners;
//import org.testng.annotations.Test;
//
///**
// * Created by nagashree on 12/09/17.
// */
//@Slf4j
//@Getter
//@Listeners (CustomisedListener.class)
//public class MinPriceChangeTest {
//    private String product_external_id_active;
//
//    private String product_external_id_inactive;
//
//    private CMSHelper cmsHelper;
//
//    public RestAssuredConfig config_dz;
//
//    private MerchantProductResponse merchantProductWithMarkUp;
//
//    private MerchantProductResponse merchantProductWithDiscount;
//
//    @BeforeClass (alwaysRun = true)
//    public void setUp() {
//
//        cmsHelper = new CMSHelper();
//        product_external_id_active = cmsHelper.createProduct();
//        product_external_id_inactive = cmsHelper.createProduct();
//        config_dz = new CurlLoggingRestAssuredConfigBuilder().printMultiliner().build();
//
//    }
//
//    @Test (description = "New product with out any merchant products", groups = {"sanity", "regression"})
//    public void checkMinPriceForNewProductCreated() {
//        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
//        given().
//                       config(config_dz).
//                       urlEncodingEnabled(false).
//                       contentType(ContentType.JSON).
//                       headers("X-APP-TOKEN", X_APP_TOKEN).
//                       expect().
//                       log().all().
//                       statusCode(200).
//                       body("base_price", equalTo(900.0f)).
//                       body("min_price", equalTo(0.0f)).
//                       when().
//                       get(GET_EXTERNAL_DETAILS, product_external_id_active);
//
//    }
//
//    @Test (priority = 1, groups = {"sanity", "regression"})
//    public void addNewActiveMerchantProduct() {
//        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
//        // create new Merchant Product in active state
//        Response response = given().
//                                           config(config_dz).
//                                           contentType(ContentType.JSON).
//                                           header("X-APP-TOKEN", X_APP_TOKEN).
//                                           header("User-Agent", USER_AGENT_MERCHANT).
//                                           body(cmsHelper.getRequestFixture("products", "create_merchant_product.json")
//                                                         .replace("$sku_id", cmsHelper.randomString())
//                                                         .replace("$product_id", product_external_id_active)).
//                                           expect().
//                                           log().all().
//                                           statusCode(201).
//                                           body("cost_price", equalTo(500)).
//                                           body("sale_price", equalTo(500.0f)).
//                                           when().
//                                           post(CREATE_MERCHANT_PRODUCT);
//        log.info("addNewActiveMerchantProduct_response" + response.prettyPrint());
//        merchantProductWithDiscount = response.getBody().as(MerchantProductResponse.class);
//
//        // Get Product By ID
//        given().
//                       config(config_dz).
//                       urlEncodingEnabled(false).
//                       contentType(ContentType.JSON).
//                       headers("X-APP-TOKEN", X_APP_TOKEN).
//                       expect().
//                       statusCode(200).
//                       body("base_price", equalTo(500.00f)).
//                       body("min_price", equalTo(500.00f)).
//                       log().all().
//                       when().
//                       get(GET_EXTERNAL_DETAILS, product_external_id_active);
//    }
//
//    @Test (priority = 2, groups = {"regression"})
//    public void addNewInactiveMerchantProduct() throws IOException {
//        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
//        // create new Merchant Product 2 in inactive state - 600,600
//        Response response = given().
//                                           config(config_dz).
//                                           contentType(ContentType.JSON).
//                                           header("X-APP-TOKEN", X_APP_TOKEN).
//                                           body(cmsHelper.getRequestFixture("products", "create_merchant_product_price_600.json")
//                                                         .replace("$sku_id", cmsHelper.randomString())
//                                                         .replace("$product_id", product_external_id_inactive).replace("$true", "false")).
//                                           log().all().
//                                           expect().
//                                           statusCode(201).
//                                           body("cost_price", equalTo(600)).
//                                           body("sale_price", equalTo(600.0f)).
//                                           log().all().
//                                           when().
//                                           post(CREATE_MERCHANT_PRODUCT);
//        log.info("addNewInactiveMerchantProduct_response" + response.prettyPrint());
//        merchantProductWithMarkUp = response.getBody().as(MerchantProductResponse.class);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();
//
//        // Get Product By ID
//        given().
//                       config(config_dz).
//                       urlEncodingEnabled(false).
//                       contentType(ContentType.JSON).
//                       headers("X-APP-TOKEN", X_APP_TOKEN).
//                       expect().
//                       statusCode(200).
//                       body("base_price", equalTo(600.00f)).
//                       body("min_price", equalTo(600.00f)).
//                       log().all().
//                       when().
//                       get(GET_EXTERNAL_DETAILS, product_external_id_inactive);
//
//        // Assert on Base & Min Price - 500 500
//    }
//
//    @Test (priority = 3, groups = {"regression"}, dependsOnMethods = {"addNewInactiveMerchantProduct"})
//    public void activateMerchantProduct() {
//        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
//        // Product 2 status as inactive from active - from TC 2
//
//        // create new Merchant Product 2 in inactive state - 600,600
//        Response response = given().
//                                           config(config_dz).
//                                           contentType(ContentType.JSON).
//                                           header("X-APP-TOKEN", X_APP_TOKEN).
//                                           header("User-Agent", USER_AGENT_MERCHANT).
//                                           body(cmsHelper.getRequestFixture("products", "create_merchant_product_price_600.json")
//                                                         .replace("$sku_id", merchantProductWithMarkUp.getSku_id())
//                                                         .replace("$product_id", product_external_id_inactive).replace("$true", "true")).
//                                           expect().
//                                           statusCode(200).
//                                           body("cost_price", equalTo(600)).
//                                           body("sale_price", equalTo(600.0f)).
//                                           log().all().
//                                           when().
//                                           put(ACTIVATE_MERCHANT_PRODUCT, merchantProductWithMarkUp.getExternal_id());
//        log.info("create_merchant_Id_inactive response" + response.prettyPrint());
//
//        // Get Product By ID
//        given().
//                       config(config_dz).
//                       urlEncodingEnabled(false).
//                       contentType(ContentType.JSON).
//                       headers("X-APP-TOKEN", X_APP_TOKEN).
//                       expect().
//                       statusCode(200).
//                       body("base_price", equalTo(600.00f)).
//                       body("min_price", equalTo(600.00f)).
//                       log().all().
//                       when().
//                       get(GET_EXTERNAL_DETAILS, product_external_id_inactive);
//
//        // merchantProductWithMarkUp.getExternal_id();
//    }
//
//    @Test (description = "Mark up", priority = 4, groups = {"regression"})
//    public void minPriceChangeOnMarkup() throws InterruptedException {
//        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
//        log.info("Merchant product id for mark up : {}", merchantProductWithMarkUp.getExternal_id());
//        given().
//                       config(config_dz).
//                       urlEncodingEnabled(false).
//                       contentType(ContentType.JSON).
//                       header("User-Agent", USER_AGENT_MERCHANT).
//                       headers("X-APP-TOKEN", X_APP_TOKEN).
//                       body(cmsHelper.getRequestFixture("products", "markup.json").replace("$item_id", merchantProductWithMarkUp.getExternal_id())).
//                       expect().
//                       log().all().
//                       when().
//                       post(MARKUP_CONVERSION);
//        log.info(merchantProductWithMarkUp.getExternal_id());
//        Thread.sleep(5000);
//        given().
//                       config(config_dz).
//                       urlEncodingEnabled(false).
//                       contentType(ContentType.JSON).
//                       headers("X-APP-TOKEN", X_APP_TOKEN).
//                       expect().
//                       log().all().
//                       statusCode(200).
//                       body("base_price", equalTo(1300.00f)).
//                       body("min_price", equalTo(1300.00f)).
//                       when().
//                       get(GET_EXTERNAL_DETAILS, product_external_id_inactive);
//
//    }
//
//    @Test (priority = 5, description = "Discount Conversion", groups = {"regression"})
//    public void minPriceChangeOnDiscount() throws InterruptedException {
//        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
//        log.info("Merchant product id for discount : {}", merchantProductWithDiscount.getExternal_id());
//        given().
//                       config(config_dz).
//                       urlEncodingEnabled(false).
//                       contentType(ContentType.JSON).
//                       header("User-Agent", USER_AGENT_MERCHANT).
//                       headers("X-APP-TOKEN", X_APP_TOKEN).
//                       body(cmsHelper.getRequestFixture("products", "discount.json")
//                                     .replace("$item_id", merchantProductWithDiscount.getExternal_id())).
//                       expect().
//                       log().all().
//                       when().
//                       post(MARKUP_CONVERSION);
//        log.info(merchantProductWithMarkUp.getExternal_id());
//        Thread.sleep(5000);
//        given().
//                       config(config_dz).
//                       urlEncodingEnabled(false).
//                       contentType(ContentType.JSON).
//                       headers("X-APP-TOKEN", X_APP_TOKEN).
//                       log().all().
//                       expect().
//                       statusCode(200).
//                       body("base_price", equalTo(300.00f)).
//                       body("min_price", equalTo(300.00f)).
//                       log().all().
//                       when().
//                       get(GET_EXTERNAL_DETAILS, product_external_id_active);
//
//    }
//}
