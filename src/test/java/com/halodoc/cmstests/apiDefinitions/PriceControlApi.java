package com.halodoc.cmstests.apiDefinitions;

import com.halodoc.cmstests.CMSHelper;
import com.halodoc.utils.http.RestClient;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.halodoc.cmstests.Constants.*;

public class PriceControlApi extends CMSHelper {
    public Response changePriceConversions(HashMap<String, String> headers, String conversion_type, String item_id, String priority, String multiplicative_factor,String attribute_id) {
        String jsonBody = getRequestFixture(PRICE_CONTROL_LOCATION_JSON, PRICE_CONTROL_JSON)
                .replace("$conversion_type", conversion_type)
                .replace("$item_id", item_id)
                .replace("$priority", priority)
                .replace("$multiplicative_factor", multiplicative_factor);
        String url = base_cms_url + GET_CONVERSION.replace("{id}",attribute_id);
        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);
        return response;
    }
}
