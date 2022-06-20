package com.halodoc.cmstests.apiDefinitions;

import com.halodoc.cmstests.CMSHelper;
import com.halodoc.utils.http.RestClient;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.halodoc.cmstests.Constants.*;

public class ProductVarianceApi extends CMSHelper {
    public Response getProductGroupRequest(HashMap<String, String> headers, String grpId) {
        String url = CMS_URL + GET_PRODUCT_VARIANT.replace("${group_id}", grpId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();
        return response;
    }

    public Response createProductGroup(HashMap<String, String> headers,String grpName) {
        String jsonBody = getRequestFixture(PRODUCT_VARIANCE_LOCATION, PRODUCT_GROUP_CREATION)
                .replace("$grpName", grpName);
        Response response = apiCreateProductGroupMapping(headers, jsonBody);
        return response;
    }

    private Response apiCreateProductGroupMapping(HashMap<String, String> headers,String jsonBody) {
        String url = CMS_URL + PRODUCT_VARIANT;
        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);
        return response;
    }

    public Response updateProductGroup(HashMap<String, String> headers,String grpId,String grpName,String status) {
        String jsonBody = getRequestFixture(PRODUCT_VARIANCE_LOCATION, PRODUCT_GROUP_UPDATION)
                .replace("$grpName", grpName)
                .replace("$status", status);
        Response response = apiUpdateProductGroupMapping(headers,grpId,jsonBody);
        return response;
    }

    private Response apiUpdateProductGroupMapping(HashMap<String, String> headers,String grpId,String jsonBody) {
        String url = CMS_URL + GET_PRODUCT_VARIANT.replace("${group_id}",grpId);
        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);
        return response;
    }

    public Response searchProductGroup(HashMap<String, String> headers, String groupName) {
        HashMap<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("search_text", groupName);

        Response response = apiSearchProductGroupMapping(headers, queryParam);

        return response;
    }
    private Response apiSearchProductGroupMapping(HashMap<String, String> headers,HashMap<String, String> queryParam) {
        String url = CMS_URL + SEARCH_PRODUCT_VARIANT;
        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet(queryParam);
        return response;
    }

}
