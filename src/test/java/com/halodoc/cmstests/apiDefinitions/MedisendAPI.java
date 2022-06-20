package com.halodoc.cmstests.apiDefinitions;

import static com.halodoc.cmstests.Constants.*;
import java.util.HashMap;
import java.util.concurrent.Callable;

import io.restassured.http.ContentType;
import org.json.simple.JSONArray;
import com.halodoc.cmstests.CMSHelper;
import com.halodoc.utils.http.RestClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.*;

public class MedisendAPI extends CMSHelper {
    public Response createDistributors(HashMap<String, String> headers, String name, String status, String description, String phoneNumbers) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, DISTRIBUTOR_JSON)
                        .replace("$name", name)
                        .replace("$status", status)
                        .replace("$description", description)
                        .replace("$phoneNumber", phoneNumbers);

        Response response = apiCreateDistributors(headers, jsonBody);

        return response;
    }

    public Response createDistributorsWithPaymentInstruction(HashMap<String, String> headers, String name, String status, String description,
            String phoneNumbers, String paymentInstructions) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, DISTRIBUTOR_PAYMENT_INSTRUCTION_JSON)
                .replace("$name", name)
                .replace("$status", status)
                .replace("$description", description)
                .replace("$phoneNumber", phoneNumbers)
                .replace("$paymentInstructions", paymentInstructions);

        Response response = apiCreateDistributors(headers, jsonBody);

        return response;
    }

    public Response getDistributors(HashMap<String, String> headers, String distributorId) {
        Response response = apiGetDistributors(headers, distributorId);

        return response;
    }

    public Response updateDistributors(HashMap<String, String> headers, String distributorId, String name, String status, String description,
            String phoneNumbers) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, DISTRIBUTOR_JSON)
                .replace("$name", name)
                .replace("$status", status)
                .replace("$description", description)
                .replace("$phoneNumber", phoneNumbers);

        Response response = apiUpdateDistributors(headers, distributorId, jsonBody);

        return response;
    }

    public Response updateDistributorsWithPaymentInstruction(HashMap<String, String> headers, String distributorId, String name, String status,
            String description, String phoneNumbers, String paymentInstructions) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, DISTRIBUTOR_PAYMENT_INSTRUCTION_JSON)
                .replace("$name", name)
                .replace("$status", status)
                .replace("$description", description)
                .replace("$phoneNumber", phoneNumbers)
                .replace("$paymentInstructions", paymentInstructions);

        Response response = apiUpdateDistributors(headers, distributorId, jsonBody);

        return response;
    }

    public Response searchDistributors(HashMap<String, String> headers, String name, String status, String pageLimit) {
        HashMap<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("name", name);
        queryParam.put("status", status);
        queryParam.put("per_page", pageLimit);

        Response response = apiSearchDistributors(headers, queryParam);

        return response;
    }

    public Response getDistributorsProducts(HashMap<String, String> headers, String distributorId) {
        Response response = apiGetDistributorsProducts(headers, distributorId);

        return response;
    }

    public Response createDistributorsBranch(HashMap<String, String> headers, String distributorId, String name, String status, String minBasketSize) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, BRANCH_JSON)
                .replace("$name", name)
                .replace("$status", status)
                .replace("$minBasketSize", minBasketSize);

        Response response = apiCreateDistributorsBranch(headers, distributorId, jsonBody);

        return response;
    }

    public Response searchDistributorsBranch(HashMap<String, String> headers, String distributorId, String name, String status, String pageLimit) {
        HashMap<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("name", name);
        queryParam.put("status", status);
        queryParam.put("per_page", pageLimit);

        Response response = apiSearchDistributorsBranch(headers, distributorId, queryParam);

        return response;
    }

    public Response searchBranches(HashMap<String, String> headers, String searchedName, String pageLimit) {
        HashMap<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("search_text", searchedName);
        queryParam.put("per_page", pageLimit);

        Response response = apiSearchBranches(headers, queryParam);

        return response;
    }

    public Response searchMerchantLocation(HashMap<String, String> headers, String searchText, String status, String per_page, String page_no){
        String url = SEARCH_MERCHANT_LOCATION_URL.replace("{search_text}",searchText).replace("{status}", status).replace("{per_page}", per_page).replace("{page_no}", page_no);

        RestClient client = new RestClient(url, headers);
        return client.executeGet();
    }

    public boolean validateSearchMerchantLocation(Response response, String expectedString, String expectedStatus){
        String actualString = response.path(RESULT_PATH + GET_RATE_CARD_ENTITY_PATH + NAME_PATH);
        String actualStatus = response.path(RESULT_PATH + GET_RATE_CARD_ENTITY_PATH + STATUS_PATH);
        return actualString.contains(expectedString.toUpperCase()) && actualStatus.equals(expectedStatus);
    }

    public Response getDistributorsBranchById(HashMap<String, String> headers, String distributorId, String branchId) {
        Response response = apiGetDistributorsBranchById(headers, distributorId, branchId);

        return response;
    }

    public Response updateDistributorsBranch(HashMap<String, String> headers, String distributorId, String branchId, String name, String status,
            String minBasketSize) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, BRANCH_JSON)
                .replace("$name", name)
                .replace("$status", status)
                .replace("$minBasketSize", minBasketSize);

        Response response = apiUpdateDistributorsBranch(headers, distributorId, branchId, jsonBody);

        return response;
    }

    public Response createDistributorsWarehouse(HashMap<String, String> headers, String distributorId, String branchId, String name, String status,
            String address, String landmark, String latitude, String longitude) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, WAREHOUSE_JSON)
                .replace("$name", name)
                .replace("$status", status)
                .replace("$address", address)
                .replace("$landmark", landmark)
                .replace("$latitude", latitude)
                .replace("$longitude", longitude);

        Response response = apiCreateDistributorsWarehouse(headers, distributorId, branchId, jsonBody);

        return response;
    }

    public Response getDistributorsWarehouse(HashMap<String, String> headers, String distributorId, String branchId) {
        Response response = apiGetDistributorsWarehouse(headers, distributorId, branchId);

        return response;
    }

    public Response getDistributorsWarehouseById(HashMap<String, String> headers, String distributorId, String branchId, String warehouseId) {
        Response response = apiGetDistributorsWarehouseById(headers, distributorId, branchId, warehouseId);

        return response;
    }

    public Response updateDistributorsWarehouse(HashMap<String, String> headers, String distributorId, String branchId, String warehouseId,
            String name, String status, String address, String landmark, String latitude, String longitude) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, WAREHOUSE_JSON)
                .replace("$name", name)
                .replace("$status", status)
                .replace("$address", address)
                .replace("$landmark", landmark)
                .replace("$latitude", latitude)
                .replace("$longitude", longitude);

        Response response = apiUpdateDistributorsWarehouse(headers, distributorId, branchId, warehouseId, jsonBody);

        return response;
    }

    public Response createProduct(HashMap<String, String> headers, String distributorId, String branchId, String productId, String status, String costPrice,
            String skuId, String availableQuantity, String discountEnabled, String segmentation) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, PRODUCT_JSON)
                .replace("$productId", productId)
                .replace("$status", status)
                .replace("$costPrice", costPrice)
                .replace("$skuId", skuId)
                .replace("$availableQuantity", availableQuantity)
                .replace("$discountEnabled", discountEnabled);
        jsonBody = segmentation != null ? jsonBody.replace("$segmentation", segmentation) : jsonBody;

        Response response = apiCreateProduct(headers, distributorId, branchId, jsonBody);

        return response;
    }

    public Response updateProduct(HashMap<String, String> headers, String distributorId, String branchId, String mappingId, String productId,
            String status, String costPrice, String skuId, String availableQuantity, String discountEnabled, String segmentation) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, PRODUCT_JSON)
                .replace("$productId", productId)
                .replace("$status", status)
                .replace("$costPrice", costPrice)
                .replace("$skuId", skuId)
                .replace("$availableQuantity", availableQuantity)
                .replace("$discountEnabled", discountEnabled);
        jsonBody = segmentation != null ? jsonBody.replace("$segmentation", segmentation) : jsonBody;

        Response response = apiUpdateProduct(headers, distributorId, branchId, mappingId, jsonBody);

        return response;
    }

    public Response getProduct(HashMap<String, String> headers, String distributorId, String branchId, String mappingId) {
        Response response = apiGetProduct(headers, distributorId, branchId, mappingId);

        return response;
    }

    public Response searchProduct(HashMap<String, String> headers, String distributorId, String branchId, String searchText, String status,
            String mappingStatus) {
        HashMap<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("search_text", searchText);
        queryParam.put("status", status);
        queryParam.put("mapped", mappingStatus);

        Response response = apiSearchProduct(headers, distributorId, branchId, queryParam);

        return response;
    }

    public Response getBranchesByProductId(HashMap<String, String> headers, String productId) {
        Response response = apiGetBranchesByProductId(headers, productId);

        return response;
    }

    public Response getDistributorsByProductId(HashMap<String, String> headers, String productId) {
        Response response = apiGetDistributorsByProductId(headers, productId);

        return response;
    }

    public Response getBranchProductList(HashMap<String, String> headers, String distributorId, String branchId, String productId) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, BRANCH_PRODUCT_LIST_JSON)
                .replace("$productId", productId);

        Response response = apiGetBranchProductList(headers, distributorId, branchId, jsonBody);

        return response;
    }

    public Response createMerchantMapping(HashMap<String, String> headers, String distributorId, String branchId, String merchantLocationId,
            String status) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, MERCHANT_JSON)
                .replace("$merchantLocationId", merchantLocationId)
                .replace("$status", status);

        Response response = apiCreateMerchantMapping(headers, distributorId, branchId, jsonBody);

        return response;
    }

    public Response updateMerchantMapping(HashMap<String, String> headers, String distributorId, String branchId, String mappingId,
            String merchantLocationId, String status) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, MERCHANT_JSON)
                .replace("$merchantLocationId", merchantLocationId)
                .replace("$status", status);

        Response response = apiUpdateMerchantMapping(headers, distributorId, branchId, mappingId, jsonBody);

        return response;
    }

    public Response getBranchesMerchantMapping(HashMap<String, String> headers, String merchantId, String merchantLocationId) {
        Response response = apiGetBranchesMerchantMapping(headers, merchantId, merchantLocationId);

        return response;
    }

    public Response getDistributorsMerchantMapping(HashMap<String, String> headers, String merchantId, String merchantLocationId) {
        Response response = apiGetDistributorsMerchantMapping(headers, merchantId, merchantLocationId);

        return response;
    }

    public Response bookInventory(HashMap<String, String> headers, String distributorId, String branchId, String type, String productId,
            String quantity) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, INVENTORY_JSON)
                .replace("$type", type)
                .replace("$productId", productId)
                .replace("$quantity", quantity);

        Response response = apiBookInventory(headers, distributorId, branchId, jsonBody);

        return response;
    }

    public Response searchPharmacy(HashMap<String, String> headers, String searchedName, String status, String pageLimit) {
        HashMap<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("search_text", searchedName);
        queryParam.put("status", status);
        queryParam.put("per_page", pageLimit);

        Response response = apiSearchBranches(headers, queryParam);

        return response;
    }

    public Response createMasterProduct(HashMap<String, String> headers, String name, String source) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, MASTER_PRODUCT_JSON).replace("$name", name).replace("$source", source);

        Response response = apiCreateMasterProduct(headers, jsonBody);

        return response;
    }

    public Response updateMasterProduct(HashMap<String, String> headers, String productId, String name) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, MASTER_PRODUCT_UPDATE_JSON).replace("$name", name);

        Response response = apiUpdateMasterProduct(headers, productId, jsonBody);

        return response;
    }

    public Response searchMasterProductPaginated(HashMap<String, String> headers, String source) {
        HashMap<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("source", source);

        Response response = apiSearchMasterProductPaginated(headers, queryParam);

        return response;
    }

    public Response searchMasterProduct(HashMap<String, String> headers, String source) {
        HashMap<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("source", source);

        Response response = apiSearchMasterProduct(headers, queryParam);

        return response;
    }

    public Response createUOMMapping(HashMap<String, String> headers, String productId, String sellingProductId, String sellingUOMValue) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, UOM_MAPPING_JSON)
                .replace("$sellingProductId", sellingProductId)
                .replace("$sellingUOMValue", sellingUOMValue);

        Response response = apiCreateUOMMapping(headers, productId, jsonBody);

        return response;
    }

    public Response createProductAttributes(HashMap<String, String> headers, String productId, String sellingUnit) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, PRODUCT_ATTRIBUTES_JSON).replace("$sellingUnit", sellingUnit);

        Response response = apiCreateProductAttributes(headers, productId, jsonBody);

        return response;
    }

    public Response getUOMMapping(HashMap<String, String> headers, String productId) {
        Response response = apiGetUOMMapping(headers, productId);

        return response;
    }

    public Response updateUOMMapping(HashMap<String, String> headers, String productId, String mappingId, String sellingUOM, String uomValue) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, UOM_MAPPING_UPDATE_JSON)
                .replace("$sellingUOMUnit", sellingUOM)
                .replace("$sellingUOMValue", uomValue);

        Response response = apiUpdateUOMMapping(headers, productId, mappingId, jsonBody);

        return response;
    }

    public Response getUOMMappingById(HashMap<String, String> headers, String productId, String mappingId) {
        Response response = apiGetUOMMappingById(headers, productId, mappingId);

        return response;
    }

    public Response deleteUOMMapping(HashMap<String, String> headers, String productId, String mappingId) {
        Response response = apiDeleteUOMMapping(headers, productId, mappingId);

        return response;
    }

    private Response apiCreateDistributors(HashMap<String, String> headers, String jsonBody) {
        String url = CMS_URL + CREATE_DISTRIBUTORS_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);

        return response;
    }

    private Response apiGetDistributors(HashMap<String, String> headers, String distributorId) {
        String url = CMS_URL + GET_DISTRIBUTORS_URL.replace("{distributor_id}", distributorId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    private Response apiUpdateDistributors(HashMap<String, String> headers, String distributorId, String jsonBody) {
        String url = CMS_URL + UPDATE_DISTRIBUTORS_URL.replace("{distributor_id}", distributorId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    private Response apiSearchDistributors(HashMap<String, String> headers, HashMap<String, String> queryParam) {
        String url = CMS_URL + SEARCH_DISTRIBUTORS_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet(queryParam);

        return response;
    }

    private Response apiGetDistributorsProducts(HashMap<String, String> headers, String distributorId) {
        String url = CMS_URL + GET_DISTRIBUTORS_PRODUCTS_URL.replace("{distributor_id}", distributorId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    private Response apiCreateDistributorsBranch(HashMap<String, String> headers, String distributorId, String jsonBody) {
        String url = CMS_URL + CREATE_DISTRIBUTORS_BRANCH_URL.replace("{distributor_id}", distributorId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);

        return response;
    }

    private Response apiSearchDistributorsBranch(HashMap<String, String> headers, String distributorId, HashMap<String, String> queryParam) {
        String url = CMS_URL + SEARCH_DISTRIBUTORS_BRANCH_URL.replace("{distributor_id}", distributorId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet(queryParam);

        return response;
    }

    private Response apiSearchBranches(HashMap<String, String> headers, HashMap<String, String> queryParam) {
        String url = CMS_URL + SEARCH_BRANCHES_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet(queryParam);

        return response;
    }

    private Response apiGetDistributorsBranchById(HashMap<String, String> headers, String distributorId, String branchId) {
        String url = CMS_URL + GET_DISTRIBUTORS_BRANCH_BY_ID_URL
                                .replace("{distributor_id}", distributorId)
                                .replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    private Response apiUpdateDistributorsBranch(HashMap<String, String> headers, String distributorId, String branchId, String jsonBody) {
        String url = CMS_URL + GET_DISTRIBUTORS_BRANCH_BY_ID_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    private Response apiCreateDistributorsWarehouse(HashMap<String, String> headers, String distributorId, String branchId, String jsonBody) {
        String url = CMS_URL + CREATE_DISTRIBUTORS_WAREHOUSE_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);

        return response;
    }

    private Response apiGetDistributorsWarehouse(HashMap<String, String> headers, String distributorId, String branchId) {
        String url = CMS_URL + CREATE_DISTRIBUTORS_WAREHOUSE_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    private Response apiGetDistributorsWarehouseById(HashMap<String, String> headers, String distributorId, String branchId, String warehouseId) {
        String url = CMS_URL + GET_DISTRIBUTORS_WAREHOUSE_BY_ID_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId)
                .replace("{warehouse_id}", warehouseId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    private Response apiUpdateDistributorsWarehouse(HashMap<String, String> headers, String distributorId, String branchId, String warehouseId,
            String jsonBody) {
        String url = CMS_URL + GET_DISTRIBUTORS_WAREHOUSE_BY_ID_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId)
                .replace("{warehouse_id}", warehouseId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    private Response apiCreateProduct(HashMap<String, String> headers, String distributorId, String branchId, String jsonBody) {
        String url = CMS_URL + CREATE_PRODUCT_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);

        return response;
    }

    private Response apiUpdateProduct(HashMap<String, String> headers, String distributorId, String branchId, String productId, String jsonBody) {
        String url = CMS_URL + UPDATE_PRODUCT_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId)
                .replace("{product_id}", productId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    private Response apiGetProduct(HashMap<String, String> headers, String distributorId, String branchId, String productId) {
        String url = CMS_URL + UPDATE_PRODUCT_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId)
                .replace("{product_id}", productId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    private Response apiSearchProduct(HashMap<String, String> headers, String distributorId, String branchId, HashMap<String, String> queryParam) {
        String url = CMS_URL + SEARCH_PRODUCT_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet(queryParam);

        return response;
    }

    private Response apiGetBranchesByProductId(HashMap<String, String> headers, String productId) {
        String path = GET_BRANCHES_BY_PRODUCT_ID_URL.replace("{product_id}", productId);

        RestAssured.baseURI = CMS_URL;
        RequestSpecification requestSpecification = RestAssured.given().urlEncodingEnabled(false);
        requestSpecification.headers(headers);
        Response response = requestSpecification.get(path);

        return response;
    }

    private Response apiGetDistributorsByProductId(HashMap<String, String> headers, String productId) {
        String path = GET_DISTRIBUTORS_BY_PRODUCT_ID_URL.replace("{product_id}", productId);

        RestAssured.baseURI = CMS_URL;
        RequestSpecification requestSpecification = RestAssured.given().urlEncodingEnabled(false);
        requestSpecification.headers(headers);
        Response response = requestSpecification.get(path);

        return response;
    }

    private Response apiGetBranchProductList(HashMap<String, String> headers, String distributorId, String branchId, String jsonBody) {
        String url = CMS_URL + CREATE_PRODUCT_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    private Response apiCreateMerchantMapping(HashMap<String, String> headers, String distributorId, String branchId, String jsonBody) {
        String url = CMS_URL + CREATE_MERCHANT_MAPPING_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);

        return response;
    }

    private Response apiUpdateMerchantMapping(HashMap<String, String> headers, String distributorId, String branchId, String mappingId, String jsonBody) {
        String url = CMS_URL + UPDATE_MERCHANT_MAPPING_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId)
                .replace("{mapping_id}", mappingId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    private Response apiGetBranchesMerchantMapping(HashMap<String, String> headers, String merchantId, String merchantLocationId) {
        String url = CMS_URL + GET_BRANCHES_MERCHANT_MAPPING_URL
                .replace("{merchant_id}", merchantId)
                .replace("{merchant_location_id}", merchantLocationId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    private Response apiGetDistributorsMerchantMapping(HashMap<String, String> headers, String merchantId, String merchantLocationId) {
        String url = CMS_URL + GET_DISTRIBUTORS_MERCHANT_MAPPING_URL
                .replace("{merchant_id}", merchantId)
                .replace("{merchant_location_id}", merchantLocationId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    private Response apiBookInventory(HashMap<String, String> headers, String distributorId, String branchId, String jsonBody) {
        String url = CMS_URL + BOOK_INVENTORY_URL
                .replace("{distributor_id}", distributorId)
                .replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePatch(jsonBody);

        return response;
    }

    private Response apiSearchPharmacy(HashMap<String, String> headers, HashMap<String, String> queryParam) {
        String url = CMS_URL + SEARCH_PHARMACY_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet(queryParam);

        return response;
    }

    public Response createRateCard(HashMap<String, String>headers, String discountValue, String min_quantity, String max_quantity, String entityId){
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, CREATE_RATE_CARD_JSON)
                .replace("$value", discountValue)
                .replace("$min_quantity", min_quantity)
                .replace("$max_quantity",max_quantity);

        Response response = apiCreateRateCard(headers, entityId, jsonBody);

        return response;
    }

    private Response apiCreateRateCard(HashMap<String, String> headers, String entityId, String jsonBody) {
        String url = CMS_URL + CREATE_RATE_CARD_URL[0] + entityId + CREATE_RATE_CARD_URL[1];
        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);
        return response;

    }

    public Response getRateCard(HashMap<String, String>headers, String entityId){
        String url = CMS_URL + CREATE_RATE_CARD_URL[0] + entityId + CREATE_RATE_CARD_URL[1];
        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();
        return response;
    }

    public Response updateRateCard(HashMap<String, String>headers, String entityId, String rateCardExternalId, String discountValue, String rateCardStatus){
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, UPDATE_RATE_CARD_JSON)
                .replace("$value", discountValue)
                .replace("$status", rateCardStatus);
        Response response = apiUpdateRateCard(headers, entityId, rateCardExternalId, jsonBody);
        return response;
    }

    public Response apiUpdateRateCard(HashMap<String, String>headers, String entityId, String rateCardExternalId, String jsonBody){
        String url = CMS_URL + UPDATE_RATE_CARD_URL[0] + entityId + UPDATE_RATE_CARD_URL[1] + rateCardExternalId;
        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);
        return response;
    }

    public Response getRateForProduct(HashMap<String, String>headers, String entityId, String quantity){
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, GET_RATE_PRODUCT_JSON)
                .replace("$quantity", quantity);
        Response response = apiGetRateForProduct(headers, entityId, quantity, jsonBody);
        return response;
    }

    public Response apiGetRateForProduct(HashMap<String, String>headers, String entityId, String quantity, String jsonBody){
        String url = CMS_URL + GET_RATE_PRODUCT_URL[0] + entityId + GET_RATE_PRODUCT_URL[1];
        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);
        return response;
    }

    private Response apiCreateMasterProduct(HashMap<String, String> headers, String jsonBody) {
        String url = CMS_URL + CREATE_MASTER_PRODUCT_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);

        return response;
    }

    private Response apiUpdateMasterProduct(HashMap<String, String> headers, String productId, String jsonBody) {
        String url = CMS_URL + UPDATE_MASTER_PRODUCT_URL.replace("{product_id}", productId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    private Response apiSearchMasterProductPaginated(HashMap<String, String> headers, HashMap<String, String> queryParam) {
        String url = CMS_URL + SEARCH_MASTER_PRODUCT_PAGINATED_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet(queryParam);

        return response;
    }

    private Response apiSearchMasterProduct(HashMap<String, String> headers, HashMap<String, String> queryParam) {
        String url = CMS_URL + SEARCH_MASTER_PRODUCT_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet(queryParam);

        return response;
    }

    private Response apiCreateUOMMapping(HashMap<String, String> headers, String productId, String jsonBody) {
        String url = CMS_URL + CREATE_UOM_MAPPING_URL.replace("{product_id}", productId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);

        return response;
    }

    private Response apiCreateProductAttributes(HashMap<String, String> headers, String productId, String jsonBody) {
        String url = CMS_URL + CREATE_PRODUCT_ATTRIBUTE_URL.replace("{product_id}", productId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);

        return response;
    }

    private Response apiGetUOMMapping(HashMap<String, String> headers, String productId) {
        String url = CMS_URL + CREATE_UOM_MAPPING_URL.replace("{product_id}", productId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    private Response apiUpdateUOMMapping(HashMap<String, String> headers, String productId, String mappingId, String jsonBody) {
        String url = CMS_URL + UPDATE_UOM_MAPPING_URL.replace("{product_id}", productId).replace("{mapping_id}", mappingId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    private Response apiGetUOMMappingById(HashMap<String, String> headers, String productId, String mappingId) {
        String url = CMS_URL + UPDATE_UOM_MAPPING_URL.replace("{product_id}", productId).replace("{mapping_id}", mappingId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    private Response apiDeleteUOMMapping(HashMap<String, String> headers, String productId, String mappingId) {
        String url = CMS_URL + UPDATE_UOM_MAPPING_URL.replace("{product_id}", productId).replace("{mapping_id}", mappingId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeDelete();

        return response;
    }

    public Response createUpdateMAIProduct(HashMap<String, String> headers, String id, String name, String price, String skuId, String status,
            String segmentation) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, MAI_PRODUCT_JSON)
                            .replace("$id", id)
                            .replace("$name", name)
                            .replace("$price", price)
                            .replace("$skuId", skuId)
                            .replace("$status", status)
                            .replace("$segmentation", segmentation);

        Response response = apiCreateMAIProduct(headers, jsonBody);

        return response;
    }

    private Response apiCreateMAIProduct(HashMap<String, String> headers, String jsonBody) {
        String url = WEBHOOK_MAI_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    public Response createUpdateMBSProduct(HashMap<String, String> headers, String id, String name, String price, String skuId, String status,
            String segmentation) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, MBS_PRODUCT_JSON)
                .replace("$id", id)
                .replace("$name", name)
                .replace("$price", price)
                .replace("$skuId", skuId)
                .replace("$status", status)
                .replace("$segmentation", segmentation);

        Response response = apiCreateMBSProduct(headers, jsonBody);

        return response;
    }

    private Response apiCreateMBSProduct(HashMap<String, String> headers, String jsonBody) {
        String url = WEBHOOK_MBS_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    public Response searchDumpProduct(HashMap<String, String> headers, String name, String status, String pageLimit, String entityId) {
        HashMap<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("name", name);
        queryParam.put("status", status);
        queryParam.put("per_page", pageLimit);
        queryParam.put("entity_id", entityId);

        Response response = apiSearchDumpProduct(headers, queryParam);

        return response;
    }

    private Response apiSearchDumpProduct(HashMap<String, String> headers, HashMap<String, String> queryParam) {
        String url = CMS_URL + SEARCH_DUMP_PRODUCT_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet(queryParam);

        return response;
    }

    public Response getProductCount(HashMap<String, String> headers, String distributorId, String branchId) {
        Response response = apiGetProductCount(headers, distributorId, branchId);

        return response;
    }

    private Response apiGetProductCount(HashMap<String, String> headers, String distributorId, String branchId) {
        String url = CMS_URL + GET_PRODUCT_COUNT_URL.replace("{distributor_id}", distributorId).replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    public Response multiGetMapped(HashMap<String, String> headers, String distributorId, String branchId, String[] skuIds) {
        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i < skuIds.length; i++) {
            jsonArray.add(skuIds[i]);
        }

        String jsonBody = jsonArray.toJSONString();

        Response response = apiMultiGetMapped(headers, distributorId, branchId, jsonBody);

        return response;
    }

    private Response apiMultiGetMapped(HashMap<String, String> headers, String distributorId, String branchId, String jsonBody) {
        String url = CMS_URL + MULTI_GET_MAPPED_URL.replace("{distributor_id}", distributorId).replace("{branch_id}", branchId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    public Response requestCSV(HashMap<String, String> headers, String distributorId, String branchId, String type) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, REQUEST_CSV_JSON)
                .replace("$distributorId", distributorId)
                .replace("$branchId", branchId)
                .replace("$type",type);

        Response response = apiRequestCSV(headers, jsonBody);

        return response;
    }

    private Response apiRequestCSV(HashMap<String, String> headers, String jsonBody) {
        String url = OMEGA_URL + REQUEST_CSV_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);

        return response;
    }

    public boolean checkStatusUntil(HashMap<String, String> headers, String requestId, String expectedStatus) {
        await().until(checkStatusCSV(headers, requestId, expectedStatus));

        return true;
    }

    private Callable<Boolean> checkStatusCSV(HashMap<String, String> headers, String requestId, String expectedStatus) {
        return () -> {
            Response response = getCSV(headers, requestId);
            String actualStatus = response.path(STATUS_PATH);
            return actualStatus.equals(expectedStatus);
        };
    }

    public Response getCSV(HashMap<String, String> headers, String requestId) {
        Response response = apiGetCSV(headers, requestId);

        return response;
    }

    private Response apiGetCSV(HashMap<String, String> headers, String requestId) {
        String url = OMEGA_URL + GET_CSV_URL.replace("{request_id}", requestId);

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    public Response createUpdateProductByWebhook(String mode, HashMap<String, String> headers, String entityId, String name, String price, String status,
            String skuId, String segmentation) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, WEBHOOK_PRODUCT_JSON)
                .replace("$entityId", entityId)
                .replace("$name", name)
                .replace("$price", price)
                .replace("$status", status)
                .replace("$skuId", skuId)
                .replace("$segmentation", segmentation);

        Response response = apiCreateUpdateProductByWebhook(mode, headers, jsonBody);

        return response;
    }

    private Response apiCreateUpdateProductByWebhook(String mode, HashMap<String, String> headers, String jsonBody) {
        String url = CMS_URL + CREATE_PRODUCT_WEBHOOK_URL;
        RestClient client = new RestClient(url, headers);
        Response response = null;

        if(mode.equals(MODE_CREATE)) {
            response = client.executePost(jsonBody);
        } else if(mode.equals(MODE_UPDATE)) {
            response = client.executePut(jsonBody);
        }

        return response;
    }

    public Response createDistributorPharmacyEntity(HashMap<String, String> headers, String distributorReferenceId, String pharmacyLocationId, String name, String status, String attributeValue) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, CREATE_DISTRIBUTOR_ENTITY_PHARMACY_JSON)
                .replace("$distributor_reference_id", distributorReferenceId).replace("$pharmacy_location_id", pharmacyLocationId)
                .replace("$name", name).replace("$status", status).replace("$value", attributeValue);

        Response response = apiCreateDistributorPharmacyEntity(headers, jsonBody);
        return response;
    }

    public Response apiCreateDistributorPharmacyEntity(HashMap<String, String> headers, String jsonBody) {
        String url = CMS_URL + CREATE_DISTRIBUTOR_ENTITY_PHARMACY_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executePost(jsonBody);
        return response;
    }

    public Response getDistributorPharmacyEntity(HashMap<String, String> headers, String externalId) {
        String url = CMS_URL + GET_DISTRIBUTOR_ENTITY_PHARMACY_URL + externalId;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    public Response getDistributorPharmacyEntityUsingDistributorAndLocationId(HashMap<String, String> headers, String distributorReferenceID,
            String pharmacyLocationId) {
        String url = CMS_URL + GET_DISTRIBUTOR_ENTITY_USING_QUERY_PARAM_URL[0] + distributorReferenceID + GET_DISTRIBUTOR_ENTITY_USING_QUERY_PARAM_URL[1] + pharmacyLocationId;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    public Response updateDistributorPharmacyEntity(HashMap<String, String> headers, String distributorReferenceId, String pharmacyLocationId, String name, String status, String address) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, UPDATE_DISTRIBUTOR_ENTITY_PHARMACY_JSON)
                .replace("$distributor_reference_id", distributorReferenceId).replace("$pharmacy_location_id", pharmacyLocationId)
                .replace("$name", name).replace("$status", status).replace("$address", address);

        Response response = apiUpdateDistributorPharmacyEntity(headers, jsonBody);
        return response;
    }

    public Response apiUpdateDistributorPharmacyEntity(HashMap<String, String> headers, String jsonBody) {
        String url = CMS_URL + UPDATE_DISTRIBUTOR_ENTITY_PHARMACY_URL;

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    public Response updateDistributorPharmacyEntityUsingExternalId(HashMap<String, String> headers, String distributorReferenceId, String pharmacyLocationId, String name, String status, String address, String externalId) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, UPDATE_DISTRIBUTOR_ENTITY_PHARMACY_JSON)
                .replace("$distributor_reference_id", distributorReferenceId).replace("$pharmacy_location_id", pharmacyLocationId)
                .replace("$name", name).replace("$status", status).replace("$address", address);

        Response response = apiUpdateDistributorPharmacyEntityUsingExternalId(headers, externalId, jsonBody);
        return response;
    }

    public Response apiUpdateDistributorPharmacyEntityUsingExternalId(HashMap<String, String> headers, String externalId, String jsonBody) {
        String url = CMS_URL + GET_DISTRIBUTOR_ENTITY_PHARMACY_URL + externalId;

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    public Response searchDistributorPharmacyEntity(HashMap<String, String> headers, String distributorReferenceId) {
        String url = CMS_URL + SEARCH_USING_DISTRIBUTOR_ID_URL + distributorReferenceId;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    public Response searchDistributorPharmacyEntityUsingText(HashMap<String, String> headers, String searchText) {
        String url = CMS_URL + SEARCH_USING_TEXT_DISTRIBUTOR_URL + searchText;

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    public Response updateDistributorPharmacyEntityMapping(HashMap<String, String> headers, String distributorId, String branchId, String merchantLocationId, String pharmacyReferenceId) {
        String jsonBody = getRequestFixture(MEDISEND_LOCATION_JSON, UPDATE_DISTRIBUTOR_ENTITY_MERCHANT_MAPPING_JSON).replace("$pharmacy_reference_id", pharmacyReferenceId);

        Response response = apiUpdateDistributorPharmacyEntityMapping(headers, distributorId, branchId, merchantLocationId, jsonBody);

        return response;
    }

    public Response apiUpdateDistributorPharmacyEntityMapping(HashMap<String, String> headers, String distributorId, String branchId, String merchantLocationId, String jsonBody) {
        String url = CMS_URL + DISTRIBUTOR_ENTITY_MAPPING_URL[0] + distributorId + DISTRIBUTOR_ENTITY_MAPPING_URL[1] + branchId + DISTRIBUTOR_ENTITY_MAPPING_URL[2] + merchantLocationId;

        RestClient client = new RestClient(url, headers);
        Response response = client.executePut(jsonBody);

        return response;
    }

    public Response getDistributorEntityMappingCount(HashMap<String, String> headers, String distributorId, String branchId) {
        String url = CMS_URL + DISTRIBUTOR_ENTITY_MAPPING_COUNT_URL[0] + distributorId + DISTRIBUTOR_ENTITY_MAPPING_COUNT_URL[1] + branchId + DISTRIBUTOR_ENTITY_MAPPING_COUNT_URL[2];

        RestClient client = new RestClient(url, headers);
        Response response = client.executeGet();

        return response;
    }

    public Response getDistributorSlug(HashMap<String, String> headers, String slugName){
        String url = CMS_URL + GET_DISTRIBUTOR_SLUG + slugName;

        RestClient client = new RestClient(url, headers);
        return client.executeGet();
    }
    public static Response searchDistributorBranchByName(String search_text,String per_page,String page_no,String status){
        return  given().
                contentType(ContentType.JSON).
                header("X-APP-TOKEN",X_APP_TOKEN_CMS).
                queryParam("search_text",search_text).
                when().
                get(CMS_URL+cms_distributor_branch_endpoint);

    }


    public static Response searchDistributorByName(String search_text,String per_page,String page_no,String status){
        return  given().
                contentType(ContentType.JSON).
                header("X-APP-TOKEN",X_APP_TOKEN_CMS).
                queryParam("status",status).
                queryParam("search_text",search_text).
                queryParam("per_page",per_page).
                queryParam("page_no",page_no).
                when().
                get(CMS_URL+cms_distributor_endpoint);

    }


}