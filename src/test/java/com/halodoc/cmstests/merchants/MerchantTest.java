package com.halodoc.cmstests.merchants;

import static com.halodoc.cmstests.Constants.*;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import com.github.javafaker.Faker;
import com.google.gson.JsonArray;
import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.CMSHelper;
import com.halodoc.cmstests.Constants;
import com.jayway.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nageshkumar
 * since  08/06/17.
 */
@Slf4j
public class MerchantTest extends BaseCMSTest {

    private int merchant_id;
    private String merchant_ext_id;
    private int bank_attribute_merchant_id;
    private String bank_attribute_merchant_ext_id;

    @Test (description = "Get all merchants by lat long", groups = {"sanity", "regression"})
    public void getAllMerchants() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           parameters("latitude", latitude).
                                           parameters("longitude", longitude).
                                           expect().
                                           statusCode(200).
                                           body(containsString("id")).
                                           body(containsString("name")).
                                           body(containsString("address_line")).
                                           body(containsString("business_hours")).
                                           when().
                                           get(base_cms_url + merchant + "/search");

        log.info("getAllMerchants response : \n" + response.prettyPrint());
    }

    @Test (description = "Get all merchants internal api", groups = {"sanity", "regression"})
    public void getAllMerchantsInternal() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
//                                           parameters("latitude", latitude).
//                                           parameters("longitude", longitude).
                                            parameters("per_page","90000").

                                           expect().
                                           statusCode(200).
                                           body(containsString("id")).
                                           body(containsString("name")).
                                           body(containsString("address_line")).
                                           body(containsString("phone_numbers")).
                                           when().
                                           get(base_cms_url + merchant + "/internal/search");

        log.info("getAllMerchantsInternal response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void createMerchant() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           body(getRequestFixture("merchants", "create_merchant.json")).
                                           expect().
                                           statusCode(201).
                                           body(containsString("id")).
                                           body(containsString("name")).
                                           body(containsString("address_line")).
                                           body(containsString("email_addresses")).
                                           when().
                                           post(base_cms_url + merchant);
        merchant_id = response.path("id");
        merchant_ext_id = response.path("external_id");
        log.info("createMerchant response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void createMerchantWithBankAttributes() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("merchants", "create_merchant_merchant_attributes.json")).
                expect().
                statusCode(201).
                body(containsString("id")).
                body(containsString("name")).
                body(containsString("address_line")).
                body(containsString("email_addresses")).
                when().
                post(base_cms_url + merchant);
        bank_attribute_merchant_id = response.path("id");
        bank_attribute_merchant_ext_id = response.path("external_id");
        log.info("createMerchant with merchant attributes response : \n" + response.prettyPrint());
    }

    @Test (groups = {"sanity", "regression"})
    public void updateMerchantBankAttributes() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("merchants", "updated_merchant_bank_attributes.json")).
                expect().
                statusCode(204).
                when().
                put(base_cms_url + update_merchant.replace("$merchant_id",bank_attribute_merchant_ext_id));

        log.info("createMerchant with merchant attributes response : \n" + response.prettyPrint());

         response = given().

                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                expect().
                statusCode(200).
                body("external_id",equalTo(bank_attribute_merchant_ext_id)).
                body(containsString("name")).
                body(containsString("address_line")).
                body(containsString("email_addresses")).
                when().
                get(base_cms_url + merchant+"/external/"+bank_attribute_merchant_ext_id);
        log.info("getMerchantByExternalId response : \n" + response.prettyPrint());
        ArrayList arr = response.getBody().jsonPath().getJsonObject("attributes");
        for (int i = 0; i <arr.size() ; i++) {
            HashMap map = (HashMap) arr.get(i);
            if(map.containsValue("bank_name"))
                Assert.assertEquals(map.get("attribute_value"), UPDATED_BANK_NAME ,"Bank name not updated");
            if(map.containsValue("npwp_number"))
                Assert.assertEquals(map.get("attribute_value"),UPDATED_NPWP ,"npwp_number not updated");
            if(map.containsValue("pharmacy_identity"))
                Assert.assertEquals(map.get("attribute_value"),UPDATED_PHARMACY_IDENTITY ,"pharmacy_identity not updated");
            if(map.containsValue("is_pkp"))
                Assert.assertEquals(map.get("attribute_value"),UPDATED_IS_PKP ,"is_pkp not updated");
        }

    }



    @Test (groups = {"sanity", "regression"})
    public void createMerchantIndividualPKP() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("merchants", "create_merchant_merchant_attributes_Individual_pkp.json")).
                expect().
                statusCode(201).
                body(containsString("id")).
                body(containsString("name")).
                body(containsString("address_line")).
                body(containsString("email_addresses")).
                when().
                post(base_cms_url + merchant);
        merchant_id = response.path("id");
        merchant_ext_id = response.path("external_id");
        log.info("createMerchant with merchant attributes response : \n" + response.prettyPrint());
    }


    @Test (groups = {"sanity", "regression"})
    public void createMerchantWithBlankAttributes() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                contentType("application/json").
                header("X-APP-TOKEN", X_APP_TOKEN).
                body(getRequestFixture("merchants", "create_merchant_merchant_attributes_blank_attributes.json")).
                expect().
                statusCode(400).
                when().
                post(base_cms_url + merchant);
        log.info("createMerchant with blank attributes response : \n" + response.prettyPrint());
    }

    @Test(priority = 1, groups = {"sanity", "regression"})
    public void getMerchantById() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           statusCode(200).
                                           body("id",equalTo(merchant_id)).
                                           body(containsString("name")).
                                           body(containsString("address_line")).
                                           body(containsString("email_addresses")).
                                           when().
                                           get(base_cms_url + merchant+"/"+merchant_id);
        log.info("getMerchantById response : \n" + response.prettyPrint());
    }

    @Test(priority = 1, groups = {"sanity", "regression"})
    public void getMerchantByExternalId() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response = given().

                                           contentType("application/json").
                                           header("X-APP-TOKEN", X_APP_TOKEN).
                                           expect().
                                           statusCode(200).
                                           body("external_id",equalTo(merchant_ext_id)).
                                           body(containsString("name")).
                                           body(containsString("address_line")).
                                           body(containsString("email_addresses")).
                                           when().
                                           get(base_cms_url + merchant+"/external/"+merchant_ext_id);
        log.info("getMerchantByExternalId response : \n" + response.prettyPrint());
    }




}
