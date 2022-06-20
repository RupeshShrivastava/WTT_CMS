package com.halodoc.cmstests.apiDefinitions;

import com.halodoc.cmstests.CMSHelper;
import com.halodoc.utils.http.RestClient;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.halodoc.cmstests.Constants.*;

public class ProductsApi extends CMSHelper {
    public Response productCreation(HashMap<String, String> headers) {
        String jsonBody = getRequestFixture(PRODUCTS_LOCATION_JSON, CREATE_PRODUCTS_JSON);
        String url = base_cms_url + product_endpoint;
        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);
        return response;
    }
}