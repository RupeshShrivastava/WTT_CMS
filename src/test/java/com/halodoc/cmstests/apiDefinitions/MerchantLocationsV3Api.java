package com.halodoc.cmstests.apiDefinitions;

import static com.halodoc.cmstests.Constants.*;
import java.util.HashMap;
import com.halodoc.utils.http.RestClient;
import io.restassured.response.Response;

public class MerchantLocationsV3Api {
    public Response getMerchantLocations(HashMap<String, String> headers, String latitude, String longitude, String storeId, boolean ignoreLocationHours,
            boolean ignoreLocationDistance) {
        HashMap<String, String> queryParam = new HashMap<String, String>();

        if(!storeId.equals(EMPTY_STRING)) {
            queryParam.put("latitude", latitude);
            queryParam.put("longitude", longitude);
            queryParam.put("store_id", storeId);
            queryParam.put("ignore_location_hours", Boolean.toString(ignoreLocationHours));
            queryParam.put("ignore_location_distance", Boolean.toString(ignoreLocationDistance));
        }

        Response response = apiGetMerchantLocations(headers, queryParam);

        return response;
    }

    public Response apiGetMerchantLocations(HashMap<String, String> headers, HashMap<String, String> queryParam) {
        String url = CMS_URL + MERCHANT_LOCATIONS_V3;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet(queryParam);

        return response;
    }
}