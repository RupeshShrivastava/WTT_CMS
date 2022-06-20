package com.halodoc.cmstests.medisend;

import static com.halodoc.cmstests.Constants.*;
import java.util.HashMap;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.apiDefinitions.MedisendAPI;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebhookMBSTests extends BaseCMSTest {
    private MedisendAPI medisendAPI = new MedisendAPI();
    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);

    @Test (groups = { "regression" }, priority = 1)
    public void verifyCreateMBSProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String epochTime = String.valueOf(getEpochTime());
        String id = epochTime;
        String productName = CREATE_MBS_PRODUCT_NAME + id;
        String skuId = CREATE_MBS_SKU + id;

        Response response = medisendAPI.createUpdateMBSProduct(headers, id, productName, COST_PRICE, skuId, STATUS_MAI_ACTIVE,
                SEGMENTATION_MBS_CONSUMER_GOODS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, CODE_PATH, HttpStatus.SC_OK));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test (groups = { "regression" }, priority = 2)
    public void verifyUpdateUnmappedMBSProduct() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        String epochTime = String.valueOf(getEpochTime());
        String id = epochTime;
        String productName = CREATE_MBS_PRODUCT_NAME + id;
        String skuId = CREATE_MBS_SKU + id;

        Response response = medisendAPI.createUpdateMBSProduct(headers, id, productName, COST_PRICE, skuId, STATUS_MAI_ACTIVE,
                SEGMENTATION_MBS_CONSUMER_GOODS);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, CODE_PATH, HttpStatus.SC_OK));

        response = medisendAPI.createUpdateMBSProduct(headers, id, productName, UPDATED_PRICE, skuId, STATUS_MAI_INACTIVE, SEGMENTATION_MBS_PHARMA);
        Assert.assertTrue(validateStatusCode(response, HttpStatus.SC_OK));
        Assert.assertTrue(validateResponseValue(response, CODE_PATH, HttpStatus.SC_OK));

        log.info("Exit Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}