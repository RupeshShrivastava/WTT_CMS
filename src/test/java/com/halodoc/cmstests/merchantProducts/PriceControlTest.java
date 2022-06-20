package com.halodoc.cmstests.merchantProducts;

import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.CMSHelper;
import com.halodoc.cmstests.apiDefinitions.MerchantApi;
import com.halodoc.cmstests.apiDefinitions.PriceControlApi;
import com.halodoc.cmstests.apiDefinitions.ProductsApi;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import static com.halodoc.cmstests.Constants.*;


@Slf4j
public class PriceControlTest extends BaseCMSTest {
    private PriceControlApi priceControlApi = new PriceControlApi();
    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);
    private int merchant_id;

    private String merchant_ext_id;

    private String merchant_location_ext_id;
    private int product_id;
    private String product_ext_id;

    private int product_id_reusable;
    private String product_ext_id_reusable;

    private String merchant_product_id;
    private CMSHelper cmsHelper = new CMSHelper();
    private MerchantApi merchantApi = new MerchantApi();
    private ProductsApi productsApi = new ProductsApi();
    private String sku_id_reusable;

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        Response merchant_response = merchantApi.createMerchant(headers);
        Assert.assertEquals(merchant_response.getStatusCode(),HttpStatus.SC_CREATED);
        merchant_id = merchant_response.path("id");
        merchant_ext_id = merchant_response.path("external_id");

        Response mer_loc_response = merchantApi.createMerchantLocations(headers, merchant_id);
        Assert.assertEquals(mer_loc_response.getStatusCode(),HttpStatus.SC_CREATED);
        merchant_location_ext_id = mer_loc_response.path("external_id");

        Response prod_response = productsApi.productCreation(headers);
        product_id_reusable = prod_response.path("id");
        product_ext_id_reusable = prod_response.path("external_id");
        Assert.assertEquals(prod_response.getStatusCode(),HttpStatus.SC_CREATED);

    }

    @Test(groups = { "sanity", "regression" }, priority = 0)
    public void verifyMerchantPriceErrorOnIncreaseInApotikMarkup() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = priceControlApi.changePriceConversions(headers,CONVERSION_TYPE_APOTIK_MARKUP,PRICE_CONVERSION_ITEM_ID,PRICE_CONVERSION_ATTRIBUTE_PRIORITY_2,APOTIK_MARKUP_MULTIPLICATIVE_FACTOR,ATTRIBUTE_ID_APOTIK_MARKUP);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_NOT_ACCEPTABLE));
        Assert.assertTrue(validateResponseValue(response, CODE, EXCEED_MERCHANT_PRICE_ERROR));
        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = {"sanity", "regression"},priority = 1)
    public void createMerchantProductWithRecommendablePriceLessThanMerchantPrice() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String sku_id=cmsHelper.randomString();
        Response response = merchantApi.createMerchantLocationsProduct(headers,sku_id, product_ext_id_reusable, merchant_ext_id, merchant_location_ext_id,
                 "500", "500", "400");
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_NOT_ACCEPTABLE);

    }

    @Test (groups = {"sanity", "regression"},priority = 2)
    public void createMerchantProductWithRecommendablePrice() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        sku_id_reusable=cmsHelper.randomString();
        Response response = merchantApi.createMerchantLocationsProduct(headers,sku_id_reusable, product_ext_id_reusable, merchant_ext_id, merchant_location_ext_id,
                "500", "500", "600");
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_CREATED);
        merchant_product_id = response.path("external_id");
    }

    @Test (groups = {"sanity", "regression"},priority = 3)
    public void changeRecommendablePriceToNegativeAndBlank() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = merchantApi.updateRecommendableMerchantProduct(headers,sku_id_reusable, product_ext_id_reusable, merchant_ext_id, merchant_location_ext_id,
                "500", "500", "",merchant_product_id);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK);
        response = merchantApi.updateRecommendableMerchantProduct(headers,sku_id_reusable, product_ext_id_reusable, merchant_ext_id, merchant_location_ext_id,
                "500", "500", "-1000",merchant_product_id);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_NOT_ACCEPTABLE);
    }

    @Test (groups = {"sanity", "regression"},priority = 4)
    public void changeRecommendablePriceMoreThanMercahntPrice() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
      Response response = merchantApi.updateRecommendableMerchantProduct(headers, sku_id_reusable, product_ext_id_reusable, merchant_ext_id, merchant_location_ext_id,
                "500", "500", "700", merchant_product_id);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test (groups = {"sanity", "regression"},priority = 5)
    public void verifyTakeRateMerchantProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        //get merchant product details by external id
        Response response = merchantApi.getMerchantProductDetails(headers,merchant_ext_id,merchant_location_ext_id,merchant_product_id);
        Float merchantPrice=response.path("merchant_price");
        Float salePrice=response.path("sale_price");
        Float expectedTakeRate=(salePrice-merchantPrice)/salePrice;
        Float actualTakeRate = response.path("take_rate");
        Assert.assertEquals(actualTakeRate,expectedTakeRate, "take rate found different");

    }

    @Test (groups = {"sanity", "regression"},priority = 6)
    public void verifyRecommendablePriceMoreThanMaxAllowablePrice() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = merchantApi.updateRecommendableMerchantProduct(headers,sku_id_reusable, product_ext_id_reusable, merchant_ext_id, merchant_location_ext_id,
                "500", "500", "10000",merchant_product_id);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_NOT_ACCEPTABLE);
        Assert.assertEquals(response.path(CODE).toString(), EXCEED_MAX_ALLOWABLE_ERROR);
    }

    }
