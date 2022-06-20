package com.halodoc.cmstests.apiDefinitions;

import com.halodoc.cmstests.CMSHelper;
import com.halodoc.utils.http.RestClient;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

import static com.halodoc.cmstests.Constants.*;

@Slf4j
public class MerchantApi extends CMSHelper {

    public Response createMerchant(HashMap<String, String> headers) {
        String jsonBody = getRequestFixture(MERCHANTS_JSON, CREATE_MERCHANT_JSON);
        String url = base_cms_url + merchant;
        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);
        log.info("createMerchant response : \n" + response.prettyPrint());
        return response;
    }

    public Response createMerchantLocations(HashMap<String, String> headers,int merchant_id) {
        String jsonBody = getRequestFixture(MERCHANTS_LOCATION_JSON, CREATE_MERCHANT_LOCATIONS_JSON);
        String url = base_cms_url + merchant_locations.replace("$id", String.valueOf(merchant_id));
        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);
        log.info("createMerchantLocations response : \n" + response.prettyPrint());
        return response;
    }

    public Response createMerchantLocationsProduct(HashMap<String, String> headers,String sku_id,String product_ext_id,String merchant_ext_id,String merchant_location_ext_id,
                                                   String cost_price,String sale_price,String recommended_merchant_price) {
        String jsonBody = getRequestFixture(PRICE_CONTROL_LOCATION_JSON, CREATE_MERCHANT_LOCATIONS_PRODUCT_JSON)
                .replace("$sku_id", sku_id)
                .replace("$product_id", product_ext_id)
                .replace("$cost_price",cost_price)
                .replace("$sale_price",sale_price)
                .replace("$recommended_merchant_price",recommended_merchant_price);

        String url = base_cms_url + merchant_products.replace("$merc_ext_id", merchant_ext_id)
                .replace("$merc_loc_id", merchant_location_ext_id);
        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);
        log.info("createMerchantLocationsProduct response : \n" + response.prettyPrint());
        return response;
    }

    public Response updateRecommendableMerchantProduct(HashMap<String, String> headers,String sku_id,String product_ext_id,String merchant_ext_id,String merchant_location_ext_id,
                                                       String cost_price,String sale_price,String recommended_merchant_price,String merchant_product_id) {
        String jsonBody = getRequestFixture(PRICE_CONTROL_LOCATION_JSON, CREATE_MERCHANT_LOCATIONS_PRODUCT_JSON)
                .replace("$sku_id", sku_id)
                .replace("$product_id", product_ext_id)
                .replace("$cost_price",cost_price)
                .replace("$sale_price",sale_price)
                .replace("$recommended_merchant_price",recommended_merchant_price);
        String url = base_cms_url + merchant_products_put.replace("$merc_ext_id", merchant_ext_id)
                .replace("$merc_loc_id", merchant_location_ext_id).replace("$product_id",merchant_product_id);
        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);
        log.info("updateRecommendableMerchantProduct response : \n" + response.prettyPrint());
        return response;
    }

    public Response getMerchantProductDetails(HashMap<String, String> headers,String merchant_ext_id,String merchant_location_ext_id,String merchant_product_id) {
        String url = base_cms_url + merchant_products_put.replace("$merc_ext_id", merchant_ext_id)
                .replace("$merc_loc_id", merchant_location_ext_id).replace("$product_id",merchant_product_id);
        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();
        log.info("getMerchantProductDetails response : \n" + response.prettyPrint());
        return response;
    }
}
